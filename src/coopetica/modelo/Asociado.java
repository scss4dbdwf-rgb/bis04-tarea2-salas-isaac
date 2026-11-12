package coopetica.modelo;

public class Asociado {
    private String cedula;
    private String nombre;
    private double saldo;

    public Asociado() {
    }

    public Asociado(String cedula, String nombre, double saldo) {
        this.cedula = cedula;
        this.nombre = nombre;
        this.saldo = saldo;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    @Override
    public String toString() {
        return "Cédula: " + cedula + " | Nombre: " + nombre + " | Aporte: ₡" + String.format("%.2f", saldo);
    }
}
