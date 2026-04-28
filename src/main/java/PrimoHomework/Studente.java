package PrimoHomework;

import java.util.ArrayList;
import java.util.Date;

public class Studente extends Utente {
    private String matricola;
    private ArrayList<Richiesta> richieste = new ArrayList<Richiesta>();

    public Studente(String nome, String cognome, String password, String login, String email, String matricola) {
        super(nome, cognome, password, login, email);
        this.matricola = matricola;
    }

    public Richiesta faiRichiesta(char stato, Date data){
        Richiesta r=new Richiesta(stato,data,this);
        richieste.add(r);
        return r;
    }

    public String getMatricola() {
        return matricola;
    }

    
}
