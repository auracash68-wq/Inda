package com.example.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.ui.theme.PrimaryGreen
import com.example.ui.theme.PrimaryNavy
import com.example.ui.theme.PureWhite
import com.example.ui.theme.VerifiedBlue
import com.example.ui.theme.WhatsAppGreen
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun AshokaChakraIcon(
    modifier: Modifier = Modifier,
    size: Dp = 50.dp,
    color: Color = PrimaryNavy
) {
    Canvas(modifier = modifier.size(size)) {
        val center = Offset(this.size.width / 2f, this.size.height / 2f)
        val outerRadius = this.size.minDimension / 2f - 2.dp.toPx()
        val innerRingRadius = outerRadius * 0.84f
        val hubRadius = outerRadius * 0.17f

        // Outer white circle fill
        drawCircle(
            color = PureWhite,
            radius = outerRadius,
            center = center
        )

        // Outer navy ring
        drawCircle(
            color = color,
            radius = outerRadius,
            center = center,
            style = Stroke(width = 3.2.dp.toPx())
        )

        // Inner thin ring
        drawCircle(
            color = color,
            radius = innerRingRadius,
            center = center,
            style = Stroke(width = 1.2.dp.toPx())
        )

        // Center hub
        drawCircle(
            color = color,
            radius = hubRadius,
            center = center
        )
        drawCircle(
            color = PureWhite,
            radius = hubRadius * 0.42f,
            center = center
        )

        // 24 spokes + 24 rim dots between spokes
        val spokes = 24
        for (i in 0 until spokes) {
            val angleRad = (i * (360f / spokes)) * (PI / 180.0)
            val startX = center.x + (hubRadius * 0.9f) * cos(angleRad).toFloat()
            val startY = center.y + (hubRadius * 0.9f) * sin(angleRad).toFloat()
            val endX = center.x + innerRingRadius * cos(angleRad).toFloat()
            val endY = center.y + innerRingRadius * sin(angleRad).toFloat()

            drawLine(
                color = color,
                start = Offset(startX, startY),
                end = Offset(endX, endY),
                strokeWidth = 1.35.dp.toPx(),
                cap = StrokeCap.Round
            )

            // Rim dot halfway between spokes
            val midAngleRad = ((i + 0.5f) * (360f / spokes)) * (PI / 180.0)
            val dotX = center.x + (innerRingRadius * 0.98f) * cos(midAngleRad).toFloat()
            val dotY = center.y + (innerRingRadius * 0.98f) * sin(midAngleRad).toFloat()
            drawCircle(
                color = color,
                radius = 1.3.dp.toPx(),
                center = Offset(dotX, dotY)
            )
        }
    }
}

@Composable
fun GoldCoinStackIcon(
    modifier: Modifier = Modifier,
    size: Dp = 26.dp
) {
    Canvas(modifier = modifier.size(size)) {
        val w = this.size.width
        val h = this.size.height
        val coinWidth = w * 0.82f
        val coinHeight = h * 0.30f
        val thickness = h * 0.11f

        val goldEdgeBrush = Brush.horizontalGradient(
            listOf(Color(0xFFC88700), Color(0xFFF3B71B), Color(0xFFB97800))
        )
        val goldTopBrush = Brush.linearGradient(
            listOf(Color(0xFFFFE57F), Color(0xFFFFC107), Color(0xFFF59F00))
        )

        fun drawSingleCoin(leftX: Float, topY: Float) {
            // Side cylinder body
            drawRoundRect(
                brush = goldEdgeBrush,
                topLeft = Offset(leftX, topY + coinHeight * 0.35f),
                size = Size(coinWidth, coinHeight * 0.65f + thickness),
                cornerRadius = CornerRadius(coinWidth / 2f, coinHeight / 2f)
            )
            // Top face
            drawOval(
                brush = goldTopBrush,
                topLeft = Offset(leftX, topY),
                size = Size(coinWidth, coinHeight)
            )
            // Inner rim highlight
            drawOval(
                color = Color(0xFFFFF3B0),
                topLeft = Offset(leftX + coinWidth * 0.1f, topY + coinHeight * 0.14f),
                size = Size(coinWidth * 0.8f, coinHeight * 0.72f),
                style = Stroke(width = 1.dp.toPx())
            )
        }

        // Bottom, middle, and top coins slightly offset like the reference screenshot
        drawSingleCoin(w * 0.10f, h * 0.56f)
        drawSingleCoin(w * 0.06f, h * 0.34f)
        drawSingleCoin(w * 0.12f, h * 0.10f)
    }
}

