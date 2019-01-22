package com.jaynewstrom.viewmode

import android.view.View

interface ViewModeTransition {
    fun transition(fromViewMode: ViewMode?, fromView: View?, toView: View, transitionComplete: () -> Unit)
}

internal object DefaultViewModeTransition : ViewModeTransition {
    override fun transition(fromViewMode: ViewMode?, fromView: View?, toView: View, transitionComplete: () -> Unit) {
        transitionComplete()
    }
}
