package model;

import java.util.Date;

public class Seduta {
    private Date data;
    private int voto;
    private Tesi tesi;
    public Seduta(Date data, int voto,Tesi tesi) {
        this.data = data;
        this.voto = voto;
        this.tesi = tesi;
    }
    public Date getData() {
        return data;
    }
    public void setVoto(int voto) {
        this.voto = voto;
    }
    public int getVoto() {
        return voto;
    }
    public Tesi getTesi(){
        return tesi;
    }
}

