/*
 * Copyright 2014 TOYAMA Sumio
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

package com.nowsprinting.hellotesting.uiautomator.util;

import android.content.res.Resources;
import android.os.Build;
import android.os.SystemClock;
import android.util.Log;

import com.android.uiautomator.core.Configurator;
import com.android.uiautomator.core.UiDevice;
import com.android.uiautomator.core.UiObject;
import com.android.uiautomator.core.UiObjectNotFoundException;
import com.android.uiautomator.core.UiSelector;

/**
 * uiautomatorで共通的に利用されるユーザー操作を実現するメソッドを定義したクラス。
 */
public class CommonUiActions {

    public static final long WAIT_TIMEOUT = 60 * 1000L;
    public static final long PRESS_BACK_WAIT = 10 * 1000L;
    private static final String TAG = "CommonUiActions";

    /**
     * APIレベル18以降において、 clickAndWaitForWindow()のタイムアウト値を {@link #WAIT_TIMEOUT} に設定する。
     * 
     * @see https://code.google.com/p/android/issues/detail?id=75610
     */
    public static void changeWaitTimeout() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            Configurator config = Configurator.getInstance();
            config.setActionAcknowledgmentTimeout(WAIT_TIMEOUT);
        }

    }

    /**
     * 引数に指定されたテキスト入力フィールドについて、既に入力されている文字列をクリアする。
     * テストを実行する端末の言語設定が英語以外でも動作する。
     * 
     * @param editText 内容をクリアしたいテキスト入力フィールド
     * @throws UiObjectNotFoundException
     */
    public static void i18nClearTextField(UiObject editText) throws UiObjectNotFoundException {
        editText.longClickTopLeft();
        // "Select all"のリソース文字列を android.R.string.selectAll から取得する。
        // この方法で、端末の言語設定に応じた文字列を取得することができる。
        String selectAllDesc = Resources.getSystem().getString(android.R.string.selectAll);
        UiObject selectAll = new UiObject(new UiSelector().descriptionContains(selectAllDesc));
        if (selectAll.waitForExists(50))
            selectAll.click();
        SystemClock.sleep(250);
        UiDevice.getInstance().pressDelete();
    }

    /**
     * 戻るボタンを押し、{@link #PRESS_BACK_WAIT}だけ待つ。
     */
    public static void pressBackAndWaitForIdle() {
        Log.d(TAG, "pressBack()");
        UiDevice.getInstance().pressBack();
        // 戻るボタンを押してから、画面が切り替わるまで待つ方法が用意されていないため、一定時間スリープする。
        Log.d(TAG, "sleep start");
        SystemClock.sleep(PRESS_BACK_WAIT);
        Log.d(TAG, "sleep end");
    }

}
