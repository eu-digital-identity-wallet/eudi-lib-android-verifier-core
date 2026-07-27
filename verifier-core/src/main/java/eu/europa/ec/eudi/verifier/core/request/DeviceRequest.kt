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
package eu.europa.ec.eudi.verifier.core.request

import eu.europa.ec.eudi.verifier.core.DataElementIdentifier
import eu.europa.ec.eudi.verifier.core.DocType
import eu.europa.ec.eudi.verifier.core.Namespace
import org.multipaz.securearea.KeyUnlockData
import org.multipaz.securearea.SecureArea
import java.security.cert.X509Certificate

/**
 * Represents a generic request in the verifier core system.
 * Used as a marker interface for different types of requests.
 */
interface Request

/**
 * Represents a request sent to a device, containing a list of document requests.
 *
 * @param docRequests The list of document requests to be processed by the device.
 * @param readerAuth The reader authentication material used to sign the request, or null to send an
 * unsigned request.
 * @param registrationCertificate The serialized relying party registration certificate (WRPRC) for
 * the request, or null when the relying party has none.
 */
class DeviceRequest(
    val docRequests: List<DocRequest>,
    val readerAuth: ReaderAuth? = null,
    val registrationCertificate: ByteArray? = null,
) : Request

/**
 * Reader authentication material used to sign an ISO/IEC 18013-5 request, authenticating the relying
 * party with its access certificate.
 *
 * The request is signed by [secureArea] using the key stored under [keyAlias]; the private key never
 * leaves the secure area. [certificateChain] is carried in the `readerAuth` structure so the holder
 * can establish trust in the reader. The chain is ordered with the access certificate first; its
 * trust anchor is not included.
 *
 * @property secureArea The secure area that holds the reader key and signs the request.
 * @property keyAlias The alias of the reader key within [secureArea].
 * @property certificateChain The access certificate chain, access certificate first, trust anchor
 * excluded.
 * @property keyUnlockData Data to unlock the key in [secureArea] when it is protected, or null.
 */
data class ReaderAuth(
    val secureArea: SecureArea,
    val keyAlias: String,
    val certificateChain: List<X509Certificate>,
    val keyUnlockData: KeyUnlockData? = null,
)

/**
 * Represents a request for a specific document type and which data elements to request.
 *
 * @property docType The type of document being requested.
 * @property itemsRequest The map of namespaces to data element identifiers and their intent to retain.
 */
data class DocRequest(
    val docType: DocType,
    var itemsRequest: ItemsRequest,
)

/**
 * The `requestInfo` member of an ISO/IEC 18013-5 `ItemsRequest` that carries the relying party
 * registration certificate (WRPRC), as defined in ETSI TS 119 472-2 clause 5.3.2.
 */
const val EU_WRPRC_REQUEST_INFO_KEY: String = "euWrprc"

/**
 * Type alias for the items request map structure.
 */
typealias ItemsRequest = Map<Namespace, Map<DataElementIdentifier, IntentToRetain>>

/**
 * Type alias for the intent to retain boolean value.
 */
typealias IntentToRetain = Boolean
