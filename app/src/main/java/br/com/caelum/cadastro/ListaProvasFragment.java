package br.com.caelum.cadastro;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.List;

/**
 * Created by android5908 on 14/04/16.
 */
public class ListaProvasFragment extends Fragment {
    private ListView listViewProvas;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState){
        View layoutProvas = inflater.inflate(R.layout.fragment_lista_provas,container,false);

        listViewProvas = (ListView) layoutProvas.findViewById(R.id.lista_provas_listview);

        Prova prova1 = new Prova("20/06/2015","Matematica");
        prova1.setTopicos(Arrays.asList("Algebra Linear","Cálculo","Estatistica"));

        Prova prova2 = new Prova("25/07/2015","Portugues");
        prova2.setTopicos(Arrays.asList("Complemento nominal","Orações subordinadas","Analise sintatica"));

        List<Prova> provas = Arrays.asList(prova1,prova2);

        listViewProvas.setAdapter(new ArrayAdapter<Prova>(getActivity(),android.R.layout.simple_list_item_1, provas));

        listViewProvas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Prova selecionada = (Prova) parent.getItemAtPosition(position);
                Toast.makeText(getActivity(),"Prova selecionada: " + selecionada.toString(),Toast.LENGTH_LONG).show();

                ProvasActivity calendarioProvas = (ProvasActivity) getActivity();
                calendarioProvas.selecionaProva(selecionada);
            }
        });

        return layoutProvas;
    }
}
