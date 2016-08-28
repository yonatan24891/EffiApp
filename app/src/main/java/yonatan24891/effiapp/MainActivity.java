package yonatan24891.effiapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    /** Called when the user clicks the Evaluar button */
    public void goToEvaluar(View view) {
        Intent intent = new Intent(this, EvaluarActivity.class);
        startActivity(intent);
    }

    /** Called when the user clicks the Preferencias button */
    public void goToPreferencias(View view) {
        Intent intent = new Intent(this, PreferenciasActivity.class);
        startActivity(intent);
    }
}
