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


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.os.SystemClock;
import android.support.test.InstrumentationRegistry;
import android.support.test.filters.SdkSuppress;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.BySelector;
import android.support.test.uiautomator.Configurator;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.Until;
import android.util.Log;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.RadioButton;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

@RunWith(AndroidJUnit4.class)
@SdkSuppress(minSdkVersion = 21)
public class CustomerDetailTest {

    /** clickAndWaitForWindow()のタイムアウト値 */
    private static final long WAIT_TIMEOUT = 30 * 1000L;

    /** 戻るボタン押下後にスリープする時間 */
    private static final long PRESS_BACK_WAIT = 10 * 1000L;

    private static final String TAG = CustomerDetailTest.class.getSimpleName();

    /** UiDeviceへの参照 */
    private UiDevice mUiDevice;

    /**
     * APIレベル18以降において、 clickAndWaitForWindow()のタイムアウト値を {@link #WAIT_TIMEOUT} に設定する。
     *
     * @see <a href="https://code.google.com/p/android/issues/detail?id=75610">Bug ID 75610</a>
     */
    private void changeWaitTimeout() {
        Configurator config = Configurator.getInstance();
        config.setActionAcknowledgmentTimeout(WAIT_TIMEOUT);
    }

    @Before
    public void setUp() throws Exception {
        // UiDeviceを初期化する。
        mUiDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
        changeWaitTimeout();
    }

    /**
     * Master画面の[add]コンポーネントを見つける。
     */
    private UiObject2 findAddButtonInMasterScreen() {
        return mUiDevice.findObject(By.desc("add"));
    }

    /**
     * Detail画面の名前入力欄を見つける。
     */
    private UiObject2 findNameTextFieldInDetailScreen() {
        return mUiDevice.findObject(By.desc("name textfield"));
    }

    /**
     * Detail画面の[preview]コンポーネントを見つける。
     */
    private UiObject2 findPreviewButtonInDetailScreen() {
        return mUiDevice.findObject(By.desc("preview"));
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
    private UiObject2 findAgeTextFieldInDetailScreen() {
        BySelector ageChildCriteria = By.clazz(EditText.class);
        return mUiDevice.findObject(By.clazz(NumberPicker.class)).findObject(ageChildCriteria);
    }

    /**
     * Detail画面の「男性」ラジオボタンを検索するためのUiObjectを生成する。
     *
     * <pre>
     * RadioGroup (contentDescription="gender segmentedcontrol")
     *  ├RadioButton ("男性")
     *  └RadioButton ("女性")
     * </pre>
     *
     * というレイアウトになっているため、
     * 「contentDescrption属性が"gender segmentedcontrol"」のコンポーネント配下の"男性"ラジオボタン」
     * を検索条件とする。
     */
    private UiObject2 findMaleRadioButtonInDetailScreen() {
        BySelector genderChildCriteria = By.clazz(RadioButton.class).text("男性");
        return mUiDevice.findObject(By.desc("gender segmentedcontrol")).findObject(genderChildCriteria);
    }

    /**
     * 新規顧客を追加できることを確認する。
     * <ul>
     * <li>Master画面で[add]ボタンを押してDetail画面に遷移する。</li>
     * <li>名前入力欄に"Newton Geizler"と入力する。</li>
     * <li>性別欄で「男性」を選択する。</li>
     * <li>年齢欄に「35」と入力する。</li>
     * <li>[preview]ボタンを押して、プレビュー画面に遷移する。</li>
     * <li>「戻る」ボタンを押してDetail画面に戻る。</li>
     * <li>Master画面に、さきほど入力した名前・性別・年齢が反映されていることを確認する。</li>
     * <li>「戻る」ボタンを押してMaster画面に戻る。</li>
     * </ul>
     */
    @Test
    public void test新規顧客の追加() throws Exception {
        // setup
        String inputName = "Newton Geizler";
        String inputAge = "35";

        try {
            // exercise
            // addボタンを押し、Detail画面が切り替わるまで待つ。
            findAddButtonInMasterScreen().clickAndWait(Until.newWindow(), WAIT_TIMEOUT);
            // 名前入力欄テキストを入力する。
            UiObject2 nameTextFieldInDetailScreen = findNameTextFieldInDetailScreen();
            nameTextFieldInDetailScreen.setText(inputName);

            // 性別欄の「男性」をクリックする。
            UiObject2 maleRadioButtonInDetailScreen = findMaleRadioButtonInDetailScreen();
            maleRadioButtonInDetailScreen.click();

            // 年齢欄に数字を入力してから、年齢入力を確定させるため、年齢入力欄をタップする。
            UiObject2 ageTextFieldInDetailScreen = findAgeTextFieldInDetailScreen();
            ageTextFieldInDetailScreen.setText(inputAge);

            // previewボタンを押して、プレビュー画面に切り替わるまで待つ。
            findPreviewButtonInDetailScreen().clickAndWait(Until.newWindow(), WAIT_TIMEOUT);

            // 戻るボタンを押し、一定時間待つ。これでDetail画面に戻る。
            mUiDevice.pressBack();
            // 戻るボタンを押してから、画面が切り替わるまで待つ方法が用意されていないため、一定時間スリープする。
            SystemClock.sleep(PRESS_BACK_WAIT);

            // verify
            // Detail画面で入力内容が保持されているか確認する。
            assertThat("「名前」の入力内容が一致しません", nameTextFieldInDetailScreen.getText(), is(inputName));
            assertThat("性別の「男性」がチェックされていません", maleRadioButtonInDetailScreen.isChecked(), is(true));
            assertThat("「年齢」の入力内容が一致しません", ageTextFieldInDetailScreen.getText(), is(inputAge));
        } finally {
            // Detail画面で例外が発生した場合も、そうでなくても、戾るボタンを押し、Master画面に遷移する。
            mUiDevice.pressBack();
        }
    }
}
