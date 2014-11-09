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
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Detail画面のページオブジェクト実装(Appiumモード)。
 */
public class DetailPage {

    public static final String WIDGET_EDIT_TEXT = "android.widget.EditText";
    public static final String WIDGET_NUMBER_PICKER = "android.widget.NumberPicker";
    public static final String WIDGET_BUTTON = "android.widget.Button";
    private static final int AGE_MIN = 4;
    private static final int AGE_MAX = 120;
    protected AndroidDriver mDriver;
    protected WebDriverWait mWait;

    public DetailPage(AndroidDriver driver) {
        mDriver = driver;
        mWait = new WebDriverWait(mDriver, 60L);
    }

    /**
     * このページがロードされるまで一定時間待つ。
     * 
     * @return ロードされた場合にはtrueを、ロードが完了する前にタイムアウトした場合にはfalseを返す。
     */
    public boolean waitUntilLoad() {
        // 名前入力欄が見えるようになるまで待つ。
        try {
            mWait.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.AccessibilityId("name textfield")));
        } catch (TimeoutException e) {
            return false;
        }
        return true;
    }

    /**
     * 「氏名」欄に入力する。
     * 引数にnullが指定された場合には何もしない。
     * 
     * @param name 入力する文字列。
     * @return この画面
     */
    public DetailPage inputName(String name) {
        if (name != null) {
            WebElement nameTextField = mDriver.findElement(MobileBy.AccessibilityId("name textfield"));
            nameTextField.sendKeys(name);
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
    public DetailPage inputMailAddress(String mailAddress) {
        if (mailAddress != null) {
            WebElement mailTextField = mDriver.findElement(MobileBy.AccessibilityId("mail textfield"));
            mailTextField.sendKeys(mailAddress);

        }
        return this;
    }

    /**
     * 「年齢」欄に入力する。
     * 引数にnullが指定された場合には何もしない。
     * 
     * @param age 入力する年齢
     * @return この画面
     */
    public DetailPage inputAge(int age) throws Exception {
        /*
         * ---------------------------
         * NumberPicker
         * ├Button (年齢を1つ減らす)
         * ├EditText (年齢の入力欄: デフォルト値「4」)
         * └Button (年齢を1つ増やす)
         * ---------------------------
         * というレイアウトになっている。
         * デフォルト値が設定されているEditTextに対してのキー入力が正しく動作しないため、
         * 年齢を増減するボタンを繰り返し押すことで年齢入力を実現する。
         * https://github.com/appium/appium/issues/3662
         */
        if (age < AGE_MIN || age > AGE_MAX) {
            // 入力可能範囲を超えているときは何もしない。
            return this;
        }

        WebElement numberPicker = mDriver.findElement(By.className(WIDGET_NUMBER_PICKER));
        int currentAge = getCurrentAge(numberPicker);
        int ageDiff = age - currentAge;
        if (ageDiff == 0) {
            // 既に入力したい年齢になっている場合は何もしない。
            return this;
        } else if (ageDiff > 0) {
            for (int i = 0; i < ageDiff; i++) {
                incrementCurrentAge(numberPicker);
            }
        } else if (ageDiff < 0) {
            for (int i = 0; i < Math.abs(ageDiff); i++) {
                decrementCurrentAge(numberPicker);
            }
        }
        return this;
    }

    /**
     * NumberPicker内のEditTextに設定されている、年齢値をintで取得します。
     */
    private int getCurrentAge(WebElement numberPicker) {
        WebElement ageTextField = numberPicker.findElement(By.className(WIDGET_EDIT_TEXT));
        // 現在のアプリの実装では、当該EditTextが空になっていることはない。
        return Integer.parseInt(ageTextField.getText(), 10);
    }

    /**
     * NumberPicker内の年齢値を1つ増加させます。
     * 既に年齢値が最大値に達しているときは何もしません。
     */
    private void incrementCurrentAge(WebElement numberPicker) throws Exception {
        int currentAge = getCurrentAge(numberPicker);
        if (currentAge == AGE_MAX) {
            return;
        }

        WebElement incButton;
        if (currentAge == AGE_MIN) {
            // 現在の年齢値が最小値のときは、「年齢を減らす」ボタンが存在せず、
            // 「年齢を増やす」ボタンしかない。
            incButton = numberPicker.findElement(By.className(WIDGET_BUTTON));
        } else {
            // 現在の年齢値が最大値でも最小値でも無いときは、「年齢を減らす」ボタンと、
            // 「年齢を増やす」ボタンの両方が存在し、2つめのボタン(indexは1)が「年齢を増やす」ボタンとなる。
            incButton = numberPicker.findElements(By.className(WIDGET_BUTTON)).get(1);
        }
        incButton.click();
        if (getCurrentAge(numberPicker) == currentAge + 1) {
            // click()で数字が1つ増加した場合はreturnする。
            return;
        }
        // click()で数字が増加しない場合はtap()を試みる。Android 5.0以上で頻発する。
        // ボタンのタップ前後にウェイトを入れないと失敗することがある。
        TimeUnit.SECONDS.sleep(1);
        ((MobileElement) incButton).tap(1, 10);
        waitUntilAgeToBe(numberPicker, currentAge + 1);
    }

    /**
     * 年齢が、引数に指定された値になるまで待ちます。
     * 
     * @param numberPicker 年齢が表示されるNumberPicker
     * @param expectedAge
     */
    private void waitUntilAgeToBe(WebElement numberPicker, int expectedAge) throws Exception {
        WebElement ageTextField = numberPicker.findElement(By.className(WIDGET_EDIT_TEXT));
        mWait.until(ExpectedConditions.textToBePresentInElement(ageTextField, String.valueOf(expectedAge)));
        TimeUnit.SECONDS.sleep(1);
    }

    /**
     * NumberPicker内の年齢値を1つ現象させます。
     * 既に年齢値が最小値に達しているときは何もしません。
     */
    private void decrementCurrentAge(WebElement numberPicker) throws Exception {
        int currentAge = getCurrentAge(numberPicker);
        if (currentAge == AGE_MIN) {
            return;
        }
        WebElement decButton;
        if (currentAge == AGE_MAX) {
            // 現在の年齢値が最大値のときは、「年齢を増やす」ボタンが存在せず、
            // 「年齢を減らす」ボタンしかない。
            decButton = numberPicker.findElement(By.className(WIDGET_BUTTON));
        } else {
            // 現在の年齢値が最大値でも最小値でも無いときは、「年齢を減らす」ボタンと、
            // 「年齢を増やす」ボタンの両方が存在し、1つめのボタン(indexは0)が「年齢を減らす」ボタンとなる。
            decButton = numberPicker.findElements(By.className(WIDGET_BUTTON)).get(0);
        }
        decButton.click();
        if (getCurrentAge(numberPicker) == currentAge - 1) {
            // click()で数字が1つ増加した場合はreturnする。
            return;
        }
        // click()で数字が減少しない場合はtap()を試みる。Android 5.0以上で頻発する。
        // ボタンのタップ前後にウェイトを入れないと失敗することがある。
        TimeUnit.SECONDS.sleep(1);
        ((MobileElement) decButton).tap(1, 10);
        waitUntilAgeToBe(numberPicker, currentAge - 1);
    }

    /**
     * 「性別」欄を選択する。
     * 引数にnullが指定された場合には何もしない。
     * 
     * @param gender 選択したい性別。
     * @return この画面
     */
    public DetailPage selectGender(Gender gender) {
        /*
         * ---------------------------
         * RadioGroup (contentDescription="gender segmentedcontrol")
         * ├RadioButton ("男性")
         * └RadioButton ("女性")
         * ---------------------------
         * というレイアウトになっているため、
         * 「contentDescrption属性が"gender segmentedcontrol"」のコンポーネント配下で、
         * 「男性」または「女性」と表示されているコンポーネントを検索する。
         */
        if (gender != null) {
            AndroidElement genderRadioGroup = (AndroidElement) mDriver.findElement(MobileBy.AccessibilityId("gender segmentedcontrol"));
            // Appiumでは、コンポーネントのtext属性を検索するAPIは用意されていない。
            // その代わり、uiautomatorの検索条件式を解釈する仕組みがあるので、その仕組みを用いる。
            //
            // 現時点ではfindElement(By.name(...))で、text属性を検索できるようだが、
            // 公式ドキュメントでは、1.0で当該機能は削除されたことになっており、使わない方が良い。
            // https://github.com/appium/appium/blob/master/docs/en/advanced-concepts/migrating-to-1-0.md#new-locator-strategies
            String uiautomatorCriteria = String.format("new UiSelector().text(\"%s\")", gender.getRadioButtonText());
            WebElement genderRadioButton = genderRadioGroup.findElement(MobileBy.AndroidUIAutomator(uiautomatorCriteria));
            genderRadioButton.click();
        }
        return this;
    }

    /**
     * 入力内容を保存し、プレビュー画面に遷移する。
     * 
     * @return プレビュー画面
     */
    public PreviewPage saveAndPreview() {
        mDriver.findElement(MobileBy.AccessibilityId("preview")).click();
        PreviewPage newPage = new PreviewPage(mDriver);
        assertTrue(newPage.waitUntilLoad());
        return newPage;
    }

}
