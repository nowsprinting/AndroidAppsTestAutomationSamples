package com.nowsprinting.hellotesting.app.models;

/** 性別 */
enum Gender{
    GenderMale,     //男性
    GenderFemale    //女性
}

/** マーケティング区分 */
enum Division{
    DivisionM1,     //M1層（男性20-34歳）
    DivisionM2,     //M2層（男性35-49歳）
    DivisionM3,     //M3層（男性50歳以上）
    DivisionF1,     //F1層（女性20-34歳）
    DivisionF2,     //F2層（女性35-49歳）
    DivisionF3,     //F3層（女性50歳以上）
    DivisionC,      //C層（男女4-12歳）
    DivisionT,      //T層（男女13-19歳）
    DivisionNone    //分類外
}


/**
 * サンプルの顧客情報管理クラス
 *
 * @author Koji Hasegawa
 */
public class Customer {

    /** 顧客名 */
    String mName;

    /** メールアドレス */
    String mMail;

    /** 性別 */
    Gender mGender;

    /** 年齢 */
    Integer mAge;

    /**
     * Constructor
     *
     * @param name
     */
    public Customer(String name){
        mName = name;
    }

    /**
     * マーケティング区分を返す
     *
     * @retuen 該当するマーケティング区分
     */
    public Division getDivision(){
        if(mAge<=3){
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
