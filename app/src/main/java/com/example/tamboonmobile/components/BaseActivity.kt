package com.example.tamboonmobile.components

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.example.tamboonmobile.R
import com.example.tamboonmobile.components.eventBus.EventIdentifier
import org.koin.standalone.KoinComponent

abstract class BaseActivity<T: ViewDataBinding, V: BaseViewModel>: AppCompatActivity(), KoinComponent {

    private lateinit var mViewDataBinding: T
    private var mViewModel: V? = null

    private val waitingDialogHelper = WaitingDialogHelper()

    @LayoutRes
    abstract fun getLayoutId(): Int

    abstract fun getViewModel(): V

    abstract fun getBindingVariable(): Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        performDataBinding()

        mViewModel!!.errorLiveData.observe(this, {
            if (it.isNotEmpty()) {
                AlertDialogUtil.showErrorDialog(this, getString(R.string.error), it) { dialog, _ ->
                    dialog.dismiss()
                    mViewModel!!.triggerEvent(EventIdentifier.ERROR_DISMISSED)
                }
            }
        })
    }

    fun getViewDataBinding(): T = mViewDataBinding

    private fun performDataBinding() {
        mViewDataBinding = DataBindingUtil.setContentView(this, getLayoutId())
        this.mViewModel = if (mViewModel == null) getViewModel() else mViewModel
        mViewDataBinding.setVariable(getBindingVariable(), mViewModel)
        mViewDataBinding.executePendingBindings()
    }

    fun showWaitingDialog(msg: String) = waitingDialogHelper.show(this, msg)

    fun hideWaitingDialog() = waitingDialogHelper.hideDialog()

}