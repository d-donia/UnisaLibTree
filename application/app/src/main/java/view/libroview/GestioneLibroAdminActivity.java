package view.libroview;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.preference.PreferenceManager;

import com.example.unisalib.R;
import com.google.gson.JsonSyntaxException;

import model.utentemanagement.Utente;
import presenter.FacadePresenter;
import view.utenteview.HomeAdminActivity;

public class GestioneLibroAdminActivity extends Activity {
    private static Context context;
    FacadePresenter fp;
    Utente u;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_gestione_libri);
        context=getApplicationContext();
        fp=new FacadePresenter();
        ImageButton userIB=findViewById(R.id.gestioneLibroIB);
        userIB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog confermaLogout = new AlertDialog.Builder(GestioneLibroAdminActivity.this).
                        setTitle("Logout").
                        setMessage("Sicuro di voler uscire?").
                        setPositiveButton(R.string.si, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                fp.logout(GestioneLibroAdminActivity.getAppContext());
                                finish();
                            }
                        }).
                        setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        }).show();
            }
        });
        SharedPreferences userSession = PreferenceManager.getDefaultSharedPreferences(this);
        try {
            u = Utente.fromJson(userSession.getString("Utente", ""));
        }
        catch (JsonSyntaxException e){
            e.printStackTrace();
        }
        Button addLibro= findViewById(R.id.addButton);
        Button modifyLibro=findViewById(R.id.modifyButton);

        addLibro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fp.informazioniAggiuntaLibro();
            }
        });

        modifyLibro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fp.mostraRicercaLibri(u.isAdmin(),getApplicationContext());
            }
        });
    }

    public static Context getAppContext(){
        return context;
    }
}
