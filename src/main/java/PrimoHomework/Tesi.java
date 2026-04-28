package PrimoHomework;

public class Tesi {
    private char stato;
    private String contenuto;
    private Seduta seduta;

    public Tesi(char stato, String contenuto, Seduta seduta){
        this.stato = stato;
        this.contenuto = contenuto;
        this.seduta = seduta;
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
    public Seduta getSeduta() {
        return seduta;
    }
    public void setSeduta(Seduta seduta) {
        this.seduta = seduta;
    }


}
