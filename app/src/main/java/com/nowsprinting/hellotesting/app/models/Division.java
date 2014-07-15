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
 * マーケティング区分
 */
public enum Division {
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
