package com.SemiColon.Hmt.elengaz.API.Service;



import com.SemiColon.Hmt.elengaz.Model.MSG;
import com.SemiColon.Hmt.elengaz.Model.Officces;
import com.SemiColon.Hmt.elengaz.Model.OfficeOfferModel;
import com.SemiColon.Hmt.elengaz.Model.ResponseModel;
import com.SemiColon.Hmt.elengaz.Model.Services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by elashry on 2/10/2018.
 */

public interface ServicesApi {



    /*---------------------------------------- user login & register -------------------------------------------*/

    @FormUrlEncoded
    @POST("RegisterClient")
    Call<MSG> userSignUp(@Field("client_user_name") String name,
                         @Field("client_password") String password,
                         @Field("client_email") String email,
                         @Field("client_phone") String mobile);

    @FormUrlEncoded
    @POST("LoginClient")
    Call<MSG> userLogIn(@Field("client_user_name") String user_name,
                        @Field("client_password") String password);

    /*---------------------------------------- office login & register -------------------------------------------*/

    @FormUrlEncoded
    @POST("RegisterOffice")
    Call<MSG> officeSignUp(@Field("office_user_name") String name,
                           @Field("office_password") String password,
                           @Field("office_email") String email,
                           @Field("office_phone") String mobile,
                           @Field("office_title") String title,
                           @Field("office_city") String city);

    @FormUrlEncoded
    @POST("LoginOffice")
    Call<MSG> officeLogIn(@Field("office_user_name") String user_name,
                        @Field("office_password") String password);


    /*---------------------------------------- display services -------------------------------------------*/


    @GET("Services")
    Call<List<Services>> getServicesData();

    @GET("AllOffice")
    Call<List<Officces>> getofficces();



    @FormUrlEncoded
    @POST("SelectService")
    Call<Services> sendservice(@Field("client_id") String client_id,
                          @Field("service_id") String service_id);




    @FormUrlEncoded
    @POST("OrdersToOffice")
    Call<Officces> sendoffices(@Field("office_id[]") ArrayList<String> office_id,
                               @Field("client_id") String client_id,
                               @Field("client_service_id") String client_service_id);


    @FormUrlEncoded
    @POST("AddOneService")
    Call<MSG> AddOneService(@Field("service_name") String service_name,
                            @Field("service_details") String service_details,
                            @Field("phone_number") String phone_number,
                            @Field("other_phone") String other_phone,
                            @Field("email") String email,
                            @Field("google_lng") String google_lng,
                            @Field("google_lat") String google_lat,
                            @Field("service_date") String service_date,
                            @Field("client_service_id") String client_service_id
    );


//    @GET("AllOffice")
//    Call<List<Officces>> getofficces();



    @FormUrlEncoded
    @POST("SearchService")
    Call<List<Services>> searchservice(@Field("search_title_service")String search_title_service);


    @FormUrlEncoded
    @POST("SearchOfficeEvaluatin")
    Call<List<Officces>> searchByRate(@FieldMap Map<String,String> map);


  /*  @FormUrlEncoded
    @POST("SearchService")
    Call<List<Officces>> searchservice(@Field("offsearch_title_serviceice_id")String search_title_service);
*/
  @GET("OfficeOffers/{client_service_id}")
  Call<List<OfficeOfferModel>> DisplayAll_OfficesOffers(@Path("client_service_id") String client_service_id);

    @FormUrlEncoded
    @POST("OfficeOffers/{client_service_id}")
    Call<ResponseModel> Send_OfficesOffersDone(@Path("client_service_id") String client_service_id, @Field("office_id_fk") String office_id_fk);

    @FormUrlEncoded
    @POST("AddEvaluation")
    Call<ResponseModel> AddRate(@FieldMap Map <String ,String> map);

}
