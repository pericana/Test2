package hr.math.test2;

import android.app.NotificationManager;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Main4Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);

        NotificationManager nm=(NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        //cancel metoda za gasenje notificationa na kraju
        nm.cancel(getIntent().getExtras().getInt("notificationID"));

        Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("https://web.math.pmf.unizg.hr/~karaga/android/images_2/permission1.txt"));
        startActivity(i);
    }
}
