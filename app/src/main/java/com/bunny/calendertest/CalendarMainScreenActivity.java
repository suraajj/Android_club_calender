package com.bunny.calendertest;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CalendarView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

public class CalendarMainScreenActivity extends AppCompatActivity {


    CalendarView calendarView;
    String[] dates;
    String[] events;
    TextView eventInfo;


    public void funtion(String text) {


        String[] lines = text.split(";;");
        String[][] words = new String[lines.length][];


        for (int i = 0; i < lines.length; i++) {
            words[i] = lines[i].split(";");
        }

        dates = new String[lines.length];
        events = new String[lines.length];
        for (int i = 0; i < lines.length; i++) {

            dates[i] = words[i][1];
            events[i] = words[i][2];
        }


        Log.i("Dates", Arrays.toString(dates));


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_main_screen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        calendarView = (CalendarView) findViewById(R.id.calendar);
        String text;
        try {
            InputStream in = getAssets().open("file.txt");
            int size = in.available();
            byte[] buffer = new byte[size];
            in.read(buffer);
            text = new String(buffer);
            funtion(text);
            //Log.i("Text",text);

        } catch (IOException e) {
            e.printStackTrace();
        }


        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {

                String date;
                if (i1 < 10 && i2 < 10) {
                    date = "0" + i2 + "/0" + (i1 + 1) + "/" + i % 100;
                } else if (i1 < 10 && i2 >= 10) {
                    date = i2 + "/0" + (i1 + 1) + "/" + i % 100;
                } else if (i1 < 10 && i2 >= 10) {
                    date = "0" + i2 + "/" + (i1 + 1) + "/" + i % 100;
                } else {
                    date = i2 + "/" + (i1 + 1) + "/" + i % 100;
                }

                int dateValue = -1;
                Log.i("date", date);
                Log.i("dateValue", dateValue + "");
                //dateValue=Arrays.asList(dates).indexOf(date);

                eventInfo = (TextView) findViewById(R.id.eventInfo);

                for (int j = 0; j < dates.length; j++)
                    if (dates[j].equalsIgnoreCase(date)) {
                        dateValue = j;
                        break;
                    }

                if (dateValue == -1) {
                    // Toast.makeText(getApplicationContext(),"Nothing on this day!",Toast.LENGTH_SHORT).show();
                    eventInfo.setTextColor(Color.RED);
                    eventInfo.setText("No Events!");
                    return;
                }
                eventInfo.setTextColor(Color.BLACK);
                eventInfo.setText(events[dateValue]);


            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_calendar_main_screen, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Log.i("id", id + "");
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
