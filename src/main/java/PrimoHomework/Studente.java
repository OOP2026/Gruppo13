package PrimoHomework;

import java.util.Date;

public class Studente extends Utente {
    public Studente(String nome, String cognome, String password, String login, String email) {
        super(nome, cognome, password, login, email);
    }

    public Richiesta faiRichiesta(char stato, Date data){
        Richiesta r=new Richiesta(stato,data,this)
    }

}
