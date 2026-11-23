
import java.util.Scanner;
public class Main {

    public static void main(String[] args) {

        ArbolBMas arbol = new ArbolBMas(4);
        Scanner scanner = new Scanner(System.in);
        int opcion;

        arbol.insertar(10);
        arbol.insertar(20);
        arbol.insertar(5);
        arbol.insertar(6);
        arbol.insertar(15);
        arbol.insertar(30);
        arbol.insertar(25);
        arbol.insertar(40);
        arbol.insertar(45);
        arbol.insertar(50);

        arbol.imprimirArbol();

        System.out.println("Buscar clave 25: " + arbol.buscar(25));
        System.out.println("Eliminar clave 25: " + arbol.eliminar(25));
        System.out.println("Buscar clave 25 despues de eliminar: " + arbol.buscar(25));
        arbol.imprimirArbol();
        arbol.recorrerRango(10, 40);
        arbol.recorrerRango(21, 29);
        arbol.recorrerRango(40, 10);

        System.out.println("Eliminar clave 5: " + arbol.eliminar(5));
        arbol.imprimirArbol();
        arbol.insertar(12);

        System.out.println("Eliminar clave 15: " + arbol.eliminar(15));
        arbol.imprimirArbol();
        arbol.recorrerRango(6, 20);

        System.out.println("Eliminar clave 40: " + arbol.eliminar(40));
        arbol.imprimirArbol();
        arbol.recorrerRango(6, 50);

        do {
            System.out.println("\n===== MENU ARBOL B+ =====");
            System.out.println("1. Insertar clave");
            System.out.println("2. Buscar clave");
            System.out.println("3. Eliminar clave");
            System.out.println("4. Recorrer elementos");
            System.out.println("5. Imprimir arbol");
            System.out.println("6. Salir");
            System.out.print("Seleccione una opcion: ");

            opcion = scanner.nextInt();

            switch (opcion) {

                case 1:
                    System.out.print("Ingrese la clave que desea insertar: ");
                    int clave = scanner.nextInt();
                    arbol.insertar(clave);
                    break;

                case 2:
                    System.out.print("Ingrese la clave que desea buscar: ");
                    int claveBuscar = scanner.nextInt();

                    if (arbol.buscar(claveBuscar)) {
                        System.out.println("La clave existe en el arbol.");
                    } else {
                        System.out.println("La clave no existe en el arbol.");
                    }
                    break;
                case 3:
                    System.out.print("Ingrese la clave que desea eliminar: ");
                    int claveEliminar = scanner.nextInt();

                    if (arbol.eliminar(claveEliminar)) {
                        System.out.println("La clave fue eliminada correctamente.");
                    } else {
                        System.out.println("La clave no existe en el arbol.");
                    }
                    break;

                case 4:
                    System.out.print("Ingrese la cantidad de elementos que desea recorrer: ");
                    int cantidad = scanner.nextInt();
                    arbol.recorrerRango(cantidad);
                    break;

                case 5:
                    arbol.imprimirArbol();
                    break;

                case 6:
                    System.out.println("Saliendo del programa...");
                    break;
            }

        } while (opcion != 6);
    }
}
