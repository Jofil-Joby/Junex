package com.example.ui.screens

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CrisisAlert
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.SosDeliveryState
import com.example.data.model.SosIncident
import com.example.data.model.UserRole
import com.example.ui.components.AppHeader
import com.example.ui.components.DarkContourMap
import com.example.ui.components.DisclaimerCard
import com.example.ui.theme.CoralRed
import com.example.ui.theme.CoralRedGlow
import com.example.ui.theme.ElectricCyan
import com.example.ui.theme.InkBlueBorder
import com.example.ui.theme.InkBlueElevated
import com.example.ui.theme.InkBlueSurface
import com.example.ui.theme.MidnightNavy
import com.example.ui.theme.SignalGreen
import com.example.ui.theme.TextDarkOnAccent
import com.example.ui.theme.TextMutedSlate
import com.example.ui.theme.TextOffWhite
import com.example.ui.theme.WarmAmber
import kotlinx.coroutines.delay

@Composable
fun CitizenHomeScreen(
    sosIncident: SosIncident,
    onStartSos: () -> Unit,
    onViewActiveSos: () -> Unit,
    onReportHazard: () -> Unit,
    onViewAlerts: () -> Unit,
    onSafetyGuide: () -> Unit,
    onSwitchRole: () -> Unit
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MidnightNavy)
    ) {
        // Header
        AppHeader(
            role = UserRole.CITIZEN,
            connectivityText = "Mesh Available",
            isConnectivityActive = true,
            onRoleClick = onSwitchRole
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(14.dp))

            // If an SOS is already active or in progress, show active SOS banner
            if (sosIncident.deliveryState != SosDeliveryState.SAVED_LOCALLY || sosIncident.incidentStatus != com.example.data.model.IncidentStatus.GATEWAY_RECEIVED) {
                ActiveSosBanner(
                    sosIncident = sosIncident,
                    onClick = onViewActiveSos
                )
                Spacer(modifier = Modifier.height(14.dp))
            }

            // Main Offline Message Card
            Card(
                colors = CardDefaults.cardColors(containerColor = InkBlueSurface),
                shape = RoundedCornerShape(14.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, InkBlueBorder, RoundedCornerShape(14.dp))
                    .testTag("offline_notice_card")
            ) {
                Row(
                    modifier = Modifier.padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(10.dp)
                            .background(ElectricCyan, CircleShape)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = "You are offline, but nearby phones can help relay an SOS.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = TextOffWhite,
                        lineHeight = 18.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Large Central Coral SOS Button
            HoldForSosButton(
                onTrigger = onStartSos
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Location Accuracy Status
            Surface(
                color = InkBlueElevated,
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, InkBlueBorder, RoundedCornerShape(12.dp))
                    .testTag("location_status_bar")
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 14.dp, vertical = 10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.LocationOn,
                            contentDescription = "Location",
                            tint = ElectricCyan,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "Location ready",
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.Bold,
                            color = TextOffWhite
                        )
                    }
                    Text(
                        text = "Approx. 24 m accuracy",
                        style = MaterialTheme.typography.labelSmall,
                        color = ElectricCyan
                    )
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            // Quick Actions Section
            Text(
                text = "Quick Actions",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = TextOffWhite,
                modifier = Modifier.align(Alignment.Start)
            )

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                QuickActionCard(
                    title = "Report a\nHazard",
                    icon = Icons.Default.Warning,
                    tint = WarmAmber,
                    modifier = Modifier.weight(1f),
                    tag = "quick_report_hazard_btn",
                    onClick = onReportHazard
                )
                QuickActionCard(
                    title = "Nearby\nAlerts",
                    icon = Icons.Default.Notifications,
                    tint = ElectricCyan,
                    modifier = Modifier.weight(1f),
                    tag = "quick_nearby_alerts_btn",
                    onClick = onViewAlerts
                )
                QuickActionCard(
                    title = "Safety\nGuide",
                    icon = Icons.Default.Shield,
                    tint = SignalGreen,
                    modifier = Modifier.weight(1f),
                    tag = "quick_safety_guide_btn",
                    onClick = onSafetyGuide
                )
            }

            Spacer(modifier = Modifier.height(18.dp))

            DisclaimerCard()

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
fun HoldForSosButton(
    onTrigger: () -> Unit,
    modifier: Modifier = Modifier
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    var progress by remember { mutableFloatStateOf(0f) }

    val pulseTransition = rememberInfiniteTransition(label = "sos_pulse")
    val pulseScale by pulseTransition.animateFloat(
        initialValue = 1.0f,
        targetValue = 1.06f,
        animationSpec = infiniteRepeatable(
            animation = tween(1400, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "sosScale"
    )

    LaunchedEffect(isPressed) {
        if (isPressed) {
            // Fill progress quickly for responsiveness or single tap
            val startTime = System.currentTimeMillis()
            while (isPressed && progress < 1f) {
                val elapsed = System.currentTimeMillis() - startTime
                progress = (elapsed / 600f).coerceIn(0f, 1f)
                if (progress >= 1f) {
                    onTrigger()
                    progress = 0f
                    break
                }
                delay(16)
            }
        } else {
            progress = 0f
        }
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.testTag("sos_button_container")
    ) {
        Box(
            modifier = Modifier
                .size(200.dp)
                .scale(if (isPressed) 0.96f else pulseScale),
            contentAlignment = Alignment.Center
        ) {
            // Outer Glowing Ring
            Box(
                modifier = Modifier
                    .size(196.dp)
                    .background(CoralRedGlow, CircleShape)
            )

            // Circular SOS Button
            Box(
                modifier = Modifier
                    .size(168.dp)
                    .shadow(elevation = 16.dp, shape = CircleShape, spotColor = CoralRed)
                    .background(
                        brush = Brush.radialGradient(
                            colors = listOf(
                                CoralRed,
                                CoralRed.copy(alpha = 0.85f),
                                Color(0xFFC0392B)
                            )
                        ),
                        shape = CircleShape
                    )
                    .border(3.dp, TextOffWhite.copy(alpha = 0.8f), CircleShape)
                    .clickable(
                        interactionSource = interactionSource,
                        indication = null
                    ) {
                        onTrigger()
                    }
                    .testTag("hold_for_sos_btn"),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.CrisisAlert,
                        contentDescription = "SOS",
                        tint = TextOffWhite,
                        modifier = Modifier.size(36.dp)
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "HOLD FOR SOS",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Black,
                        color = TextOffWhite,
                        letterSpacing = 1.sp
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Use only for immediate danger",
            style = MaterialTheme.typography.labelSmall,
            color = WarmAmber,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Composable
fun QuickActionCard(
    title: String,
    icon: ImageVector,
    tint: androidx.compose.ui.graphics.Color,
    modifier: Modifier = Modifier,
    tag: String,
    onClick: () -> Unit
) {
    Card(
        colors = CardDefaults.cardColors(containerColor = InkBlueSurface),
        shape = RoundedCornerShape(12.dp),
        modifier = modifier
            .border(1.dp, InkBlueBorder, RoundedCornerShape(12.dp))
            .clickable { onClick() }
            .testTag(tag)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 14.dp, horizontal = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Surface(
                color = tint.copy(alpha = 0.15f),
                shape = CircleShape,
                modifier = Modifier
                    .size(36.dp)
                    .border(1.dp, tint.copy(alpha = 0.4f), CircleShape)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = icon,
                        contentDescription = title,
                        tint = tint,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = title,
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.SemiBold,
                color = TextOffWhite,
                textAlign = TextAlign.Center,
                lineHeight = 15.sp
            )
        }
    }
}

@Composable
fun ActiveSosBanner(
    sosIncident: SosIncident,
    onClick: () -> Unit
) {
    Card(
        colors = CardDefaults.cardColors(containerColor = InkBlueElevated),
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .fillMaxWidth()
            .border(1.5.dp, CoralRed.copy(alpha = 0.7f), RoundedCornerShape(12.dp))
            .clickable { onClick() }
            .testTag("active_sos_banner")
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.weight(1f)
            ) {
                Box(
                    modifier = Modifier
                        .size(10.dp)
                        .background(CoralRed, CircleShape)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Column {
                    Text(
                        text = "Active SOS (${sosIncident.id})",
                        style = MaterialTheme.typography.labelLarge,
                        fontWeight = FontWeight.Bold,
                        color = CoralRed
                    )
                    Text(
                        text = "Status: ${sosIncident.deliveryState.title}",
                        style = MaterialTheme.typography.bodySmall,
                        color = TextOffWhite
                    )
                }
            }
            Surface(
                color = CoralRed,
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(
                    text = "View Status",
                    style = MaterialTheme.typography.labelSmall,
                    fontWeight = FontWeight.Bold,
                    color = TextOffWhite,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                )
            }
        }
    }
}
