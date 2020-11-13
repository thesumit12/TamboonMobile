package com.example.tamboonmobile.components.eventBus

class Event<T> {

    private val handlers = arrayListOf<(Event<T>.(T) -> Unit)>()

    operator fun plusAssign(handler: Event<T>.(T) -> Unit) {
        handlers.add(handler)
    }

    operator fun minusAssign(handler: Event<T>.(T) -> Unit) {
        handlers.remove(handler)
    }

    operator fun invoke(value: T) {
        for (handler in handlers) handler(value)
    }

    fun removeAllhandlers() {
        handlers.clear()
    }
}