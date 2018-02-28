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
 *   See the License for the specific language governing commandSettings and
 *   limitations under the License.
 */
package com.github.kvnxiao.kommandant.command.annotations

import com.github.kvnxiao.kommandant.command.CommandDefaults

/**
 * Runtime annotation to describe annotated commands. This includes the command's unique id, aliases, and optionally the
 * parent command's unique id as well.
 */
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FUNCTION)
annotation class Command(
    /**
     * The unique id of the command.
     */
    val id: String,
    /**
     * The set of aliases used for calling the command.
     */
    val aliases: Array<String>,
    /**
     * The unique id of the parent command. This is optional as it defaults to an empty string to signify that this
     * command has no parent.
     */
    val parentId: String = CommandDefaults.PARENT_ID
)