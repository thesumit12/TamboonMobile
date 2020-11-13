package com.example.tamboonmobile.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.tamboonmobile.components.BaseViewModel
import com.example.tamboonmobile.model.Charity
import com.example.tamboonmobile.repository.TamboonRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(private val repository: TamboonRepository): BaseViewModel() {
    val loadingMsg = MutableLiveData("")

    fun getCharity(): LiveData<List<Charity>> {
        loadingMsg.value = "Fetching List.."
        val charityLiveData = MutableLiveData<List<Charity>>()
        CoroutineScope(Dispatchers.IO).launch {
            charityLiveData.postValue(repository.getCharityList())
        }

        return charityLiveData
    }
}