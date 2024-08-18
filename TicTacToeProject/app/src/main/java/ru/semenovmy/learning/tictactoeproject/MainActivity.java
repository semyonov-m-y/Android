package ru.semenovmy.learning.tictactoeproject;


import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String GAME_STATE = "gameState";
    private static final String CROSS_WINS = "crossWinsCount";
    private static final String CIRCLE_WINS = "circleWinsCount";

    private int crossWinsCount = 0;
    private int circleWinsCount = 0;
    private boolean isCrossMove = true;
    private TextView crossWins;
    private TextView circleWins;
    private TicTacToeField ticTacToeField;
    private TextView winner;
    private ArrayList<View> buttonsList;

    private void createTwoButtonsAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setMessage("Choose next action");
        builder.setNegativeButton("Quit",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,
                                        int which) {
                        finish();
                    }
                });
        builder.setNeutralButton("Replay",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,
                                        int which) {
                        restartGame();
                    }
                });
        builder.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        crossWins = findViewById(R.id.crosses_count);
        circleWins = findViewById(R.id.circles_count);
        winner = findViewById(R.id.winner_figure);

        restartGame();
        changeScore();

        if (savedInstanceState != null) {
            ticTacToeField = savedInstanceState.getParcelable(GAME_STATE);
            crossWinsCount = savedInstanceState.getInt(CROSS_WINS);
            circleWinsCount = savedInstanceState.getInt(CIRCLE_WINS);

            crossWins.setText(String.valueOf(crossWinsCount));
            circleWins.setText(String.valueOf(circleWinsCount));
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(GAME_STATE, ticTacToeField);
        outState.putInt(CROSS_WINS, crossWinsCount);
        outState.putInt(CIRCLE_WINS, circleWinsCount);
    }

    private void restartGame() {
        initGrid();
        ticTacToeField = new TicTacToeField(3);
    }

    private void initGrid() {
        buttonsList = new ArrayList<>();

        View button1 = findViewById(R.id.button1);
        View button2 = findViewById(R.id.button2);
        View button3 = findViewById(R.id.button3);
        View button4 = findViewById(R.id.button4);
        View button5 = findViewById(R.id.button5);
        View button6 = findViewById(R.id.button6);
        View button7 = findViewById(R.id.button7);
        View button8 = findViewById(R.id.button8);
        View button9 = findViewById(R.id.button9);

        buttonsList.add(button1);
        buttonsList.add(button2);
        buttonsList.add(button3);
        buttonsList.add(button4);
        buttonsList.add(button5);
        buttonsList.add(button6);
        buttonsList.add(button7);
        buttonsList.add(button8);
        buttonsList.add(button9);

        for (View view : buttonsList) {
            view.setOnClickListener(clickListener);
            view.setBackgroundColor(getColor(R.color.grey));
            view.setClickable(true);
        }
    }


    private void changeScore() {
        crossWins.setText(String.valueOf(crossWinsCount));
        circleWins.setText(String.valueOf(circleWinsCount));
    }

    View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String coordinates = (String) v.getTag();
            int row = Integer.parseInt(String.valueOf(coordinates.charAt(0)));
            int column = Integer.parseInt(String.valueOf(coordinates.charAt(1)));

            if (isCrossMove) {
                v.setBackground(getDrawable(R.drawable.cross));
                ticTacToeField.setFigure(row, column, TicTacToeField.Figure.CROSS);
            } else {
                v.setBackground(getDrawable(R.drawable.circle));
                ticTacToeField.setFigure(row, column, TicTacToeField.Figure.CIRCLE);

            }
            isCrossMove = !isCrossMove;
            v.setClickable(false);

            buttonsList.remove(v);

            if (ticTacToeField.getWinner() == TicTacToeField.Figure.CROSS) {
                gameRoundEnd(TicTacToeField.Figure.CROSS);
            }

            if (ticTacToeField.getWinner() == TicTacToeField.Figure.CIRCLE) {
                gameRoundEnd(TicTacToeField.Figure.CIRCLE);
            }

            if (ticTacToeField.isFull() && ticTacToeField.getWinner() == TicTacToeField.Figure.NONE) {
                gameRoundEnd(TicTacToeField.Figure.NONE);
            }
        }
    };

    private void gameRoundEnd(TicTacToeField.Figure figure) {
        switch (figure) {
            case CROSS:
                crossWinsCount++;
                break;
            case CIRCLE:
                circleWinsCount++;
        }

        winner.setText(String.format(getResources().getString(R.string.formatter_winner), figure.name(), getResources().getString(R.string.wins)));

        for (View view : buttonsList) {
            view.setClickable(false);
            view.setBackground(null);
        }

        createTwoButtonsAlertDialog();

        buttonsList = null;
        winner.setVisibility(View.VISIBLE);

        changeScore();
    }
}


