package src.com.hoho.android.usbserial.examples;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.hoho.android.usbserial.examples.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ComputeActivity extends Activity {

    int height;
    int weight;
    boolean gender;

    public static final String MY_PREFS_NAME = "MyPrefsFile";

    ArrayList<String> dataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compute);

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                dataList = null;
            } else {
                dataList = extras.getStringArrayList("height");
            }
        } else {
            Log.e("fail", "max");
        }

        System.out.println(dataList);
/*
        String last[] = new String[dataList.size()];
        for(int i = 0; i<dataList.size(); i++)
        {
            String temp[] = dataList.get(i).split(" ");
            for(int j = 0; j<temp.length; j++)
            System.out.println(temp[j]);
            //last[i] = temp[temp.length-1];
            //Log.e("last", last[i]);
        }*/
        String string = "";
        for(int i = 0; i<dataList.size(); i++)
        {
            string += dataList + " ";
        }

        Pattern pattern = Pattern.compile("(\\.\\.)([0-9]*?)(\\.\\.)");
        Matcher matcher = pattern.matcher(string);

        List<String> listMatches = new ArrayList<String>();

        while(matcher.find())
        {
            listMatches.add(matcher.group(2));
        }

        int numbers[] = new int[listMatches.size()];
        int j = 0;
        for(String s : listMatches)
        {
            System.out.println(s);
            try {
                numbers[j++] = Integer.parseInt(s);
            }
            catch (NumberFormatException e)
            {
                e.printStackTrace();
            }
            Log.e("number", numbers[j - 1] + "");
        }

        int smallest = numbers[0];
        int largetst = numbers[0];

        for(int i=1; i< numbers.length; i++)
        {
            if(numbers[i] > largetst)
                largetst = numbers[i];
            else if (numbers[i] < smallest)
                smallest = numbers[i];

        }

        System.out.println("Largest Number is : " + largetst);
        System.out.println("Smallest Number is : " + smallest);

        int t = largetst;

        double result1 = 4.88*Math.pow(10, -3)*t;

        double result2 = result1/Math.sqrt(2);

        double result3 = result2/(0.8*Math.pow(10, -3));

        Log.e("result1", result1+"");
        Log.e("result2", result2 + "");
        Log.e("result3", result3 + "");

        TextView tv = (TextView)findViewById(R.id.text);


        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        height = prefs.getInt("height", 0);
        weight = prefs.getInt("weight", 0);
        gender = prefs.getBoolean("gender", false);

        //Fat-free mass
        double result = 0;
        if(gender)
        {
            result = 0.485*(height*height/result3)+0.338*weight+23.52;
        }
        else
        {
            result = 0.475*(height*height/result3)+0.295*weight+5.49;
        }
        double bmi = 0;
        bmi = weight/((height/100)*(height/100));

        Log.e("fat free mass", result+"");
        Log.e("bmi", bmi+"");

        tv.setText("1. " + result1 + "\n2. " + result2 + "\n3. " + result3 + "\n4. " + result + "\n5. " + bmi);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_compute, menu);
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
