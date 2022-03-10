package com.example.einzelbeispiel_pettauer_silvio;

import android.os.AsyncTask;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class sendtoserver extends AsyncTask<String,Void,Void> {

        Socket s;
        ServerSocket ss;
        DataOutputStream dos;
        PrintWriter pw;

    protected Void doInBackground(String... voids) {

        String message = voids[0];

        try {

            ServerSocket ss = new ServerSocket(53212);
            s = ss.accept();
            pw = new PrintWriter(s.getOutputStream());
            pw.write(message);
            pw.flush();
            pw.close();
            s.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void execute(String toString) {
    }
}
