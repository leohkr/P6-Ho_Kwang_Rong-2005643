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

public class level5 extends AppCompatActivity implements View.OnClickListener {
    private static final long START_TIME_IN_MILLIS5 = 5000; // 5 seconds
    private long mTimeLeftinMs5 = START_TIME_IN_MILLIS5;
    private int mSuccessfulTouches;
    private TextView mScore5;
    private CountDownTimer mTimer5;
    private TextView mTextcountdown5;
    private Button startbutton5;
    private Button endbutton5;
    private boolean timerunning5;
    private boolean gamestarted;
    private DBHelper db5;
    private List<TextView> viewList5 ;
    private TextView currentHighlightedView5;
    private Random random5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intentlv5=getIntent();
        db5 = new DBHelper(level5.this);
        mSuccessfulTouches=intentlv5.getIntExtra("score",mSuccessfulTouches);
        setContentView(R.layout.activity_level5);
        startbutton5 = findViewById(R.id.start_button5);
        endbutton5 =findViewById(R.id.end_button5);
        mTextcountdown5 = findViewById(R.id.timer_text5);
        mScore5 = findViewById(R.id.scoreTextView5);
        // Initialize the views and add them to the list
        TextView view55 = findViewById(R.id.view55);
        TextView view56 = findViewById(R.id.view56);
        TextView view57 = findViewById(R.id.view57);
        TextView view58 = findViewById(R.id.view58);
        TextView view59 = findViewById(R.id.view59);
        TextView view60 = findViewById(R.id.view60);
        TextView view61 = findViewById(R.id.view61);
        TextView view62 = findViewById(R.id.view62);
        TextView view63 = findViewById(R.id.view63);
        TextView view64 = findViewById(R.id.view64);
        TextView view65 = findViewById(R.id.view65);
        TextView view66 = findViewById(R.id.view66);
        TextView view67 = findViewById(R.id.view67);
        TextView view68 = findViewById(R.id.view68);
        TextView view69 = findViewById(R.id.view69);
        TextView view70 = findViewById(R.id.view70);
        TextView view71 = findViewById(R.id.view71);
        TextView view72 = findViewById(R.id.view72);
        TextView view73 = findViewById(R.id.view73);
        TextView view74 = findViewById(R.id.view74);
        TextView view75 = findViewById(R.id.view75);
        TextView view76 = findViewById(R.id.view76);
        TextView view77 = findViewById(R.id.view77);
        TextView view78 = findViewById(R.id.view78);
        TextView view79 = findViewById(R.id.view79);
        TextView view80 = findViewById(R.id.view80);
        TextView view81 = findViewById(R.id.view81);

        viewList5 = new ArrayList<>();
        viewList5.add(view55);
        viewList5.add(view56);
        viewList5.add(view57);
        viewList5.add(view58);
        viewList5.add(view59);
        viewList5.add(view60);
        viewList5.add(view61);
        viewList5.add(view62);
        viewList5.add(view63);
        viewList5.add(view64);
        viewList5.add(view65);
        viewList5.add(view66);
        viewList5.add(view67);
        viewList5.add(view68);
        viewList5.add(view69);
        viewList5.add(view70);
        viewList5.add(view71);
        viewList5.add(view72);
        viewList5.add(view73);
        viewList5.add(view74);
        viewList5.add(view75);
        viewList5.add(view76);
        viewList5.add(view77);
        viewList5.add(view78);
        viewList5.add(view79);
        viewList5.add(view80);
        viewList5.add(view81);






