package com.SemiColon.Hmt.elengaz.Activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Button;

import com.SemiColon.Hmt.elengaz.R;

import me.anwarshahriar.calligrapher.Calligrapher;

public class Pay extends AppCompatActivity {

Button trans;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);
        Calligrapher calligrapher=new Calligrapher(this);
        calligrapher.setFont(this,"JannaLT-Regular.ttf",true);


        trans =findViewById(R.id.trans);

        trans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Pay.this, AlertDialog.THEME_HOLO_LIGHT);

                // Set a title for alert dialog
                // Define the title color to red
                //builder.setTitle(Html.fromHtml("<font color='#ff0000'>Say Hello!</font>"));

                // Another way to change alert dialog title text color

                // Specify the alert dialog title
                String titleText = "تهانينا";

                // Initialize a new foreground color span instance
                ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(Color.GREEN);

                // Initialize a new spannable string builder instance
                SpannableStringBuilder ssBuilder = new SpannableStringBuilder(titleText);

                // Apply the text color span
                ssBuilder.setSpan(
                        foregroundColorSpan,
                        0,
                        titleText.length(),
                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                );

                // Set the alert dialog title using spannable string builder
                builder.setTitle(ssBuilder);

                // Show a message on alert dialog
                builder.setMessage("لقد تم التحويل بنجاح نشكرك على ثقتك فى خدمات");
                // Set the positive button
                builder.setPositiveButton("تم",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent i=new Intent(Pay.this,OrderState.class);
                        startActivity(i);                    }
                });


                // Create the alert dialog
                AlertDialog dialog = builder.create();

                // Finally, display the alert dialog
                dialog.show();
            }
        });

    }
}
