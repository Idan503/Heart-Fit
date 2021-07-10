package com.idan_koren_israeli.heartfit.db.room_db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "exercise_summary")
data class ExerciseSummary(
    @PrimaryKey(autoGenerate = true) val id: Long,
    var timestamp: Long,
    var userId : String,
    var heartsCollected : Int,
    var caloriesBurned : Int,
    var totalTime : String,
    var difficulty : String,
    var muscles : String
)

