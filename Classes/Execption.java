package Classes;

class ProductoNoEncontradoException extends Exception {
    public ProductoNoEncontradoException(String mensaje) { 
        super(mensaje); 
    }
}

class StockInsuficienteException extends Exception {
    public StockInsuficienteException(String mensaje) { 
        super(mensaje); 
    }
}

class ClienteInvalidoException extends Exception {
    public ClienteInvalidoException(String mensaje) { 
        super(mensaje); 
    }
}