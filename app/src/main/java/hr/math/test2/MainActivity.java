package hr.math.test2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Button mojButton = findViewById(R.id.mojGumbSave);
        final EditText mojTekst1 = findViewById(R.id.mojTekst);


        final DataBase mojaBaza = new DataBase(MainActivity.this);

        final DataBase2 mojaBaza2 = new DataBase2(MainActivity.this);


  /*      mojaBaza.open();

        mojaBaza.insertContact("Ivo Peric", "ivo@peric.ba");
        mojaBaza.insertContact("Nikola Peric", "nikola@peric.ba");
        mojaBaza.insertContact("Sveti Petar", "petar@sveti.ba");
        mojaBaza.insertContact("Marija Travica", "marija@travica.hr");

        mojaBaza.close();
*/

/*        mojaBaza2.open();

        mojaBaza2.insertContact("Ivo Peric", "volimAnu1", 28, 1);
        mojaBaza2.insertContact("Nikola Peric", "volimMladenku01", 59, 1);
        mojaBaza2.insertContact("Sveti Petar", "volimIsusa333", 2018, 0);
        mojaBaza2.insertContact("Marija Travica", "20102005", 12, 0);

        mojaBaza2.close();
*/



        mojButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

/*                int mode=MODE_PRIVATE;
                SharedPreferences mySharedPreferences=getSharedPreferences("MYPREFS",mode); //ovo vazda kopiram da dohvatim (ako postoji) ili stvorim taj .xml file

                SharedPreferences.Editor editor=mySharedPreferences.edit(); //ovo isto kopiram, služi za spremanje

                editor.putString("upisano", mojTekst1.getText().toString());
                editor.commit();*/


                mojaBaza.open();

//                mojaBaza.updateContact(4, "Marta", "marta@travica.hr");

                mojaBaza.deleteContact(10);

                mojaBaza.close();

                Intent mojIntent = new Intent(MainActivity.this, Main2Activity.class);

                startActivity(mojIntent);






            }


        });

        Button mojButton2 = findViewById(R.id.mojGumbLoad);
        final TextView mojTekst = findViewById(R.id.mojTekstIspis);

        mojButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                /*int mode=MODE_PRIVATE;
                SharedPreferences mySharedPreferences=getSharedPreferences("MYPREFS",mode);


                String stringPreference=mySharedPreferences.getString("upisano", "nista");

                mojTekst.setText(stringPreference);
                */

                mojaBaza.open();

                Cursor mojCursor = mojaBaza.getAllContacts();

                if(mojCursor.moveToFirst()){

                    do{
                        mojTekst.setText(mojTekst.getText().toString() + "\n" + mojCursor.getInt(0) + ":" + mojCursor.getString(1));
                    }while(mojCursor.moveToNext());

                }

                mojaBaza.close();

                mojaBaza2.open();

                Cursor ivinCursor = mojaBaza2.getAllContacts();

                if(ivinCursor.moveToFirst())
                {
                    do{
                        Log.d("Log", ivinCursor.getInt(0) + ":" + ivinCursor.getString(1) + ", " + ivinCursor.getInt(3) + ", " + ivinCursor.getInt(4));
                    } while(ivinCursor.moveToNext());
                }


                mojaBaza2.close();

                Intent mojIntent = new Intent(MainActivity.this, Main3Activity.class);

                startActivity(mojIntent);



            }
        });





    }

    protected void savePreferences(){ //pisanje filea
        //stvorimo shared preference
        int mode=MODE_PRIVATE;
        SharedPreferences mySharedPreferences=getSharedPreferences("MYPREFS",mode); //ovo vazda kopiram da dohvatim (ako postoji) ili stvorim taj .xml file


        //editor za modificiranje shared preference
        SharedPreferences.Editor editor=mySharedPreferences.edit(); //ovo isto kopiram, služi za spremanje

        //spremamo vrijednosti u shared preference
        editor.putBoolean("isTrue", true);
        editor.putFloat("lastFloat",1f);
        editor.putInt("wholeNumber", 5);
        editor.putLong("aNumber", 35);
        editor.putString("textEntryValue", "Neki tekst");

        //commit promjene
        editor.commit();
    }

    public void loadPreferences(){ //čitanje iz filea
        // dohvatimo preference
        int mode=MODE_PRIVATE;
        SharedPreferences mySharedPreferences=getSharedPreferences("MYPREFS",mode);

        //dohvatimo vrijednosti, prvo ide key, a  ovo drugo je default vrijednost
        boolean isTrue=mySharedPreferences.getBoolean("isTrue", false);
        float lastFloat=mySharedPreferences.getFloat("lastFloat", 0f);
        int wholeNumber=mySharedPreferences.getInt("WholeNumber", 7);
        long aNumber=mySharedPreferences.getLong("aNumber", 0);
        String stringPreference=mySharedPreferences.getString("textEntryValue", "nista");

        //ispisemo string da se nesto ispise...
        Toast.makeText(getBaseContext(),
                stringPreference,
                Toast.LENGTH_SHORT).show();

    }
}
