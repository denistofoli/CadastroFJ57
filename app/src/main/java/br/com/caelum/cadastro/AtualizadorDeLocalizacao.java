package br.com.caelum.cadastro;

import android.location.Location;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by android5908 on 15/04/16.
 */
public class AtualizadorDeLocalizacao implements LocationListener {
    private GoogleApiClient client;
    private MostraAluno mapa;

    public AtualizadorDeLocalizacao(MostraAluno maps) {
        mapa = maps;

        Configurador config = new Configurador(this);
        this.client = new GoogleApiClient.Builder(mapa)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(config)
                .build();
        this.client.connect();
    }

    @Override
    public void onLocationChanged(Location location) {
        Double latitude = location.getLatitude();
        Double longitude = location.getLongitude();

        LatLng local = new LatLng(latitude, longitude);

        mapa.centraliza(local);
    }

    public void inicia(LocationRequest request) {
        LocationServices.FusedLocationApi.requestLocationUpdates(client, request, this);
    }

    public void cancela(){
        LocationServices.FusedLocationApi.removeLocationUpdates(this.client,this);
        this.client.disconnect();
    }
}
