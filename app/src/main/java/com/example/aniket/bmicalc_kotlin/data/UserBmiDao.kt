package com.example.aniket.bmicalc_kotlin.data

import android.arch.persistence.room.*

/**
 * Created by aniket on 17-09-2017.
 */
@Dao
interface UserBmiDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUserBmiData(userBmi: UserBmi): Long

    @Query("SELECT * FROM UserBmi")
    fun getAll(): MutableList<UserBmi>

    /*Kotlin isn't preserving the names of the arguments properly
     to overcome this no arg0 is used until now
    */
    @Query("SELECT * FROM UserBmi WHERE id = :arg0")
    fun getBmiUserData(id: Int): UserBmi

    @Update
    fun updateUserBmiData(userBmi: UserBmi): Int

    @Delete
    fun deleteUserBmiData(userBmi: UserBmi): Int
}
