package com.example.a3;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class PhoneFragment extends android.support.v4.app.ListFragment {
	private static final String TAG = "PhoneFragment";
	private ListSelectionListener mListener = null;
	private PhoneImageViewerActivity hostActivity = null;

	public interface ListSelectionListener {
		public void onListSelection(int index);
	}

	@Override
	public void onListItemClick(ListView l, View v, int pos, long id) {

		getListView().setItemChecked(pos, true);
		mListener.onListSelection(pos);
	}

	@Override
	public void onAttach(Context activity) {
		super.onAttach(activity);
		
		try {
			mListener = (ListSelectionListener) activity;
			hostActivity = (PhoneImageViewerActivity) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString()
					+ " must implement OnArticleSelectedListener");
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		return super.onCreateView(inflater, container, savedInstanceState);
	}

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public void onActivityCreated(Bundle savedState) {
		super.onActivityCreated(savedState);

		setListAdapter(new ArrayAdapter<String>(getActivity(),
				R.layout.phone_image, PhoneImageViewerActivity.mPhoneArray));

		getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
	}
}