package net.novate.cubers.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

/**
 * author: gavin
 * created on: 2017-11-15
 * description:记录报告
 */
@Entity
public class Report implements Comparable<Report> {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo
    private String content;

    @ColumnInfo
    private long duration;

    @ColumnInfo
    private long createTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    /**
     * 按创建时间降序排序
     *
     * @param target 比对目标
     * @return 排序参数
     */
    @Override
    public int compareTo(@NonNull Report target) {
        long sub = target.getCreateTime() - getCreateTime();
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
        return "Report{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", duration=" + duration +
                ", createTime=" + createTime +
                '}';
    }
}
