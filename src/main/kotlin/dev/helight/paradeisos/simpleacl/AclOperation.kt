package dev.helight.paradeisos.simpleacl

/**
 * A list enum of the CURSED / CRUDES operations
 *
 * **Read**: Reading or downloading of a document or entity
 *
 * **Update**: Updating a document or entity
 *
 * **Create**: Creating a document or entity
 *
 * **Delete**: Deleting a document or entity
 *
 * **Execute**: Executing or triggering a task that may be related to a document or entity
 *
 * **Special**: Special permission reserved for non listed cases
 */
enum class AclOperation(private val bitMask: Int, private val char: Char) {

    // Read
    Read(0b100000, 'r'),

    // Write
    Update(0b010000, 'u'),
    Create(0b001000, 'c'),
    Delete(0b000100, 'd'),

    // Execute
    Execute(0b000010, 'e'),

    // Special
    Special(0b000001, 's');

    fun isPresentIn(permissionValue: Int): Boolean {
        return permissionValue.and(bitMask) >= 1
    }

    companion object {

        //Char List String
        fun parseCls(string: String): Int {
            val list = values().filter {
                string.contains(it.char)
            }.toMutableList()
            if (string.contains("w")) {
                if (!list.contains(Update)) list.add(Update)
                if (!list.contains(Create)) list.add(Create)
                if (!list.contains(Delete)) list.add(Delete)
            }
            if (string.contains("x")) {
                if (!list.contains(Execute)) list.add(Execute)
            }
            return compose(*list.toTypedArray())
        }

        fun serializeCls(vararg operations: AclOperation): String {
            return operations.map {
                it.char
            }.map { it.toString() }.reduce { a,b -> a+b }
        }

        fun combinePermissionValues(vararg ints: Int): Int {
            if (ints.isEmpty()) return 0x00
            var iterate = ints[0]
            for (i: Int in 1 until ints.size) {
                iterate = iterate.or(ints[i])
            }
            return iterate
        }

        fun parse(permissionValue: Int): List<AclOperation> {
            return values().filter { it.isPresentIn(permissionValue) }.toList()
        }

        fun compose(vararg operations: AclOperation): Int {
            return combinePermissionValues(*operations.map { it.bitMask }.toIntArray())
        }

        fun compare(actual: Int, vararg operations: AclOperation): Boolean {
            return operations.all { it.isPresentIn(actual) }
        }

        fun compare(actual: Int, expect: Int): Boolean {
            return compare(actual, *parse(expect).toTypedArray())
        }
    }
}