package com.openlattice.chronicle.organizations

import com.openlattice.chronicle.ids.IdConstants
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size
import java.util.*

/**
 *
 * @author Matthew Tamayo-Rios &lt;matthew@openlattice.com&gt;
 */
public data class Organization(
    var id: UUID = IdConstants.UNINITIALIZED.id,

    @field:NotBlank(message = "Organization title is required")
    @field:Size(max = 255, message = "Organization title exceeds maximum length")
    var title: String,

    @field:Size(max = 4000, message = "Organization description exceeds maximum length")
    var description: String = "",

    var settings: Map<String, Any> = mutableMapOf()
)
