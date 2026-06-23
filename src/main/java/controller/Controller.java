package controller;
import database_connection.ConnessioneDatabase;
import implementazioneDao.*;
import model.*;

import java.sql.ResultSet;
import java.lang.Exception.*;
import java.sql.SQLException;
import java.time.*;
import java.util.*;
import static implementazioneDao.SQLExceptionHandler.*;

public class Controller {
	ArrayList<Docente> docenti = new ArrayList<>();
	ArrayList<Studente> studenti = new ArrayList<>();
	ArrayList<Tirocinio> tirocini= new ArrayList<>();
	ArrayList<Richiesta> richieste = new ArrayList<>();
	ArrayList<Seduta> sedute=new ArrayList<>();
	ArrayList<Tesi> tesi=new ArrayList<>();
	StudenteImplementazionePostgres studenteDao = new StudenteImplementazionePostgres();
	DocenteImplementazionePostgres docenteDao = new DocenteImplementazionePostgres();
	RichiestaImplementazionePostgres richiestaDao = new RichiestaImplementazionePostgres();
	TesiImplementazionePostgres  tesiDao = new TesiImplementazionePostgres();
	TirocinioImplementazionePostgres tirocinioDao = new TirocinioImplementazionePostgres();
	TirocinioEsternoImplementazionePostgres tirocinioEsternoDao = new TirocinioEsternoImplementazionePostgres();
	SedutaImplementazionePostgres sedutaDao = new SedutaImplementazionePostgres();

	public Controller(){
		//todo
	}

	public Utente login(String email, String password, boolean isDocente)throws InconsistencyException{
		Utente log=null;
		if(isDocente){
			for(Docente x:docenti){
				log=x.logIn(email, password);
			}
		}
		else{
			for (Studente x : studenti) {
				log = x.logIn(email, password);
			}
		}
		if (log==null)
			try {
				if(isDocente&&!docenteDao.login(email,password,ConnessioneDatabase.getInstance()))
					throw new InconsistencyException("Docente trovato in locale e non in db");
				else if(!isDocente&&!studenteDao.login(email,password,ConnessioneDatabase.getInstance()))
					throw new InconsistencyException("Studente trovato in locale e non in db");
			}
			catch (SQLException e) {
				handleSQLException(e);
			}
		return log;
	}
	public void logout(Utente u,boolean isDocente)throws InconsistencyException{
			try{
				boolean x;
				u.logOut();
				if(isDocente)
					x=docenteDao.logout(u.getLogin(),ConnessioneDatabase.getInstance());
				else
					x=studenteDao.logout(u.getLogin(),ConnessioneDatabase.getInstance());
				if(!x)
					throw new InconsistencyException("Docente logout in locale ma non in db");
			}
			catch(NullPointerException e){
				System.out.println("Problema nell'esecuzione\n");
				e.printStackTrace();
			}
			catch (SQLException e) {
				handleSQLException(e);
			}
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


	public boolean nuovoTirocinioEsterno(Docente docente,String nome, String descrizione,LocalDate data,String nomeAzienda,String referente){
		Tirocinio x=docente.aggiungiTirocinio(nome,descrizione,data,nomeAzienda,referente);
		//integrità con DB
		//se DB Approva Modifica
		tirocini.add(x);
		return true;
	}

	public boolean nuovoTirocinioInterno(Docente docente,String nome, String descrizione,LocalDate data) {
		Tirocinio t=docente.aggiungiTirocinio(nome,descrizione,data);
		//integrità con DB
		//se DB Approva Modifica
		tirocini.add(t);
		return true;
	}

	public boolean faiRichiesta(Studente studente,Tirocinio tirocinio) throws NullPointerException{
		try{
			Richiesta r=studente.faiRichiesta(LocalDate.now(ZoneId.systemDefault()),tirocinio);
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
			if(!isDocente&&x.getStudente().equals(user)||isDocente&&x.getTirocinio().getRelatore().equals(user))
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

	public boolean aggiungiSedutaDiLaurea(Docente d,LocalDate data,LocalTime ora) throws NullPointerException{
		try{
			sedute.add(d.aggiungiSedutaDiLaurea(data,ora));
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
			if(isDocente&&x.getDocente().equals(u)||!isDocente)
				lista.add(x);
		}
		return lista;
	}

	public String vediSedutaDiLaurea(Utente u,boolean isDocente){
		String s="Elenco Sedute:\n";
		for(Seduta x:sedute){
			if(isDocente&&x.getDocente().equals(u)||!isDocente&&x.getTesi().getRichiesta().getStudente().equals(u))
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
		return st.prenotaSedutadiLaurea(se,t);
	}
	public Boolean inserisciVotoSedutaDiLaurea(Docente d,Seduta s,int voto){
		return d.aggiungiVotoSedutaDiLaurea(s,voto);
	}
}
/*
 ui: login docente
 login studente
 home docente (pulsanti che chiamano altre finestre e funzioni controller)
 home studente
 schema albero per sanificare input
*/