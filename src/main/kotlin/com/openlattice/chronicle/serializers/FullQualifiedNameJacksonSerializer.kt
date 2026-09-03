/*
 * Copyright (C) 2018. OpenLattice, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * You can contact the owner of the copyright at support@openlattice.com
 */
package com.openlattice.chronicle.serializers

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializerProvider
import com.fasterxml.jackson.databind.deser.std.StdDeserializer
import com.fasterxml.jackson.databind.module.SimpleModule
import com.fasterxml.jackson.databind.ser.std.StdSerializer
import org.apache.olingo.commons.api.edm.FullQualifiedName
import java.io.IOException
import com.openlattice.chronicle.util.JsonFields

public object FullQualifiedNameJacksonSerializer {
    private val module = SimpleModule(
        FullQualifiedNameJacksonSerializer::class.java.name
    )

    init {
        module.addSerializer(
            FullQualifiedName::class.java, Serializer()
        )
        module.addDeserializer(
            FullQualifiedName::class.java, Deserializer()
        )
    }

    @JvmStatic
    public fun registerWithMapper(mapper: ObjectMapper) {
        mapper.registerModule(module)
    }

    public class Serializer @JvmOverloads constructor(clazz: Class<FullQualifiedName> = FullQualifiedName::class.java) :
        StdSerializer<FullQualifiedName>(clazz) {
        @Throws(IOException::class)
        override fun serialize(value: FullQualifiedName, jgen: JsonGenerator, provider: SerializerProvider) {
            jgen.writeStartObject()
            jgen.writeStringField(JsonFields.NAMESPACE_FIELD, value.namespace)
            jgen.writeStringField(JsonFields.NAME_FIELD, value.name)
            jgen.writeEndObject()
        }
    }

    public class Deserializer private constructor(clazz: Class<FullQualifiedName>) :
        StdDeserializer<FullQualifiedName>(clazz) {
        public constructor() : this(FullQualifiedName::class.java)

        @Throws(IOException::class)
        override fun deserialize(jp: JsonParser, ctxt: DeserializationContext): FullQualifiedName {
            val node = jp.codec.readTree<JsonNode>(jp)
            return FullQualifiedName(
                node[JsonFields.NAMESPACE_FIELD].asText(),
                node[JsonFields.NAME_FIELD].asText()
            )
        }
    }
}
