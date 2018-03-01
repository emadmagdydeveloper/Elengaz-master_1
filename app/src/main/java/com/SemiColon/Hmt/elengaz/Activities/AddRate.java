package com.SemiColon.Hmt.elengaz.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.Toast;

import com.SemiColon.Hmt.elengaz.API.Service.APIClient;
import com.SemiColon.Hmt.elengaz.API.Service.ServicesApi;
import com.SemiColon.Hmt.elengaz.Model.ResponseModel;
import com.SemiColon.Hmt.elengaz.R;

import java.util.HashMap;
import java.util.Map;

import me.anwarshahriar.calligrapher.Calligrapher;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class AddRate extends AppCompatActivity {
    private Button addrate;
    private RatingBar ratingBar;
    private EditText addAddress,addRate;
    private ProgressDialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_rate);
        Calligrapher calligrapher=new Calligrapher(this);
        calligrapher.setFont(this,"JannaLT-Regular.ttf",true);
        CreateProgressDialog();

        initView();

        addrate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ratingBar.getRating()==0.0)
                {
                    Toast.makeText(AddRate.this, "Add Rate", Toast.LENGTH_SHORT).show();
                }
                else if (TextUtils.isEmpty(addAddress.getText().toString()))
                {
                    Toast.makeText(AddRate.this, "Address is empty", Toast.LENGTH_SHORT).show();

                }else if (TextUtils.isEmpty(addRate.getText().toString()))
                {
                    Toast.makeText(AddRate.this, "Rate is empty", Toast.LENGTH_SHORT).show();

                }else
                    {
                        dialog.show();
                        Map <String ,String> map = new HashMap<>();
                        map.put("client_service_id","");
                        map.put("client_evaluation",String.valueOf((int)ratingBar.getRating()));
                        map.put("client_evaluation_title",addRate.getText().toString());
                        map.put("client_evaluation_comment",addAddress.getText().toString());
                        Retrofit retrofit = APIClient.getClient();
                        ServicesApi service  = retrofit.create(ServicesApi.class);
                        Call<ResponseModel> call = service.AddRate(map);
                        call.enqueue(new Callback<ResponseModel>() {
                            @Override
                            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                                if (response.isSuccessful())
                                {
                                    ResponseModel responseModel = response.body();
                                    if (responseModel.getSuccess()==1)
                                    {
                                        dialog.dismiss();
                                         Intent i=new Intent(AddRate.this,Notification.class);
                                         startActivity(i);

                                    }else
                                    {
                                        dialog.dismiss();
                                    }
                                }
                            }

                            @Override
                            public void onFailure(Call<ResponseModel> call, Throwable t) {
                                Toast.makeText(AddRate.this, "error", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            }
                        });

                    Toast.makeText(AddRate.this, "please select offer you want", Toast.LENGTH_SHORT).show();
                }                    }


        });
    }

    private void initView() {
        addrate =findViewById(R.id.btnadd);
        ratingBar = findViewById(R.id.ratingBar);
        addAddress = findViewById(R.id.addAddress);
        addRate = findViewById(R.id.addRate);

    }
    private void CreateProgressDialog() {

        ProgressBar bar = new ProgressBar(this);
        Drawable drawable = bar.getIndeterminateDrawable().mutate();
        drawable.setColorFilter(ContextCompat.getColor(this,R.color.colorPrimary), PorterDuff.Mode.SRC_IN);
        dialog = new ProgressDialog(this);
        dialog.setMessage("Wait for sending offer...");
        dialog.setIndeterminateDrawable(drawable);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(false);

    }
}
