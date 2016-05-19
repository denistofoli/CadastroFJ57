package br.com.caelum.cadastro;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by android5908 on 14/04/16.
 */
public class ProvasActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstaceState){
        super.onCreate(savedInstaceState);
        setContentView(R.layout.activity_provas);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (isTablet()){
            transaction.replace(R.id.provas_lista, new ListaProvasFragment());
            transaction.replace(R.id.provas_detalhes, new DetalhesProvaFragment());
        } else {
            transaction.replace(R.id.provas_view, new ListaProvasFragment());
        }
        transaction.commit();

    }

    private boolean isTablet(){
        return getResources().getBoolean(R.bool.isTablet);
    }

    public void selecionaProva(Prova prova){
        FragmentManager manager = getSupportFragmentManager();

        if (isTablet()){
            DetalhesProvaFragment detalhesProva = (DetalhesProvaFragment) manager.findFragmentById(R.id.provas_detalhes);
            detalhesProva.populaCamposComDados(prova);
        } else{
            Bundle argumentos = new Bundle();
            argumentos.putSerializable("prova",prova);

            DetalhesProvaFragment detalhesProva = new DetalhesProvaFragment();
            detalhesProva.setArguments(argumentos);

            FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(R.id.provas_view, detalhesProva);

            transaction.addToBackStack(null);

            transaction.commit();
        }
    }

}
