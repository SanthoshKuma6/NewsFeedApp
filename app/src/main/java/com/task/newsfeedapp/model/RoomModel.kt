package com.task.newsfeedapp.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize


@Parcelize
@Entity(tableName = "articles")
data class RoomModel(
    var webUrl: String? = null,
    var abstract: String? = null,
    var snippet: String? = null,
    var leadParagraph: String? = null,
    var source: String? = null,
    var pubDate: String? = null,
    var documentType: String? = null,
    var newsDesk: String? = null,
    var sectionName: String? = null,
    var typeOfMaterial: String? = null,
    var uri: String? = null,
    var wordCount: Int? = null,

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
):Parcelable
