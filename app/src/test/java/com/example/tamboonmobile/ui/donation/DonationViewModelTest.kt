package com.example.tamboonmobile.ui.donation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.tamboonmobile.components.eventBus.EventIdentifier
import com.example.tamboonmobile.network.DonationResponse
import com.example.tamboonmobile.repository.TamboonRepository
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import javax.net.ssl.HttpsURLConnection

class DonationViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private fun sleep() {
        Thread.sleep(500)
    }

    @Test
    fun initTest() {
        val repository: TamboonRepository = mockk()
        val viewModel = DonationViewModel(repository)

        assertNull(viewModel.donationAmount.get())
    }

    @Test
    fun makeDonationClickTest() {
        val repository: TamboonRepository = mockk()
        val viewModel = DonationViewModel(repository)
        var donation = false

        viewModel.onEventReceived += {event->
            when(event.type) {
                EventIdentifier.MAKE_DONATION -> donation = true
                else -> {}
            }
        }
        viewModel.makeDonationClicked()
        assertTrue(donation)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun when_makeDonation_expect_success() {
        var donationSuccess = false
        val repository: TamboonRepository = mockk()
        val viewModel = DonationViewModel(repository)
        val donationResponse: DonationResponse = mockk()
        every { donationResponse.statusCode } returns HttpsURLConnection.HTTP_OK
        runBlockingTest {
            coEvery { repository.makeDonation(any()) } returns donationResponse
            viewModel.onEventReceived += {event->
                when(event.type) {
                    EventIdentifier.DONATION_SUCCESS -> donationSuccess = true
                    else -> {}
                }
            }
            viewModel.donationAmount.set("100")
            viewModel.makeDonation("test", "testToken")
            assertEquals(viewModel.loadingMsg.value, "Making Donation..")
            sleep()
            assertEquals(viewModel.loadingMsg.value, null)
            assertTrue(donationSuccess)
        }
    }
}