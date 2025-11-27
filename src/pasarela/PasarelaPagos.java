package pasarela;

import java.util.Scanner;

/**
 * Pasarela de pagos en consola que permite realizar el cobro de un importe
 * mediante efectivo, cargo en cuenta bancaria (CCC) o tarjeta de crédito.
 *
 * El programa valida las entradas del usuario y no termina hasta que
 * se realiza un pago correcto.
 */
public class PasarelaPagos {

    /**
     * Punto de entrada de la aplicación.
     *
     * @param args Argumentos de línea de comandos (no se utilizan).
     */
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        double importeAPagar = solicitarImporte(scanner);
        boolean pagoRealizado = false;

        while (!pagoRealizado) {

            int opcionSeleccionada = mostrarMenu(scanner, importeAPagar);

            switch (opcionSeleccionada) {
                case 1:
                    realizarPagoEnEfectivo(scanner, importeAPagar);
                    pagoRealizado = true;
                    break;

                case 2:
                    pagoRealizado = realizarPagoCuentaBancaria(scanner, importeAPagar);
                    break;

                case 3:
                    pagoRealizado = realizarPagoTarjeta(scanner, importeAPagar);
                    break;

                default:
                    System.out.println("\nOpción incorrecta. Vuelva a intentarlo....\n");
            }
        }

