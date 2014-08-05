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

package com.nowsprinting.hellotesting.uiautomator;

import com.android.uiautomator.core.UiObject;
import com.android.uiautomator.core.UiSelector;
import com.android.uiautomator.testrunner.UiAutomatorTestCase;

import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.RadioButton;

public class CustomerDetailTest extends UiAutomatorTestCase {

    /** Master画面の[add]コンポーネント */
    private UiObject mAddButtonInMasterScreen;

    /** Detail画面の名前入力欄 */
    private UiObject mNameTextFieldInDetailScreen;

    /** Detail画面の「男性」ラジオボタン */
    private UiObject mMaleRadioButtonInDetailScreen;

    /** Detail画面の年齢入力欄 */
    private UiObject mAgeTextFieldInDetailScreen;

    /** Detail画面の[preview]コンポーネント */
    private UiObject mPreviewButtonInDetailScreen;


    @Override
    protected void setUp() throws Exception {
        super.setUp();

        // 各UIコンポーネントを検索するためのUiObjectを構築する。
        // この時点UiObjectをnewした時点)では実際の検索は行われないことに注意。
        mAddButtonInMasterScreen = new UiObject(new UiSelector().description("add"));
        mNameTextFieldInDetailScreen = new UiObject(new UiSelector().description("name textfield"));
        mMaleRadioButtonInDetailScreen = createMailRadioButtonInDetailScreen();
        mAgeTextFieldInDetailScreen = createAgeTextFieldInDetailScreen();
        mPreviewButtonInDetailScreen = new UiObject(new UiSelector().description("preview"));
    }

    /**
     * Detail画面の「年齢」入力欄を検索するためのUiObjectを生成する。
     * <pre>
     * NumberPicker
     *   ├Button
     *   ├EditText (年齢の入力欄)
     *   └Button
     * </pre>
     * というレイアウトになっているため、「NumberPicker配下のEditText」を検索条件とする。
     */
    private UiObject createAgeTextFieldInDetailScreen() {
        UiSelector ageChildCriteria = new UiSelector().className(EditText.class);
        return new UiObject(
                new UiSelector().className(NumberPicker.class).childSelector(ageChildCriteria));
    }

    /**
     * Detail画面の「男性」ラジオボタンを検索するためのUiObjectを生成する。
     * <pre>
     * RadioGroup (contentDescription="gender segmentedcontrol")
     *  ├RadioButton ("男性")
     *  └RadioButton ("女性")
     * </pre>
     * というレイアウトになっているため、
     * 「contentDescrption属性が"gender segmentedcontrol"」のコンポーネント配下の"男性"ラジオボタン」
     * を検索条件とする。
     */
    private UiObject createMailRadioButtonInDetailScreen() {
        UiSelector genderChildCriteria = new UiSelector().className(RadioButton.class).text("男性");
        return new UiObject(new UiSelector().description("gender segmentedcontrol").childSelector(
                genderChildCriteria));
    }

    /**
     * 新規顧客を追加できることを確認する。
     * <ul>
     *     <li>Master画面で[add]ボタンを押してDetail画面に遷移する。</li>
     *     <li>名前入力欄に"Newton Geizler"と入力する。</li>
     *     <li>性別欄で「男性」を選択する。</li>
     *     <li>年齢欄に「35」と入力する。</li>
     *     <li>[preview]ボタンを押して、プレビュー画面に遷移する。</li>
     *     <li>「戻る」ボタンを押してDetail画面に戻る。</li>
     *     <li>Master画面に、さきほど入力した名前・性別・年齢が反映されていることを確認する。</li>
     *     <li>「戻る」ボタンを押してMaster画面に戻る。</li>
     * </ul>
     */
    public void test新規顧客の追加() throws Exception {
        // setup
        String inputName = "Newton Geizler";
        String inputAge = "35";

        try {
            // exercise
            // addボタンを押し、Detail画面が切り替わるまで待つ。
            mAddButtonInMasterScreen.clickAndWaitForNewWindow();
            // 名前入力欄テキストを入力する。
            mNameTextFieldInDetailScreen.setText(inputName);
            // 性別欄の「男性」をクリックする。
            mMaleRadioButtonInDetailScreen.click();
            // 年齢欄に数字を入力してから、年齢入力を確定させるため、年齢入力欄をタップする。
            mAgeTextFieldInDetailScreen.setText(inputAge);
            mAgeTextFieldInDetailScreen.click();
            // previewボタンを押して、プレビュー画面に切り替わるまで待つ。
            mPreviewButtonInDetailScreen.clickAndWaitForNewWindow();

            // 戻るボタンを押し、アイドルになるまで待つ。これでDetail画面に戻る。
            getUiDevice().pressBack();
            getUiDevice().waitForIdle();

            // verify
            // Detail画面で入力内容が保持されているか確認する。
            assertEquals("「名前」の入力内容が一致しません", inputName, mNameTextFieldInDetailScreen.getText());
            assertTrue("性別の「男性」がチェックされていません", mMaleRadioButtonInDetailScreen.isChecked());
            assertEquals("「年齢」の入力内容が一致しません", inputAge, mAgeTextFieldInDetailScreen.getText());
        } finally {
            // Detail画面で例外が発生した場合も、そうでなくても、戾るボタンを押し、Master画面に遷移する。
            getUiDevice().pressBack();
        }
    }
}
