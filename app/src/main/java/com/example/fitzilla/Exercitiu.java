package com.example.fitzilla;

import androidx.annotation.NonNull;

public class Exercitiu {

    private String nume;
    private String grupa;

    private String descriere;

    int nrSerii;
    private String[] greutate;
    private String[] nrRepetari;



    public Exercitiu(String nume, String grupa, String descriere,int nrSerii, String[] greutate, String[] nrRepetari) {
        this.nume = nume;
        this.grupa = grupa;
        this.descriere = descriere;
        this.nrSerii = nrSerii;
        this.greutate = greutate;
        this.nrRepetari = nrRepetari;
    }

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

    public int getNrSerii() {
        return nrSerii;
    }

    public void setNrSerii(int nrSerii) {
        this.nrSerii = nrSerii;
    }

    public String[] getGreutate() {
        return greutate;
    }

    public void setGreutate(String[] greutate) {
        this.greutate = greutate;
    }

    public String[] getNrRepetari() {
        return nrRepetari;
    }

    public void setNrRepetari(String[] nrRepetari) {
        this.nrRepetari = nrRepetari;
    }

    @Override
    public String toString() {
        return "Exercitiu{" +
                "nume='" + nume + '\'' +
                ", grupa='" + grupa + '\'' +
                ", descriere='" + descriere + '\'' +
                ", greutate='" + greutate + '\'' +
                ", nrRepetari='" + nrRepetari + '\'' +
                '}';
    }
}
