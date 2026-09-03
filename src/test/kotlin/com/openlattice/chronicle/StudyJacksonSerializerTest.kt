package com.openlattice.chronicle

import com.openlattice.chronicle.study.Study
import org.apache.commons.lang3.RandomStringUtils

/**
 *
 * @author Matthew Tamayo-Rios &lt;matthew@getmethodic.com&gt;
 */
class StudyJacksonSerializerTest : AbstractJacksonSerializationTest<Study>() {
    override val sampleData: Study
        get() = Study(title = "This is a test study.",
                      contact = "${RandomStringUtils.secure().nextAlphabetic(5)}@openlattice.com")
    override val clazz: Class<Study>?
        get() = Study::class.java
    override fun logResult(result: SerializationResult<Study>) {
        logger.info(result.jsonString)
    }

}
