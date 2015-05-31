package nz.co.deuteriumlabs.usbtest;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.usb.UsbAccessory;
import android.hardware.usb.UsbManager;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;


public class MainActivity extends Activity {

    private static final String TAG = "MainActivity";
    UsbAccessory accessory;
    UsbManager usbManager;
    ParcelFileDescriptor fileDescriptor;
    FileInputStream inputStream;
    FileOutputStream outputStream;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        usbManager = (UsbManager) getSystemService(Context.USB_SERVICE);

        Intent intent = getIntent();
        if (intent != null) {
            handleAccessoryIntent(intent);
        }
    }

    private void handleAccessoryIntent(Intent intent) {
        UsbAccessory accessory = intent.getParcelableExtra(UsbManager.EXTRA_ACCESSORY);
        if (accessory != null) {
            identifyAccessory(accessory);
            enableHelloButton();
        }
    }

    private void enableHelloButton() {
        Button button = (Button) findViewById(R.id.button);
        button.setEnabled(true);
    }

    private void identifyAccessory(UsbAccessory accessory) {
        String model_name = accessory.getModel();
        TextView accessoryModelView = (TextView) findViewById(R.id.accessory_model);
        accessoryModelView.setText(model_name);
    }
}
