package src.com.hoho.android.usbserial.examples;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;

import com.hoho.android.usbserial.examples.R;

public class MainActivity extends Activity {

    public static final String MY_PREFS_NAME = "MyPrefsFile";

    EditText h;
    EditText w;
    com.rey.material.widget.Switch aSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle("FitTech");

        h = (EditText)findViewById(R.id.height);
        w = (EditText)findViewById(R.id.weight);
        aSwitch = (com.rey.material.widget.Switch)findViewById(R.id.gender);
    }

    public void ok(View view)
    {
        String height = h.getText().toString();
        String weight = w.getText().toString();
        boolean sex = aSwitch.isChecked();

        SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
        editor.putInt("height", Integer.parseInt(height));
        editor.putInt("weight", Integer.parseInt(weight));
        editor.putBoolean("gender", sex);
        editor.commit();

        Intent i = new Intent(this, PlaySound.class);
        i.putExtra("height", height);
        i.putExtra("weight", weight);
        i.putExtra("gender", sex);

        startActivity(i);
    }
}
