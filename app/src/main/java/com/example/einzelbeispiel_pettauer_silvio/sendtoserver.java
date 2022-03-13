package com.example.einzelbeispiel_pettauer_silvio;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class sendtoserver extends Thread {

    String answer;
    InputStream str;

    public sendtoserver(InputStream str) {
        this.str = str;
    }

    @Override
    public void run() {
        try {
            answer = getanswer(str);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getanswer(InputStream stream) throws Exception{
        String sentence;
        String modifiedsentence;

        BufferedReader brd = new BufferedReader(new InputStreamReader(stream));

        Socket clientSocket = new Socket("se2-isys.aau.at", 53212);
        DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
        BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

        sentence = brd.readLine();

        outToServer.writeBytes((sentence + '\n'));

        modifiedsentence = inFromServer.readLine();

        clientSocket.close();
        return modifiedsentence;


    }

    public String getAnswer() {
        return answer;
    }
}
