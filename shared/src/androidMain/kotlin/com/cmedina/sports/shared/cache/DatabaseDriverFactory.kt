package com.cmedina.sports.shared.cache

import android.content.Context
import com.cmedina.training.shared.cache.SportsDatabase
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver

actual class DatabaseDriverFactory(private val context: Context) {
    actual fun createDriver(): SqlDriver {
        return AndroidSqliteDriver(SportsDatabase.Schema, context, "sports.db")
    }
}