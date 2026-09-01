package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.RadioButtonChecked
import androidx.compose.material.icons.filled.RadioButtonUnchecked
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.SignalCellularAlt
import androidx.compose.material.icons.filled.Sync
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.outlined.Shield
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.AuditStep
import com.example.data.model.SosDeliveryState
import com.example.data.model.UserRole
import com.example.ui.theme.CoralRed
import com.example.ui.theme.CoralRedGlow
import com.example.ui.theme.ElectricCyan
import com.example.ui.theme.ElectricCyanGlow
import com.example.ui.theme.InkBlueBorder
import com.example.ui.theme.InkBlueDivider
import com.example.ui.theme.InkBlueElevated
import com.example.ui.theme.InkBlueSurface
import com.example.ui.theme.MidnightNavy
import com.example.ui.theme.NodeGray
import com.example.ui.theme.SignalGreen
import com.example.ui.theme.TextDarkOnAccent
import com.example.ui.theme.TextMutedSlate
import com.example.ui.theme.TextOffWhite
import com.example.ui.theme.WarmAmber
import com.example.ui.theme.WarmAmberMuted

@Composable
fun AppHeader(
    role: UserRole,
    connectivityText: String = "Mesh Available",
    isConnectivityActive: Boolean = true,
    onRoleClick: (() -> Unit)? = null
) {
    Surface(
        color = InkBlueSurface,
        modifier = Modifier
            .fillMaxWidth()
            .border(
                width = 1.dp,
                color = InkBlueBorder,
                shape = RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp)
            )
            .clip(RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 14.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Logo & App Name
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.testTag("app_header_logo")
                ) {
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .background(
                                brush = Brush.linearGradient(
                                    colors = listOf(ElectricCyan, WarmAmber)
                                ),
                                shape = RoundedCornerShape(10.dp)
                            )
                            .padding(2.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Box(
                            modifier = Modifier
                                .size(32.dp)
                                .background(MidnightNavy, shape = RoundedCornerShape(8.dp)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.SignalCellularAlt,
                                contentDescription = "Disaster Mesh Logo",
                                tint = ElectricCyan,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Text(
                            text = "DISASTER MESH",
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 10.sp,
                            letterSpacing = 1.5.sp,
                            color = TextMutedSlate
                        )
                        Text(
                            text = "Offline Emergency Relay",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = TextOffWhite
                        )
                    }
                }

                // Connectivity Chip
                ConnectivityStatusChip(
                    text = connectivityText,
                    isActive = isConnectivityActive
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Role Badge Bar
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "Current Role: ",
                        style = MaterialTheme.typography.labelMedium,
                        color = TextMutedSlate
                    )
                    RoleBadge(
                        role = role,
                        modifier = Modifier.clickable(enabled = onRoleClick != null) {
                            onRoleClick?.invoke()
                        }
                    )
                }

                if (onRoleClick != null) {
                    Text(
                        text = "Switch",
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.SemiBold,
                        color = WarmAmber,
                        modifier = Modifier
                            .clickable { onRoleClick() }
                            .padding(4.dp)
                            .testTag("switch_role_header_btn")
                    )
                }
            }
        }
    }
}

@Composable
fun RoleBadge(
    role: UserRole,
    modifier: Modifier = Modifier
) {
    val (bgColor, textColor, borderColor) = when (role) {
        UserRole.CITIZEN -> Triple(InkBlueElevated, ElectricCyan, ElectricCyan.copy(alpha = 0.5f))
        UserRole.VOLUNTEER -> Triple(InkBlueElevated, WarmAmber, WarmAmber.copy(alpha = 0.5f))
        UserRole.RESPONDER -> Triple(InkBlueElevated, CoralRed, CoralRed.copy(alpha = 0.5f))
    }

    Surface(
        color = bgColor,
        shape = RoundedCornerShape(12.dp),
        modifier = modifier
            .border(1.dp, borderColor, RoundedCornerShape(12.dp))
            .testTag("role_badge_${role.name.lowercase()}")
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(6.dp)
                    .background(textColor, CircleShape)
            )
            Spacer(modifier = Modifier.width(5.dp))
            Text(
                text = role.badgeText,
                style = MaterialTheme.typography.labelSmall,
                fontWeight = FontWeight.SemiBold,
                color = textColor
            )
            if (role == UserRole.RESPONDER) {
                Spacer(modifier = Modifier.width(3.dp))
                Icon(
                    imageVector = Icons.Default.Lock,
                    contentDescription = "Verified",
                    tint = CoralRed,
                    modifier = Modifier.size(10.dp)
                )
            }
        }
    }
}

