package eu.europa.ec.eudi.verifier.core

import android.content.Context
import android.util.Log
import java.io.InputStream
import java.security.cert.CertificateFactory
import java.security.cert.X509Certificate

object CertificateProvider {

    /**
     * Loads X.509 certificates from the raw resources of the consuming application.
     *
     * @param context The application context.
     * @param certificateRawIds A list of raw resource IDs from the app's R file.
     * @return A list of X509Certificate objects.
     */
    fun loadCertificates(
        context: Context,
        certificateRawIds: List<Int>
    ): List<X509Certificate> {
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
                    Log.e("CertificateProvider", "Error loading certificate for R.raw ID $rawId: ${e.message}")
                }
            }
        } catch (e: Exception) {
            Log.e("CertificateProvider", "Error getting CertificateFactory: ${e.message}")
        }

        return certificates
    }
}