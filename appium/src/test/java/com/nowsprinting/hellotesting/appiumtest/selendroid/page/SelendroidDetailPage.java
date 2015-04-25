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

package com.nowsprinting.hellotesting.appiumtest.selendroid.page;

import static org.junit.Assert.*;
import io.appium.java_client.android.AndroidDriver;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.nowsprinting.hellotesting.appiumtest.appium.page.DetailPage;
import com.nowsprinting.hellotesting.appiumtest.appium.page.Gender;
import com.nowsprinting.hellotesting.appiumtest.appium.page.PreviewPage;

public class SelendroidDetailPage extends DetailPage {

    public SelendroidDetailPage(AndroidDriver driver) {
        super(driver);
    }

    @Override
    public boolean waitUntilLoad() {
        // 名前入力欄が見えるようになるまで待つ。
        try {
            mWait.until(ExpectedConditions.visibilityOfElementLocated(By.name("name textfield")));
        } catch (TimeoutException e) {
            return false;
        }
        return true;
    }

    @Override
    public DetailPage inputName(String name) {
        if (name != null) {
            WebElement nameTextField = mDriver.findElement(By.name("name textfield"));
            nameTextField.sendKeys(name);
        }
        return this;
    }

    @Override
    public DetailPage inputMailAddress(String mailAddress) {
        if (mailAddress != null) {
            WebElement mailTextField = mDriver.findElement(By.name("mail textfield"));
            mailTextField.sendKeys(mailAddress);

        }
        return this;
    }

    @Override
    public DetailPage inputAge(int age) {
        /*
         * ---------------------------
         * NumberPicker
         * ├Button (年齢を1つ減らす)
         * ├EditText (年齢の入力欄: デフォルト値「4」)
         * └Button (年齢を1つ増やす)
         * ---------------------------
         * というレイアウトになっているため、
         * 「NumberPicker配下のEditText」を検索し、文字入力を行う。
         */
        WebElement numberPicker = mDriver.findElement(By.className(WIDGET_NUMBER_PICKER));
        WebElement ageTextField = numberPicker.findElement(By.className(WIDGET_EDIT_TEXT));
        ageTextField.sendKeys(String.valueOf(age));
        ageTextField.click();
        return this;
    }

    @Override
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
            WebElement genderRadioGroup = mDriver.findElement(By.name("gender segmentedcontrol"));
            genderRadioGroup.findElement(By.linkText(gender.getRadioButtonText())).click();
        }
        return this;
    }

    @Override
    public PreviewPage saveAndPreview() {
        mDriver.findElement(By.name("preview")).click();
        PreviewPage newPage = new PreviewPage(mDriver);
        assertTrue(newPage.waitUntilLoad());
        return newPage;
    }

}
