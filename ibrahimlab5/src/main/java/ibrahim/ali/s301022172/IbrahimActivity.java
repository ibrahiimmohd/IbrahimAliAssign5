/**
 * Full Name: Ibrahim Ali
 * Student ID: 301022172
 * Section: COMP 304 - 002
 * */
package ibrahim.ali.s301022172;

import android.Manifest;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class IbrahimActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    ImageButton homeImgBtn;
    DrawerLayout drawer;
    final private int REQUEST_CODE_ASK_PERMISSIONS = 123;
    LocationManager mLocationManager;
    Boolean checkPermission;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);

        NavigationView navigationView = findViewById(R.id.ibrahimNavView);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_ibrahim, R.id.nav_ali, R.id.nav_settings)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.ibrahim_nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        View hView = navigationView.getHeaderView(0);

        homeImgBtn = (ImageButton) hView.findViewById(R.id.ibrahimImgBtn);
        homeImgBtn.setOnClickListener(v -> {

            Spinner mySpinner = (Spinner) findViewById(R.id.ibrahimSpinnerInsert);
            String text = mySpinner.getSelectedItem().toString();

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage(text);
            builder.setCancelable(true);

            builder.setNegativeButton(
                    "Cancel",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });

            AlertDialog alert = builder.create();
            alert.show();
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_help:
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.centennialcollege.com"));
                startActivity(browserIntent);
                return true;
            case R.id.action_location:
                checkPermission = checkSendSmsPermission("LOCATION");
                mLocationManager = (LocationManager) getSystemService(getApplicationContext().LOCATION_SERVICE);
                if (checkPermission && mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    getLastLocation();
                }else{
                    Toast.makeText(this, "Please check location settings", Toast.LENGTH_SHORT).show();
                }
                return true;
            case R.id.action_sms:
                checkPermission = checkSendSmsPermission("SMS");
                if (checkPermission) {
                    sendSMS("6472864816", "Hello There!");
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.ibrahim_nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    protected void sendSMS(String phoneNo, String message) {
        // TODO Auto-generated method stub
        String SENT = "SMS_SENT";
        String DELIVERED = "SMS_DELIVERED";

        PendingIntent sentPI = PendingIntent.getBroadcast(this, 0,
                new Intent(SENT), 0);

        PendingIntent deliveredPI = PendingIntent.getBroadcast(this, 0,
                new Intent(DELIVERED), 0);

        //---when the SMS has been sent---
        registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context arg0, Intent arg1) {
                switch (getResultCode()) {
                    case Activity.RESULT_OK:
                        Snackbar.make(drawer, "SMS sent", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                        break;
                    case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                        Snackbar.make(drawer, "Generic failure", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                        break;
                    case SmsManager.RESULT_ERROR_NO_SERVICE:
                        Snackbar.make(drawer, "No service", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                        break;
                    case SmsManager.RESULT_ERROR_NULL_PDU:
                        Snackbar.make(drawer, "Null PDU", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                        break;
                    case SmsManager.RESULT_ERROR_RADIO_OFF:
                        Snackbar.make(drawer, "Radio off", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                        break;
                }
            }
        }, new IntentFilter(SENT));

        //---when the SMS has been delivered---
        registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context arg0, Intent arg1) {
                switch (getResultCode()) {
                    case Activity.RESULT_OK:
                        Snackbar.make(drawer, "SMS delivered", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                        break;
                    case Activity.RESULT_CANCELED:
                        Snackbar.make(drawer, "SMS not delivered", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                        break;
                }
            }
        }, new IntentFilter(DELIVERED));

        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(phoneNo, null, message, sentPI, deliveredPI);
    }

    //checkSendSmsPermission function
    private Boolean checkSendSmsPermission(String permission) {
        if (permission.equals("SMS")) {
            int hasWriteContactsPermission = 0;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                hasWriteContactsPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS);

                if (hasWriteContactsPermission != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{Manifest.permission.SEND_SMS},
                            REQUEST_CODE_ASK_PERMISSIONS);
                    return false;
                }
            }

        } else if (permission.equals("LOCATION")) {
            int hasWriteContactsPermission = 0;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                hasWriteContactsPermission = ContextCompat.checkSelfPermission(this, ACCESS_FINE_LOCATION);

                if (hasWriteContactsPermission != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                            REQUEST_CODE_ASK_PERMISSIONS);
                    return false;
                }
            }
        }
        return true;
    }

    private void getLastLocation() {
        try {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                return;
            }else{
                Location locationGPS = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                if (locationGPS != null) {
                    double latitude = locationGPS.getLatitude();
                    double longitude = locationGPS.getLongitude();
                    Snackbar.make(drawer,String.format("Latitude: %.4f & Longitude: %.4f", latitude, longitude), Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            Toast.makeText(this, "Location Not Found", Toast.LENGTH_SHORT).show();
        }
    }

    //If back btn pressed, display alert dialog
    @Override
    public void onBackPressed(){
        ImageView image = new ImageView(this);
        image.setImageResource(R.drawable.bird1);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.backPressStr);
        builder.setCancelable(false);
        builder.setTitle(R.string.app_name);
        builder.setView(image);

        builder.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        System.exit(1);
                    }
                });

        builder.setNegativeButton(
                "No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });


        AlertDialog alert = builder.create();
        alert.show();
    }
}