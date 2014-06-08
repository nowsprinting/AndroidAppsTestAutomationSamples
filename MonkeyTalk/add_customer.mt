#顧客登録と検証をパラメタライズ
Vars * Define name mail gender age division

#顧客を追加
Label add tap

#顧客情報を入力
TextArea "name textfield" enterText ${name}
TextArea "mail textfield" enterText ${mail}
ButtonSelector "gender segmentedcontrol" Select ${gender}
View "age pickerview" tap
Input #3 enterText ${age} enter

#NumberPickerへの入力を確定させるためにTap
View "age pickerview" tap

#Previewで表示内容を確認
Label preview tap
View name Verify ${name}
View gender Verify ${gender}
View age Verify ${age}
View division Verify ${division}

#Detailに戻る
Device * Back

#Masterに戻る（画面遷移直後はボタンが反応しないためVerify後にTapする）
Label "Customer Detail" Verify "Customer Detail"
Device * Back

