package com.example.tamboonmobile.ui.charity

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.MutableLiveData
import com.example.tamboonmobile.components.BaseViewModel
import com.example.tamboonmobile.components.eventBus.EventIdentifier
import com.example.tamboonmobile.model.Charity
import com.example.tamboonmobile.repository.TamboonRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.net.ssl.HttpsURLConnection

class MainViewModel(private val repository: TamboonRepository): BaseViewModel() {

    val noData = ObservableBoolean(false)
    val charityList = MutableLiveData<List<Charity>>()

    fun getCharity() {
        loadingMsg.value = "Fetching List.."
        CoroutineScope(Dispatchers.IO).launch {
            val res = repository.getCharityList()
            if (res.statusCode == HttpsURLConnection.HTTP_OK) {
                charityList.postValue(res.data)
                loadingMsg.postValue(null)
            }else {
                errorLiveData.postValue(res.errorMsg)
                loadingMsg.postValue(null)
                noData.set(true)
            }
        }

    }

    fun retry() {
        triggerEvent(EventIdentifier.RETRY)
    }
}