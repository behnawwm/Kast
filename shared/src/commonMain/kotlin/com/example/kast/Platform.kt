
package com.example.kast

import org.koin.core.annotation.Single

@Single
interface Platform {
    val name: String
}

expect fun getPlatform(): Platform