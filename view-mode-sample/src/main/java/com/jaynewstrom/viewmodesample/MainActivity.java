package com.jaynewstrom.viewmodesample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.jaynewstrom.viewmode.ViewModeView;

import butterknife.BindView;
import butterknife.ButterKnife;

public final class MainActivity extends AppCompatActivity {

    private static final String SAVED_STATE_VIEW_MODE = "savedState.viewMode";

    @BindView(R.id.view_mode_view) ViewModeView viewModeView;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        if (savedInstanceState == null) {
            viewModeView.showViewMode(ViewModes.CONTENT);
        } else {
            viewModeView.showViewMode((ViewModes) savedInstanceState.getSerializable(SAVED_STATE_VIEW_MODE));
        }

        if (viewModeView.currentViewMode() == ViewModes.CONTENT) {
            ContentView contentView = (ContentView) viewModeView.viewForViewMode(ViewModes.CONTENT);
            // Usually I would use dependency injection to get a reference, but to keep things simple, this works.
            contentView.setViewModeView(viewModeView);
        }
    }

    @Override protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(SAVED_STATE_VIEW_MODE, (ViewModes) viewModeView.currentViewMode());
    }
}
