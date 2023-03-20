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

public class level4 extends AppCompatActivity implements View.OnClickListener {
    private static final long START_TIME_IN_MILLIS4 = 5000; // 5 seconds
    private long mTimeLeftinMs4 = START_TIME_IN_MILLIS4;
    private int mSuccessfulTouches;
    private TextView mScore4;
    private CountDownTimer mTimer4;
    private TextView mTextcountdown4;
    private Button startbutton4;
    private Button endbutton4;
    private boolean timerunning4;
    private boolean gamestarted;
    private List<TextView> viewList4 ;
    private TextView currentHighlightedView4;
    private Random random4;
    private DBHelper db4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intentlv3=getIntent();
        db4 = new DBHelper(level4.this);

        mSuccessfulTouches=intentlv3.getIntExtra("score",mSuccessfulTouches);
        setContentView(R.layout.activity_level4);
        startbutton4 = findViewById(R.id.start_button4);
        endbutton4 =findViewById(R.id.end_button4);
        mTextcountdown4 = findViewById(R.id.timer_text4);
        mScore4 = findViewById(R.id.scoreTextView4);
        // Initialize the views and add them to the list
        TextView view30 = findViewById(R.id.view30);
        TextView view31 = findViewById(R.id.view31);
        TextView view32 = findViewById(R.id.view32);
        TextView view33 = findViewById(R.id.view33);
        TextView view34 = findViewById(R.id.view34);
        TextView view35 = findViewById(R.id.view35);
        TextView view36 = findViewById(R.id.view36);
        TextView view37 = findViewById(R.id.view37);
        TextView view38 = findViewById(R.id.view38);
        TextView view39 = findViewById(R.id.view39);
        TextView view40 = findViewById(R.id.view40);
        TextView view41 = findViewById(R.id.view41);
        TextView view42 = findViewById(R.id.view42);
        TextView view43 = findViewById(R.id.view43);
        TextView view44 = findViewById(R.id.view44);
        TextView view45 = findViewById(R.id.view45);
        TextView view46 = findViewById(R.id.view46);
        TextView view47 = findViewById(R.id.view47);
        TextView view48 = findViewById(R.id.view48);
        TextView view49 = findViewById(R.id.view49);
        TextView view50 = findViewById(R.id.view50);
        TextView view51 = findViewById(R.id.view51);
        TextView view52 = findViewById(R.id.view52);
        TextView view53 = findViewById(R.id.view53);
        TextView view54 = findViewById(R.id.view54);
        viewList4 = new ArrayList<>();
        viewList4.add(view30);
        viewList4.add(view31);
        viewList4.add(view32);
        viewList4.add(view33);
        viewList4.add(view34);
        viewList4.add(view35);
        viewList4.add(view36);
        viewList4.add(view37);
        viewList4.add(view38);
        viewList4.add(view39);
        viewList4.add(view40);
        viewList4.add(view41);
        viewList4.add(view42);
        viewList4.add(view43);
        viewList4.add(view44);
        viewList4.add(view45);
        viewList4.add(view46);
        viewList4.add(view47);
        viewList4.add(view48);
        viewList4.add(view49);
        viewList4.add(view50);
        viewList4.add(view51);
        viewList4.add(view52);
        viewList4.add(view53);
        viewList4.add(view54);


