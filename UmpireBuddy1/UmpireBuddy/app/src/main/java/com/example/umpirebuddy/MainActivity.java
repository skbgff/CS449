package com.example.umpirebuddy;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    Button strike;
    Button reset;
    Button logout;
    Button ball;

    TextView ballText;
    TextView strikeText;

    int strikeNum = 0;
    int ballNum = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ball = findViewById(R.id.ballButton);
        strike = findViewById(R.id.strikeButton);
        reset = findViewById(R.id.resetButton);
        logout = findViewById(R.id.logoutButton);

        ballText = findViewById(R.id.ballText);
        ballText.setText("Ball: "+ ballNum);
        strikeText = findViewById(R.id.strikeText);
        strikeText.setText("Strike: "+ strikeNum);

        strike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                strikeNum += 1;
                if (strikeNum > 3){
                    strikeNum = 0;
                    ballNum = 0;
                }
                strikeText.setText("Strike: " + strikeNum);
                ballText.setText("Ball: "+ ballNum);
            }
        });

        ball.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                ballNum +=1;
                if (ballNum > 3){
                    ballNum = 0;
                    strikeNum = 0;
                }
                strikeText.setText("Strike: " + strikeNum);
                ballText.setText("Ball: "+ ballNum);
            }
        });

        reset.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                strikeNum = 0;
                ballNum = 0;
                strikeText.setText("Strike: "+ strikeNum);
                ballText.setText("Ball: "+ ballNum);
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                System.exit(0);
            }
        });

    }
}
