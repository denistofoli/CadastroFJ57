package br.com.caelum.cadastro;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;

import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.List;

/**
 * Created by android5908 on 14/04/16.
 */
public class Localizador {
    private Geocoder geo;

    public Localizador(Context context){
        geo = new Geocoder(context);
    }

    public LatLng getCoords(String endereco){
        try {
            List<Address> ends = geo.getFromLocationName(endereco,1);

            if (!ends.isEmpty()){
                Address resultados = ends.get(0);
                return new LatLng(resultados.getLatitude(),resultados.getLongitude());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
