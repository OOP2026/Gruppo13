package model;

public class Tesi {
    private char stato='?';
    private String contenuto;
    private Richiesta richiesta;

    public Tesi(String contenuto,Richiesta richiesta){
        this.contenuto = contenuto;
        this.richiesta = richiesta;
    }

    public char getStato() {
        return stato;
    }
    public void setStato(char stato) {
        this.stato = stato;
    }
    public String getContenuto() {
        return contenuto;
    }
    public void setContenuto(String contenuto) {
        this.contenuto = contenuto;
    }
    public Richiesta getRichiesta() {
        return richiesta;
    }
    public void setRichiesta(Richiesta richiesta) {
        this.richiesta = richiesta;
    }
}
