import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    static ArrayList<Astronauta> listaAstronautas = new ArrayList<>();
    static ArrayList<Mision> listaMisiones = new ArrayList<>();
    static int contadorAstronauta = 1;
    static int contadorMision = 1;
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        int opcion = 0;
        do {
            mostrarMenuPrincipal();
            opcion = leerEnteroValido("  Ingrese una opción: ");
            switch (opcion) {
                case 1:
                    menuAstronautas();
                    break;
                case 2:
                    menuMisiones();
                    break;
                case 3:
                    menuReportes();
                    break;
                case 4:
                    System.out.println("\n  ¡Hasta luego! Cerrando el sistema...\n");
                    break;
                default:
                    System.out.println("\n  [ERROR] Opción inválida. Ingrese un número entre 1 y 4.\n");
            }
        } while (opcion != 4);
    }

    // =========================================================
    //  MENÚ PRINCIPAL
    // =========================================================

    static void mostrarMenuPrincipal() {
        System.out.println("\n╔══════════════════════════════════════════╗");
        System.out.println("║     SISTEMA DE GESTIÓN ESPACIAL          ║");
        System.out.println("╠══════════════════════════════════════════╣");
        System.out.println("║  1. Gestión de Astronautas               ║");
        System.out.println("║  2. Gestión de Misiones                  ║");
        System.out.println("║  3. Módulo de Reportes                   ║");
        System.out.println("║  4. Salir                                ║");
        System.out.println("╚══════════════════════════════════════════╝");
    }

    // =========================================================
    //  MÓDULO ASTRONAUTAS
    // =========================================================

    static void menuAstronautas() {
        int opcion = 0;
        do {
            System.out.println("\n╔══════════════════════════════════════════╗");
            System.out.println("║       GESTIÓN DE ASTRONAUTAS             ║");
            System.out.println("╠══════════════════════════════════════════╣");
            System.out.println("║  1. Agregar astronauta                   ║");
            System.out.println("║  2. Actualizar datos de un astronauta    ║");
            System.out.println("║  3. Cambiar estado de un astronauta      ║");
            System.out.println("║  4. Eliminar un astronauta               ║");
            System.out.println("║  5. Regresar                             ║");
            System.out.println("╚══════════════════════════════════════════╝");
            opcion = leerEnteroValido("  Ingrese una opción: ");
            switch (opcion) {
                case 1: agregarAstronauta(); break;
                case 2: actualizarAstronauta(); break;
                case 3: cambiarEstadoAstronauta(); break;
                case 4: eliminarAstronauta(); break;
                case 5: System.out.println("\n  Regresando al menú principal...\n"); break;
                default: System.out.println("\n  [ERROR] Opción inválida. Ingrese un número entre 1 y 5.\n");
            }
        } while (opcion != 5);
    }

    static void agregarAstronauta() {
        System.out.println("\n--- AGREGAR ASTRONAUTA ---");
        String id = generarIdAstronauta();

        String nombre     = leerTextoValido("  Nombre (mín. 3 caracteres): ", 3);
        String cedula     = leerCedulaUnica();
        String profesion  = seleccionarProfesion();
        String estado     = seleccionarEstado();
        String pais       = leerTextoValido("  País de origen (mín. 3 caracteres): ", 3);
        double salario    = leerSalarioValido();

        Astronauta nuevo = new Astronauta(id, nombre, cedula, profesion, estado, pais, salario);
        listaAstronautas.add(nuevo);
        contadorAstronauta++;
        System.out.println("\n  [OK] Astronauta registrado exitosamente con ID: " + id + "\n");
    }

    static void actualizarAstronauta() {
        System.out.println("\n--- ACTUALIZAR ASTRONAUTA ---");
        String id = leerTextoSimple("  Ingrese el ID del astronauta a actualizar: ").toUpperCase().trim();
        Astronauta ast = buscarAstronautaPorId(id);
        if (ast == null) {
            System.out.println("\n  [INFO] No se encontró un astronauta con el ID: " + id + "\n");
            return;
        }
        System.out.println("\n  Astronauta encontrado: " + ast.getNombre());
        System.out.println("  (Deje en blanco y presione Enter para mantener el valor actual)\n");

        String nombre = leerTextoOpcional("  Nuevo nombre [" + ast.getNombre() + "]: ", 3);
        if (!nombre.isEmpty()) ast.setNombre(nombre);

        String cedula = leerCedulaOpcionalUnica(ast.getCedula());
        if (!cedula.isEmpty()) ast.setCedula(cedula);

        System.out.println("  Nueva profesión [" + ast.getProfesion() + "] (0 = no cambiar): ");
        String profesion = seleccionarProfesionOpcional();
        if (!profesion.isEmpty()) ast.setProfesion(profesion);

        System.out.println("  Nuevo estado [" + ast.getEstado() + "] (0 = no cambiar): ");
        String estado = seleccionarEstadoOpcional();
        if (!estado.isEmpty()) ast.setEstado(estado);

        String pais = leerTextoOpcional("  Nuevo país de origen [" + ast.getPaisOrigen() + "]: ", 3);
        if (!pais.isEmpty()) ast.setPaisOrigen(pais);

        String salInput = leerTextoSimple("  Nuevo salario [" + ast.getSalario() + "] (0 = no cambiar): ").trim();
        if (!salInput.isEmpty() && !salInput.equals("0")) {
            try {
                double sal = Double.parseDouble(salInput);
                if (sal > 0) {
                    ast.setSalario(sal);
                    recalcularCostosMisionesConAstronauta(id);
                } else {
                    System.out.println("  [AVISO] Salario no válido, se conserva el valor anterior.");
                }
            } catch (NumberFormatException e) {
                System.out.println("  [AVISO] Valor no numérico, se conserva el salario anterior.");
            }
        }

        System.out.println("\n  [OK] Astronauta actualizado exitosamente.\n");
    }

    static void cambiarEstadoAstronauta() {
        System.out.println("\n--- CAMBIAR ESTADO DE ASTRONAUTA ---");
        String id = leerTextoSimple("  Ingrese el ID del astronauta: ").toUpperCase().trim();
        Astronauta ast = buscarAstronautaPorId(id);
        if (ast == null) {
            System.out.println("\n  [INFO] No se encontró un astronauta con el ID: " + id + "\n");
            return;
        }
        System.out.println("  Estado actual: " + ast.getEstado());
        String nuevoEstado = seleccionarEstado();
        ast.setEstado(nuevoEstado);
        System.out.println("\n  [OK] Estado actualizado a: " + nuevoEstado + "\n");
    }

    static void eliminarAstronauta() {
        System.out.println("\n--- ELIMINAR ASTRONAUTA ---");
        String id = leerTextoSimple("  Ingrese el ID del astronauta a eliminar: ").toUpperCase().trim();
        Astronauta ast = buscarAstronautaPorId(id);
        if (ast == null) {
            System.out.println("\n  [INFO] No se encontró un astronauta con el ID: " + id + "\n");
            return;
        }
        if (astronautaEnMision(id)) {
            System.out.println("\n  [INFO] No se puede eliminar. El astronauta " + id +
                               " está asociado a una misión registrada.\n");
            return;
        }
        listaAstronautas.remove(ast);
        System.out.println("\n  [OK] Astronauta " + id + " eliminado exitosamente.\n");
    }

    // =========================================================
    //  MÓDULO MISIONES
    // =========================================================

    static void menuMisiones() {
        int opcion = 0;
        do {
            System.out.println("\n╔══════════════════════════════════════════╗");
            System.out.println("║         GESTIÓN DE MISIONES              ║");
            System.out.println("╠══════════════════════════════════════════╣");
            System.out.println("║  1. Agregar misión                       ║");
            System.out.println("║  2. Actualizar datos de una misión       ║");
            System.out.println("║  3. Eliminar una misión                  ║");
            System.out.println("║  4. Regresar                             ║");
            System.out.println("╚══════════════════════════════════════════╝");
            opcion = leerEnteroValido("  Ingrese una opción: ");
            switch (opcion) {
                case 1: agregarMision(); break;
                case 2: actualizarMision(); break;
                case 3: eliminarMision(); break;
                case 4: System.out.println("\n  Regresando al menú principal...\n"); break;
                default: System.out.println("\n  [ERROR] Opción inválida. Ingrese un número entre 1 y 4.\n");
            }
        } while (opcion != 4);
    }

    static void agregarMision() {
        System.out.println("\n--- AGREGAR MISIÓN ---");

        boolean hayActivo = false;
        for (Astronauta a : listaAstronautas) {
            if (a.getEstado().equalsIgnoreCase("Activo")) { hayActivo = true; break; }
        }
        if (!hayActivo) {
            System.out.println("\n  [INFO] No hay astronautas activos registrados. Registre al menos uno antes de crear una misión.\n");
            return;
        }

        String id              = generarIdMision();
        String nombre          = leerTextoValido("  Nombre de la misión (mín. 3 caracteres): ", 3);
        String tipoMision      = seleccionarTipoMision();
        String tipoNave        = seleccionarTipoNave();
        int duracion           = leerDuracionValida();
        boolean expPrevios     = seleccionarSiNo("  ¿Tuvo experimentos previos?");

        Mision nueva = new Mision(id, nombre, tipoMision, tipoNave, duracion, expPrevios);

        agregarAstronautasAMision(nueva);

        if (nueva.getAstronautas().isEmpty()) {
            System.out.println("\n  [INFO] La misión no fue registrada porque no tiene astronautas asociados.\n");
            return;
        }

        nueva.calcularCostoMision();
        listaMisiones.add(nueva);
        contadorMision++;
        System.out.println("\n  [OK] Misión registrada con ID: " + id);
        System.out.println("  Costo total calculado: $" + String.format("%,.2f", nueva.getCostoTotal()) + "\n");
    }

    static void agregarAstronautasAMision(Mision mision) {
        System.out.println("\n  -- Asociar astronautas a la misión --");
        System.out.println("  (Solo se aceptan astronautas activos. Ingrese 'FIN' para terminar.)");

        boolean continuar = true;
        while (continuar) {
            mostrarAstronautasActivos();
            String idAst = leerTextoSimple("  ID del astronauta a agregar (o 'FIN' para terminar): ").toUpperCase().trim();
            if (idAst.equals("FIN")) {
                if (mision.getAstronautas().isEmpty()) {
                    System.out.println("  [ERROR] Debe agregar al menos un astronauta. Ingrese un ID válido.");
                } else {
                    continuar = false;
                }
                continue;
            }
            Astronauta ast = buscarAstronautaPorId(idAst);
            if (ast == null) {
                System.out.println("  [ERROR] No existe un astronauta con el ID: " + idAst);
            } else if (!ast.getEstado().equalsIgnoreCase("Activo")) {
                System.out.println("  [ERROR] El astronauta " + idAst + " no está activo.");
            } else if (mision.tieneAstronauta(idAst)) {
                System.out.println("  [ERROR] El astronauta " + idAst + " ya fue agregado a esta misión.");
            } else {
                mision.agregarAstronauta(ast);
                System.out.println("  [OK] Astronauta " + ast.getNombre() + " agregado a la misión.");
            }
        }
    }

    static void actualizarMision() {
        System.out.println("\n--- ACTUALIZAR MISIÓN ---");
        String id = leerTextoSimple("  Ingrese el ID de la misión a actualizar: ").toUpperCase().trim();
        Mision mis = buscarMisionPorId(id);
        if (mis == null) {
            System.out.println("\n  [INFO] No se encontró una misión con el ID: " + id + "\n");
            return;
        }
        System.out.println("\n  Misión encontrada: " + mis.getNombre());
        System.out.println("  (Deje en blanco y presione Enter para mantener el valor actual)\n");

        String nombre = leerTextoOpcional("  Nuevo nombre [" + mis.getNombre() + "]: ", 3);
        if (!nombre.isEmpty()) mis.setNombre(nombre);

        System.out.println("  Tipo de misión actual: " + mis.getTipoMision() + " (0 = no cambiar)");
        String tipoM = seleccionarTipoMisionOpcional();
        if (!tipoM.isEmpty()) mis.setTipoMision(tipoM);

        System.out.println("  Tipo de nave actual: " + mis.getTipoNave() + " (0 = no cambiar)");
        String tipoN = seleccionarTipoNaveOpcional();
        if (!tipoN.isEmpty()) mis.setTipoNave(tipoN);

        String durInput = leerTextoSimple("  Nueva duración en días [" + mis.getDuracion() + "] (0 = no cambiar): ").trim();
        if (!durInput.isEmpty() && !durInput.equals("0")) {
            try {
                int dur = Integer.parseInt(durInput);
                if (dur > 0) {
                    mis.setDuracion(dur);
                } else {
                    System.out.println("  [AVISO] Duración no válida, se conserva el valor anterior.");
                }
            } catch (NumberFormatException e) {
                System.out.println("  [AVISO] Valor no numérico, se conserva la duración anterior.");
            }
        }

        System.out.print("  ¿Desea actualizar los experimentos previos? [" + (mis.isExperimentosPrevios() ? "Sí" : "No") + "] (s/n): ");
        String respExp = scanner.nextLine().trim().toLowerCase();
        if (respExp.equals("s")) {
            boolean expPrevios = seleccionarSiNo("  ¿Tuvo experimentos previos?");
            mis.setExperimentosPrevios(expPrevios);
        }

        System.out.print("  ¿Desea actualizar la lista de astronautas? (s/n): ");
        String respAst = scanner.nextLine().trim().toLowerCase();
        if (respAst.equals("s")) {
            mis.getAstronautas().clear();
            agregarAstronautasAMision(mis);
            if (mis.getAstronautas().isEmpty()) {
                System.out.println("  [AVISO] No se agregaron astronautas, la actualización de astronautas fue cancelada.");
            }
        }

        mis.calcularCostoMision();
        System.out.println("\n  [OK] Misión actualizada. Nuevo costo total: $" + String.format("%,.2f", mis.getCostoTotal()) + "\n");
    }

    static void eliminarMision() {
        System.out.println("\n--- ELIMINAR MISIÓN ---");
        String id = leerTextoSimple("  Ingrese el ID de la misión a eliminar: ").toUpperCase().trim();
        Mision mis = buscarMisionPorId(id);
        if (mis == null) {
            System.out.println("\n  [INFO] No se encontró una misión con el ID: " + id + "\n");
            return;
        }
        listaMisiones.remove(mis);
        System.out.println("\n  [OK] Misión " + id + " eliminada exitosamente.\n");
    }

    // =========================================================
    //  MÓDULO REPORTES
    // =========================================================

    static void menuReportes() {
        int opcion = 0;
        do {
            System.out.println("\n╔══════════════════════════════════════════╗");
            System.out.println("║          MÓDULO DE REPORTES              ║");
            System.out.println("╠══════════════════════════════════════════╣");
            System.out.println("║  1. Mostrar astronautas                  ║");
            System.out.println("║  2. Buscar astronauta                    ║");
            System.out.println("║  3. Mostrar misiones                     ║");
            System.out.println("║  4. Mostrar misión con mayor costo       ║");
            System.out.println("║  5. Buscar misiones por tipo de nave     ║");
            System.out.println("║  6. Regresar                             ║");
            System.out.println("╚══════════════════════════════════════════╝");
            opcion = leerEnteroValido("  Ingrese una opción: ");
            switch (opcion) {
                case 1: reporteMostrarAstronautas(); break;
                case 2: reporteBuscarAstronauta(); break;
                case 3: reporteMostrarMisiones(); break;
                case 4: reporteMisionMayorCosto(); break;
                case 5: reporteBuscarMisionesPorNave(); break;
                case 6: System.out.println("\n  Regresando al menú principal...\n"); break;
                default: System.out.println("\n  [ERROR] Opción inválida. Ingrese un número entre 1 y 6.\n");
            }
        } while (opcion != 6);
    }

    static void reporteMostrarAstronautas() {
        System.out.println("\n--- REPORTE: TODOS LOS ASTRONAUTAS ---");
        if (listaAstronautas.isEmpty()) {
            System.out.println("  [INFO] No hay astronautas registrados en el sistema.\n");
            return;
        }
        for (Astronauta a : listaAstronautas) {
            System.out.println(a);
        }
    }

    static void reporteBuscarAstronauta() {
        System.out.println("\n--- BUSCAR ASTRONAUTA ---");
        String id = leerTextoSimple("  Ingrese el ID del astronauta: ").toUpperCase().trim();
        Astronauta ast = buscarAstronautaPorId(id);
        if (ast == null) {
            System.out.println("\n  [INFO] No se encontró un astronauta con el ID: " + id + "\n");
        } else {
            System.out.println(ast);
        }
    }

    static void reporteMostrarMisiones() {
        System.out.println("\n--- REPORTE: TODAS LAS MISIONES ---");
        if (listaMisiones.isEmpty()) {
            System.out.println("  [INFO] No hay misiones registradas en el sistema.\n");
            return;
        }
        for (Mision m : listaMisiones) {
            System.out.println(m);
        }
    }

    static void reporteMisionMayorCosto() {
        System.out.println("\n--- MISIÓN CON MAYOR COSTO ---");
        if (listaMisiones.isEmpty()) {
            System.out.println("  [INFO] No hay misiones registradas en el sistema.\n");
            return;
        }
        Mision mayor = listaMisiones.get(0);
        for (int i = 1; i < listaMisiones.size(); i++) {
            if (listaMisiones.get(i).getCostoTotal() > mayor.getCostoTotal()) {
                mayor = listaMisiones.get(i);
            }
        }
        System.out.println(mayor);
    }

    static void reporteBuscarMisionesPorNave() {
        System.out.println("\n--- BUSCAR MISIONES POR TIPO DE NAVE ---");
        String tipoNave = seleccionarTipoNave();
        boolean encontrado = false;
        for (Mision m : listaMisiones) {
            if (m.getTipoNave().equalsIgnoreCase(tipoNave)) {
                System.out.println(m);
                encontrado = true;
            }
        }
        if (!encontrado) {
            System.out.println("\n  [INFO] No se encontraron misiones con nave tipo: " + tipoNave + "\n");
        }
    }

    // =========================================================
    //  MÉTODOS AUXILIARES DE BÚSQUEDA
    // =========================================================

    static Astronauta buscarAstronautaPorId(String id) {
        for (Astronauta a : listaAstronautas) {
            if (a.getIdAstronauta().equalsIgnoreCase(id)) return a;
        }
        return null;
    }

    static Mision buscarMisionPorId(String id) {
        for (Mision m : listaMisiones) {
            if (m.getIdMision().equalsIgnoreCase(id)) return m;
        }
        return null;
    }

    static boolean astronautaEnMision(String idAstronauta) {
        for (Mision m : listaMisiones) {
            if (m.tieneAstronauta(idAstronauta)) return true;
        }
        return false;
    }

    static void recalcularCostosMisionesConAstronauta(String idAstronauta) {
        for (Mision m : listaMisiones) {
            if (m.tieneAstronauta(idAstronauta)) {
                m.calcularCostoMision();
            }
        }
    }

    static void mostrarAstronautasActivos() {
        System.out.println("  -- Astronautas activos disponibles --");
        boolean hayActivos = false;
        for (Astronauta a : listaAstronautas) {
            if (a.getEstado().equalsIgnoreCase("Activo")) {
                System.out.println("    [" + a.getIdAstronauta() + "] " + a.getNombre() + " | Salario: $" + String.format("%,.2f", a.getSalario()));
                hayActivos = true;
            }
        }
        if (!hayActivos) System.out.println("    (Ninguno disponible)");
    }

    // =========================================================
    //  GENERADORES DE ID
    // =========================================================

    static String generarIdAstronauta() {
        return String.format("AST-%02d", contadorAstronauta);
    }

    static String generarIdMision() {
        return String.format("MIS-%02d", contadorMision);
    }

    // =========================================================
    //  MÉTODOS DE LECTURA Y VALIDACIÓN
    // =========================================================

    static int leerEnteroValido(String mensaje) {
        while (true) {
            try {
                System.out.print(mensaje);
                String linea = scanner.nextLine().trim();
                return Integer.parseInt(linea);
            } catch (NumberFormatException e) {
                System.out.println("  [ERROR] Entrada inválida. Debe ingresar un número entero.");
            }
        }
    }

    static String leerTextoSimple(String mensaje) {
        System.out.print(mensaje);
        return scanner.nextLine();
    }

    static String leerTextoValido(String mensaje, int minCaracteres) {
        while (true) {
            System.out.print(mensaje);
            String valor = scanner.nextLine().trim();
            if (valor.isEmpty()) {
                System.out.println("  [ERROR] El campo no puede estar vacío.");
            } else if (valor.length() < minCaracteres) {
                System.out.println("  [ERROR] El valor debe tener al menos " + minCaracteres + " caracteres.");
            } else {
                return valor;
            }
        }
    }

    static String leerTextoOpcional(String mensaje, int minCaracteres) {
        System.out.print(mensaje);
        String valor = scanner.nextLine().trim();
        if (valor.isEmpty()) return "";
        if (valor.length() < minCaracteres) {
            System.out.println("  [AVISO] Valor muy corto (mín. " + minCaracteres + " caracteres), se ignora el cambio.");
            return "";
        }
        return valor;
    }

    static String leerCedulaUnica() {
        while (true) {
            System.out.print("  Cédula (exactamente 9 dígitos): ");
            String cedula = scanner.nextLine().trim();
            if (!cedula.matches("\\d{9}")) {
                System.out.println("  [ERROR] La cédula debe contener exactamente 9 dígitos numéricos.");
                continue;
            }
            boolean duplicada = false;
            for (Astronauta a : listaAstronautas) {
                if (a.getCedula().equals(cedula)) { duplicada = true; break; }
            }
            if (duplicada) {
                System.out.println("  [ERROR] Ya existe un astronauta registrado con esa cédula.");
            } else {
                return cedula;
            }
        }
    }

    static String leerCedulaOpcionalUnica(String cedulaActual) {
        System.out.print("  Nueva cédula [" + cedulaActual + "] (Enter para no cambiar): ");
        String cedula = scanner.nextLine().trim();
        if (cedula.isEmpty()) return "";
        if (!cedula.matches("\\d{9}")) {
            System.out.println("  [AVISO] Cédula inválida (debe tener 9 dígitos), se conserva la actual.");
            return "";
        }
        for (Astronauta a : listaAstronautas) {
            if (a.getCedula().equals(cedula) && !cedula.equals(cedulaActual)) {
                System.out.println("  [AVISO] Ya existe un astronauta con esa cédula, se conserva la actual.");
                return "";
            }
        }
        return cedula;
    }

    static double leerSalarioValido() {
        while (true) {
            try {
                System.out.print("  Salario en dólares (mayor que 0): ");
                String linea = scanner.nextLine().trim();
                if (linea.isEmpty()) {
                    System.out.println("  [ERROR] El salario no puede estar vacío.");
                    continue;
                }
                double sal = Double.parseDouble(linea);
                if (sal <= 0) {
                    System.out.println("  [ERROR] El salario debe ser mayor que 0.");
                } else {
                    return sal;
                }
            } catch (NumberFormatException e) {
                System.out.println("  [ERROR] Entrada inválida. Ingrese un valor numérico para el salario.");
            }
        }
    }

    static int leerDuracionValida() {
        while (true) {
            try {
                System.out.print("  Duración de la misión en días (entero mayor que 0): ");
                String linea = scanner.nextLine().trim();
                if (linea.isEmpty()) {
                    System.out.println("  [ERROR] La duración no puede estar vacía.");
                    continue;
                }
                if (linea.contains(".") || linea.contains(",")) {
                    System.out.println("  [ERROR] La duración debe ser un número entero, no se aceptan decimales.");
                    continue;
                }
                int dur = Integer.parseInt(linea);
                if (dur <= 0) {
                    System.out.println("  [ERROR] La duración debe ser mayor que 0.");
                } else {
                    return dur;
                }
            } catch (NumberFormatException e) {
                System.out.println("  [ERROR] Entrada inválida. Ingrese un número entero para la duración.");
            }
        }
    }

    static String seleccionarProfesion() {
        while (true) {
            System.out.println("  Profesión:");
            System.out.println("    1. Químico");
            System.out.println("    2. Físico");
            System.out.println("    3. Ingeniero");
            int op = leerEnteroValido("  Seleccione (1-3): ");
            switch (op) {
                case 1: return "Químico";
                case 2: return "Físico";
                case 3: return "Ingeniero";
                default: System.out.println("  [ERROR] Opción inválida. Seleccione 1, 2 o 3.");
            }
        }
    }

    static String seleccionarProfesionOpcional() {
        System.out.println("    1. Químico");
        System.out.println("    2. Físico");
        System.out.println("    3. Ingeniero");
        System.out.println("    0. No cambiar");
        try {
            System.out.print("  Seleccione (0-3): ");
            String linea = scanner.nextLine().trim();
            int op = Integer.parseInt(linea);
            switch (op) {
                case 1: return "Químico";
                case 2: return "Físico";
                case 3: return "Ingeniero";
                default: return "";
            }
        } catch (NumberFormatException e) {
            return "";
        }
    }

    static String seleccionarEstado() {
        while (true) {
            System.out.println("  Estado:");
            System.out.println("    1. Activo");
            System.out.println("    2. Inactivo");
            int op = leerEnteroValido("  Seleccione (1-2): ");
            switch (op) {
                case 1: return "Activo";
                case 2: return "Inactivo";
                default: System.out.println("  [ERROR] Opción inválida. Seleccione 1 o 2.");
            }
        }
    }

    static String seleccionarEstadoOpcional() {
        System.out.println("    1. Activo");
        System.out.println("    2. Inactivo");
        System.out.println("    0. No cambiar");
        try {
            System.out.print("  Seleccione (0-2): ");
            String linea = scanner.nextLine().trim();
            int op = Integer.parseInt(linea);
            switch (op) {
                case 1: return "Activo";
                case 2: return "Inactivo";
                default: return "";
            }
        } catch (NumberFormatException e) {
            return "";
        }
    }

    static String seleccionarTipoMision() {
        while (true) {
            System.out.println("  Tipo de misión:");
            System.out.println("    1. Microgravedad");
            System.out.println("    2. Biología humana");
            System.out.println("    3. Física de materiales");
            int op = leerEnteroValido("  Seleccione (1-3): ");
            switch (op) {
                case 1: return "Microgravedad";
                case 2: return "Biología humana";
                case 3: return "Física de materiales";
                default: System.out.println("  [ERROR] Opción inválida. Seleccione 1, 2 o 3.");
            }
        }
    }

    static String seleccionarTipoMisionOpcional() {
        System.out.println("    1. Microgravedad");
        System.out.println("    2. Biología humana");
        System.out.println("    3. Física de materiales");
        System.out.println("    0. No cambiar");
        try {
            System.out.print("  Seleccione (0-3): ");
            String linea = scanner.nextLine().trim();
            int op = Integer.parseInt(linea);
            switch (op) {
                case 1: return "Microgravedad";
                case 2: return "Biología humana";
                case 3: return "Física de materiales";
                default: return "";
            }
        } catch (NumberFormatException e) {
            return "";
        }
    }

    static String seleccionarTipoNave() {
        while (true) {
            System.out.println("  Tipo de nave:");
            System.out.println("    1. Automática");
            System.out.println("    2. Manual");
            int op = leerEnteroValido("  Seleccione (1-2): ");
            switch (op) {
                case 1: return "Automatica";
                case 2: return "Manual";
                default: System.out.println("  [ERROR] Opción inválida. Seleccione 1 o 2.");
            }
        }
    }

    static String seleccionarTipoNaveOpcional() {
        System.out.println("    1. Automática");
        System.out.println("    2. Manual");
        System.out.println("    0. No cambiar");
        try {
            System.out.print("  Seleccione (0-2): ");
            String linea = scanner.nextLine().trim();
            int op = Integer.parseInt(linea);
            switch (op) {
                case 1: return "Automatica";
                case 2: return "Manual";
                default: return "";
            }
        } catch (NumberFormatException e) {
            return "";
        }
    }

    static boolean seleccionarSiNo(String pregunta) {
        while (true) {
            System.out.println(pregunta);
            System.out.println("    1. Sí");
            System.out.println("    2. No");
            int op = leerEnteroValido("  Seleccione (1-2): ");
            switch (op) {
                case 1: return true;
                case 2: return false;
                default: System.out.println("  [ERROR] Opción inválida. Seleccione 1 o 2.");
            }
        }
    }
}
