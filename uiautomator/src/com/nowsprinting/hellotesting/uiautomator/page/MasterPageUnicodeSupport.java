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

import com.android.uiautomator.core.UiObjectNotFoundException;

/**
 * Master画面を表すクラス(Unicode文字の入力に対応)。
 */
public class MasterPageUnicodeSupport extends MasterPageScrollSupport {

    @Override
    public DetailPage goDetailPageToAddCustomer() throws UiObjectNotFoundException {
        super.goDetailPageToAddCustomer();
        // 戻り値だけ、DatailPageUnicodeSupportのインスタンスに変更する。
        return new DetailPageUnicodeSupport();
    }

    @Override
    public DetailPage goDetailPageToModifyCustomer(String customerName) throws UiObjectNotFoundException {
        super.goDetailPageToModifyCustomer(customerName);
        // 戻り値だけ、DatailPageUnicodeSupportのインスタンスに変更する。
        return new DetailPageUnicodeSupport();
    }

}
