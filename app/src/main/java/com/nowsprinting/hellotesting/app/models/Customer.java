/*
 * Copyright 2014 Koji Hasegawa
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

package com.nowsprinting.hellotesting.app.models;

/**
 * サンプルの顧客情報管理クラス
 *
 * @author Koji Hasegawa
 */
public class Customer {

    /** idのカウンタ */
    static int sLastId = 0;

    /** ユニークなidentifier */
    String mId;

    /** 顧客名 */
    String mName;

    /** メールアドレス */
    String mMail;

    /** 性別 */
    Gender mGender;

    /** 年齢 */
    Integer mAge;

    /**
     * Default Constructor
     */
    public Customer(){
        mId = Integer.toString(sLastId++);
    }

    /**
     * Constructor
     *
     * @param name
     * @param gender
     * @param age
     */
    public Customer(String name, Gender gender, int age){
        mId = Integer.toString(sLastId++);
        mName = name;
        mGender = gender;
        mAge = age;
    }

    /**
     * マーケティング区分を返す
     *
     * @retuen 該当するマーケティング区分
     */
    public Division getDivision(){
        if(mAge==null || mAge<=3){
            return Division.DivisionNone;

        }else if(mAge<=12){
            return Division.DivisionC;

        }else if(mAge<=19){
            return Division.DivisionT;

        }else if(mAge<=34){
            if(mGender==Gender.GenderFemale){
                return Division.DivisionF1;
            }else{
                return Division.DivisionM1;
            }

        }else if(mAge<=49){
            if(mGender==Gender.GenderFemale){
                return Division.DivisionF2;
            }else{
                return Division.DivisionM2;
            }

        }else{
            if(mGender==Gender.GenderFemale){
                return Division.DivisionF3;
            }else{
                return Division.DivisionM3;
            }
        }
    }

    /**
     * 引数のマーケティング区分に一致する顧客か否かをBOOL型で返す
     *
     * @param division マーケティング区分
     * @return YES:この顧客が引数のマーケティング区分に一致する
     */
    public boolean isInDivision(Division division){
        return division==getDivision();
    }

    /**
     * 性別を文字列で返す
     */
    public String getGenderString(){
        switch (mGender) {
            case GenderMale:
                return "男性";
            case GenderFemale:
                return "女性";
        }
        return "";
    }

    /**
     * 年齢を文字列で返す
     */
    public String getAgeString() {
        if(mAge!=null){
            return mAge.toString();
        }else{
            return "NaN";
        }
    }

    /**
     * マーケティング区分を文字列で返す
     */
    public String getDivisionString(){
        switch (getDivision()) {
            case DivisionM1:
                return "M1層";
            case DivisionM2:
                return "M2層";
            case DivisionM3:
                return "M3層";
            case DivisionF1:
                return "F1層";
            case DivisionF2:
                return "F2層";
            case DivisionF3:
                return "F3層";
            case DivisionC:
                return "C層";
            case DivisionT:
                return "T層";
            case DivisionNone:
                return "分類外";
        }
        return "分類外";
    }

    @Override
    public String toString(){
        return mName;
    }

    public String getId() {
        return mId;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        this.mName = name;
    }

    public String getMail() {
        return mMail;
    }

    public void setMail(String mail) {
        this.mMail = mail;
    }

    public Gender getGender() {
        return mGender;
    }

    public void setGender(Gender gender) {
        this.mGender = gender;
    }

    public Integer getAge() {
        return mAge;
    }

    public void setAge(Integer age) {
        this.mAge = age;
    }
}
