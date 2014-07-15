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

import android.test.AndroidTestCase;

/**
 * Customerクラスのテスト
 *
 * package privateのフィールドにアクセスするため、テスト対象クラスと同一packageにしています
 *
 * @author Koji Hasegawa
 */
public class CustomerTest extends AndroidTestCase{

    public void testGetDivision_C層となること_男性4歳(){
        Customer sut = new Customer();
        sut.mGender = Gender.GenderMale;
        sut.mAge = 4;
        assertEquals(Division.DivisionC, sut.getDivision());
    }

    public void testGetDivision_C層となること_男性12歳(){
        Customer sut = new Customer();
        sut.mGender = Gender.GenderMale;
        sut.mAge = 12;
        assertEquals(Division.DivisionC, sut.getDivision());
    }

    public void testGetDivision_C層となること_女性4歳(){
        Customer sut = new Customer();
        sut.mGender = Gender.GenderFemale;
        sut.mAge = 4;
        assertEquals(Division.DivisionC, sut.getDivision());
    }

    public void testGetDivision_C層となること_女性12歳(){
        Customer sut = new Customer();
        sut.mGender = Gender.GenderFemale;
        sut.mAge = 12;
        assertEquals(Division.DivisionC, sut.getDivision());
    }


    public void testGetDivision_T層となること_男性13歳(){
        Customer sut = new Customer();
        sut.mGender = Gender.GenderMale;
        sut.mAge = 13;
        assertEquals(Division.DivisionT, sut.getDivision());
    }

    public void testGetDivision_T層となること_男性19歳(){
        Customer sut = new Customer();
        sut.mGender = Gender.GenderMale;
        sut.mAge = 19;
        assertEquals(Division.DivisionT, sut.getDivision());
    }

    public void testGetDivision_T層となること_女性13歳(){
        Customer sut = new Customer();
        sut.mGender = Gender.GenderFemale;
        sut.mAge = 13;
        assertEquals(Division.DivisionT, sut.getDivision());
    }

    public void testGetDivision_T層となること_女性19歳(){
        Customer sut = new Customer();
        sut.mGender = Gender.GenderFemale;
        sut.mAge = 19;
        assertEquals(Division.DivisionT, sut.getDivision());
    }


    public void testGetDivision_M1層となること_男性20歳(){
        Customer sut = new Customer();
        sut.mGender = Gender.GenderMale;
        sut.mAge = 20;
        assertEquals(Division.DivisionM1, sut.getDivision());
    }

    public void testGetDivision_M1層となること_男性34歳(){
        Customer sut = new Customer();
        sut.mGender = Gender.GenderMale;
        sut.mAge = 34;
        assertEquals(Division.DivisionM1, sut.getDivision());
    }


    public void testGetDivision_M2層となること_男性35歳(){
        Customer sut = new Customer();
        sut.mGender = Gender.GenderMale;
        sut.mAge = 35;
        assertEquals(Division.DivisionM2, sut.getDivision());
    }

    public void testGetDivision_M2層となること_男性49歳(){
        Customer sut = new Customer();
        sut.mGender = Gender.GenderMale;
        sut.mAge = 49;
        assertEquals(Division.DivisionM2, sut.getDivision());
    }


    public void testGetDivision_M3層となること_男性50歳(){
        Customer sut = new Customer();
        sut.mGender = Gender.GenderMale;
        sut.mAge = 50;
        assertEquals(Division.DivisionM3, sut.getDivision());
    }

    public void testGetDivision_M3層となること_男性51歳(){
        Customer sut = new Customer();
        sut.mGender = Gender.GenderMale;
        sut.mAge = 51;
        assertEquals(Division.DivisionM3, sut.getDivision());
    }


    public void testGetDivision_F1層となること_女性20歳(){
        Customer sut = new Customer();
        sut.mGender = Gender.GenderFemale;
        sut.mAge = 20;
        assertEquals(Division.DivisionF1, sut.getDivision());
    }

    public void testGetDivision_F1層となること_女性34歳(){
        Customer sut = new Customer();
        sut.mGender = Gender.GenderFemale;
        sut.mAge = 34;
        assertEquals(Division.DivisionF1, sut.getDivision());
    }


    public void testGetDivision_F2層となること_女性35歳(){
        Customer sut = new Customer();
        sut.mGender = Gender.GenderFemale;
        sut.mAge = 35;
        assertEquals(Division.DivisionF2, sut.getDivision());
    }

    public void testGetDivision_F2層となること_女性49歳(){
        Customer sut = new Customer();
        sut.mGender = Gender.GenderFemale;
        sut.mAge = 49;
        assertEquals(Division.DivisionF2, sut.getDivision());
    }


    public void testGetDivision_F3層となること_女性50歳(){
        Customer sut = new Customer();
        sut.mGender = Gender.GenderFemale;
        sut.mAge = 50;
        assertEquals(Division.DivisionF3, sut.getDivision());
    }

    public void testGetDivision_F3層となること_女性51歳(){
        Customer sut = new Customer();
        sut.mGender = Gender.GenderFemale;
        sut.mAge = 51;
        assertEquals(Division.DivisionF3, sut.getDivision());
    }


    public void testGetDivision_分類外となること_男性3歳(){
        Customer sut = new Customer();
        sut.mGender = Gender.GenderMale;
        sut.mAge = 3;
        assertEquals(Division.DivisionNone, sut.getDivision());
    }

    public void testGetDivision_分類外となること_女性3歳(){
        Customer sut = new Customer();
        sut.mGender = Gender.GenderFemale;
        sut.mAge = 3;
        assertEquals(Division.DivisionNone, sut.getDivision());
    }


    public void testGetGenderString_男性(){
        Customer sut = new Customer();
        sut.mGender = Gender.GenderMale;
        assertEquals("男性", sut.getGenderString());
    }

    public void testGetGenderString_女性(){
        Customer sut = new Customer();
        sut.mGender = Gender.GenderFemale;
        assertEquals("女性", sut.getGenderString());
    }


    public void testGetDivisionString_F1層(){
        Customer sut = new Customer();
        sut.mGender = Gender.GenderFemale;
        sut.mAge = 20;
        assertEquals("F1層", sut.getDivisionString());
    }

    public void testGetDivisionString_M3層(){
        Customer sut = new Customer();
        sut.mGender = Gender.GenderMale;
        sut.mAge = 50;
        assertEquals("M3層", sut.getDivisionString());
    }

}
