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
    public double getPrix(){return prix;}

    @Override
    public boolean equals(Object o) {
        if (o instanceof Drug) {
            if (((Drug) o).cip == this.cip) {
                return true;
            }
        }
        return false;
    }
}