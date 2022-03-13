package com.example.einzelbeispiel_pettauer_silvio;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    EditText et;
    Button btn;
    Button send;
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et = (EditText) findViewById(R.id.Mtknr_eingeben);
        send = (Button) findViewById(R.id.sendbutton);
        btn = (Button) findViewById(R.id.berechne);
        tv = (TextView) findViewById(R.id.textView);

        send.setOnClickListener(this);
        btn.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        //if (internetconnection()) {
        String answer = "";
        switch (view.getId()) {
            case R.id.sendbutton:
                InputStream str = new ByteArrayInputStream(tv.getText().toString().getBytes(StandardCharsets.UTF_8));
                sendtoserver messageSender = new sendtoserver(str);
                messageSender.start();

                try {
                    messageSender.join(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                answer = messageSender.getAnswer();
                break;
            case R.id.berechne:
                answer = berechnung(tv.getText().toString());
                break;
        }
        tv.setText(answer);
        //Toast.makeText(this,answer,Toast.LENGTH_SHORT);


    }


    public String berechnung(String input) {
        {
            // Current indexes from left and right
            char[] arr = new char[input.length()];

            // Copy character by character into array
            for (int i = 0; i < input.length(); i++) {
                arr[i] = input.charAt(i);
            }
            int n = input.length();
            String answer = "";
            int l = 0, r = n - 1;

            // Count of odd numbers
            int k = 0;

            for (int i = 0; i < arr.length; i++) {

                while (l < r) {

                    // Find first even number from left side.
                    while (arr[l] % 2 != 0) {
                        l++;
                        k++;
                    }

                    // Find first odd number from right side.
                    while (arr[r] % 2 == 0 && l < r)
                        r--;

                    // Swap even number present on left and odd
                    // number right.
                    if (l < r) {

                        // swap arr[l] arr[r]
                        char temp = arr[l];
                        arr[l] = arr[r];
                        arr[r] = temp;
                    }
                }
            }
            char[] oddnumbers = new char[]{};
            for (int i = k; i < k; i++) {
                oddnumbers[i] = arr[i];
            }
            char[] evennumbers = new char[]{};
            for (int z = arr.length - k;z < arr.length;z++){
                evennumbers[z] = arr[z];
            }
            answer = ascendingorder(evennumbers.toString() + ascendingorder(oddnumbers.toString()));
            answer = answer.replaceAll("\\D+", "");
            return arr.toString();
        }
    }

    public String ascendingorder(String str) {
        char[] array = str.toCharArray();
        Arrays.sort(array);
        return Arrays.toString(array);
    }
}
