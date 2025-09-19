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

package eu.europa.ec.eudi.verifier.core.transfer

import android.content.Context
import eu.europa.ec.eudi.verifier.core.logging.Logger

/**
 * Factory interface for creating instances of [TransferManager].
 * Provides methods to create transfer managers with different configuration approaches.
 */
interface TransferManagerFactory {

    /**
     * Creates a new [TransferManager] instance with the specified configuration.
     *
     * @param config The pre-configured [TransferConfig] to use.
     * @return A new instance of [TransferManager].
     */
    fun createTransferManager(config: TransferConfig): TransferManager

    /**
     * Creates a new [TransferManager] instance using a configuration lambda.
     *
     * @param configure Lambda with receiver for configuring the [TransferConfig.Builder].
     * @return A new instance of [TransferManager].
     */
    fun createTransferManager(configure: TransferConfig.Builder.() -> Unit): TransferManager
}

/**
 * Implementation of [TransferManagerFactory] that creates [TransferManagerImpl] instances.
 *
 * @property context The Android application context.
 */
class TransferManagerFactoryImpl(context: Context, private val logger: Logger) : TransferManagerFactory {
    private val context = context.applicationContext

    /**
     * Creates a new [TransferManager] instance with the specified configuration.
     *
     * @param config The pre-configured [TransferConfig] to use.
     * @return A new instance of [TransferManagerImpl].
     */
    override fun createTransferManager(config: TransferConfig): TransferManager {
        return TransferManagerImpl(context, config, logger)
    }

    /**
     * Creates a new [TransferManager] instance using a configuration lambda.
     *
     * @param configure Lambda with receiver for configuring the [TransferConfig.Builder].
     * @return A new instance of [TransferManagerImpl].
     */
    override fun createTransferManager(configure: TransferConfig.Builder.() -> Unit): TransferManager {
        return TransferManagerImpl(context, TransferConfig(configure), logger)
    }
}