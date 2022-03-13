package com.example.einzelbeispiel_pettauer_silvio;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class sendtoserver extends Thread {

        Socket s;
        DataOutputStream dos;
        PrintWriter pw;
        BufferedReader brd;
        BufferedReader brdanswer;
        InputStream str;
        String answer;

    public sendtoserver(InputStream str) {
        this.str = str;
    }

    protected String sendmsg(InputStream msg) {

        InputStream message = msg;

        try {

            s = new Socket("se2-isys.aau.at",53212);
            brd = new BufferedReader(new InputStreamReader(message));
            brdanswer = new BufferedReader(new InputStreamReader(s.getInputStream()));
            dos =  new DataOutputStream(s.getOutputStream());
            pw = new PrintWriter(s.getOutputStream());

            String servermsg = brd.readLine();
            dos.writeBytes(servermsg);
            String answer = brdanswer.readLine();
            s.close();
            return answer;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void run() {
        try {
            answer = sendmsg(str);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getanswer() {
        return answer;
    }
}
