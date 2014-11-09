/*
 * Copyright 2015 TOYAMA Sumio
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.nowsprinting.hellotesting.app;

import android.annotation.TargetApi;
import android.app.Application;
import android.os.Build;
import android.webkit.WebView;

/**
 * WebViewのデバッグモードをONにするためのApplicationクラス実装です。
 * Appiumモードで、かつ、WebViewのテストを実施する場合に必要になります。
 *
 * @see <a href="https://developer.chrome.com/devtools/docs/remote-debugging#debugging-webviews">Remote Debugging on Android with Chrome</a>
 * @see <a href="http://appium.io/slate/en/v1.3.4/?java#automating-hybrid-android-apps">Appium公式ドキュメント</a>
 */
@TargetApi(Build.VERSION_CODES.KITKAT)
public class WebViewDebuggableApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WebView.setWebContentsDebuggingEnabled(true);
        }
    }

}
