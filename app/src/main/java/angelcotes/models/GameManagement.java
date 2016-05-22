package angelcotes.models;

import java.util.Random;


/**
 * Created by Alberto Mario Camargo Castro on 30-Apr-16.
 */
public class GameManagement {

    int moneyToPay, level;

    public void setLevel(int level) {
        this.level = level;
    }

    public GameManagement() {
        level = 0;
        moneyToPay = 0;
    }

    public void newGame() {
        // Generate number multiples of 10.000. maximum number 250.000
        if (this.level == 0) {
            this.moneyToPay = (1 + new Random().nextInt(25)) * 1000;
        } else if (this.level == 1) {
            this.moneyToPay = (1 + new Random().nextInt(50)) * 1000;
        } else if(this.level == 2) {
            this.moneyToPay = (1 + new Random().nextInt(150)) * 1000;
        } else if (this.level == 3) {
            this.moneyToPay = (1 + new Random().nextInt(200)) * 1000;
        } else {
            this.moneyToPay = (1 + new Random().nextInt(250)) * 1000;
        }


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
            this.level += 1;
        } else if (isLoser(cashRegister)) {
            status = -1;
        }
        return status;
    }


}
