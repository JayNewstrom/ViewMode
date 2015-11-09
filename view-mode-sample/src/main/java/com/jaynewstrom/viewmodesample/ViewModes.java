package com.jaynewstrom.viewmodesample;

import android.view.LayoutInflater;
import android.view.View;

import com.jaynewstrom.viewmode.ViewMode;
import com.jaynewstrom.viewmode.ViewModeView;

public enum ViewModes implements ViewMode {

    CONTENT {
        @Override public View createView(ViewModeView parent) {
            return LayoutInflater.from(parent.getContext()).inflate(R.layout.content, parent, false);
        }
    },
    FAILED_TO_LOAD {
        @Override public View createView(ViewModeView parent) {
            return LayoutInflater.from(parent.getContext()).inflate(R.layout.failed_to_load, parent, false);
        }
    }
}
