package com.example.capstonedesign_geo.data.Query

import com.example.capstonedesign_geo.data.db.connectToDatabase
import java.sql.SQLException

fun searchPlace(
    title: String?,
    addr1: String?,
    addr2: String?
) {
    val connection = connectToDatabase()
    try {
        connection?.let {
            val query =
                StringBuilder("SELECT addr1, addr2, hours, mapx, mapy, tel, title, content, amenity, disabled FROM TourAPIEX WHERE 1=1")

            if (!title.isNullOrEmpty()) query.append(" AND title LIKE '%$title%'")
            if (!addr1.isNullOrEmpty()) query.append(" AND addr1 LIKE '%$addr1%'")
            if (!addr2.isNullOrEmpty()) query.append(" AND addr2 LIKE '%$addr2%'")

            val statement = it.createStatement()
            val resultSet = statement.executeQuery(query.toString())

            while (resultSet.next()) {
                val addr1 = resultSet.getString("addr1")
                val addr2 = resultSet.getString("addr2")
                val hours = resultSet.getString("hours")
                val mapx = resultSet.getString("mapx")
                val mapy = resultSet.getString("mapy")
                val tel = resultSet.getString("tel")
                val title = resultSet.getString("title")
                val content = resultSet.getString("content")
                val amenity = resultSet.getString("amenity")
                val disabled = resultSet.getString("disabled")
            }
        }
    } catch (e: SQLException) {
        e.printStackTrace()
    } finally {
        connection?.close()
    }
}