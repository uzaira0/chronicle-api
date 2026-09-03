package com.openlattice.chronicle

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.dataformat.smile.SmileFactory
import com.fasterxml.jackson.datatype.guava.GuavaModule
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.KotlinModule
import org.junit.Assert
import org.junit.Test
import org.slf4j.LoggerFactory
import java.io.IOException
import java.util.*
import java.util.function.Consumer

/**
 * Base class for writing jackson serialization tests.
 *
 * @param <T> The type whose serialization will be tested.
</T> */
abstract class AbstractJacksonSerializationTest<T> {
    protected val logger = LoggerFactory.getLogger(javaClass)
    @Test
    @Throws(IOException::class)
    fun testSerdes() {
        val data = sampleData
        val result = serialize(data)
        logResult(result)
        if (clazz != null) {
            Assert.assertEquals(data, result.deserializeJsonString(clazz))
            Assert.assertEquals(data, result.deserializeJsonBytes(clazz))
            Assert.assertEquals(data, result.deserializeSmileBytes(clazz))
        } else if (typeReference != null) {
            Assert.assertEquals(data, result.deserializeJsonString(typeReference))
            Assert.assertEquals(data, result.deserializeJsonBytes(typeReference))
            Assert.assertEquals(data, result.deserializeSmileBytes(typeReference))
        }
    }

    @Throws(IOException::class)
    protected fun serialize(data: T): SerializationResult<T> {
        return SerializationResult(mapper.writeValueAsString(data),
                                   mapper.writeValueAsBytes(data),
                                   smile.writeValueAsBytes(data))
    }

    protected open fun logResult(result: SerializationResult<T>) {}


    protected abstract val sampleData: T
    protected abstract val clazz: Class<T>?

    protected val typeReference: TypeReference<T>?
        protected get() = null

    protected class SerializationResult<T>(val jsonString: String, jsonBytes: ByteArray, smileBytes: ByteArray) {
        private val jsonBytes: ByteArray
        private val smileBytes: ByteArray

        init {
            this.jsonBytes = Arrays.copyOf(jsonBytes, jsonBytes.size)
            this.smileBytes = Arrays.copyOf(smileBytes, smileBytes.size)
        }

        @Throws(IOException::class)
        fun deserializeJsonString(clazz: Class<T>?): T {
            return mapper.readValue(jsonString, clazz)
        }

        @Throws(IOException::class)
        fun deserializeJsonBytes(clazz: Class<T>?): T {
            return mapper.readValue(jsonBytes, clazz)
        }

        @Throws(IOException::class)
        fun deserializeSmileBytes(clazz: Class<T>?): T {
            return smile.readValue(smileBytes, clazz)
        }

        @Throws(IOException::class)
        fun deserializeJsonString(typeRef: TypeReference<T>?): T {
            return mapper.readValue(jsonString, typeRef)
        }

        @Throws(IOException::class)
        fun deserializeJsonBytes(typeRef: TypeReference<T>?): T {
            return mapper.readValue(jsonBytes, typeRef)
        }

        @Throws(IOException::class)
        fun deserializeSmileBytes(typeRef: TypeReference<T>?): T {
            return smile.readValue(smileBytes, typeRef)
        }
    }

    companion object {
        protected val mapper: ObjectMapper = objectMapper()
        protected val smile: ObjectMapper = createSmileMapper()
        fun registerModule(c: Consumer<ObjectMapper?>) {
            c.accept(mapper)
            c.accept(smile)
        }

        protected fun createSmileMapper(): ObjectMapper {
            val smileMapper = ObjectMapper(SmileFactory())
            smileMapper.registerModule(Jdk8Module())
            smileMapper.registerModule(GuavaModule())
            smileMapper.registerModule(JavaTimeModule())

            smileMapper.registerModule(KotlinModule.Builder().build())
            smileMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
            smileMapper.disable(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE)
            return smileMapper
        }
        fun objectMapper() :ObjectMapper {
            val mapper = ObjectMapper()
            mapper.registerModule(Jdk8Module())
            mapper.registerModule(JavaTimeModule())
            mapper.registerModule(GuavaModule())


            mapper.registerModule(KotlinModule.Builder().build())
            mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
            mapper.disable(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE)
            return mapper
        }
    }
}
