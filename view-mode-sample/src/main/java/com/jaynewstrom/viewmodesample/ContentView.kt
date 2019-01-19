package com.jaynewstrom.viewmodesample

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import butterknife.ButterKnife
import butterknife.OnClick
import com.jaynewstrom.viewmode.ViewModeView

class ContentView(context: Context, attrs: AttributeSet) : LinearLayout(context, attrs) {
    private lateinit var viewModeView: ViewModeView

    override fun onFinishInflate() {
        super.onFinishInflate()
        ButterKnife.bind(this)
    }

    @OnClick(R.id.button) internal fun onButtonClicked() {
        viewModeView.showViewMode(ViewModes.FAILED_TO_LOAD)
    }

    fun setViewModeView(viewModeView: ViewModeView) {
        this.viewModeView = viewModeView
    }
}
