package com.trip.safe.common.error.exception

import com.trip.safe.common.error.BaseException

class RequestHandlerNotFoundException(
    errorMessage: String,
) : BaseException(errorMessage, 400) {
    companion object {
        const val REQUEST_HANDLER_NOT_FOUND = "Request Handler Not Found"
    }
}