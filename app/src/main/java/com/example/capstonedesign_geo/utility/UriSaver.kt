package com.example.capstonedesign_geo.utility

import android.net.Uri
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.SaverScope

// Uri를 문자열로 저장하는 Saver
class UriSaver : Saver<MutableList<Uri>, List<String>> {
    override fun restore(value: List<String>): MutableList<Uri> = value.map {
        Uri.parse(it)
    }.toMutableList()

    // Uri를 문자열로 저장하는 Saver
    override fun SaverScope.save(value: MutableList<Uri>): List<String> = value.map { it.toString() }
}