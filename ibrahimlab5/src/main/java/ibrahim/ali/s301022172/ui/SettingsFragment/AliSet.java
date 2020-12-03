package ibrahim.ali.s301022172.ui.SettingsFragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import ibrahim.ali.s301022172.R;

public class AliSet extends Fragment {


    Button btnSave;
    RadioButton yellow,red,green;
    RadioButton hr12,hr24;
    RadioButton font12,font13,font14,font15;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.ali_set, container, false);

        yellow = (RadioButton) root.findViewById(R.id.ibrahimYellowRadioBtn);
        red = (RadioButton) root.findViewById(R.id.ibrahimRedRadioBtn);
        green = (RadioButton) root.findViewById(R.id.ibrahimGreenRadioBtn);

        hr12 = (RadioButton) root.findViewById(R.id.ibrahimHr12RadioBtn);
        hr24 = (RadioButton) root.findViewById(R.id.ibrahimHr24RadioBtn);

        font12 = (RadioButton) root.findViewById(R.id.ibrahimFont12RadioBtn);
        font13 = (RadioButton) root.findViewById(R.id.ibrahimFont13RadioBtn);
        font14 = (RadioButton) root.findViewById(R.id.ibrahimFont14RadioBtn);
        font15 = (RadioButton) root.findViewById(R.id.ibrahimFont15RadioBtn);

        btnSave = (Button) root.findViewById(R.id.ibrahimSharedPrefSaveBtn);

        final SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(getContext());

        btnSave.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                SharedPreferences.Editor editor = sharedPreferences.edit();

                if(yellow.isChecked()){
                    editor.putString("color_selection", "yellow");
                }else if(red.isChecked()){
                    editor.putString("color_selection", "red");
                }else if(green.isChecked()){
                    editor.putString("color_selection", "green");
                }

                if(hr12.isChecked()){
                    editor.putString("hour_selection", "12");
                }else if(hr24.isChecked()){
                    editor.putString("hour_selection", "24");
                }

                if(font12.isChecked()){
                    editor.putString("font_selection", "12");
                }else if(font13.isChecked()){
                    editor.putString("font_selection", "13");
                }else if(font14.isChecked()){
                    editor.putString("font_selection", "14");
                }else if(font15.isChecked()){
                    editor.putString("font_selection", "15");
                }

                editor.commit();
            }
        });

        return root;
    }
}