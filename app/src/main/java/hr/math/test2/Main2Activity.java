package hr.math.test2;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);


        Button buttonSMS = findViewById(R.id.mojGumbSMS);

        buttonSMS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                sendSMS();

            }
        });

        Button buttonmail = findViewById(R.id.mojGumbMail);

        buttonmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                sendmail();

            }
        });


    }



    private void sendmail()
    {
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setData(Uri.parse("mailto:"));
        String[] to = new  String[]{"ivo17peric@gmail.com", "ana.travica91@gmail.com"};
        String[] cc = new String[]{"ivo17peric@gmail.com"};
        emailIntent.putExtra(Intent.EXTRA_EMAIL, to);
        emailIntent.putExtra(Intent.EXTRA_CC, cc);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "volim anu");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "ana, gliupa si i necu ti vise bit muzonja ni muz");
        emailIntent.setType("message/rfc822");
        startActivity(Intent.createChooser(emailIntent, "Email"));

    }

    private void sendSMS(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) //for new versions
        {
            Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("sms:" + 5556));
            i.putExtra("sms_body", "Moj tekst koji saljem.");
            startActivity(i);

        }
        else // For early versions, do what worked before.
        {
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setType("vnd.android-dir/mms-sms");
            i.putExtra("address", "5556");
            i.putExtra("sms_body","Moj tekst koji saljem.");
            startActivity(i);
        }



    }
}