        startbutton5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //if game started, highlight random view and start timer
                if (!gamestarted) {
                    gamestarted = true;
                    startGame5();
                    random5 = new Random();
                    currentHighlightedView5 = viewList5.get(random5.nextInt(viewList5.size()));
                    currentHighlightedView5.setBackgroundResource(R.drawable.highlighted_background);


                } else if (timerunning5) {
                    endGame5();

                }



            }
        });
        //end button action
        endbutton5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                endGame5();
                Toast.makeText(level5.this,"Game Ended !",Toast.LENGTH_SHORT).show();
                for (TextView view : viewList5) {
                    view.setBackgroundResource(R.drawable.unhighlighted_background);
                }
                Intent intentend5 = new Intent (level5.this, MainActivity.class);
                startActivity(intentend5);
            }
        });
        updateCountdownText5();

        // disable click for the views
        for (TextView view : viewList5) {
            view.setEnabled(false);
        }

    }
    //start timer and enable click views
    private void startGame5() {
        startTimer5();
        startbutton5.setVisibility(View.INVISIBLE);
        endbutton5.setVisibility(View.VISIBLE);

        for (TextView view : viewList5) {
            view.setEnabled(true);
            view.setOnClickListener(this);

        }
        gamestarted = true;

    }
    //stop timer and disable click views
    private void endGame5() {
        mTimer5.cancel();
        timerunning5= false;
        startbutton5.setVisibility(View.INVISIBLE);
        endbutton5.setVisibility(View.INVISIBLE);
        resetTimer5();
        gamestarted = false;
        for (TextView view : viewList5) {
            view.setEnabled(false);
        }
    }

    //stop timer and reset score
    private void resetTimer5(){
        mTimeLeftinMs5 = START_TIME_IN_MILLIS5;
        updateCountdownText5();
        endbutton5.setVisibility(View.VISIBLE);
        startbutton5.setVisibility(View.VISIBLE);
        mSuccessfulTouches =0 ;

    }
    private void startTimer5() {

        // Start the timer
        mTimer5 = new CountDownTimer(mTimeLeftinMs5, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                // Update the timer display
                mTimeLeftinMs5 = millisUntilFinished;
                updateCountdownText5();

            }

            //end of timer
            @Override
            public void onFinish() {
                timerunning5=false;
                startbutton5.setText("Start");
                endbutton5.setVisibility(View.VISIBLE);

                AlertDialog.Builder builder = new AlertDialog.Builder(level5.this);
                builder.setTitle("Congratulations! You're in the top 25!");
                builder.setMessage("Enter your name to save your score:");
                final EditText input = new EditText(level5.this);
                builder.setView(input);
                builder.setCancelable(false);
                builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String name =input.getText().toString();
                        int score = mSuccessfulTouches;
                        boolean isInserted = db5.insertuserdata(name, score);
                        if (isInserted) {
                            Toast.makeText(level5.this, "Data saved successfully", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(level5.this, "Error: Data not saved", Toast.LENGTH_SHORT).show();
                        }

                        Intent intent = new Intent (level5.this, MainActivity2.class);
                        startActivity(intent);

                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Intent intent5 = new Intent (level5.this, MainActivity.class);
                        startActivity(intent5);
                    }
                });
                builder.show();



            }
        }.start();
        timerunning5= true;
    }

    //show timer
    private void updateCountdownText5(){
        int min = (int)(mTimeLeftinMs5/1000) /60;
        int sec = (int)(mTimeLeftinMs5/1000) %60;

        String timeleftformat = String.format(Locale.getDefault(),"%02d:%02d",min,sec);
        mTextcountdown5.setText(timeleftformat);

    }
    @Override
    public void onClick(View v) {
        // Check if the clicked view is the current highlighted view

        if (v == currentHighlightedView5) {
            // Remove the highlight from the current view
            currentHighlightedView5.setBackgroundResource(R.drawable.unhighlighted_background);

            // Randomly highlight a new view
            List<TextView> unhighlightedViews = new ArrayList<>(viewList5);
            unhighlightedViews.remove(currentHighlightedView5);
            currentHighlightedView5 = unhighlightedViews.get(random5.nextInt(unhighlightedViews.size()));
            currentHighlightedView5.setBackgroundResource(R.drawable.highlighted_background);
            //increase score when clicked highlighted view
            mSuccessfulTouches++;
            mScore5.setText("Score: " + mSuccessfulTouches);
        }

    }
}