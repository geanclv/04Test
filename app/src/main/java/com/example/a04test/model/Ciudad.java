package com.example.a04test.model;

import com.example.a04test.application.MyApplication;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

public class Ciudad extends RealmObject {

    @PrimaryKey
    private int id;
    @Required
    private String nombre;
    @Required
    private String descripcion;
    private float estrellas;
    @Required
    private String imagen;

    public Ciudad() {
    }

    public Ciudad(String nombre, String descripcion, float estrellas, String imagen) {
        this.id = MyApplication.CiudadID.incrementAndGet();
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.estrellas = estrellas;
        this.imagen = imagen;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public float getEstrellas() {
        return estrellas;
    }

    public void setEstrellas(float estrellas) {
        this.estrellas = estrellas;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }
}
