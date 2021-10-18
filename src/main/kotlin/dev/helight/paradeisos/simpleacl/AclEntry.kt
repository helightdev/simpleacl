package dev.helight.paradeisos.simpleacl

import dev.helight.paradeisos.simpleacl.Extensions.toCls

data class AclEntry(
    val path: String,
    val permission: Int
) {

    /**
     * Gets the permission value as a char list string
     */
    fun cls() = AclOperation.parse(permission).toCls()

    /**
     * Gets the permission value as a list of allowed operations
     */
    fun operations() = AclOperation.parse(permission)

    /**
     * Splits all operations into different rules making this compatible with default role based algorithms
     * which just check whether a user has an exactly matching role, mostly the '*' should also be
     * supported
     */
    fun splinted() = operations().map {
        of(path, it)
    }.toList()

    /**
     * Splits all operations into singular rules making this directly injectable into string list based
     * role systems
     */
    fun roles(basePath: Boolean = false): List<String> {
        val base = operations().map {
            "$path.${it.name.lowercase()}"
        }.toMutableList()
        if (basePath || permission.and(0b111111) == 0b111111) base.add(path) // Add base permission if full permission
        return base
    }

    /**
     * Serializes the whole entry to a single string while encoding the value using [strategy]
     */
    fun serialize(strategy: SerializationStrategy = SerializationStrategy.CLS): String {
        return "$path:${strategy.serializeValue(permission)}"
    }

    /**
     * Checks whether the both paths collide. Placeholders will only be checked on the calling instance
     *
     * @param query target/non calling entry
     * @param reverse calls `collidesWith` on the target entry if the initial collide check returns false
     *
     */
    fun collidesWith(query: AclEntry, reverse: Boolean = false): Boolean {
        return AclPath(path).collidesWith(AclPath(query.path), reverse)
    }

    override fun toString(): String {
        return "'$path:${cls()}'"
    }


    companion object {
        fun of(path: String, vararg operations: AclOperation): AclEntry {
            return AclEntry(path, AclOperation.compose(*operations))
        }

        fun of(path: String, permission: Int): AclEntry {
            return AclEntry(path, permission)
        }

        fun of(string: String, strategy: SerializationStrategy = SerializationStrategy.CLS): AclEntry {
            val split = string.split(":")
            return if (split.size == 2) {
                AclEntry(split[0], strategy.deserializeValue(split[1]))
            } else {
                AclEntry(split[0], 0b111111)
            }
        }
    }
}

