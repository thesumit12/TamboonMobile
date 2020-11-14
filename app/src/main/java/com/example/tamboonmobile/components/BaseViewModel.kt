package com.example.tamboonmobile.components

import androidx.databinding.Observable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tamboonmobile.components.eventBus.Event
import com.example.tamboonmobile.components.eventBus.EventIdentifier
import com.example.tamboonmobile.components.eventBus.EventType

/**
 * BaseviewModel class for all viewModels to extend.
 * @author Sumit Lakra
 * @date 12/07/19
 */
open class BaseViewModel: ViewModel() {

    val onEventReceived: Event<EventType> = Event()
    val loadingMsg = MutableLiveData("")

    fun triggerEvent(type: EventIdentifier, dataObj: Any = "") {
        val eventType = EventType(type, dataObj)
        onEventReceived(eventType)
    }

    @Suppress("UNCHECKED_CAST")
    fun <T : Observable> T.addOnPropertyChanged(callback: (T) -> Unit) =
        object : Observable.OnPropertyChangedCallback() {
            @Suppress("UNCHECKED_CAST")
            override fun onPropertyChanged(observable: Observable?, i: Int) =
                callback(observable as T)
        }.also { addOnPropertyChangedCallback(it) }
}