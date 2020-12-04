/**
 * Full Name: Ibrahim Ali
 * Student ID: 301022172
 * Section: COMP 304 - 002
 * */
package ibrahim.ali.s301022172.ui.SettingsFragment;

import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import ibrahim.ali.s301022172.R;

public class AliSet extends Fragment implements AdapterView.OnItemSelectedListener{


    Button btnSave;
    RadioButton yellow,red,green;
    RadioButton hr12,hr24;
    Spinner spinner;
    Switch portrait;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.ali_set, container, false);

        yellow = (RadioButton) root.findViewById(R.id.ibrahimYellowRadioBtn);
        red = (RadioButton) root.findViewById(R.id.ibrahimRedRadioBtn);
        green = (RadioButton) root.findViewById(R.id.ibrahimGreenRadioBtn);

        hr12 = (RadioButton) root.findViewById(R.id.ibrahimHr12RadioBtn);
        hr24 = (RadioButton) root.findViewById(R.id.ibrahimHr24RadioBtn);

        portrait = (Switch) root.findViewById(R.id.ibrahimPortraitMode);

        spinner = (Spinner) root.findViewById(R.id.ibrahimFontSizeSpinner);
        spinner.setOnItemSelectedListener(this);

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.fonts_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

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

                editor.putString("font_selection",spinner.getSelectedItem().toString());

                editor.putBoolean("portrait_selection", portrait.isChecked());

                editor.commit();
            }
        });

        String color_selection = sharedPreferences.getString("color_selection","empty");
        String hour_selection = sharedPreferences.getString("hour_selection","empty");
        String font_selection = sharedPreferences.getString("font_selection","empty");
        Boolean portrait_selection = sharedPreferences.getBoolean("portrait_selection",false);

        switch(color_selection){
            case "yellow":
                yellow.setChecked(true);
                break;
            case "red":
                red.setChecked(true);
                break;
            case "green":
                green.setChecked(true);
                break;
        }

        if(portrait_selection){
            portrait.setChecked(true);
        }else{
            portrait.setChecked(false);
        }

        switch(hour_selection){
            case "12":
                hr12.setChecked(true);
                break;
            case "24":
                hr24.setChecked(true);
                break;
        }

        switch(font_selection){
            case "12":
                spinner.setSelection(0);
                break;
            case "13":
                spinner.setSelection(1);
                break;
            case "14":
                spinner.setSelection(2);
                break;
            case "15":
                spinner.setSelection(3);
                break;
        }


        return root;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}