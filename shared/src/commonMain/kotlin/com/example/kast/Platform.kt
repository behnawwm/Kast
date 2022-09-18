package com.example.kast

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform