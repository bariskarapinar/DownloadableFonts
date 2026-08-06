package com.myapp.downloadablefonts

import android.app.Application
import android.graphics.Typeface
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.googlefonts.Font
import androidx.compose.ui.text.googlefonts.GoogleFont
import androidx.lifecycle.AndroidViewModel

class FontViewModel(application: Application) : AndroidViewModel(application) {

    private val fontRequestManager = FontRequestManager(application)

    private val _fontFamily = mutableStateOf<FontFamily>(FontFamily.Default)
    val fontFamily: State<FontFamily> = _fontFamily

    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    private val _error = mutableStateOf<String?>(null)
    val error: State<String?> = _error

    private val provider = GoogleFont.Provider(
        providerAuthority = "com.google.android.gms.fonts",
        providerPackage = "com.google.android.gms",
        certificates = R.array.com_google_android_gms_fonts_certs
    )

    fun fetchFont(fontName: String) {
        Log.d("FontViewModel", "Fetching font: $fontName")
        _isLoading.value = true
        _error.value = null

        try {
            val font = GoogleFont(fontName)
            _fontFamily.value = FontFamily(
                Font(googleFont = font, fontProvider = provider)
            )
            _isLoading.value = false
            Log.d("FontViewModel", "Successfully set font family for $fontName")
        } catch (e: Exception) {
            Log.e("FontViewModel", "Failed to load font: ${e.message}")
            _error.value = "Failed to load font: ${e.message}"
            _isLoading.value = false
        }
    }

    // Keep the legacy method just to show Java integration
    fun fetchFontLegacy(fontName: String) {
        _isLoading.value = true
        fontRequestManager.requestFont(fontName, object : FontRequestManager.FontCallback {
            override fun onSuccess(typeface: Typeface) {
                _fontFamily.value = FontFamily(typeface)
                _isLoading.value = false
            }

            override fun onError(reason: Int) {
                _error.value = "Legacy fetch failed. Reason: $reason"
                _isLoading.value = false
            }
        })
    }
}
