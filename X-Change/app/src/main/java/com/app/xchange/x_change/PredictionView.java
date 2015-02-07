package com.app.xchange.x_change;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;


public class PredictionView extends ActionBarActivity {

    public final static String CURRENCY = "com.app.xchange.x_change.MESSAGE";
    public Intent intentRec;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get the message from the intent
        intentRec = getIntent();
        String message = intentRec.getStringExtra(MainActivity.CURRENCY);
        setContentView(R.layout.activity_prediction_view);

        TextView text = (TextView) findViewById(R.id.testText);
        text.setText(message);

        // Create the text view
        /*TextView textView = new TextView(this);
        textView.setTextSize(40);
        textView.setText(message);

        // Set the text view as the activity layout
        setContentView(textView);*/
    }

    /** Called when the user clicks the Stats button */
    public void openStats(View view) {
        Intent intent = new Intent(this, StatisticsView.class);
        intent.putExtra(CURRENCY, intentRec.getStringExtra(MainActivity.CURRENCY));
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}