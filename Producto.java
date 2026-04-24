//CLASE PADRE
public class Producto implements IVendible {
    protected int id;
    protected String nombre;
    protected double precio;
    protected int stock;
    
    public Producto(int id, String nombre, double precio, int stock) {
        this.id = id;
        this.nombre = nombre;
        setPrecio(precio);//Validacion Setters
        setStock(stock);
    }
    
    // SETTERS AND GETTERS
    public int getId(){
        return id;
    }
    
    public String getNombre(){
        return nombre;
    }
    
    public double getPrecio(){
        return precio;
    }
    
    public int getStock(){
        return stock;
    }
    
    public void setPrecio(double precio){
        if (precio > 0) this.precio = precio;
    }
    
    public void setStock(int stock){
        if (stock >= 0) this.stock = stock;
    }
    
    @Override
    public void aplicarStock(int cantidad){
        this.stock += cantidad;
    }
    
    @Override
    public String getDetalles(){
        return "ID " + id + "|" + " Nombre " + nombre + "|" + " Precio " + precio + "|" + "Stock " + stock;
    }
}

//CLASE HIJA ELECTRODOMESTICOS
class ProductoElectrodomestico extends Producto {
    private int mesesGarantia;
    public ProductoElectrodomestico(int id, String nombre, double precio, int stock, int meses) {
        super(id, nombre, precio, stock);
        this.mesesGarantia = meses;
    }
    
    @Override
    public String getDetalles() { 
        if (this.getStock() > 0) {
            return super.getDetalles() + " | La Garantia es: " + mesesGarantia + " meses";
        } 
        
        else {
            return super.getDetalles() + " | [AGOTADO - No disponible]";
        }
    }
}

//CLASE HIJA ROPA
class ProductoRopa extends Producto {
    private String size;
    public ProductoRopa (int id, String nombre, double precio, int stock, String size) {
        super(id, nombre, precio, stock);
        this.size = size;
    }
    
    @Override
    public String getDetalles(){
        if (this.getStock() > 0) {
            return super.getDetalles() + " | El size es: " + size;
        } 
        
        else {
            return super.getDetalles() + " | [AGOTADO - No disponible]";
        }
    }
}

//CLASE HIJA ALIMENTOS
class ProductoAlimento extends Producto {
    private String expiracion;
    public ProductoAlimento (int id, String nombre, double precio, int stock, String expiracion) {
        super(id, nombre, precio, stock);
        this.expiracion = expiracion;
    }
    
    @Override
    public String getDetalles(){
        if (this.getStock() > 0) {
            return super.getDetalles() + " | Expira el: " + expiracion;
        } 
        
        else {
            return super.getDetalles() + " | [AGOTADO - No disponible]";
        }
    }
}