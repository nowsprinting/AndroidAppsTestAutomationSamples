When(/^choose age as (\d+)$/) do |age|
  changeAgePicker(age)
end


When(/^go preview$/) do
  touchButtonPreview
end


When(/^wait for preview screen$/) do
  waitForPreviewScreen
end


Then(/^I should see name as "(.*?)"$/) do |expected|
  validateNameOnWebView(expected)
end


Then(/^I should see "(.*?)" at "(.*?)"$/) do |expected, name|
  validateWebViewItem(expected, name)
end


When(/^I enter customer information and preview it as follows:$/) do |table|

  table.hashes().each do |raw_data|

    # Input on Customer Detail
    enterText("name",  raw_data["name"])
    enterText("email", raw_data["email"])
    chooseRadioButton(raw_data["gender"])

    unless raw_data["age"].empty?
      changeAgePicker(raw_data["age"])
    end

    # Execute SUT
    touchButtonPreview
    waitForPreviewScreen

    # Validate on Preview
    validateNameOnWebView(raw_data["name"])
    validateWebViewItem(raw_data["email"], "mail")
    validateWebViewItem(raw_data["previewGender"], "gender")

    unless raw_data["age"].empty?
      validateWebViewItem(raw_data["age"], "age")
    else
      validateWebViewItem("NaN", "age")
    end

    validateWebViewItem(raw_data["division"], "division")

    # Move back to Customer Detail and tear down
    press_back_button
    waitForCustomerDetailScreen
    clearText("name")
    clearText("email")

  end

end


@@baseDir = "./report/bdd/"
Then(/^take photo$/) do
  screenshotDir  = @@baseDir + FeatureNameMemory.feature_name
  screenshotName = FeatureNameMemory.scenario.name

  FileUtils.mkdir_p(screenshotDir) unless FileTest.exists?(screenshotDir)
  screenshot_embed({:prefix => screenshotDir + "/", :name => screenshotName, :label => screenshotName})
end


def enterText(id, text)
  enter_text("android.widget.EditText id:'#{id}'", text)
end


def chooseRadioButton(id)
  if ("male" == id)
    tap_when_element_exists("RadioButton id:'genderMale'")
  else
    tap_when_element_exists("RadioButton id:'genderFemale'")
  end
end


def changeAgePicker(age)
  query("NumberPicker id:'agePicker'", :setValue=>age.to_i)
  pan("NumberPicker id:'agePicker'", :up)
  pan("NumberPicker id:'agePicker'", :down)
  # ê›íËíÜÇ…âÊñ Ç™ëJà⁄ÇµÇ»Ç¢ÇÊÇ§ÅAîOÇÃÇΩÇﬂ1ïbë“ã@ÇµÇ‹Ç∑ÅB
  sleep 1
end


def touchButtonPreview
  touch("ActionMenuItemView id:'button_preview'")
end


def waitForPreviewScreen
  wait_for_element_exists("webView css:'h4[name=name]'")
end


def validateNameOnWebView(expected)
  actual = query("webView css:'h4[name=name]'", "textContent")
  if (actual.length != 1 || expected != actual[0])
    raise "Expected value is #{expected} but #{actual} returned!"
  end
end


def validateWebViewItem(expected, name)
  actual = query("webView css:\'div[name=" + name + "]\'", "textContent")
  if (actual.length != 1 || expected != actual[0])
    raise "Expected value is #{expected} but #{actual} returned!"
  end
end


def clearText(id)
  clear_text("android.widget.EditText id:'#{id}'")
end


def waitForCustomerDetailScreen
  wait_for_element_exists("EditText id:'name'")
end
