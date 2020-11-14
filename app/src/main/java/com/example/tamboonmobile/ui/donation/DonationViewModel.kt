package com.example.tamboonmobile.ui.donation

import androidx.databinding.ObservableField
import com.example.tamboonmobile.components.BaseViewModel
import com.example.tamboonmobile.components.eventBus.EventIdentifier
import com.example.tamboonmobile.model.Donation
import com.example.tamboonmobile.repository.TamboonRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.net.ssl.HttpsURLConnection

class DonationViewModel(private val repository: TamboonRepository): BaseViewModel() {

    val donationAmount = ObservableField<String>()

    fun makeDonationClicked() {
        triggerEvent(EventIdentifier.MAKE_DONATION)
    }

    fun makeDonation(token: String, name: String) {
        loadingMsg.value = "Making Donation.."
        val donation = Donation(name, token, donationAmount.get()!!.toInt())
        CoroutineScope(Dispatchers.IO).launch {
            val res = repository.makeDonation(donation)
                loadingMsg.postValue(null)
                if (res.statusCode == HttpsURLConnection.HTTP_OK) {
                    triggerEvent(EventIdentifier.DONATION_SUCCESS)
                }else {
                    errorLiveData.value = res.errorMsg
                    triggerEvent(EventIdentifier.DONATION_FAILURE)
                }
        }
    }
}