package com.example.kast.android.ui

import android.os.Bundle
import androidx.navigation.NavType
import com.example.kast.data.model.MovieView
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class MovieType : JsonNavType<MovieView>() {
    override fun fromJsonParse(value: String): MovieView {
        return Json.decodeFromString(value)
    }

    override fun MovieView.getJsonParse(): String {
        return Json.encodeToString(this)
    }

}

//class SerializationJsonNavType<T> : JsonNavType<T>() {
//    override fun fromJsonParse(value: String): T {
//        return Json.decodeFromString(value)
//    }
//
//    override fun T.getJsonParse(): String {
//        return Json.encodeToString(this)
//    }
//
//}

abstract class JsonNavType<T> : NavType<T>(isNullableAllowed = false) {
    abstract fun fromJsonParse(value: String): T
    abstract fun T.getJsonParse(): String

    override fun get(bundle: Bundle, key: String): T? =
        bundle.getString(key)?.let { parseValue(it) }

    override fun parseValue(value: String): T = fromJsonParse(value)

    override fun put(bundle: Bundle, key: String, value: T) {
        bundle.putString(key, value.getJsonParse())
    }
}