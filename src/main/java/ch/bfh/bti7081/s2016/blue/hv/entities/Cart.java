package ch.bfh.bti7081.s2016.blue.hv.entities;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by kerberos on 05/06/16.
 */
public class Cart {

    List<DrugOrderItem> items;
    int cartSize;
    Patient patient;
    String remarks;

    public Cart(Patient patient) {
        this.patient = patient;
        items = new ArrayList<DrugOrderItem>();
        cartSize = 0;
        remarks = "";
    }

    public synchronized void addItem(DrugOrderItem drugOrderItem){

        if(drugOrderItem.getDrug().isPrescribed(patient)) {

            boolean newItem = true;

            for (DrugOrderItem i : items) {
                if (i.getName().equals(drugOrderItem.getName())) { // besser mit ID??? -> implement
                    newItem = false;
                    i.setQuantity(i.getQuantity() + drugOrderItem.getQuantity());
                }
            }
            if (newItem) {
                items.add(drugOrderItem);
            }
        }  else {
            // throw not prescribed exception
        }
    }

    public synchronized void update(DrugOrderItem drugOrderItem, int quantity){
        if (quantity >= 0){
            DrugOrderItem item = null;
            for (DrugOrderItem i : items){
                if (i.getName().equals(drugOrderItem.getName())) { // s.o.
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
        } else {
            // what to do ??? Throw Exception?
        }
    }

    public synchronized List<DrugOrderItem> getItems(){
        return items;
    }

    public synchronized int getNumberOfItems(){
        cartSize = 0;
        for (DrugOrderItem i : items){
            cartSize += i.getQuantity();
        }
        return cartSize;
    }

    public synchronized void emptyCart(){
        items.clear();
        cartSize = 0;
    }

    public DrugOrder getDrugOrder() {
        if (getNumberOfItems() > 0) {
            DrugOrder drugOrder = new DrugOrder();
            drugOrder.setPatient(patient);
            drugOrder.setDrugs(new HashSet<DrugOrderItem>(getItems()));
            drugOrder.setRemarks(remarks);
            return drugOrder;
        } else {
            return null;
        }
    }
}
