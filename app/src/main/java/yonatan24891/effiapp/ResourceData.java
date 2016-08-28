package yonatan24891.effiapp;

/**
 * Created by miriam on 26/08/16.
 */
public class ResourceData {

    String nombre;
    int minRango;
    int maxRango;
    int minRangoI;
    int maxRangoI;
    double peso=0.125;

    public ResourceData(String nombre, int minRango, int maxRango, int minRangoI, int maxRangoI) {
        this.nombre = nombre;
        this.minRango = minRango;
        this.maxRango = maxRango;
        this.minRangoI = minRangoI;
        this.maxRangoI = maxRangoI;
    }

    public ResourceData(String nombre, int minRango, int maxRango, int minRangoI, int maxRangoI, double peso) {
        this(nombre, minRango, maxRango, minRangoI, maxRangoI);

        this.peso = peso;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getMinRango() {
        return minRango;
    }

    public void setMinRango(int minRango) {
        this.minRango = minRango;
    }

    public int getMaxRango() {
        return maxRango;
    }

    public void setMaxRango(int maxRango) {
        this.maxRango = maxRango;
    }

    public int getMinRangoI() {
        return minRangoI;
    }

    public void setMinRangoI(int minRangoI) {
        this.minRangoI = minRangoI;
    }

    public int getMaxRangoI() {
        return maxRangoI;
    }

    public void setMaxRangoI(int maxRangoI) {
        this.maxRangoI = maxRangoI;
    }
}
