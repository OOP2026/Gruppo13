package model;

public abstract class Utente {
    protected String nome;
    protected String cognome;
    protected String password;
    protected String login;
    protected String email;
    protected boolean loggedin = false;

    protected Utente(String nome, String cognome, String password, String login,String  email){
            this.nome = nome;
            this.cognome = cognome;
            this.password = password;
            this.login = login;
            this.email = email;
    }

    public abstract String visualizzaRichiesta(Richiesta richiesta);
    public abstract String visualizzaTesi(Tesi tesi);
    public abstract String visualizzaSedutaDiLaurea(Seduta seduta);
    public void setPassword(String password){
        if (loggedin) {
            this.password = password;
        }
    }

    public void setUsername(String login){
        if (loggedin){
            this.login = login;
        }
    }

    public Utente logIn(String login, String password) {
        if (login.equals(this.login) && password.equals(this.password)) {
            loggedin = true;
            System.out.println("Sei loggato");
            return this;
        }
        else {
            System.out.println("Credenziali sbagliate");
            return null;
        }

    }

    public String getNome() {
        return nome;
    }

    public String getCognome() {
        return cognome;
    }

    public String getLogin() {
        return login;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {return password;}

    public void logOut(){
        loggedin=false;
        System.out.println("LogOut eseguito");
    }
}
