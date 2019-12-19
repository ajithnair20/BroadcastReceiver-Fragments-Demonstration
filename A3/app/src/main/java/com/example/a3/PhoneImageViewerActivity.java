package com.example.a3;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

public class PhoneImageViewerActivity
        extends FragmentActivity
		implements PhoneFragment.ListSelectionListener {

	//Declaring constants and initializing constants
	public static String[] mPhoneArray;
	public static int[] mImageArray;
	public static String[] mURLArray;
	private final ImageFragment mImageFragment = new ImageFragment();
	private android.support.v4.app.FragmentManager mFragmentManager;
	private FrameLayout mPhoneFrameLayout, mImageFrameLayout;
	private static final int MATCH_PARENT = LinearLayout.LayoutParams.MATCH_PARENT;
	private static final String TAG = "PhoneImageViewer";
	private static final String WEBSITE_INTENT = "edu.uic.cs478.s19.kaboom.website";
	private static final String KABOOM_PERMISSION = "edu.uic.cs478.s19.kaboom";
	public int itemIndex=-1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		Log.i(TAG, getClass().getSimpleName() + ": entered onCreate()");

		super.onCreate(savedInstanceState);

		//Setup actionbar and logo
		getActionBar().setDisplayShowHomeEnabled(true);
		getActionBar().setLogo(R.drawable.ic_phonelink_ring_black_24dp);
		getActionBar().setDisplayUseLogoEnabled(true);


		//Initializing cell phone details
		mPhoneArray = new String[]{"Google Pixel 3","Apple IPhone X","One Plus 7 Pro","Xiaomi Redmi Note 8 Pro","Oppo K5","Samsung Galaxy S10e","Motorola Moto G7","LG G5","Nokia 9","Asus Zenfone 6","Honor 10"};
		mImageArray = new int[] {R.drawable.image1,R.drawable.image2,R.drawable.image3,R.drawable.image4,R.drawable.image5,R.drawable.image6,R.drawable.image7,R.drawable.image8,R.drawable.image9,R.drawable.image10,R.drawable.image11};
		mURLArray = new String[]{
				"https://www.gsmarena.com/google_pixel_3-9256.php",
				"https://www.gsmarena.com/apple_iphone_x-8858.php",
				"https://www.gsmarena.com/oneplus_7_pro-9689.php",
				"https://m.gsmarena.com/xiaomi_redmi_note_8_pro-9812.php",
				"https://www.gsmarena.com/oppo_k5-9907.php",
				"https://www.gsmarena.com/samsung_galaxy_s10e-9537.php",
				"https://www.gsmarena.com/motorola_moto_g7-9357.php",
				"https://ww.gsmarena.com/lg_g5-7815.php",
				"https://www.gsmarena.com/nokia_9_pureview-8867.php",
				"https://www.gsmarena.com/asus_zenfone_6_zs630kl-9698.php",
				"https://www.gsmarena.com/honor_10-9157.php"};


		setContentView(R.layout.main);


		//Setting up fragment manager and fragment details
		mPhoneFrameLayout = (FrameLayout) findViewById(R.id.phone_fragment_container);
		mImageFrameLayout = (FrameLayout) findViewById(R.id.image_fragment_container);

		mFragmentManager = getSupportFragmentManager();

		android.support.v4.app.FragmentTransaction fragmentTransaction = mFragmentManager
				.beginTransaction();

		fragmentTransaction.replace(
		        R.id.phone_fragment_container,
				new PhoneFragment());

		fragmentTransaction.commit();

		mFragmentManager.addOnBackStackChangedListener(
				new android.support.v4.app.FragmentManager.OnBackStackChangedListener() {
					public void onBackStackChanged() {
						setLayout();
					}
				});
	}


	//Setting up layout of fragments
	//Setup width of fragments based on the orientation
	private void setLayout() {

		if (!mImageFragment.isAdded()) {
			mPhoneFrameLayout.setLayoutParams(new LinearLayout.LayoutParams(
					MATCH_PARENT, MATCH_PARENT));
			mImageFrameLayout.setLayoutParams(new LinearLayout.LayoutParams(0,
					MATCH_PARENT));
		} else {
            int orientation = this.getResources().getConfiguration().orientation;
            if (orientation == Configuration.ORIENTATION_PORTRAIT) {
                Log.i("orientation","Orientation is porttrait");
				mPhoneFrameLayout.setLayoutParams(new LinearLayout.LayoutParams(0,
                        MATCH_PARENT, 0f));

				mImageFrameLayout.setLayoutParams(new LinearLayout.LayoutParams(0,
                        MATCH_PARENT, 1f));
            } else {
                Log.i("orientation","Orientation is landscape");
				mPhoneFrameLayout.setLayoutParams(new LinearLayout.LayoutParams(0,
                        MATCH_PARENT, 1f));

				mImageFrameLayout.setLayoutParams(new LinearLayout.LayoutParams(0,
                        MATCH_PARENT, 2f));
            }
		}
	}

	//invoked when an item form the phone list is selected
	@Override
	public void onListSelection(int index) {

		itemIndex = index;
		if (!mImageFragment.isAdded()) {

			android.support.v4.app.FragmentTransaction fragmentTransaction = mFragmentManager
					.beginTransaction();

			fragmentTransaction.add(R.id.image_fragment_container,
					mImageFragment);

			fragmentTransaction.addToBackStack(null);

			fragmentTransaction.commit();

			mFragmentManager.executePendingTransactions();
		}
		
		if (mImageFragment.getShownIndex() != index) {

			mImageFragment.showImageAtIndex(index);
		
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.top_menu, menu);
		return true;
	}

	//Setting up functionality of option menus
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.launch_m:
				if(itemIndex < 0)
					Toast.makeText(this, "Please select a phone from the list.", Toast.LENGTH_LONG).show() ;
				else
					sendBroadcastToApps();
				return true;
			case R.id.exit_m:
				finishAffinity();
				System.exit(0);
				return true;
			default:
				return false;
		}
	}

	//Broadcast message to App 1 and App2
	public void sendBroadcastToApps(){
		Intent aIntent = new Intent(WEBSITE_INTENT) ;
		aIntent.putExtra("webURL", mURLArray[itemIndex]);
		sendOrderedBroadcast(aIntent, KABOOM_PERMISSION) ;
	}


	//Methods to save and recover index and layout of phone selected in the fragments before changing orientation.
		@Override
	protected void onSaveInstanceState(Bundle outState) {
		outState.putInt("currentIndex",itemIndex);
		super.onSaveInstanceState(outState);
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		itemIndex = savedInstanceState.getInt("currentIndex");
		super.onRestoreInstanceState(savedInstanceState);
		setLayout();
		onListSelection(itemIndex);

	}
}