@Composable
fun ShieldVerifiedIcon(
    modifier: Modifier = Modifier,
    size: Dp = 20.dp,
    shieldColor: Color = PrimaryGreen,
    checkColor: Color = PureWhite,
    outlinedShield: Boolean = false
) {
    Canvas(modifier = modifier.size(size)) {
        val w = this.size.width
        val h = this.size.height
        val shieldPath = Path().apply {
            moveTo(w * 0.5f, h * 0.06f)
            lineTo(w * 0.88f, h * 0.22f)
            lineTo(w * 0.88f, h * 0.50f)
            cubicTo(
                w * 0.88f, h * 0.74f,
                w * 0.68f, h * 0.88f,
                w * 0.50f, h * 0.95f
            )
            cubicTo(
                w * 0.32f, h * 0.88f,
                w * 0.12f, h * 0.74f,
                w * 0.12f, h * 0.50f
            )
            lineTo(w * 0.12f, h * 0.22f)
            close()
        }

        if (outlinedShield) {
            drawPath(
                path = shieldPath,
                color = shieldColor,
                style = Stroke(width = 2.dp.toPx(), join = StrokeJoin.Round)
            )
        } else {
            drawPath(
                path = shieldPath,
                color = shieldColor
            )
        }

        val checkPath = Path().apply {
            moveTo(w * 0.33f, h * 0.51f)
            lineTo(w * 0.46f, h * 0.64f)
            lineTo(w * 0.69f, h * 0.38f)
        }
        drawPath(
            path = checkPath,
            color = checkColor,
            style = Stroke(
                width = 2.1.dp.toPx(),
                cap = StrokeCap.Round,
                join = StrokeJoin.Round
            )
        )
    }
}

@Composable
fun WhatsAppCircleIcon(
    modifier: Modifier = Modifier,
    size: Dp = 22.dp
) {
    Canvas(modifier = modifier.size(size)) {
        val w = this.size.width
        val h = this.size.height
        // Outer green circle
        drawCircle(
            color = WhatsAppGreen,
            radius = w * 0.46f,
            center = Offset(w * 0.5f, h * 0.5f)
        )
        // White speech bubble outline
        drawCircle(
            color = PureWhite,
            radius = w * 0.37f,
            center = Offset(w * 0.5f, h * 0.5f),
            style = Stroke(width = 1.6.dp.toPx())
        )
        // Speech bubble tail at bottom-left
        val tailPath = Path().apply {
            moveTo(w * 0.22f, h * 0.68f)
            lineTo(w * 0.14f, h * 0.86f)
            lineTo(w * 0.34f, h * 0.78f)
            close()
        }
        drawPath(path = tailPath, color = PureWhite)
        // Handset curve inside
        val phonePath = Path().apply {
            moveTo(w * 0.38f, h * 0.36f)
            cubicTo(w * 0.34f, h * 0.48f, w * 0.52f, h * 0.65f, w * 0.64f, h * 0.61f)
        }
        drawPath(
            path = phonePath,
            color = PureWhite,
            style = Stroke(width = 2.3.dp.toPx(), cap = StrokeCap.Round)
        )
    }
}

@Composable
fun BlueGlobeIcon(
    modifier: Modifier = Modifier,
    size: Dp = 28.dp,
    color: Color = VerifiedBlue
) {
    Canvas(modifier = modifier.size(size)) {
        val w = this.size.width
        val h = this.size.height
        val r = (w.coerceAtMost(h) / 2f) * 0.86f
        val center = Offset(w / 2f, h / 2f)
        val stroke = 2.dp.toPx()

        drawCircle(color = color, radius = r, center = center, style = Stroke(width = stroke))
        drawOval(
            color = color,
            topLeft = Offset(center.x - r * 0.48f, center.y - r),
            size = Size(r * 0.96f, r * 2f),
            style = Stroke(width = stroke)
        )
        drawLine(
            color = color,
            start = Offset(center.x - r, center.y),
            end = Offset(center.x + r, center.y),
            strokeWidth = stroke
        )
        drawLine(
            color = color,
            start = Offset(center.x - r * 0.85f, center.y - r * 0.45f),
            end = Offset(center.x + r * 0.85f, center.y - r * 0.45f),
            strokeWidth = stroke * 0.85f
        )
        drawLine(
            color = color,
            start = Offset(center.x - r * 0.85f, center.y + r * 0.45f),
            end = Offset(center.x + r * 0.85f, center.y + r * 0.45f),
            strokeWidth = stroke * 0.85f
        )
    }
}

