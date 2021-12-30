package model.utentemanagement;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import model.libromanagement.Libro;
import model.prenotazionemanagement.Prenotazione;
import model.prestitomanagement.Prestito;

public class Utente implements Serializable {
    private String email, password, nome, cognome, matricola, genere;
    private int eta;
    private boolean admin, nuovo;
    private ArrayList<Libro> interessi;
    private ArrayList<Prestito> prestiti;
    private ArrayList<Prenotazione> prenotazioni;

    public String getEmail() {
        return email;
    }

    public String getPassword() { return password; }

    public String getNome() {
        return nome;
    }

    public String getCognome() {
        return cognome;
    }

    public String getMatricola() {
        return matricola;
    }

    public String getGenere() {
        return genere;
    }

    public int getEta() {
        return eta;
    }

    public boolean isAdmin() {
        return admin;
    }

    public boolean isNuovo() { return nuovo; }

    public ArrayList<Libro> getInteressi() {
        return interessi;
    }

    public ArrayList<Prestito> getPrestiti() {
        return prestiti;
    }

    public ArrayList<Prenotazione> getPrenotazioni() {
        return prenotazioni;
    }

    public static class UtenteBuilder{
        private String email, password, nome, cognome, matricola, genere;
        private int eta;
        private boolean admin, nuovo;
        private ArrayList<Libro> interessi;
        private ArrayList<Prestito> prestiti;
        private ArrayList<Prenotazione> prenotazioni;

        public UtenteBuilder email(String email){
            this.email=email;
            return this;
        }

        public UtenteBuilder password(String password){
            this.password=password;
            return this;
        }

        public UtenteBuilder nome(String nome){
            this.nome=nome;
            return this;
        }

        public UtenteBuilder cognome(String cognome){
            this.cognome=cognome;
            return this;
        }

        public UtenteBuilder matricola(String matricola){
            this.matricola=matricola;
            return this;
        }

        public UtenteBuilder genere(String genere){
            this.genere=genere;
            return this;
        }

        public UtenteBuilder eta(int eta){
            this.eta=eta;
            return this;
        }

        public UtenteBuilder admin(boolean admin){
            this.admin=admin;
            return this;
        }

        public UtenteBuilder nuovo(boolean nuovo){
            this.nuovo=nuovo;
            return this;
        }

        public UtenteBuilder interessi(ArrayList<Libro> interessi){
            this.interessi=interessi;
            return this;
        }

        public UtenteBuilder prestiti(ArrayList<Prestito> prestiti){
            this.prestiti=prestiti;
            return this;
        }

        public UtenteBuilder prenotazioni(ArrayList<Prenotazione> prenotazioni){
            this.prenotazioni=prenotazioni;
            return this;
        }

        public Utente build(){
            return new Utente(this);
        }


    }

    private Utente(UtenteBuilder ub){
        this.email=ub.email;
        this.password=ub.password;
        this.nome=ub.nome;
        this.cognome=ub.cognome;
        this.matricola=ub.matricola;
        this.genere=ub.genere;
        this.eta=ub.eta;
        this.admin=ub.admin;
        this.nuovo=ub.nuovo;
        this.interessi=ub.interessi;
        this.prestiti=ub.prestiti;
        this.prenotazioni=ub.prenotazioni;

    }

    public static Utente fromJson(String json) throws JsonSyntaxException {
        Gson gson = new Gson();
        Utente p = gson.fromJson(json,Utente.class);
        return p;
    }

    public static Utente fromJson(JSONObject json) throws JSONException {
        Gson gson = new Gson();
        Utente p = gson.fromJson(""+json.get("Utente"),Utente.class);
        return p;
    }

    public static Utente[] fromJson(JsonArray response){
        Gson gson = new Gson();
        Utente[] u = gson.fromJson("" + response, Utente[].class);
        return u;
    }

    public static String toJson(Utente u){
        Gson gson = new Gson();
        Type fooType = new TypeToken<Utente>() {}.getType();
        String json = gson.toJson(u,fooType);
        return json;
    }
}
