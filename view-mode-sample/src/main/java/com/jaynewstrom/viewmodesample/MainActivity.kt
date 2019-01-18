package com.jaynewstrom.viewmodesample

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import butterknife.BindView
import butterknife.ButterKnife
import com.jaynewstrom.viewmode.ViewModeView

class MainActivity : AppCompatActivity() {
    @BindView(R.id.view_mode_view) lateinit var viewModeView: ViewModeView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ButterKnife.bind(this)

        if (savedInstanceState == null) {
            viewModeView.showViewMode(ViewModes.CONTENT)
        } else {
            viewModeView.showViewMode(savedInstanceState.getSerializable(SAVED_STATE_VIEW_MODE) as ViewModes)
        }

        if (viewModeView.currentViewMode() === ViewModes.CONTENT) {
            val contentView = viewModeView.viewForViewMode(ViewModes.CONTENT) as ContentView
            // Usually I would use dependency injection to get a reference, but to keep things simple, this works.
            contentView.setViewModeView(viewModeView)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putSerializable(SAVED_STATE_VIEW_MODE, viewModeView.currentViewMode() as ViewModes?)
    }

    companion object {
        private const val SAVED_STATE_VIEW_MODE = "savedState.viewMode"
    }
}
