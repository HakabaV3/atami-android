package com.atami.kikurage.atamikeyboard.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.atami.kikurage.atamikeyboard.R;
import com.atami.kikurage.atamikeyboard.model.StampAdapter;
import com.atami.kikurage.atamikeyboard.view.StampGridView;

/**
 * A placeholder fragment containing a simple view.
 */
public class StampSelectFragment extends Fragment {

    private StampGridView mStampGrid;
    private StampAdapter mAdapter;
    private Context mContext;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_stamp_select, container, false);
        mContext = getActivity().getApplicationContext();

        mStampGrid = (StampGridView) view.findViewById(R.id.stampGrid);
        mAdapter = new StampAdapter(mContext);
        mStampGrid.setAdapter(mAdapter);

        mAdapter.pGetImageSearch("");

        return view;
    }
}
