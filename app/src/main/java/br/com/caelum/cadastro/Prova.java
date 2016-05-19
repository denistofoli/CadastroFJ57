package br.com.caelum.cadastro;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by android5908 on 14/04/16.
 */
public class Prova implements Serializable {
    private String data;
    private String materia;
    private List<String> topicos = new ArrayList<String>();

    public Prova (String dt, String mat){
        this.data = dt;
        this.materia = mat;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getMateria() {
        return materia;
    }

    public void setMateria(String materia) {
        this.materia = materia;
    }

    public List<String> getTopicos() {
        return topicos;
    }

    public void setTopicos(List<String> topicos) {
        this.topicos = topicos;
    }

    @Override
    public String toString(){
        return materia;
    }
}
