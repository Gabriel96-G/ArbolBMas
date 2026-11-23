
import java.util.Scanner;
public class Main {

    public static void main(String[] args) {

        ArbolBMas arbol = new ArbolBMas(4);
        Scanner scanner = new Scanner(System.in);
        int opcion;


        do {
            System.out.println("\n===== MENU ARBOL B+ =====");
            System.out.println("1. Insertar clave");
            System.out.println("2. Buscar clave");
            System.out.println("3. Eliminar clave");
            System.out.println("4. Recorrer elementos");
            System.out.println("5. Imprimir arbol");
            System.out.println("6. Salir");
            System.out.print("Seleccione una opcion: ");

            if (scanner.hasNextInt()) {
                opcion = scanner.nextInt();
            } else {
                System.out.println("Entrada no valida. Debe ingresar un numero.");
                scanner.next();
                opcion = 0;
                continue;
            }

            switch (opcion) {

                case 1:
                    System.out.print("Ingrese la clave que desea insertar: ");
                    if (!scanner.hasNextInt()) {
                        System.out.println("Entrada no valida. Debe ingresar un numero.");
                        scanner.next();
                        break;
                    }
                    int clave = scanner.nextInt();
                    arbol.insertar(clave);
                    break;

                case 2:
                    System.out.print("Ingrese la clave que desea buscar: ");
                    if (!scanner.hasNextInt()) {
                        System.out.println("Entrada no valida. Debe ingresar un numero.");
                        scanner.next();
                        break;
                    }
                    int claveBuscar = scanner.nextInt();

                    if (arbol.buscar(claveBuscar)) {
                        System.out.println("La clave existe en el arbol.");
                    } else {
                        System.out.println("La clave no existe en el arbol.");
                    }
                    break;
                case 3:
                    System.out.print("Ingrese la clave que desea eliminar: ");
                    if (!scanner.hasNextInt()) {
                        System.out.println("Entrada no valida. Debe ingresar un numero.");
                        scanner.next();
                        break;
                    }
                    int claveEliminar = scanner.nextInt();

                    if (arbol.eliminar(claveEliminar)) {
                        System.out.println("La clave fue eliminada correctamente.");
                    }
                    break;

                case 4:
                    System.out.print("Ingrese la clave inicial: ");
                    if (!scanner.hasNextInt()) {
                        System.out.println("Entrada no valida. Debe ingresar un numero.");
                        scanner.next();
                        break;
                    }

                    int claveInicial = scanner.nextInt();

                    System.out.print("Ingrese la cantidad de elementos siguientes: ");
                    if (!scanner.hasNextInt()) {
                        System.out.println("Entrada no valida. Debe ingresar un numero.");
                        scanner.next();
                        break;
                    }

                    int cantidad = scanner.nextInt();
                    arbol.recorrerDesdeClave(claveInicial, cantidad);
                    break;

                case 5:
                    arbol.imprimirArbol();
                    break;

                case 6:
                    System.out.println("Saliendo del programa...");
                    break;
                default:
                    System.out.println("Opcion no valida. Intente nuevamente.");
                    break;
            }

        } while (opcion != 6);
    }
}
