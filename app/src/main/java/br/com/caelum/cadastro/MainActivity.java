package br.com.caelum.cadastro;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<Aluno> alunos;
    private ListView lista;
    public static final String ALUNOSEL = "alunoSelecionado";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                String[] perms = {Manifest.permission.CALL_PHONE};
                requestPermissions(perms,123);
            }
        }

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                String[] perms = {Manifest.permission.RECEIVE_SMS};
                requestPermissions(perms,123);
            }
        }

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                String[] perms = {Manifest.permission.INTERNET};
                requestPermissions(perms,123);
            }
        }


        lista = (ListView) findViewById(R.id.lista);
        registerForContextMenu(lista);

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent edicao = new Intent(MainActivity.this, FormularioActivity.class);
                edicao.putExtra(ALUNOSEL, (Aluno) lista.getItemAtPosition(position));
                startActivity(edicao);
            }
        });

        /*
        lista.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                String nome = parent.getAdapter().getItem(position).toString();

                Toast.makeText(MainActivity.this, "Nome: " + nome, Toast.LENGTH_LONG).show();

                return true;
            }
        });*/

        Button buttonAdd = (Button) findViewById(R.id.floatingAdd);
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, FormularioActivity.class);
                startActivity(intent);
            }
        });
    }

    private void carregaLista() {
        AlunoDAO dao = new AlunoDAO(this);
        alunos = dao.getLista();
        dao.close();

        //final ArrayAdapter<Aluno> adapter = new ArrayAdapter<Aluno>(this, android.R.layout.simple_list_item_1, alunos);
        ListaAlunosAdapter adapter = new ListaAlunosAdapter(this, alunos);

        lista.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        carregaLista();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        final Aluno alunoSelecionado = (Aluno) lista.getAdapter().getItem(info.position);

        getMenuInflater().inflate(R.menu.menu_context, menu);

        // Menu Ligar
        MenuItem ligar = menu.getItem(0);
        Intent intentLigar = new Intent(Intent.ACTION_CALL);
        intentLigar.setData(Uri.parse("tel:"+alunoSelecionado.getTelefone()));
        ligar.setIntent(intentLigar);

        // Menu SMS
        Intent intentSMS = new Intent(Intent.ACTION_VIEW);
        intentSMS.setData(Uri.parse("sms:" + alunoSelecionado.getTelefone()));
        intentSMS.putExtra("sms_body", "um pedaco da mensagem");
        MenuItem sms = menu.getItem(1);
        sms.setIntent(intentSMS);

        // Menu Mapa
        Intent intentMapa = new Intent(Intent.ACTION_VIEW);
        intentMapa.setData(Uri.parse("geo:0,0?z=14&q=" + alunoSelecionado.getEndereco()));
        MenuItem mapa = menu.getItem(2);
        mapa.setIntent(intentMapa);

        // Menu Site
        Intent intentSite = new Intent(Intent.ACTION_VIEW);
        String siteURL = alunoSelecionado.getSite();
        if (!siteURL.startsWith("http://")) {
            siteURL = "http://" + siteURL;
        }
        intentSite.setData(Uri.parse(siteURL));
        MenuItem site = menu.getItem(3);
        site.setIntent(intentSite);


        // Menus Deletar
        MenuItem deletar = menu.getItem(4);
        deletar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                new AlertDialog.Builder(MainActivity.this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Excluir")
                        .setMessage("Deseja mesmo excluir")
                        .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                AlunoDAO dao = new AlunoDAO(MainActivity.this);
                                dao.deletar(alunoSelecionado);
                                dao.close();

                                carregaLista();
                            }
                        }).setNegativeButton("Não", null).show();
                return false;
            }
        });
    }

    private void fazerLigacao() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            Intent intentLigar = new Intent(Intent.ACTION_CALL);
            startActivity(intentLigar);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_lista_alunos,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.menu_enviar_notas:
                new EnviaAlunoTask(this).execute();
                return true;
            case R.id.menu_receber_provas:
                Intent provas = new Intent(this, ProvasActivity.class);
                startActivity(provas);
                return true;
            case R.id.menu_mapa:
                Intent maps = new Intent(this, MostraAluno.class);
                startActivity(maps);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
