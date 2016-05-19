package br.com.caelum.cadastro;

import android.os.Bundle;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;

/**
 * Created by android5908 on 15/04/16.
 */
public class Configurador implements GoogleApiClient.ConnectionCallbacks {
    private AtualizadorDeLocalizacao atualizador;

    public Configurador(AtualizadorDeLocalizacao atual){
        atualizador = atual;
    }

    @Override
    public void onConnected(Bundle bundle){
        LocationRequest request = LocationRequest.create();
        request.setInterval(2000);
        request.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        request.setSmallestDisplacement(50);

        atualizador.inicia(request);

    }

    @Override
    public void onConnectionSuspended(int i) {

    }
}
