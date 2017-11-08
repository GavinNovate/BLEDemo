package net.novate.cubers.data;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import net.novate.cubers.entity.DeviceEntity;

/**
 * author: gavin
 * create on: 2017/10/31.
 * description:
 */
@Database(entities = {DeviceEntity.class}, version = 1)
public abstract class DataBase extends RoomDatabase {

    public static final String DATABASE_NAME = "database";

    public abstract DeviceDao deviceDao();
}
