package utar.edu.my.mainactivity;

import androidx.appcompat.app.AppCompatActivity;

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

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class level2 extends AppCompatActivity implements View.OnClickListener {
    private static final long START_TIME_IN_MILLIS2 = 5000; // 5 seconds
    private long mTimeLeftinMs2 = START_TIME_IN_MILLIS2;
    private int mSuccessfulTouches;
    private TextView mScore2;
    private CountDownTimer mTimer2;
    private TextView mTextcountdown2;
    private Button startbutton2;
    private Button endbutton2;
    private boolean timerunning2;
    private boolean gamestarted;
    private DBHelper db2;
    private List<TextView> viewList2 ;
    private TextView currentHighlightedView2;
    private Random random2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intentlv2=getIntent();
        db2 = new DBHelper(level2.this);
        mSuccessfulTouches=intentlv2.getIntExtra("score",mSuccessfulTouches);
        setContentView(R.layout.activity_level2);
        startbutton2 = findViewById(R.id.start_button2);
        endbutton2 =findViewById(R.id.end_button2);
        mTextcountdown2 = findViewById(R.id.timer_text2);
        mScore2 = findViewById(R.id.scoreTextView2);
        // Initialize the views and add them to the list
        TextView view5 = findViewById(R.id.view5);
        TextView view6 = findViewById(R.id.view6);
        TextView view7 = findViewById(R.id.view7);
        TextView view8 = findViewById(R.id.view8);
        TextView view9 = findViewById(R.id.view9);
        TextView view10 = findViewById(R.id.view10);
        TextView view11 = findViewById(R.id.view11);
        TextView view12 = findViewById(R.id.view12);
        TextView view13 = findViewById(R.id.view13);
        viewList2 = new ArrayList<>();
        viewList2.add(view5);
        viewList2.add(view6);
        viewList2.add(view7);
        viewList2.add(view8);
        viewList2.add(view9);
        viewList2.add(view10);
        viewList2.add(view11);
        viewList2.add(view12);
        viewList2.add(view13);

        startbutton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //if game started, highlight random view and start timer
                if (!gamestarted) {
                    gamestarted = true;
                    startGame2();
                    random2 = new Random();
                    currentHighlightedView2 = viewList2.get(random2.nextInt(viewList2.size()));
                    currentHighlightedView2.setBackgroundResource(R.drawable.highlighted_background);


                } else if (timerunning2) {
                    endGame2();

                }



            }
        });
        //end button action
        endbutton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                endGame2();
                Toast.makeText(level2.this,"Game Ended !",Toast.LENGTH_SHORT).show();
                for (TextView view : viewList2) {
                    view.setBackgroundResource(R.drawable.unhighlighted_background);
                }
                Intent intentend2 = new Intent (level2.this, MainActivity.class);
                startActivity(intentend2);
            }
        });
        updateCountdownText2();

        // disable click for the views
        for (TextView view : viewList2) {
            view.setEnabled(false);
        }

    }
    //start timer and enable click views
    private void startGame2() {
        startTimer2();
        startbutton2.setVisibility(View.INVISIBLE);
        endbutton2.setVisibility(View.VISIBLE);

        for (TextView view : viewList2) {
            view.setEnabled(true);
            view.setOnClickListener(this);

        }
        gamestarted = true;

    }
    //stop timer and disable click views
    private void endGame2() {
        mTimer2.cancel();
        timerunning2 = false;
        startbutton2.setVisibility(View.INVISIBLE);
        endbutton2.setVisibility(View.INVISIBLE);
        resetTimer2();
        gamestarted = false;
        for (TextView view : viewList2) {
            view.setEnabled(false);
        }

    }

    //stop timer and reset score
    private void resetTimer2(){
        mTimeLeftinMs2 = START_TIME_IN_MILLIS2;
        updateCountdownText2();
        endbutton2.setVisibility(View.VISIBLE);
        startbutton2.setVisibility(View.VISIBLE);
        mSuccessfulTouches =0 ;

    }
    private void startTimer2() {

        // Start the timer
        mTimer2 = new CountDownTimer(mTimeLeftinMs2, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                // Update the timer display
                mTimeLeftinMs2 = millisUntilFinished;
                updateCountdownText2();

            }

            //end of timer
            @Override
            public void onFinish() {
                timerunning2=false;
                startbutton2.setText("Start");

                endbutton2.setVisibility(View.VISIBLE);

                AlertDialog.Builder builder = new AlertDialog.Builder(level2.this);
                builder.setTitle("Your Score is : "+ mSuccessfulTouches);
                builder.setMessage("Do you want to proceed to the next level?");
                builder.setCancelable(false);
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Intent intent = new Intent (level2.this, level3.class);
                        intent.putExtra("score",mSuccessfulTouches);
                        startActivity(intent);

                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        AlertDialog.Builder builder2 = new AlertDialog.Builder(level2.this);
                        builder2.setTitle("Congratulations! You're in the top 25!");
                        builder2.setMessage("Enter your name to save your score:");
                        final EditText input2 = new EditText(level2.this);
                        builder2.setView(input2);
                        builder2.setCancelable(true);
                        builder2.setPositiveButton("Save", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String name2 =input2.getText().toString();
                                int score2 = mSuccessfulTouches;
                                boolean isInserted2 = db2.insertuserdata(name2, score2);
                                if (isInserted2) {
                                    Toast.makeText(level2.this, "Data saved successfully", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(level2.this, "Error: Data not saved", Toast.LENGTH_SHORT).show();
                                }

                                Intent intent2 = new Intent (level2.this, MainActivity2.class);
                                startActivity(intent2);

                            }
                        });builder2.show();

                    }

                });
                builder.show();



            }
        }.start();
        timerunning2= true;
    }

    //show timer
    private void updateCountdownText2(){
        int min = (int)(mTimeLeftinMs2/1000) /60;
        int sec = (int)(mTimeLeftinMs2/1000) %60;

        String timeleftformat = String.format(Locale.getDefault(),"%02d:%02d",min,sec);
        mTextcountdown2.setText(timeleftformat);

    }
    @Override
    public void onClick(View v) {
        // Check if the clicked view is the current highlighted view

        if (v == currentHighlightedView2) {
            // Remove the highlight from the current view
            currentHighlightedView2.setBackgroundResource(R.drawable.unhighlighted_background);

            // Randomly highlight a new view
            List<TextView> unhighlightedViews = new ArrayList<>(viewList2);
            unhighlightedViews.remove(currentHighlightedView2);
            currentHighlightedView2 = unhighlightedViews.get(random2.nextInt(unhighlightedViews.size()));
            currentHighlightedView2.setBackgroundResource(R.drawable.highlighted_background);
            //increase score when clicked highlighted view
            mSuccessfulTouches++;
            mScore2.setText("Score: " + mSuccessfulTouches);
        }

    }
}