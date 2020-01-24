package com.wisnu.launcher.compiler

private val classMap = mutableMapOf<String, Class<*>>()

private inline fun <reified T : Any> Any.castOrNull() = this as? T

internal fun <T> String.loadClassOrNull(): Class<out T>? =
    classMap.getOrPut(this) {
        try {
            Class.forName(this)
        } catch (e: ClassNotFoundException) {
            return null
        }
    }.castOrNull()
