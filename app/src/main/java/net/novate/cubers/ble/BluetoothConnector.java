package net.novate.cubers.ble;

import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.util.Log;

/**
 * author: gavin
 * created on: 2017-11-15
 * description:蓝牙连接器
 */
public class BluetoothConnector extends BluetoothGattCallback {

    private static final String TAG = "BluetoothConnector";

    /**
     * 回调表明 Gatt 连接状态
     *
     * @param gatt
     * @param status
     * @param newState
     */
    @Override
    public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
        Log.d(TAG, "onConnectionStateChange() called with: gatt = [" + gatt + "], status = [" + status + "], newState = [" + newState + "]");
    }

    /**
     * 回调通知远程服务列表
     *
     * @param gatt
     * @param status
     */
    @Override
    public void onServicesDiscovered(BluetoothGatt gatt, int status) {
        Log.d(TAG, "onServicesDiscovered() called with: gatt = [" + gatt + "], status = [" + status + "]");
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
