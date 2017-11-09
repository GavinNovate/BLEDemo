package net.novate.cubers.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

/**
 * author: gavin
 * create on: 2017/10/31.
 * description:
 */

@Entity(indices = {@Index(value = "address", unique = true)})
public class Device implements Comparable<Device> {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo
    private String name;

    @ColumnInfo
    private String address;

    @ColumnInfo
    private long lastTime;

    public Device() {
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public long getLastTime() {
        return lastTime;
    }

    public void setLastTime(long lastTime) {
        this.lastTime = lastTime;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Device && ((Device) obj).getAddress().equals(address);
    }

    /**
     * 按最后连接时间降序排列
     *
     * @param target 比对目标
     * @return 排序参数
     */
    @Override
    public int compareTo(@NonNull Device target) {
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
