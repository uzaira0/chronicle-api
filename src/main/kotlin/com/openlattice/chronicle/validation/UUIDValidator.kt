/*
 * Copyright (C) 2024. Chronicle.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.openlattice.chronicle.validation

import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext
import java.util.UUID

/**
 * Validator implementation for @ValidUUID annotation.
 * Validates that a string value is a properly formatted UUID.
 *
 * @author uzaira0
 */
public class UUIDValidator : ConstraintValidator<ValidUUID, String> {

    override fun initialize(constraintAnnotation: ValidUUID) {
        // No initialization needed
    }

    override fun isValid(value: String?, context: ConstraintValidatorContext): Boolean {
        if (value == null) {
            return true // Use @NotNull for null checks
        }

        return try {
            UUID.fromString(value)
            true
        } catch (ignore: IllegalArgumentException) {
            // A non-parseable value is simply invalid; the exception carries no extra signal.
            false
        }
    }
}
