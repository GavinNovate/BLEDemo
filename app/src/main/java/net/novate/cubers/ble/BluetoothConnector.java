package net.novate.cubers.ble;

import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.util.Log;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;

/**
 * author: gavin
 * created on: 2017-11-15
 * description:蓝牙连接器
 */
public class BluetoothConnector extends BluetoothGattCallback {

    private static final String TAG = "BluetoothConnector";

    private Subject<Integer> connectStatSubject;

    public BluetoothConnector() {

        connectStatSubject = PublishSubject.create();
    }

    public Observable<Integer> observeConnectState() {
        return connectStatSubject;
    }

    /**
     * 回调表明 Gatt 连接状态
     *
     * @param gatt
     * @param status
     * @param newState
     */
    @Override
    public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
        /**
         * 连接状态：
         *    * The profile is in disconnected state   *public static final int STATE_DISCONNECTED  = 0;
         *    * The profile is in connecting state     *public static final int STATE_CONNECTING    = 1;
         *    * The profile is in connected state      *public static final int STATE_CONNECTED    = 2;
         *    * The profile is in disconnecting state  *public static final int STATE_DISCONNECTING = 3;
         *
         */
        Log.e(TAG, "连接状态:" + newState);
        if (BluetoothGatt.STATE_CONNECTED == newState) {
            Log.e(TAG, "连接成功:");
            gatt.discoverServices(); //必须有，可以让onServicesDiscovered显示所有Services
        } else if (BluetoothGatt.STATE_DISCONNECTED == newState) {
            Log.e(TAG, "断开连接:");
        }
        connectStatSubject.onNext(newState);
    }

    /**
     * 回调通知远程服务列表
     *
     * @param gatt
     * @param status
     */
    @Override
    public void onServicesDiscovered(BluetoothGatt gatt, int status) {
        for (BluetoothGattService service : gatt.getServices()) {
            Log.d(TAG, "onServicesDiscovered: Service = " + service.getUuid().toString());
            for (BluetoothGattCharacteristic characteristic : service.getCharacteristics()) {
                Log.d(TAG, "onServicesDiscovered: characteristic = " + characteristic.getUuid().toString());
                for (BluetoothGattDescriptor descriptor : characteristic.getDescriptors()) {
                    Log.d(TAG, "onServicesDiscovered: descriptor = " + descriptor.getUuid().toString());
                }
            }
        }
    }

    /**
     * 回调报告特征值读操作的结果
     *
     * @param gatt
     * @param characteristic
     * @param status
     */
    @Override
    public void onCharacteristicRead(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
        Log.d(TAG, "onCharacteristicRead() called with: gatt = [" + gatt + "], characteristic = [" + characteristic + "], status = [" + status + "]");
    }

    /**
     * 回调指示特征值写操作的结果
     *
     * @param gatt
     * @param characteristic
     * @param status
     */
    @Override
    public void onCharacteristicWrite(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
        Log.d(TAG, "onCharacteristicWrite() called with: gatt = [" + gatt + "], characteristic = [" + characteristic + "], status = [" + status + "]");
    }

    /**
     * 回调通知由于远程触发特征值
     *
     * @param gatt
     * @param characteristic
     */
    @Override
    public void onCharacteristicChanged(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic) {
        Log.d(TAG, "onCharacteristicChanged() called with: gatt = [" + gatt + "], characteristic = [" + characteristic + "]");
    }

    /**
     * 回调报告描述符读操作的结果
     *
     * @param gatt
     * @param descriptor
     * @param status
     */
    @Override
    public void onDescriptorRead(BluetoothGatt gatt, BluetoothGattDescriptor descriptor, int status) {
        Log.d(TAG, "onDescriptorRead() called with: gatt = [" + gatt + "], descriptor = [" + descriptor + "], status = [" + status + "]");
    }

    /**
     * 回调指示描述符写操作的结果
     *
     * @param gatt
     * @param descriptor
     * @param status
     */
    @Override
    public void onDescriptorWrite(BluetoothGatt gatt, BluetoothGattDescriptor descriptor, int status) {
        Log.d(TAG, "onDescriptorWrite() called with: gatt = [" + gatt + "], descriptor = [" + descriptor + "], status = [" + status + "]");
    }

    /**
     * 回调报告一个可靠的写操作已完成
     *
     * @param gatt
     * @param status
     */
    @Override
    public void onReliableWriteCompleted(BluetoothGatt gatt, int status) {
        Log.d(TAG, "onReliableWriteCompleted() called with: gatt = [" + gatt + "], status = [" + status + "]");
    }

}
