package yonatan24891.effiapp;

import android.app.ActivityManager;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.os.Debug;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;



public class EvaluarActivity extends AppCompatActivity {

    public void goToEvaluar(View view) {
        Intent intent = new Intent(this, EvaluarActivity.class);
        startActivity(intent);
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
        final int nTasks = foregroundTaskInfo.size();
        int[] taskIds = new int[nTasks];
        List<ActivityManager.RunningAppProcessInfo> foregroundAppProcessesInfo = am.getRunningAppProcesses();
        int[] ProcessesIds = new int[nTasks];
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

        //PROCESSES
        for (int i = 0; i < nTasks; i++) {
            ActivityManager.RunningAppProcessInfo process = foregroundAppProcessesInfo.get(i);
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Task: {0}", process.toString());

            ProcessesIds[i] = process.pid;
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
            System.out.println(nombres[i]);
        }


        //CPU

        for (int i = 0; i < nTasks; i++) {
            Random random = new Random();
            cpu[i] = random.nextFloat() * (13.0f - 1.0f) + 1.0f;
            System.out.println("CPU: " + cpu[i]);
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
            Random random = new Random();
            datosEnv[i] = random.nextInt(15 - 2) + 2;
            datosRec[i] = random.nextInt(15 - 2) + 2;
            System.out.println("datosE: " + datosEnv[i]);
            System.out.println("datosR: " + datosRec[i]);
        }


        //capacidad
        for (int i = 0; i < nTasks; i++) {
            Random random = new Random();
            capacidad[i] = random.nextFloat() * (300.0f - 50.0f) + 50.0f;
            System.out.println("capacidad: " + capacidad[i]);
        }


        //nota media y descargas
        for (int i = 0; i < nTasks; i++) {
            Random random = new Random();
            notaMedia[i] = random.nextInt(4 - 1) + 1;
            nDescargas[i] = random.nextInt(100000 - 10000) + 10000;
            System.out.println("nmedia: " + notaMedia[i]);
            System.out.println("ndescargas: " + nDescargas[i]);
        }


//            Debug.MemoryInfo[] memory = activityManager.getProcessMemoryInfo(new int[]{parseInt((String) appObj.get("pid"))});
//            /** De esta manera, obtenemos el consumo de memoria RAM según http://developer.android.com/tools/debugging/debugging-memory.html#TrackAllocations **/
//            appObj.put("ram", memory[0].getTotalPss());
//            Log.d("TOTAL RAM", String.valueOf(memory[0].getTotalPss()));
//            double cpuUsage = tool.readCPUusagePerProcess(parseInt((String) appObj.get("pid")));
//            //Obtenemos el tráfico de datos
//            received[0] = TrafficStats.getUidRxBytes(Integer.parseInt((appObj.getString("uid"))));  //Obtenemos la cantidad de datos recibidos
//            //Log.d("received BEFORE IF", String.valueOf(received[0]));
//            send[0] = TrafficStats.getUidTxBytes(Integer.parseInt(appObj.getString("uid")));    //Obtenemos la cantidad de datos enviados
//            //Log.d("sent BEFORE IF", String.valueOf(send[0]));
//            if(received[0] == -1) {
//                received[0] = 0;
//            }
//            if(send[0] == -1) {
//                send[0] = 0;
//            }
//
//            receivedData = received[0]/1024;
//            sentData = send[0]/1024;

//            public double readCPUusagePerProcess(int pid) {
//                try {
//
//                    RandomAccessFile readerCPUproc = new RandomAccessFile("proc/"+ pid +"/stat", "r");
//                    String loadCPUproc = readerCPUproc.readLine();
//                    //Log.d("FILE with the stats: ", loadCPUproc);
//                    String[] columnsCPUproc = loadCPUproc.split(" +");
//
//                    long utimeCPUproc = Long.parseLong(columnsCPUproc[13]); //utime - CPU time spent in user code, measured in clock ticks
//                    //Log.d("utime", String.valueOf(utimeCPUproc));
//                    long stimeCPUproc = Long.parseLong(columnsCPUproc[14]);   //CPU time spent in kernel code, measured in clock ticks
//                    //Log.d("stime", String.valueOf(stimeCPUproc));
//                    long cutimeCPUproc = Long.parseLong(columnsCPUproc[17]);   //Waited-for children's CPU time spent in user code (in clock ticks)
//                    long cstimeCPUproc = Long.parseLong(columnsCPUproc[16]);   //Waited-for children's CPU time spent in kernel code (in clock ticks)
//                    long starttimeCPUproc = Long.parseLong(columnsCPUproc[21]);    //Time when the process started, measured in clock ticks
//                    //RandomAccessFile hertzReader = new RandomAccessFile("/sys/devices/system/cpu/cpu0/cpufreq/scaling_cur_freq", "r");
//                    //String loadHertz = hertzReader.readLine();
//                    long hertz = 100; //Long.parseLong(loadHertz); //Number of clock ticks (HERTZ), under assumption of an unmodified Android system
//                    //Log.d("Hertz", String.valueOf(hertz));
//                    long totalTime = utimeCPUproc + stimeCPUproc;   //Tiempo total de uso de la CPU por el proceso
//                    //totalTime = totalTime + cutimeCPUproc + cstimeCPUproc;  //Añadimos al total el tiempo de uso de CPU de los procesos hijo del proceso analizado
//                    RandomAccessFile uptimeReader = new RandomAccessFile("proc/uptime", "r");
//                    String loadUptime = uptimeReader.readLine();
//                    //Log.d("uptime FILE: ", loadUptime);
//                    String[] valuesUptimeReader = loadUptime.split(" +");
//                    double uptime = Double.parseDouble(valuesUptimeReader[0]);   //The uptime of the system (seconds)
//                    //Log.d("UPTIME", String.valueOf(uptime));
//                    //Log.d("STARTTIME", String.valueOf(starttimeCPUproc));
//                    //Log.d("TOTALTIME", String.valueOf(totalTime));
//                    double seconds = (uptime - (starttimeCPUproc / hertz)); //Total elapsed time in seconds since the process started
//                    double cpu_usage = 100 * ((totalTime / hertz) / seconds); //Total CPU usage in percentage
//                    //Log.d("SECONDS", String.valueOf(seconds));
//                    //Log.d("CPU_USAGE", String.valueOf(cpu_usage));
//                    return cpu_usage;
//
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//
//                return 7;
//            }


        //TOMA DE DECISION MULTICRITERIO RIM

        //RANGO DE TRABAJO Y RANGO IDEAL
        final ResourceData[] recursos = new ResourceData[]{
                new ResourceData("CPU", 0, 100, 0, 8),
                new ResourceData("RAM", 0, 1024, 0, 10),
                new ResourceData("Bateria", 0, 100, 0, 10),
                new ResourceData("NotaMedia", 0, 5, 4, 5),
                new ResourceData("NDescargas", 0, 500000000, 100000, 500000000),
                new ResourceData("DatosEnv", 0, 100, 0, 10),
                new ResourceData("DatosRec", 0, 100, 0, 10),
                new ResourceData("Capacidad", 0, 1000, 0, 50)
        };

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
            public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {

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

//                final String item = (String) parent.getItemAtPosition(position);
//                view.animate().setDuration(2000).alpha(0).withEndAction(new Runnable() {
//                    @Override
//                    public void run() {
//                        list.remove(item);
//                        adapter.notifyDataSetChanged();
//                        view.setAlpha(1);
//                    }
//                });
            }
        });
    }

//    private class StableArrayAdapter extends ArrayAdapter<String> {
//
//        HashMap<String, Integer> mIdMap = new HashMap<String, Integer>();
//
//        public StableArrayAdapter(Context context, int textViewResourceId,
//                                  List<String> objects) {
//            super(context, textViewResourceId, objects);
//            for (int i = 0; i < objects.size(); ++i) {
//                mIdMap.put(objects.get(i), i);
//            }
//        }
//
//        @Override
//        public long getItemId(int position) {
//            String item = getItem(position);
//            return mIdMap.get(item);
//        }
//
//        @Override
//        public boolean hasStableIds() {
//            return true;
//        }
//    }
}
