package com.nowsprinting.hellotesting.app;

import android.app.Activity;
import android.os.Bundle;

public class CustomerPreviewActivity extends Activity {

    String mCustomerId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_preview);

        // Show the Up button in the action bar.
        getActionBar().setDisplayHomeAsUpEnabled(true);

        // savedInstanceState is non-null when there is fragment state
        // saved from previous configurations of this activity
        // (e.g. when rotating the screen from portrait to landscape).
        // In this case, the fragment will automatically be re-added
        // to its container so we don't need to manually add it.
        // For more information, see the Fragments API guide at:
        //
        // http://developer.android.com/guide/components/fragments.html
        //
        if (savedInstanceState == null) {
            mCustomerId = getIntent().getStringExtra(CustomerPreviewFragment.ARG_ITEM_ID);

            // Create the detail fragment and add it to the activity
            // using a fragment transaction.
            Bundle arguments = new Bundle();
            arguments.putString(CustomerPreviewFragment.ARG_ITEM_ID, mCustomerId);
            CustomerPreviewFragment fragment = new CustomerPreviewFragment();
            fragment.setArguments(arguments);
            getFragmentManager().beginTransaction()
                    .add(R.id.customer_preview_container, fragment)
                    .commit();
        }
    }

}
