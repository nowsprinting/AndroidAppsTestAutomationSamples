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
 */

package com.nowsprinting.hellotesting.uiautomator;

import com.nowsprinting.hellotesting.uiautomator.page.Gender;
import com.nowsprinting.hellotesting.uiautomator.page.MasterPage;
import com.nowsprinting.hellotesting.uiautomator.page.MasterPageScrollSupport;
import com.nowsprinting.hellotesting.uiautomator.util.ShellCommands;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.support.test.InstrumentationRegistry;
import android.support.test.filters.SdkSuppress;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.UiDevice;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(AndroidJUnit4.class)
@SdkSuppress(minSdkVersion = 21)
public class CustomerModifyTest3 {

    private static final String AUT_PACKAGE = "com.nowsprinting.hellotesting.app";

    private static final String AUT_ACTIVITY = AUT_PACKAGE + ".CustomerListActivity";

    /** UiDeviceへの参照 */
    private UiDevice mUiDevice;

    @Before
    public void setUp() throws Exception {
        // UiDeviceを初期化する。
        mUiDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
        ShellCommands.startApp(AUT_PACKAGE, AUT_ACTIVITY);
    }

    /**
     * 指定された個数の顧客を追加します。
     *
     * @param numberOfCustomers 追加する顧客の数
     * @param namePrefix        追加する顧客氏名のプレフィックス
     * @return 最後に追加された顧客の氏名
     */
    private String addCustomers(int numberOfCustomers, String namePrefix) throws Exception {
        String lastAddedName = "";
        MasterPage page = new MasterPageScrollSupport(mUiDevice);
        for (int i = 0; i < numberOfCustomers; i++) {
            String name = String.format("%s %d", namePrefix, i);
            String email = String.format("name%d@example.com", i);
            Gender gender = (i % 2 == 0) ? Gender.MALE : Gender.FEMALE;
            int age = 5 + i * 2;
            page.goDetailPageToAddCustomer()
                    .updateCustomerDetail(name, email, gender, String.valueOf(age));
            lastAddedName = name;
        }
        return lastAddedName;
    }

    @Test
    public void test顧客修正_15人_氏名が日本語() throws Exception {
        // setup
        // 15件の顧客を追加
        // lastAddedNameには最後に追加した顧客の氏名が入る
        String lastAddedName = addCustomers(15, "山田");

        // exercise
        String newName = "田中";
        MasterPage page = new MasterPageScrollSupport(mUiDevice);
        page = page.goDetailPageToModifyCustomer(lastAddedName).typeName(newName).saveAndGoBack();

        // verify
        assertThat("Master画面に[" + newName + "]が見付かりません",
                page.doesCustomerNameExist(newName), is(true));
    }

}
