/*
 *   Copyright (C) 2017-2018 Ze Hao Xiao
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 */
package com.github.kvnxiao.kommandant.command.registry

import com.github.kvnxiao.kommandant.command.CommandDefaults

/**
 * Interface which defines the methods required to validate a command's information before allowing it to be added
 * into the registry.
 */
interface CommandRegistryValidator {
    /**
     * Ensure that before adding a new command, none of the prefix + alias combinations already exist in the registry
     */
    fun validateAliases(prefix: String = CommandDefaults.NO_PREFIX, aliases: Set<String>): Boolean

    /**
     * Ensure that before adding a new command, the unique identifier does not already exist in the registry
     */
    fun validateUniqueId(id: String): Boolean
}
