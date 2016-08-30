package yonatan24891.effiapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.SeekBar;

import java.util.ArrayList;

public class PreferenciasActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferencias);
        cambiaPeso();




    }

    int pesoCpu;
    int pesoRam;
    int pesoBateria;
    int pesoNotaMed;
    int pesoNDesc;
    int pesoDatosEnv;
    int pesoDatosRec;
    int pesoCapacidad;


    public void cambiaPeso(){


        final SeekBar cpuSeekBar = (SeekBar) findViewById(R.id.cpuSeekBar);
        final SeekBar ramSeekBar = (SeekBar) findViewById(R.id.ramSeekBar);
        final SeekBar bateriaSeekBar = (SeekBar) findViewById(R.id.bateriaSeekBar);
        final SeekBar notaMedSeekBar = (SeekBar) findViewById(R.id.notaMedSeekBar);
        final SeekBar nDescSeekBar = (SeekBar) findViewById(R.id.nDescSeekBar);
        final SeekBar datosEnvSeekBar = (SeekBar) findViewById(R.id.datosEnvSeekBar);
        final SeekBar datosRecSeekBar = (SeekBar) findViewById(R.id.datosRecSeekBar);
        final SeekBar capacidadSeekBar = (SeekBar) findViewById(R.id.capacidadSeekBar);

        cpuSeekBar.setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                        pesoCpu = cpuSeekBar.getProgress();
                    }
                }
        );

        ramSeekBar.setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                        pesoRam = ramSeekBar.getProgress();
                    }
                }
        );

        bateriaSeekBar.setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                        pesoBateria = bateriaSeekBar.getProgress();
                    }
                }
        );

        notaMedSeekBar.setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                        pesoNotaMed = notaMedSeekBar.getProgress();
                    }
                }
        );

        nDescSeekBar.setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                        pesoNDesc = nDescSeekBar.getProgress();
                    }
                }
        );

        datosEnvSeekBar.setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                        pesoDatosEnv = datosEnvSeekBar.getProgress();
                    }
                }
        );

        datosRecSeekBar.setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                        pesoDatosRec = datosRecSeekBar.getProgress();
                    }
                }
        );

        capacidadSeekBar.setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                        pesoCapacidad = capacidadSeekBar.getProgress();
                    }
                }
        );



    }




    public void goToMain(View view) {


        int pesoTotal= pesoCpu+pesoRam+pesoBateria+pesoCapacidad+pesoDatosEnv+pesoDatosRec+pesoNDesc+pesoNotaMed;

        EvaluarActivity.recursos[0].setPeso((float)pesoCpu/pesoTotal);
        EvaluarActivity.recursos[1].setPeso((float)pesoRam/pesoTotal);
        EvaluarActivity.recursos[2].setPeso((float)pesoBateria/pesoTotal);
        EvaluarActivity.recursos[3].setPeso((float)pesoNotaMed/pesoTotal);
        EvaluarActivity.recursos[4].setPeso((float)pesoNDesc/pesoTotal);
        EvaluarActivity.recursos[5].setPeso((float)pesoDatosEnv/pesoTotal);
        EvaluarActivity.recursos[6].setPeso((float)pesoDatosRec/pesoTotal);
        EvaluarActivity.recursos[7].setPeso((float)pesoCapacidad/pesoTotal);

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);


    }
}
