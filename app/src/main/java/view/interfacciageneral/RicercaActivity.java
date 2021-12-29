package view.interfacciageneral;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import com.example.unisalib.R;
import presenter.FacadePresenter;

public class RicercaActivity extends Activity {
    public ListView listView;
    public FacadePresenter fp;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.utente_ricerca_libri);
        fp=new FacadePresenter();
        Intent i=getIntent();
        String[] categorie=i.getStringArrayExtra("Categorie");
        listView=findViewById(R.id.mylistview);

        ArrayAdapter arrayAdapter=new ArrayAdapter(this,R.layout.categoria_list_element,R.id.elem_lista_categoria, categorie);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                fp.ricercaLibriCategoria(listView.getItemAtPosition(position).toString());
            }
        });

        SearchView sv=findViewById(R.id.simpleSearchView);
        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                fp.ricercaLibri(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }
}
