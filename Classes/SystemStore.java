package Classes;
import java.util.*;
import Interface.ICupon;
import Interface.IDescontable;

public class SystemStore {
// Colecciones para persistencia durante la ejecución
    final private static ArrayList<Producto> inventario = new ArrayList<>();
    final private static HashMap<String, Cliente> clientes = new HashMap<>();

    public static void main(String[] args) {
        System.out.println("======= INICIANDO DEMOSTRACION DEL SISTEMA DE TIENDA =======");

        // 1. POBLAR DATOS (CREATE)
        poblarSistema();

        // 2. MOSTRAR INVENTARIO INICIAL (READ)
        mostrarInventario();

        // 3. DEMOSTRACION DE POLIMORFISMO Y DESCUENTOS (PROCESAR PEDIDOS)
        System.out.println("\n--- PROCESANDO VENTAS ---");
        
        // Venta a Cliente Regular (Sin descuento)
        realizarVentaAutomatica("REG-01", 1); // Licuadora
        
        // Venta a Cliente Premium (Con 15% de descuento via Interface Descontable)
        realizarVentaAutomatica("PRE-99", 2); // Camisa
        
        realizarVentaAutomatica("CUP-03", 3);
        
        // 4. DEMOSTRACION DE MANEJO DE EXCEPCIONES
        System.out.println("\n--- PRUEBA DE GESTION DE ERRORES ---");
        
        // Caso A: Producto no existe
        realizarVentaAutomatica("PRE-99", 500); 
        
        // Caso B: Stock insuficiente
        // Intentamos comprar 5 veces la pizza (solo hay 3 en stock)
        System.out.println("Intentando agotar stock de Pizza...");
        for(int i = 0; i < 5; i++) {
            realizarVentaAutomatica("REG-01", 3);
        }

        // 5. OPERACIONES DE ACTUALIZACION Y ELIMINACION (UPDATE / DELETE)
        System.out.println("\n--- ACTUALIZACION Y ELIMINACION ---");
        
        // Actualizar stock manualmente (Uso de Interface Vendible)
        inventario.get(0).aplicarStock(50);
        System.out.println("Stock de licuadora Pro actualizado (+50).");

        // Eliminar un producto
        inventario.removeIf(p -> p.getId() == 2);
        System.out.println("Producto ID 2 (Ropa) eliminado del sistema.");

        // 6. RESULTADO FINAL
        System.out.println("\n--- ESTADO FINAL DEL INVENTARIO ---");
        mostrarInventario();
        
        System.out.println("\n======= FIN DE LA DEMOSTRACION =======");
    }

    private static void poblarSistema() {
        // Agregando diferentes tipos de productos (Herencia)
        inventario.add(new ProductoElectrodomestico(1, "Licuadora Pro", 3500.0, 10, 24));
        inventario.add(new ProductoRopa(2, "Camisa Polo", 1200.0, 15, "L"));
        inventario.add(new ProductoAlimento(3, "Pizza Congelada", 450.0, 3, "2026-10-15"));

        // Agregando clientes (Herencia)
        clientes.put("REG-01", new ClienteRegular("REG-01", "Danny"));
        clientes.put("PRE-99", new ClientePremium("PRE-99", "Amelia"));
        clientes.put("CUP-03", new ClienteConCupon("CUP-03", "Dayron"));
    }

    private static void mostrarInventario() {
        System.out.println("\nInventario Actual:");
        for (Producto p : inventario) {
            // Comportamiento polimorfico: getDetalles() cambia segun la subclase
            System.out.println(" > " + p.getDetalles());
        }
    }

    private static void realizarVentaAutomatica(String idCliente, int idProducto) {
        try {
            Cliente c = clientes.get(idCliente);
            if (c == null) throw new ClienteInvalidoException("ID de cliente invalido.");

            Producto p = inventario.stream()
                    .filter(prod -> prod.getId() == idProducto)
                    .findFirst()
                    .orElseThrow(() -> new ProductoNoEncontradoException("Producto ID " + idProducto + " no encontrado."));

            if (p.getStock() <= 0) {
                throw new StockInsuficienteException("No queda stock de " + p.getNombre());
            }

            // Logica de venta
            p.setStock(p.getStock() - 1);
            double total = p.getPrecio();
            
            String mensaje = "Venta: " + c.getNombre() + " compro, " + p.getNombre();
            
            // Polimorfismo con interfaces
            if (c instanceof IDescontable) {
                double ahorro = ((IDescontable) c).calcularDescuento(total);
                total -= ahorro;
                mensaje += " (Descuento aplicado: $" + ahorro + ")";
            }
            
            if (c instanceof ICupon) {
                double ahorro = ((ICupon) c).aplicarCupon(total);
                total -= ahorro;
                mensaje += " (Cupon desconto: $" + ahorro + ")";
            }
            
            System.out.println(mensaje + " | Total final: $" + total);

        } catch (Exception e) {
            System.err.println("[EXCEPCION CAPTURADA]: " + e.getMessage());
        }
    }
}