package com.example.recyclerview.database

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
data class Contact(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo("id")
    var id: Int = 0,

    @ColumnInfo("name")
    var name: String,

    @ColumnInfo("number")
    var number: String,

    @ColumnInfo("nim")
    var nim: String,

    @ColumnInfo("instagram")
    var instagram: String,
) : Parcelable
