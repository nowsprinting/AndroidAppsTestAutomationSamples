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
import com.nowsprinting.hellotesting.uiautomator.util.CommonUiActions;

import jp.jun_nama.test.utf7ime.helper.Utf7ImeHelper;

/**
 * Detail画面を表すクラス(氏名欄のみUnicode文字の入力に対応)。
 */
public class DetailPageUnicodeSupport extends DetailPage {

    @Override
    public DetailPage typeName(String name) throws UiObjectNotFoundException {

        if (name != null) {
            // 国際化対応版のclearTextField()を最初に呼び出す
            CommonUiActions.i18nClearTextField(mNameTextField);
            // Utf7Imeが理解できる文字列に変換してからsetTextする
            mNameTextField.setText(Utf7ImeHelper.e(name));
        }
        return this;
    }

}
