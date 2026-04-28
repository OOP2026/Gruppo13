package PrimoHomework;

public class Utente {
    protected String nome;
    protected String cognome;
    protected String password;
    protected String login;
    protected String email;
    protected boolean loggedin = false;

    public Utente(String nome, String cognome, String password, String login,String  email){
            this.nome = nome;
            this.cognome = cognome;
            this.password = password;
            this.login = login;
            this.email = email;
    }

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

    public void logIn(String email, String password) {
        if (email.equals(this.email) && password.equals(this.password)) {
            loggedin = true;
            System.out.println("Sei loggato");
        }
        else {
            System.out.println("Credenziali sbagliate");
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

    public void logOut(){
        loggedin=false;
        System.out.println("LogOut eseguito");
    }
}
