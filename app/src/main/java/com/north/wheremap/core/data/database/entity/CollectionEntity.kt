package com.north.wheremap.core.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.bson.types.ObjectId

@Entity(
    tableName = "collection",
)
data class CollectionEntity(
    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "description")
    val description: String?,

    @ColumnInfo(name = "city")
    val city: String?,

    @ColumnInfo(name = "is_private")
    val isPrivate: Boolean,

    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    val id: String = ObjectId().toHexString()
)
