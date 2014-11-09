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

import static org.junit.Assert.*;
import io.appium.java_client.MobileBy;
import io.appium.java_client.android.AndroidDriver;

import org.openqa.selenium.WebElement;

/**
 * Master画面のページオブジェクト実装(Appiumモード)。
 */
public class MasterPage {

    protected AndroidDriver mDriver;

    public MasterPage(AndroidDriver driver) {
        mDriver = driver;
    }

    /**
     * 顧客を新規追加するためのDetail画面を開く。
     * 
     * @return Detail画面オブジェクト
     */
    public DetailPage goDetailPageToAddCustomer() {
        // Accessibility ID (= AndroidではcontentDescription属性)
        // が"add"であるUI部品を探し、クリックする。
        WebElement addButton = mDriver.findElement(MobileBy.AccessibilityId("add"));
        addButton.click();
        DetailPage detailPage = new DetailPage(mDriver);
        assertTrue(detailPage.waitUntilLoad());
        return detailPage;
    }
}
