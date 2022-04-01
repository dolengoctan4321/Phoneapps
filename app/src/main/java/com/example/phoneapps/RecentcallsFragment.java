package com.example.phoneapps;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.CallLog;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class RecentcallsFragment extends Fragment {

    RecyclerView recyclerView;
    ArrayList<CallModel> callModelArrayList;
    CallAdapter callAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_recentcalls, null);

        recyclerView = v.findViewById(R.id.recycle_recent);

        callModelArrayList = new ArrayList<>();

        callAdapter = new CallAdapter(getContext(),callModelArrayList);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        recyclerView.setAdapter(callAdapter);

        getCallDetails(getContext(),callModelArrayList,callAdapter);

        return v;
    }

    private void getCallDetails(Context context, ArrayList<CallModel> arrayList, CallAdapter callAdapter) {
        Cursor cursor = context.getContentResolver().query(CallLog.Calls.CONTENT_URI,
                null, null, null, CallLog.Calls.DATE + " DESC");
        int name = cursor.getColumnIndex(CallLog.Calls.CACHED_NAME);
        int number = cursor.getColumnIndex(CallLog.Calls.NUMBER);
        int type = cursor.getColumnIndex(CallLog.Calls.TYPE);
        int date = cursor.getColumnIndex(CallLog.Calls.DATE);
        int duration = cursor.getColumnIndex(CallLog.Calls.DURATION);
        while(cursor.moveToNext()) {
                String phName = cursor.getString(name);
                String phNumber = cursor.getString(number);
                String callType = cursor.getString(type);
                String callDate = cursor.getString(date);
                Date callDayTime = new Date(Long.valueOf(callDate));

                Format format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                String time = format.format(callDayTime);

                String callDuration = cursor.getString(duration);
                String dir = null;
                int dircode = Integer.parseInt(callType);

                switch (dircode) {
                    case CallLog.Calls.OUTGOING_TYPE:
                        dir = "GỌI ĐI";
                        break;
                    case CallLog.Calls.INCOMING_TYPE:
                        dir = "GỌI ĐẾN";
                        break;

                    case CallLog.Calls.MISSED_TYPE:
                        dir = "CUỘC GỌI NHỠ";
                        break;
                }

                int min = Integer.parseInt(callDuration) / 60;
                int seconds = Integer.parseInt(callDuration) - min * 60;
                if (phName==null){
                    arrayList.add(new CallModel(phNumber, dir, String.valueOf(min) + ":" + String.valueOf(seconds), time));
                } else {
                    arrayList.add(new CallModel(phName, dir, String.valueOf(min) + ":" + String.valueOf(seconds), time));
                }

        }

        callAdapter.notifyDataSetChanged();
        cursor.close();
    }

}