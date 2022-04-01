package com.example.phoneapps;

import android.app.AlertDialog;
import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;


public class ContactsFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    EditText editText;
    FloatingActionButton fab,delete;

    RecyclerView recyclerView;
    ArrayList<Model> arrayList;

    Adapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_contacts,null);

        recyclerView = view.findViewById(R.id.recycleview_contacts);
        arrayList = new ArrayList<>();

        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        editText = view.findViewById(R.id.editTextTextPersonName);


        adapter = new Adapter(getContext(),arrayList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        readContacts();

        fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                LayoutInflater li = LayoutInflater.from(getContext());
                View getEmpIdView = li.inflate(R.layout.dialog_contacts_details, null);

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());

                // set dialog_contact_details.xml to alertdialog builder
                alertDialogBuilder.setView(getEmpIdView);

                final EditText nameInput = (EditText) getEmpIdView.findViewById(R.id.editTextDialogNameInput);
                final EditText phoneInput = (EditText) getEmpIdView.findViewById(R.id.editTextDialogPhoneInput);
                // set dialog message

                alertDialogBuilder
                        .setCancelable(false)
                        .setPositiveButton("Thêm", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // get user input and set it to result
                                // edit text
                                insertContact(nameInput.getText().toString(), phoneInput.getText().toString());
                                restartLoader();

                            }
                        })
                        .setNegativeButton("HỦY", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).create();
                        alertDialogBuilder.show();

            }

        });

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                filter(s.toString());

            }
        });

        return view;
    }

    private void insertContact(String contactName, String contactPhone) {
        ContentValues values = new ContentValues();
        values.put(DBOpenHelper.CONTACT_NAME, contactName);
        values.put(DBOpenHelper.CONTACT_PHONE, contactPhone);
        Uri contactUri = getActivity().getContentResolver().insert(ContactsProvider.CONTENT_URI, values);
        long ret = ContentUris.parseId(contactUri);
        Toast.makeText(getContext(), "Created Contact " + contactName, Toast.LENGTH_LONG).show();
    }


    private void restartLoader() {
        getLoaderManager().restartLoader(0, null, this);
    }

    void readContacts(){
        Cursor phones = getActivity().getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,null,null,"UPPER("+ContactsContract.Contacts.DISPLAY_NAME + ") ASC");
        while (phones.moveToNext())
        {
            String name=phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            String phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

            arrayList.add(new Model(name,phoneNumber));
        }

        adapter.notifyDataSetChanged();
        phones.close();
    }

    private void filter(String tostring){

        List<Model> filterlist = new ArrayList<>();

        for (Model item : arrayList) {

            if(item.getName().toLowerCase().contains(tostring.toLowerCase())){
                filterlist.add(item);
            }
        }

        adapter.filterlist(filterlist);

    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable  Bundle args) {
        return new CursorLoader(getContext(),ContactsProvider.CONTENT_URI,null,null,null, "UPPER("+ContactsContract.Contacts.DISPLAY_NAME + ") ASC");
    }


    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor phones) {
        while (phones.moveToNext())
        {
            int index1 = phones.getColumnIndex(DBOpenHelper.CONTACT_NAME);
            int index2 = phones.getColumnIndex(DBOpenHelper.CONTACT_PHONE);


            String name = phones.getString(index1);
            String phone_no = phones.getString(index2);

            Model contact = new Model(name, phone_no);
            arrayList.add(contact);
        }


        adapter = new Adapter(getContext(), arrayList);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        phones.close();
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {

    }
}