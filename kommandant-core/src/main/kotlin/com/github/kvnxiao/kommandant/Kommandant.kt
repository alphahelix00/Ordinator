package com.github.kvnxiao.kommandant

import com.github.kvnxiao.kommandant.command.CommandContext
import com.github.kvnxiao.kommandant.command.CommandResult
import com.github.kvnxiao.kommandant.command.ICommand
import com.github.kvnxiao.kommandant.command.Success
import com.github.kvnxiao.kommandant.impl.CommandBank
import com.github.kvnxiao.kommandant.impl.CommandExecutor
import com.github.kvnxiao.kommandant.impl.CommandParser
import com.github.kvnxiao.kommandant.utility.ImmutableCommandMap
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.lang.reflect.InvocationTargetException
import kotlin.reflect.KClass

/**
 * Created by kvnxiao on 3/3/17.
 */
open class Kommandant(protected val cmdBank: ICommandBank = CommandBank(),
                      protected val cmdExecutor: ICommandExecutor = CommandExecutor(),
                      protected val cmdParser: ICommandParser = CommandParser()) {

    companion object {
        @JvmField
        val LOGGER: Logger = LoggerFactory.getLogger(Kommandant::class.java)
    }

    open fun <T> process(input: String, vararg opt: Any?): CommandResult<T> {
        val context = CommandContext(input)
        if (context.hasAlias()) {
            val command: ICommand<*>? = this.getCommand(context.alias)
            if (command !== null) return processCommand(command, context, opt)
        }
        return CommandResult(false)
    }

    open protected fun <T> processCommand(command: ICommand<*>, context: CommandContext, vararg opt: Any?): CommandResult<T> {
        val success = Success()
        val result: T? = cmdExecutor.execute(command, context, success, opt)
        return CommandResult(success.success, result)
    }

    /* * *
    *
    *   Wrapper functions for the command bank, parser, and executor
    *
    * * */

    fun clearAll() = cmdBank.clearAll()

    fun addCommand(command: ICommand<*>): Boolean = cmdBank.addCommand(command)

    fun getCommand(prefixedAlias: String): ICommand<*>? = cmdBank.getCommand(prefixedAlias)

    fun deleteCommand(command: ICommand<*>?): Boolean = if (command !== null) cmdBank.deleteCommand(command) else false

    fun changePrefix(command: ICommand<*>?, newPrefix: String): Boolean = if (command !== null) cmdBank.changePrefix(command, newPrefix) else false

    fun getPrefixes(): Set<String> = cmdBank.getPrefixes()

    fun getCommandsForPrefix(prefix: String): ImmutableCommandMap = cmdBank.getCommandsForPrefix(prefix)

    fun getAllCommands(): ImmutableCommandMap = cmdBank.getCommands()

    fun getCommandsUnique(): List<ICommand<*>> = cmdBank.getCommandsUnique()

    fun addAnnotatedCommands(clazz: Class<*>) {
        try {
            cmdParser.parseAnnotations(clazz, this.cmdBank)
        } catch (e: InvocationTargetException) {
            LOGGER.error("'${e.localizedMessage}': Could not instantiate an object instance of class '${clazz.name}'!")
        } catch (e: IllegalAccessException) {
            LOGGER.error("'${e.localizedMessage}': Failed to access method definition in class '${clazz.name}'!")
        }
    }

    fun addAnnotatedCommands(ktClazz: KClass<*>) = this.addAnnotatedCommands(ktClazz.java)

}