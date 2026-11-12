package coopetica.logica;

import coopetica.datos.AsociadoArchivoDAO;
import coopetica.modelo.Asociado;
import java.io.IOException;
import java.util.ArrayList;

public class AsociadoServicio {
    private AsociadoArchivoDAO dao;

    public AsociadoServicio() {
        this.dao = new AsociadoArchivoDAO();
    }

    public void registrarAsociado(String cedula, String nombre, String saldoTexto) throws Exception {
        // 1. Validación de campos obligatorios
        if (cedula == null || cedula.trim().isEmpty() ||
            nombre == null || nombre.trim().isEmpty() ||
            saldoTexto == null || saldoTexto.trim().isEmpty()) {
            throw new Exception("Todos los campos (cédula, nombre y aporte) son obligatorios.");
        }

        // 2. Validación de saldo numérico
        double saldo;
        try {
            saldo = Double.parseDouble(saldoTexto.trim());
        } catch (NumberFormatException e) {
            throw new Exception("El monto de aporte debe ser un valor numérico válido.");
        }

        // 3. Validación de saldo negativo
        if (saldo < 0) {
            throw new Exception("El monto de aporte no puede ser negativo.");
        }

        // 4. Validación de cédula repetida
        if (buscarPorCedula(cedula.trim()) != null) {
            throw new Exception("Ya existe un asociado registrado con la cédula: " + cedula.trim());
        }

        Asociado nuevo = new Asociado(cedula.trim(), nombre.trim(), saldo);
        dao.guardar(nuevo);
    }

    public ArrayList<Asociado> listarAsociados() throws IOException {
        return dao.listarTodos();
    }

    public int obtenerCantidadAsociados() throws IOException {
        return dao.listarTodos().size();
    }

    public double obtenerSumaAportes() throws IOException {
        ArrayList<Asociado> lista = dao.listarTodos();
        double suma = 0;
        for (Asociado a : lista) {
            suma += a.getSaldo();
        }
        return suma;
    }

    public Asociado buscarPorCedula(String cedula) throws IOException {
        if (cedula == null || cedula.trim().isEmpty()) {
            return null;
        }
        ArrayList<Asociado> lista = dao.listarTodos();
        for (Asociado a : lista) {
            if (a.getCedula().trim().equalsIgnoreCase(cedula.trim())) {
                return a;
            }
        }
        return null;
    }
}
