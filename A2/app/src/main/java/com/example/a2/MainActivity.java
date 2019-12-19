package com.example.a2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    //Declaring class fields
    private Button button ;
    private IntentFilter filter;
    private Receiver2 receiver;

    //Initializing constants
    private static final String KABOOM_PERMISSION = "edu.uic.cs478.s19.kaboom" ;
    private static final String WEBSITE_INTENT = "edu.uic.cs478.s19.kaboom.website";
    private static final String TAG = "Application2";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Setting click listener to button
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
    //On receiving permission register the broadcast receiver and launch App 3
    private void checkPermissionAndBroadcast() {
        if (ContextCompat.checkSelfPermission(this, KABOOM_PERMISSION) == PackageManager.PERMISSION_GRANTED) {
            filter = new IntentFilter(WEBSITE_INTENT) ;
            filter.setPriority(10) ;
            receiver = new Receiver2() ;
            registerReceiver(receiver, filter) ;

            //Launch App 2 using App 3's package name
            Intent launchIntent = getPackageManager().getLaunchIntentForPackage("com.example.a3");
            if (launchIntent != null) { //Check if intent is not null
                launchIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(launchIntent);//null pointer check in case package name was not found
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

                //Permission was granted. Launch App 3. Register broadcast receiver
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    filter = new IntentFilter(WEBSITE_INTENT) ;
                    filter.setPriority(10) ;
                    receiver = new Receiver2() ;
                    registerReceiver(receiver, filter) ;

                    //Launch App 2 using App 3's package name
                    Intent launchIntent = getPackageManager().getLaunchIntentForPackage("com.example.a3");
                    if (launchIntent != null) { //Check if intent is not null
                        launchIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(launchIntent);//null pointer check in case package name was not found
                    }

                    //Permission was denied. Show error message
                } else if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_DENIED){
                    Toast.makeText(this, "Failed to launch App3. User permission denied.", Toast.LENGTH_LONG).show() ;
                    finishAffinity();
                    System.exit(0);
                }
                //User cancelled permission request.
                else {
                    Toast.makeText(this, "Permission required to launch App3. Press button again.", Toast.LENGTH_LONG).show() ;
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
    public class Receiver2 extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.i(TAG, "App2 received from App3") ;
            Toast.makeText(context, "This is a toast message from A2! ",
                    Toast.LENGTH_LONG).show() ;
        }
    }
}
