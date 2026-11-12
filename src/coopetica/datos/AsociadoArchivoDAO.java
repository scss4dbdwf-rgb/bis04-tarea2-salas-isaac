package coopetica.datos;

import coopetica.modelo.Asociado;
import java.io.*;
import java.util.ArrayList;

public class AsociadoArchivoDAO {
    private final String RUTA_DIRECTORIO = "datos";
    private final String RUTA_ARCHIVO = "datos/asociados.txt";

    public AsociadoArchivoDAO() {
        File dir = new File(RUTA_DIRECTORIO);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        File archivo = new File(RUTA_ARCHIVO);
        if (!archivo.exists()) {
            try {
                archivo.createNewFile();
            } catch (IOException e) {
                // Manejado en capas superiores
            }
        }
    }

    public void guardar(Asociado asociado) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(RUTA_ARCHIVO, true))) {
            writer.write(asociado.getCedula() + ";" + asociado.getNombre() + ";" + asociado.getSaldo());
            writer.newLine();
        }
    }

    public ArrayList<Asociado> listarTodos() throws IOException {
        ArrayList<Asociado> lista = new ArrayList<>();
        File archivo = new File(RUTA_ARCHIVO);
        if (!archivo.exists()) {
            return lista;
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                if (!linea.trim().isEmpty()) {
                    String[] partes = linea.split(";");
                    if (partes.length == 3) {
                        String cedula = partes[0];
                        String nombre = partes[1];
                        double saldo = Double.parseDouble(partes[2]);
                        lista.add(new Asociado(cedula, nombre, saldo));
                    }
                }
            }
        }
        return lista;
    }
}
