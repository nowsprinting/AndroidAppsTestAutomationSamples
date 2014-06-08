package com.nowsprinting.hellotesting.app;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.RadioButton;
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
        NumberPicker agePicker = (NumberPicker)rootView.findViewById(R.id.agePicker);
        agePicker.setMaxValue(120);
        agePicker.setMinValue(4);

        // Show the dummy content as text in a TextView.
        if (mItem != null) {
            if(mItem.getName()!=null){
                ((EditText)rootView.findViewById(R.id.name)).setText(mItem.getName());
            }
            if(mItem.getMail()!=null){
                ((EditText)rootView.findViewById(R.id.email)).setText(mItem.getMail());
            }
            if(mItem.getGender()==Gender.GenderMale){
                ((RadioGroup)rootView.findViewById(R.id.gender)).check(R.id.genderMale);
            }else{
                ((RadioGroup)rootView.findViewById(R.id.gender)).check(R.id.genderFemale);
            }
            if(mItem.getAge()>=4){
                agePicker.setValue(mItem.getAge());
            }
        }

        return rootView;
    }
}
