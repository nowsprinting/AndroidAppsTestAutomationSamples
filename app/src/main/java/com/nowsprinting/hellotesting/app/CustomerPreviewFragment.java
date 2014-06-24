package com.nowsprinting.hellotesting.app;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.nowsprinting.hellotesting.app.models.Customer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.IllegalFormatException;

public class CustomerPreviewFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";

    /**
     * The dummy content this fragment is presenting.
     */
    private Customer mItem;

    public CustomerPreviewFragment() {
        // Required empty public constructor
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
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_customer_preview, container, false);

        // Setup WebView
        try {
            // Read template
            InputStream rawResourceStream = getResources().openRawResource(R.raw.customer_preview);
            InputStreamReader inputStreamReader = new InputStreamReader(rawResourceStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuilder template = new StringBuilder(); //readStream(openRawResource);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                template.append(line);
            }
            bufferedReader.close();
            inputStreamReader.close();
            rawResourceStream.close();

            // Set values
            String name = mItem.getName();
            String mail = mItem.getMail();
            String gender = mItem.getGenderString();
            String age = mItem.getAgeString();
            String division = mItem.getDivisionString();
            String html = String.format(template.toString(), name, mail, gender, age, division);

            // Set html
            WebView webView = (WebView)rootView.findViewById(R.id.previewWebView);
            webView.loadDataWithBaseURL(null, html, "text/html", null, null);

        }catch(IOException e) {

        }catch(IllegalFormatException e) {

        }finally {

        }

        return rootView;
    }
}
