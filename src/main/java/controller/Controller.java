package controller;
import model.*;
import java.time.*;
import java.util.*;
import java.lang.*;
import java.util.logging.Logger;

public class Controller {
	ArrayList<Docente> docenti = new ArrayList<>();
	ArrayList<Studente> studenti = new ArrayList<>();
	ArrayList<Tirocinio> tirocini= new ArrayList<>();
	ArrayList<Richiesta> richieste = new ArrayList<>();
	ArrayList<Seduta> seduta=new ArrayList<>();
	ArrayList<Tesi> tesi=new ArrayList<>();

	public Controller() {
		//Integrità con il DB dei dati
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
				s=s.concat("\n"+x.toString());
		}
		return s;
	}
	public ArrayList<Tirocinio> getTirocini(Utente user,boolean isDocente){
		ArrayList<Tirocinio> t=new ArrayList<>();
		for(Tirocinio x:tirocini){
			if(!isDocente||x.getRelatore().equals(user))
				t.add(x);
		}
		return t;
	}


	public boolean nuovoTirocinioEsterno(Docente docente,String nome, String descrizione,String nomeAzienda,String referente){
		Tirocinio x=docente.aggiungiTirocinio(nome,descrizione,nomeAzienda,referente);
		//integrità con DB
		//se DB Approva Modifica
		tirocini.add(x);
		return true;
	}

	public boolean nuovoTirocinioInterno(Docente docente,String nome, String descrizione) {
		Tirocinio t=docente.aggiungiTirocinio(nome,descrizione);
		//integrità con DB
		//se DB Approva Modifica
		tirocini.add(t);
		return true;
	}

	public boolean faiRichiesta(Studente studente,Tirocinio tirocinio) throws NullPointerException{
		try{
			Richiesta r=studente.faiRichiesta(Date.from(Instant.from(LocalDate.now())),tirocinio);
			//integrità con DB
			//se DB Approva Modifica
			richieste.add(r);
			return true;
		}
		catch(NullPointerException e){
			System.out.println("Richiesta non inserita, controlla studente e/o tirocinio");
			e.printStackTrace();
		}
	}

	public ArrayList<Richiesta> vediRichiesta(Utente user,boolean isDocente){
		ArrayList<Richiesta> r=new ArrayList<>();
		for(Richiesta x:richieste){
			if(!isDocente&&x.getStudente().equals((Studente)user)||isDocente&&x.getTirocinio().getRelatore().equals((Docente)user))
				r.add(x);
		}
		return r;
	}

	public boolean modificaStatoRichiesta(Docente docente,Richiesta richiesta,boolean ok){
			if(ok){
				//Integrità con DB
				return docente.accettaRichiesta(richiesta);
			}
			else {
				//Integrità con DB
				return docente.rifiutaRichiesta(richiesta);
			}
	}
	//MAY BE NULL
	public String getStatoRichiesta(Studente s,Richiesta richiesta){
		return s.visualizzaRichiesta(richiesta);
	}

	public boolean eliminaTirocinio(Docente docente,Tirocinio tirocinio){
		if(tirocinio.getRelatore().equals(docente)&&tirocini.contains(tirocinio)){
			tirocini.remove(tirocinio);
			return true;
		}
		return false;
	}
	//MAY BE NULL
	public Tirocinio cercaTirocinio (String nome){
		for(Tirocinio x:tirocini){
			if(x.getNome().equals(nome)){
				return x;
			}
		}
		return null;
	}
	//MAY BE NULL
	public Docente cercaDocente (String nome, String cognome){
		for(Docente x:docenti){
			if(x.getNome().equals(nome)&&x.getCognome().equals(cognome)){
				return x;
			}
		}
		return null;
	}
	//MAY BE NULL
	public Richiesta cercaRichiesta (Studente studente,Tirocinio tirocinio){
		for (Richiesta x : richieste) {
			if(x.getStudente().equals(studente)&&x.getTirocinio().equals(tirocinio))
				return x;
		}
		return null;
	}
	//MAY BE NULL
	public Tesi cercaTesi (Richiesta richiesta){
		for(Tesi x:tesi){
			if(x.getRichiesta().equals(richiesta))
				return x;
		}
		return null;
	}
	//MAY BE NULL
	public Seduta cercaSeduta(Tesi tesi){
		for(Seduta x:seduta){
			if(x.getTesi().equals(tesi))
				return x;
		}
		return null;
	}
	//MAY BE NULL
	public String visualizzaRichiesta(Utente user,Richiesta richiesta){
		return user.visualizzaRichiesta(richiesta);
	}

	public boolean modificaTesi(Studente studente,Tesi tesi,String contenuto){
		return studente.aggiornaTesi(tesi,contenuto);
	}

	public boolean aggiungiTesi(Studente studente,Richiesta r, String contenuto) throws NullPointerException{
		try {
			if (r.getStudente().equals(studente)){
				Tesi x=(studente.aggiungiTesi(r,contenuto);
				//Integrità con il db
				tesi.add(x);
				return true;
			}
			return false;
		}
		catch(NullPointerException e){
			System.out.println("Tesi non inserita correttamente, ricontrolla la richiesta!");
			e.printStackTrace();
		}

	}

	//MAY BE NULL
	public String visualizzaTesi(Utente user,Tesi tesi){
		return user.visualizzaTesi(tesi);
	}




}
/*
todo:
login studente OK
login docente OK
docente\studente:vedi tirocinio OK
docente: nuovo tirocinio OK
docente:accetta/rifiuta richiesta OK
docente: elimina tirocinio OK
docente/studente:cerca tirocinio OK
cerca elaborato e cerca richiesta ok
docente\studente:vedi elaborato ok
docente:accetta/rifiuta elaborato
docente:aggiungi seduta di laurea
docente:carica voto seduta di laurea
studente:fai richiesta OK
studente\docente:vedi richieste OK
studente:vedi stato richieste OK
studente:aggiungi elaborato OK
studente:modifica elaborato OK
studente:prenota seduta di laurea
studente visualizza sedute di laurea

 ui: login docente
 login studente
 home docente (pulsanti che chiamano altre finestre e funzioni controller)
 home studente
 schema albero per sanificare input
*/