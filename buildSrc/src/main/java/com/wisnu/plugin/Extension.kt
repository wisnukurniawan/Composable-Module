package com.wisnu.plugin

import org.gradle.api.Project

val Project.isRoot get() = this == rootProject