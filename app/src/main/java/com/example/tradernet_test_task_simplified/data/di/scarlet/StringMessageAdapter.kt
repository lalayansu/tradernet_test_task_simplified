package com.example.tradernet_test_task_simplified.data.di.scarlet

import com.tinder.scarlet.Message
import com.tinder.scarlet.MessageAdapter
import java.lang.reflect.Type

class StringMessageAdapter : MessageAdapter<String> {
    override fun fromMessage(message: Message): String = when (message) {
        is Message.Text -> message.value
        else -> throw IllegalArgumentException("Unsupported message type")
    }

    override fun toMessage(data: String): Message = Message.Text(data)
}

class StringMessageAdapterFactory : MessageAdapter.Factory {
    override fun create(type: Type, annotations: Array<Annotation>): MessageAdapter<*> =
        when (type) {
            String::class.java -> StringMessageAdapter()
            else -> throw IllegalArgumentException("Unsupported message type")
        }
}