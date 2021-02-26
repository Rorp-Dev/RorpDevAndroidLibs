package com.rorp.rorpdevlibs.network.usecase

import com.rorp.rorpdevlibs.network.exceptions.ApiError

interface UseCaseResponse<Type> {

    fun onSuccess(result: Type)

    fun onError(apiError: ApiError?)
}