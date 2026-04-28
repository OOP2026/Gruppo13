package PrimoHomework;

public class Tirocinio {
    private String nome;
    private String descrizione;
    private Docente relatore;
    public Tirocinio(String nome, String descrizione,Docente relatore) {
        this.nome = nome;
        this.descrizione = descrizione;
        this.relatore = relatore;
    }
    public String getNome() {return this.nome;}
    public String getDescrizione() {return this.descrizione;}
    
}
