package model;

public class TirocinioEsterno extends Tirocinio{
    protected String nomeAzienda;
    protected String referente;

    public TirocinioEsterno(String nome, String descrizione, Docente relatore,String nomeAzienda,String referente) {
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
    public void setNome(String nome){
        this.nome=nome;
    }
    public void setDescrizione(String descrizione){
        this.descrizione=descrizione;
    }
    public String getNome(){
        return nome;
    }
    public String getDescrizione(){
        return descrizione;
    }

    @Override
    public String toString() {
        String s=super.toString();
        s+="\nNome azienda: "+nomeAzienda+"\nReferente: "+referente;
        return s;
    }
}
