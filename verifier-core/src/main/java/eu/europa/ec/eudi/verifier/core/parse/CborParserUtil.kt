/*
 * Copyright (c) 2025 European Commission
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
 */

package eu.europa.ec.eudi.verifier.core.parse

import com.upokecenter.cbor.CBORObject
import com.upokecenter.cbor.CBORType

/**
 * A utility object for parsing raw CBOR byte arrays into native Kotlin types.
 */
object CborParserUtil {

    /**
     * The main parsing function. Decodes a ByteArray into a native Kotlin type.
     */
    fun parse(bytes: ByteArray): Any? {
        return CBORObject.DecodeFromBytes(bytes).toObject()
    }

    /**
     * Extension function that recursively parses a CBORObject into a native Kotlin type.
     */
    private fun CBORObject.toObject(): Any? = when {
        isNull -> null
        isTrue -> true
        isFalse -> false

        // Handles dates by checking for standard CBOR date tags
        HasMostOuterTag(0) || HasMostOuterTag(1004) -> AsString()

        else -> when (type) {
            CBORType.Integer -> {
                when {
                    CanValueFitInInt32() -> AsInt32()
                    CanValueFitInInt64() -> AsNumber().ToInt64Checked()
                    else -> AsDouble()
                }
            }
            CBORType.FloatingPoint -> AsDouble()
            CBORType.ByteString -> when {
                // Handles embedded CBOR data within a byte string
                HasMostOuterTag(24) -> GetByteString().let { CBORObject.DecodeFromBytes(it).toObject() }
                // Return other byte strings as is
                else -> GetByteString()
            }
            CBORType.TextString -> AsString()
            CBORType.Array -> values.map { it.toObject() }.toList()
            CBORType.Map -> keys.associate { it.toObject() to this[it].toObject() }
            else -> null
        }
    }
}