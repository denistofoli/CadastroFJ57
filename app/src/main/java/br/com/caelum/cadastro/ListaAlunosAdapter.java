package br.com.caelum.cadastro;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.util.List;

/**
 * Created by android5908 on 11/04/16.
 */
public class ListaAlunosAdapter extends BaseAdapter {
    private final List<Aluno> alunos;
    private final Activity activity;

    public ListaAlunosAdapter(Activity activity, List<Aluno> alunos){
        this.activity = activity;
        this.alunos = alunos;
    }

    @Override
    public int getCount() {
        return alunos.size();
    }

    @Override
    public Object getItem(int position) {
        return alunos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return alunos.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = activity.getLayoutInflater().inflate(R.layout.item, parent, false);

        Aluno aluno = alunos.get(position);
        TextView nome = (TextView) view.findViewById(R.id.item_nome);
        nome.setText(aluno.getNome());

        String localArquivoFoto = aluno.getCaminhoFoto();
        if (localArquivoFoto != null){
            if (! localArquivoFoto.isEmpty()){
                File arqFoto = new File(localArquivoFoto);
                if (arqFoto.exists()){
                    ImageView foto = (ImageView) view.findViewById(R.id.item_foto);

                    Bitmap imagemFoto = BitmapFactory.decodeFile(localArquivoFoto);
                    Bitmap imagemReduz = Bitmap.createScaledBitmap(imagemFoto, 100 , 100, true);

                    foto.setImageBitmap(imagemReduz);;
                    foto.setTag(localArquivoFoto);
                    foto.setScaleType(ImageView.ScaleType.FIT_XY);
                }
            }
        }

        TextView telefone = (TextView) view.findViewById(R.id.item_telefone);
        TextView site = (TextView) view.findViewById(R.id.item_site);

        if (telefone != null){
            telefone.setText(aluno.getTelefone());
            site.setText(aluno.getSite());
        }

        if (position % 2 == 0){
            view.setBackgroundColor(activity.getResources().getColor(R.color.linhaPar));
        } else {
            view.setBackgroundColor(activity.getResources().getColor(R.color.linhaImpar));
        }

        return view;
    }
}
