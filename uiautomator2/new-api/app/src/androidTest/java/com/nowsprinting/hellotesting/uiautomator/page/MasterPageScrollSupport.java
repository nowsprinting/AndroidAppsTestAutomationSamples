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

package com.nowsprinting.hellotesting.uiautomator.page;


import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiScrollable;
import android.support.test.uiautomator.UiSelector;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Master画面を表すクラス(スクロール対応版)。
 */

public class MasterPageScrollSupport extends MasterPage {

    public MasterPageScrollSupport(UiDevice uiDevice) {
        super(uiDevice);
    }

    /**
     * 顧客名一覧のListViewの中から、指定された顧客名が表示されているリストアイテムを取得する(スクロール対応版)。
     *
     * @param customerName 検索したい顧客名
     */
    @Override
    protected UiObject getCustomerItem(String customerName) throws UiObjectNotFoundException {
        UiScrollable customerList = new UiScrollable(
                new UiSelector().className(ListView.class.getName()));
        // スクロール方向を指定する
        customerList.setAsVerticalList();
        return customerList
                .getChildByText(new UiSelector().className(TextView.class.getName()), customerName);
    }

}
