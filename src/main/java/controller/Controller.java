package controller;
import jdk.vm.ci.meta.Local;
import model.*;
import java.time.*;
import java.util.*;


public class Controller {
	ArrayList<Docente> docenti = new ArrayList<>();
	ArrayList<Studente> studenti = new ArrayList<>();
	ArrayList<Tirocinio> tirocini= new ArrayList<>();
	ArrayList<Richiesta> richieste = new ArrayList<>();
	ArrayList<Seduta> seduta=new ArrayList<>();
	ArrayList<Tesi> tesi=new ArrayList<>();

	public Controller() {

	}

	public Utente login(String email, String password, boolean isDocente){
		Utente log=null;
		if(isDocente){
			for(Docente x:docenti){
				log=x.logIn(email, password);
				if (log!=null) return log;
			}
		}
		else{
			for (Studente x : studenti){
				log=x.logIn(email, password);
				if (log!=null) return log;
			}
		}
		return log;
	}

	public String vediTirocini(Utente user, boolean isDocente){
		String s="Elenco tirocini:\n";
		for(Tirocinio x:tirocini){
			if(!isDocente||x.getRelatore().equals(user))
				s.concat("\n"+x.toString());
		}
		return s;
	}
	public ArrayList<Tirocinio> GetTirocini(Utente user,boolean isDocente){
		ArrayList<Tirocinio> t=new ArrayList<>();
		for(Tirocinio x:tirocini){
			if(!isDocente||x.getRelatore().equals(user))
				t.add(x);
		}
		return t;
	}


	public boolean nuovoTirocinioEsterno(String nome, String descrizione,Docente relatore,String nomeAzienda,String referente) {
		TirocinioEsterno x =new TirocinioEsterno(nome,descrizione,relatore,nomeAzienda,referente);
		//integrità con DB
		//if(DBApprovaModifica)
		tirocini.add(x);
		return true;
	}

	public boolean nuovoTirocinioInterno(String nome, String descrizione,Docente relatore) {
		TirocinioInterno x = new TirocinioInterno(nome, descrizione, relatore);
		//integrità con DB
		//if(DBApprovaModifica)
		tirocini.add(x);
		return true;
	}

	public boolean faiRichiesta(Studente studente,Tirocinio tirocinio){
		Richiesta r=studente.faiRichiesta('?', Date.from(Instant.from(LocalDate.now())),tirocinio);
		//integrità con DB
		//if(DBApprovaModifica)
		richieste.add(r);
		return true;
	}

	public ArrayList<Richiesta> vediRichiesta(Utente user,boolean isDocente){
		ArrayList<Richiesta> r=new ArrayList<>();
		for(Richiesta x:richieste){
			if(!isDocente&&x.getStudente().equals((Studente)user))
				r.add(x);
			else if(x.getTirocinio().getRelatore().equals((Docente)user))
				r.add(x);
		}
		return r;
	}


}
/*
todo:
login studente OK
login docente OK
docente\studente:vedi tirocinio OK ish
docente: nuovo tirocinio OK
docente:accetta/rifiuta richiesta
docente: elimina tirocinio
docente\studente:vedi elaborato
docente:accetta/rifiuta elaborato
docente:aggiungi seduta di laurea
docente:carica voto seduta di laurea
studente:fai richiesta OK
studente\docente:vedi richieste OK
studente:vedi stato richieste
studente:aggiungi elaborato
studente:modifica elaborato
studente:prenota seduta di laurea
studente visualizza sedute di laurea

 ui: login docente
 login studente
 home docente (pulsanti che chiamano altre finestre e funzioni controller)
 home studente
 schema albero per sanificare input
*/