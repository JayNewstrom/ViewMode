package com.jaynewstrom.viewmode

import android.view.View

interface ViewMode {
    fun createView(parent: ViewModeView): View
}
