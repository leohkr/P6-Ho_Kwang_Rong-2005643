package utar.edu.my.mainactivity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity2 extends AppCompatActivity {
    private DBHelper db;
    private Cursor res;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //link database sqlite
        db = new DBHelper(this);
        setContentView(R.layout.activity_main2);
        int count = 0;
        //get input and score data
        res = db.getdata();
        if (res.getCount() == 0) {
            Toast.makeText(MainActivity2.this, "Not Exists", Toast.LENGTH_LONG).show();
            return;
        }
        //display the data stored
        StringBuffer buffer = new StringBuffer();
            while (res.moveToNext() && count < 25) {
                buffer.append("Name :" + res.getString(0) + "\n");
                buffer.append("Score:" + res.getInt(1) + "\n");
                buffer.append("________________________________________\n");
                count++;
            }
        //button to go back
        Button backbtn = findViewById(R.id.backbtn);
            backbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intentmain2 = new Intent(MainActivity2.this,MainActivity.class);
                    startActivity(intentmain2);
                }
            });
        //leaderboard to display name and score
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity2.this);
        builder.setCancelable(false);
        builder.setTitle("Leaderboard");
        builder.setMessage(buffer.toString());
        //button to go back
        builder.setPositiveButton("Back", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                finish(); // Call the finish() method to close the current activity
            }
        });
        //button to delete all data recorded
        builder.setNegativeButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                Integer var = db.deleteData();
                if (var > 0) {
                    Toast.makeText(MainActivity2.this, "All data has been deleted", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity2.this, "Delete Failed", Toast.LENGTH_SHORT).show();

                }
            }
        });
        builder.show();



    }
}