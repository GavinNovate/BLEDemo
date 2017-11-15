package net.novate.cubers.data;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import net.novate.cubers.model.Report;

import java.util.List;

import io.reactivex.Flowable;

/**
 * author: gavin
 * created on: 2017-11-15
 * description:
 */
@Dao
public interface ReportDao {

    @Insert
    void install(Report... reports);

    @Delete
    void delete(Report... reports);

    @Update
    void update(Report report);

    @Query("SELECT * FROM Report")
    List<Report> selectAll();

    @Query("SELECT * FROM Report WHERE id = :id")
    Report selectByID(int id);

    @Query("SELECT * FROM Report")
    Flowable<List<Report>> observeAll();
}
