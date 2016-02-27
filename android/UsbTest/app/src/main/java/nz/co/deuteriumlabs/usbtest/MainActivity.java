package nz.co.deuteriumlabs.usbtest;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.usb.UsbAccessory;
import android.hardware.usb.UsbManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;


public class MainActivity extends Activity {

    private static final String TAG = "MainActivity";
    UsbManager usbManager;

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
            startTestAsync();
        }
    }

    private void startTestAsync() {
        new TestAsyncTask().execute();
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

    private class TestAsyncTask extends AsyncTask<Void, Void, Void> {
        private String name = "";

        /**
         * Override this method to perform a computation on a background thread. The
         * specified parameters are the parameters passed to {@link #execute}
         * by the caller of this task.
         * <p/>
         * This method can call {@link #publishProgress} to publish updates
         * on the UI thread.
         *
         * @param params The parameters of the task.
         * @return A result, defined by the subclass of this task.
         * @see #onPreExecute()
         * @see #onPostExecute
         * @see #publishProgress
         */
        @Override
        protected Void doInBackground(Void... params) {
            name = "Matt";
            return null;
        }

        /**
         * <p>Runs on the UI thread after {@link #doInBackground}. The
         * specified result is the value returned by {@link #doInBackground}.</p>
         * <p/>
         * <p>This method won't be invoked if the task was cancelled.</p>
         *
         * @param aVoid The result of the operation computed by {@link #doInBackground}.
         * @see #onPreExecute
         * @see #doInBackground
         * @see #onCancelled(Object)
         */
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            TextView view = (TextView) findViewById(R.id.desc);
            view.setText("Hello, " + name + "!");
        }
    }
}
