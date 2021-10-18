package dev.helight.paradeisos.simpleacl

import kotlin.math.min

class AclPath(
    val path: String
) {

    fun collidesWith(b: AclPath, checkReverse: Boolean = false): Boolean {
        val ruleSplit = b.path.split(".")
        val querySplit = path.split(".")
        val min = min(ruleSplit.size, querySplit.size)
        val equalLength = ruleSplit.size == querySplit.size
        val ruleShorter = min == ruleSplit.size
        if (debug) println()
        if (debug) println("Matching collision between '$path' with '${b.path}'")
        var loopResult = true
        for (index: Int in 0 until min) {
            val rulePart = ruleSplit[index]
            val queryPart = querySplit[index]
            val result = compare(rulePart, queryPart, ruleShorter && min - 1 == index)
            if (debug) println("[$index] '$queryPart' collide with '$rulePart' => $result")
            return when (result) {
                0 -> {
                    loopResult = false
                    break
                }
                1 -> continue
                2 -> {
                    if (debug) println("[+] Rule ends with asterisk")
                    true
                }
                else -> error("Invalid rule part collision result")
            }
        }
        if (loopResult && equalLength) {
            if (debug) println("[+] Iteration ended with true and length is equal: true")
            return true
        } else if (loopResult && !equalLength && debug) println("[/] Iteration ended with true and length is equal: false")
        if (!loopResult && checkReverse) {
            if (b.collidesWith(this, checkReverse = false)) {
                if (debug) println("[+] Reverse collision match: true")
                return true
            } else if (debug) {
                println("[/] Reverse collision match: false")
            }
        }
        if (debug) println("[-] '$path' doesn't collide with '${b.path}'")
        return false
    }

    private fun compare(rulePart: String, query: String, ruleEnd: Boolean): Int {
        return when(rulePart) {
            "*" -> when (ruleEnd) {
                true -> 2
                false -> 1
            }
            query -> 1
            else -> 0
        }
    }

    companion object {
        var debug: Boolean = false
    }

}