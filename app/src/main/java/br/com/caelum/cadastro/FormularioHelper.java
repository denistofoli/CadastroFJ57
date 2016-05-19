package br.com.caelum.cadastro;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;

import java.io.File;

/**
 * Created by android5908 on 06/04/16.
 */
public class FormularioHelper {
    private Aluno aluno;

    private EditText nome;
    private EditText telefone;
    private EditText endereco;
    private EditText site;
    private RatingBar nota;
    private ImageView foto;
    private Button fotoButton;

    public FormularioHelper(FormularioActivity activity) {
        aluno = new Aluno();

        nome = (EditText) activity.findViewById(R.id.textName);
        telefone = (EditText) activity.findViewById(R.id.textPhone);
        endereco = (EditText) activity.findViewById(R.id.textAddress);
        site = (EditText) activity.findViewById(R.id.textSite);
        nota = (RatingBar) activity.findViewById(R.id.ratingScore);
        foto = (ImageView) activity.findViewById(R.id.imgFormFoto);
        fotoButton = (Button) activity.findViewById(R.id.btnFormFoto);
    }

    public Aluno pegaAlunoDoFormulario(){
        aluno.setNome(nome.getText().toString());
        aluno.setTelefone(telefone.getText().toString());
        aluno.setEndereco(endereco.getText().toString());
        aluno.setSite(site.getText().toString());
        aluno.setNota((double) nota.getProgress());
        aluno.setCaminhoFoto((String) foto.getTag());

        return aluno;
    }

    public void colocaNoFormulario(Aluno alunoSel){
        aluno = alunoSel;

        nome.setText(aluno.getNome());
        telefone.setText(aluno.getTelefone());
        endereco.setText(aluno.getEndereco());
        site.setText(aluno.getSite());
        nota.setProgress(aluno.getNota().intValue());
        carregaImagem(aluno.getCaminhoFoto());
    }

    public boolean temNome(){
        return !nome.getText().toString().isEmpty();
    }

    public void mostraErro(){
        nome.setError("Campo nome não pode ser vazio");
    }

    public Button getFotoButton(){ return fotoButton;}

    public void carregaImagem(String localArquivoFoto){
        if (localArquivoFoto != null){
            if (! localArquivoFoto.isEmpty()){
                File arqFoto = new File(localArquivoFoto);
                if (arqFoto.exists()){
                    Bitmap imagemFoto = BitmapFactory.decodeFile(localArquivoFoto);
                    Bitmap imagemReduz = Bitmap.createScaledBitmap(imagemFoto,imagemFoto.getWidth(), 300, true);

                    foto.setImageBitmap(imagemReduz);;
                    foto.setTag(localArquivoFoto);
                    foto.setScaleType(ImageView.ScaleType.FIT_XY);
                }
            }
        }
    }

}
