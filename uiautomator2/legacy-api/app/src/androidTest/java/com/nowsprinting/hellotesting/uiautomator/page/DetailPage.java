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

package com.nowsprinting.hellotesting.uiautomator.page;

import com.nowsprinting.hellotesting.uiautomator.util.CommonUiActions;

import android.os.Build;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiSelector;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.RadioButton;

/**
 * Detail画面を表すクラス。
 */

public class DetailPage {

    protected UiObject mNameTextField = new UiObject(
            new UiSelector().description("name textfield"));

    protected UiObject mMailTextField = new UiObject(
            new UiSelector().description("mail textfield"));

    protected UiObject mAgeTextField = createAgeTextField();

    static {
        CommonUiActions.changeWaitTimeout();
    }

    public MasterPage updateCustomerDetail(String name, String mailAddress, Gender gender,
            String age) throws UiObjectNotFoundException {
        return typeName(name)
                .typeMailAddress(mailAddress)
                .selectGender(gender)
                .typeAge(age)
                .saveAndGoBack();
    }

    /**
     * 「氏名」欄に入力する。
     * 引数にnullが指定された場合には何もしない。
     *
     * @param name 入力する文字列。
     * @return この画面
     */
    public DetailPage typeName(String name) throws UiObjectNotFoundException {
        if (name != null) {
            if (!mNameTextField.isFocused()) {
                mNameTextField.click();
            }
            mNameTextField.setText(name);
        }
        return this;
    }

    /**
     * 「mail」欄に入力する。
     * 引数にnullが指定された場合には何もしない。
     *
     * @param mailAddress 入力する文字列
     * @return この画面
     */
    public DetailPage typeMailAddress(String mailAddress) throws UiObjectNotFoundException {
        if (mailAddress != null) {
            if (!mMailTextField.isFocused()) {
                mMailTextField.click();
            }
            mMailTextField.setText(mailAddress);
        }
        return this;
    }

    /**
     * 「年齢」欄に入力する。
     * 引数にnullが指定された場合には何もしない。
     *
     * @param age 入力する文字列。
     * @return この画面
     */
    public DetailPage typeAge(String age) throws UiObjectNotFoundException {
        // 年齢欄に数字を入力してから、年齢入力を確定させるため、年齢入力欄をタップする。
        if (age != null) {
            mAgeTextField.setText(age);
            if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.KITKAT) {
                mAgeTextField.click();
            }
        }
        return this;
    }

    /**
     * 「性別」欄を選択する。
     * 引数にnullが指定された場合には何もしない。
     *
     * @param gender 選択したい性別。
     * @return この画面
     */
    public DetailPage selectGender(Gender gender) throws UiObjectNotFoundException {
        if (gender != null) {
            UiObject genderRadio = createGenderRadioButtonInDetailScreen(gender);
            // 既にチェックされている場合にclick()すると、
            // accessibility eventが返ってこないため、タイムアウトするまで次の操作に進まない。
            // そのため、既にチェックされている場合は何もしないようにする。
            if (!genderRadio.isChecked()) {
                genderRadio.click();
            }
        }
        return this;
    }

    public MasterPage saveAndGoBack() {
        MasterPage masterPage = new MasterPage();
        CommonUiActions.pressBackAndWaitForIdle();
        // ソフトキーボード表示中の場合に「戻る」キーを押しても、
        // ソフトキーボード隠れるだけで、画面遷移しないことがある。
        // Master画面が表示されていない場合は、再度「戻る」キーを押す。
        if (!masterPage.isShowing()) {
            CommonUiActions.pressBackAndWaitForIdle();
        }
        return new MasterPage();
    }

    /**
     * Detail画面の「年齢」入力欄を検索するためのUiObjectを生成する。
     *
     * <pre>
     * NumberPicker
     *   ├Button
     *   ├EditText (年齢の入力欄)
     *   └Button
     * </pre>
     *
     * というレイアウトになっているため、「NumberPicker配下のEditText」を検索条件とする。
     */
    private UiObject createAgeTextField() {
        UiSelector ageChildCriteria = new UiSelector().className(EditText.class);
        return new UiObject(new UiSelector().className(NumberPicker.class).childSelector(
                ageChildCriteria));
    }

    /**
     * Detail画面で、指定された性別のラジオボタンを検索するためのUiObjectを生成する。
     *
     * <pre>
     * RadioGroup (contentDescription="gender segmentedcontrol")
     *  ├RadioButton ("男性")
     *  └RadioButton ("女性")
     * </pre>
     *
     * というレイアウトになっているため、
     * 「contentDescrption属性が"gender segmentedcontrol"」のコンポーネント配下の
     * "男性"/"女性"ラジオボタン」 を検索条件とする。
     */
    private UiObject createGenderRadioButtonInDetailScreen(Gender gender) {
        UiSelector genderChildCriteria = new UiSelector().className(RadioButton.class.getName())
                .text(gender.getRadioButtonText());
        return new UiObject(new UiSelector().description("gender segmentedcontrol").childSelector(
                genderChildCriteria));
    }

}
