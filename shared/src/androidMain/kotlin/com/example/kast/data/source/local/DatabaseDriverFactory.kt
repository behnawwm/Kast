package com.example.kast.data.source.local

import android.content.Context
import com.example.kast.KastDb
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver

actual class DatabaseDriverFactory(private val context: Context) {
    actual fun createDriver(): SqlDriver {
        return AndroidSqliteDriver(KastDb.Schema, context, "kast.db")
    }
}
