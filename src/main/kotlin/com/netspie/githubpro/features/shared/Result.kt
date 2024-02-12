package com.netspie.githubpro.features.shared

import com.netspie.githubpro.features.shared.Message.Companion.containErrors

open class Result(
    open val messages: MutableList<Message> = mutableListOf()) {

    open val isSuccess: Boolean
        get() = !messages.containErrors()

    operator fun plus(other: Result): Result =
        Result((this.messages + other.messages).toMutableList())

    fun fail(message: String): Boolean =
        messages.add(Message(MessageLevel.error, message))

    fun add(other: Result): Boolean =
        messages.addAll(other.messages)
}

class ResultT<T>(
    val value: T?) : Result() {

    override val isSuccess: Boolean
        get() = value != null && super.isSuccess

    fun with(other: Result): ResultT<T> {
        messages.addAll(other.messages)
        return this
    }
}

fun resultAction(f: (result: Result) -> Unit): Result {
    var result = Result()
    f(result)
    return result
}

inline fun<reified T> resultActionOfT(f: (result: Result) -> T?): ResultT<T> {
    var result = Result()
    return ResultT(f(result)).with(result)
}

class ResultOf<T>(messages: MutableList<Message>) : Result(messages) {}

fun failure(message: String) = Result(mutableListOf(Message(MessageLevel.error, message)))
fun success() = Result()

fun Result.addTo(other: Result): Result {
    other.add(this)
    return this
}

data class Message(val level: MessageLevel, val content: String, val subMessages: List<Message> = emptyList()) {
    companion object {
        fun Iterable<Message>.containErrors(): Boolean =
            this.any{
                it.level == MessageLevel.error || it.subMessages.containErrors()
            }
    }
}

data class MessageLevel(val name: String) {
    companion object {
        val info: MessageLevel = MessageLevel("info")
        val warning: MessageLevel = MessageLevel("warning")
        val error: MessageLevel = MessageLevel("error")
    }
}

