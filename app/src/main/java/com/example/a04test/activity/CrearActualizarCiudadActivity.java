package com.example.a04test.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Toast;

import com.example.a04test.R;
import com.example.a04test.model.Ciudad;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

import io.realm.Realm;

public class CrearActualizarCiudadActivity extends AppCompatActivity {

    private Realm realm;

    private EditText txtCiudadNombre;
    private EditText txtCiudadDescripcion;
    private EditText txtCiudadLink;
    private ImageView imgCiudad;
    private Button btnPrevisualizar;
    private FloatingActionButton fab;
    private RatingBar ratingBarCiudad;

    private int ciudadId;
    private boolean operaCreacion;
    private Ciudad ciudad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_actualizar_ciudad);

        initBD();
        initComponents();

        validarOperacion();

        setActivityTitle();

        if (!operaCreacion) {
            ciudad = obtenerCiudadPorId(ciudadId);
            llenarCamposParaActualizar();
        }

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                agregarEditarCiudad();
            }
        });

        btnPrevisualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String link = txtCiudadLink.getText().toString();
                if (link.length() > 0)
                    cargarImagenParaPrevisualizar(link);
            }
        });
    }

    private void agregarEditarCiudad() {
        if(dataValidaParaCrearCiudad()){
            String nombre = txtCiudadNombre.getText().toString();
            String descripcion = txtCiudadDescripcion.getText().toString();
            String link = txtCiudadLink.getText().toString();
            float estrellas = ratingBarCiudad.getRating();

            Ciudad ciudad = new Ciudad(nombre, descripcion, estrellas, link);
            if(!operaCreacion) ciudad.setId(ciudadId);

            realm.beginTransaction();
            realm.copyToRealmOrUpdate(ciudad);
            realm.commitTransaction();

            irAActivityPrincipal();
        } else {
            Toast.makeText(this, "Datos incorrectos, validar y volver a probar",
                    Toast.LENGTH_LONG).show();
        }
    }

    private Ciudad obtenerCiudadPorId(int idCiudad) {
        return realm.where(Ciudad.class).equalTo("id", idCiudad).findFirst();
    }

    private void irAActivityPrincipal(){
        Intent intent = new Intent(CrearActualizarCiudadActivity.this, CiudadActivity.class);
        startActivity(intent);
    }

    private boolean dataValidaParaCrearCiudad() {
        if (txtCiudadNombre.getText().toString().length() > 0
                && txtCiudadDescripcion.getText().toString().length() > 0
                && txtCiudadLink.getText().toString().length() > 0) {
            return true;
        } else {
            return false;
        }
    }

    private void llenarCamposParaActualizar() {
        txtCiudadNombre.setText(ciudad.getNombre());
        txtCiudadDescripcion.setText(ciudad.getDescripcion());
        txtCiudadLink.setText(ciudad.getImagen());
        cargarImagenParaPrevisualizar(ciudad.getImagen());
        ratingBarCiudad.setRating(ciudad.getEstrellas());
    }

    private void cargarImagenParaPrevisualizar(String linkImagen) {
        Picasso.get().load(linkImagen).fit().into(imgCiudad);
    }

    private void setActivityTitle() {
        String titulo = "Editar ciudad";
        if (operaCreacion) titulo = "Crear nueva ciudad";
        setTitle(titulo);
    }

    private void validarOperacion() {
        if (getIntent().getExtras() != null) {
            ciudadId = getIntent().getExtras().getInt("id");
            operaCreacion = false;
        } else {
            operaCreacion = true;
        }
    }

    private void initComponents() {
        txtCiudadNombre = findViewById(R.id.txtCiudadNombre);
        txtCiudadDescripcion = findViewById(R.id.txtCiudadDescripcion);
        txtCiudadLink = findViewById(R.id.txtCiudadLink);
        imgCiudad = findViewById(R.id.imgCiudad);
        btnPrevisualizar = findViewById(R.id.btnPrevisualizar);
        fab = findViewById(R.id.fabCiudadGrabar);
        ratingBarCiudad = findViewById(R.id.ratingBarCiudad);
    }

    private void initBD() {
        realm = Realm.getDefaultInstance();
    }
}
