package com.example.capstonedesign_geo.data.Query

import com.example.capstonedesign_geo.data.db.connectToDatabase
import java.sql.SQLException

fun placeFetchData() {
    val connection = connectToDatabase()
    try {
        connection?. let {
            val statement = it.createStatement() // 쿼리를 실행하는 객체 생성
            val resultSet = statement.executeQuery("SELECT addr1, addr2, hours, mapx, mapy, tel, title, content, amenity, disabled FROM TourAPIEX")

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