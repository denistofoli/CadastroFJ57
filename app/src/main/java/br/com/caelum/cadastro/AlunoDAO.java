package br.com.caelum.cadastro;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by android5908 on 06/04/16.
 */
public class AlunoDAO extends SQLiteOpenHelper {
    private static final int VERSAO = 3;
    private static final String DATABASE = "CadastroCaelum";
    private static final String TABELA = "Alunos";

    public AlunoDAO(Context context){
        super(context, DATABASE,null,VERSAO);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String ddl = "CREATE TABLE " + TABELA
                + " (id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + " nome TEXT NOT NULL,"
                + " telefone TEXT,"
                + " endereco TEXT,"
                + " site TEXT,"
                + " caminhoFoto TEXT,"
                + " nota REAL);";

        db.execSQL(ddl);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion >= 3){
            String ddl = "ALTER TABLE " + TABELA + " ADD COLUMN caminhoFoto TEXT;";
            db.execSQL(ddl);
        }
    }

    public void insere(Aluno aluno){
        ContentValues values = new ContentValues();

        values.put("nome", aluno.getNome());
        values.put("telefone", aluno.getTelefone());
        values.put("endereco", aluno.getEndereco());
        values.put("site", aluno.getSite());
        values.put("nota", aluno.getNota());
        values.put("caminhoFoto",aluno.getCaminhoFoto());

        getWritableDatabase().insert(TABELA, null, values);
    }

    public List<Aluno> getLista(){
        List<Aluno> alunos = new ArrayList<Aluno>();

        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + TABELA + ";",null);

        while (c.moveToNext()){
            Aluno aluno = new Aluno();

            aluno.setId(c.getLong(c.getColumnIndex("id")));
            aluno.setNome(c.getString(c.getColumnIndex("nome")));
            aluno.setEndereco(c.getString(c.getColumnIndex("endereco")));
            aluno.setTelefone(c.getString(c.getColumnIndex("telefone")));
            aluno.setSite(c.getString(c.getColumnIndex("site")));
            aluno.setNota(c.getDouble(c.getColumnIndex("nota")));
            aluno.setCaminhoFoto(c.getString(c.getColumnIndex("caminhoFoto")));

            alunos.add(aluno);
        }
        c.close();
        return alunos;
    }

    public void deletar(Aluno aluno){
        String[] args = {aluno.getId().toString()};
        getWritableDatabase().delete(TABELA,"id=?", args);
    }

    public void alterar(Aluno aluno){
        ContentValues values = new ContentValues();

        values.put("nome", aluno.getNome());
        values.put("telefone", aluno.getTelefone());
        values.put("endereco", aluno.getEndereco());
        values.put("site", aluno.getSite());
        values.put("nota", aluno.getNota());
        values.put("caminhoFoto",aluno.getCaminhoFoto());

        getWritableDatabase().update(TABELA, values, "id=?", new String[] { aluno.getId().toString() });
    }

    public void gravar(Aluno aluno){
        if (aluno.getId() != null){
            alterar(aluno);
        } else {
            insere(aluno);
        }
    }

    public boolean isAluno(String telefone){
        String[] parametros = {telefone};

        Cursor rawQuery = getReadableDatabase().rawQuery("SELECT telefone FROM " + TABELA + " WHERE telefone = ?",parametros);

        int total = rawQuery.getCount();
        rawQuery.close();

        return total > 0;
    }

}
