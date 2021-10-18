package dev.helight.paradeisos.simpleacl

import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

internal class AclOperationTest {

    @Test
    fun test1() {
        val expect = AclOperation.compose()
        val actual = AclOperation.compose()
        assertTrue(AclOperation.compare(actual, expect))
    }

    @Test
    fun test2() {
        val expect = AclOperation.compose()
        val actual = AclOperation.compose(AclOperation.Read)
        assertTrue(AclOperation.compare(actual, expect))
    }

    @Test
    fun test3() {
        val expect = AclOperation.compose(AclOperation.Read)
        val actual = AclOperation.compose()
        assertFalse(AclOperation.compare(actual, expect))
    }

    @Test
    fun test4() {
        val expect = AclOperation.compose(AclOperation.Read)
        val actual = AclOperation.compose(AclOperation.Read)
        assertTrue(AclOperation.compare(actual, expect))
    }

    @Test
    fun test5() {
        val expect = AclOperation.compose(AclOperation.Read, AclOperation.Update)
        val actual = AclOperation.compose(AclOperation.Read)
        assertFalse(AclOperation.compare(actual, expect))
    }

    @Test
    fun test6() {
        val expect = AclOperation.compose(AclOperation.Read, AclOperation.Update)
        val actual = AclOperation.compose(AclOperation.Read, AclOperation.Update)
        assertTrue(AclOperation.compare(actual, expect))
    }

    @Test
    fun test7() {
        val expect = AclOperation.compose(AclOperation.Read, AclOperation.Update)
        val actual = AclOperation.compose(AclOperation.Read, AclOperation.Delete)
        assertFalse(AclOperation.compare(actual, expect))
    }

    @Test
    fun test8() {
        val expect = AclOperation.compose(AclOperation.Read)
        val actual = AclOperation.compose(AclOperation.Read, AclOperation.Update)
        println(AclOperation.serializeCls(AclOperation.Read, AclOperation.Update))
        assertTrue(AclOperation.compare(actual, expect))
    }
}