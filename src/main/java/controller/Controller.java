package controller;
import model.*;
import java.time.*;
import java.util.*;

public class Controller {
	ArrayList<Docente> docenti = new ArrayList<>();
	ArrayList<Studente> studenti = new ArrayList<>();
	ArrayList<Tirocinio> tirocini= new ArrayList<>();
	ArrayList<Richiesta> richieste = new ArrayList<>();
	ArrayList<Seduta> sedute=new ArrayList<>();
	ArrayList<Tesi> tesi=new ArrayList<>();

	public Controller() {
		//Integrità con il DB dei dati
		//valori di test da eliminare
		docenti.add(new Docente("pippo", "poppo", "pippo06", "pippo6", "pippo@pippo.com", false));
		studenti.add(new Studente("pippo", "poppo", "pippo06", "pippo6", "pippo@pippo.com","PIPPOPIPPO"));
		tirocini.add(new Tirocinio("","",docenti.get(0),LocalDate.now()));
		richieste.add(new Richiesta(LocalDate.now(),studenti.get(0),tirocini.get(0)));
		tesi.add(new Tesi("www.pippo.it",richieste.get(0)));
		sedute.add(new Seduta(LocalDate.now(),docenti.get(0)));
		sedute.get(0).setTesi(tesi.get(0));

	}

	public Utente login(String email, String password, boolean isDocente){
		Utente log=null;
		if(isDocente){
			for(Docente x:docenti){
				log=x.logIn(email, password);
			}
		}
		else{
			for (Studente x : studenti){
				log=x.logIn(email, password);
			}
		}
		return log;
	}
	public void logout(Utente u){
			u.logOut();
	}

	public String vediTirocini(Utente user, boolean isDocente){
		String s="Elenco tirocini:\n";
		for(Tirocinio x:tirocini){
			if(!isDocente||x.getRelatore().equals(user))
				s=s.concat("\n"+x.toString());
		}
		return s;
	}
	public List<Tirocinio> getTirocini(Utente user,boolean isDocente){
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
			Richiesta r=studente.faiRichiesta(LocalDate.now(),tirocinio);
			//integrità con DB
			//se DB Approva Modifica
			richieste.add(r);
			return true;
		}
		catch(NullPointerException e){
			System.out.println("Richiesta non inserita, controlla studente e/o tirocinio");
			e.printStackTrace();
			return false;
		}
	}

	public List<Richiesta> getRichiesta(Utente user,boolean isDocente){
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

	public String getStatoRichiesta(Utente u,Richiesta richiesta){
		return u.visualizzaRichiesta(richiesta);
	}

	public boolean modificaStatoTesi(Docente docente,Tesi t,boolean ok){
		if(ok){
			//Integrità con DB
			return docente.accettaTesi(t);
		}
		else {
			//Integrità con DB
			return docente.rifiutaTesi(t);
		}
	}
	//MAY BE NULL
	public String getStatoTesi(Utente u,Tesi t){
		return u.visualizzaTesi(t);
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
		for(Seduta x:sedute){
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
				Tesi x=studente.aggiungiTesi(r,contenuto);
				//Integrità con il db
				tesi.add(x);
				return true;
			}
			return false;
		}
		catch(NullPointerException e){
			System.out.println("Tesi non inserita correttamente, ricontrolla la richiesta!");
			e.printStackTrace();
			return false;
		}
	}

	//MAY BE NULL
	public String visualizzaTesi(Utente user,Tesi tesi){
		return user.visualizzaTesi(tesi);
	}

	public boolean aggiungiSedutaDiLaurea(Docente d,LocalDate data) throws NullPointerException{
		try{
			sedute.add(d.aggiungiSedutaDiLaurea(data));
			return true;
		}
		catch(NullPointerException e){
			System.out.println("Seduta non inserita correttamente,ricontrolla la data!");
			e.printStackTrace();
			return false;
		}
	}

	//MAY BE NULL
	public String visualizzaSedutaDiLaurea(Utente u,Seduta seduta){
		return u.visualizzaSedutaDiLaurea(seduta);
	}

	public List<Seduta> getSedutaDiLaurea(Utente u, boolean isDocente){
		ArrayList<Seduta> lista = new ArrayList<>();
		for(Seduta x:sedute){
			if(isDocente&&x.getDocente().equals((Docente)u)||!isDocente)
				lista.add(x);
		}
		return lista;
	}

	public String vediSedutaDiLaurea(Utente u,boolean isDocente){
		String s="Elenco Sedute:\n";
		for(Seduta x:sedute){
			if(isDocente&&x.getDocente().equals((Docente)u)||!isDocente&&x.getTesi().getRichiesta().getStudente().equals((Studente)u))
				s=s.concat(x.toString());
		}
		return s;
	}

	//may be null
	public Seduta cercaSedutaDiLaurea(Docente d,LocalDate data){
		for (Seduta x : sedute) {
			if(x.getDocente().equals(d) && x.getData().equals(data))
				return x;
			}
		return null;
	}
	public Boolean prenotaSedutaDiLaurea(Studente st,Seduta se,Tesi t){
		return st.PrenotaSedutadiLaurea(se,t);
	}
	public Boolean inserisciVotoSedutaDiLaurea(Docente d,Seduta s,int voto){
		return d.aggiungiVotoSedutaDiLaurea(s,voto);
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
docente:accetta/rifiuta elaborato ok
docente:aggiungi seduta di laurea OK
docente:carica voto seduta di laurea OK
studente:fai richiesta OK
studente\docente:vedi richieste OK
studente:vedi stato richieste OK
studente:aggiungi elaborato OK
studente:modifica elaborato OK
studente visualizza sedute di laurea OK
studente prenota seduta di laurea OK

 ui: login docente
 login studente
 home docente (pulsanti che chiamano altre finestre e funzioni controller)
 home studente
 schema albero per sanificare input
*/