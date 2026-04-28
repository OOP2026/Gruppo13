package PrimoHomework;

public class TirocinioEsterno extends Tirocinio{
    protected String nomeAzienda;
    protected String referente;

    protected TirocinioEsterno(String nome, String descrizione, Docente relatore,String nomeAzienda,String referente) {
        super(nome, descrizione, relatore);
        this.nomeAzienda = nomeAzienda;
        this.referente = referente;
    }

    public String getNomeAzienda() {
        return nomeAzienda;
    }

    public String getReferente() {
        return referente;
    }
}