        startbutton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //if game started, highlight random view and start timer
                if (!gamestarted) {
                    gamestarted = true;
                    startGame4();
                    random4 = new Random();
                    currentHighlightedView4 = viewList4.get(random4.nextInt(viewList4.size()));
                    currentHighlightedView4.setBackgroundResource(R.drawable.highlighted_background);


                } else if (timerunning4) {
                    endGame4();

                }



            }
        });
        //end button action
        endbutton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                endGame4();
                Toast.makeText(level4.this,"Game Ended !",Toast.LENGTH_SHORT).show();
                for (TextView view : viewList4) {
                    view.setBackgroundResource(R.drawable.unhighlighted_background);

                }
                Intent intentend4 = new Intent (level4.this, MainActivity.class);
                startActivity(intentend4);
            }
        });
        updateCountdownText4();

        // disable click for the views
        for (TextView view : viewList4) {
            view.setEnabled(false);
        }

    }
    //start timer and enable click views
    private void startGame4() {
        startTimer4();
        startbutton4.setVisibility(View.INVISIBLE);
        endbutton4.setVisibility(View.VISIBLE);

        for (TextView view : viewList4) {
            view.setEnabled(true);
            view.setOnClickListener(this);

        }
        gamestarted = true;

    }
    //stop timer and disable click views
    private void endGame4() {
        mTimer4.cancel();
        timerunning4 = false;
        startbutton4.setVisibility(View.INVISIBLE);
        endbutton4.setVisibility(View.INVISIBLE);
        resetTimer4();
        gamestarted = false;
        for (TextView view : viewList4) {
            view.setEnabled(false);
        }
    }

    //stop timer and reset score
    private void resetTimer4(){
        mTimeLeftinMs4 = START_TIME_IN_MILLIS4;
        updateCountdownText4();
        endbutton4.setVisibility(View.VISIBLE);
        startbutton4.setVisibility(View.VISIBLE);
        mSuccessfulTouches =0 ;

    }
    private void startTimer4() {

        // Start the timer
        mTimer4 = new CountDownTimer(mTimeLeftinMs4, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                // Update the timer display
                mTimeLeftinMs4 = millisUntilFinished;
                updateCountdownText4();

            }

            //end of timer
            @Override
            public void onFinish() {
                timerunning4 = false;
                startbutton4.setText("Start");
                endbutton4.setVisibility(View.VISIBLE);

                AlertDialog.Builder builder = new AlertDialog.Builder(level4.this);
                builder.setTitle("Your Score is : " + mSuccessfulTouches);
                builder.setMessage("Do you want to proceed to the next level?");
                builder.setCancelable(false);
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Intent intent = new Intent(level4.this, level5.class);
                        intent.putExtra("score", mSuccessfulTouches);
                        startActivity(intent);

                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        AlertDialog.Builder builder4 = new AlertDialog.Builder(level4.this);
                        builder4.setTitle("Congratulations! You're in the top 25!");
                        builder4.setMessage("Enter your name to save your score:");
                        final EditText input4 = new EditText(level4.this);
                        builder4.setView(input4);
                        builder4.setCancelable(true);
                        builder4.setPositiveButton("Save", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String name4 = input4.getText().toString();
                                int score4 = mSuccessfulTouches;
                                boolean isInserted3 = db4.insertuserdata(name4, score4);
                                if (isInserted3) {
                                    Toast.makeText(level4.this, "Data saved successfully", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(level4.this, "Error: Data not saved", Toast.LENGTH_SHORT).show();
                                }

                                Intent intent4 = new Intent(level4.this, MainActivity2.class);
                                startActivity(intent4);

                            }
                        });
                        builder4.show();
                    }

                });
                builder.show();

            }
        }.start();
        timerunning4= true;
    }

    //show timer
    private void updateCountdownText4(){
        int min = (int)(mTimeLeftinMs4/1000) /60;
        int sec = (int)(mTimeLeftinMs4/1000) %60;

        String timeleftformat = String.format(Locale.getDefault(),"%02d:%02d",min,sec);
        mTextcountdown4.setText(timeleftformat);

    }
    @Override
    public void onClick(View v) {
        // Check if the clicked view is the current highlighted view

        if (v == currentHighlightedView4) {
            // Remove the highlight from the current view
            currentHighlightedView4.setBackgroundResource(R.drawable.unhighlighted_background);

            // Randomly highlight a new view
            List<TextView> unhighlightedViews = new ArrayList<>(viewList4);
            unhighlightedViews.remove(currentHighlightedView4);
            currentHighlightedView4 = unhighlightedViews.get(random4.nextInt(unhighlightedViews.size()));
            currentHighlightedView4.setBackgroundResource(R.drawable.highlighted_background);
            //increase score when clicked highlighted view
            mSuccessfulTouches++;
            mScore4.setText("Score: " + mSuccessfulTouches);
        }

    }
}