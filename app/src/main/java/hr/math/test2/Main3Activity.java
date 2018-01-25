package hr.math.test2;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import static android.provider.ContactsContract.CommonDataKinds.Website.URL;

public class Main3Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);


        Button dlButton = findViewById(R.id.buttonDownload);

        dlButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                skini();
            }
        });


        Button txButton = findViewById(R.id.buttonDownloadText);

        txButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                skiniTekst();
            }
        });

    }

////////// OVO JE YA SKIDANJE FILOVA; SLIKE I TEKSTA
    private InputStream OpenHttpConnection(String urlString)
            throws IOException
    {
        InputStream in = null;
        int response = -1;

        java.net.URL url = new URL(urlString);
        URLConnection conn = url.openConnection();

        if (!(conn instanceof HttpURLConnection))
            throw new IOException("Not an HTTP connection");
        try{
            HttpURLConnection httpConn = (HttpURLConnection) conn;
            httpConn.setAllowUserInteraction(false);
            httpConn.setInstanceFollowRedirects(true);
            httpConn.setRequestMethod("GET");
            httpConn.connect();
            response = httpConn.getResponseCode();
            if (response == HttpURLConnection.HTTP_OK) {
                in = httpConn.getInputStream();
            }
        }
        catch (Exception ex)
        {
            Log.d("Networking", ex.getLocalizedMessage());
            throw new IOException("Error connecting");
        }
        return in;
    }


    private void skini()
    {

        new DownloadImageTask().execute("https://cdn.geek.hr/wp-content/uploads/sites/10/store/mali-zec-slika.jpg");

    }


    private void skiniTekst()
    {

        new DownloadTextTask().execute("https://web.math.pmf.unizg.hr/~karaga/android/images_2/permission1.txt");

    }

    private Bitmap DownloadImage(String URL)
    {
        Bitmap bitmap = null;
        InputStream in = null;
        try {
            in = OpenHttpConnection(URL);
            bitmap = BitmapFactory.decodeStream(in);
            in.close();
        } catch (IOException e1) {
            Log.d("NetworkingActivity", e1.getLocalizedMessage());
        }
        return bitmap;
    }


    private String DownloadText(String URL)
    {
        int BUFFER_SIZE = 2000;
        InputStream in = null;
        try {
            in = OpenHttpConnection(URL);
        } catch (IOException e) {
            Log.d("NetworkingActivity", e.getLocalizedMessage());
            return "";
        }

        InputStreamReader isr = new InputStreamReader(in);
        int charRead;
        String str = "";
        char[] inputBuffer = new char[BUFFER_SIZE];
        try {
            while ((charRead = isr.read(inputBuffer))>0) {
                //---convert the chars to a String---
                String readString =
                        String.copyValueOf(inputBuffer, 0, charRead);
                str += readString;
                inputBuffer = new char[BUFFER_SIZE];
            }
            in.close();
        } catch (IOException e) {
            Log.d("NetworkingActivity", e.getLocalizedMessage());
            return "";
        }
        return str;
    }


    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        protected Bitmap doInBackground(String... urls) {
            return DownloadImage(urls[0]);
        }

        protected void onPostExecute(Bitmap result) {
            ImageView img = (ImageView) findViewById(R.id.mojaSlika);
            img.setImageBitmap(result);
        }
    }

    private class DownloadTextTask extends AsyncTask<String, Void, String> {
        protected String doInBackground(String... urls) {
            return DownloadText(urls[0]);
        }

        protected void onPostExecute(String result) {
            TextView txt = findViewById(R.id.skinutiTekst);
            txt.setText(result);

            displayNotification();
        }
    }
///////////SVE DOVDE YA SKIDANJE FILOVA

    /////// METODA YA POKAZIVANJE NOTIFIKACIJE
    private void displayNotification()
    {
        //---PendingIntent to launch activity if the user selects
        // this notification---
        Intent i = new Intent(this, Main4Activity.class);

        i.putExtra("notificationID", 1);


        PendingIntent pendingIntent =
                PendingIntent.getActivity(this, 0, i, 0);

        long[] vibrate = new long[] { 100, 250, 100, 500};

//za sve verzije
        NotificationManager nm = (NotificationManager)
                getSystemService(NOTIFICATION_SERVICE);


        final NotificationCompat.Builder notif = new NotificationCompat.Builder(this, "12")

                .setDefaults(Notification.DEFAULT_ALL)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setVibrate(vibrate)
                .setSound(null)
                .setChannelId("12")
                .setContentTitle("Meeting with customer at 3pm...")
                .setContentText("this is the second row")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setTicker("Reminder: meeting starts in 5 minutes")
                .setContentIntent(pendingIntent)
                .setAutoCancel(false);

// za sve verzije

        nm.notify(1, notif.build());
    }


}
