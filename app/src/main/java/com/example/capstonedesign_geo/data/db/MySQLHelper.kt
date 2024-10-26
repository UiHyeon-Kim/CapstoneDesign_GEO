package com.example.capstonedesign_geo.data.db

import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException

fun connectToDatabase(): Connection? {
    var connectoin: Connection? = null
    try {
        Class.forName("com.mysql.jdbc.Driver")

        val url = "jdbc:mysql://3.35.170.12:3306/Place"
        val user = "Geo"
        val password = "Gep2024"

        connectoin = DriverManager.getConnection(url, user, password)
    } catch (e: ClassNotFoundException) {
        e.printStackTrace()
    } catch (e: SQLException) {
        e.printStackTrace()
    }
    return connectoin
}