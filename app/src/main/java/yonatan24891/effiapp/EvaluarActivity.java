package yonatan24891.effiapp;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Debug;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EvaluarActivity extends AppCompatActivity {

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
        int nTasks = foregroundTaskInfo.size();
        int[] taskIds = new int[nTasks];
        List<ActivityManager.RunningAppProcessInfo> foregroundAppProcessesInfo = am.getRunningAppProcesses();
        int[] ProcessesIds = new int[nTasks];
        String[] nombres = new String[nTasks];
        double[] ram = new double[nTasks];
        double[] cpu = new double[nTasks];
        double[] bateria = new double[nTasks];
        double[] notaMedia = new double[nTasks];
        double[] nDescargas = new double[nTasks];
        double[] datosEnv = new double[nTasks];
        double[] datosRec = new double[nTasks];
        double[] capacidad = new double[nTasks];
        double[] r = new double[nTasks];

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

        //RAM
        Debug.MemoryInfo[] memoryInfo = am.getProcessMemoryInfo(ProcessesIds);
        for (int i = 0; i < nTasks; i++) {
            //System.out.println(memoryInfo[i].getTotalPss()/1024.0);
            ram[i] = memoryInfo[i].getTotalPss() / 1024.0;
            System.out.println("RAM: " + ram[i]);
           /* System.out.println(memoryInfo[i].dalvikPrivateDirty);
            System.out.println(memoryInfo[i].otherPss);*/
        }

        //CPU

        for (int i = 0; i < nTasks; i++) {
            Random random = new Random();
            cpu[i] = random.nextFloat() * (13.0f - 1.0f) + 1.0f;
            System.out.println("CPU: " + cpu[i]);
        }
        //bateria
        for (int i = 0; i < nTasks; i++) {
            Random random = new Random();
            bateria[i] = random.nextInt(30 - 10) + 10;
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
            capacidad[i] = random.nextInt(300 - 100) + 100;
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

        double[][] matriz = new double[8][nTasks];
        matriz[0] = Arrays.copyOf(cpu, cpu.length);
        matriz[1] = Arrays.copyOf(ram, ram.length);
        matriz[2] = Arrays.copyOf(bateria, bateria.length);
        matriz[3] = Arrays.copyOf(notaMedia, notaMedia.length);
        matriz[4] = Arrays.copyOf(nDescargas, nDescargas.length);
        matriz[5] = Arrays.copyOf(datosEnv, datosEnv.length);
        matriz[6] = Arrays.copyOf(datosRec, datosRec.length);
        matriz[7] = Arrays.copyOf(capacidad, capacidad.length);


        ResourceData[] recursos = new ResourceData[]{
                new ResourceData("CPU", 0, 100, 0, 8),
                new ResourceData("RAM", 0, 1024, 0, 10),
                new ResourceData("Bateria", 0, 100, 0, 10),
                new ResourceData("NotaMedia", 0, 5, 4, 5),
                new ResourceData("NDescargas", 0, 500000000, 100000, 500000000),
                new ResourceData("DatosEnv", 0, 100, 0, 10),
                new ResourceData("DatosRec", 0, 100, 0, 10),
                new ResourceData("Capacidad", 0, 1000, 0, 50)
        };

        /*//[A,B]
        int[] rangoCPU = new int[] {0, 100};//%n
        int[] rangoRAM = new int[] {0, 1024};//MB
        int[] rangoBateria = new int[] {0, 100};
        int[] rangoNotaMedia = new int[] {0, 5};
        int[] rangoNDescargas = new int[] {0, 500000000};
        int[] rangoDatosEnv = new int[] {0, 100};//Kb/s
        int[] rangoDatosRec = new int[] {0, 100};//Kb/s
        int[] rangoCapacidad = new int[] {0, 1000};//MB
        //[C,D]
        int[] rangoICPU = new int[] {0, 8};
        int[] rangoIRAM = new int[] {0, 10};
        int[] rangoIBateria = new int[] {0, 10};
        int[] rangoINotaMedia = new int[] {4, 5};
        int[] rangoIDescargas = new int[] {100000, 500000000};
        int[] rangoIDatosEnv = new int[] {0, 10};
        int[] rangoIDatosRec = new int[] {0, 10};
        int[] rangoICapacidad = new int[] {0, 50};
        */

        for (int i = 0; i < recursos.length; i++) {
            for (int j = 0; j < nTasks; j++) {

                matriz[i][j] = normalizar(matriz[i][j], recursos[i]);
                System.out.println("matrizNormalizada: " + matriz[i][j]);
                matriz[i][j] = matriz[i][j] * recursos[i].getPeso();
                System.out.println("matrizConPesos: " + matriz[i][j]);
            }
        }

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
        String[] values = new String[nTasks];

        for (int i = 0; i < nTasks; i++) {
            //values[i]=(nombres[i]+"         "+ ((int)(r[i]*100)));
            values[i]=((int)(r[i]*100)) + "%    " + nombres[i];
        }

        final ArrayList<String> list = new ArrayList<String>();
        for (int i = 0; i < values.length; ++i) {
            list.add(values[i]);
        }
        final ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, list);
        listview.setAdapter(adapter);

//       //
//            @Override
//            public void onItemClick(AdapterView<?> parent, final View view,
//                                    int position, long id) {
//                final String item = (String) parent.getItemAtPosition(position);
//                view.animate().setDuration(2000).alpha(0)
//                        .withEndAction(new Runnable() {
//                            @Override
//                            public void run() {
//                                list.remove(item);
//                                adapter.notifyDataSetChanged();
//                                view.setAlpha(1);
//                            }
//                        });
//            }
//
//        });
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
