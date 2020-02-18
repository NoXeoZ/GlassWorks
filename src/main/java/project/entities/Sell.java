package project.entities;

import java.util.Random;

public class Sell {
    private String nom;
    private String prenom;
    private int cip;
    private double prix;
    private int idPharm;

    public Sell(Person p, Drug g, Pharm ph) {
        this.nom = p.getLastName();
        this.prenom=p.getFirstName();
        this.cip = g.getCip();
        this.prix=(new Random().nextDouble()*0.2-0.1)*g.getPrix()+g.getPrix();
        this.idPharm=ph.getId();
    }

    public Sell(String nom, String prenom, int cip, double prix, int idPharm) {
        this.nom = nom;
        this.prenom = prenom;
        this.cip = cip;
        this.prix = prix;
        this.idPharm = idPharm;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public int getCip() {
        return cip;
    }

    public double getPrix() {
        return prix;
    }

    public int getIdPharm() {
        return idPharm;
    }
}
