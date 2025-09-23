package eu.europa.ec.eudi.verifier.core

import android.content.Context
import eu.europa.ec.eudi.verifier.core.logging.Logger
import eu.europa.ec.eudi.verifier.core.logging.e
import java.io.InputStream
import java.security.cert.CertificateFactory
import java.security.cert.X509Certificate

/**
 * Functional interface for providing X.509 certificates used in EUDI verification processes.
 *
 * This interface defines a contract for retrieving certificates that can be used for
 * document signature verification, trust chain validation, or other cryptographic operations
 * within the EUDI verifier framework.
 *
 * Implementations should handle certificate loading asynchronously and provide appropriate
 * error handling for scenarios where certificates cannot be loaded.
 */
fun interface CertificatesProvider {
    /**
     * Retrieves a list of X.509 certificates.
     *
     * This method is called to obtain certificates that will be used for verification
     * operations. The implementation should load certificates from the configured source
     * and return them as a list.
     *
     * @return List of [X509Certificate] objects. Returns an empty list if no certificates
     *         are available or if an error occurs during loading.
     */
    suspend fun getCertificates(): List<X509Certificate>

    companion object {
        /**
         * A default [CertificatesProvider] implementation that returns no certificates.
         *
         * This can be used as a fallback or placeholder when no certificates are needed
         * or available for verification operations.
         */
        val NoCertificates = CertificatesProvider { emptyList() }

        /**
         * Creates a [CertificatesProvider] that loads certificates from Android raw resources.
         *
         * This factory method creates a provider that loads X.509 certificates from the
         * application's raw resources directory. The certificates should be stored as
         * raw resource files and referenced by their resource IDs.
         *
         * @param context The Android context used to access raw resources
         * @param certificateRawIds List of raw resource IDs pointing to certificate files.
         *                         Each ID should reference a valid X.509 certificate file
         *                         in DER or PEM format.
         * @param logger Optional logger for recording certificate loading operations and errors.
         *               If null, no logging will be performed.
         * @return A [CertificatesProvider] that loads certificates from the specified raw resources
         *
         * @see CertificatesResourcesProvider
         */
        fun fromRawResources(
            context: Context,
            certificateRawIds: List<Int>,
            logger: Logger? = null,
        ): CertificatesProvider {
            return CertificatesResourcesProvider(context, certificateRawIds, logger)
        }
    }
}

/**
 * Implementation of [CertificatesProvider] that loads X.509 certificates from Android raw resources.
 *
 * This class provides a concrete implementation for loading certificates stored as raw resources
 * within an Android application. It handles the certificate loading process, including error
 * handling and logging of any issues that occur during the loading process.
 *
 * The provider uses the Android [CertificateFactory] to parse certificate files and converts
 * them to [X509Certificate] objects. If any certificate fails to load, the error is logged
 * (if a logger is provided) and the provider continues with the remaining certificates.
 *
 * @param context The Android context used to access raw resources
 * @param certificateRawIds List of raw resource IDs pointing to certificate files
 * @param logger Optional logger for recording certificate loading operations and errors
 *
 * @see CertificatesProvider
 */
class CertificatesResourcesProvider(
    private val context: Context,
    private val certificateRawIds: List<Int>,
    private val logger: Logger? = null,
) : CertificatesProvider {

    /**
     * Loads and returns X.509 certificates from the configured raw resources.
     *
     * This method iterates through all configured raw resource IDs and attempts to load
     * each certificate file. The loading process:
     *
     * 1. Creates an X.509 certificate factory
     * 2. Opens each raw resource as an input stream
     * 3. Parses the certificate data using the certificate factory
     * 4. Adds successfully loaded certificates to the result list
     * 5. Logs any errors that occur during loading (if logger is provided)
     *
     * If the certificate factory cannot be created or any individual certificate fails
     * to load, the error is logged and the method continues with remaining certificates.
     *
     * @return List of successfully loaded [X509Certificate] objects. May be empty if
     *         no certificates could be loaded or if an error occurred.
     */
    override suspend fun getCertificates(): List<X509Certificate> {
        val certificates = mutableListOf<X509Certificate>()

        try {
            val factory = CertificateFactory.getInstance("X.509")

            certificateRawIds.forEach { rawId ->
                try {
                    val inputStream: InputStream = context.resources.openRawResource(rawId)
                    inputStream.use { stream ->
                        val certificate = factory.generateCertificate(stream) as? X509Certificate
                        certificate?.let { certificates.add(it) }
                    }
                } catch (e: Exception) {
                    logger?.e(
                        TAG, "Error loading certificate for R.raw ID $rawId: ${e.message}"
                    )
                }
            }
        } catch (e: Exception) {
            logger?.e(TAG, "Error getting CertificateFactory: ${e.message}")
        }

        return certificates
    }

    companion object {
        /** Tag used for logging certificate loading operations and errors */
        private const val TAG = "CertificateProvider"
    }
}