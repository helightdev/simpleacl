package dev.helight.paradeisos.simpleacl.compatibility

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonDeserializer
import com.fasterxml.jackson.databind.JsonSerializer
import com.fasterxml.jackson.databind.SerializerProvider
import dev.helight.paradeisos.simpleacl.AclEntry

class AclEntryJacksonSerializer : JsonSerializer<AclEntry>() {
    override fun serialize(value: AclEntry, gen: JsonGenerator, serializers: SerializerProvider) {
        gen.writeString(value.serialize())
    }
}

class AclEntryJacksonDeserializer : JsonDeserializer<AclEntry>() {
    override fun deserialize(p: JsonParser, ctxt: DeserializationContext): AclEntry {
        return AclEntry.of(p.valueAsString)
    }
}