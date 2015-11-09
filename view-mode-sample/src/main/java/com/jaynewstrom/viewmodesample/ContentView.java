package com.jaynewstrom.viewmodesample;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.jaynewstrom.viewmode.ViewModeView;

import butterknife.ButterKnife;
import butterknife.OnClick;

public final class ContentView extends LinearLayout {

    private ViewModeView viewModeView;

    public ContentView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.bind(this);
    }

    @OnClick(R.id.button) void onButtonClicked() {
        viewModeView.showViewMode(ViewModes.FAILED_TO_LOAD);
    }

    public void setViewModeView(ViewModeView viewModeView) {
        this.viewModeView = viewModeView;
    }
}
