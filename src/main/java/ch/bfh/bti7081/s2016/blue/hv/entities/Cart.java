package ch.bfh.bti7081.s2016.blue.hv.entities;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kerberos on 05/06/16.
 */
public class Cart {

    List<CartItem> items;
    int cartSize;

    public Cart() {
        items = new ArrayList<CartItem>();
        cartSize = 0;
    }

    public synchronized void addItem(Product product){

        boolean newItem = true;

        for (CartItem i : items){
            if (i.getProduct().getName().equals(product.getName())) {
                newItem = false;
                i.inc();
            }
        }
        if (newItem){
            CartItem i = new CartItem(product);
            items.add(i);
        }
    }

    public synchronized void update(Product product, int quantity){
        if (quantity >= 0){
            CartItem item = null;
            for (CartItem i : items){
                if (i.getProduct().getName().equals(product.getName())) {
                    if (quantity != 0){
                        i.setQuantity(quantity);
                    } else {
                        item = i;
                        break;
                    }
                }
            }
            if (item != null){
                items.remove(item);
            }
        }
    }

    public synchronized List<CartItem> getItems(){
        return items;
    }

    public synchronized int getNumberOfItems(){
        cartSize = 0;
        for (CartItem i : items){
            cartSize += i.getQuantity();
        }
        return cartSize;
    }

    public synchronized void emptyCart(){
        items.clear();
        cartSize = 0;
    }
}
