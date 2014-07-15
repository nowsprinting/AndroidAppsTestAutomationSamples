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

package com.nowsprinting.hellotesting.app;

import android.os.Bundle;
import android.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.RadioGroup;

import com.nowsprinting.hellotesting.app.models.Customer;
import com.nowsprinting.hellotesting.app.models.Gender;

/**
 * A fragment representing a single Customer detail screen.
 * This fragment is either contained in a {@link CustomerListActivity}
 * in two-pane mode (on tablets) or a {@link CustomerDetailActivity}
 * on handsets.
 */
public class CustomerDetailFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";

    /**
     * The dummy content this fragment is presenting.
     */
    private Customer mItem;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public CustomerDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            mItem = CustomerContent.ITEM_MAP.get(getArguments().getString(ARG_ITEM_ID));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_customer_detail, container, false);

        //名前
        EditText nameEditText = (EditText)rootView.findViewById(R.id.name);
        nameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mItem.setName(s.toString());
            }
            @Override
            public void afterTextChanged(Editable s) {}
        });

        //メール
        EditText mailEditText = (EditText)rootView.findViewById(R.id.email);
        mailEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mItem.setMail(s.toString());
            }
            @Override
            public void afterTextChanged(Editable s) {}
        });

        //性別
        RadioGroup genderRadio = (RadioGroup)rootView.findViewById(R.id.gender);
        genderRadio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId==R.id.genderFemale){
                    mItem.setGender(Gender.GenderFemale);
                }else{
                    mItem.setGender(Gender.GenderMale);
                }
            }
        });

        //年齢
        NumberPicker agePicker = (NumberPicker)rootView.findViewById(R.id.agePicker);
        agePicker.setMaxValue(120);
        agePicker.setMinValue(4);
        agePicker.setWrapSelectorWheel(false);
        agePicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener(){
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                mItem.setAge(newVal);
            }
        });

        // Show the dummy content as text in a TextView.
        if (mItem != null) {
            if(mItem.getName()!=null){
                nameEditText.setText(mItem.getName());
            }
            if(mItem.getMail()!=null){
                mailEditText.setText(mItem.getMail());
            }
            if(mItem.getGender()==Gender.GenderMale){
                genderRadio.check(R.id.genderMale);
            }else{
                genderRadio.check(R.id.genderFemale);
            }
            if(mItem.getAge()!=null && mItem.getAge()>=4){
                agePicker.setValue(mItem.getAge());
            }
        }

        return rootView;
    }
}
