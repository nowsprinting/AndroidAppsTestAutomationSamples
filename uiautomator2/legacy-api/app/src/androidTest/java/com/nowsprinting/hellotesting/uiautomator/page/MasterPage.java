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

import com.nowsprinting.hellotesting.uiautomator.util.CommonUiActions;

import junit.framework.Assert;

import android.support.test.uiautomator.UiCollection;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiSelector;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Master画面を表すクラス。
 */
public class MasterPage {

    static {
        CommonUiActions.changeWaitTimeout();
    }

    private UiObject mAddButton = new UiObject(new UiSelector().description("add"));

    /**
     * この画面が現在表示中かどうかを返す。
     *
     * @return 表示中の場合はtrue。そうでない場合はfasle。
     */
    public boolean isShowing() {
        // "HelloTesting"と書かれたTextViewがあり、かつ、追加ボタンがある場合に、この画面表示中であるとみなす。
        UiObject title = new UiObject(
                new UiSelector().className(TextView.class.getName()).text("HelloTesting"));
        return mAddButton.exists() && title.exists();
    }

    /**
     * 顧客を新規追加するためのDetail画面を開く。
     *
     * @return Detail画面オブジェクト
     */
    public DetailPage goDetailPageToAddCustomer() throws UiObjectNotFoundException {
        String assertMsg = "Detail画面に切り替わる前にタイムアウトしました";
        Assert.assertTrue(assertMsg,
                mAddButton.clickAndWaitForNewWindow(CommonUiActions.WAIT_TIMEOUT));
        return new DetailPage();
    }

    /**
     * 指定された名前の顧客を修正するためのDetail画面を開く
     *
     * @param customerName 修正したい顧客名
     */
    public DetailPage goDetailPageToModifyCustomer(String customerName)
            throws UiObjectNotFoundException {
        String assertMsg = "Detail画面に切り替わる前にタイムアウトしました";
        UiObject listItem = getCustomerItem(customerName);
        Assert.assertTrue(assertMsg,
                listItem.clickAndWaitForNewWindow(CommonUiActions.WAIT_TIMEOUT));
        return new DetailPage();
    }

    /**
     * 指定された顧客名が、顧客一覧の中にに表示されているかどうかを返す。
     *
     * @param customerName 検索したい顧客名
     * @return 表示されていればtrue。そうでなければfalse。
     */
    public boolean doesCustomerNameExist(String customerName) {
        try {
            // 指定された顧客名のリストアイテムを探す。
            getCustomerItem(customerName);
            // UiObjectNotFoundExceptionが発生しなければ存在するのでtrueを返す。
            return true;
        } catch (UiObjectNotFoundException e) {
            return false;
        }
    }

    /**
     * 顧客名一覧のListViewの中から、指定された顧客名が表示されているリストアイテムを取得する。
     *
     * @param customerName 検索したい顧客名
     */
    protected UiObject getCustomerItem(String customerName) throws UiObjectNotFoundException {
        UiCollection customerList = new UiCollection(
                new UiSelector().className(ListView.class.getName()));
        return customerList
                .getChildByText(new UiSelector().className(TextView.class.getName()), customerName);
    }
}
