package com.alhanpos.store.util

import android.net.Uri

interface Callback {
    fun captureImageData(uri: Uri?)
    fun browseImageData(uri: Uri?)
    fun pdfData(uri: Uri?)
    fun permissionGranted()
    fun permissionNotGranted()
}