package net.novate.cubers.data;

import android.arch.persistence.room.Room;
import android.content.Context;

import io.reactivex.Flowable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * author: gavin
 * create on: 2017/10/31.
 * description:
 */

public class DataBaseBuilder {

    private static final Object LOCK = new Object();

    private DataBase dataBase;

    private DataBaseBuilder() {
    }

    public static DataBaseBuilder get() {
        return SingletonHolder.INSTANCE;
    }

    public Flowable<DataBase> getDataBase(Context context) {
        return Flowable.just(context)
                .observeOn(Schedulers.io())
                .map(new Function<Context, DataBase>() {
                    @Override
                    public DataBase apply(Context context) throws Exception {
                        if (dataBase == null) {
                            synchronized (LOCK) {
                                if (dataBase == null) {
                                    dataBase = Room.databaseBuilder(context.getApplicationContext(), DataBase.class, DataBase.DATABASE_NAME).build();
                                }
                            }
                        }
                        return dataBase;
                    }
                });
    }

    private static class SingletonHolder {
        private static final DataBaseBuilder INSTANCE = new DataBaseBuilder();
    }
}
