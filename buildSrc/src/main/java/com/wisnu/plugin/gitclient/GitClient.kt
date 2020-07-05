package com.wisnu.plugin.gitclient

import org.gradle.api.logging.Logger
import java.io.File
import java.util.concurrent.TimeUnit

interface GitClient {

    fun findChangedFilesSince(
        sha: String,
        top: String = "HEAD",
        includeUncommitted: Boolean = false
    ): List<String>

    fun findPreviousMergeCL(): String?

    /**
     * Abstraction for running execution commands for testability
     */
    interface CommandRunner {
        /**
         * Executes the given shell command and returns the stdout as a string.
         */
        fun execute(command: String): String

        /**
         * Executes the given shell command and returns the stdout by lines.
         */
        fun executeAndParse(command: String): List<String>
    }
}

/**
 * A simple git client that uses system process commands to communicate with the git setup in the
 * given working directory.
 */
class GitClientImpl(
    /**
     * The root location for git
     */
    private val workingDir: File,
    private val logger: Logger?,
    private val commandRunner: GitClient.CommandRunner = RealCommandRunner(
        workingDir = workingDir,
        logger = logger
    )
) : GitClient {

    /**
     * Finds changed file paths since the given sha
     */
    override fun findChangedFilesSince(
        sha: String,
        top: String,
        includeUncommitted: Boolean
    ): List<String> {
        // use this if we don't want local changes
        return commandRunner.executeAndParse(
            if (includeUncommitted) {
                "$CHANGED_FILES_CMD_PREFIX HEAD..$sha"
            } else {
                "$CHANGED_FILES_CMD_PREFIX $top $sha"
            }
        )
    }

    /**
     * checks the history to find the first merge CL.
     */
    override fun findPreviousMergeCL(): String? {
        return commandRunner.executeAndParse(PREV_MERGE_CMD)
            .firstOrNull()
            ?.split(" ")
            ?.firstOrNull()
    }

    private class RealCommandRunner(
        private val workingDir: File,
        private val logger: Logger?
    ) : GitClient.CommandRunner {
        override fun execute(command: String): String {
            val parts = command.split("\\s".toRegex())
            logger?.info("running command $command in $workingDir")
            val proc = ProcessBuilder(*parts.toTypedArray())
                .directory(workingDir)
                .redirectOutput(ProcessBuilder.Redirect.PIPE)
                .redirectError(ProcessBuilder.Redirect.PIPE)
                .start()
            proc.waitFor(1, TimeUnit.MINUTES)
            val stdout = proc
                .inputStream
                .bufferedReader()
                .readText()
            val stderr = proc
                .errorStream
                .bufferedReader()
                .readText()
            val message = stdout + stderr
            if (stderr != "") {
                logger?.error("Response: $message")
            } else {
                logger?.info("Response: $message")
            }
            check(proc.exitValue() == 0) { "Nonzero exit value running git command." }
            return stdout
        }

        override fun executeAndParse(command: String): List<String> {
            return execute(command)
                .split(System.lineSeparator())
                .filterNot { it.isEmpty() }
        }

    }

    companion object {
        const val PREV_MERGE_CMD = "git log -1 --merges --oneline"
        const val CHANGED_FILES_CMD_PREFIX = "git diff --name-only"
    }

}