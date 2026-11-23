import java.util.ArrayList;

public class ArbolBMas {

    // Atributos
    private final int orden;
    private NodoArbolBMas raiz;

    // Métodos
    // Constructor
    public ArbolBMas(int orden) {
        this.orden = orden;
        raiz = new NodoArbolBMas(true);
    }

    // Inserción de una llave en el árbol B+


    public void insertar(int clave) {
        insertar(clave, "Dato de la clave " + clave);
    }

    public void insertar(int clave, String dato) {

        // Verificar si la clave ya existe.
        if (buscar(clave)) {
            System.out.println("La clave ya existe en el arbol.");
            return;
        }

        // Se inserta primero de forma recursiva.
        insertarRecursivo(this.raiz, clave, dato);


        // Se verifica si la raiz se desbordo.
        if (this.raiz.getClaves().size() == orden) {
            NodoArbolBMas nuevaRaiz = new NodoArbolBMas(false);
            nuevaRaiz.getHijos().add(this.raiz);
            dividirHijo(nuevaRaiz, 0);
            this.raiz = nuevaRaiz;
        }
    }



    // Inserción recursiva de una llave
    // Permite que los nodos se desborden temporalmente y los divide al regresar de la recursión
    private void insertarRecursivo(NodoArbolBMas nodo, int llave, String dato) {

        int i = nodo.getClaves().size() - 1;

        if(nodo.esHoja()) {
            while(i >= 0 && llave < nodo.getClaves().get(i)) {
                i--;
            }
            nodo.getClaves().add(i + 1, llave);
            nodo.getDatos().add(i + 1, dato);

        } else {
            while(i >= 0 && llave < nodo.getClaves().get(i)) {
                i--;
            }
            i++;
            NodoArbolBMas hijo = nodo.getHijos().get(i);

            // Llamada recursiva para insertar la llave primero en el hijo correspondiente
            insertarRecursivo(hijo, llave, dato);


            // Evaluación de desbordamiento al retornar de la recursividad
            if(hijo.getClaves().size() == orden) {
                dividirHijo(nodo, i);
            }
        }
    }

    // Dividir un nodo que está lleno
    private void dividirHijo(NodoArbolBMas padre, int indice) {
        NodoArbolBMas nodoLleno = padre.getHijos().get(indice);
        NodoArbolBMas nuevoNodo = new NodoArbolBMas(nodoLleno.esHoja());
        int mitad = orden / 2;

        if (nodoLleno.esHoja()) {
            // En nodos hoja, se mantiene la llave media en ambos lados
            nuevoNodo.setClaves(new ArrayList<>(nodoLleno.getClaves().subList(mitad, nodoLleno.getClaves().size())));
            nodoLleno.setClaves(new ArrayList<>(nodoLleno.getClaves().subList(0, mitad)));

            nuevoNodo.setDatos(new ArrayList<>(
                    nodoLleno.getDatos().subList(mitad, nodoLleno.getDatos().size())));

            nodoLleno.setDatos(new ArrayList<>(
                    nodoLleno.getDatos().subList(0, mitad)));


            // Enlazar la nueva hoja con la siguiente.
            nuevoNodo.setSiguiente(nodoLleno.getSiguiente());
            nodoLleno.setSiguiente(nuevoNodo);

            // El padre recibe como separador la primera llave del nuevo nodo
            int llaveMedia = nuevoNodo.getClaves().getFirst();
            padre.getClaves().add(indice, llaveMedia);
            padre.getHijos().add(indice + 1, nuevoNodo);
        }
        else {
            // En nodos internos, la llave media sube y se elimina del hijo
            int claveMedia = nodoLleno.getClaves().get(mitad);
            nuevoNodo.setClaves(new ArrayList<>(nodoLleno.getClaves().subList(mitad + 1, nodoLleno.getClaves().size())));
            nodoLleno.setClaves(new ArrayList<>(nodoLleno.getClaves().subList(0, mitad)));

            // Mover los hijos del nodo dividido
            nuevoNodo.setHijos(new ArrayList<>(nodoLleno.getHijos().subList(mitad + 1, nodoLleno.getHijos().size())));
            nodoLleno.setHijos(new ArrayList<>(nodoLleno.getHijos().subList(0, mitad + 1)));

            padre.getClaves().add(indice, claveMedia);
            padre.getHijos().add(indice + 1, nuevoNodo);
        }
    }

    // Impresión del árbol como parte de la interfaz pública del árbol
    public void imprimirArbol() {
        imprimirNodo(raiz, "", true);
    }

    // Impresión recursiva privada de los nodos a partir de uno inicial
    private void imprimirNodo(NodoArbolBMas nodo, String indentacion, boolean esUltimo) {
        System.out.println(indentacion + "+- "
                + (nodo.esHoja() ? "Hoja > " : "Interno > ")
                + nodo.getClaves()
                + (nodo.esHoja() ? " | Datos: " + nodo.getDatos() : ""));

        indentacion += esUltimo ? "   " : "|  ";
        for(int i = 0; i < nodo.getHijos().size(); i++) {
            imprimirNodo(nodo.getHijos().get(i), indentacion, i == (nodo.getHijos().size() - 1));
        }
    }

