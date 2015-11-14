package src.com.hoho.android.usbserial.examples;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;

import android.graphics.Color;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.hardware.usb.UsbManager;
import android.hardware.usb.UsbDeviceConnection;
import java.util.List;
import java.io.IOException;

import com.hoho.android.usbserial.driver.UsbSerialPort;
import com.hoho.android.usbserial.driver.UsbSerialProber;
import com.hoho.android.usbserial.driver.UsbSerialDriver;
import com.hoho.android.usbserial.examples.R;

public class TestActivity extends Activity {

    private static UsbSerialPort sPort = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    //write the serial data

    private void writetoserial(int col) {
        byte[] colorBytes = {(byte)Color.red(col),
                (byte)Color.green(col),
                (byte)Color.blue(col),
                0x0A};
        //remove spurious line endings so the serial device doesn't get confused
        for (int i=0; i<colorBytes.length-1; i++){
            if (colorBytes[i] == 0x0A){
                colorBytes[i] = 0x0B;
            }
        }

        // Find all available drivers from attached devices.
        UsbManager manager = (UsbManager) getSystemService(Context.USB_SERVICE);
        List<UsbSerialDriver> availableDrivers = UsbSerialProber.getDefaultProber().findAllDrivers(manager);
        if (availableDrivers.isEmpty()) {
            return;
        }

        // Open a connection to the first available driver.
        UsbSerialDriver driver = availableDrivers.get(0);
        UsbDeviceConnection connection = manager.openDevice(driver.getDevice());

        if (connection == null) {
            return;
        }

        try {
            sPort = driver.getPorts().get(0);
            sPort.open(connection);
            sPort.setParameters(115200, 8, UsbSerialPort.STOPBITS_1, UsbSerialPort.PARITY_NONE);

            sPort.write(colorBytes, 500);
        } catch (IOException e) {
            // Deal with error.
            try {
                sPort.close();
            } catch (IOException e2) {
                // Ignore.
            }
            sPort = null;
            return;
        }
    }

    public void onClickRed(View view){
        //write the serial data
        int col;
        col = 1;
        writetoserial(col);
    }

    public void onClickBlue(View view){
        //write the serial data
        int col;
        col = 2;
        writetoserial(col);
    }

    public void onClickGreen(View view){
        //write the serial data
        int col;
        col = 3;
        writetoserial(col);
    }
}