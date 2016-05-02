package angelcotes.models;

import java.util.Random;


/**
 * Created by Alberto Mario Camargo Castro on 30-Apr-16.
 */
public class GameManagement {

    int moneyToPay, level;

    public GameManagement() {
        level = 0;
        moneyToPay = 0;
    }

    public void newGame() {
        // Generate number multiples of 10.000. maximum number 250.000
        this.moneyToPay = (1 + new Random().nextInt(250)) * 1000;
        this.level += 1;
    }

    // Method for change activities
    public void ActivityGame() {

    }

    public int getMoneyToPay() {
        return moneyToPay;
    }

    private boolean isWinner(CashRegister cashRegister) {
        return cashRegister.getTotal() == this.moneyToPay;
    }

    private boolean isLoser(CashRegister cashRegister) {
        return cashRegister.getTotal() > this.moneyToPay;
    }

    private boolean keepPlaying(CashRegister cashRegister) {
        return cashRegister.getTotal() < this.moneyToPay;
    }

    // return -> 0: keep playing. 1: win. -1: lost
    public int gameStatus(CashRegister cashRegister) {
        int status = 0;
        if (isWinner(cashRegister)) {
            status = 1;
        } else if (isLoser(cashRegister)) {
            status = -1;
        }
        return status;
    }


}
