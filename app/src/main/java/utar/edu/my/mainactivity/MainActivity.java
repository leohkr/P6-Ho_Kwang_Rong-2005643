package utar.edu.my.mainactivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final long START_TIME_IN_MILLIS = 5000; // 5 seconds
    private long mTimeLeftinMs = START_TIME_IN_MILLIS;

    private int mSuccessfulTouches;
    private TextView mScore;
    private CountDownTimer mTimer;
    private TextView mTextcountdown;
    private Button startbutton;
    private Button endbutton;
    private boolean timerunning;
    private boolean gamestarted;
    private List<TextView> viewList ;
    private TextView currentHighlightedView;
    private Random random;
    private DBHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = new DBHelper(MainActivity.this);
        startbutton = findViewById(R.id.start_button);
        endbutton =findViewById(R.id.end_button);
        mTextcountdown = findViewById(R.id.timer_text);
        mScore = findViewById(R.id.scoreTextView);
        // Initialize the views and add them to the list
        TextView view1 = findViewById(R.id.view1);
        TextView view2 = findViewById(R.id.view2);
        TextView view3 = findViewById(R.id.view3);
        TextView view4 = findViewById(R.id.view4);
        viewList = new ArrayList<>();
        viewList.add(view1);
        viewList.add(view2);
        viewList.add(view3);
        viewList.add(view4);

        //start button action
        startbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //if game started, highlight random view and start timer
                if (!gamestarted) {
                    gamestarted = true;
                    startGame();
                    random = new Random();
                    currentHighlightedView = viewList.get(random.nextInt(viewList.size()));
                    currentHighlightedView.setBackgroundResource(R.drawable.highlighted_background);


                } else if (timerunning) {
                    endGame();
                }
            }
        });

        //end button action
        endbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                endGame();
                Toast.makeText(MainActivity.this,"Game Ended !",Toast.LENGTH_SHORT).show();
                for (TextView view : viewList) {
                    view.setBackgroundResource(R.drawable.unhighlighted_background);
                }
            }
        });

        updateCountdownText();

        // disable click for the views
        for (TextView view : viewList) {
            view.setEnabled(false);
        }

    }

    //start timer and enable click views
    private void startGame() {
        startTimer();
        startbutton.setVisibility(View.INVISIBLE);
        endbutton.setVisibility(View.VISIBLE);

        for (TextView view : viewList) {
            view.setEnabled(true);
            view.setOnClickListener(this);

        }
        gamestarted = true;

    }

    //stop timer and disable click views
    private void endGame() {
        mTimer.cancel();
        timerunning = false;
        startbutton.setVisibility(View.INVISIBLE);
        endbutton.setVisibility(View.INVISIBLE);
        resetTimer();
        gamestarted = false;
        for (TextView view : viewList) {
            view.setEnabled(false);
        }
    }

    //stop timer and reset score
    private void resetTimer(){
        mTimeLeftinMs = START_TIME_IN_MILLIS;
        updateCountdownText();
        endbutton.setVisibility(View.VISIBLE);
        startbutton.setVisibility(View.VISIBLE);
        mSuccessfulTouches =0 ;

    }


    private void startTimer() {

        // Start the timer
        mTimer = new CountDownTimer(mTimeLeftinMs, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                // Update the timer display
                mTimeLeftinMs = millisUntilFinished;
                updateCountdownText();

            }

            //end of timer
            @Override
            public void onFinish() {
                timerunning=false;
                startbutton.setText("Start");

                endbutton.setVisibility(View.VISIBLE);

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Your Score is : "+ mSuccessfulTouches);
                builder.setMessage("Do you want to proceed to the next level?");
                builder.setCancelable(false);
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Intent intent = new Intent (MainActivity.this, level2.class);
                        intent.putExtra("score",mSuccessfulTouches);
                        startActivity(intent);

                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        AlertDialog.Builder builder1 = new AlertDialog.Builder(MainActivity.this);
                        builder1.setTitle("Congratulations! You're in the top 25!");
                        builder1.setMessage("Enter your name to save your score:");
                        final EditText input = new EditText(MainActivity.this);
                        builder1.setView(input);
                        builder1.setCancelable(true);
                        builder1.setPositiveButton("Save", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String name =input.getText().toString();
                                int score = mSuccessfulTouches;
                                boolean isInserted = db.insertuserdata(name, score);
                                if (isInserted) {
                                    Toast.makeText(MainActivity.this, "Data saved successfully", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(MainActivity.this, "Error: Data not saved", Toast.LENGTH_SHORT).show();
                                }

                                Intent intent1 = new Intent (MainActivity.this, MainActivity2.class);
                                startActivity(intent1);

                            }
                        });builder1.show();
                    }

                });
                builder.show();



            }
        }.start();
        timerunning= true;



    }
    //show timer
    private void updateCountdownText(){
        int min = (int)(mTimeLeftinMs/1000) /60;
        int sec = (int)(mTimeLeftinMs/1000) %60;

        String timeleftformat = String.format(Locale.getDefault(),"%02d:%02d",min,sec);
        mTextcountdown.setText(timeleftformat);

    }

    //after clicking the highlighted views
    @Override
    public void onClick(View view) {
        // Check if the clicked view is the current highlighted view

        if (view == currentHighlightedView) {
            // Remove the highlight from the current view
            currentHighlightedView.setBackgroundResource(R.drawable.unhighlighted_background);

            // Randomly highlight a new view
            List<TextView> unhighlightedViews = new ArrayList<>(viewList);
            unhighlightedViews.remove(currentHighlightedView);
            currentHighlightedView = unhighlightedViews.get(random.nextInt(unhighlightedViews.size()));
            currentHighlightedView.setBackgroundResource(R.drawable.highlighted_background);
            //increase score when clicked highlighted view
            mSuccessfulTouches++;
            mScore.setText("Score: " + mSuccessfulTouches);
        }
    }


}
