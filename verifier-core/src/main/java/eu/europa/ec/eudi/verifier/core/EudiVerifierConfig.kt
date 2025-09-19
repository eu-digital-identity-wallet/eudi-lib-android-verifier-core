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
package eu.europa.ec.eudi.verifier.core

import eu.europa.ec.eudi.verifier.core.logging.Logger

/**
 * Configuration for the EUDI verifier.
 */
class EudiVerifierConfig {

    @Logger.Level
    var logLevel: Int = Logger.LEVEL_INFO
        private set
    var logSizeLimit: Int = 1000
        private set

    /**
     * Configure the built-in logging. This allows to configure the log level and the log size limit.
     *
     * The default log level is set to [Logger.LEVEL_INFO] and the default log size limit is set to
     * 1000.
     *
     * @param level the log level
     * @param sizeLimit the log size limit
     * @return the [EudiVerifierConfig] instance
     */
    @JvmOverloads
    fun configureLogging(level: Int, sizeLimit: Int? = null) = apply {
        logLevel = level
        sizeLimit?.let { logSizeLimit = it }
    }

    companion object {

        /**
         * DSL entry point to configure and create an [EudiVerifierConfig] instance.
         *
         * @param configure Lambda with receiver to configure the [EudiVerifierConfig].
         * @return Configured [EudiVerifierConfig] instance.
         */
        operator fun invoke(configure: EudiVerifierConfig.() -> Unit): EudiVerifierConfig =
            EudiVerifierConfig().apply(configure)
    }
}