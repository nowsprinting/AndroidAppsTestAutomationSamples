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

package com.nowsprinting.hellotesting.appiumtest.appium.page;

import io.appium.java_client.android.AndroidDriver;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Preview画面のページオブジェクト実装(Appiumモード)。
 */
public class PreviewPage {

    protected AndroidDriver mDriver;
    protected WebDriverWait mWait;

    public PreviewPage(AndroidDriver driver) {
        mDriver = driver;
        mWait = new WebDriverWait(mDriver, 60L);
    }

    /**
     * このページがロードされるまで一定時間待つ。
     * 
     * @return ロードされた場合にはtrueを、ロードが完了する前にタイムアウトした場合にはfalseを返す。
     */
    public boolean waitUntilLoad() {
        try {
            // WebViewが存在するまで待つ。
            mWait.until(ExpectedConditions.presenceOfElementLocated(By.className("android.webkit.WebView")));
        } catch (TimeoutException e) {
            return false;
        }
        return true;
    }

    /**
     * このページに表示されている「氏名」を取得する。
     */
    public String getName() {
        ensureWebViewContext();
        return mDriver.findElement(By.name("name")).getText();
    }

    /**
     * このページに表示されている「メールアドレス」を取得する。
     */
    public String getMailAddress() {
        ensureWebViewContext();
        return mDriver.findElement(By.name("mail")).getText();
    }

    /**
     * このページに表示されている「性別」を取得する。
     */
    public String getGender() {
        ensureWebViewContext();
        return mDriver.findElement(By.name("gender")).getText();
    }

    /**
     * このページに表示されている「年齢」を取得する。
     */
    public String getAge() {
        ensureWebViewContext();
        return mDriver.findElement(By.name("age")).getText();
    }

    /**
     * このページに表示されている「区分」を取得する。
     */
    public String getDivision() {
        ensureWebViewContext();
        return mDriver.findElement(By.name("division")).getText();
    }

    /**
     * 現在のコンテキストがWebViewコンテキストであるかどうかを判定する。
     * 
     * @return 現在のコンテキストがWebViewコンテキストである場合にtrueを、そうでない場合にfalseを返す。
     */
    private boolean isInWebViewContext() {
        String currentContext = mDriver.getContext();
        return isWebViewContext(currentContext);
    }

    /**
     * 指定されたコンテキストがWebViewコンテキストであるかどうかを判定する。
     * 文字列が"NATIVE_APP"で<b>なければ</b>、WebViewコンテキストであると判断する。
     * 
     * @return WebViewコンテキストの場合にtrue、そうでない場合にfalseを返す。
     */
    private boolean isWebViewContext(String context) {
        return !context.equals("NATIVE_APP");
    }

    /**
     * WebViewコンテキストに切り替える。
     * 既にWebViewコンテキストの場合は何もしない。
     */
    private void ensureWebViewContext() {
        if (isInWebViewContext()) {
            // すでにWebViewコンテキストの場合は何もしない。
            return;
        }

        for (String context : mDriver.getContextHandles()) {
            if (isWebViewContext(context)) {
                mDriver.context(context);
                return;
            }
        }
        Assert.fail("WebView context doesn't exist");
    }
}
