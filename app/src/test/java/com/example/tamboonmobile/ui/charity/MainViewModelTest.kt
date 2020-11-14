package com.example.tamboonmobile.ui.charity

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.tamboonmobile.components.eventBus.EventIdentifier
import com.example.tamboonmobile.model.Charity
import com.example.tamboonmobile.network.CharityResponse
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

class MainViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private fun sleep() {
        Thread.sleep(500)
    }

    @Test
    fun initTest() {
        val repository: TamboonRepository = mockk()
        val viewModel = MainViewModel(repository)

        assertFalse(viewModel.noData.get())
        assertNull(viewModel.charityList.value)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun when_getCharity_expect_success() {
        val repository: TamboonRepository = mockk()
        val charityResponse: CharityResponse = mockk()
        val mockList: List<Charity> = mockk()
        every { mockList.size } returns 5
        every { charityResponse.statusCode } returns HttpsURLConnection.HTTP_OK
        every { charityResponse.data } returns mockList

        runBlockingTest {
            coEvery { repository.getCharityList() } returns charityResponse
            val viewModel = MainViewModel(repository)
            viewModel.getCharity()
            assertEquals(viewModel.loadingMsg.value, "Fetching List..")
            sleep()
            assertEquals(viewModel.loadingMsg.value, null)
            assertNotNull(viewModel.charityList.value)
            assertEquals(viewModel.charityList.value!!.size, 5)
        }
    }

    @ExperimentalCoroutinesApi
    @Test
    fun when_getCharity_expect_failure() {
        val repository: TamboonRepository = mockk()
        val charityResponse: CharityResponse = mockk()
        every { charityResponse.statusCode } returns HttpsURLConnection.HTTP_INTERNAL_ERROR
        every { charityResponse.errorMsg } returns "test_error"
        runBlockingTest {
            coEvery { repository.getCharityList() } returns charityResponse
            val viewModel = MainViewModel(repository)
            viewModel.getCharity()
            assertEquals(viewModel.loadingMsg.value, "Fetching List..")
            sleep()
            assertEquals(viewModel.loadingMsg.value, null)
            assertEquals(viewModel.errorLiveData.value, "test_error")
            assertTrue(viewModel.noData.get())
        }
    }

    @Test
    fun retryTest() {
        val repository: TamboonRepository = mockk()
        var retry = false
        val viewModel = MainViewModel(repository)
        viewModel.onEventReceived += {event->
            when(event.type) {
                EventIdentifier.RETRY -> retry = true
                else -> {}
            }
        }
        viewModel.retry()
        assertTrue(retry)
    }
}