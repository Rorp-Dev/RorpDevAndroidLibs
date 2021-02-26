package com.rorp.rorpdevlibs.base

import androidx.fragment.app.Fragment
import com.rorp.rorpdevlibs.util.extensions.TransitionAnimation

interface NavigationListener {

    fun navigateTo(
        fragment: Fragment,
        backStackTag: String,
        animationType: TransitionAnimation,
        isAdd: Boolean
    )

}