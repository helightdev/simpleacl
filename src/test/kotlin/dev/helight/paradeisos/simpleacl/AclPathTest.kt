package dev.helight.paradeisos.simpleacl

import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

internal class AclPathTest {

    @Test
    fun test1() {
        val rule = AclPath("user.*")
        val query = AclPath("user.helight.access")
        assertTrue(query.collidesWith(rule))
    }


    @Test
    fun test2() {
        val rule = AclPath("user.helight")
        val query = AclPath("user.helight.access")
        assertFalse(query.collidesWith(rule))
    }

    @Test
    fun test3() {
        val rule = AclPath("*")
        val query = AclPath("user.helight.access")
        assertTrue(query.collidesWith(rule))
    }

    @Test
    fun test4() {
        val rule = AclPath("user.helight.upload")
        val query = AclPath("user.helight.access")
        assertFalse(query.collidesWith(rule))
    }

    @Test
    fun test5() {
        val rule = AclPath("user.helight.access.tasks")
        val query = AclPath("user.helight.access")
        assertFalse(query.collidesWith(rule))
    }

    @Test
    fun test6() {
        val rule = AclPath("user.helight.access")
        val query = AclPath("user.helight.access")
        assertTrue(query.collidesWith(rule))
    }

    @Test
    fun test7() {
        val rule = AclPath("user.*.access")
        val query = AclPath("user.helight.access")
        assertTrue(query.collidesWith(rule))
    }

    @Test
    fun test8() {
        val rule = AclPath("user.*.tasks")
        val query = AclPath("user.helight.access")
        assertFalse(query.collidesWith(rule))
    }

    @Test
    fun test9() {
        val rule = AclPath("user.*.access")
        val query = AclPath("user.*.access.tasks")
        assertFalse(query.collidesWith(rule))
    }

    @Test
    fun test10() {
        val rule = AclPath("user.*.access.tasks")
        val query = AclPath("user.*.access")
        assertFalse(query.collidesWith(rule))
    }

    @Test
    fun test11() {
        val rule = AclPath("user.helight")
        val query = AclPath("user.helight.access")
        assertFalse(query.collidesWith(rule))
    }


}