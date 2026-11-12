package coopetica.presentacion;

import coopetica.logica.AsociadoServicio;
import coopetica.modelo.Asociado;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class VentanaPrincipal extends JFrame {
    private AsociadoServicio servicio;

    private JTextField txtCedula;
    private JTextField txtNombre;
    private JTextField txtSaldo;
    private JTextField txtBuscarCedula;

    private JButton btnRegistrar;
    private JButton btnListar;
    private JButton btnResumen;
    private JButton btnBuscar;

    private JTextArea areaResultados;

    public VentanaPrincipal() {
        this.servicio = new AsociadoServicio();
        inicializarComponentes();
    }

    private void inicializarComponentes() {
        setTitle("CoopeTica - Gestión de Asociados | Isaac Gabriel Salas Monge");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));
        panelPrincipal.setBorder(new EmptyBorder(15, 15, 15, 15));

        // Encabezado con nombre del estudiante
        JLabel lblEncabezado = new JLabel("Sistema de Asociados CoopeTica - Estudiante: Isaac Gabriel Salas Monge", SwingConstants.CENTER);
        lblEncabezado.setFont(new Font("SansSerif", Font.BOLD, 14));
        panelPrincipal.add(lblEncabezado, BorderLayout.NORTH);

        // Panel central con formulario y búsqueda
        JPanel panelCentro = new JPanel(new GridLayout(2, 1, 10, 10));

        // Panel de registro
        JPanel panelRegistro = new JPanel(new GridLayout(4, 2, 8, 8));
        panelRegistro.setBorder(BorderFactory.createTitledBorder("Registrar Nuevo Asociado"));

        panelRegistro.add(new JLabel("Cédula:"));
        txtCedula = new JTextField();
        panelRegistro.add(txtCedula);

        panelRegistro.add(new JLabel("Nombre completo:"));
        txtNombre = new JTextField();
        panelRegistro.add(txtNombre);

        panelRegistro.add(new JLabel("Monto de aporte (₡):"));
        txtSaldo = new JTextField();
        panelRegistro.add(txtSaldo);

        btnRegistrar = new JButton("Registrar Asociado");
        btnListar = new JButton("Listar Asociados");
        panelRegistro.add(btnRegistrar);
        panelRegistro.add(btnListar);

        // Panel de búsqueda y resumen
        JPanel panelConsultas = new JPanel(new GridLayout(2, 3, 8, 8));
        panelConsultas.setBorder(BorderFactory.createTitledBorder("Consultas y Resumen"));

        panelConsultas.add(new JLabel("Buscar por Cédula:"));
        txtBuscarCedula = new JTextField();
        panelConsultas.add(txtBuscarCedula);

        btnBuscar = new JButton("Buscar");
        panelConsultas.add(btnBuscar);

        panelConsultas.add(new JLabel("Totales registrados:"));
        btnResumen = new JButton("Ver Resumen");
        panelConsultas.add(btnResumen);
        panelConsultas.add(new JLabel(""));

        panelCentro.add(panelRegistro);
        panelCentro.add(panelConsultas);

        panelPrincipal.add(panelCentro, BorderLayout.CENTER);

        // Área de salida
        areaResultados = new JTextArea(12, 45);
        areaResultados.setEditable(false);
        areaResultados.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane scrollPane = new JScrollPane(areaResultados);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Resultados"));
        panelPrincipal.add(scrollPane, BorderLayout.SOUTH);

        add(panelPrincipal);

        // Eventos (antes del pack)
        btnRegistrar.addActionListener(e -> registrarAsociado());
        btnListar.addActionListener(e -> listarAsociados());
        btnResumen.addActionListener(e -> mostrarResumen());
        btnBuscar.addActionListener(e -> buscarAsociado());

        pack();
        setLocationRelativeTo(null);
    }

    private void registrarAsociado() {
        String cedula = txtCedula.getText();
        String nombre = txtNombre.getText();
        String saldo = txtSaldo.getText();

        try {
            servicio.registrarAsociado(cedula, nombre, saldo);
            JOptionPane.showMessageDialog(this, "Asociado registrado exitosamente.", "Registro Exitoso", JOptionPane.INFORMATION_MESSAGE);
            txtCedula.setText("");
            txtNombre.setText("");
            txtSaldo.setText("");
            listarAsociados();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error de Validación", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void listarAsociados() {
        try {
            ArrayList<Asociado> lista = servicio.listarAsociados();
            areaResultados.setText("");
            if (lista.isEmpty()) {
                areaResultados.setText("No hay asociados registrados actualmente.");
            } else {
                StringBuilder sb = new StringBuilder("=== LISTA DE ASOCIADOS REGISTRADOS ===\n\n");
                for (Asociado a : lista) {
                    sb.append(a.toString()).append("\n");
                }
                areaResultados.setText(sb.toString());
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error al leer los datos: " + ex.getMessage(), "Error de Archivo", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void mostrarResumen() {
        try {
            int cantidad = servicio.obtenerCantidadAsociados();
            double totalAportes = servicio.obtenerSumaAportes();
            String mensaje = String.format("Cantidad de asociados registrados: %d\nSuma total de aportes: ₡%.2f", cantidad, totalAportes);
            areaResultados.setText("=== RESUMEN GENERAL ===\n\n" + mensaje);
            JOptionPane.showMessageDialog(this, mensaje, "Resumen de Asociados", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error al calcular el resumen: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void buscarAsociado() {
        String cedula = txtBuscarCedula.getText();
        try {
            Asociado encontrado = servicio.buscarPorCedula(cedula);
            if (encontrado != null) {
                areaResultados.setText("=== RESULTADO DE BÚSQUEDA ===\n\n" + encontrado.toString());
            } else {
                String msg = "No se encontró ningún asociado con la cédula: " + cedula.trim();
                areaResultados.setText(msg);
                JOptionPane.showMessageDialog(this, msg, "Sin Resultados", JOptionPane.WARNING_MESSAGE);
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error al buscar el asociado: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
