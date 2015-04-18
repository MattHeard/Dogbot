package nz.co.deuteriumlabs.usbtest;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.usb.UsbAccessory;
import android.hardware.usb.UsbManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        if (intent != null) {
            handleAccessoryIntent(intent);
        }
    }

    private void handleAccessoryIntent(Intent intent) {
        UsbAccessory accessory = intent.getParcelableExtra(UsbManager.EXTRA_ACCESSORY);
        if (accessory != null) {
            String model_name = accessory.getModel();
            TextView accessoryModelView = (TextView) findViewById(R.id.accessory_model);
            accessoryModelView.setText(model_name);
        }
    }
}
