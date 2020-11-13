package com.example.tamboonmobile.ui

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tamboonmobile.BR
import com.example.tamboonmobile.R
import com.example.tamboonmobile.components.BaseActivity
import com.example.tamboonmobile.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>() {

    private val mainViewModel: MainViewModel by viewModel()
    private lateinit var mAdapter: CharityAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.title = "Charity List"
        initializeRecyclerView()
        subscribeObservers()
    }

    private fun initializeRecyclerView() {
        mAdapter = CharityAdapter(this)
        rvCharityList.layoutManager = LinearLayoutManager(this)
        rvCharityList.adapter = mAdapter
        rvCharityList.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))
    }

    private fun subscribeObservers() {
        mainViewModel.loadingMsg.observe(this, { msg->
            if (msg != null) {
                showWaitingDialog(msg)
            }else {
                hideWaitingDialog()
            }
        })

        mainViewModel.getCharity().observe(this, {
            mainViewModel.loadingMsg.value = null
            mAdapter.setList(it)
        })

    }

    override fun getLayoutId(): Int = R.layout.activity_main

    override fun getViewModel(): MainViewModel = mainViewModel

    override fun getBindingVariable(): Int = BR.viewModel
}