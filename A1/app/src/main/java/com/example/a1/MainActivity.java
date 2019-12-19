package com.example.a1;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    //Declaring Class Variables
    private Button button ;
    private IntentFilter filter;
    private Receiver1 receiver;

    //Initializing Permissions and Intents and App 2 package
    private static final String KABOOM_PERMISSION = "edu.uic.cs478.s19.kaboom" ;
    private static final String WEBSITE_INTENT = "edu.uic.cs478.s19.kaboom.website";
    private static final String APP2_PACKAGE = "com.example.a2";

    //Declaring TAG for logs
    private static final String TAG = "Application1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Setting up listener for Button
        button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "Pressed Button");
                checkPermissionAndBroadcast();
            }
        });

    }

    //Check whether app has received permission declared in App 3
    //If not request user to grant permission
    //On receiving permission register the broadcast receiver and launch App 2
    private void checkPermissionAndBroadcast() {
        if (ContextCompat.checkSelfPermission(this, KABOOM_PERMISSION) == PackageManager.PERMISSION_GRANTED) {
            filter = new IntentFilter(WEBSITE_INTENT) ;
            filter.setPriority(1) ;
            receiver = new Receiver1() ;
            registerReceiver(receiver, filter) ;

            //Launch App 2 using App 2's package name
            Intent launchIntent = getPackageManager().getLaunchIntentForPackage(APP2_PACKAGE);
            if (launchIntent != null) { //Check if intent is not null
                launchIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(launchIntent);
            }
        }
        else {
            //Request user to grant permission
            ActivityCompat.requestPermissions(this, new String[]{KABOOM_PERMISSION}, 1) ;
        }
    }

    //Check permission result and perform corresponding actions
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 1: {

                //Permission was granted. Launch App 2. Register broadcast receiver
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    filter = new IntentFilter(WEBSITE_INTENT) ;
                    filter.setPriority(1) ;
                    receiver = new Receiver1() ;
                    registerReceiver(receiver, filter) ;

                    //Launch App 2 using App 2's package name
                    Intent launchIntent = getPackageManager().getLaunchIntentForPackage(APP2_PACKAGE);
                    if (launchIntent != null) { //Check if intent is not null
                        launchIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(launchIntent);
                    }

                    //Permission was denied. Show error message
                } else if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_DENIED){
                    Toast.makeText(this, "Failed to launch App2. User permission denied.", Toast.LENGTH_LONG).show() ;
                }
                //User cancelled permission request.
                else {
                    Toast.makeText(this, "Permission required to launch App2. Press button again.", Toast.LENGTH_LONG).show() ;
                }
                return;
            }
        }
    }

    //Unregister broadcast receiver on closing the app
    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver) ;
    }

    //Broadcast receiver to respond to broadcast message received from App 3
    public class Receiver1 extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.i(TAG, "App 1 received from App3") ;

            //Check if URL of phone was passed in intent and open the corresponding web-page in browser.
            //If not raise a toast message that phone was not selected in App 3
            if(intent.getStringExtra("webURL").isEmpty()){
                Toast.makeText(context, "No phone was selected in App3.", Toast.LENGTH_LONG).show() ;
            }else {
                String url = intent.getStringExtra("webURL");
                Intent webIntent = new Intent(MainActivity.this,WebActivity.class);
                webIntent.putExtra("url",url);
                finish();
                startActivity(webIntent);
            }
        }
    }
}
