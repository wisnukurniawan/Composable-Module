package com.wisnu.plugin

import org.gradle.api.Project
import org.gradle.api.plugins.ExtraPropertiesExtension
import java.io.File

val Project.isRoot get() = this == rootProject

/**
 * Returns the path to the canonical root project directory, e.g. {@code frameworks/support}.
 */
fun Project.getSupportRootFolder(): File {
    // When the `ui` project is merged, this can be simplified to `project.rootDir`.
    val extension = project.rootProject.property("ext") as ExtraPropertiesExtension
    return extension.get("supportRootFolder") as File
}
