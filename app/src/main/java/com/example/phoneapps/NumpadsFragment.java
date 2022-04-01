package com.example.phoneapps;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.fragment.app.Fragment;

public class NumpadsFragment extends Fragment {
    private EditText editTextDemo;
    private MyKeyboard keyboard;
    private ImageButton button_call;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_numpads, null);

        editTextDemo = v.findViewById(R.id.edt_demo_keyboard);

        keyboard = v.findViewById(R.id.keyboard);

        button_call = v.findViewById(R.id.callingbutton);

        // prevent system keyboard from appearing when EditText is tapped
        editTextDemo.setRawInputType(InputType.TYPE_CLASS_TEXT);
        editTextDemo.setTextIsSelectable(true);

        // pass the InputConnection from the EditText to the keyboard
        InputConnection ic = editTextDemo.onCreateInputConnection(new EditorInfo());
        keyboard.setInputConnection(ic);

        button_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String number=editTextDemo.getText().toString();
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:"+number));
                startActivity(callIntent);
            }
        });
        return v;
    }
}
