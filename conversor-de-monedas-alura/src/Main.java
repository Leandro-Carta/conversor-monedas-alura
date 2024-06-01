import com.google.gson.Gson;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        String opcion = ""; // Inicializar la opción fuera del bucle

        while (!opcion.equals("salir")) { // Mientras la opción no sea "salir"
            Scanner entrada = new Scanner(System.in); // Crear el objeto Scanner dentro del bucle

            System.out.println("escriba la divisa que quiere cambiar");
            System.out.println("########################################");
            System.out.println("\n");
            System.out.println("elija las siguientes opciones");
            System.out.println("USD , EUR , GBP , JYP");
            String monedaBase = entrada.nextLine();
            System.out.println("\n");

            switch (monedaBase.toUpperCase()) {
                case "USD":
                    System.out.println("Has seleccionado Dólares Estadounidenses.");
                    break;
                case "EUR":
                    System.out.println("Has seleccionado Euros.");
                    break;
                case "GBP":
                    System.out.println("Has seleccionado Libras Esterlinas.");
                    break;
                case "JPY":
                    System.out.println("Has seleccionado Yenes Japoneses.");
                    break;
                default:
                    System.out.println("Moneda no reconocida. Procediendo con la entrada proporcionada.");
                    break;
            }

            System.out.println("\n");
            System.out.println("\n");
            System.out.println("##############################");

            System.out.println("que cantidad de esa moneda quiere cambiar?");

            double cantidadMoneda = entrada.nextDouble();

            System.out.println("\n");
            System.out.println("\n");
            System.out.println("###########");
            System.out.println("a que moneda lo quiere cambiar??");

            entrada.nextLine();
            String monedaCambio = entrada.nextLine();

            String direccion = "https://v6.exchangerate-api.com/v6/a2b2cac6e908a2566a1b699c/latest/" + monedaBase;

            HttpClient cliente = HttpClient.newHttpClient();
            HttpRequest solicitud = HttpRequest.newBuilder()
                    .uri(URI.create(direccion))
                    .build();

            try {
                HttpResponse<String> respuesta = cliente.send(solicitud, HttpResponse.BodyHandlers.ofString());

                String respuestaJson = respuesta.body();

                Gson gson = new Gson();
                Conversor miconversor = gson.fromJson(respuestaJson, Conversor.class);

                Map<String, Double> rates = miconversor.conversion_rates();

                double tasaDeCambio = rates.get(monedaCambio.toUpperCase());
                double cantidadConvertida = cantidadMoneda * tasaDeCambio;


                System.out.println("a continuacion te dejo todas las cotizacione de las monedas");
                System.out.println(respuestaJson);

                // Imprimir los resultados
                System.out.println("La tasa de cambio de " + monedaBase + " a " + monedaCambio + " es: " + tasaDeCambio);
                System.out.println("La cantidad de " + cantidadMoneda + " " + monedaBase + " es igual a " + cantidadConvertida + " " + monedaCambio);
                System.out.println("Escriba 'salir' para terminar el programa o cualquier otra cosa para continuar.");



                opcion = entrada.nextLine(); // Leer la opción del usuario

            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
