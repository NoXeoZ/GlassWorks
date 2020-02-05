package project.entities;

public class Sell {
    private Person p;
    private Drug g;
    private Pharm ph;

    public Sell(Person p, Drug g, Pharm ph) {
        this.p = p;
        this.g = g;
        this.ph = ph;
    }
}
