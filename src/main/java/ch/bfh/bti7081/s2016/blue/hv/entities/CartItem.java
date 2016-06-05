package ch.bfh.bti7081.s2016.blue.hv.entities;

/**
 * Created by kerberos on 05/06/16.
 */
public class CartItem {

    Product product;
    int quantity;

    public CartItem(Product product) {
        this.product = product;
        this.quantity = 1;
    }

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void inc(){
        this.quantity++;
    }

    public void dec(){
        this.quantity--;
    }
}
