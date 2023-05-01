package com.ekachandra.storyapplite

import com.ekachandra.storyapplite.data.remote.response.ListStoryItem

object DataDummy {

    fun generateDummyStoryResponse(): List<ListStoryItem> {
        val items: MutableList<ListStoryItem> = arrayListOf()
        for (i in 0..100) {
            val story = ListStoryItem(
                id = i.toString(),
                photoUrl = "photoUrl + $i",
                createdAt = "createAt = $i",
                name = "name + $i",
                description = "description + $i",
                lon = i.toFloat(),
                lat = i.toFloat(),
            )
            items.add(story)
        }
        return items
    }
}