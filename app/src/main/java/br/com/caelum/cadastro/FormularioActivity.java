package br.com.caelum.cadastro;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;

public class FormularioActivity extends AppCompatActivity {
    private FormularioHelper helper;
    private AlunoDAO dao;
    private Aluno aluno;
    private static final int TIRAR_FOTO = 123;
    private String localArquivoFoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario);
        helper = new FormularioHelper(this);

        Button fotoButton = helper.getFotoButton();
        fotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ActivityCompat.checkSelfPermission(FormularioActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        String[] perms = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
                        requestPermissions(perms,123);
                    }
                } else {
                    localArquivoFoto = getExternalFilesDir(null) + "/" + System.currentTimeMillis() + ".jpg";
                    Uri localFoto = Uri.fromFile(new File(localArquivoFoto));

                    Intent irParaCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    irParaCamera.putExtra(MediaStore.EXTRA_OUTPUT,localFoto);
                    startActivityForResult(irParaCamera,TIRAR_FOTO);

                }
            }
        });


        aluno = (Aluno) getIntent().getSerializableExtra(MainActivity.ALUNOSEL);
        if (aluno != null){
            helper.colocaNoFormulario(aluno);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_form, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.menu_form_ok:
                aluno = helper.pegaAlunoDoFormulario();

                if (helper.temNome()){
                    dao = new AlunoDAO(this);
                    dao.gravar(aluno);
                    dao.close();

                    Toast.makeText(this, "Aluno " + aluno.getNome() + " salvo com sucesso", Toast.LENGTH_LONG).show();
                    finish();
                } else {
                    helper.mostraErro();
                }

                return false;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if (requestCode == TIRAR_FOTO){
            if (resultCode == Activity.RESULT_OK){
                helper.carregaImagem(localArquivoFoto);;
            } else {
                localArquivoFoto = null;
            }

        }
    }
}
