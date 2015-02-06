package nz.co.deuteriumlabs.dogbot;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class MainActivity extends Activity implements SensorEventListener {

    ArrayAdapter<String> arrayAdapter;
    private SensorManager sensorManager;
    private HashMap<Integer, Sensor> sensors;
    private HashMap<Integer, Integer> listIndexes;
    private List<String> sensorVals;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initSensors();
        initListView();
    }

    private void initListView() {
        ListView listView = (ListView) findViewById(R.id.sensor_list);
        listView.setAdapter(arrayAdapter);
    }

    private void initSensors() {
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensors = new HashMap<Integer, Sensor>();
        listIndexes = new HashMap<Integer, Integer>();
        sensorVals = new ArrayList<String>();
        int index = 0;
        List<Integer> types = getTypes();
        for (int type : types) {
            Sensor sensor = sensorManager.getDefaultSensor(type);
            if (sensor != null) {
                sensors.put(type, sensor);
                listIndexes.put(type, index);
                sensorVals.add(index, "");
                index++;
            }
        }
        arrayAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,
                sensorVals);
    }

    private List<Integer> getTypes() {
        List<Integer> types = new ArrayList<Integer>();
        types.add(Sensor.TYPE_ACCELEROMETER);
        types.add(Sensor.TYPE_AMBIENT_TEMPERATURE);
        types.add(Sensor.TYPE_GRAVITY);
        types.add(Sensor.TYPE_GYROSCOPE);
        types.add(Sensor.TYPE_LIGHT);
        types.add(Sensor.TYPE_LINEAR_ACCELERATION);
        types.add(Sensor.TYPE_MAGNETIC_FIELD);
        types.add(Sensor.TYPE_PRESSURE);
        types.add(Sensor.TYPE_PROXIMITY);
        types.add(Sensor.TYPE_RELATIVE_HUMIDITY);
        types.add(Sensor.TYPE_ROTATION_VECTOR);
        return types;
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

    @Override
    public void onSensorChanged(SensorEvent event) {
        final int type = event.sensor.getType();
        Integer index = listIndexes.get(type);
        if (index != null) {
            String output = getSensorVal(event);
            sensorVals.set(index, output);
            arrayAdapter.notifyDataSetChanged();
        }
    }

    private String getSensorVal(SensorEvent event) {
        final int type = event.sensor.getType();
        switch (type) {
            case Sensor.TYPE_ACCELEROMETER:
                return getAccelerometerVal(event);
            case Sensor.TYPE_AMBIENT_TEMPERATURE:
                return getAmbientTemperatureVal(event);
            case Sensor.TYPE_GRAVITY:
                return getGravityVal(event);
            case Sensor.TYPE_GYROSCOPE:
                return getGyroscopeVal(event);
            case Sensor.TYPE_LIGHT:
                return getLightVal(event);
            case Sensor.TYPE_LINEAR_ACCELERATION:
                return getLinearAccelerationVal(event);
            case Sensor.TYPE_MAGNETIC_FIELD:
                return getMagneticFieldVal(event);
            case Sensor.TYPE_PRESSURE:
                return getPressureVal(event);
            case Sensor.TYPE_PROXIMITY:
                return getProximityVal(event);
            case Sensor.TYPE_RELATIVE_HUMIDITY:
                return getRelativeHumidityVal(event);
            case Sensor.TYPE_ROTATION_VECTOR:
                return getRotationVectorVal(event);
            default:
                return "Unknown sensor: no value reported";
        }
    }

    private String getRotationVectorVal(SensorEvent event) {
        float x = event.values[0];
        float y = event.values[1];
        float z = event.values[2];
        float scalar = event.values[3];
        return "Rotation vector:\n" +
                "x=" + x + "\n" +
                "y=" + y + "\n" +
                "z=" + z + "\n" +
                "scalar=" + scalar;
    }

    private String getRelativeHumidityVal(SensorEvent event) {
        float humidity = event.values[0];
        return "Relative humidity:\n" + humidity + "%";
    }

    private String getProximityVal(SensorEvent event) {
        float proximity = event.values[0];
        return "Proximity:\n" + proximity + "cm";
    }

    private String getPressureVal(SensorEvent event) {
        float pressure = event.values[0];
        return "Pressure:\n" + pressure + " hPa (mbar)";
    }

    private String getMagneticFieldVal(SensorEvent event) {
        float x = event.values[0];
        float y = event.values[1];
        float z = event.values[2];
        return "Magnetic field:\n" +
                "x=" + x + " μT\n" +
                "y=" + y + " μT\n" +
                "z=" + z + " μT";
    }

    private String getLinearAccelerationVal(SensorEvent event) {
        float x = event.values[0];
        float y = event.values[1];
        float z = event.values[2];
        return "Linear acceleration (excludes gravity):\n" +
                "x=" + x + " m/s²\n" +
                "y=" + y + " m/s²\n" +
                "z=" + z + " m/s²";
    }

    private String getGyroscopeVal(SensorEvent event) {
        float x = event.values[0];
        float y = event.values[1];
        float z = event.values[2];
        return "Gyroscope:\n" +
                "x=" + x + " rad/s²\n" +
                "y=" + y + " rad/s²\n" +
                "z=" + z + " rad/s²";
    }

    private String getGravityVal(SensorEvent event) {
        float x = event.values[0];
        float y = event.values[1];
        float z = event.values[2];
        return "Gravity:\n" +
                "x=" + x + " m/s²\n" +
                "y=" + y + " m/s²\n" +
                "z=" + z + " m/s²";
    }

    private String getAmbientTemperatureVal(SensorEvent event) {
        float temperature = event.values[0];
        return "Ambient air temperature:\n" + temperature + "°C";
    }

    private String getAccelerometerVal(SensorEvent event) {
        float x = event.values[0];
        float y = event.values[1];
        float z = event.values[2];
        return "Accelerometer (includes gravity):\n" +
                "x=" + x + " m/s²\n" +
                "y=" + y + " m/s²\n" +
                "z=" + z + " m/s²";
    }

    private String getLightVal(SensorEvent event) {
        float lux = event.values[0];
        return "Light: " + Float.toString(lux) + " lux";
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        /* Do nothing. */
    }

    @Override
    protected void onResume() {
        super.onResume();
        for (int type : sensors.keySet()) {
            SensorEventListener listener = this;
            Sensor sensor = sensors.get(type);
            int delay = 1000000;    // 1,000,000 microseconds, which is also 1 second
            sensorManager.registerListener(listener, sensor, delay);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }
}