@Composable
fun NavyTicketIcon(
    modifier: Modifier = Modifier,
    size: Dp = 30.dp,
    color: Color = PrimaryNavy
) {
    Canvas(modifier = modifier.size(size)) {
        val w = this.size.width
        val h = this.size.height
        // Ticket body
        drawRoundRect(
            color = color,
            topLeft = Offset(w * 0.06f, h * 0.22f),
            size = Size(w * 0.88f, h * 0.56f),
            cornerRadius = CornerRadius(4.dp.toPx(), 4.dp.toPx())
        )
        // Left & right circular cutouts
        drawCircle(
            color = PureWhite,
            radius = h * 0.11f,
            center = Offset(w * 0.06f, h * 0.5f)
        )
        drawCircle(
            color = PureWhite,
            radius = h * 0.11f,
            center = Offset(w * 0.94f, h * 0.5f)
        )
        // Perforated vertical divider dots
        for (i in 0..3) {
            drawCircle(
                color = PureWhite,
                radius = 1.3.dp.toPx(),
                center = Offset(w * 0.36f, h * (0.30f + i * 0.13f))
            )
        }
        // Small white star/sparkle on right side of ticket
        val cx = w * 0.64f
        val cy = h * 0.50f
        val sr = h * 0.13f
        val starPath = Path().apply {
            moveTo(cx, cy - sr)
            lineTo(cx + sr * 0.35f, cy - sr * 0.35f)
            lineTo(cx + sr, cy)
            lineTo(cx + sr * 0.35f, cy + sr * 0.35f)
            lineTo(cx, cy + sr)
            lineTo(cx - sr * 0.35f, cy + sr * 0.35f)
            lineTo(cx - sr, cy)
            lineTo(cx - sr * 0.35f, cy - sr * 0.35f)
            close()
        }
        drawPath(path = starPath, color = PureWhite)
    }
}

@Composable
fun NavyHeadsetIcon(
    modifier: Modifier = Modifier,
    size: Dp = 28.dp,
    color: Color = PrimaryNavy
) {
    Canvas(modifier = modifier.size(size)) {
        val w = this.size.width
        val h = this.size.height
        val stroke = 2.6.dp.toPx()

        // Headband arc
        drawArc(
            color = color,
            startAngle = 180f,
            sweepAngle = 180f,
            useCenter = false,
            topLeft = Offset(w * 0.16f, h * 0.12f),
            size = Size(w * 0.68f, h * 0.65f),
            style = Stroke(width = stroke, cap = StrokeCap.Round)
        )
        // Left ear cup
        drawRoundRect(
            color = color,
            topLeft = Offset(w * 0.10f, h * 0.44f),
            size = Size(w * 0.16f, h * 0.28f),
            cornerRadius = CornerRadius(4.dp.toPx(), 4.dp.toPx())
        )
        // Right ear cup
        drawRoundRect(
            color = color,
            topLeft = Offset(w * 0.74f, h * 0.44f),
            size = Size(w * 0.16f, h * 0.28f),
            cornerRadius = CornerRadius(4.dp.toPx(), 4.dp.toPx())
        )
        // Microphone boom from right ear cup to bottom center
        val boomPath = Path().apply {
            moveTo(w * 0.82f, h * 0.70f)
            cubicTo(w * 0.82f, h * 0.88f, w * 0.66f, h * 0.90f, w * 0.52f, h * 0.90f)
        }
        drawPath(
            path = boomPath,
            color = color,
            style = Stroke(width = 2.2.dp.toPx(), cap = StrokeCap.Round)
        )
        drawRoundRect(
            color = color,
            topLeft = Offset(w * 0.42f, h * 0.84f),
            size = Size(w * 0.16f, h * 0.11f),
            cornerRadius = CornerRadius(3.dp.toPx(), 3.dp.toPx())
        )
    }
}

