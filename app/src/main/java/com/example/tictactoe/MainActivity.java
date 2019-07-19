package com.example.tictactoe;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button[][] buttons = new Button[3][3];
    Button btnReset;
    TextView tvPlayer1;
    TextView tvPlayer2;

    int roundCount;
    int player1Points;
    int player2Points;

    boolean player1 = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialize();
        clickEvent();
    }

    private void initialize() {
        tvPlayer1 = (TextView) findViewById(R.id.tvPlayer1);
        tvPlayer2 = (TextView) findViewById(R.id.tvPlayer2);

        btnReset = (Button) findViewById(R.id.btnReset);

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                String btnID = "btn" + i + j;
                int resID = getResources().getIdentifier(btnID, "id", getPackageName());
                buttons[i][j] = findViewById(resID);
            }
        }

    }

    private void clickEvent() {

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                player1Points = 0;
                player2Points = 0;
                updatePoints();
                resetBoard();
            }
        });

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (!((Button) v).getText().toString().equals("")) {
                            return;
                        }

                        if (player1) {
                            ((Button) v).setText("X");
                        } else {
                            ((Button) v).setText("O");
                        }

                        roundCount++;

                        if (checkForWinner()) {
                            if (player1) {
                                player1Wins();
                            } else {
                                player2Wins();
                            }
                        } else if (roundCount == 9) {
                            draw();
                        } else {
                            player1 = !player1;
                        }

                    }
                });
            }
        }
    }

    private boolean checkForWinner() {
        String[][] field = new String[3][3];

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                field[i][j] = buttons[i][j].getText().toString();
            }
        }

        for (int i = 0; i < 3; i++) {
            if (field[i][0].equals(field[i][1]) && field[i][1].equals(field[i][2]) && !field[i][1].equals("")) {
                return true;
            }
        }

        for (int i = 0; i < 3; i++) {
            if (field[0][i].equals(field[1][i]) && field[1][i].equals(field[2][i]) && !field[1][i].equals("")) {
                return true;
            }
        }

        if (field[0][0].equals(field[1][1]) && field[1][1].equals(field[2][2]) && !field[1][1].equals("")) {
            return true;
        }

        if (field[0][2].equals(field[1][1]) && field[1][1].equals(field[2][0]) && !field[1][1].equals("")) {
            return true;
        }

        return false;
    }

    private void player1Wins() {
        player1Points++;
        Toast.makeText(this, "Player1 wins!", Toast.LENGTH_SHORT).show();
        updatePoints();
        resetBoard();
    }

    private void player2Wins() {
        player2Points++;
        Toast.makeText(this, "Player2 wins!", Toast.LENGTH_SHORT).show();
        updatePoints();
        resetBoard();
    }

    private void draw() {
        Toast.makeText(this, "Draw!", Toast.LENGTH_SHORT).show();
        resetBoard();
    }

    private void updatePoints() {
        tvPlayer1.setText("Player 1: " + player1Points);
        tvPlayer2.setText("Player 2: " + player2Points);
    }

    private void resetBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setText("");
            }
        }
        roundCount = 0;
        player1 = true;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt("roundCount", roundCount);
        outState.putInt("player1Points", player1Points);
        outState.putInt("player2Points", player2Points);
        outState.putBoolean("player1", player1);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        roundCount = savedInstanceState.getInt("roundCount");
        player1Points = savedInstanceState.getInt("player1Points");
        player2Points = savedInstanceState.getInt("player2Points");
        player1 = savedInstanceState.getBoolean("player1");
    }
}
