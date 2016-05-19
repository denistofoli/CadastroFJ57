package br.com.caelum.cadastro;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Created by android5908 on 14/04/16.
 */
public class DetalhesProvaFragment extends Fragment {
    private Prova prova;
    private TextView materia;
    private TextView data;
    private ListView topicos;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View layout = inflater.inflate(R.layout.fragment_detalhes_prova, container, false);

        if (getArguments() != null){
            this.prova = (Prova) getArguments().getSerializable("prova");
        }

        buscaComponentes(layout);
        populaCamposComDados(prova);

        return layout;
    }

    private void buscaComponentes(View layout){
        this.materia = (TextView) layout.findViewById(R.id.detalhe_prova_materia);
        this.data = (TextView) layout.findViewById(R.id.detalhe_prova_data);
        this.topicos = (ListView) layout.findViewById(R.id.detalhe_prova_topicos);
    }

    public void populaCamposComDados(Prova prv){
        if (prv != null){
            this.materia.setText(prv.getMateria());
            this.data.setText(prv.getData());

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,prv.getTopicos());
            this.topicos.setAdapter(adapter);
        }
    }
}
