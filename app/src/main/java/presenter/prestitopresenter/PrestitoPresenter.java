package presenter.prestitopresenter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Toast;

import androidx.preference.PreferenceManager;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;
import model.prestitomanagement.Prestito;
import model.utentemanagement.Utente;
import view.interfacciageneral.MainActivity;
import view.interfacciautenteunisa.DettagliLibroUtenteUnisaActivity;
import view.interfacciautenteunisa.HomeUtenteUnisaActivity;
import view.interfacciautenteunisa.MieiPrestitiActivity;

public class PrestitoPresenter {
    static final String GenericURL="http://192.168.1.7:8080/UnisaLIBServer/PrestitoPresenter";
    private AsyncHttpClient client=new AsyncHttpClient();
    public void creaPrestito(Prestito p) {
        String MYURL=GenericURL + "/crea-prestito";
        RequestParams params;
        params=new RequestParams();
        params.put("prestito", Prestito.toJson(p));
        client.post(MYURL, params, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    Utente utenteAggiornato = Utente.fromJson(response);
                    if(utenteAggiornato!=null) {
                        SharedPreferences userSession = PreferenceManager.getDefaultSharedPreferences(DettagliLibroUtenteUnisaActivity.getAppContext());
                        SharedPreferences.Editor editor = userSession.edit();
                        editor.putString("Utente", Utente.toJson(utenteAggiornato)).apply();
                        Toast.makeText(DettagliLibroUtenteUnisaActivity.getAppContext(), "Prestito avvenuto con successo. Ritirare il libro in biblioteca", Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                System.out.println("Prestito fallito");
                Toast.makeText(DettagliLibroUtenteUnisaActivity.getAppContext(), errorResponse.toString() + ". Riprovare più tardi", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                System.out.println("Prestito fallito");
                Toast.makeText(DettagliLibroUtenteUnisaActivity.getAppContext(), errorResponse.toString() + ". Riprovare più tardi", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                System.out.println("Prestito fallito");
                Toast.makeText(DettagliLibroUtenteUnisaActivity.getAppContext(), responseString + ". Riprovare più tardi", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void mostraMieiPrestiti(Context c) {
        SharedPreferences userSession = PreferenceManager.getDefaultSharedPreferences(c);
        String MYURL=GenericURL + "/all-prestiti";
        RequestParams params;
        params=new RequestParams();
        Utente u=Utente.fromJson(userSession.getString("Utente",""));
        params.put("utente",u.getEmail());
        client.post(MYURL, params, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                super.onSuccess(statusCode, headers, response);
                Intent i = new Intent();
                i.setClass(c, MieiPrestitiActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.putExtra("prestiti",""+response);
                c.startActivity(i);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
            }
        });

    }

    public void valutaPrestito(Prestito p, int voto, String commento) {
        String MYURL=GenericURL + "/valuta-prestito";

        System.out.println("Commento: " + commento);

        Prestito p_valutato= new Prestito.PrestitoBuilder().
                utente(p.getUtente()).
                libro(p.getLibro()).
                dataInizio(p.getDataInizio()).
                dataFine(p.getDataFine()).
                dataConsegna(p.getDataConsegna()).
                attivo(p.isAttivo()).
                voto(voto).
                commento(commento).
                build();

        System.out.println("Commento prestito: " + p_valutato.getCommento());

        RequestParams params;
        params= new RequestParams();
        params.put("prestito", Prestito.toJson(p_valutato));
        client.post(MYURL, params, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    Utente utenteAggiornato = Utente.fromJson(response);
                    if(utenteAggiornato!=null) {
                        SharedPreferences userSession = PreferenceManager.getDefaultSharedPreferences(MieiPrestitiActivity.getAppContext());
                        SharedPreferences.Editor editor = userSession.edit();
                        editor.putString("Utente", Utente.toJson(utenteAggiornato)).apply();

                        userSession = PreferenceManager.getDefaultSharedPreferences(MieiPrestitiActivity.getAppContext());
                        Utente utente=Utente.fromJson(userSession.getString("Utente", ""));
                        ArrayList<Prestito> prestiti=utente.getPrestiti();

                        Intent i = new Intent();
                        i.setClass(MieiPrestitiActivity.getAppContext(), MieiPrestitiActivity.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        i.putExtra("prestiti",Prestito.toJson(prestiti));
                        MieiPrestitiActivity.getAppContext().startActivity(i);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
            }
        });
    }
}
