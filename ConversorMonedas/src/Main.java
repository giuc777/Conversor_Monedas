import com.Modelo.ConsultaHttp;
import com.Modelo.Moneda;

import java.util.*;

public class Main {


    public static void main(String[] args) {
        ConsultaHttp consulta = new ConsultaHttp();

        Map<String, Double> r = consulta.fetchData();

        List <Moneda> Monedas = new ArrayList<>();
        r.forEach((moneda, tasa) -> {
            Moneda mon = new Moneda(moneda, tasa);
            Monedas.add(mon);

        });

        while (true){
            menu();
            System.out.println("Ingresa el nombre de la moneda local: ");
            Scanner leer = new Scanner(System.in);
            String nombreMonedaLocal = leer.nextLine();
            System.out.println("Ingresa la moneda a convertir: ");
            String nombreMonedaConvertir= leer.nextLine();
            System.out.println("Ingresa la cantidad a convertir: ");
            double cantidad = leer.nextDouble();

            Moneda local = buscar(Monedas,nombreMonedaLocal) ;
            Moneda AConverir = buscar(Monedas, nombreMonedaConvertir);

            if(local== null) {
                System.out.println("No se encontro una moneda con ese nombre de la moneda local");
            } else if (AConverir == null) {
                System.out.println("No se encontro una moneda con ese nombre de la moneda a convertir");

            }else{
                System.out.println(local);
                System.out.println(AConverir);
                Moneda resultado =local.conversion(AConverir, cantidad);
                System.out.println(resultado.convertido());

            }

            System.out.println("Quieres convertir otra moneda: S/N");
            leer.nextLine();
            String respuestaUser = leer.nextLine();

            if(respuestaUser.equalsIgnoreCase("n")){
                System.out.println("Gracias por usar nuestro conversor");
                break;
            }
        }

    }

    public static void menu(){
        System.out.println("---    Menú        ----");
        System.out.println("*** Monedas dsiponibles:   ");
        System.out.println("ARS (PESO ARGENTINO), JPY (YEN JAPONES)");
        System.out.println("MXN (PESO MEXICANO), EUR (EURO), USD (DOLAR)");
        System.out.println("BOB (PESO BOLIBIANO, COP (PESO COLOMBIANO)");
        System.out.println("CLP (PESO CHILENO), BRL (REAL BRASILEÑO)");
        System.out.println("Escribe la moneda local luego la cantida por ultimo moneda a convertir");
        System.out.println("Ejemplo: USD, COP, 10000");
    }

    public static Moneda buscar(List <Moneda> Monedas, String nombre){
        for (Moneda moneda : Monedas) {
            if (moneda.getPais().equalsIgnoreCase(nombre)) {
                return moneda; // Retorna la moneda si se encuentra
            }
        }
        return null; // Si no se encuentra, retorna null
    }
}