    // Búsqueda de un nodo como parte de la interfaz pública del árbol
    public boolean buscar(int llave) {
        return buscarNodo(raiz, llave);
    }

    // Búsqueda recursiva privada de un nodo a partir de uno inicial
    private boolean buscarNodo(NodoArbolBMas nodo, int llave) {
        int i = 0;

        // Si es una hoja, verificar si contiene la llave.
        if (nodo.esHoja()) {
            return nodo.getClaves().contains(llave);
        }

        // Buscar el hijo correspondiente.
        while (i < nodo.getClaves().size()
                && llave >= nodo.getClaves().get(i)) {
            i++;
        }

        // Continuar la busqueda en el hijo seleccionado.
        return buscarNodo(nodo.getHijos().get(i), llave);
    }

    // Eliminación

    public boolean eliminar(int clave) {

        // Verificar si la clave existe.
        if (!buscar(clave)) {
            System.out.println("La clave no existe en el arbol.");
            return false;
        }

        // Eliminar la clave del arbol.
        eliminarRecursivo(raiz, clave);

        // Si la raiz queda con un solo hijo,
        // ese hijo pasa a ser la nueva raiz.
        if (!raiz.esHoja() && raiz.getHijos().size() == 1) {
            raiz = raiz.getHijos().get(0);
        }

        return true;
    }

    // Recorrer los primeros n elementos del arbol B+
    public void recorrerRango(int n) {

        if (n <= 0) {
            System.out.println("La cantidad debe ser mayor que cero.");
            return;
        }

        NodoArbolBMas actual = raiz;

        while (!actual.esHoja()) {
            actual = actual.getHijos().get(0);
        }

        int contador = 0;

        while (actual != null && contador < n) {

            for (int i = 0; i < actual.getClaves().size() && contador < n; i++) {

                System.out.println("Clave: " + actual.getClaves().get(i)
                        + " | Dato: " + actual.getDatos().get(i));

                contador++;
            }

            actual = actual.getSiguiente();
        }

        if (contador == 0) {
            System.out.println("El arbol no contiene elementos.");
        }
    }

// Recorrido por rango (Debe mostrar los datos correspondientes al rango recuperado.)

public void recorrerRango(int desde, int hasta) {

        if (desde > hasta) {
            System.out.println("El rango ingresado no es valido.");
            return;
        }

        // Buscar la hoja donde comienza el rango.
        NodoArbolBMas actual = raiz;

        while (!actual.esHoja()) {
            int i = 0;

            while (i < actual.getClaves().size()
                    && desde >= actual.getClaves().get(i)) {
                i++;
            }

            actual = actual.getHijos().get(i);
        }

        // Recorrer las hojas enlazadas.
        boolean encontrado = false;

        while (actual != null) {

            for (int i = 0; i < actual.getClaves().size(); i++) {

                int clave = actual.getClaves().get(i);

                if (clave > hasta) {
                    if (!encontrado) {
                        System.out.println("No hay datos en el rango indicado.");
                    }
                    return;
                }

                if (clave >= desde) {
                    System.out.println("Clave: " + clave
                            + " | Dato: " + actual.getDatos().get(i));
                    encontrado = true;
                }
            }

            actual = actual.getSiguiente();
        }

        if (!encontrado) {
            System.out.println("No hay datos en el rango indicado.");
        }
    }

    private void eliminarRecursivo(NodoArbolBMas nodo, int clave) {

        // Si es una hoja, eliminar la clave y su dato.
        if (nodo.esHoja()) {

            int posicion = nodo.getClaves().indexOf(clave);

            nodo.getClaves().remove(posicion);
            nodo.getDatos().remove(posicion);

            return;
        }

        // Buscar el hijo correspondiente.
        int i = 0;

        while (i < nodo.getClaves().size()
                && clave >= nodo.getClaves().get(i)) {
            i++;
        }

        NodoArbolBMas hijo = nodo.getHijos().get(i);

        // Continuar la eliminacion en el hijo.
        eliminarRecursivo(hijo, clave);

        // Verificar si el hijo necesita rebalanceo.

        if (hijo.getClaves().size() < minimoClaves(hijo)) {
            reequilibrarHijo(nodo, i);
        }

        // Actualizar las claves separadoras.
        actualizarSeparadores(nodo);
    }

    // Obtener la cantidad minima de claves de un nodo.
    private int minimoClaves(NodoArbolBMas nodo) {

        // Cantidad minima de claves en un nodo hoja.
        if (nodo.esHoja()) {
            return orden / 2;
        }

        // Cantidad minima de claves en un nodo interno.
        return (orden - 1) / 2;
    }

