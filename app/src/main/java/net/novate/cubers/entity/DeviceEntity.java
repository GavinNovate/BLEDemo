package net.novate.cubers.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import net.novate.cubers.model.Device;

/**
 * author: gavin
 * create on: 2017/10/31.
 * description:
 */

@Entity(indices = {@Index(value = "address", unique = true)})
public class DeviceEntity implements Device, Comparable<DeviceEntity> {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo
    private String name;

    @ColumnInfo
    private String address;

    @ColumnInfo
    private long lastTime;

    public DeviceEntity() {
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public long getLastTime() {
        return lastTime;
    }

    public void setLastTime(long lastTime) {
        this.lastTime = lastTime;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof DeviceEntity && ((DeviceEntity) obj).getAddress().equals(address);
    }

    /**
     * 按最后连接时间降序排列
     *
     * @param target 比对目标
     * @return 排序参数
     */
    @Override
    public int compareTo(@NonNull DeviceEntity target) {
        long sub = target.getLastTime() - getLastTime();
        if (sub > 0) {
            return -1;
        } else if (sub < 0) {
            return 1;
        } else {
            return 0;
        }
    }

    @Override
    public String toString() {
        return "Device:" + id + " " + name + " - " + address;
    }
}
