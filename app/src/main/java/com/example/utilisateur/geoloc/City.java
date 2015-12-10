package com.example.utilisateur.geoloc;

/**
 * Created by godevin on 03/12/2015.
 */
public class City {
    private int id;
    private String nom;

    public City(String nom){
        this.nom = nom;
    }

    public String getNom() {
        return nom;
    }

    public int getId() {
        return id;
    }

    public void setNom() {
        this.nom=nom;
    }
}
