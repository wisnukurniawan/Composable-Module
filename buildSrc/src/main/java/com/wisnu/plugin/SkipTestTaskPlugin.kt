package com.wisnu.plugin

import com.wisnu.plugin.dependencyTracker.AffectedModuleDetector
import org.gradle.api.Plugin
import org.gradle.api.Project

class SkipTestTaskPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        if (!project.isRoot) throw Exception("This plugin should only be applied to root project")

        AffectedModuleDetector.configure(project.gradle, project)

        project.subprojects {
            tasks.whenTaskAdded {
                AffectedModuleDetector.configureTaskGuard(this)
            }
        }
    }
}