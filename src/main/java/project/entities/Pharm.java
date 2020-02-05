package project.entities;

public class Pharm{
    private int id;
    private String nom;
    private String adresse;
    private String depart;
    private String region;

    public Pharm(int id, String nom, String adresse, String depart, String region) {
        this.id = id;
        this.nom = nom;
        this.adresse = adresse;
        this.depart = depart;
        this.region = region;
    }

    public int getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String getAdresse() {
        return adresse;
    }

    public String getDepart() {
        return depart;
    }

    public String getRegion() {
        return region;
    }
}
