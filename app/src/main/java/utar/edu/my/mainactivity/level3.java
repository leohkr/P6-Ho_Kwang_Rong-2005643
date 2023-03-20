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

public class level3 extends AppCompatActivity implements View.OnClickListener {
    private static final long START_TIME_IN_MILLIS3 = 5000; // 5 seconds
    private long mTimeLeftinMs3 = START_TIME_IN_MILLIS3;
    private int mSuccessfulTouches;
    private TextView mScore3;
    private CountDownTimer mTimer3;
    private TextView mTextcountdown3;
    private Button startbutton3;
    private Button endbutton3;
    private boolean timerunning3;
    private boolean gamestarted;
    private List<TextView> viewList3 ;
    private TextView currentHighlightedView3;
    private Random random3;
    private DBHelper db3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intentlv3=getIntent();
        db3 = new DBHelper(level3.this);

        mSuccessfulTouches=intentlv3.getIntExtra("score",mSuccessfulTouches);
        setContentView(R.layout.activity_level3);
        startbutton3 = findViewById(R.id.start_button3);
        endbutton3 =findViewById(R.id.end_button3);
        mTextcountdown3 = findViewById(R.id.timer_text3);
        mScore3 = findViewById(R.id.scoreTextView3);
        // Initialize the views and add them to the list
        TextView view14 = findViewById(R.id.view14);
        TextView view15 = findViewById(R.id.view15);
        TextView view16 = findViewById(R.id.view16);
        TextView view17 = findViewById(R.id.view17);
        TextView view18 = findViewById(R.id.view18);
        TextView view19 = findViewById(R.id.view19);
        TextView view20 = findViewById(R.id.view20);
        TextView view21 = findViewById(R.id.view21);
        TextView view22 = findViewById(R.id.view22);
        TextView view23 = findViewById(R.id.view23);
        TextView view24 = findViewById(R.id.view24);
        TextView view25 = findViewById(R.id.view25);
        TextView view26 = findViewById(R.id.view26);
        TextView view27 = findViewById(R.id.view27);
        TextView view28 = findViewById(R.id.view28);
        TextView view29 = findViewById(R.id.view29);
        viewList3 = new ArrayList<>();
        viewList3.add(view14);
        viewList3.add(view15);
        viewList3.add(view16);
        viewList3.add(view17);
        viewList3.add(view18);
        viewList3.add(view19);
        viewList3.add(view20);
        viewList3.add(view21);
        viewList3.add(view22);
        viewList3.add(view23);
        viewList3.add(view24);
        viewList3.add(view25);
        viewList3.add(view26);
        viewList3.add(view27);
        viewList3.add(view28);
        viewList3.add(view29);


        startbutton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //if game started, highlight random view and start timer
                if (!gamestarted) {
                    gamestarted = true;
                    startGame3();
                    random3 = new Random();
                    currentHighlightedView3 = viewList3.get(random3.nextInt(viewList3.size()));
                    currentHighlightedView3.setBackgroundResource(R.drawable.highlighted_background);


                } else if (timerunning3) {
                    endGame3();

                }



            }
        });
        //end button action
        endbutton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                endGame3();
                Toast.makeText(level3.this,"Game Ended !",Toast.LENGTH_SHORT).show();
                for (TextView view : viewList3) {
                    view.setBackgroundResource(R.drawable.unhighlighted_background);
                }
                Intent intentend3 = new Intent (level3.this, MainActivity.class);
                startActivity(intentend3);
            }
        });
        updateCountdownText3();

        // disable click for the views
        for (TextView view : viewList3) {
            view.setEnabled(false);
        }

    }
    //start timer and enable click views
    private void startGame3() {
        startTimer3();
        startbutton3.setVisibility(View.INVISIBLE);
        endbutton3.setVisibility(View.VISIBLE);

        for (TextView view : viewList3) {
            view.setEnabled(true);
            view.setOnClickListener(this);

        }
        gamestarted = true;

    }
    //stop timer and disable click views
    private void endGame3() {
        mTimer3.cancel();
        timerunning3 = false;
        startbutton3.setVisibility(View.INVISIBLE);
        endbutton3.setVisibility(View.INVISIBLE);
        resetTimer3();
        gamestarted = false;
        for (TextView view : viewList3) {
            view.setEnabled(false);
        }
    }

    //stop timer and reset score
    private void resetTimer3(){
        mTimeLeftinMs3 = START_TIME_IN_MILLIS3;
        updateCountdownText3();
        endbutton3.setVisibility(View.VISIBLE);
        startbutton3.setVisibility(View.VISIBLE);
        mSuccessfulTouches =0 ;

    }
    private void startTimer3() {

        // Start the timer
        mTimer3 = new CountDownTimer(mTimeLeftinMs3, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                // Update the timer display
                mTimeLeftinMs3 = millisUntilFinished;
                updateCountdownText3();

            }

            //end of timer
            @Override
            public void onFinish() {
                timerunning3=false;
                startbutton3.setText("Start");

                endbutton3.setVisibility(View.VISIBLE);

                AlertDialog.Builder builder = new AlertDialog.Builder(level3.this);
                builder.setTitle("Your Score is : "+ mSuccessfulTouches);
                builder.setMessage("Do you want to proceed to the next level?");
                builder.setCancelable(false);
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Intent intent = new Intent (level3.this, level4.class);
                        intent.putExtra("score",mSuccessfulTouches);
                        startActivity(intent);

                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        AlertDialog.Builder builder3 = new AlertDialog.Builder(level3.this);
                        builder3.setTitle("Congratulations! You're in the top 25!");
                        builder3.setMessage("Enter your name to save your score:");
                        final EditText input3 = new EditText(level3.this);
                        builder3.setView(input3);
                        builder3.setCancelable(true);
                        builder3.setPositiveButton("Save", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String name3 =input3.getText().toString();
                                int score3 = mSuccessfulTouches;
                                boolean isInserted3 = db3.insertuserdata(name3, score3);
                                if (isInserted3) {
                                    Toast.makeText(level3.this, "Data saved successfully", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(level3.this, "Error: Data not saved", Toast.LENGTH_SHORT).show();
                                }

                                Intent intent3 = new Intent (level3.this, MainActivity2.class);
                                startActivity(intent3);

                            }
                        });builder3.show();


                    }

                });
                builder.show();



            }
        }.start();
        timerunning3= true;
    }

    //show timer
    private void updateCountdownText3(){
        int min = (int)(mTimeLeftinMs3/1000) /60;
        int sec = (int)(mTimeLeftinMs3/1000) %60;

        String timeleftformat = String.format(Locale.getDefault(),"%02d:%02d",min,sec);
        mTextcountdown3.setText(timeleftformat);

    }
    @Override
    public void onClick(View v) {
        // Check if the clicked view is the current highlighted view

        if (v == currentHighlightedView3) {
            // Remove the highlight from the current view
            currentHighlightedView3.setBackgroundResource(R.drawable.unhighlighted_background);

            // Randomly highlight a new view
            List<TextView> unhighlightedViews = new ArrayList<>(viewList3);
            unhighlightedViews.remove(currentHighlightedView3);
            currentHighlightedView3 = unhighlightedViews.get(random3.nextInt(unhighlightedViews.size()));
            currentHighlightedView3.setBackgroundResource(R.drawable.highlighted_background);
            //increase score when clicked highlighted view
            mSuccessfulTouches++;
            mScore3.setText("Score: " + mSuccessfulTouches);
        }

    }
}