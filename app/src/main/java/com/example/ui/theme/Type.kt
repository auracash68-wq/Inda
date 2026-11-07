package com.example.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

val CashIndiaFontFamily = FontFamily.SansSerif // Roboto on Android

val Typography = Typography(
    headlineLarge = TextStyle(
        fontFamily = CashIndiaFontFamily,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 27.sp,
        lineHeight = 32.sp,
        color = PrimaryNavy
    ),
    headlineMedium = TextStyle(
        fontFamily = CashIndiaFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 21.sp,
        lineHeight = 26.sp,
        color = PrimaryNavy
    ),
    titleLarge = TextStyle(
        fontFamily = CashIndiaFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp,
        lineHeight = 23.sp,
        color = PrimaryNavy
    ),
    titleMedium = TextStyle(
        fontFamily = CashIndiaFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp,
        lineHeight = 20.sp,
        color = PrimaryNavy
    ),
    bodyLarge = TextStyle(
        fontFamily = CashIndiaFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        lineHeight = 19.sp,
        color = SecondaryText
    ),
    bodyMedium = TextStyle(
        fontFamily = CashIndiaFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 13.sp,
        lineHeight = 17.sp,
        color = SecondaryText
    ),
    labelLarge = TextStyle(
        fontFamily = CashIndiaFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 15.sp,
        lineHeight = 19.sp,
        color = PureWhite
    ),
    labelMedium = TextStyle(
        fontFamily = CashIndiaFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 12.sp,
        lineHeight = 15.sp,
        color = PrimaryNavy
    )
)
