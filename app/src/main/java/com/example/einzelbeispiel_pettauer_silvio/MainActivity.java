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
import java.util.Collections;

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

                answer = messageSender.getanswer();
                break;
            case R.id.berechne:
                answer = berechnung(tv.getText().toString());
        }
        tv.setText(answer);


        /*} else {
        Toast.makeText(getApplicationContext(), "No Internet Connection!", Toast.LENGTH_SHORT).show();
        }*/

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

                // Sort odd number in descending order
                Arrays.sort(arr, 0, k, Collections.reverseOrder());

                // Sort even number in ascending order
                Arrays.sort(arr, k, n);
            }
            return arr.toString();
        }
    }

        /*public boolean internetconnection() {
            ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            return networkInfo != null && networkInfo.isConnectedOrConnecting();
        }*/

        /*@Override
        public void onPointerCaptureChanged ( boolean hasCapture){
            super.onPointerCaptureChanged(hasCapture);
        }*/
}
