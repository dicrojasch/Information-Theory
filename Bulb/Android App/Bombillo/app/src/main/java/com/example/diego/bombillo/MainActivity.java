package com.example.diego.bombillo;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
    TextView mensajes;
    EditText editTextAddress, editTextPort;
    Button buttonConnect;
    int prendido;
    String number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editTextAddress = (EditText)findViewById(R.id.address);
        editTextPort = (EditText)findViewById(R.id.puerto);
        buttonConnect = (Button)findViewById(R.id.connect);
        mensajes = (TextView)findViewById(R.id.mensajes);
        prendido = 0;

        buttonConnect.setOnClickListener(buttonConnectOnClickListener);
    }

    OnClickListener buttonConnectOnClickListener =
                new OnClickListener(){

                    @Override
                    public void onClick(View arg0){
                        /*mensajes.setText("Iniciando...");
                        Socket socket = null;
                        String response = "";
                        try{
                            mensajes.setText("Estableciendo Parametros...");
                            socket = new Socket(editTextAddress.getText().toString(), Integer.parseInt(editTextPort.getText().toString()));
                            DataOutputStream mensaje = new DataOutputStream(socket.getOutputStream());
                            number = Integer.toString( prendido%2 );
                            mensajes.setText("Enviando Mensaje...");
                            mensaje.writeBytes(number);
                            prendido++;
                        }catch( UnknownHostException e ){
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                            response = "UnknownHostException: " + e.toString();
                            mensajes.setText("error: "+response);
                        } catch( IOException e ) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                            response = "IOException: " + e.toString();
                            mensajes.setText("error: "+response);
                        }finally{
                            if( socket != null ){
                                try {
                                    socket.close();
                                }catch( IOException e ) {
                                    // TODO Auto-generated catch block
                                    e.printStackTrace();
                                    mensajes.setText("error: "+response);
                                }
                            }
                        }*/





                        MyClientTask myClientTask = new MyClientTask(
                                editTextAddress.getText().toString(),
                                Integer.parseInt(editTextPort.getText().toString())
                        );
                        myClientTask.execute();
                        mensajes.setText("Parametro Enviado "+ Integer.toString(prendido) +"...");
                    }
                };

    public class MyClientTask extends AsyncTask<Void, Void, Void>{
        String dstAddress;
        int dstPort;
        String response = "";
        TextView mensajesBack;


        MyClientTask(String addr, int port){
            dstAddress = addr;
            dstPort = port;
            mensajesBack = (TextView)findViewById(R.id.mensajes);

        }
        @Override
        protected Void doInBackground(Void... arg0) {
            Socket socket = null;
            try{
                socket = new Socket(dstAddress, dstPort);
                DataOutputStream mensaje = new DataOutputStream(socket.getOutputStream());
                number = Integer.toString( prendido%2 );
                mensaje.writeBytes(number);
                prendido++;
            }catch( UnknownHostException e ){
                // TODO Auto-generated catch block
                e.printStackTrace();
                response = "UnknownHostException: " + e.toString();
            } catch( IOException e ) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                response = "IOException: " + e.toString();
            }finally{
                if( socket != null ){
                    try {
                        socket.close();
                    }catch( IOException e ) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            mensajesBack.setText(response);
            super.onPostExecute(result);
        }
        @Override
        protected void onCancelled() {
            mensajesBack.setText("No se pudo finalizar");
        }


    }



}
