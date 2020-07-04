package com.wisnu.plugin.dependencyTracker

import com.wisnu.plugin.getSupportRootFolder
import org.gradle.api.Project
import java.io.File
import org.gradle.api.logging.Logger

/**
 * Creates a project graph for fast lookup by file path
 */
class ProjectGraph(project: Project, val logger: Logger? = null) {
    private val rootNode: Node

    init {
        // always use cannonical file: b/112205561
        logger?.info("initializing ProjectGraph")
        rootNode = Node(logger)
        val rootProjectDir = project.getSupportRootFolder().canonicalFile
        project.subprojects.forEach {
            logger?.info("creating node for ${it.path}")
            val relativePath = it.projectDir.canonicalFile.toRelativeString(rootProjectDir)
            val sections = relativePath.split(File.separatorChar)
            logger?.info("relative path: $relativePath , sections: $sections")
            val leaf = sections.fold(rootNode) { left, right ->
                left.getOrCreateNode(right)
            }
            leaf.project = it
        }
        logger?.info("finished creating ProjectGraph")
    }

    /**
     * Finds the project that contains the given file.
     * The file's path prefix should match the project's path.
     */
    fun findContainingProject(filePath: String): Project? {
        val sections = filePath.split(File.separatorChar)
        logger?.info("finding containing project for $filePath , sections: $sections")
        return rootNode.find(sections, 0)
    }

    private class Node(val logger: Logger? = null) {
        var project: Project? = null
        private val children = mutableMapOf<String, Node>()
        fun getOrCreateNode(key: String): Node {
            return children.getOrPut(key) { Node(logger) }
        }

        fun find(sections: List<String>, index: Int): Project? {
            logger?.info("finding $sections with index $index in ${project?.path ?: "root"}")
            if (sections.size <= index) {
                logger?.info("nothing")
                return project
            }
            val child = children[sections[index]]
            return if (child == null) {
                logger?.info("no child found, returning ${project?.path ?: "root"}")
                project
            } else {
                child.find(sections, index + 1)
            }
        }
    }
}
