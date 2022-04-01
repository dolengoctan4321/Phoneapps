package com.example.phoneapps;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder > {

    Context context;
    ArrayList<Model> modelArrayList;

    public Adapter(Context context, ArrayList<Model> modelArrayList) {
        this.context = context;
        this.modelArrayList = modelArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.item_layout,parent,false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter.ViewHolder holder, int position) {

        holder.name.setText(modelArrayList.get(position).getName());
        holder.number.setText(modelArrayList.get(position).getNumber());
        holder.call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String number = modelArrayList.get(position).getNumber();

                Intent in = new Intent(Intent.ACTION_CALL);
                in.setData(Uri.parse("tel:" +number));
                context.startActivity(in);

            }
        });

        holder.sms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText message = new EditText(v.getContext());
                message.setHint("Hãy nhắn gì đó vào đây...");


                final AlertDialog.Builder sending_sms = new AlertDialog.Builder(v.getContext());

                sending_sms.setIcon(R.drawable.ic_baseline_textsms_24)
                        .setTitle("Đã gửi tới :"+modelArrayList.get(position).getName())
                        .setView(message)
                        .setPositiveButton("GỬI", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {



                                sendSMS(modelArrayList.get(position).getNumber(),message.getText().toString());

                            }
                        })
                        .setNegativeButton("HỦY", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).create();

                sending_sms.show();

            }
        });

    }

    public void filterlist(List<Model> filterlist){
        modelArrayList = (ArrayList<Model>) filterlist;
        notifyDataSetChanged();
    }


    private void sendSMS(String phonenumber, String message){

        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phonenumber, null, message, null, null);

            Toast.makeText(context, "Tin nhắn đã được gửi tới " + phonenumber, Toast.LENGTH_SHORT).show();
        }
        catch (Exception exception){
            Toast.makeText(context,"Lỗi không gửi được tin nhắn",Toast.LENGTH_SHORT);
        }
    }

    @Override
    public int getItemCount() {
        return modelArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView name,number;

        ImageView call,sms;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.id_name);
            number = itemView.findViewById(R.id.id_number);
            call = itemView.findViewById(R.id.id_call);
            sms = itemView.findViewById(R.id.id_sms);
        }
    }

}
