package com.example.a04test.application;

import android.app.Application;

import com.example.a04test.model.Ciudad;

import java.util.concurrent.atomic.AtomicInteger;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmObject;
import io.realm.RealmResults;

public class MyApplication extends Application {

    public static AtomicInteger CiudadID = new AtomicInteger();

    @Override
    public void onCreate() {
        super.onCreate();

        configureRealm();

        Realm realm = Realm.getDefaultInstance();
        CiudadID = getIdByTable(realm, Ciudad.class);

        realm.close();
    }

    private void configureRealm(){
        Realm.init(this);
        RealmConfiguration configuration = new RealmConfiguration
                .Builder()
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(configuration);
    }

    private <T extends RealmObject> AtomicInteger getIdByTable(Realm realm, Class<T> anyClass){
        RealmResults<T> results = realm.where(anyClass).findAll();
        return (results.size() > 0)
                ? new AtomicInteger(results.max("id").intValue())
                : new AtomicInteger();
    }
}
