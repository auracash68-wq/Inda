package com.example.ui.theme

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

// Refined Brand Colors (Indian Tricolor Fintech Palette)
val PrimaryNavy = Color(0xFF000080)
val DarkNavy = Color(0xFF0A1172)
val SecondaryNavy = Color(0xFF1E293B)
val SaffronOrange = Color(0xFFFF9933)
val DeepOrange = Color(0xFFF07F13)
val PrimaryGreen = Color(0xFF138808)
val DeepGreen = Color(0xFF0F7306)
val PureWhite = Color(0xFFFFFFFF)

// Background & Border Colors
val MainScreenBackground = Color(0xFFF8FAFC)
val CardBackground = Color(0xFFFFFFFF)
val LightSurface = Color(0xFFF1F5F9)
val DividerColor = Color(0xFFE2E8F0)
val CardBorderColor = Color(0xFFE2E8F0)
val UnselectedChipBg = Color(0xFFEFF4F9)

// Status & Accent Colors
val SuccessGreen = Color(0xFF138808)
val PendingOrange = Color(0xFFE67E00)
val SecondaryText = Color(0xFF475569)
val MutedText = Color(0xFF64748B)

// Specialized UI Surface Accents
val PendingCapsuleBg = Color(0xFFFFF7ED)
val PendingCapsuleBorder = Color(0xFFFDBA74)
val PendingCapsuleText = Color(0xFFC2410C)

val SuccessCapsuleLightBg = Color(0xFFDCFCE7)
val SuccessCapsuleDarkText = Color(0xFF15803D)

val GoldCapsuleStart = Color(0xFFFDE68A)
val GoldCapsuleEnd = Color(0xFFFCD34D)

val InfoBannerBlueBg = Color(0xFFEFF6FF)
val VerifiedBlue = Color(0xFF0284C7)
val WhatsAppGreen = Color(0xFF25D366)
val ReferralYellow = Color(0xFFFFF066)

// Subtle, Clean Gradients
val OrangeButtonGradient = Brush.verticalGradient(
    colors = listOf(Color(0xFFFF9933), Color(0xFFF07F13))
)

val OrangeHorizontalGradient = Brush.horizontalGradient(
    colors = listOf(Color(0xFFFF9933), Color(0xFFF07F13))
)

val GreenButtonGradient = Brush.verticalGradient(
    colors = listOf(Color(0xFF169C0A), Color(0xFF138808))
)

val TricolorTopBarBrush = Brush.horizontalGradient(
    0.0f to SaffronOrange,
    0.45f to SaffronOrange,
    0.50f to Color(0xFFF1F5F9),
    0.55f to PrimaryGreen,
    1.0f to PrimaryGreen
)
