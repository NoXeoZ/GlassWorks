package project.entities;

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
        this.prix=g.getPrix();
        this.idPharm=ph.getId();
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
