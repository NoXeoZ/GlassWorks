package project.entities;

public class Drug{
    private int cip;
    private double prix;
    public Drug(int cip, double prix){
        this.cip=cip;
        this.prix=prix;
    }
    public int getCip(){
        return cip;
    }
    public double getPrix(){
        return prix;
    }
}