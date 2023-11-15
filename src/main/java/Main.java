import java.nio.file.Path;

public class Main {
    public static void main(String[] args) {
        OperacionesCRUDPilotos opsCRUD = new OperacionesCRUDPilotos();

        //CrearPiloto(), que reciba un objeto Piloto y lo añada a la base de datos
        Piloto piloto1 = new Piloto(null, "CES", "César", "Álvaro","1979-02-22","Spanish", "http://www.google.es"  );

        if(opsCRUD.crearPiloto(piloto1)){
            System.out.println("Piloto creado " + piloto1.toString());
        }else{
            System.out.println("El piloto no se ha creado");
        }
        System.out.println();

        //LeerPiloto(), que reciba un entero y devuelva un objeto Piloto con la información del piloto
        System.out.println("Mostrando piloto con ID 4:");
        System.out.println(opsCRUD.leerPiloto(4));

        //leerPilotos(), que devuelva un ArrayList con todos los pilotos de la base de datos
        System.out.println("Mostrando todos los pilotos:");
        opsCRUD.leerPilotos().forEach(System.out::println);
        System.out.println();

        //actualizarPiloto(), que reciba un objeto Piloto y actualice sus datos en la base de datos
        Piloto piloto2 = opsCRUD.leerPiloto(39);
        piloto2.setCode("ALV");

        if(opsCRUD.actualizarPiloto(piloto2)){
            System.out.println("Piloto actualizado");
        }else{
            System.out.println("El piloto no se ha actualizado");
        }
        System.out.println();

        //borrarPiloto(), que reciba un objeto Piloto y lo borre de la base de datos
        if(opsCRUD.borrarPiloto(piloto2)){
            System.out.println("Piloto borrado");
        }else{
            System.out.println("El piloto no se ha borrado");
        }
        System.out.println();

        //mostrarClasificacionPiloto(), que muestre por pantalla la clasificación de pilotos
        opsCRUD.mostrarClasificacionPiloto();

        //mostrarClasificacionConstructores(), que muestre por pantalla la clasificación de constructores
        opsCRUD.mostrarClasificacionConstructores();




    }
}
