package com.SemiColon.Hmt.elengaz.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.widget.SearchView;
import android.widget.Toast;

import com.SemiColon.Hmt.elengaz.API.Model.Services;
import com.SemiColon.Hmt.elengaz.API.Service.APIClient;
import com.SemiColon.Hmt.elengaz.API.Service.ServicesApi;
import com.SemiColon.Hmt.elengaz.Adapters.ServicesAdapter;
import com.SemiColon.Hmt.elengaz.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import me.anwarshahriar.calligrapher.Calligrapher;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Home extends AppCompatActivity {

    ArrayList<Services> Model;
    ServicesAdapter adapter;
    RecyclerView recyclerView;
    SearchView searchView;
   public String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        final Intent intent=getIntent();
        id=intent.getStringExtra("id");
        Toast.makeText(this, ""+id, Toast.LENGTH_SHORT).show();
        Calligrapher calligrapher=new Calligrapher(this);
        calligrapher.setFont(this,"JannaLT-Regular.ttf",true);
         searchView= (SearchView) findViewById(R.id.searchView);

         searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
             @Override
             public boolean onQueryTextSubmit(String s) {
                 Toast.makeText(Home.this, ""+s, Toast.LENGTH_SHORT).show();

                 ServicesApi service = APIClient.getClient().create(ServicesApi.class);

                 Call<List<Services>> callApi = service.searchservice(s);
                 callApi.enqueue(new Callback<List<Services>>() {
                     @Override
                     public void onResponse(Call<List<Services>> call, Response<List<Services>>response) {

                         if (response.isSuccessful()) {

                             List<Services> servicesList = response.body();
                             Intent intent1 = new Intent(Home.this,Activity_Search_Results.class);

                             intent1.putExtra("servicesList", (Serializable) servicesList);
                             intent1.putExtra("clientId",id);
                             startActivity(intent1);

                             Toast.makeText(Home.this, ""+response.body().get(0).getService_title(), Toast.LENGTH_SHORT).show();

                         }


                     }

                     @Override
                     public void onFailure(Call<List<Services>> call, Throwable t) {
                         Toast.makeText(Home.this, "rrrrr", Toast.LENGTH_SHORT).show();


                     }
                 });



                 return false;
             }

             @Override
             public boolean onQueryTextChange(String s) {
                 return false;
             }
         });
        recyclerView = findViewById(R.id.recyc_service);

        Model=new ArrayList<>();

        recyclerView.setLayoutManager(new GridLayoutManager(Home.this,1));
        recyclerView.setHasFixedSize(true);

        adapter=new ServicesAdapter(Home.this,Model);
        recyclerView.setAdapter(adapter);

        searchView.setQueryHint(Html.fromHtml("<font color = #000>" + "ابحث عن خدمه" + "</font>"));
        ServicesApi service = APIClient.getClient().create(ServicesApi.class);

        Call<List<Services>> call = service.getServicesData();
        call.enqueue(new Callback<List<Services>>() {
            @Override
            public void onResponse(Call<List<Services>> call, Response<List<Services>>response) {

                if (response.isSuccessful()){
                Model.addAll(response.body());
                adapter.notifyDataSetChanged();}else {
                    Toast.makeText(Home.this, "error", Toast.LENGTH_SHORT).show();

                }


            }

            @Override
            public void onFailure(Call<List<Services>> call, Throwable t) {
                Toast.makeText(Home.this, "rrrrr", Toast.LENGTH_SHORT).show();


            }
        });

    }
}
