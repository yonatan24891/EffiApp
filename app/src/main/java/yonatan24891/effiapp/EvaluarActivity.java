package yonatan24891.effiapp;

import android.app.ActivityManager;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageStats;
import android.graphics.Color;
import android.net.TrafficStats;
import android.net.Uri;
import android.os.Bundle;
import android.os.Debug;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;



public class EvaluarActivity extends AppCompatActivity {





    //RANGO DE TRABAJO Y RANGO IDEAL
    static final ResourceData[] recursos = new ResourceData[]{
            new ResourceData("CPU", 0, 100, 0, 8),
            new ResourceData("RAM", 0, 500, 0, 15),
            new ResourceData("Bateria", 0, 50, 0, 6),
            new ResourceData("NotaMedia", 0, 5, 4, 5),
            new ResourceData("NDescargas", 0, 500000000, 100000, 500000000),
            new ResourceData("DatosEnv", 0, 100, 0, 10),
            new ResourceData("DatosRec", 0, 100, 0, 10),
            new ResourceData("Capacidad", 0, 1000, 0, 100)
    };

    public void goToEvaluar(View view) {
        Intent intent = new Intent(this, EvaluarActivity.class);
        startActivity(intent);
    }

    private float restrictPercentage(float percentage) {
        if (percentage > 100)
            return 100;
        else if (percentage < 0)
            return 0;
        else return percentage;
    }


