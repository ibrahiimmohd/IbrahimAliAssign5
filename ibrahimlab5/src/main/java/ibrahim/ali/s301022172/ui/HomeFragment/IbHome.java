/**
 * Full Name: Ibrahim Ali
 * Student ID: 301022172
 * Section: COMP 304 - 002
 * */
package ibrahim.ali.s301022172.ui.HomeFragment;

import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import ibrahim.ali.s301022172.R;

public class IbHome extends Fragment implements AdapterView.OnItemSelectedListener{

    Spinner spinner;
    String hourFormat;

    @RequiresApi(api = Build.VERSION_CODES.N)
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.ib_home, container, false);

        spinner = (Spinner) root.findViewById(R.id.ibrahimSpinnerInsert);
        spinner.setOnItemSelectedListener(this);

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.courses_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        TextView dateTv = (TextView) root.findViewById(R.id.ibrahimCurrentDate);
        dateTv.setText(date);

        TextView timeTv = (TextView) root.findViewById(R.id.ibrahimCurrentTime);
        TextView fullnameTv = (TextView) root.findViewById(R.id.ibrahimFullNameTv);
        TextView studentNumTv = (TextView) root.findViewById(R.id.ibrahimStudentNumTv);


        final SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(getContext());

        String color_selection = sharedPreferences.getString("color_selection","empty");
        String hour_selection = sharedPreferences.getString("hour_selection","empty");
        String font_selection = sharedPreferences.getString("font_selection","empty");
        Boolean portrait_selection = sharedPreferences.getBoolean("portrait_selection",false);

        Log.d("Settings",color_selection + " " + hour_selection + " " + font_selection + " " +portrait_selection);

        switch(color_selection){
            case "yellow":
                root.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.yellow));
                break;
            case "red":
                root.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.red));
                break;
            case "green":
                root.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.green));
                break;
        }

        timeTv.setTextSize(TypedValue.COMPLEX_UNIT_SP,Integer.parseInt(font_selection));
        dateTv.setTextSize(TypedValue.COMPLEX_UNIT_SP,Integer.parseInt(font_selection));
        fullnameTv.setTextSize(TypedValue.COMPLEX_UNIT_SP,Integer.parseInt(font_selection));
        studentNumTv.setTextSize(TypedValue.COMPLEX_UNIT_SP,Integer.parseInt(font_selection));

        if(portrait_selection){
            getActivity().setRequestedOrientation(
                    ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            Toast.makeText(getContext(),"Portriat Mode is On",Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(getContext(),"Portriat Mode is Off",Toast.LENGTH_SHORT).show();
        }

        switch(hour_selection){
            case "12":
                hourFormat = "hh:mm:ss a";
                break;
            case "24":
                hourFormat = "HH:mm:ss";
                break;
        }

        Thread thread = new Thread(){
            @Override
            public void run(){
                try{
                    while(!isInterrupted()){
                        Thread.sleep(1000);
                        getActivity().runOnUiThread(new Runnable(){
                            @Override
                            public void run() {
                                Calendar calendar = Calendar.getInstance();
                                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(hourFormat);
                                String currenttime = simpleDateFormat.format(calendar.getTime());
                                timeTv.setText(currenttime);
                            }
                        });
                    }
                }catch (Exception e){

                }
            }
        };

        thread.start();

        return root;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
