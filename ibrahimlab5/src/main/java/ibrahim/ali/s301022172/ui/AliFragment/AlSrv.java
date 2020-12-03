package ibrahim.ali.s301022172.ui.AliFragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import ibrahim.ali.s301022172.R;

public class AlSrv extends Fragment {

    private TextView txtDisplayWeather;
    private EditText zipCode;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.al_srv, container, false);

        txtDisplayWeather = (TextView) root.findViewById(R.id.ibrahimWeatherDisplay);

        Button btn = (Button) root.findViewById(R.id.ibrahimWebServiceBtn);
        btn.setOnClickListener(v -> {

            zipCode = (EditText) root.findViewById(R.id.ibrahimZipcodeInsert);

            if(zipCode.getText().toString().isEmpty() == true || zipCode.getText().toString().length() != 5){
                zipCode.setError("Please Insert 5 digits");
            }else{
                String url = "https://api.openweathermap.org/data/2.5/weather?zip="+zipCode.getText().toString()+",us&appid=38bad841f49c781c1a8f6a2b957b35bc";
                new ReadJSONFeedTask().execute(url);
            }

        });

        return root;
    }

    public String readJSONFeed(String address) {
        URL url = null;
        try {
            url = new URL(address);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        };
        StringBuilder stringBuilder = new StringBuilder();
        HttpURLConnection urlConnection = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            InputStream content = new BufferedInputStream(
                    urlConnection.getInputStream());
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(content));
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            urlConnection.disconnect();
        }
        return stringBuilder.toString();
    }

    private class ReadJSONFeedTask extends AsyncTask<String, Void, String> {
        protected String doInBackground(String... urls) {
            return readJSONFeed(urls[0]);
        }
        protected void onPostExecute(String result) {
            try {
                Log.d("URL",result);

                JSONObject weatherJson = new JSONObject(result);
                String strResults="Weather\n";

                //
                JSONObject jsonObject1= weatherJson.getJSONObject("coord");
                strResults +="\nLon: "+jsonObject1.getString("lon");
                strResults +="\nLat: "+jsonObject1.getString("lat");

                //
                JSONObject jsonObject2= weatherJson.getJSONObject("main");
                strResults +="\nHumidity: "+jsonObject2.getString("humidity");

                //
                String jsonObject3= weatherJson.getString("name");
                strResults +="\nName: "+ jsonObject3;

                strResults +="\nZip code: "+ zipCode.getText().toString();

                txtDisplayWeather.setText(strResults);

            } catch (Exception e) {
                e.printStackTrace();
                zipCode.setError("Please enter valid zip code");
            }
        }
    }
}