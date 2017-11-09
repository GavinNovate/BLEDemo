package net.novate.cubers.data;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import net.novate.cubers.model.Device;

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
    void install(Device... devices);

    @Delete
    void delete(Device... devices);

    @Update
    void update(Device device);

    @Query("SELECT * FROM Device")
    List<Device> selectAll();

    @Query("SELECT * FROM Device WHERE id = :id")
    Device selectByID(int id);

    @Query("SELECT * FROM Device WHERE name LIKE :name")
    Device selectByName(String name);

    @Query("SELECT * FROM Device WHERE address LIKE :address")
    Device selectByAddress(String address);

    @Query("SELECT * FROM Device")
    Flowable<List<Device>> observeAll();
}
