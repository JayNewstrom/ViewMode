ViewMode
=========

ViewModeView is a [View][androidView] that provides a simple mechanism to show different view modes depending on state. 
For instance, it allows you to show a loading view while the data is being downloaded, the content view once the download is complete, 
or a failed to load view if the data fails to download.

Usage
------------

Using `ViewModeView` is as easy as calling `viewModeView.showViewMode`. `showViewMode` accepts an input parameter that's an interface
which has a single method to create the view.

```java
public final class MainActivity extends Activity {

    @Bind(R.id.view_mode_view) ViewModeView viewModeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        viewModeView.showViewMode(ViewModes.CONTENT);
    }
}
```

Setup
------------
```groovy
dependencies {
    compile 'com.jaynewstrom:view-mode:1.2.0'
}
```

License
-------

    Copyright 2015 Jay Newstrom

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.

[androidView]: http://developer.android.com/reference/android/view/View.html
