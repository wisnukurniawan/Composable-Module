package com.wisnu.plugin.dependencyTracker

import org.gradle.BuildAdapter
import org.gradle.BuildResult
import org.gradle.api.invocation.Gradle
import org.gradle.api.logging.LogLevel
import org.gradle.api.logging.Logger
import org.gradle.internal.logging.slf4j.OutputEventListenerBackedLogger
import org.gradle.internal.logging.slf4j.OutputEventListenerBackedLoggerContext
import org.gradle.internal.time.Clock

/**
 * Gradle logger that logs to a string.
 */
class ToStringLogger(
    private val stringBuilder: StringBuilder = StringBuilder()
) : OutputEventListenerBackedLogger(
    "my_logger",
    OutputEventListenerBackedLoggerContext(
        Clock {
            System.currentTimeMillis()
        }
    ).also {
        it.level = LogLevel.DEBUG
        it.setOutputEventListener {
            stringBuilder.appendln(it.toString())
        }
    },
    Clock {
        System.currentTimeMillis()
    }
) {

    /**
     * Returns the current log.
     */
    fun buildString() = stringBuilder.toString()

    companion object {
        fun createWithLifecycle(
            gradle: Gradle,
            onComplete: (String) -> Unit
        ): Logger {
            val logger = ToStringLogger()
            gradle.addBuildListener(object : BuildAdapter() {
                override fun buildFinished(result: BuildResult) {
                    onComplete(logger.buildString())
                }
            })
            return logger
        }
    }
}