        System.out.println("GRACIAS POR SU VISITA!");
        scanner.close();
    }

    // ---------------------------------------------------------------------
    // 1) PEDIR IMPORTE
    // ---------------------------------------------------------------------

    /**
     * Solicita al usuario el importe a cobrar. No permite valores menores
     * o iguales a cero.
     *
     * @param scanner Scanner ya creado sobre la entrada estándar.
     * @return Importe válido a cobrar.
     */
    static double solicitarImporte(Scanner scanner) {

        double importe;

        do {
            System.out.println("BIENVENIDO A LA PASARELA DE PAGOS");
            System.out.println("**************************************");
            System.out.print("Introduce el importe a cobrar (ej: 123,45): ");
            importe = scanner.nextDouble();

            if (importe <= 0) {
                System.out.println("\nEl importe debe ser mayor que cero.\n");
            }

        } while (importe <= 0);

        return importe;
    }

    // ---------------------------------------------------------------------
    // 2) MENÚ PRINCIPAL
    // ---------------------------------------------------------------------

    /**
     * Muestra el menú de métodos de pago y devuelve la opción seleccionada
     * por el usuario.
     *
     * @param scanner     Scanner para leer por consola.
     * @param importeAPagar Importe pendiente de cobro.
     * @return Opción elegida por el usuario.
     */
    static int mostrarMenu(Scanner scanner, double importeAPagar) {

        System.out.println("\nSELECCIONE UN MÉTODO DE PAGO (" + importeAPagar + "€)");
        System.out.println("****************************************");
        System.out.println(" 1. Efectivo.");
        System.out.println(" 2. Cargo en Cuenta.");
        System.out.println(" 3. Tarjeta de crédito.");
        System.out.print("Seleccione una opción: ");

        return scanner.nextInt();
    }

    // ---------------------------------------------------------------------
    // 3) PAGO EN EFECTIVO
    // ---------------------------------------------------------------------

    /**
     * Gestiona el pago en efectivo: pide la cantidad entregada y calcula
     * el cambio en billetes y monedas.
     *
     * @param scanner      Scanner para leer la entrada del usuario.
     * @param importeAPagar Importe que se debe cobrar.
     */
    static void realizarPagoEnEfectivo(Scanner scanner, double importeAPagar) {

        System.out.print("\nIntroduce la cantidad a entregar (ej: 123,45): ");
        double cantidadEntregada = scanner.nextDouble();

        while (cantidadEntregada < importeAPagar) {
            System.out.println("Cantidad insuficiente. Introduce una cantidad mayor:");
            cantidadEntregada = scanner.nextDouble();
        }

        // Convertimos a céntimos para evitar errores de coma flotante
        int cambioEnCentimos = (int) Math.round((cantidadEntregada - importeAPagar) * 100);

        System.out.println("\n SU CAMBIO ");
        System.out.println("***************************");

        int restoCentimos = cambioEnCentimos;

        int billetes50 = restoCentimos / 5000;
        restoCentimos %= 5000;

        int billetes20 = restoCentimos / 2000;
        restoCentimos %= 2000;

        int billetes10 = restoCentimos / 1000;
        restoCentimos %= 1000;

        int billetes5 = restoCentimos / 500;
        restoCentimos %= 500;

        int monedas1Euro = restoCentimos / 100;
        restoCentimos %= 100;

        int monedasCentimo = restoCentimos;

        System.out.println(" 50€     " + billetes50 + " billete(s)");
        System.out.println(" 20€     " + billetes20 + " billete(s)");
        System.out.println(" 10€     " + billetes10 + " billete(s)");
        System.out.println(" 5€      " + billetes5 + " billete(s)");
        System.out.println(" 1€      " + monedas1Euro + " moneda(s)");
        System.out.println(" Céntimos: " + monedasCentimo + " céntimo(s)");

        double totalDevuelto = Math.round((cantidadEntregada - importeAPagar) * 100) / 100.0;
        System.out.println("Total devolución: " + totalDevuelto + " €");
    }

    // ---------------------------------------------------------------------
    // 4) PAGO CON TARJETA
    // ---------------------------------------------------------------------

    /**
     * Gestiona el pago con tarjeta: limpia la entrada, detecta el tipo de tarjeta
     * por el primer dígito y valida el número con el algoritmo de Luhn.
     *
     * @param scanner      Scanner para leer la entrada del usuario.
     * @param importeAPagar Importe a cobrar.
     * @return true si el pago se ha realizado correctamente, false en caso contrario.
     */
    static boolean realizarPagoTarjeta(Scanner scanner, double importeAPagar) {

        System.out.print("\nIntroduce el número de tarjeta (16 números): ");
        scanner.nextLine(); // limpiar salto pendiente
        String entradaUsuario = scanner.nextLine();

        String numeroTarjeta = obtenerSoloDigitos(entradaUsuario);
        System.out.println("Credit Card Number: " + numeroTarjeta);

        if (numeroTarjeta.length() != 16) {
            System.out.println("Formato incorrecto. Por favor, seleccione de nuevo.");
            return false;
        }

        int primerDigito = numeroTarjeta.charAt(0) - '0';

        if (primerDigito == 3) {
            System.out.println("Tarjeta American Express");
        } else if (primerDigito == 4) {
            System.out.println("Tarjeta VISA");
        } else if (primerDigito == 5) {
            System.out.println("Tarjeta MasterCard");
        } else {
            System.out.println("Tipo de tarjeta desconocido.");
            return false;
        }

        if (validarTarjeta(numeroTarjeta)) {
            System.out.println("La tarjeta " + numeroTarjeta + " es correcta.");
            System.out.println("Se le ha cobrado " + importeAPagar + " euros.");
            return true;
        } else {
            System.out.println("La tarjeta " + numeroTarjeta + " no es válida.");
            return false;
        }
    }

    /**
     * Valida un número de tarjeta de crédito usando el algoritmo de Luhn.
     *
     * Nota: el límite de suma (<= 150) forma parte del enunciado original,
     * aunque lo habitual en Luhn es solo comprobar el módulo 10.
     *
     * @param numeroTarjeta Número de tarjeta formado solo por dígitos.
     * @return true si el número de tarjeta pasa la validación, false en caso contrario.
     */
    static boolean validarTarjeta(String numeroTarjeta) {

        int sumaTotal = 0;

        for (int i = 0; i < 16; i++) {

            int digito = numeroTarjeta.charAt(i) - '0';

            // En posiciones pares (empezando en 0) se multiplica por 2 según Luhn
            if (i % 2 == 0) {
                digito = digito * 2;
                if (digito > 9) {
                    digito = digito - 9;
                }
            }
            sumaTotal += digito;
        }

        return (sumaTotal % 10 == 0) && (sumaTotal <= 150);
    }

    // ---------------------------------------------------------------------
    // 5) PAGO CON CUENTA BANCARIA (CCC)
    // ---------------------------------------------------------------------

    /**
     * Gestiona el pago con cuenta bancaria (CCC de 20 dígitos). Calcula y comprueba
     * los dígitos de control para validar la cuenta.
     *
     * @param scanner      Scanner para leer por consola.
     * @param importeAPagar Importe a cobrar.
     * @return true si la cuenta es correcta y el pago se realiza, false en caso contrario.
     */
    static boolean realizarPagoCuentaBancaria(Scanner scanner, double importeAPagar) {

        System.out.print("\nIntroduce el número de cuenta corriente (20 números): ");
        scanner.nextLine();
        String entradaUsuario = scanner.nextLine();

        String numeroCuenta = obtenerSoloDigitos(entradaUsuario);
        System.out.println("Núm. CCC: " + numeroCuenta);

        if (numeroCuenta.length() != 20) {
            System.out.println("Formato incorrecto. Por favor, seleccione de nuevo.");
            return false;
        }

        String codigoEntidadOficina = numeroCuenta.substring(0, 8);
        String digitosControl = numeroCuenta.substring(8, 10);
        String numeroCuentaCliente = numeroCuenta.substring(10, 20);

        int digitoControlEntidadOficinaCalculado = calcularDigitoControlBloque("00" + codigoEntidadOficina);
        int digitoControlCuentaCalculado = calcularDigitoControlBloque(numeroCuentaCliente);

        int digitoControlEntidadOficinaReal = digitosControl.charAt(0) - '0';
        int digitoControlCuentaReal = digitosControl.charAt(1) - '0';

        if (digitoControlEntidadOficinaReal == digitoControlEntidadOficinaCalculado
                && digitoControlCuentaReal == digitoControlCuentaCalculado) {

            System.out.println("La cuenta " + numeroCuenta + " es correcta. DC: " + digitosControl
                    + " -> " + digitoControlEntidadOficinaCalculado + "" + digitoControlCuentaCalculado);
            System.out.println("Se le ha cobrado " + importeAPagar + " euros.");
            return true;
        } else {
            System.out.println("La cuenta " + numeroCuenta + " NO es correcta. DC: " + digitosControl
                    + " -> " + digitoControlEntidadOficinaCalculado + "" + digitoControlCuentaCalculado);
            return false;
        }
    }

    /**
     * Calcula un dígito de control (DC) para un bloque de 10 dígitos según
     * los pesos definidos en el enunciado.
     *
     * @param bloque10 Cadena de exactamente 10 dígitos.
     * @return Dígito de control resultante (0-9).
     */
    static int calcularDigitoControlBloque(String bloque10) {

        // Pesos según enunciado (sin arrays para simplificar la lógica)
        int peso1 = 1;
        int peso2 = 2;
        int peso3 = 4;
        int peso4 = 8;
        int peso5 = 5;
        int peso6 = 10;
        int peso7 = 9;
        int peso8 = 7;
        int peso9 = 3;
        int peso10 = 6;

        int sumaPonderada =
                (bloque10.charAt(0) - '0') * peso1 +
                (bloque10.charAt(1) - '0') * peso2 +
                (bloque10.charAt(2) - '0') * peso3 +
                (bloque10.charAt(3) - '0') * peso4 +
                (bloque10.charAt(4) - '0') * peso5 +
                (bloque10.charAt(5) - '0') * peso6 +
                (bloque10.charAt(6) - '0') * peso7 +
                (bloque10.charAt(7) - '0') * peso8 +
                (bloque10.charAt(8) - '0') * peso9 +
                (bloque10.charAt(9) - '0') * peso10;

        int resto = sumaPonderada % 11;
        int digitoControl = 11 - resto;

        if (digitoControl == 10) {
            digitoControl = 1;
        }
        if (digitoControl == 11) {
            digitoControl = 0;
        }

        return digitoControl;
    }

    // ---------------------------------------------------------------------
    // 6) LIMPIAR ENTRADA: QUEDARSE SOLO CON DÍGITOS
    // ---------------------------------------------------------------------

    /**
     * Elimina todos los caracteres no numéricos de una cadena y devuelve
     * solo los dígitos.
     *
     * @param texto Cadena introducida por el usuario.
     * @return Cadena formada únicamente por los dígitos encontrados en el texto.
     */
    static String obtenerSoloDigitos(String texto) {

        String resultadoLimpio = "";

        for (int i = 0; i < texto.length(); i++) {
            char caracterActual = texto.charAt(i);

            if (caracterActual >= '0' && caracterActual <= '9') {
                resultadoLimpio = resultadoLimpio + caracterActual;
            }
        }
        return resultadoLimpio;
    }
}
