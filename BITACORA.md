# Bitácora Técnica - Tarea 2 (Programación II)

**Estudiante:** Isaac Gabriel Salas Monge  
**Repositorio:** bis04-tarea2-salas-isaac  

## Métodos agregados y ubicación en la arquitectura
1. **`obtenerCantidadAsociados()`** en `coopetica.logica.AsociadoServicio`: Devuelve la cantidad total (`int`) de asociados registrados consultando la capa de datos.
2. **`obtenerSumaAportes()`** en `coopetica.logica.AsociadoServicio`: Recorre la lista de asociados obtenida del DAO y suma sus aportes (`double`).
3. **`buscarPorCedula(String cedula)`** en `coopetica.logica.AsociadoServicio`: Busca un asociado comparando su cédula sin espacios adicionales y retorna el objeto `Asociado` o `null`.

## Justificación arquitectónica
El cálculo de totales, conteo y búsqueda se programaron exclusivamente en `AsociadoServicio` (capa de lógica) y no en `VentanaPrincipal` (capa de presentación) para respetar el principio de separación de responsabilidades. La ventana únicamente captura eventos y muestra resultados. De esta forma, si en el futuro se requiere mostrar estos mismos indicadores en un reporte impreso o en otra pantalla, la lógica de negocio es 100% reutilizable sin duplicar código ni acoplar reglas de negocio a la interfaz gráfica.
