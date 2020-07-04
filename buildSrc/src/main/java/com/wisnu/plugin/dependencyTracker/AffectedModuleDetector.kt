package com.wisnu.plugin.dependencyTracker

import com.wisnu.plugin.*
import com.wisnu.plugin.gitclient.GitClientImpl
import org.gradle.BuildAdapter
import org.gradle.api.GradleException
import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.api.invocation.Gradle
import org.gradle.api.logging.Logger
import java.io.File

/**
 * A utility class that can discover which files are changed based on git history.
 *
 * To enable this, you need to pass [ENABLE_ARG] into the build as a command line parameter
 * (-P<name>)
 *
 * Passing [DEPENDENT_PROJECTS_ARG] will result in only DEPENDENT_PROJECTS being returned (see enum)
 * Passing [CHANGED_PROJECTS_ARG] will behave likewise.
 *
 * If neither of those are passed, ALL_AFFECTED_PROJECTS is returned.
 *
 * Currently, it checks git logs to find last merge CL to discover where the anchor CL is.
 *
 * Eventually, we'll move to the props passed down by the build system when it is available.
 *
 * Since this needs to check project dependency graph to work, it cannot be accessed before
 * all projects are loaded. Doing so will throw an exception.
 */
abstract class AffectedModuleDetector {

    /**
     * Returns whether this project was affected by current changes..
     */
    abstract fun shouldInclude(project: Project): Boolean

    companion object {
        private const val ROOT_PROP_NAME = "affectedModuleDetector"
        private const val LOG_FILE_NAME = "affected_module_detector_log.txt"
        private const val ENABLE_ARG = "com.wisnu.enableAffectedModuleDetection"
        private const val DEPENDENT_PROJECTS_ARG = "com.wisnu.dependentProjects"
        private const val CHANGED_PROJECTS_ARG = "com.wisnu.changedProjects"
        private const val LOG_FILE_LOCATION = "./"

        @JvmStatic
        fun configure(gradle: Gradle, rootProject: Project) {
            val enabled = rootProject.hasProperty(ENABLE_ARG)
            if (!enabled) {
                setInstance(rootProject, AcceptAll())
                return
            }
            val logger = ToStringLogger.createWithLifecycle(gradle) { log ->
                val distDir = File(LOG_FILE_LOCATION)
                distDir.let {
                    val outputFile = it.resolve(LOG_FILE_NAME)
                    outputFile.writeText(log)
                    println("wrote dependency log to ${outputFile.absolutePath}")
                }
            }
            logger.info("setup: enabled: $enabled")
            gradle.addBuildListener(object : BuildAdapter() {
                override fun projectsEvaluated(gradle: Gradle) {
                    logger.lifecycle("projects evaluated")
                    setInstance(rootProject, AffectedModuleDetectorImpl(rootProject, logger))
                }
            })
        }

        private fun setInstance(
            rootProject: Project,
            detector: AffectedModuleDetector
        ) {
            rootProject.extensions.add(ROOT_PROP_NAME, detector)
        }

        private fun getInstance(project: Project): AffectedModuleDetector? {
            val extensions = project.rootProject.extensions
            return extensions.getByName(ROOT_PROP_NAME) as? AffectedModuleDetector
        }

        private fun getOrThrow(project: Project): AffectedModuleDetector {
            return getInstance(project) ?: throw GradleException(
                """
                    Tried to get affected module detector too early.
                    You cannot access it until all projects are evaluated.
                        """.trimIndent()
            )
        }

        /**
         * Call this method to configure the given task to execute only if the owner project
         * is affected by current changes
         */
        @Throws(GradleException::class)
        @JvmStatic
        fun configureTaskGuard(task: Task) {
            task.onlyIf { getOrThrow(task.project).shouldInclude(task.project) }
        }

    }
}

/**
 * Implementation that accepts everything without checking.
 */
private class AcceptAll(
    private val wrapped: AffectedModuleDetector? = null,
    private val logger: Logger? = null
) : AffectedModuleDetector() {
    override fun shouldInclude(project: Project): Boolean {
        val wrappedResult = wrapped?.shouldInclude(project)
        logger?.info("[AcceptAll] wrapper returned $wrappedResult but I'll return true")
        return true
    }
}

/**
 * Real implementation that checks git logs to decide what is affected.
 *
 * If any file outside a module is changed, we assume everything has changed.
 *
 * When a file in a module is changed, all modules that depend on it are considered as changed.
 */
class AffectedModuleDetectorImpl constructor(
    private val rootProject: Project,
    private val logger: Logger?
) : AffectedModuleDetector() {
    private val git by lazy {
        GitClientImpl(rootProject.projectDir, logger)
    }

    private val dependencyTracker by lazy {
        DependencyTracker(rootProject, logger)
    }

    private val allProjects by lazy {
        rootProject.subprojects.toSet()
    }

    private val projectGraph by lazy {
        ProjectGraph(rootProject, logger)
    }

    private val affectedProjects by lazy {
        findAffectedProjects()
    }

    private val changedProjects by lazy {
        findChangedProjects()
    }

    private val dependentProjects by lazy {
        findDependentProjects()
    }

    override fun shouldInclude(project: Project): Boolean {
        println("wisnu project ${project}")
        println("wisnu affected ${dependentProjects}")
        println("wisnu affected ${changedProjects}")
        println("wisnu affected ${affectedProjects}")
        println("wisnu contains ${affectedProjects.contains(project)}")
        return (project.isRoot || affectedProjects.contains(project)).also {
            logger?.info("checking whether I should include ${project.path} and my answer is $it")
        }
    }

    private fun findAffectedProjects(): Set<Project> {
        return changedProjects + dependentProjects
    }

    private fun findDependentProjects(): Set<Project> {
        return changedProjects.flatMap { dependencyTracker.findAllDependents(it) }.toSet()
    }

    private fun findChangedProjects(): Set<Project> {
        val lastMergeSha = git.findPreviousMergeCL() ?: return allProjects
        val changedFiles = git.findChangedFilesSince(
            sha = lastMergeSha,
            includeUncommitted = true
        )
        val changedProjects: MutableSet<Project> = mutableSetOf()
        changedFiles.forEach { filePath ->
            val containingProject = findContainingProject(filePath)
            if (containingProject == null) {
                logger?.info("Couldn't find containing project for file$filePath.")
            } else {
                changedProjects.add(containingProject)
                logger?.info("For file $filePath containing project is $containingProject. Adding to changedProjects.")
            }
        }
        return changedProjects
    }

    private fun findContainingProject(filePath: String): Project? {
        return projectGraph.findContainingProject(filePath).also {
            logger?.info("search result for $filePath resulted in ${it?.path}")
        }
    }
}