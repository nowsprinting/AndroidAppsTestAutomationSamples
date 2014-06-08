#顧客を追加
Label add tap

#顧客情報を入力
TextArea "name textfield" enterText "Newton Geizler"
ButtonSelector "gender segmentedcontrol" Select 男性
View "age pickerview" tap
Input #3 enterText 35 enter

#NumberPickerへの入力を確定させるためにTap
View "age pickerview" tap

#Previewで表示内容を確認
Label preview tap
View name Verify "Newton Geizler"
View gender Verify 男性
View age Verify 35
View division Verify "M2層"

#Detailに戻る
Device * Back

#Masterに戻る（画面遷移直後はボタンが反応しないためVerify後にTapする）
Label "Customer Detail" Verify "Customer Detail"
Device * Back