    public void goToMain(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    double DistanciaMinima(double x, ResourceData ABCD) {

        return Math.min(Math.abs(x - ABCD.getMinRangoI()), Math.abs(x - ABCD.getMaxRangoI()));
    }

    double normalizar(double x, ResourceData ABCD) {
        double valor = 1;

        if (x > ABCD.getMinRango() && x < ABCD.getMinRangoI() && (ABCD.getMinRango() != ABCD.getMinRangoI()))
            valor = DistanciaMinima(x, ABCD) / (Math.abs(ABCD.getMinRango() - ABCD.getMinRangoI()));

        else if (x > ABCD.getMaxRangoI() && x < ABCD.getMaxRango() && (ABCD.getMaxRangoI() != ABCD.getMaxRango()))
            valor = DistanciaMinima(x, ABCD) / (Math.abs(ABCD.getMaxRangoI() - ABCD.getMaxRango()));
        return valor;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluar);

        ActivityManager am = (ActivityManager) this.getSystemService(ACTIVITY_SERVICE);
        // The first in the list of RunningTasks is always the foreground task.
        List<ActivityManager.RunningTaskInfo> foregroundTaskInfo = am.getRunningTasks(10);
        List<ActivityManager.RunningAppProcessInfo> foregroundAppProcessesInfo = am.getRunningAppProcesses();

        final int nTasks = foregroundTaskInfo.size();
        int[] ProcessesIds = new int[nTasks];
        int[] taskIds = new int[nTasks];
        int[] usersIds = new int[nTasks];
        final String[] packageNames = new String[nTasks];
        final String[] nombres = new String[nTasks];
        final double[] ram = new double[nTasks];
        final double[] cpu = new double[nTasks];
        final double[] bateria = new double[nTasks];
        final double[] notaMedia = new double[nTasks];
        final double[] nDescargas = new double[nTasks];
        final double[] datosEnv = new double[nTasks];
        final double[] datosRec = new double[nTasks];
        final double[] capacidad = new double[nTasks];
        final double[] r = new double[nTasks];
        BufferedReader reader;
        String[] sa;
        long total, work, workAM;


        //System.out.println(foregroundAppProcessesInfo.get(0));

        //ANDROID 5.1.1 AND ABOVE http://stackoverflow.com/questions/30619349/android-5-1-1-and-above-getrunningappprocesses-returns-my-application-packag

        //PROCESSES
        for (int i = 0; i < nTasks; i++) {
            ActivityManager.RunningAppProcessInfo process = foregroundAppProcessesInfo.get(i);
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Task: {0}", process.toString());

            ProcessesIds[i] = process.pid;
            usersIds[i] = process.uid;
            //System.out.println(process.processName);
        }

        //TASKS
        for (int i = 0; i < nTasks; i++) {
            ActivityManager.RunningTaskInfo task = foregroundTaskInfo.get(i);
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Task: {0}", task.toString());
            // System.out.println(task.baseActivity.getPackageName());

            taskIds[i] = task.id;

            String foregroundTaskPackageName = task.baseActivity.getPackageName();
            PackageManager pm = this.getPackageManager();
            PackageInfo foregroundAppPackageInfo = null;

            try {
                foregroundAppPackageInfo = pm.getPackageInfo(foregroundTaskPackageName, 0);

            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }

            String foregroundTaskAppName = foregroundAppPackageInfo.applicationInfo.loadLabel(pm).toString();
            nombres[i] = foregroundTaskAppName;
            packageNames[i] = task.baseActivity.getPackageName();
            System.out.println(nombres[i]);

            //CAPACIDAD
            try {
                ApplicationInfo applicationInfo = pm.getApplicationInfo(packageNames[i], 0);
                File file = new File(applicationInfo.sourceDir);
                capacidad[i] = file.length()/100000.0;
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
        }





        //CPU
        for (int i = 0; i < nTasks; i++) {

            try {

                reader = new BufferedReader(new FileReader("/proc/stat"));
                sa = reader.readLine().split("[ ]+", 9);
                work = Long.parseLong(sa[1]) + Long.parseLong(sa[2]) + Long.parseLong(sa[3]);
                total = work + Long.parseLong(sa[4]) + Long.parseLong(sa[5]) + Long.parseLong(sa[6]) + Long.parseLong(sa[7]);
                reader.close();

                reader = new BufferedReader(new FileReader("/proc/" + ProcessesIds[i] + "/stat"));
                sa = reader.readLine().split("[ ]+", 18);
                workAM  = Long.parseLong(sa[13]) + Long.parseLong(sa[14]) + Long.parseLong(sa[15]) + Long.parseLong(sa[16]);
                reader.close();


                cpu[i] = restrictPercentage(workAM * 1000 / (float) total);


            } catch (IOException e) {
            e.printStackTrace();
            }

        }



        //RAM
        Debug.MemoryInfo[] memoryInfo = am.getProcessMemoryInfo(ProcessesIds);
        for (int i = 0; i < nTasks; i++) {
            //System.out.println(memoryInfo[i].getTotalPss()/1024.0);
            ram[i] = memoryInfo[i].getTotalPss() / 1024.0;
            System.out.println("RAM: " + ram[i]);
           /* System.out.println(memoryInfo[i].dalvikPrivateDirty);
            System.out.println(memoryInfo[i].otherPss);*/
        }


        //bateria
        for (int i = 0; i < nTasks; i++) {
            Random random = new Random();
            bateria[i] = random.nextFloat() * (30.0f - 10.0f) + 10.0f;
            System.out.println("bateria: " + bateria[i]);
        }


        //datos
        for (int i = 0; i < nTasks; i++) {
            //Random random = new Random();
            //datosEnv[i] = random.nextInt(15 - 2) + 2;
            datosEnv[i] = (TrafficStats.getUidTxBytes(usersIds[i]))/1024;
            //datosRec[i] = random.nextInt(15 - 2) + 2;
            datosRec[i] = (TrafficStats.getUidRxBytes(usersIds[i]))/1024;
            System.out.println("datosE: " + datosEnv[i]);
            System.out.println("datosR: " + datosRec[i]);
        }




        //nota media y descargas
        for (int i = 0; i < nTasks; i++) {
            Random random = new Random();
            notaMedia[i] = random.nextInt(4 - 1) + 1;
            nDescargas[i] = random.nextInt(100000 - 10000) + 10000;
            System.out.println("nmedia: " + notaMedia[i]);
            System.out.println("ndescargas: " + nDescargas[i]);
        }


        //TOMA DE DECISION MULTICRITERIO RIM



        //INICIALIZACIÓN DE LA MATRIZ
        double[][] matriz = new double[8][nTasks];
        matriz[0] = Arrays.copyOf(cpu, cpu.length);
        matriz[1] = Arrays.copyOf(ram, ram.length);
        matriz[2] = Arrays.copyOf(bateria, bateria.length);
        matriz[3] = Arrays.copyOf(notaMedia, notaMedia.length);
        matriz[4] = Arrays.copyOf(nDescargas, nDescargas.length);
        matriz[5] = Arrays.copyOf(datosEnv, datosEnv.length);
        matriz[6] = Arrays.copyOf(datosRec, datosRec.length);
        matriz[7] = Arrays.copyOf(capacidad, capacidad.length);




        //MATRIZ NORMALIZADA Y CON PESOS
        for (int i = 0; i < recursos.length; i++) {
            for (int j = 0; j < nTasks; j++) {

                matriz[i][j] = normalizar(matriz[i][j], recursos[i]);
                System.out.println("matrizNormalizada: " + matriz[i][j]);
                matriz[i][j] = matriz[i][j] * recursos[i].getPeso();
                System.out.println("matrizConPesos: " + matriz[i][j]);
            }
        }

        //CALCULO DE I- I+ Y R
        for (int j = 0; j < nTasks; j++) {
            double iMinus = 0, iMax = 0;
            for (int i = 0; i < recursos.length; i++) {
                iMax += Math.pow(matriz[i][j] - recursos[i].getPeso(), 2);  // recursos de i o de j???
                iMinus += Math.pow(matriz[i][j], 2);
            }
            System.out.println("IMAX: " + iMax);
            System.out.println("IMINUS: " + iMinus);
            iMinus = Math.sqrt(iMinus);
            iMax = Math.sqrt(iMax);
            System.out.println("SQRTI+: " + iMax);
            System.out.println("SQRTI-: " + iMinus);
            r[j] = iMinus / (iMax + iMinus);
        }

        for (int i = 0; i < nTasks; i++) {
            System.out.println("R: " + r[i]);
        }


        final ListView listview = (ListView) findViewById(R.id.listView);
        final ArrayList<String> list = new ArrayList<String>();
        final ArrayList<String> list2 = new ArrayList<String>(recursos.length);
        final Dialog dialog = new Dialog(this);

        final String[] values = new String[nTasks];
        final String[] values2 = new String[] {"","","","","","","",""};
        list2.addAll(Arrays.asList(values2));


        for (int i = 0; i < nTasks; i++) {
            values[i]=((int)(r[i]*100)) + "%    " + nombres[i];
        }


        for (int i = 0; i < values.length; ++i) {
            list.add(values[i]);
        }



        final ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, list);
        listview.setAdapter(adapter);
        final ArrayAdapter adapter2 = new ArrayAdapter(this, android.R.layout.simple_list_item_1, list2);




        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, final View view, final int position, long id) {

                dialog.setContentView(R.layout.dialog);
                dialog.setTitle( nombres[position]);
                ListView listview2 = (ListView) dialog.findViewById(R.id.listView2);



                values2[0]= "CPU: "+String.valueOf(String.format("%.2f", cpu[position]))+ " %";
                values2[1]= "RAM: "+String.valueOf(String.format("%.2f",ram[position]))+ " MB";
                values2[2]= "Bateria: "+String.valueOf(String.format("%.2f",bateria[position])) + " %";
                values2[3]= "Nota media: "+String.valueOf(String.format("%.2f",notaMedia[position]));
                values2[4]= "Nº descargas: "+String.valueOf(String.format("%.0f",nDescargas[position]));
                values2[5]= "Datos Env: "+String.valueOf(String.format("%.2f",datosEnv[position]))+ " kb/s";
                values2[6]= "Datos Rec: "+String.valueOf(String.format("%.2f",datosRec[position]))+ " kb/s";
                values2[7]= "Capacidad: "+String.valueOf(String.format("%.2f",capacidad[position]))+ " MB";


                for (int i = 0; i < values2.length; ++i) {
                    list2.set(i,values2[i]);// values 2 esta vacio a rellenar
                }

                listview2.setAdapter(adapter2);

                dialog.show();


                final Button button = (Button) dialog.findViewById(R.id.aceptarBt);
                //System.out.println(button);
                button.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        dialog.hide();
                    }
                });

                final Button desinstalar = (Button) dialog.findViewById(R.id.desinstalarBt);
                desinstalar.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        //Uri packageURI = Uri.parse("package:"+"your.packagename.here");
                        Uri packageURI = Uri.parse("package:"+packageNames[position]);
                        Intent uninstallIntent = new Intent(Intent.ACTION_DELETE, packageURI);
                        startActivity(uninstallIntent);

                    }
                });

            }
        });
    }

}