@Composable
fun ConnectivityStatusChip(
    text: String,
    isActive: Boolean = true,
    modifier: Modifier = Modifier
) {
    val chipColor = if (text.contains("Waiting", ignoreCase = true)) WarmAmber else ElectricCyan
    val pulseTransition = rememberInfiniteTransition(label = "pulse")
    val alpha by pulseTransition.animateFloat(
        initialValue = 0.4f,
        targetValue = 1.0f,
        animationSpec = infiniteRepeatable(
            animation = tween(1200, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulseAlpha"
    )

    Surface(
        color = InkBlueElevated,
        shape = RoundedCornerShape(20.dp),
        modifier = modifier
            .border(1.dp, chipColor.copy(alpha = 0.4f), RoundedCornerShape(20.dp))
            .testTag("connectivity_chip")
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(8.dp)
                    .background(
                        color = if (isActive) chipColor.copy(alpha = alpha) else TextMutedSlate,
                        shape = CircleShape
                    )
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = text,
                style = MaterialTheme.typography.labelSmall,
                fontWeight = FontWeight.SemiBold,
                color = if (isActive) TextOffWhite else TextMutedSlate
            )
        }
    }
}

@Composable
fun DisclaimerCard(
    modifier: Modifier = Modifier
) {
    Card(
        colors = CardDefaults.cardColors(containerColor = InkBlueElevated.copy(alpha = 0.7f)),
        shape = RoundedCornerShape(12.dp),
        modifier = modifier
            .fillMaxWidth()
            .border(1.dp, InkBlueBorder, RoundedCornerShape(12.dp))
            .testTag("disclaimer_card")
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.Top
        ) {
            Icon(
                imageVector = Icons.Default.Info,
                contentDescription = "Important Protocol Notice",
                tint = WarmAmber,
                modifier = Modifier
                    .size(18.dp)
                    .padding(top = 2.dp)
            )
            Spacer(modifier = Modifier.width(10.dp))
            Column {
                Text(
                    text = "Emergency Mesh Protocol Notice",
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.Bold,
                    color = WarmAmber
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = "Disaster Mesh operates via peer-to-peer radio hops. It does not replace 112 emergency services, satellite phones, or official disaster networks. Delivery depends on nearby participating devices.",
                    style = MaterialTheme.typography.bodySmall,
                    color = TextMutedSlate,
                    lineHeight = 15.sp
                )
            }
        }
    }
}

