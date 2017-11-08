package net.novate.cubers.data;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import net.novate.cubers.entity.DeviceEntity;

import java.util.List;

import io.reactivex.Flowable;

/**
 * author: gavin
 * create on: 2017/10/31.
 * description:
 * <p>
 * 增 删 查 改
 */

@Dao
public interface DeviceDao {

    @Insert
    void install(DeviceEntity... deviceEntities);

    @Delete
    void delete(DeviceEntity... deviceEntities);

    @Update
    void update(DeviceEntity deviceEntity);

    @Query("SELECT * FROM deviceentity")
    List<DeviceEntity> selectAll();

    @Query("SELECT * FROM deviceentity WHERE id = :id")
    DeviceEntity selectByID(int id);

    @Query("SELECT * FROM deviceentity WHERE name LIKE :name")
    DeviceEntity selectByName(String name);

    @Query("SELECT * FROM deviceentity WHERE address LIKE :address")
    DeviceEntity selectByAddress(String address);

    @Query("SELECT * FROM deviceentity")
    Flowable<List<DeviceEntity>> observeAll();
}
