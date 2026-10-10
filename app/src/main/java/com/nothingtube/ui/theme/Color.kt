package com.nothingtube.ui.theme

import androidx.compose.ui.graphics.Color

val NothingBlack = Color(0xFF000000)
val NothingWhite = Color(0xFFFFFFFF)
val NothingGray = Color(0xFF8A8A8A)
val NothingDarkGray = Color(0xFF1A1A1A)
val NothingRed = Color(0xFFFF0000)
val NothingDot = Color(0x33FFFFFF)

fun accentColorFor(name: String): Color = when (name) {
    "white" -> Color(0xFFFFFFFF)
    "orange" -> Color(0xFFFF6600)
    "purple" -> Color(0xFF9B59B6)
    "blue" -> Color(0xFF00A8FF)
    else -> Color(0xFFFF0000)
}
