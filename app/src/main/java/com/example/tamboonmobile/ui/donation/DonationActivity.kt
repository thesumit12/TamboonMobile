package com.example.tamboonmobile.ui.donation

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContract
import co.omise.android.models.Token
import co.omise.android.ui.CreditCardActivity
import co.omise.android.ui.OmiseActivity
import com.example.tamboonmobile.BR
import com.example.tamboonmobile.R
import com.example.tamboonmobile.components.BaseActivity
import com.example.tamboonmobile.components.eventBus.EventIdentifier
import com.example.tamboonmobile.databinding.ActivityDonationBinding
import com.example.tamboonmobile.ui.SuccessActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class DonationActivity : BaseActivity<ActivityDonationBinding, DonationViewModel>() {

    private val mViewModel: DonationViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        subscribeObservers()
        supportActionBar?.title = intent?.extras?.getString(EXTRA_TITLE)
    }

    private fun subscribeObservers() {
        mViewModel.onEventReceived += {event->
            when(event.type) {
                EventIdentifier.MAKE_DONATION -> showCreditCardForm()
                EventIdentifier.DONATION_SUCCESS -> navigateToSuccessScreen()
                EventIdentifier.ERROR_DISMISSED -> this@DonationActivity.finish()
                else -> {}
            }
        }

        mViewModel.loadingMsg.observe(this, {msg->
            if (msg.isNullOrEmpty()) {
                hideWaitingDialog()
            }else {
                showWaitingDialog(msg)
            }
        })
    }

    private fun showCreditCardForm() {
        val token = registerForActivityResult(CreditCardActivityContract()) {
            if (it != null) {
                mViewModel.makeDonation(it.id!!, it.card!!.name!!)
            }

        }
        token.launch(OMISE_PKEY)
    }

    private fun navigateToSuccessScreen() {
        val intent = Intent(this, SuccessActivity::class.java)
        startActivity(intent)
    }

    override fun getLayoutId(): Int = R.layout.activity_donation

    override fun getViewModel(): DonationViewModel = mViewModel

    override fun getBindingVariable(): Int = BR.viewModel

    inner class CreditCardActivityContract: ActivityResultContract<String, Token?>() {
        override fun createIntent(context: Context, input: String?): Intent {
            return Intent(context, CreditCardActivity::class.java).apply {
                putExtra(OmiseActivity.EXTRA_PKEY, input)
            }
        }

        override fun parseResult(resultCode: Int, intent: Intent?): Token? {
            return if (resultCode == Activity.RESULT_OK) intent?.getParcelableExtra(OmiseActivity.EXTRA_TOKEN_OBJECT)
            else null
        }

    }

    companion object {
        private val OMISE_PKEY: String = "pkey_test_5lvcdsk63lo6lvpopxw"
        private const val EXTRA_TITLE = "EXTRA_TITLE"

        fun newIntent(context: Context, title: String?): Intent {
            return Intent(context, DonationActivity::class.java).apply {
                putExtra(EXTRA_TITLE, title)
            }
        }
    }
}