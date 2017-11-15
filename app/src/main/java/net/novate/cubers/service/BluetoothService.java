package net.novate.cubers.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import net.novate.cubers.ble.Bluetooth;

public class BluetoothService extends Service {

    Bluetooth bluetooth;
    @Override
    public void onCreate() {
        super.onCreate();
        bluetooth = Bluetooth.get();

        bluetooth.connect(getApplicationContext(),false,"88:0F:10:A0:E6:5B");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }


}
