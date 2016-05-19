package br.com.caelum.cadastro;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.util.List;

/**
 * Created by android5908 on 12/04/16.
 */
public class EnviaAlunoTask extends AsyncTask<Object, Object, String>{
    private final Context context;
    private ProgressDialog progress;

    public EnviaAlunoTask(Context contextPar){
        this.context = contextPar;
    }

    @Override
    protected void onPreExecute(){
        progress = ProgressDialog.show(context, "Aguarde...","Envio para a Web ...", true, true);
    }

    @Override
    protected String doInBackground(Object... paranms){
        AlunoDAO dao = new AlunoDAO(context);
        List<Aluno> alunos = dao.getLista();
        dao.close();

        String json = new AlunoConverter().toJSON(alunos);
        WebClient client = new WebClient();
        String resposta = client.post(json);

        return resposta;
    }

    @Override
    public void onPostExecute(String result){
        progress.dismiss();
        Toast.makeText(context ,result,Toast.LENGTH_LONG).show();
    }
}
