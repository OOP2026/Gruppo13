package controller;
import implementazioneDao.*;
import model.*;
import utilities.InconsistencyException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.*;
import java.util.*;

import static database_connection.ConnessioneDatabase.getInstance;
import static utilities.ExceptionHandler.*;

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
	public Controller() throws InconsistencyException {
		try {
			ResultSet x = studenteDao.getAll(getInstance());
			while(x.next()){
				studenti.add(new Studente(x.getString("nome"),x.getString("cognome"),x.getString("password"),x.getString("login"),x.getString("email"),x.getString("matricola")));
			}
			x=docenteDao.getAll(getInstance());
			while(x.next()){
				docenti.add(new Docente(x.getString("nome"),x.getString("cognome"),x.getString("password"),x.getString("login"),x.getString("email"),x.getBoolean("coordinatore")));
			}
			x=tirocinioDao.getAll(getInstance());
			while(x.next()){
				tirocini.add(new TirocinioInterno(x.getString("nome"),x.getString("descrizione"),cercaDocente(x.getString("login")),x.getDate("Data").toLocalDate()));
			}
			x=tirocinioEsternoDao.getAll(getInstance());
			while(x.next()){
				tirocini.add(new TirocinioEsterno(x.getString("nome"),x.getString("descrizione"),cercaDocente(x.getString("login")),x.getDate("Data").toLocalDate(),x.getString("nomeazienda"),x.getString("referente")));
			}
			x=richiestaDao.getAll(getInstance());
			while(x.next()){
				Richiesta r = new Richiesta(x.getDate("r.data").toLocalDate(),cercaStudente(x.getString("login")),cercaTirocinio(x.getString("t.nome"),x.getDate("t.data").toLocalDate(),cercaDocente(x.getString("t.login"))));
				richieste.add(r);
			}
			x=tesiDao.getAll(getInstance());
			while(x.next()){
				tesi.add(new Tesi(x.getString("contenuto"),cercaRichiesta(cercaStudente(x.getString("login")),cercaTirocinio(x.getString("t.nome"),x.getDate("t.data").toLocalDate(),cercaDocente(x.getString("t.login"))))));
			}
			x=sedutaDao.getAll(getInstance());
			while(x.next()){
				sedute.add(new Seduta(x.getDate("se.data").toLocalDate(),x.getTime("ora").toLocalTime(),x.getInt("voto"),cercaTesi(cercaRichiesta(cercaStudente(x.getString("r.login")),cercaTirocinio(x.getString("ti.nome"),x.getDate("ti.data").toLocalDate(),cercaDocente(x.getString("ti.login"))))),cercaDocente(x.getString("te.login"))));
			}
		} catch (SQLException e) {
			throw new InconsistencyException("Fallimento nella presa del db dei dati, impossibile inizializzare localmente");
		}
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
				if(isDocente&&!docenteDao.login(email,password, getInstance()))
					throw new InconsistencyException("Docente trovato in locale e non in db");
				else if(!isDocente&&!studenteDao.login(email,password, getInstance()))
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
					x=docenteDao.logout(u.getLogin(), getInstance());
				else
					x=studenteDao.logout(u.getLogin(), getInstance());
				if(!x)
					throw new InconsistencyException("Docente logout in locale ma non in db");
			}
			catch(NullPointerException e){
				System.out.println("Problema nell'esecuzione\n");
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
		try {
			if (docenteDao.aggiungiTirocinio(docente.getLogin(),nome, descrizione, data,nomeAzienda,referente, getInstance()))
				tirocini.add(x);
		}
		catch (SQLException e){
			handleSQLException(e);
		}
		return true;
	}

	public boolean nuovoTirocinioInterno(Docente docente,String nome, String descrizione,LocalDate data) {
		Tirocinio t=docente.aggiungiTirocinio(nome,descrizione,data);
		try {
			if(docenteDao.aggiungiTirocinio(docente.getLogin(),nome,descrizione,data, getInstance()))
				tirocini.add(t);
		} catch (SQLException e) {
			handleSQLException(e);
		} catch (NullPointerException ex){
			handleNullPointerException(ex);
		}
		return true;
	}

	public boolean faiRichiesta(Studente studente,Tirocinio tirocinio){
		try {
			Richiesta r = studente.faiRichiesta(LocalDate.now(ZoneId.systemDefault()), tirocinio);
			if (studenteDao.aggiungiRichiesta(studente.getMatricola(), tirocinio.getNome(), tirocinio.getData(), tirocinio.getRelatore().getLogin(), getInstance())){
				richieste.add(r);
				return true;
			}
		}
		catch(SQLException e){
			handleSQLException(e);
		}
		catch (NullPointerException ex){
			handleNullPointerException(ex);
		    System.out.println("Richiesta non inserita, controlla studente e/o tirocinio");
		}
		return false;
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
			try {
				if (ok) {
					if (docenteDao.accettaRichiesta(richiesta.getStudente().getLogin(), richiesta.getTirocinio().getNome(), richiesta.getTirocinio().getData(), docente.getLogin(), getInstance()))
						return docente.accettaRichiesta(richiesta);
					else return false;
				} else {
					if (docenteDao.rifiutaRichiesta(richiesta.getStudente().getLogin(), richiesta.getTirocinio().getNome(), richiesta.getTirocinio().getData(), docente.getLogin(), getInstance()))
						return docente.rifiutaRichiesta(richiesta);
				}
			}
			catch(SQLException e){
				handleSQLException(e);
			}
			return false;
	}
	//MAY BE NULL

	public String getStatoRichiesta(Utente u,Richiesta richiesta){
		return u.visualizzaRichiesta(richiesta);
	}

	public boolean modificaStatoTesi(Docente docente,Tesi t,boolean ok){
		try{
			if(ok){
				if (docenteDao.accettaTesi(t.getRichiesta().getStudente().getLogin(),t.getRichiesta().getTirocinio().getNome(),t.getRichiesta().getTirocinio().getData(),t.getRichiesta().getTirocinio().getRelatore().getLogin(), getInstance()))
					return docente.accettaTesi(t);
			}
			else {
				if(docenteDao.rifiutaRichiesta(t.getRichiesta().getStudente().getLogin(),t.getRichiesta().getTirocinio().getNome(),t.getRichiesta().getTirocinio().getData(),t.getRichiesta().getTirocinio().getRelatore().getLogin(), getInstance()))
					return docente.rifiutaTesi(t);
			}
		} catch(SQLException e){
			handleSQLException(e);
		} catch(NullPointerException ex){
			handleNullPointerException(ex);
		}
		return false;
	}
	//MAY BE NULL
	public String getStatoTesi(Utente u,Tesi t){
		return u.visualizzaTesi(t);
	}

	public boolean eliminaTirocinio(Docente docente,Tirocinio tirocinio){
		try{
			if(tirocinio.getRelatore().equals(docente)&&tirocini.contains(tirocinio)&&getInstance().executeUpdate("DELETE FROM Tirocinio WHERE Nome ='"+tirocinio.getNome()+"' AND Data = '"+tirocinio.getData()+"' AND Login = '"+docente.getLogin()+"';")>=1){
					tirocini.remove(tirocinio);
					return true;
			}
		} catch (SQLException e) {
            handleSQLException(e);
        } catch (NullPointerException ex){
			handleNullPointerException(ex);
		}
        return false;
	}
	//MAY BE NULL
	public Tirocinio cercaTirocinio (String nome,LocalDate data,Docente docente)throws InconsistencyException{
		Tirocinio tirocinio = null;
		for(Tirocinio x:tirocini){
			if(x.getNome().equals(nome) && x.getData().equals(data) && x.getRelatore().isSameDocente(docente))
				tirocinio = x;
		}
		try{
			ResultSet x=tirocinioDao.queryViaTirocinio("SELECT TOP 1 FROM Tirocinio Where Nome='"+nome+"' AND Data='"+data+"';",getInstance());
			if(x.next()) {
				if (tirocinio != null)
					return tirocinio;
				else
					throw new InconsistencyException("Tirocinio presente nel db e non in locale");
			}
			else
				throw new InconsistencyException("Tirocinio presente in locale e non nel db");
		}
		catch(SQLException e){
			handleSQLException(e);
		}
		return tirocinio;
	}
	//MAY BE NULL
	public Studente cercaStudente (String login)throws InconsistencyException{
		Studente s = null;
		for(Studente x:studenti){
			if(x.getLogin().equals(login)){
				s=x;
			}
		}
		try{
			ResultSet x=docenteDao.queryViaUtente("SELECT TOP 1 FROM Studente WHERE login = '"+login+"';",getInstance());
			if(x.next()) {
				if (s == null)
					throw new InconsistencyException("Docente presente nel db e non in locale");
			}
			else if (s!=null)
				throw new InconsistencyException("Docente presente in locale e non nel db");
		}
		catch (SQLException e){
			handleSQLException(e);
		}
		return s;
	}
	//MAY BE NULL
	public Docente cercaDocente (String login)throws InconsistencyException{
		Docente docente = null;
		for(Docente x:docenti){
			if(x.getLogin().equals(login)){
				docente=x;
			}
		}
		try{
			ResultSet x=docenteDao.queryViaUtente("SELECT TOP 1 FROM Docente WHERE login = '"+login+"';",getInstance());
			if(x.next()) {
				if (docente != null)
					return docente;
				else
					throw new InconsistencyException("Docente presente nel db e non in locale");
			}
			else if (docente!=null)
				throw new InconsistencyException("Docente presente in locale e non nel db");
		}
		catch (SQLException e){
			handleSQLException(e);
		}
		return docente;
	}
	//MAY BE NULL
	public Richiesta cercaRichiesta (Studente studente,Tirocinio tirocinio) throws InconsistencyException{
		Richiesta richiesta = null;
		for (Richiesta x : richieste) {
			if(x.getStudente().equals(studente)&&x.getTirocinio().equals(tirocinio))
				richiesta= x;

		}
		try{
			ResultSet x =richiestaDao.queryViaRichiesta("SELECT TOP 1 FROM Richiesta WHERE Login='"+studente.getLogin()+"' AND ID_Ti =(SELECT ID_Ti FROM Tirocinio WHERE Nome = '"+tirocinio.getNome()+"' AND Data = '"+tirocinio.getData()+"' AND Login = '"+tirocinio.getRelatore().getLogin()+"');",getInstance());
			if(x.next()){
				if(richiesta!=null)
					return richiesta;
				else
					throw new InconsistencyException("Richiesta presente nel db ma non in locale");
			}
			else if(richiesta!=null)
				throw new InconsistencyException("Richiesta presente in locale e non nel db");
		}
		catch(SQLException e){
			handleSQLException(e);
		}
		return null;
	}
	//MAY BE NULL
	public Tesi cercaTesi (Richiesta richiesta) throws InconsistencyException{
		Tesi t = null;
		for(Tesi x:tesi){
			if(x.getRichiesta().equals(richiesta))
				t= x;
		}
		try{
			ResultSet x=tesiDao.queryViaTesi("SELECT TOP 1 FROM Tesi WHERE ID_Ri=(SELECT ID_Ri FROM Richiesta WHERE Login='"+richiesta.getStudente().getLogin()+"' AND ID_Ti = (Select ID_Ti FROM Tirocinio WHERE Nome = '"+richiesta.getTirocinio().getNome()+"' AND Data = '"+richiesta.getTirocinio().getData()+"' AND Login = '"+richiesta.getTirocinio().getRelatore().getLogin()+"'));",getInstance());
			if(x.next()){
				if (t!=null)
					return t;
				else
					throw new InconsistencyException("Richiesta presente nel db e non in locale");
			}
			else if (t!=null)
				throw new InconsistencyException("Richiesta presente in locale e non nel db");
		}
		catch(SQLException e){
			handleSQLException(e);
		}
		return null;
	}
	//MAY BE NULL
	public Seduta cercaSeduta(Tesi tesi) throws InconsistencyException{
		Seduta seduta=null;
		for(Seduta x:sedute){
			if(x.getTesi().equals(tesi))
				seduta = x;
		}
		try{
			ResultSet x=sedutaDao.queryViaSeduta("SELECT TOP 1 FROM Seduta WHERE ID_Te=(SELECT ID_Te FROM Tesi WHERE ID_Ri=(SELECT ID_Ri FROM Richiesta WHERE Login='"+tesi.getRichiesta().getStudente().getLogin()+"' AND ID_Ti = (Select ID_Ti FROM Tirocinio WHERE Nome = '"+tesi.getRichiesta().getTirocinio().getNome()+"' AND Data = '"+tesi.getRichiesta().getTirocinio().getData()+"' AND Login = '"+tesi.getRichiesta().getTirocinio().getRelatore().getLogin()+"')));",getInstance());
			if(x.next()){
				if (seduta!=null)
					return seduta;
				else
					throw new InconsistencyException("Seduta presente nel db e non in locale");
			}
			else if (seduta!=null)
				throw new InconsistencyException("Seduta presente in locale e non nel db");
		}
		catch(SQLException e){
			handleSQLException(e);
		}
		return null;
	}
	//MAY BE NULL
	public String visualizzaRichiesta(Utente user,Richiesta richiesta){
		return user.visualizzaRichiesta(richiesta);
	}

	public boolean modificaTesi(Studente studente,Tesi tesi,String contenuto){
		try {
			if (studenteDao.aggiornaTesi(studente.getMatricola(), contenuto, tesi.getRichiesta().getTirocinio().getNome(), tesi.getRichiesta().getTirocinio().getData(), tesi.getRichiesta().getTirocinio().getRelatore().getLogin(),getInstance()))
				return studente.aggiornaTesi(tesi, contenuto);
		}
		catch (SQLException e){
			handleSQLException(e);
		}
		return false;
	}

	public boolean aggiungiTesi(Studente studente,Richiesta r, String contenuto) throws NullPointerException{
		try {
			if (r.getStudente().equals(studente)){
				Tesi x=studente.aggiungiTesi(r,contenuto);
				if(studenteDao.aggiungiTesi(studente.getMatricola(),contenuto,r.getTirocinio().getNome(),r.getTirocinio().getData(),r.getTirocinio().getRelatore().getLogin(),getInstance())) {
					tesi.add(x);
					return true;
				}
			}
			return false;
		}
		catch(SQLException e){
			handleSQLException(e);
		}
		catch(NullPointerException e){
			handleNullPointerException(e);
		}
		return false;
	}

	//MAY BE NULL
	public String visualizzaTesi(Utente user,Tesi tesi){
		return user.visualizzaTesi(tesi);
	}

	public boolean aggiungiSeduta(Docente d,LocalDate data,LocalTime ora) throws NullPointerException{
		try{
			if(docenteDao.aggiungiSedutaDiLaurea(data,ora,d.getLogin(),getInstance())) {
				sedute.add(d.aggiungiSedutaDiLaurea(data,ora));
				return true;
			}
		}
		catch (SQLException e){
			handleSQLException(e);
		}
		catch(NullPointerException e){
			handleNullPointerException(e);
		}
		return false;
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
	public Seduta cercaSeduta(Docente d,LocalDate data,LocalTime ora) throws InconsistencyException{
		Seduta seduta=null;
		for (Seduta x : sedute) {
			if(x.getDocente().equals(d) && x.getData().equals(data))
				seduta = x;
			}

		try{
			ResultSet x =sedutaDao.queryViaSeduta("SELECT TOP 1 FROM Seduta WHERE Login='"+d.getLogin()+"' AND Data = '"+data+"' AND Ora = '"+ora+"');",getInstance());
			if(x.next()){
				if(seduta!=null)
					return seduta;
				else
					throw new InconsistencyException("Seduta presente nel db ma non in locale");
			}
			else if(seduta!=null)
				throw new InconsistencyException("Seduta presente in locale e non nel db");
		}
		catch(SQLException e){
			handleSQLException(e);
		}
		return null;
	}
	public Boolean prenotaSedutaDiLaurea(Studente st,Seduta se,Tesi t){
		try{
			if(studenteDao.prenotaSedutaDiLaurea(st.getMatricola(),se.getData(),t.getRichiesta().getTirocinio().getNome(),t.getRichiesta().getTirocinio().getData(),t.getRichiesta().getTirocinio().getRelatore().getLogin(),getInstance()))
				return st.prenotaSedutadiLaurea(se,t);
		}
		catch(SQLException e){
			handleSQLException(e);
		}
		return false;
	}
	public Boolean inserisciVotoSedutaDiLaurea(Docente d,Seduta s,int voto){
		try{
			if(docenteDao.aggiungiVotoSedutaDiLaurea(voto,s.getData(),s.getOra(),s.getDocente().getLogin(),getInstance()))
				return d.aggiungiVotoSedutaDiLaurea(s,voto);
		}
		catch (SQLException e){
			handleSQLException(e);
		}
		return false;
	}
}
/*
 ui: login docente
 login studente
 home docente (pulsanti che chiamano altre finestre e funzioni controller)
 home studente
 schema albero per sanificare input
*/