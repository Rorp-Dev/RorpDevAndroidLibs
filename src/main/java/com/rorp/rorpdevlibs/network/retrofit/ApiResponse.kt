package com.rorp.rorpdevlibs.network.retrofit

import com.rorp.rorpdevlibs.network.exceptions.ApiError

/*
        _          _ ____
       / \   _ __ (_)  _ \ ___  ___ _ __   ___  _ __  ___  ___
      / _ \ | '_ \| | |_) / _ \/ __| '_ \ / _ \| '_ \/ __|/ _ \
     / ___ \| |_) | |  _ <  __/\__ \ |_) | (_) | | | \__ \  __/
    /_/   \_\ .__/|_|_| \_\___||___/ .__/ \___/|_| |_|___/\___|
            |_|                    |_|
 */
interface ApiResponse {
    fun onSuccess(response: String)

    fun onError(error: ApiError?)
}