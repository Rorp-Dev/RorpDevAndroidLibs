package com.rorp.rorpdevlibs.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import com.rorp.rorpdevlibs.network.NetworkCallback

/*
     ____                    _        _   _       _ _
    | __ )  __ _ ___  ___   / \   ___| |_(_)_   _(_) |_ _   _
    |  _ \ / _` / __|/ _ \ / _ \ / __| __| \ \ / / | __| | | |
    | |_) | (_| \__ \  __// ___ \ (__| |_| |\ V /| | |_| |_| |
    |____/ \__,_|___/\___/_/   \_\___|\__|_| \_/ |_|\__|\__, |
                                                        |___/
 */
/**
 * @author Matt Dev
 * @since 2021.02.05
 */
abstract class BaseActivity<VM : BaseViewModel, DB : ViewDataBinding>(protected val mViewModelClass: Class<VM>): AppCompatActivity() {

    val mBaseBinding by lazy {
        DataBindingUtil.setContentView(this, setLayoutRes()) as DB
    }

    val mBaseViewModel by lazy{
        ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(BaseViewModel::class.java)
    }

    private lateinit var networkCallback: NetworkCallback

    /**
     * return layoutId (eg: return R.layout.activity_main)
     */
    @LayoutRes
    abstract fun setLayoutRes() : Int

    /**
     *  Must set viewModel to mBaseBinding: mBaseBinding.viewModel = mViewModel
     *
     *  eg: in layout
     *  <data>
            <variable name="viewModel" type="com.example.kotlinlab.ui.more.MoreViewModel" />
        </data>
     *
     */
    abstract fun setViewModel(viewModel : VM)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // observe network connection
        networkCallback = NetworkCallback(this)
        networkCallback.observeNetworkConnection(object : NetworkCallback.NetworkObservation{
            override fun isActive(isOnline: Boolean) {
                if(isOnline){
                    InternetSnackbar.internet(this@BaseActivity, mBaseBinding.root, "Back to online", InternetSnackbar.ONLINE)
                }else{
                    InternetSnackbar.internet(this@BaseActivity, mBaseBinding.root, "No internet", InternetSnackbar.OFFLINE)
                }
            }
        })
    }

    override fun onResume() {
        super.onResume()
        // register network callback
        networkCallback.registerNetworkCallback()
    }

    override fun onPause() {
        // register network callback
        networkCallback.registerNetworkCallback()

        super.onPause()
    }

    override fun onDestroy() {
        // unregister network callback
        networkCallback.unregisterNetworkCallback()

        super.onDestroy()
    }
}