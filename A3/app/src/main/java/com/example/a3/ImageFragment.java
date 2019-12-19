package com.example.a3;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class ImageFragment extends android.support.v4.app.Fragment {

	private static final String TAG = "ImageFragment";

	private ImageView mPhoneView = null;
	private int mCurrIdx = -1;
	private int mPhoneArrLen;

	int getShownIndex() {
		return mCurrIdx;
	}

	void showImageAtIndex(int newIndex) {
		if (newIndex < 0 || newIndex >= mPhoneArrLen)
			return;
		mCurrIdx = newIndex;
		mPhoneView.setImageResource(PhoneImageViewerActivity.mImageArray[mCurrIdx]);
	}
	
	@Override
	public void onAttach(Context activity) {
		super.onAttach(activity);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.phone_list,
				container, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		mPhoneView = (ImageView) getActivity().findViewById(R.id.phoneListView);
		mPhoneArrLen = PhoneImageViewerActivity.mImageArray.length;
	}
}
