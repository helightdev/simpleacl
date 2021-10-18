package dev.helight.paradeisos.simpleacl

import dev.helight.paradeisos.simpleacl.Extensions.has
import dev.helight.paradeisos.simpleacl.Extensions.parseAcl
import dev.helight.paradeisos.simpleacl.Extensions.toAclEntry
import dev.helight.paradeisos.simpleacl.Extensions.toAclMap
import dev.helight.paradeisos.simpleacl.Extensions.toAclStringList
import dev.helight.paradeisos.simpleacl.Extensions.toAclStringStringMap
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class SimpleAclTest {

    @Test
    fun test1() {
        val actual = listOf(
            "user.helight:100000"
        ).parseAcl(SerializationStrategy.Radix2)
        assertTrue(actual.has("user.helight:r"))
    }

    @Test
    fun test2() {
        val actual = listOf(
            "user.helight:010000"
        ).parseAcl(SerializationStrategy.Radix2)
        assertFalse(actual.has("user.helight:r"))
    }

    @Test
    fun test3() {
        val actual = listOf(
            "user.helight:100000",
            "user.helight:010000"
        ).parseAcl(SerializationStrategy.Radix2)
        assertTrue(actual.has("user.helight:ru"))
    }

    @Test
    fun test4() {
        val actual = listOf(
            "user.helight:100000",
            "user.helight:001000"
        ).parseAcl(SerializationStrategy.Radix2)
        assertFalse(actual.has("user.helight:ru"))
    }

    @Test
    fun test5() {
        val actual = listOf(
            "user.helight:100000",
            "user.helight:001000",
            "random.other.path:111111"
        ).parseAcl(SerializationStrategy.Radix2)
        assertFalse(actual.has("user.helight:ru"))
    }

    @Test
    fun test6() {
        val actual = listOf(
            "*:100000",
            "user.helight:010000"
        ).parseAcl(SerializationStrategy.Radix2)
        assertTrue(actual.has("user.helight:ru"))
    }

    @Test
    fun test7() {
        val actual = listOf(
            "*:001000",
            "user.helight:010000"
        ).parseAcl(SerializationStrategy.Radix2)
        assertFalse(actual.has("user.helight:ru"))
    }

    @Test
    fun test8() {
        val actual = listOf(
            "*:100000",
            "user.*:010000",
            "user.helight:001000"
        ).parseAcl(strategy = SerializationStrategy.Radix2)
        assertTrue(actual.has("user.helight:ruc"))
    }

    @Test
    fun test9() {
     val actual = listOf(
            "*:r",
            "user.*:u",
            "user.helight:c"
        ).parseAcl(strategy = SerializationStrategy.CLS)
        assertTrue(actual.has("user.helight:ruc"))
    }

    @Test
    fun test10() {
        val actual = listOf(
            "user.helight"
        ).parseAcl(strategy = SerializationStrategy.CLS)
        assertTrue(actual.has("user.helight:ru"))
    }

    @Test
    fun clsLinuxFilePermissionCompatibility() {
        val actual = listOf(
            "user.helight:rucde"
        ).parseAcl(strategy = SerializationStrategy.CLS)
        assertTrue(actual.has("user.helight:rwx"))
        assertFalse(actual.has("user.helight:s"))
    }

    @Test
    fun splintedTest() {
        val actual = "user.helight:curse".toAclEntry().splinted()
        assertTrue(actual.has("user.helight:c"))
        assertTrue(actual.has("user.helight:u"))
        assertTrue(actual.has("user.helight:r"))
        assertTrue(actual.has("user.helight:s"))
        assertTrue(actual.has("user.helight:e"))
        assertEquals(actual.size, 5)
    }

    @Test
    fun noErrorCheck1() {
        val actual = listOf(
            "user.helight:100000",
            "user.helight:001000",
            "random.other.path:111111"
        ).parseAcl(SerializationStrategy.Radix2)

        println("Map " + actual.toAclMap())
        println("StringStringMap " + actual.toAclStringStringMap())
        println("Radix2 " + actual.toAclStringList(strategy = SerializationStrategy.Radix2))
        println("Radix10 " + actual.toAclStringList(strategy = SerializationStrategy.Radix10))
        println("Radix16 " + actual.toAclStringList(strategy = SerializationStrategy.Radix16))
        println("CLS " + actual.toAclStringList(strategy = SerializationStrategy.CLS))

        assertTrue(true)
    }
}