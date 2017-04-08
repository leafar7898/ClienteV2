package br.usjt.ftce.desmob.clientev1;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class MainActivity extends AppCompatActivity {
    EditText textNome;
    ArrayList<Cliente> lista;
    ClienteRequester requester;
    Intent intent;
    String chave;

    public static final String SERVIDOR = "http://10.0.2.2:8080";
    public static final String APLICACAO = "/arqdsis_poetas";
    public static final String RECURSO = "/cliente";
    public static final String CHAVE = "br.usjt.ftce.desmob.clientev1.busca";
    public static final String LISTA = "br.usjt.ftce.desmob.clientev1.lista";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textNome = (EditText) findViewById(R.id.buscar_clientes);
    }

    public void buscarCliente(View view) {
        intent = new Intent(this, ListarClientesActivity.class);
        chave  = textNome.getText().toString();
        requester = new ClienteRequester();
        if(requester.isConnected(this)){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        lista = requester.get(SERVIDOR+APLICACAO+RECURSO, chave);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                intent.putExtra(LISTA, lista);
                                startActivity(intent);
                            }
                        });
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }).start();
        } else{
            Toast toast = Toast.makeText(this, "Rede indisponivel", Toast.LENGTH_LONG);
        }

        //startActivity(intent);
    }
}
