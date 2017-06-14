package id.sch.smktelkom_mlg.geofencinglogin_sigma2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class ProfilActivity extends AppCompatActivity {

    public static final String MyNotif = "myNotifDetail";
    public static final String Det = "nameDet";
    SharedPreferences sharedpreferencesnotif;
    TextView getData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);

        findViewById(R.id.btnList).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfilActivity.this, ListviewActivity.class));
            }
        });

    }

    public void get1(View view) {
        getData = (TextView) findViewById(R.id.detailEntered);
        sharedpreferencesnotif = getSharedPreferences(MyNotif,
                Context.MODE_PRIVATE);

        if (sharedpreferencesnotif.contains(Det)) {
            getData.setText(sharedpreferencesnotif.getString(Det, ""));
        } else {
            Toast.makeText(ProfilActivity.this, "Belum Memasuki Kawasan Ini",
                    Toast.LENGTH_SHORT).show();
        }
    }
}
