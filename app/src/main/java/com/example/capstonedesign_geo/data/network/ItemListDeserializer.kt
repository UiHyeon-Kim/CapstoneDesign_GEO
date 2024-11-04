package com.example.capstonedesign_geo.data.network

import com.example.capstonedesign_geo.data.model.Item
import com.google.gson.JsonArray
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type

class ItemListDeserializer : JsonDeserializer<List<Item>> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): List<Item> {
        val items = mutableListOf<Item>()
        val itemsJson = json?.asJsonArray ?: JsonArray()

        if (json?.isJsonArray == true) {
            // item이 배열일 경우
            itemsJson.forEach {
                val item = context?.deserialize<Item>(it, Item::class.java)
                if (item != null) {
                    items.add(item)
                }
            }
        } else if (json?.isJsonObject == true) {
            // item이 객체일 경우
            val item = context?.deserialize<Item>(json, Item::class.java)
            if (item != null) {
                items.add(item)
            }
        }

        return items
    }
}
