package com.jaynewstrom.viewmode

import org.junit.Assert.assertEquals

inline fun <reified T> assertThrowsWithMessage(message: String, block: () -> Unit) {
    try {
        block()
    } catch (e: Throwable) {
        if (e is T) {
            assertEquals(e.message, message)
            return
        } else {
            throw e
        }
    }
    throw AssertionError("Expected ${T::class}")
}