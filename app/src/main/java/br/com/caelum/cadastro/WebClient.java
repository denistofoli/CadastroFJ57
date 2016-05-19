package br.com.caelum.cadastro;

import java.io.IOException;
import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by android5908 on 12/04/16.
 */
public class WebClient {
    public String post(String json){
        String jsonDeResposta = "Algo deu errado";

        try {
            URL url = new URL("https://www.caelum.com.br/mobile");

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Accept","application/json");
            connection.setRequestProperty("Content-type","application/json");
            connection.setDoInput(true);
            connection.setDoOutput(true);

            PrintStream saida = new PrintStream(connection.getOutputStream());
            saida.println(json);

            connection.connect();

            jsonDeResposta = new Scanner(connection.getInputStream()).next();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return jsonDeResposta;
    }
}
