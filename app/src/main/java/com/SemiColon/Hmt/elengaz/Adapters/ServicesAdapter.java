package com.SemiColon.Hmt.elengaz.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.SemiColon.Hmt.elengaz.API.Model.Services;
import com.SemiColon.Hmt.elengaz.API.Service.APIClient;
import com.SemiColon.Hmt.elengaz.API.Service.ServicesApi;
import com.SemiColon.Hmt.elengaz.R;
import com.SemiColon.Hmt.elengaz.Activities.Home;
import com.SemiColon.Hmt.elengaz.Activities.OfficeWork;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ServicesAdapter extends RecyclerView.Adapter<ServicesAdapter.Holder> {
    Context context;
    Services mmodel;
    List<Services> Array;
    Home home;

    public ServicesAdapter(Context context, List<Services> Array ) {
        this.context = context;
        this.Array = Array;
        this.home= (Home) context;

    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.services_list_item, parent, false);

        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        mmodel = Array.get(position);
        holder.text.setTag(position);
        holder.text.setText(mmodel.getService_title());

    }

    @Override
    public int getItemCount() {
        return Array.size();
    }

    class Holder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView text;

         Holder(View itemView) {
            super(itemView);

            text = itemView.findViewById(R.id.txtlist);
            text.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

            int position =getAdapterPosition();
            mmodel = Array.get(position);
            Toast.makeText(context, mmodel.getService_id()+"", Toast.LENGTH_SHORT).show();

            sendData();
            Intent i = new Intent(context,OfficeWork.class);
            i.putExtra("client_id",home.id);
            i.putExtra("service_id",mmodel.getService_id());
            context.startActivity(i);

        }


    }

    private void sendData() {


        ServicesApi service= APIClient.getClient().create(ServicesApi.class);
        Call<Services> call=service.sendservice(home.id,mmodel.getService_id());
        call.enqueue(new Callback<Services>() {
            @Override
            public void onResponse(Call<Services> call, Response<Services> response) {
                if (response.body().getSuccess()==1){

                    Toast.makeText(context, ""+home.id+mmodel.getService_id(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Services> call, Throwable t) {

            }
        });



    }



}
