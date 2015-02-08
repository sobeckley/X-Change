package com.app.xchange.x_change;

import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.StringTokenizer;


public class PredictionView extends ActionBarActivity {

    public final static String CURRENCY = "com.app.xchange.x_change.MESSAGE";
    public Intent intentRec;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {

        double[] prevData = new double[10];
        for (int i = 0; i < prevData.length; i++) {
            prevData[i] = i;
        }

        double[] newDataDouble = null;
        float[] newData = null;

        if (prevData == null) {
            System.out.println("WE GOTS NO DATA CAP'N");
            throw new NullPointerException();
        } else {
            //newDataDouble = PredictionMath.predict(prevData);
            newDataDouble = new double[10];

            newData = new float[newDataDouble.length];
            for(int j = 0; j < newDataDouble.length;j++) {
                newData[j] = (float) newDataDouble[j];
            }
        }

        float[] totalData = new float[prevData.length + newData.length];

        for(int q = 0; q < prevData.length; q++) {
            totalData[q] = (float) prevData[q];
        }

        for(int l = 0; l < newData.length; l++) {
            totalData[prevData.length + l] = newData[l];
        }

        super.onCreate(savedInstanceState);

        LinearLayout buttonGraph = new LinearLayout(this);
        buttonGraph.setOrientation(LinearLayout.VERTICAL);

        LinearLayout buttons = new LinearLayout(this);
        buttons.setOrientation(LinearLayout.VERTICAL);
        TextView CurrentValue = new TextView(this);
        CurrentValue.setText("Current Value: 27.98");
        TextView FiftyDayHigh = new TextView(this);
        FiftyDayHigh.setText("Fifty Day High: 28.31");
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT); // Verbose!

        buttons.addView(CurrentValue, lp);
        buttons.addView(FiftyDayHigh, lp);
        buttonGraph.addView(buttons, lp);

        float[] days = new float[totalData.length];
        for(int k = 0; k < totalData.length; k++) {
            days[k] = Float.parseFloat(Integer.toString(k));
        }

        float[] xvalues = days;
        float[] yvalues = totalData;

        int color = prevData.length;

        plot2d graph = new plot2d(this, xvalues, yvalues, 1, color);

        buttonGraph.addView(graph, lp);

        setContentView(buttonGraph);

    }

    private double[] readAndInsert(String csvSource) throws UnsupportedEncodingException {

        ArrayList<String> objList= new ArrayList<String>();
        AssetManager assetManager = getAssets();
        InputStream is = null;

        try {
            is = assetManager.open(csvSource);
        } catch (IOException e) {
            e.printStackTrace();
        }

        BufferedReader reader = null;
        reader = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));

        String line = "";
        StringTokenizer st = null;
        try {

            while ((line = reader.readLine()) != null) {
                st = new StringTokenizer(line, ",");
                String obj= new String ();
                //your attributes
                obj = st.nextToken();

                objList.add(obj);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        double[] data = new double[objList.size()];

        for(int i = 0; i < objList.size(); i++) {
            data[i] = Double.parseDouble(objList.get(i));
            System.out.println(objList.get(i));
        }

        return data;
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