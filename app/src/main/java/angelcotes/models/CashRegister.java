package angelcotes.models;

import java.util.ArrayList;

/**
 * Created by Alberto Mario Camargo Castro on 30-Apr-16.
 */
public class CashRegister {
    ArrayList<Bill> bills;
    int total;

    public CashRegister() {
        total = 0;
        bills = new ArrayList<>();
    }

    public void addBill(Bill bill) {
       bills.add(bill);
       total += bill.getValue();
    }

    public ArrayList<Bill> getBills() {
        return bills;
    }

    public int getTotal() {
        return total;
    }
}
