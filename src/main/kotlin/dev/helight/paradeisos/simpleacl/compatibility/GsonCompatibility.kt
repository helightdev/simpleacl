package dev.helight.paradeisos.simpleacl.compatibility

import com.google.gson.*
import dev.helight.paradeisos.simpleacl.AclEntry
import dev.helight.paradeisos.simpleacl.Extensions.gson
import java.lang.reflect.Type

class AclEntrySerializer : JsonSerializer<AclEntry> {
    override fun serialize(src: AclEntry, typeOfSrc: Type, context: JsonSerializationContext): JsonElement {
        return gson.toJsonTree(src.serialize())
    }
}

class AclEntryDeserializer : JsonDeserializer<AclEntry> {
    override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): AclEntry {
        return AclEntry.of(json.asString)
    }
}