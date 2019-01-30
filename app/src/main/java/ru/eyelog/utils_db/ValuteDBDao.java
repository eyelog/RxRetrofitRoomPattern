package ru.eyelog.utils_db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Single;

@Dao
public interface ValuteDBDao {

    @Query("SELECT * FROM ValuteDB")
    Flowable<List<ValuteDB>> getAll();

    @Query("SELECT * FROM ValuteDB WHERE id = :id")
    Single<ValuteDB> getById(long id);

    @Insert
    void insertAll(List<ValuteDB> valuteDB);

    @Insert
    void insert(ValuteDB valuteDB);

    @Update
    void update(ValuteDB valuteDB);

    @Delete
    void delete(ValuteDB valuteDB);

    @Query("DELETE FROM ValuteDB")
    void deleteAll();

}