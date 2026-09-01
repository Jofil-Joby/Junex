package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.BatteryAlert
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Handshake
import androidx.compose.material.icons.filled.HealthAndSafety
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.LockClock
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.SignalCellularAlt
import androidx.compose.material.icons.filled.Sync
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.RelayStats
import com.example.data.model.SosDeliveryState
import com.example.data.model.UserRole
import com.example.data.model.VolunteerHelpRequest
import com.example.ui.components.AppHeader
import com.example.ui.components.DarkContourMap
import com.example.ui.components.DisclaimerCard
import com.example.ui.components.MeshNetworkDiagram
import com.example.ui.theme.ElectricCyan
import com.example.ui.theme.InkBlueBorder
import com.example.ui.theme.InkBlueDivider
import com.example.ui.theme.InkBlueElevated
import com.example.ui.theme.InkBlueSurface
import com.example.ui.theme.MidnightNavy
import com.example.ui.theme.SignalGreen
import com.example.ui.theme.TextDarkOnAccent
import com.example.ui.theme.TextMutedSlate
import com.example.ui.theme.TextOffWhite
import com.example.ui.theme.WarmAmber

// ==========================================
// SCREEN 7: Help Nearby (Volunteer Helper Home)
// ==========================================

@Composable
fun VolunteerHelpNearbyScreen(
    relayStats: RelayStats,
    requests: List<VolunteerHelpRequest>,
    onRequestClick: (VolunteerHelpRequest) -> Unit,
    onViewRelayActivity: () -> Unit,
    onSafetyGuidance: () -> Unit,
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
            role = UserRole.VOLUNTEER,
            connectivityText = if (relayStats.relayModeActive) "Relay Active" else "Relay Paused",
            isConnectivityActive = relayStats.relayModeActive,
            onRoleClick = onSwitchRole
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
                .verticalScroll(scrollState)
        ) {
            Spacer(modifier = Modifier.height(14.dp))

            // Top Message
            Card(
                colors = CardDefaults.cardColors(containerColor = InkBlueSurface),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, InkBlueBorder, RoundedCornerShape(12.dp))
                    .testTag("volunteer_top_message_card")
            ) {
                Row(
                    modifier = Modifier.padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Handshake,
                        contentDescription = null,
                        tint = WarmAmber,
                        modifier = Modifier.size(22.dp)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = "Your phone is helping carry emergency messages safely.",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Medium,
                        color = TextOffWhite,
                        lineHeight = 18.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // SECTION 1: Relay Contribution
            Card(
                colors = CardDefaults.cardColors(containerColor = InkBlueElevated),
                shape = RoundedCornerShape(14.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, WarmAmber.copy(alpha = 0.4f), RoundedCornerShape(14.dp))
                    .testTag("relay_contribution_card")
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Relay Contribution",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = TextOffWhite
                        )
                        Surface(
                            color = if (relayStats.relayModeActive) SignalGreen.copy(alpha = 0.15f) else InkBlueSurface,
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Text(
                                text = if (relayStats.relayModeActive) "Relay mode is active" else "Paused",
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold,
                                color = if (relayStats.relayModeActive) SignalGreen else TextMutedSlate,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = "${relayStats.messagesRelayedToday} emergency messages safely carried today",
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.SemiBold,
                        color = TextOffWhite
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Button(
                        onClick = onViewRelayActivity,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = InkBlueSurface,
                            contentColor = WarmAmber
                        ),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(1.dp, WarmAmber.copy(alpha = 0.5f), RoundedCornerShape(8.dp))
                            .testTag("view_relay_activity_btn")
                    ) {
                        Text("View Relay Activity", fontWeight = FontWeight.Bold)
                    }
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            // SECTION 2: Nearby Help Requests (Limited Information)
            Text(
                text = "Nearby Help Requests",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = TextOffWhite
            )
            Text(
                text = "Limited privacy-preserving citizen reports near your approximate sector.",
                style = MaterialTheme.typography.bodySmall,
                color = TextMutedSlate
            )

            Spacer(modifier = Modifier.height(10.dp))

            requests.forEach { req ->
                NearbyHelpRequestCard(
                    request = req,
                    onClick = { onRequestClick(req) }
                )
                Spacer(modifier = Modifier.height(10.dp))
            }

            Spacer(modifier = Modifier.height(14.dp))

            // SECTION 3: Volunteer Safety Card
            Card(
                colors = CardDefaults.cardColors(containerColor = InkBlueSurface),
                shape = RoundedCornerShape(14.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, InkBlueBorder, RoundedCornerShape(14.dp))
                    .testTag("volunteer_safety_card")
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Shield,
                            contentDescription = null,
                            tint = WarmAmber,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Volunteer Safety",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = TextOffWhite
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Only help when it is safe. Do not enter dangerous areas or unstable structures.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = TextMutedSlate,
                        lineHeight = 18.sp
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Button(
                        onClick = onSafetyGuidance,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = InkBlueElevated,
                            contentColor = TextOffWhite
                        ),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(1.dp, InkBlueBorder, RoundedCornerShape(8.dp))
                            .testTag("safety_guidance_btn")
                    ) {
                        Text("Safety Guidance")
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
fun NearbyHelpRequestCard(
    request: VolunteerHelpRequest,
    onClick: () -> Unit
) {
    Card(
        colors = CardDefaults.cardColors(containerColor = InkBlueSurface),
        shape = RoundedCornerShape(14.dp),
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, InkBlueBorder, RoundedCornerShape(14.dp))
            .clickable { onClick() }
            .testTag("nearby_request_card_${request.id.lowercase()}")
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = request.title,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    color = TextOffWhite
                )
                if (request.isHelped) {
                    Surface(color = SignalGreen.copy(alpha = 0.2f), shape = RoundedCornerShape(6.dp)) {
                        Text(
                            text = "Help Offered",
                            style = MaterialTheme.typography.labelSmall,
                            color = SignalGreen,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(6.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "${request.distance} away",
                    style = MaterialTheme.typography.bodySmall,
                    color = WarmAmber
                )
                Text(
                    text = "Received ${request.receivedAgo}",
                    style = MaterialTheme.typography.labelSmall,
                    color = TextMutedSlate
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            Button(
                onClick = onClick,
                colors = ButtonDefaults.buttonColors(
                    containerColor = InkBlueElevated,
                    contentColor = TextOffWhite
                ),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(38.dp)
                    .border(1.dp, InkBlueBorder, RoundedCornerShape(8.dp))
            ) {
                Text("View Safe Details", style = MaterialTheme.typography.labelMedium)
            }
        }
    }
}

// ==========================================
// SCREEN 8: Nearby Help Request Detail
// ==========================================

@Composable
fun NearbyHelpDetailScreen(
    request: VolunteerHelpRequest,
    onOfferHelp: () -> Unit,
    onRelayRequest: () -> Unit,
    onShareSafetyGuidance: () -> Unit,
    onBack: () -> Unit
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MidnightNavy)
            .padding(horizontal = 16.dp, vertical = 14.dp)
    ) {
        // Top Header
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = onBack,
                modifier = Modifier.testTag("help_detail_back_btn")
            ) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = TextOffWhite)
            }
            Text(
                text = "Help Request",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = TextOffWhite,
                modifier = Modifier.testTag("help_detail_title")
            )
            Surface(
                color = WarmAmber.copy(alpha = 0.15f),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.border(1.dp, WarmAmber.copy(alpha = 0.4f), RoundedCornerShape(8.dp))
            ) {
                Text(
                    text = "Unverified citizen report",
                    style = MaterialTheme.typography.labelSmall,
                    color = WarmAmber,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(scrollState)
        ) {
            // Coarse Approximate Map (Not Exact Address)
            DarkContourMap(
                locationName = request.approximateArea,
                accuracyText = "Coarse Sector Radius (~500m)",
                height = 160.dp,
                showCoarseOnly = true
            )

            Spacer(modifier = Modifier.height(14.dp))

            // Details Card
            Card(
                colors = CardDefaults.cardColors(containerColor = InkBlueSurface),
                shape = RoundedCornerShape(14.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, InkBlueBorder, RoundedCornerShape(14.dp))
                    .testTag("help_request_detail_card")
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = request.title,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = TextOffWhite
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    MetadataRow(label = "Approximate area", value = request.approximateArea)
                    MetadataRow(label = "Distance", value = request.distance)
                    MetadataRow(label = "Received", value = request.receivedAgo)
                    MetadataRow(label = "People affected", value = "${request.peopleAffected}")

                    Spacer(modifier = Modifier.height(10.dp))
                    Box(modifier = Modifier.fillMaxWidth().height(1.dp).background(InkBlueDivider))
                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = "Safe Guidance for Volunteers:",
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                        color = ElectricCyan
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = request.safeGuidance,
                        style = MaterialTheme.typography.bodySmall,
                        color = TextOffWhite,
                        lineHeight = 16.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Safety Warning Notice
            Card(
                colors = CardDefaults.cardColors(containerColor = InkBlueElevated),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, WarmAmber.copy(alpha = 0.4f), RoundedCornerShape(12.dp))
            ) {
                Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.Top) {
                    Icon(
                        imageVector = Icons.Default.Warning,
                        contentDescription = null,
                        tint = WarmAmber,
                        modifier = Modifier.size(18.dp).padding(top = 2.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Do not enter unsafe areas. Call emergency services if a normal network is available. You are not an official responder.",
                        style = MaterialTheme.typography.bodySmall,
                        color = TextMutedSlate,
                        lineHeight = 15.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
        }

        // Actions
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(
                onClick = onOfferHelp,
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (request.isHelped) SignalGreen else WarmAmber,
                    contentColor = TextDarkOnAccent
                ),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .testTag("volunteer_offer_help_btn")
            ) {
                Icon(
                    imageVector = if (request.isHelped) Icons.Default.Check else Icons.Default.Handshake,
                    contentDescription = null,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = if (request.isHelped) "Help Marked" else "I Can Safely Help",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(
                    onClick = onRelayRequest,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = InkBlueElevated,
                        contentColor = ElectricCyan
                    ),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier
                        .weight(1f)
                        .height(44.dp)
                        .border(1.dp, ElectricCyan.copy(alpha = 0.4f), RoundedCornerShape(10.dp))
                        .testTag("volunteer_relay_request_btn")
                ) {
                    Icon(Icons.Default.Sync, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Relay This Request", style = MaterialTheme.typography.labelSmall)
                }

                Button(
                    onClick = onShareSafetyGuidance,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = InkBlueElevated,
                        contentColor = TextOffWhite
                    ),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier
                        .weight(1f)
                        .height(44.dp)
                        .border(1.dp, InkBlueBorder, RoundedCornerShape(10.dp))
                        .testTag("share_safety_guidance_btn")
                ) {
                    Icon(Icons.Default.Share, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Share Guidance", style = MaterialTheme.typography.labelSmall)
                }
            }
        }
    }
}

// ==========================================
// SCREEN 9: Volunteer Relay Activity
// ==========================================

@Composable
fun VolunteerRelayActivityScreen(
    relayStats: RelayStats,
    onToggleBatteryMode: () -> Unit,
    onToggleRelayMode: () -> Unit,
    onBack: () -> Unit
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MidnightNavy)
            .padding(horizontal = 16.dp, vertical = 14.dp)
    ) {
        // Header
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = onBack,
                modifier = Modifier.testTag("relay_activity_back_btn")
            ) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = TextOffWhite)
            }
            Text(
                text = "Your Relay Activity",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = TextOffWhite,
                modifier = Modifier.testTag("relay_activity_title")
            )
            Spacer(modifier = Modifier.width(48.dp))
        }

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = "Your phone helps emergency messages move when networks fail.",
            style = MaterialTheme.typography.bodyMedium,
            color = TextMutedSlate,
            modifier = Modifier.padding(horizontal = 4.dp)
        )

        Spacer(modifier = Modifier.height(14.dp))

        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(scrollState)
        ) {
            // Node Diagram: Nearby Phone -> Your Phone -> Responder Gateway
            MeshNetworkDiagram(
                deliveryState = SosDeliveryState.RELAY_ACCEPTED
            )

            Spacer(modifier = Modifier.height(14.dp))

            // Relay Statistics Card
            Card(
                colors = CardDefaults.cardColors(containerColor = InkBlueSurface),
                shape = RoundedCornerShape(14.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, InkBlueBorder, RoundedCornerShape(14.dp))
                    .testTag("relay_stats_card")
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Encrypted Packet Statistics",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = TextOffWhite
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    MetadataRow(label = "Messages currently stored", value = "${relayStats.messagesStored}")
                    MetadataRow(label = "Messages relayed today", value = "${relayStats.messagesRelayedToday}")
                    MetadataRow(label = "Last relay", value = relayStats.lastRelay)
                    MetadataRow(
                        label = "Relay mode",
                        value = if (relayStats.relayModeActive) "Active" else "Paused",
                        isHighlight = relayStats.relayModeActive
                    )
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Emergency Battery Mode Switch Card
            Card(
                colors = CardDefaults.cardColors(containerColor = InkBlueElevated),
                shape = RoundedCornerShape(14.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, InkBlueBorder, RoundedCornerShape(14.dp))
                    .testTag("battery_mode_card")
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.BatteryAlert,
                                contentDescription = null,
                                tint = WarmAmber,
                                modifier = Modifier.size(22.dp)
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            Column {
                                Text(
                                    text = "Emergency Battery Mode",
                                    style = MaterialTheme.typography.titleSmall,
                                    fontWeight = FontWeight.Bold,
                                    color = TextOffWhite
                                )
                                Text(
                                    text = "Prioritizes life-threatening and urgent messages.",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = TextMutedSlate
                                )
                            }
                        }

                        Switch(
                            checked = relayStats.emergencyBatteryMode,
                            onCheckedChange = { onToggleBatteryMode() },
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = TextDarkOnAccent,
                                checkedTrackColor = WarmAmber,
                                uncheckedThumbColor = TextMutedSlate,
                                uncheckedTrackColor = InkBlueSurface
                            ),
                            modifier = Modifier.testTag("emergency_battery_switch")
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Privacy Note Card
            Card(
                colors = CardDefaults.cardColors(containerColor = InkBlueSurface),
                shape = RoundedCornerShape(14.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, InkBlueBorder, RoundedCornerShape(14.dp))
                    .testTag("privacy_note_card")
            ) {
                Row(modifier = Modifier.padding(14.dp), verticalAlignment = Alignment.Top) {
                    Icon(
                        imageVector = Icons.Default.Lock,
                        contentDescription = "Encrypted",
                        tint = ElectricCyan,
                        modifier = Modifier.size(20.dp).padding(top = 2.dp)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Text(
                            text = "Zero-Knowledge Encryption",
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.Bold,
                            color = ElectricCyan
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = "Messages are end-to-end encrypted. As a Volunteer Helper, your device carries anonymous binary packets but cannot read private emergency content.",
                            style = MaterialTheme.typography.bodySmall,
                            color = TextMutedSlate,
                            lineHeight = 16.sp
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}
