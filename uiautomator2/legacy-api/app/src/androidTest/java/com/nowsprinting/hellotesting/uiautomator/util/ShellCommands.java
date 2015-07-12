/*
 * Copyright 2014, 2015 TOYAMA Sumio
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
 *
 * ========================
 * startApp() getLauncherPackageName() is inspired from
 *     https://github.com/googlesamples/android-testing/blob/master/uiautomator/BasicSample/app/src/androidTest/java/com/example/android/testing/uiautomator/BasicSample/ChangeTextBehaviorTest.java
 *
 * The original copyright is as follows:
 *
 * Copyright 2015, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.nowsprinting.hellotesting.uiautomator.util;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.support.test.InstrumentationRegistry;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.Until;
import android.util.Log;

import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

/**
 * 端末内のシェルコマンド発行に関係するメソッドを定義したクラス。
 */
public class ShellCommands {

    public static final int LAUNCH_TIMEOUT = 5000;

    private static final String TAG = "ShellCommands";

    /**
     * 指定されたパッケージ名とアクティビティ名を元に、アプリを起動する。
     * 既に起動済みの場合は、一旦強制終了してから起動する。
     *
     * @param packageName  起動したいパッケージ名
     * @param activityName 起動したいアクティビティ名 (使わなくなった)
     * @throws IOException          起動中にエラーが発生した場合
     * @throws InterruptedException 起動待ち中に割り込みが発生した場合
     */
    public static void startApp(String packageName, String activityName) throws IOException,
            InterruptedException {
        UiDevice device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());

        // amコマンドを発行できなくなったため、
        // 以下の方法で強制終了→再起動を実現する。
        // 1. ホームキーを押し、ターゲットアプリをバックグラウンドに移行させる。
        // 2. ActivityManager#killBackgroundProcess(String) を使って、ターゲットアプリをkillする。
        //    ※このメソッドはバックグラウンドアプリにしか効かないため、手順1が必須となる。
        // 3. ターゲットアプリをstartActivity()により起動する。
        device.pressHome();

        // Wait for launcher
        final String launcherPackage = getLauncherPackageName();
        Log.d(TAG, "Wait for launcher");
        device.wait(Until.hasObject(By.pkg(launcherPackage).depth(0)), LAUNCH_TIMEOUT);
        Log.d(TAG, "Wait end");

        // すぐにkillBackgroundProcess()すると、killできないことがあるので、少しウェイトする。
        Log.d(TAG, "sleep start");
        TimeUnit.SECONDS.sleep(5);
        Log.d(TAG, "sleep end");

        Log.d(TAG, "killBackgroundProcess(" + packageName + ")");
        Context context = InstrumentationRegistry.getContext();
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        am.killBackgroundProcesses(packageName);

        final Intent intent = context.getPackageManager()
                .getLaunchIntentForPackage(packageName);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);    // Clear out any previous instances
        context.startActivity(intent);

        // Wait for the app to appear
        device.wait(Until.hasObject(By.pkg(packageName).depth(0)), LAUNCH_TIMEOUT);
    }

    /**
     * Uses package manager to find the package name of the device launcher. Usually this package
     * is "com.android.launcher" but can be different at times. This is a generic solution which
     * works on all platforms.`
     */
    private static String getLauncherPackageName() {
        // Create launcher Intent
        final Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);

        // Use PackageManager to get the launcher package name
        PackageManager pm = InstrumentationRegistry.getContext().getPackageManager();
        ResolveInfo resolveInfo = pm.resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY);
        return resolveInfo.activityInfo.packageName;
    }
}
