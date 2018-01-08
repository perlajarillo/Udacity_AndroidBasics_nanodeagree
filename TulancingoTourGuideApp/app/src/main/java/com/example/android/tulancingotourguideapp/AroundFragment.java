package com.example.android.tulancingotourguideapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

/**
 * A simple {@link Fragment} subclass.
 */
public class AroundFragment extends Fragment {
    View popupView;
    PopupWindow popupWindow;
    LayoutInflater layoutInflater;
    Button btnClose;
    ImageView imgResource;
    TextView txtDesc;
    TextView txtTitle;

    public AroundFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_around, container, false);
        final ArrayList<Topic> topics = new ArrayList<Topic>();
        topics.add(new Topic(getString(R.string.title_acaxochitlan), getString(R.string.short_desc_acaxochitlan), getString(R.string.desc_acaxochitlan), R.drawable.around_acaxochitlan));
        topics.add(new Topic(getString(R.string.title_apulco), getString(R.string.short_desc_apulco), getString(R.string.desc_apulco), R.drawable.around_balloons));
        topics.add(new Topic(getString(R.string.title_prismas), getString(R.string.short_desc_prismas), getString(R.string.desc_prismas), R.drawable.around_prismas));
        topics.add(new Topic(getString(R.string.title_real), getString(R.string.short_desc_real), getString(R.string.desc_real), R.drawable.around_real));
        topics.add(new Topic(getString(R.string.title_sanmiguel), getString(R.string.short_desc_sanmiguel), getString(R.string.desc_sanmiguel), R.drawable.around_sanmiguel));
        topics.add(new Topic(getString(R.string.title_tejocotal), getString(R.string.short_desc_tejocotal), getString(R.string.desc_tejocotal), R.drawable.around_tejocotal));
        TopicAdapter adapter = new TopicAdapter(getActivity(), topics, R.color.category_around);
        ListView listView = (ListView) rootView.findViewById(R.id.list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Topic itemWord = topics.get(position);
                layoutInflater = (LayoutInflater) getContext().getSystemService(LAYOUT_INFLATER_SERVICE);
                //Calling the popup_layout
                popupView = layoutInflater.inflate(R.layout.layout_popup, null);
                popupWindow = new PopupWindow(popupView, RadioGroup.LayoutParams.WRAP_CONTENT,
                        RadioGroup.LayoutParams.WRAP_CONTENT);
                imgResource = (ImageView) popupView.findViewById(R.id.imageViewPu);
                txtDesc = (TextView) popupView.findViewById(R.id.description_text_view_pu);
                txtTitle = (TextView) popupView.findViewById(R.id.title_text_view_pu);
                imgResource.setImageResource(itemWord.getImageResourceId());
                txtDesc.setText(itemWord.getDescription());
                txtTitle.setText(itemWord.getTitle());
                btnClose = (Button) popupView.findViewById(R.id.btn_close);
                btnClose.setOnClickListener(new Button.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        popupWindow.dismiss();
                    }
                });
                popupWindow.showAtLocation(parent, 100, 250, 150);
            }

        });

        return rootView;
    }

}
