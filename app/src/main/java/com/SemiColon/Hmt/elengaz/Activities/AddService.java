package com.SemiColon.Hmt.elengaz.Activities;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.SemiColon.Hmt.elengaz.API.Model.MSG;
import com.SemiColon.Hmt.elengaz.API.Service.APIClient;
import com.SemiColon.Hmt.elengaz.API.Service.ServicesApi;
import com.SemiColon.Hmt.elengaz.R;

import java.util.Calendar;

import me.anwarshahriar.calligrapher.Calligrapher;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddService extends AppCompatActivity {
    private ProgressDialog pDialog;
    Button btnDatePicker, btnplace,add;
    private int mYear, mMonth, mDay;
    EditText serviseName,detail,phone,otherPhone,email;
    String sDate,client_id;
    Double sLatitude,sLongitude;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_service);
        Calligrapher calligrapher=new Calligrapher(this);
        calligrapher.setFont(this,"JannaLT-Regular.ttf",true);

        serviseName=findViewById(R.id.edt_service_name);
        detail=findViewById(R.id.edt_service_detail);
        phone=findViewById(R.id.edt_service_phone);
        otherPhone=findViewById(R.id.edt_service_other_phone);
        email=findViewById(R.id.edt_service_email);

        btnDatePicker=findViewById(R.id.btndate);
        btnplace=findViewById(R.id.btnplace);
        add=(Button) findViewById(R.id.btn_add_service);

        Intent intent=getIntent();
       sLatitude= intent.getDoubleExtra("latitude",1.1);
       sLongitude= intent.getDoubleExtra("longitude",1.1);
      client_id= intent.getStringExtra("client_id");
        btnDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog = new DatePickerDialog(AddService.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {



                                Toast.makeText(AddService.this, ""+dayOfMonth + "-" + (monthOfYear + 1) + "-" + year, Toast.LENGTH_SHORT).show();
                                sDate=dayOfMonth+(monthOfYear+1)+year+"";
                            }
                        }, mYear, mMonth, mDay);

                datePickerDialog.show();
            }

        });



        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent i=new Intent(AddService.this,Notification1.class);
//                startActivity(i);

                pDialog = new ProgressDialog(AddService.this);
                pDialog.setIndeterminate(true);
                pDialog.setMessage("Creating Account...");
                pDialog.setCancelable(false);

                showpDialog();

                String sName = serviseName.getText().toString();
                String sDetail = detail.getText().toString();
                String sPhone = phone.getText().toString();
                String sOtherPhone = otherPhone.getText().toString();
                String sEmail = email.getText().toString();



                ServicesApi service = APIClient.getClient().create(ServicesApi.class);


                Call<MSG> userCall = service.AddOneService(sName,sDetail,sPhone,sOtherPhone,sEmail,sLatitude.toString(),sLongitude.toString(),sDate,client_id);
                // startActivity(new Intent(Register.this, ListMarma.class));

                userCall.enqueue(new Callback<MSG>() {
                    @Override
                    public void onResponse(Call<MSG> call, Response<MSG> response) {
                        hidepDialog();
                        //onSignupSuccess();
//                Log.d("onResponse", "" + response.body().getMessage());


                        if (response.isSuccessful()) {
                            startActivity(new Intent(AddService.this, Offers.class));

                            Toast.makeText(AddService.this, ""+sLatitude.toString()+" "+sLongitude.toString()+" "+sDate+" "+client_id, Toast.LENGTH_LONG).show();

                            finish();
                        } else {
                            Toast.makeText(AddService.this, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<MSG> call, Throwable t) {
                        hidepDialog();
                        Log.d("onFailure", t.toString());
                    }
                });
            }
        });

       btnplace.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent i=new Intent(AddService.this,AddPlace.class);
            startActivity(i);
        }
    });}
    private void showpDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hidepDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }
}