@Composable
fun BottomNavEarnIcon(
    color: Color,
    modifier: Modifier = Modifier,
    size: Dp = 26.dp
) {
    Canvas(modifier = modifier.size(size)) {
        val w = this.size.width
        val h = this.size.height
        // Back stack of coins on left-top and front coin stack on right-bottom matching screenshot
        val coinW = w * 0.64f
        val coinH = h * 0.24f

        for (i in 2 downTo 0) {
            val y = h * (0.14f + i * 0.20f)
            val x = if (i == 0) w * 0.16f else w * 0.10f
            drawOval(
                color = color,
                topLeft = Offset(x, y),
                size = Size(coinW, coinH)
            )
            drawRoundRect(
                color = color,
                topLeft = Offset(x, y + coinH * 0.4f),
                size = Size(coinW, coinH * 0.72f),
                cornerRadius = CornerRadius(coinW / 2f, coinH / 2f)
            )
            // White separator cut line between stacked coins
            drawArc(
                color = PureWhite,
                startAngle = 10f,
                sweepAngle = 160f,
                useCenter = false,
                topLeft = Offset(x - 1.dp.toPx(), y + coinH * 0.2f),
                size = Size(coinW + 2.dp.toPx(), coinH * 0.85f),
                style = Stroke(width = 1.4.dp.toPx())
            )
        }
    }
}

@Composable
fun BottomNavTasksIcon(
    color: Color,
    modifier: Modifier = Modifier,
    size: Dp = 26.dp
) {
    Canvas(modifier = modifier.size(size)) {
        val w = this.size.width
        val h = this.size.height
        // Top clipboard tab bar
        drawRoundRect(
            color = color,
            topLeft = Offset(w * 0.28f, h * 0.06f),
            size = Size(w * 0.44f, h * 0.10f),
            cornerRadius = CornerRadius(2.dp.toPx(), 2.dp.toPx())
        )
        // Main rounded square body
        drawRoundRect(
            color = color,
            topLeft = Offset(w * 0.12f, h * 0.18f),
            size = Size(w * 0.76f, h * 0.74f),
            cornerRadius = CornerRadius(5.dp.toPx(), 5.dp.toPx())
        )
        // White checkmark inside
        val checkPath = Path().apply {
            moveTo(w * 0.30f, h * 0.55f)
            lineTo(w * 0.45f, h * 0.70f)
            lineTo(w * 0.72f, h * 0.41f)
        }
        drawPath(
            path = checkPath,
            color = PureWhite,
            style = Stroke(width = 2.6.dp.toPx(), cap = StrokeCap.Round, join = StrokeJoin.Round)
        )
    }
}

@Composable
fun BottomNavWalletIcon(
    color: Color,
    modifier: Modifier = Modifier,
    size: Dp = 26.dp
) {
    Canvas(modifier = modifier.size(size)) {
        val w = this.size.width
        val h = this.size.height
        // Top folded flap
        val flapPath = Path().apply {
            moveTo(w * 0.16f, h * 0.28f)
            lineTo(w * 0.72f, h * 0.12f)
            lineTo(w * 0.78f, h * 0.28f)
            close()
        }
        drawPath(path = flapPath, color = color)

        // Main wallet rectangle
        drawRoundRect(
            color = color,
            topLeft = Offset(w * 0.10f, h * 0.28f),
            size = Size(w * 0.80f, h * 0.62f),
            cornerRadius = CornerRadius(5.dp.toPx(), 5.dp.toPx())
        )

        // Right clasp cutout in white + clasp dot
        drawRoundRect(
            color = PureWhite,
            topLeft = Offset(w * 0.62f, h * 0.48f),
            size = Size(w * 0.30f, h * 0.24f),
            cornerRadius = CornerRadius(6.dp.toPx(), 6.dp.toPx()),
            style = Stroke(width = 1.6.dp.toPx())
        )
        drawCircle(
            color = PureWhite,
            radius = 2.2.dp.toPx(),
            center = Offset(w * 0.73f, h * 0.60f)
        )
    }
}

@Composable
fun BottomNavProfileIcon(
    color: Color,
    modifier: Modifier = Modifier,
    size: Dp = 26.dp
) {
    Canvas(modifier = modifier.size(size)) {
        val w = this.size.width
        val h = this.size.height
        // Head circle
        drawCircle(
            color = color,
            radius = w * 0.21f,
            center = Offset(w * 0.50f, h * 0.30f)
        )
        // Shoulders dome
        val bodyPath = Path().apply {
            addRoundRect(
                RoundRect(
                    rect = Rect(
                        left = w * 0.15f,
                        top = h * 0.56f,
                        right = w * 0.85f,
                        bottom = h * 0.90f
                    ),
                    topLeft = CornerRadius(w * 0.35f, h * 0.30f),
                    topRight = CornerRadius(w * 0.35f, h * 0.30f),
                    bottomLeft = CornerRadius(4.dp.toPx(), 4.dp.toPx()),
                    bottomRight = CornerRadius(4.dp.toPx(), 4.dp.toPx())
                )
            )
        }
        drawPath(path = bodyPath, color = color)
    }
}