    // Aqui comienza el nuevo metodo.
    private void actualizarSeparadores(NodoArbolBMas nodo) {

        // Actualizar los separadores de los nodos internos descendientes.
        for (NodoArbolBMas hijo : nodo.getHijos()) {
            if (!hijo.esHoja()) {
                actualizarSeparadores(hijo);
            }
        }

        // Actualizar cada separador con la primera clave del hijo derecho.
        for (int i = 1; i < nodo.getHijos().size(); i++) {

            NodoArbolBMas actual = nodo.getHijos().get(i);

            // Descender hasta la primera hoja del subarbol.
            while (!actual.esHoja()) {
                actual = actual.getHijos().get(0);
            }

            nodo.getClaves().set(i - 1, actual.getClaves().get(0));
        }
    }
    private void reequilibrarHijo(NodoArbolBMas padre, int indice) {

        NodoArbolBMas hijo = padre.getHijos().get(indice);

        // Intentar prestar desde el hermano izquierdo.
        if (indice > 0) {
            NodoArbolBMas izquierdo = padre.getHijos().get(indice - 1);

            if (izquierdo.getClaves().size() > minimoClaves(izquierdo)) {
                prestarDesdeIzquierda(padre, indice);
                return;
            }
        }

        // Intentar prestar desde el hermano derecho.
        if (indice < padre.getHijos().size() - 1) {
            NodoArbolBMas derecho = padre.getHijos().get(indice + 1);

            if (derecho.getClaves().size() > minimoClaves(derecho)) {
                prestarDesdeDerecha(padre, indice);
                return;
            }
        }

        // Fusionar si los hermanos no pueden prestar.
        if (indice > 0) {
            fusionarHijos(padre, indice - 1);
        } else {
            fusionarHijos(padre, indice);
        }
    }

    private void prestarDesdeIzquierda(NodoArbolBMas padre, int indice) {

        NodoArbolBMas hijo = padre.getHijos().get(indice);
        NodoArbolBMas izquierdo = padre.getHijos().get(indice - 1);

        int ultimaPosicion = izquierdo.getClaves().size() - 1;

        if (hijo.esHoja()) {

            // Mover la ultima clave y su dato al inicio de la hoja.
            int clave = izquierdo.getClaves().remove(ultimaPosicion);
            String dato = izquierdo.getDatos().remove(ultimaPosicion);

            hijo.getClaves().add(0, clave);
            hijo.getDatos().add(0, dato);

            // Actualizar la clave separadora del padre.
            padre.getClaves().set(indice - 1, hijo.getClaves().get(0));

        } else {

            // Mover un hijo y ajustar las claves internas.
            int separador = padre.getClaves().get(indice - 1);
            int clavePrestada = izquierdo.getClaves().remove(ultimaPosicion);

            NodoArbolBMas hijoPrestado = izquierdo.getHijos()
                    .remove(izquierdo.getHijos().size() - 1);

            hijo.getClaves().add(0, separador);
            hijo.getHijos().add(0, hijoPrestado);

            padre.getClaves().set(indice - 1, clavePrestada);
        }
    }

        private void prestarDesdeDerecha(NodoArbolBMas padre, int indice) {

            NodoArbolBMas hijo = padre.getHijos().get(indice);
            NodoArbolBMas derecho = padre.getHijos().get(indice + 1);

            if (hijo.esHoja()) {

                // Mover la primera clave y su dato desde la hoja derecha.
                int clave = derecho.getClaves().remove(0);
                String dato = derecho.getDatos().remove(0);

                hijo.getClaves().add(clave);
                hijo.getDatos().add(dato);

                // Actualizar la clave separadora del padre.
                padre.getClaves().set(indice, derecho.getClaves().get(0));

            } else {

                // Mover un hijo y ajustar las claves internas.
                int separador = padre.getClaves().get(indice);
                int clavePrestada = derecho.getClaves().remove(0);

                NodoArbolBMas hijoPrestado = derecho.getHijos().remove(0);

                hijo.getClaves().add(separador);
                hijo.getHijos().add(hijoPrestado);

                padre.getClaves().set(indice, clavePrestada);
            }
        }

    private void fusionarHijos(NodoArbolBMas padre, int indice) {

        NodoArbolBMas izquierdo = padre.getHijos().get(indice);
        NodoArbolBMas derecho = padre.getHijos().get(indice + 1);

        if (izquierdo.esHoja()) {

            // Unir las claves y los datos de ambas hojas.
            izquierdo.getClaves().addAll(derecho.getClaves());
            izquierdo.getDatos().addAll(derecho.getDatos());

            // Mantener el enlace con la siguiente hoja.
            izquierdo.setSiguiente(derecho.getSiguiente());

        } else {

            // Incorporar el separador del padre.
            izquierdo.getClaves().add(padre.getClaves().get(indice));

            // Unir las claves y los hijos de los nodos internos.
            izquierdo.getClaves().addAll(derecho.getClaves());
            izquierdo.getHijos().addAll(derecho.getHijos());
        }

        // Eliminar el separador y el nodo derecho del padre.
        padre.getClaves().remove(indice);
        padre.getHijos().remove(indice + 1);
    }

}

