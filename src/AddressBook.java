import java.util.Scanner;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class AddressBook {
    private Map<String, String> contactos;

    public AddressBook() {
        this.contactos = new HashMap<>();
    }
    // Main 
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);

        AddressBook addressBook = new AddressBook();
        addressBook.load("contactos.txt");
        // Menú bucle.
        int op;
        do {
            System.out.println("-        Menú         -");
            System.out.println("-----------------------");
            System.out.println("1. Mostrar contactos.");
            System.out.println("2. Crear nuevo contacto.");
            System.out.println("3. Borrar un contacto.");
            System.out.println("4. Salir.");
            System.out.println("-----------------------");
            System.out.print("Seleccione una opción: ");

            op = sc.nextInt();
            sc.nextLine(); //

            switch (op) {
                case 1:
                    addressBook.list();

                    contTecla(sc);
                    break;
                case 2:
                    System.out.print("Número de teléfono: ");
                    String nuevoNumero = sc.nextLine();

                    if (nuevoNumero.length() == 10) {
                        System.out.print("Nombre de contacto: ");
                        String nuevoNombre = sc.nextLine();
                        addressBook.create(nuevoNumero, nuevoNombre);
                        System.out.println("-----------------------");
                        addressBook.save("contactos.txt");
                    } else {
                        System.out.println("El número de teléfono debe tener 10 digitos, intente otra vez.");
                    }

                    contTecla(sc);
                    break;
                case 3:
                    System.out.print("Ingrese el número de teléfono que quiere eliminar: ");
                    String numBorrar = sc.nextLine();
                    addressBook.delete(numBorrar);

                    System.out.println("-----------------------");
                    addressBook.save("contactos.txt");
                    contTecla(sc);
                    break;
                case 4:
                    System.out.println("Saliendo ...");
                    break;
                default:
                    System.out.println("Opción no valida, intente de nuevo.");
                    contTecla(sc);
                    break;
            }
        } while (op != 4);

        sc.close();
    }
    
    public static void contTecla(Scanner scanner) {
        System.out.print("Presione cualquier tecla para continuar...");
        scanner.nextLine();
    }


    // Lectura y escritura.
    public void load(String archivo) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(archivo));
            String linea;
            while ((linea = reader.readLine()) != null) {
                String[] parts = linea.split(",");
                if (parts.length == 2) {
                    contactos.put(parts[0], parts[1]);
                } else {
                    System.out.println("Error al leer la línea: " + linea);
                }
            }
            reader.close();
            // System.out.println("Contactos cargados correctamente.");
        } catch (IOException e) {
            System.out.println("Error al cargar los contactos: " + e.getMessage());
        }
    }

    public void save(String archivo) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(archivo));
            for (Map.Entry<String, String> entrada : contactos.entrySet()) {
                writer.write(entrada.getKey() + "," + entrada.getValue() + "\n");
            }
            writer.close();
            System.out.println("Cambios guardados correctamente.");
        } catch (IOException e) {
            System.out.println("Error al guardar los cambios: " + e.getMessage());
        }
    }

    // Modificación de contactos.
    public void list() { // Mostrar la lista de contactos.
        System.out.println("Contactos:");
        for (Map.Entry<String, String> entrada : contactos.entrySet()) {
            System.out.println(entrada.getKey() + " : " + entrada.getValue());
        }
    }

    public void create(String numero, String nombre) { // Crear un nuevo contacto.
        contactos.put(numero, nombre);
        System.out.println("Contacto creado.");
    }

    public void delete(String numero) { // Borrar un contacto.
        if (contactos.containsKey(numero)) {
            String nombre = contactos.get(numero);
            contactos.remove(numero);
            System.out.println("Contacto eliminado: " + numero + " - " + nombre);
        } else {
            System.out.println("El número no existe en la agenda.");
        }
    }
}