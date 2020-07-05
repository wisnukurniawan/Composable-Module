package com.wisnu.feature

import com.wisnu.kurn.core_utils.Constant
import junit.framework.Assert.assertEquals
import org.junit.Test

class AdditionTest {

    @Test
    fun add() {
        assertEquals(2, 1 + 1)
        assertEquals(2, Constant.RANDOM)
    }

}