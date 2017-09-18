package by.grsu.ftf.Bluetooth;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;

/**
 * Created by Lenovo on 29.08.2017.
 */

public class BluetoothConfigurator extends Activity{
    private static final int REQUEST_ENABLE_BT = 1;

    public void onBluetooth(){
    BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    if (mBluetoothAdapter == null) {
        // Device does not support Bluetooth
    }
    if (!mBluetoothAdapter.isEnabled()) {
        Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
        startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
    }
}
}
