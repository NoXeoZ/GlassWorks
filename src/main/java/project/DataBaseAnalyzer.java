package project;

import project.entities.Drug;
import project.entities.Pharm;

import java.math.RoundingMode;
import java.sql.*;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DataBaseAnalyzer {

    public static List<Drug> drugs=new ArrayList<Drug>();
    public static List<Pharm> pharms=new ArrayList<Pharm>();

    public static Drug getRandomDrug(){
        return drugs.get(new Random().nextInt(drugs.size()));
    }

    public static Pharm getRandomPharm(){
        return pharms.get(new Random().nextInt(pharms.size()));
    }

    public static void ScanData() {
            System.out.println("Drugs");
            Connection c = null;
            Statement stmt = null;
            try {
                Class.forName("org.postgresql.Driver");
                c = DriverManager
                        .getConnection("jdbc:postgresql://localhost:5432/project",
                                "postgres", "admin");
                c.setAutoCommit(false);
                System.out.println("Opened database successfully");
                stmt = c.createStatement();
                ResultSet rs = stmt.executeQuery( "SELECT * FROM public.drugs4projet;" );

                while ( rs.next() ) {
                    int cip = rs.getInt("cip");
                    double prix=rs.getFloat("prix");
                    prix+=(new Random().nextDouble()*0.2-0.1)*prix*100;
                    drugs.add(new Drug(cip,prix));
                }
                rs.close();
                stmt.close();
                c.close();
            } catch ( Exception e ) {
                System.err.println( e.getClass().getName()+": "+ e.getMessage() );
                System.exit(0);
            }
            System.out.println("Operation done successfully");

            System.out.println("Pharma");
            c = null;
            stmt = null;
            try {
                Class.forName("org.postgresql.Driver");
                c = DriverManager
                        .getConnection("jdbc:postgresql://localhost:5432/project",
                                "postgres", "admin");
                c.setAutoCommit(false);
                System.out.println("Opened database successfully");
                stmt = c.createStatement();
                ResultSet rs = stmt.executeQuery( "SELECT * FROM public.pharm4projet;" );
                while ( rs.next() ) {
                    int id = rs.getInt("id");
                    String nom=rs.getString("nom");
                    String adresse=rs.getString("adresse");
                    String depart=rs.getString("depart");
                    String region=rs.getString("region");
                    pharms.add(new Pharm(id, nom, adresse, depart, region));
                }
                rs.close();
                stmt.close();
                c.close();
            } catch ( Exception e ) {
                System.err.println( e.getClass().getName()+": "+ e.getMessage() );
                System.exit(0);
            }
            System.out.println("Operation done successfully");

            System.out.println("Drug size: "+drugs.size());
            System.out.println("Pharm size: "+pharms.size());
        }
    }

