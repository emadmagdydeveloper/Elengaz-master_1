package com.SemiColon.Hmt.elengaz.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.SemiColon.Hmt.elengaz.API.Service.APIClient;
import com.SemiColon.Hmt.elengaz.API.Service.ServicesApi;
import com.SemiColon.Hmt.elengaz.Adapters.OffersAdapter;
import com.SemiColon.Hmt.elengaz.Model.OfficeOfferModel;
import com.SemiColon.Hmt.elengaz.Model.ResponseModel;
import com.SemiColon.Hmt.elengaz.R;

import java.util.List;

import me.anwarshahriar.calligrapher.Calligrapher;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class Offers extends AppCompatActivity implements View.OnClickListener{

    private RecyclerView recView_offers;
    private RecyclerView.LayoutManager manager;
    private RecyclerView.Adapter adapter;
    private List<OfficeOfferModel> officeOfferModelList;
    private String service_id,office_id_fk;
    private Button choose;
    private ProgressDialog dialog;
    private ImageView back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offers);
        Calligrapher calligrapher=new Calligrapher(this);
        calligrapher.setFont(this,"JannaLT-Regular.ttf",true);

        initView();
        CreateProgressDialog();
        getDataFromIntent();
        choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (office_id_fk!=null||!TextUtils.isEmpty(office_id_fk))
                {
                    dialog.show();
                    Retrofit retrofit = APIClient.getClient();
                    ServicesApi service  = retrofit.create(ServicesApi.class);
                    Call<ResponseModel> call = service.Send_OfficesOffersDone(service_id, office_id_fk);
                    call.enqueue(new Callback<ResponseModel>() {
                        @Override
                        public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                            if (response.isSuccessful())
                            {
                               ResponseModel responseModel = response.body();
                                if (responseModel.getSuccess()==1)
                                {
                                    dialog.dismiss();
                                    Intent i=new Intent(Offers.this,TransAccount.class);
                                    startActivity(i);
                                }else
                                    {
                                        dialog.dismiss();
                                    }
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseModel> call, Throwable t) {
                            Toast.makeText(Offers.this, "error", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        }
                    });
                }else
                    {
                        Toast.makeText(Offers.this, "please select offer you want", Toast.LENGTH_SHORT).show();
                    }

            }
        });
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

    private void getDataFromIntent() {
        Intent intent = getIntent();
        if (intent!=null)
        {
            if (intent.hasExtra("service_id"))
            {
                service_id = intent.getStringExtra("service_id");
            }
        }
    }

    private void initView() {
        choose = findViewById(R.id.choose);
        back   = findViewById(R.id.back);
        recView_offers = findViewById(R.id.recView_offers);
        manager = new LinearLayoutManager(this);
        recView_offers.setLayoutManager(manager);
        recView_offers.setHasFixedSize(true);

        back.setOnClickListener(this);

    }

    private void DisplayAllOffices_Offers()
    {
        Toast.makeText(this, "sid"+service_id, Toast.LENGTH_SHORT).show();
        Retrofit retrofit = APIClient.getClient();
        ServicesApi service  = retrofit.create(ServicesApi.class);
        Call<List<OfficeOfferModel>> call = service.DisplayAll_OfficesOffers(service_id);
        call.enqueue(new Callback<List<OfficeOfferModel>>() {
            @Override
            public void onResponse(Call<List<OfficeOfferModel>> call, Response<List<OfficeOfferModel>> response) {

                if (response.isSuccessful())
                {
                    officeOfferModelList = response.body();
                    adapter = new OffersAdapter(Offers.this,officeOfferModelList);
                    adapter.notifyDataSetChanged();
                    recView_offers.setAdapter(adapter);
                    Toast.makeText(Offers.this, "dddd"+officeOfferModelList.get(0).getOffice_title(), Toast.LENGTH_SHORT).show();

                }
            }
            @Override
            public void onFailure(Call<List<OfficeOfferModel>> call, Throwable t) {
                Toast.makeText(Offers.this, "error", Toast.LENGTH_SHORT).show();

            }
        });

    }

    public void setPos(int pos)
    {
        office_id_fk = officeOfferModelList.get(pos).getOffice_id_fk();
    }

    @Override
    protected void onStart() {
        super.onStart();
        DisplayAllOffices_Offers();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.back:
                finish();
                break;
        }
    }
}
