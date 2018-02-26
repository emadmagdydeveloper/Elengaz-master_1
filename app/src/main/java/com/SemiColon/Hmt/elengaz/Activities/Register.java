package com.SemiColon.Hmt.elengaz.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.SemiColon.Hmt.elengaz.API.Model.User;
import com.SemiColon.Hmt.elengaz.API.Service.APIClient;
import com.SemiColon.Hmt.elengaz.API.Service.ServicesApi;
import com.SemiColon.Hmt.elengaz.API.Model.MSG;
import com.SemiColon.Hmt.elengaz.R;

import me.anwarshahriar.calligrapher.Calligrapher;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Register extends AppCompatActivity {

    private static final String TAG = "SignupActivity";
    EditText username,password ,phone, email;
    Button register;
    private ProgressDialog pDialog;
    User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Calligrapher calligrapher = new Calligrapher(this);
        calligrapher.setFont(this, "JannaLT-Regular.ttf", true);
        username=findViewById(R.id.edtusername);
        password=findViewById(R.id.edtpass);
        email=findViewById(R.id.edtemail);
        phone=findViewById(R.id.edtphone);
        register=findViewById(R.id.register);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                 user=new User(
                        username.getText().toString(),
                        password.getText().toString(),
                        email.getText().toString(),
                        phone.getText().toString()

                );
                signup();

                // sendNetworkRequest(user);
            }
        });


    }


    public void signup() {
        Log.d(TAG, "Signup");

        if (validate() == false) {
            onSignupFailed();
            return;
        }

        saveToServerDB();

    }


    public void onSignupSuccess() {
        register.setEnabled(true);
        setResult(RESULT_OK, null);
        finish();
    }

    public void onSignupFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

        register.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String name = username.getText().toString();
        String uemail = email.getText().toString();
        String pass = password.getText().toString();
        String mobile = phone.getText().toString();

        if (name.isEmpty() || name.length() < 3) {
            username.setError("at least 3 characters");
            valid = false;
        } else {
            username.setError(null);
        }


        if (uemail.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(uemail).matches()) {
            email.setError("enter a valid email address");
            valid = false;
        } else {
            email.setError(null);
        }


        if (pass.isEmpty() || pass.length() < 4 || pass.length() > 10) {
            password.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            password.setError(null);
        }

        if (mobile.isEmpty() || mobile.length() < 8 || mobile.length() > 13) {
            phone.setError("phone Do not match");
            valid = false;
        } else {
            phone.setError(null);
        }

        return valid;
    }

    private void saveToServerDB() {
        pDialog = new ProgressDialog(Register.this);
        pDialog.setIndeterminate(true);
        pDialog.setMessage("Creating Account...");
        pDialog.setCancelable(false);

        showpDialog();

        String name = username.getText().toString();
        String pass = password.getText().toString();

        String uemail = email.getText().toString();
        String mobile = phone.getText().toString();


        ServicesApi service = APIClient.getClient().create(ServicesApi.class);


        Call<MSG> userCall = service.userSignUp(name,pass, uemail, mobile);
        // startActivity(new Intent(Register.this, ListMarma.class));

        userCall.enqueue(new Callback<MSG>() {
            @Override
            public void onResponse(Call<MSG> call, Response<MSG> response) {
                hidepDialog();
                //onSignupSuccess();
//                Log.d("onResponse", "" + response.body().getMessage());


                if (response.body().getSuccess() == 1) {
                    startActivity(new Intent(Register.this, Home.class));

                    Toast.makeText(Register.this, "email"+user.getEmail()+"10"+user.getPassword(), Toast.LENGTH_SHORT).show();

                    finish();
                } else {
                    Toast.makeText(Register.this, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<MSG> call, Throwable t) {
                hidepDialog();
                Log.d("onFailure", t.toString());
            }
        });
    }

    private void showpDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hidepDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

    /*private void sendNetworkRequest(User user){
        Retrofit.Builder builder=new Retrofit.Builder()
                .baseUrl("http://clup.alatheertech.com/Api/")
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit=builder.build();
        ServicesApi client=retrofit.create(ServicesApi.class);
       Call<User>call= client.createAccount(user);
       call.enqueue(new Callback<User>() {
           @Override
           public void onResponse(Call<User> call, Response<User> response) {
               Toast.makeText(Register.this, "Yeah ,User Id ", Toast.LENGTH_SHORT).show();
               Intent i=new Intent(Register.this,ListMarma.class);
               startActivity(i);
           }

           @Override
           public void onFailure(Call<User> call, Throwable t) {

               Toast.makeText(Register.this, "somthing wrong", Toast.LENGTH_SHORT).show();

           }
       });

    }*/
}
