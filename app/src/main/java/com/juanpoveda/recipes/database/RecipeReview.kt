package com.juanpoveda.recipes.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

// ****Room s2: Add Entities and mark the Entity, PrimaryKey and Columns with the following annotations
@Entity(tableName = "recipe_review_table")
data class RecipeReview (
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L,
    @ColumnInfo(name = "recipe_name")
    var recipeName: String = "",
    @ColumnInfo(name = "image_url")
    var imageUrl: String = "",
    @ColumnInfo(name = "rating")
    var rating: Int = -1,
    @ColumnInfo(name = "real_spent_time")
    var realSpentTime: Int = -1,
    @ColumnInfo(name = "comments")
    var comments: String = ""
)