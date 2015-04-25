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

package com.nowsprinting.hellotesting.appiumtest.selendroid;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;

import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.nowsprinting.hellotesting.appiumtest.appium.page.DetailPage;
import com.nowsprinting.hellotesting.appiumtest.appium.page.Gender;
import com.nowsprinting.hellotesting.appiumtest.appium.page.MasterPage;
import com.nowsprinting.hellotesting.appiumtest.appium.page.PreviewPage;
import com.nowsprinting.hellotesting.appiumtest.selendroid.page.SelendroidMasterPage;

/**
 * Selendroidモードで動作するテストクラスのサンプル。
 */
public class AddCustomerTest {

    private AndroidDriver mDriver;

    @Before
    public void setUp() throws Exception {
        // このテストに必要なdesired capabilitiesを構築し、それに基づいたAndroidDriverインスタンスを生成する。
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(CapabilityType.BROWSER_NAME, "");
        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "Android Emulator");
        // Selendroidモードで動作させるときには、AUTOMATION_NAMEを"Selendroid"にする。
        capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, "Selendroid");
        capabilities.setCapability(MobileCapabilityType.APP_PACKAGE, "com.nowsprinting.hellotesting.appium");
        capabilities.setCapability(MobileCapabilityType.APP_ACTIVITY, "com.nowsprinting.hellotesting.app.CustomerListActivity");
        capabilities.setCapability("unicodeKeyboard", true);
        capabilities.setCapability("resetKeyboard", true);
        mDriver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
        // デフォルトのタイムアウト値を80秒とする。
        mDriver.manage().timeouts().implicitlyWait(80, TimeUnit.SECONDS);
    }

    @After
    public void tearDown() throws Exception {
        mDriver.quit();
    }

    /**
     * 新規に顧客を追加し、プレビュー画面に追加した顧客の情報が正しく表示されることを確認する。
     * また、プレビュー画面上に表示される階層が正しいこともあわせて確認する。
     */
    @Test
    public void 新規顧客が追加できること() throws Exception {
        MasterPage master = new SelendroidMasterPage(mDriver);
        DetailPage detail = master.goDetailPageToAddCustomer();
        PreviewPage preview = detail.inputName("Yamada Taro")
                .inputMailAddress("yamada@example.com")
                .selectGender(Gender.MALE)
                .inputAge(9)
                .saveAndPreview();

        assertThat(preview.getName(), is("Yamada Taro"));
        assertThat(preview.getMailAddress(), is("yamada@example.com"));
        assertThat(preview.getAge(), is("9"));
        assertThat(preview.getGender(), is("男性"));
        assertThat(preview.getDivision(), is("C層"));
    }
}
