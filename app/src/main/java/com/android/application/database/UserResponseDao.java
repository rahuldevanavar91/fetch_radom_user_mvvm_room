package com.android.application.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.android.application.model.ResultItem;

import java.util.List;

@Dao
public interface UserResponseDao {

    @Query("Select * from RESULT_ITEM")
    List<ResultItem> getAllResponse();

    @Query("Select Count(*) from RESULT_ITEM")
    int getRowCount();

    @Insert
    void insertResponse(List<ResultItem> result);

    @Query("update result_item set member_request_status=:status where `key`=:id")
    void updateLikeDislike(int id, int status);

    @Query("DELETE FROM RESULT_ITEM WHERE `key` IN " +
            "(SELECT `key` FROM RESULT_ITEM ORDER BY `key` asc LIMIT 50)")
    void deleteOldData();
}

