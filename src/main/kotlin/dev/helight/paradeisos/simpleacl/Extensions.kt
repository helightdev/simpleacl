package dev.helight.paradeisos.simpleacl

import com.google.gson.Gson

object Extensions {

    val gson: Gson = Gson()

    fun Map<String,Any>.parseAcl(strategy: SerializationStrategy = SerializationStrategy.CLS): ACL {
        return this.map {
            AclEntry.of(it.key, strategy.deserializeValue(it.value as String))
        }.toList()
    }

    fun Iterable<String>.parseAcl(strategy: SerializationStrategy = SerializationStrategy.CLS): ACL {
        return this.map {
            val split = it.split(":")
            if (split.size == 1) {
                AclEntry.of(split[0], 0b111111)
            } else {
                AclEntry.of(split[0], strategy.deserializeValue(split[1]))
            }
        }.toList()
    }

    fun ACL.toAclStringList(strategy: SerializationStrategy = SerializationStrategy.CLS): List<String> {
        return this.map {
           "${it.path}:${strategy.serializeValue(it.permission)}"
        }.toList()
    }

    fun ACL.toAclMap(strategy: SerializationStrategy = SerializationStrategy.CLS): Map<String, Any> {
        return this.associate {
            it.path to strategy.serializeValue(it.permission)
        }
    }

    fun ACL.toAclStringStringMap(strategy: SerializationStrategy = SerializationStrategy.CLS): Map<String, String> {
        return this.associate {
            it.path to strategy.serializeValue(it.permission)
        }
    }

    fun String.toAclEntry(strategy: SerializationStrategy = SerializationStrategy.CLS): AclEntry {
        return AclEntry.of(this, strategy)
    }

    fun ACL.has(query: AclEntry, reverse: Boolean = false): Boolean {
        var permissionValue = 0

        val permissions = this.filter {
            query.collidesWith(it, reverse)
        }.map {
            it.permission
        }.toList()

        if (permissions.isNotEmpty()) permissionValue = permissions.reduce { a, b ->
            AclOperation.combinePermissionValues(a, b)
        }

        return AclOperation.compare(permissionValue, query.permission)
    }

    fun ACL.has(string: String, strategy: SerializationStrategy = SerializationStrategy.CLS, reverse: Boolean = false): Boolean {
        return has(AclEntry.of(string, strategy), reverse)
    }

    fun ACL.roles() = flatMap { it.roles() }

    fun Iterable<AclOperation>.toCls(): String {
        return AclOperation.serializeCls(*this.toList().toTypedArray())
    }
}