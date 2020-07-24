package com.example.a04test.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.a04test.R;
import com.example.a04test.adapter.CiudadAdapter;
import com.example.a04test.model.Ciudad;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;

public class CiudadActivity extends AppCompatActivity
        implements RealmChangeListener<RealmResults<Ciudad>> {

    private Realm realm;
    private FloatingActionButton fabCiudadAgregar;

    private RealmResults<Ciudad> lstCiudad;

    private RecyclerView recyclerView;
    private CiudadAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ciudad);

        initDB();
        initComponents();

        listarCiudad();

        configureRecyclerView();
        configureLayoutManager();
        recyclerView.setLayoutManager(layoutManager);

        configureFabCiudadAgregar();
        mostrarOcultarFabCiudadAgregar();

        configureAdapter();

        recyclerView.setAdapter(adapter);
        //lstCiudad.addChangeListener(this);
    }

    private void listarCiudad(){
        lstCiudad = realm.where(Ciudad.class).findAll();
        lstCiudad.addChangeListener(this);
    }

    private void eliminarCiudad(int posicion){
        realm.beginTransaction();
        lstCiudad.get(posicion).deleteFromRealm();
        realm.commitTransaction();
    }

    @Override
    public void onChange(RealmResults<Ciudad> ciudads) {
        adapter.notifyDataSetChanged();
    }

    private void mostrarAlertaParaEliminarCiudad(String titulo, String mensaje, final int posicion){
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle(titulo)
                .setMessage(mensaje)
                .setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        eliminarCiudad(posicion);
                        Toast.makeText(CiudadActivity.this,
                                "La ciudad se ha eliminado correctamente",
                                Toast.LENGTH_LONG).show();
                    }
                }).setNegativeButton("Cancelar", null)
                .show();
    }

    private void configureAdapter(){
        adapter = new CiudadAdapter(this, R.layout.recycler_view_item_ciudad,
                lstCiudad, new CiudadAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Ciudad ciudad, int posicion) {
                Intent intent = new Intent(CiudadActivity.this, CrearActualizarCiudadActivity.class);
                intent.putExtra("id", ciudad.getId());
                startActivity(intent);
            }
        }, new CiudadAdapter.OnButtonClickListener() {
            @Override
            public void onButtonClick(Ciudad ciudad, int posicion) {
                mostrarAlertaParaEliminarCiudad("Eliminar ciudad",
                        "¿Seguro que desea eliminar " + ciudad.getNombre() + "?",
                        posicion);
            }
        });
    }

    private void mostrarOcultarFabCiudadAgregar(){
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                if(dy > 0)
                    fabCiudadAgregar.hide();
                else if(dy < 0)
                    fabCiudadAgregar.show();
            }
        });
    }

    private void configureFabCiudadAgregar(){
        fabCiudadAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CiudadActivity.this, CrearActualizarCiudadActivity.class);
                startActivity(intent);
            }
        });
    }

    private void configureLayoutManager(){
        layoutManager = new LinearLayoutManager(this);
    }

    private void configureRecyclerView(){
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    private void initComponents(){
        fabCiudadAgregar = findViewById(R.id.fabCiudadAgregar);
        recyclerView = findViewById(R.id.recyclerViewCiudad);
    }

    private void initDB(){
        realm = Realm.getDefaultInstance();
    }
}
