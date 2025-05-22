package com.task.newsfeedapp.base

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.task.newsfeedapp.R
import com.task.newsfeedapp.base.component.ActivityComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import javax.inject.Inject

abstract class BaseActivity<VIEWMODEL:BaseViewModel> : AppCompatActivity() {

    private val jobMain: Job = Job()
    val scopeMain = CoroutineScope(jobMain + Dispatchers.Main + Dispatchers.IO)

    @Inject
    lateinit var viewModel: VIEWMODEL


    @SuppressLint("HardwareIds")
    override fun onCreate(savedInstanceState: Bundle?) {
        injectDependencies(buildActivityComponent())
        super.onCreate(savedInstanceState)
        setContentView(provideLayoutId())
        setupObservers()
        setupView(savedInstanceState)
        viewModel.onCreate()
    }


    protected open fun setupObservers() {


    }

    private fun buildActivityComponent() = DaggerActivityComponent.builder()
        .applicationComponent((application as BaseApplication).applicationComponent)
//         .activityModule(ActivityModule(this))
        .build()



    @LayoutRes
    protected abstract fun provideLayoutId(): Int

    protected abstract fun injectDependencies(activityComponent: ActivityComponent)

    protected abstract fun setupView(savedInstanceState: Bundle?)

    protected open fun onNetworkChanged(isConnected: Boolean) {
        // method is empty
    }

    override fun finish() {
        super.finish()

    }

    fun Activity.hideKeyboard() {
        hideKeyboard(currentFocus ?: View(this))
    }

    private fun hideKeyboard(view: View) {
        val inputMethodManager =
            getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }
    protected open fun onTtsStop() {
        // method is empty
    }
    override fun onDestroy() {
        super.onDestroy()
        scopeMain.cancel()
        jobMain.cancel()

    }

    override fun onResume() {
        super.onResume()

    }

    override fun onPause() {
        super.onPause()

    }

    private fun supportFragmentBaseManager(): FragmentManager {
        return supportFragmentManager
    }

    fun getCurrentFragment(): Fragment? {
        return supportFragmentBaseManager().findFragmentByTag(resources.getString(R.string.my_dashboard))
    }
}