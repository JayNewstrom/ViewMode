package com.jaynewstrom.viewmodesample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.jaynewstrom.viewmode.ViewModeView;

import butterknife.Bind;
import butterknife.ButterKnife;

public final class MainActivity extends AppCompatActivity {

    @Bind(R.id.view_mode_view) ViewModeView viewModeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        viewModeView.showViewMode(ViewModes.CONTENT);
        ContentView contentView = (ContentView) viewModeView.viewForViewMode(ViewModes.CONTENT);
        contentView.setViewModeView(viewModeView);
    }
}
