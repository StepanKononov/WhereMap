package com.north.wheremap.core.ui

import androidx.annotation.StringRes
import com.north.wheremap.R
import com.north.wheremap.core.domain.utils.DataError

@StringRes
fun DataError.asStringRes(): Int {
    return when (this) {
        DataError.Local.DISK_FULL -> R.string.error_disk_full

        DataError.Network.REQUEST_TIMEOUT -> R.string.error_request_timeout

        DataError.Network.TOO_MANY_REQUESTS -> R.string.error_too_many_requests

        DataError.Network.NO_INTERNET -> R.string.error_no_internet

        DataError.Network.PAYLOAD_TOO_LARGE -> R.string.error_payload_too_large

        DataError.Network.SERVER_ERROR -> R.string.error_server_error

        DataError.Network.SERIALIZATION -> R.string.error_serialization

        else -> R.string.error_unknown
    }
}