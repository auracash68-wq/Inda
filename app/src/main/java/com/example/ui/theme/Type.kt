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
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp,
        lineHeight = 29.sp,
        color = PrimaryNavy
    ),
    headlineMedium = TextStyle(
        fontFamily = CashIndiaFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 19.sp,
        lineHeight = 24.sp,
        color = PrimaryNavy
    ),
    titleLarge = TextStyle(
        fontFamily = CashIndiaFontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 16.sp,
        lineHeight = 21.sp,
        color = PrimaryNavy
    ),
    titleMedium = TextStyle(
        fontFamily = CashIndiaFontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 15.sp,
        lineHeight = 20.sp,
        color = PrimaryNavy
    ),
    bodyLarge = TextStyle(
        fontFamily = CashIndiaFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 13.5.sp,
        lineHeight = 18.sp,
        color = SecondaryText
    ),
    bodyMedium = TextStyle(
        fontFamily = CashIndiaFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 12.5.sp,
        lineHeight = 17.sp,
        color = SecondaryText
    ),
    labelLarge = TextStyle(
        fontFamily = CashIndiaFontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 14.sp,
        lineHeight = 18.sp,
        color = PureWhite
    ),
    labelMedium = TextStyle(
        fontFamily = CashIndiaFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 12.5.sp,
        lineHeight = 16.sp,
        color = PrimaryNavy
    )
)
