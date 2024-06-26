package com.trip.safe.common.error.exception

import com.trip.safe.common.error.BaseException

class InvalidSignatureException (
    errorMessage: String,
) : BaseException(errorMessage, 401) {
    companion object {
        const val INVALID_SIGNATURE = "Invalid Signature"
    }
}