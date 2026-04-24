//CLASE PADRE
public class Cliente {
    protected String id;
    protected String nombre;

    public Cliente(String id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }
    
    //GETTERS
    public String getId() { 
        return id; 
    }
    
    public String getNombre() { 
        return nombre; 
    }

    @Override
    public String toString() { 
        return "ID: " + id + " | Nombre: " + nombre; 
    }
}

//CLASE HIJA CLIENTE REGULAR
class ClienteRegular extends Cliente {
    public ClienteRegular(String id, String nombre) { 
        super(id, nombre); 
    }
}

//CLASE HIJA CLIENTE PREMIUN
class ClientePremium extends Cliente implements IDescontable {
    public ClientePremium(String id, String nombre) { 
        super(id, nombre); 
    }
    
    @Override
    public double calcularDescuento(double total) { 
        return total * 0.15; 
    }
}

class ClienteConCupon extends Cliente implements ICupon {
    public ClienteConCupon(String id, String nombre) {
        super(id, nombre);
    }
    
    @Override
    public double aplicarCupon (double total){
        return total * 0.10;
    }
}