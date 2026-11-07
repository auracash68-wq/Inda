package com.example.ui.theme

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

// Primary Brand Colors (Phase 2 Exact Color Design System)
val PrimaryNavy = Color(0xFF07145F)
val DarkNavy = Color(0xFF101B65)
val SecondaryNavy = Color(0xFF18266F)
val SaffronOrange = Color(0xFFFF7200)
val DeepOrange = Color(0xFFF45B00)
val PrimaryGreen = Color(0xFF078A12)
val DeepGreen = Color(0xFF087B12)
val PureWhite = Color(0xFFFFFFFF)

// Background & Border Colors
val MainScreenBackground = Color(0xFFF5F8FC)
val CardBackground = Color(0xFFFFFFFF)
val LightSurface = Color(0xFFF8FAFD)
val DividerColor = Color(0xFFD9E0EA)
val CardBorderColor = Color(0xFFE1E7EF)
val UnselectedChipBg = Color(0xFFE5ECF6)

// Status & Accent Colors
val SuccessGreen = Color(0xFF078A12)
val PendingOrange = Color(0xFFF47A00)
val SecondaryText = Color(0xFF26336C)
val MutedText = Color(0xFF6E7892)

// Specialized UI Surface Accents from Reference Screenshots
val PendingCapsuleBg = Color(0xFFFFF2D6)
val PendingCapsuleBorder = Color(0xFFF5A623)
val PendingCapsuleText = Color(0xFFD95900)

val SuccessCapsuleLightBg = Color(0xFFA6F0B2)
val SuccessCapsuleDarkText = Color(0xFF064E10)

val GoldCapsuleStart = Color(0xFFFAD36B)
val GoldCapsuleEnd = Color(0xFFF4BC3C)

val InfoBannerBlueBg = Color(0xFFDCE8FC)
val VerifiedBlue = Color(0xFF0066FF)
val WhatsAppGreen = Color(0xFF25D366)
val ReferralYellow = Color(0xFFFFEA00)

// Reusable Gradients
val OrangeButtonGradient = Brush.verticalGradient(
    colors = listOf(Color(0xFFFF8500), Color(0xFFF45B00))
)

val OrangeHorizontalGradient = Brush.horizontalGradient(
    colors = listOf(Color(0xFFFF7A00), Color(0xFFF45B00))
)

val GreenButtonGradient = Brush.verticalGradient(
    colors = listOf(Color(0xFF0B9B17), Color(0xFF077E11))
)

val TricolorTopBarBrush = Brush.horizontalGradient(
    0.0f to SaffronOrange,
    0.46f to SaffronOrange,
    0.50f to Color(0xFFEAF2EC),
    0.54f to PrimaryGreen,
    1.0f to PrimaryGreen
)
