package src.com.hoho.android.usbserial.examples;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.hoho.android.usbserial.examples.R;

public class MainActivity extends Activity {

    EditText h;
    EditText w;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle("FitTech");

        h = (EditText)findViewById(R.id.height);
        w = (EditText)findViewById(R.id.weight);
    }

    public void ok(View view)
    {
        String height = h.getText().toString();
        String weight = w.getText().toString();

        Intent i = new Intent(this, PlaySound.class);
        i.putExtra("height", height);
        i.putExtra("weight", weight);

        startActivity(i);
    }
}
