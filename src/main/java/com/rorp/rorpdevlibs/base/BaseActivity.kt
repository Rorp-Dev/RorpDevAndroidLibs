package com.rorp.rorpdevlibs.base

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.MutableLiveData
import com.rorp.rorpdevlibs.biometric.BiometricAuthenticator
import com.rorp.rorpdevlibs.network.connection.NetworkCallback
import com.rorp.rorpdevlibs.util.extensions.TransitionAnimation
import com.rorp.rorpdevlibs.util.extensions.setCustomAnimation


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
abstract class BaseActivity<V : BaseViewModel, B : ViewDataBinding>: AppCompatActivity(), BaseViewGroup<V, B>, NavigationListener {

    private lateinit var networkCallback: NetworkCallback
    private lateinit var biometricAuthenticator: BiometricAuthenticator

    final override lateinit var binding: B
    abstract var frameContainerId: Int
    private val backCallback: MutableLiveData<OnBackPressedListener?> = MutableLiveData()
    private var lastFragmentTag = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, layoutId)
        binding.lifecycleOwner = this

        try {
            // observe network connection
            networkCallback = NetworkCallback(this)
            networkCallback.observeNetworkConnection(object : NetworkCallback.NetworkObservation{
                override fun isActive(isOnline: Boolean) {
                    if(isOnline){
                        InternetSnackbar.internet(this@BaseActivity, binding.root, "Back to online", InternetSnackbar.ONLINE)
                    }else{
                        InternetSnackbar.internet(this@BaseActivity, binding.root, "No internet", InternetSnackbar.OFFLINE)
                    }
                }
            })

            biometricAuthenticator =
                BiometricAuthenticator.instance(this, object : BiometricAuthenticator.Listener {
                    override fun onNewMessage(message: String) {
                        Toast.makeText(this@BaseActivity, message, Toast.LENGTH_LONG).show()
                    }
                })

            // show negative/cancel
            biometricAuthenticator.showNegativeButton = true

        } catch (e: Exception) {}
    }

    override fun onBackPressed() {
        if (backCallback.value == null) {
            if (!canBack())
                super.onBackPressed()
        } else {
            if (backCallback.value?.onBackPressed(this) == false){
                if (!canBack()) {
                    super.onBackPressed()
                }
            }
        }
    }

    private fun canBack(): Boolean {
        if (supportFragmentManager.backStackEntryCount == 0) {
            finish()
            return true
        }

        supportFragmentManager.getBackStackEntryAt(supportFragmentManager.backStackEntryCount - 1)
            .name?.let {
                supportFragmentManager.findFragmentByTag(it)?.apply {
                    if (childFragmentManager.backStackEntryCount != 0) {
                        childFragmentManager.popBackStack()
                        return true
                    }

                }
            }
        return false
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

        if (backCallback.value != null) {
            backCallback.value = null
        }
        super.onDestroy()
    }

    override fun navigateTo(fragment: Fragment, backStackTag: String, animationType: TransitionAnimation, isAdd: Boolean) {
        if (lastFragmentTag.equals(backStackTag, ignoreCase = true)) {
            Log.e("BaseActivity", "Cannot navigate to the current fragment. It's already visible on the screen")
            return
        }

        if (frameContainerId == 0) {
            Log.e("BaseActivity", "No container is defined to navigate on!")
            return
        }

        if (supportFragmentManager.backStackEntryCount > 0 && isAdd) {
            val tag = supportFragmentManager
                .getBackStackEntryAt(supportFragmentManager.backStackEntryCount - 1)
                .name

            supportFragmentManager.findFragmentByTag(tag)?.apply {
                this.onPause()
            }
        }

        supportFragmentManager.findFragmentByTag(backStackTag)?.let {
            supportFragmentManager.popBackStack(backStackTag, FragmentManager.POP_BACK_STACK_INCLUSIVE)
            return
        }


        supportFragmentManager.beginTransaction().apply {
            setCustomAnimation(animationType)
            if (isAdd) {
                add(frameContainerId, fragment, backStackTag)
            } else {
                replace(frameContainerId, fragment, backStackTag)
            }
            //addToBackStack(backStackTag)
            commitAllowingStateLoss()
        }

        lastFragmentTag = backStackTag
    }

    /**
     * Show System dialog credentials
     */
    fun showCredentialScreen(){
        biometricAuthenticator.authenticateWithoutCrypto(this)
    }

}