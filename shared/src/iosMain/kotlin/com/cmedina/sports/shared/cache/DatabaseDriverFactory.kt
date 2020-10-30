package com.cmedina.sports.shared.cache

import com.cmedina.training.shared.cache.SportsDatabase
import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.drivers.native.NativeSqliteDriver

actual class DatabaseDriverFactory {
    actual fun createDriver(): SqlDriver {
        return NativeSqliteDriver(SportsDatabase.Schema, "sports.db")
    }
}