@Composable
fun DarkContourMap(
    locationName: String = "Riverside Road, Pune",
    accuracyText: String = "Approx. 24 m accuracy",
    modifier: Modifier = Modifier,
    height: Dp = 160.dp,
    showCoarseOnly: Boolean = false
) {
    val infiniteTransition = rememberInfiniteTransition(label = "map_radar")
    val radarRadius by infiniteTransition.animateFloat(
        initialValue = 0.2f,
        targetValue = 1.0f,
        animationSpec = infiniteRepeatable(
            animation = tween(2400, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "radar"
    )

    Card(
        colors = CardDefaults.cardColors(containerColor = MidnightNavy),
        shape = RoundedCornerShape(14.dp),
        modifier = modifier
            .fillMaxWidth()
            .height(height)
            .border(1.dp, InkBlueBorder, RoundedCornerShape(14.dp))
            .clip(RoundedCornerShape(14.dp))
            .testTag("dark_offline_map")
    ) {
        Box(modifier = Modifier.fillMaxWidth()) {
            // Custom Canvas drawing contour lines, grid, and coordinates
            Canvas(modifier = Modifier.matchParentSize()) {
                val w = size.width
                val h = size.height

                // Draw background grid lines
                val gridSpacing = 36.dp.toPx()
                var x = 0f
                while (x < w) {
                    drawLine(
                        color = InkBlueBorder.copy(alpha = 0.25f),
                        start = Offset(x, 0f),
                        end = Offset(x, h),
                        strokeWidth = 1f
                    )
                    x += gridSpacing
                }
                var y = 0f
                while (y < h) {
                    drawLine(
                        color = InkBlueBorder.copy(alpha = 0.25f),
                        start = Offset(0f, y),
                        end = Offset(w, y),
                        strokeWidth = 1f
                    )
                    y += gridSpacing
                }

                // Draw Topographic Contour Paths
                val contourPath1 = Path().apply {
                    moveTo(0f, h * 0.35f)
                    cubicTo(w * 0.25f, h * 0.2f, w * 0.6f, h * 0.5f, w, h * 0.3f)
                }
                val contourPath2 = Path().apply {
                    moveTo(0f, h * 0.65f)
                    cubicTo(w * 0.3f, h * 0.85f, w * 0.7f, h * 0.55f, w, h * 0.75f)
                }
                val roadPath = Path().apply {
                    moveTo(0f, h * 0.8f)
                    lineTo(w * 0.45f, h * 0.45f)
                    lineTo(w, h * 0.2f)
                }

                drawPath(
                    path = contourPath1,
                    color = ElectricCyan.copy(alpha = 0.15f),
                    style = Stroke(width = 1.5f, pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f))
                )
                drawPath(
                    path = contourPath2,
                    color = ElectricCyan.copy(alpha = 0.15f),
                    style = Stroke(width = 1.5f, pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f))
                )
                drawPath(
                    path = roadPath,
                    color = WarmAmber.copy(alpha = 0.35f),
                    style = Stroke(width = 3.5f)
                )

                // Center Pin Position
                val pinCenter = Offset(w * 0.52f, h * 0.5f)

                if (showCoarseOnly) {
                    // Coarse radius zone for volunteer privacy protection
                    drawCircle(
                        color = WarmAmber.copy(alpha = 0.15f),
                        radius = 48.dp.toPx(),
                        center = pinCenter
                    )
                    drawCircle(
                        color = WarmAmber.copy(alpha = 0.5f),
                        radius = 48.dp.toPx(),
                        center = pinCenter,
                        style = Stroke(width = 2f, pathEffect = PathEffect.dashPathEffect(floatArrayOf(8f, 6f), 0f))
                    )
                } else {
                    // Accuracy Radar Pulse
                    val maxRadar = 50.dp.toPx()
                    drawCircle(
                        color = ElectricCyan.copy(alpha = (1f - radarRadius) * 0.35f),
                        radius = maxRadar * radarRadius,
                        center = pinCenter
                    )
                    drawCircle(
                        color = ElectricCyan.copy(alpha = (1f - radarRadius) * 0.6f),
                        radius = maxRadar * radarRadius,
                        center = pinCenter,
                        style = Stroke(width = 1.5f)
                    )
                }
            }

            // Map Pin Overlay
            Box(
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(bottom = 8.dp)
            ) {
                if (showCoarseOnly) {
                    Surface(
                        color = WarmAmber.copy(alpha = 0.2f),
                        shape = CircleShape,
                        modifier = Modifier
                            .size(36.dp)
                            .border(1.5.dp, WarmAmber, CircleShape),
                        contentColor = WarmAmber
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(
                                imageVector = Icons.Default.LocationOn,
                                contentDescription = "Approximate Area",
                                tint = WarmAmber,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }
                } else {
                    Surface(
                        color = CoralRed.copy(alpha = 0.25f),
                        shape = CircleShape,
                        modifier = Modifier
                            .size(36.dp)
                            .border(2.dp, ElectricCyan, CircleShape),
                        contentColor = ElectricCyan
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(
                                imageVector = Icons.Default.LocationOn,
                                contentDescription = "Location Pin",
                                tint = ElectricCyan,
                                modifier = Modifier.size(22.dp)
                            )
                        }
                    }
                }
            }

            // Top-Right "Offline Vector Map" indicator
            Surface(
                color = InkBlueSurface.copy(alpha = 0.85f),
                shape = RoundedCornerShape(bottomStart = 8.dp),
                modifier = Modifier.align(Alignment.TopEnd)
            ) {
                Text(
                    text = "OFFLINE MAP",
                    style = MaterialTheme.typography.labelSmall,
                    color = ElectricCyan,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                )
            }

            // Bottom Location Label Bar
            Surface(
                color = InkBlueSurface.copy(alpha = 0.92f),
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .border(1.dp, InkBlueBorder, RoundedCornerShape(bottomStart = 14.dp, bottomEnd = 14.dp))
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.LocationOn,
                            contentDescription = null,
                            tint = if (showCoarseOnly) WarmAmber else ElectricCyan,
                            modifier = Modifier.size(14.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = locationName,
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.SemiBold,
                            color = TextOffWhite
                        )
                    }
                    Text(
                        text = accuracyText,
                        style = MaterialTheme.typography.labelSmall,
                        color = TextMutedSlate
                    )
                }
            }
        }
    }
}

