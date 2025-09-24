package eu.europa.ec.eudi.verifier.core

import eu.europa.ec.eudi.verifier.core.logging.Logger
import kotlin.test.Test
import kotlin.test.assertEquals

class EudiVerifierConfigTest {

    @Test
    fun testDefaultValues() {
        val config = EudiVerifierConfig()
        assertEquals(Logger.LEVEL_INFO, config.logLevel)
        assertEquals(1000, config.logSizeLimit)
    }

    @Test
    fun testConfigureLogging() {
        val config = EudiVerifierConfig {
            configureLogging(Logger.LEVEL_ERROR, sizeLimit = 500)
        }

        assertEquals(Logger.LEVEL_ERROR, config.logLevel)
        assertEquals(500, config.logSizeLimit)

        // Test only with logLevel changed (sizeLimit null)
        config.configureLogging(Logger.OFF)
        assertEquals(Logger.OFF, config.logLevel)
        assertEquals(500, config.logSizeLimit) // Should remain unchanged
    }
}