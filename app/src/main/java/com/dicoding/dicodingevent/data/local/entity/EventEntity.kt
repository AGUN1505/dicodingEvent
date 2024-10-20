package com.dicoding.dicodingevent.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "event")
class EventEntity(
    @PrimaryKey(autoGenerate = false)
    @field:ColumnInfo(name = "id")
    var id: String,

    @field:ColumnInfo(name = "name")
    var name: String,

    @field:ColumnInfo(name = "imageLogo")
    var imageLogo: String?
)