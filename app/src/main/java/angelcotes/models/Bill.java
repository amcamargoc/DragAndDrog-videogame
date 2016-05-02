package angelcotes.models;

/**
 * Created by Alberto Mario Camargo Castro on 30-Apr-16.
 */
public class Bill  {

    private int value;
    private String name;

    public Bill(int value, String name) {
        this.value = value;
        this.name = name;
    }

    public Bill(int value) {
        this.value = value;
        assignName(value);
    }

    public int getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setValue(int value) {
        this.value = value;
    }


    private void assignName(int value) {

        switch (value) {
            case 1000:
                this.name = "one thousand";
                break;

            case 2000:
                this.name = "two thousand";
                break;

            case 5000:
                this.name = "five thousand";
                break;

            case 10000:
                this.name = "ten thousand";
                break;

            case 20000:
                this.name = "twenty thousand";
                break;

            case 50000:
                this.name = "fifty thousand";
                break;

            case 100000:
                this.name = "one hundred thousand";
                break;

        }
    }
}
