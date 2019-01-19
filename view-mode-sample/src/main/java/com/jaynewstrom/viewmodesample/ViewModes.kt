package com.jaynewstrom.viewmodesample

import android.view.LayoutInflater
import android.view.View

import com.jaynewstrom.viewmode.ViewMode
import com.jaynewstrom.viewmode.ViewModeView

enum class ViewModes : ViewMode {
    CONTENT {
        override fun createView(parent: ViewModeView): View {
            return LayoutInflater.from(parent.context).inflate(R.layout.content, parent, false)
        }
    },
    FAILED_TO_LOAD {
        override fun createView(parent: ViewModeView): View {
            return LayoutInflater.from(parent.context).inflate(R.layout.failed_to_load, parent, false)
        }
    }
}