@Composable
fun MeshNetworkDiagram(
    deliveryState: SosDeliveryState,
    modifier: Modifier = Modifier
) {
    Card(
        colors = CardDefaults.cardColors(containerColor = InkBlueSurface),
        shape = RoundedCornerShape(14.dp),
        modifier = modifier
            .fillMaxWidth()
            .border(1.dp, InkBlueBorder, RoundedCornerShape(14.dp))
            .testTag("mesh_network_diagram")
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Mesh Relay Path",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = TextOffWhite
                )
                val statusText = when (deliveryState) {
                    SosDeliveryState.SAVED_LOCALLY -> "Searching Node"
                    SosDeliveryState.RELAY_ACCEPTED -> "Relayed (1 Hop)"
                    SosDeliveryState.GATEWAY_RECEIVED -> "Gateway Connected"
                    SosDeliveryState.RESPONDER_ACKNOWLEDGED -> "Acknowledged"
                }
                Surface(
                    color = InkBlueElevated,
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.border(1.dp, ElectricCyan.copy(alpha = 0.4f), RoundedCornerShape(12.dp))
                ) {
                    Text(
                        text = statusText,
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.SemiBold,
                        color = ElectricCyan,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            // 3 Node visual representation: Your Phone -> Nearby Volunteer Devices -> Responder Gateway
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Node 1: Your Phone
                MeshNodeItem(
                    title = "Your Phone",
                    subtitle = "Origin (Encrypted)",
                    isActive = true,
                    isCompleted = true,
                    highlightColor = ElectricCyan,
                    modifier = Modifier.weight(1f)
                )

                // Connector 1
                MeshPathConnector(
                    isActive = deliveryState.stepIndex >= 1,
                    isPulse = deliveryState == SosDeliveryState.SAVED_LOCALLY,
                    modifier = Modifier.width(36.dp)
                )

                // Node 2: Nearby Volunteer Devices
                MeshNodeItem(
                    title = "Nearby Devices",
                    subtitle = if (deliveryState.stepIndex >= 1) "Relay Node B4" else "No Relay Found",
                    isActive = deliveryState.stepIndex >= 1,
                    isCompleted = deliveryState.stepIndex >= 1,
                    highlightColor = if (deliveryState.stepIndex >= 1) WarmAmber else NodeGray,
                    modifier = Modifier.weight(1.1f)
                )

                // Connector 2
                MeshPathConnector(
                    isActive = deliveryState.stepIndex >= 2,
                    isPulse = deliveryState == SosDeliveryState.RELAY_ACCEPTED,
                    modifier = Modifier.width(36.dp)
                )

                // Node 3: Responder Gateway
                MeshNodeItem(
                    title = "Gateway",
                    subtitle = if (deliveryState.stepIndex >= 2) "Station Portal" else "Muted Gateway",
                    isActive = deliveryState.stepIndex >= 2,
                    isCompleted = deliveryState.stepIndex >= 3,
                    highlightColor = if (deliveryState.stepIndex >= 2) ElectricCyan else NodeGray,
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Composable
fun MeshNodeItem(
    title: String,
    subtitle: String,
    isActive: Boolean,
    isCompleted: Boolean,
    highlightColor: Color,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Surface(
            color = if (isActive) highlightColor.copy(alpha = 0.18f) else InkBlueElevated,
            shape = CircleShape,
            modifier = Modifier
                .size(42.dp)
                .border(
                    width = if (isActive) 2.dp else 1.dp,
                    color = if (isActive) highlightColor else InkBlueBorder,
                    shape = CircleShape
                )
        ) {
            Box(contentAlignment = Alignment.Center) {
                Icon(
                    imageVector = if (isCompleted) Icons.Default.CheckCircle else Icons.Default.RadioButtonChecked,
                    contentDescription = title,
                    tint = if (isActive) highlightColor else TextMutedSlate,
                    modifier = Modifier.size(20.dp)
                )
            }
        }
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            text = title,
            style = MaterialTheme.typography.labelSmall,
            fontWeight = FontWeight.Bold,
            color = if (isActive) TextOffWhite else TextMutedSlate
        )
        Text(
            text = subtitle,
            style = MaterialTheme.typography.labelSmall,
            fontSize = 9.sp,
            color = TextMutedSlate
        )
    }
}

@Composable
fun MeshPathConnector(
    isActive: Boolean,
    isPulse: Boolean,
    modifier: Modifier = Modifier
) {
    val pulseTransition = rememberInfiniteTransition(label = "path_pulse")
    val alpha by pulseTransition.animateFloat(
        initialValue = 0.3f,
        targetValue = 1.0f,
        animationSpec = infiniteRepeatable(
            animation = tween(800, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pathAlpha"
    )

    Box(
        modifier = modifier.height(18.dp),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.fillMaxWidth().height(4.dp)) {
            val color = when {
                isActive -> ElectricCyan
                isPulse -> WarmAmber.copy(alpha = alpha)
                else -> InkBlueBorder
            }
            drawLine(
                color = color,
                start = Offset(0f, size.height / 2),
                end = Offset(size.width, size.height / 2),
                strokeWidth = if (isActive || isPulse) 3f else 1.5f,
                pathEffect = if (!isActive && !isPulse) PathEffect.dashPathEffect(floatArrayOf(6f, 6f), 0f) else null
            )
        }
    }
}

@Composable
fun VerticalEvidenceTimeline(
    currentState: SosDeliveryState,
    auditSteps: List<AuditStep>,
    modifier: Modifier = Modifier
) {
    Card(
        colors = CardDefaults.cardColors(containerColor = InkBlueElevated.copy(alpha = 0.5f)),
        shape = RoundedCornerShape(12.dp),
        modifier = modifier
            .fillMaxWidth()
            .border(1.dp, InkBlueBorder, RoundedCornerShape(12.dp))
            .testTag("vertical_evidence_timeline")
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Delivery Evidence Timeline",
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.Bold,
                    color = TextOffWhite
                )
                Text(
                    text = "Cryptographic Proof",
                    style = MaterialTheme.typography.labelSmall,
                    fontSize = 11.sp,
                    color = ElectricCyan
                )
            }

            Spacer(modifier = Modifier.height(14.dp))

            val stages = listOf(
                Pair(SosDeliveryState.SAVED_LOCALLY, "SOS Saved Locally"),
                Pair(SosDeliveryState.RELAY_ACCEPTED, "Relay Accepted"),
                Pair(SosDeliveryState.GATEWAY_RECEIVED, "Gateway Received"),
                Pair(SosDeliveryState.RESPONDER_ACKNOWLEDGED, "Responder Acknowledged")
            )

            stages.forEachIndexed { index, (stage, label) ->
                val isCompleted = currentState.stepIndex >= stage.stepIndex
                val isCurrent = currentState == stage
                val matchingAudit = auditSteps.find { it.title.contains(label, ignoreCase = true) }
                val timeText = matchingAudit?.time ?: if (isCompleted) "Completed" else "Pending"
                val descText = matchingAudit?.description ?: when (stage) {
                    SosDeliveryState.SAVED_LOCALLY -> "Stored in encrypted local device cache"
                    SosDeliveryState.RELAY_ACCEPTED -> "Carried safely by nearby volunteer node"
                    SosDeliveryState.GATEWAY_RECEIVED -> "Received at emergency response gateway"
                    SosDeliveryState.RESPONDER_ACKNOWLEDGED -> "Verified responder reviewed & assigned"
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.Top
                ) {
                    // Left Timeline Track
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.width(28.dp)
                    ) {
                        Surface(
                            color = when {
                                isCompleted -> ElectricCyan
                                isCurrent -> WarmAmber
                                else -> MidnightNavy
                            },
                            shape = CircleShape,
                            modifier = Modifier
                                .size(20.dp)
                                .border(
                                    1.dp,
                                    if (isCompleted) ElectricCyan else if (isCurrent) WarmAmber else TextMutedSlate.copy(alpha = 0.4f),
                                    CircleShape
                                )
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                if (isCompleted) {
                                    Icon(
                                        imageVector = Icons.Default.CheckCircle,
                                        contentDescription = "Completed",
                                        tint = MidnightNavy,
                                        modifier = Modifier.size(14.dp)
                                    )
                                } else if (isCurrent) {
                                    Box(
                                        modifier = Modifier
                                            .size(8.dp)
                                            .background(MidnightNavy, CircleShape)
                                    )
                                }
                            }
                        }

                        if (index < stages.size - 1) {
                            Box(
                                modifier = Modifier
                                    .width(2.dp)
                                    .height(34.dp)
                                    .background(
                                        if (currentState.stepIndex > stage.stepIndex) ElectricCyan.copy(alpha = 0.7f) else TextMutedSlate.copy(alpha = 0.25f)
                                    )
                            )
                        }
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    // Right Content
                    Column(modifier = Modifier.padding(bottom = if (index < stages.size - 1) 12.dp else 0.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = label,
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = if (isCompleted || isCurrent) FontWeight.Bold else FontWeight.Medium,
                                color = if (isCompleted) TextOffWhite else if (isCurrent) WarmAmber else TextMutedSlate
                            )
                            Surface(
                                color = if (isCompleted) ElectricCyan.copy(alpha = 0.15f) else InkBlueElevated,
                                shape = RoundedCornerShape(6.dp)
                            ) {
                                Text(
                                    text = timeText,
                                    style = MaterialTheme.typography.labelSmall,
                                    fontSize = 11.sp,
                                    color = if (isCompleted) ElectricCyan else TextMutedSlate,
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = descText,
                            style = MaterialTheme.typography.bodySmall,
                            fontSize = 12.sp,
                            color = TextMutedSlate,
                            lineHeight = 16.sp
                        )
                    }
                }
            }
        }
    }
}
