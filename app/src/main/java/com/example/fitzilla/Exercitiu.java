package com.example.fitzilla;

import androidx.annotation.NonNull;

public class Exercitiu {

    private String nume;
    private String grupa;

    private String descriere;



    public Exercitiu(String nume, String grupa, String descriere) {
        this.nume = nume;
        this.grupa = grupa;
        this.descriere = descriere;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getGrupa() {
        return grupa;
    }

    public void setGrupa(String grupa) {
        this.grupa = grupa;
    }

    public String getDescriere() {
        return descriere;
    }

    public void setDescriere(String descriere) {
        this.descriere = descriere;
    }

    @NonNull
    @Override
    public String toString() {
        return "nume: " + nume + " grupa: " + grupa + " descriere: " + descriere;
    }
}
