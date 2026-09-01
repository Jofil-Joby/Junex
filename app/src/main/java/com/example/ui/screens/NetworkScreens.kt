package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BatteryAlert
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.SignalCellularAlt
import androidx.compose.material.icons.filled.Sync
import androidx.compose.material.icons.filled.Wifi
import androidx.compose.material.icons.filled.WifiTethering
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.RelayStats
import com.example.data.model.SosDeliveryState
import com.example.data.model.SosIncident
import com.example.data.model.UserRole
import com.example.ui.components.AppHeader
import com.example.ui.components.ConnectivityStatusChip
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
// SCREEN 11: Connection Status
// ==========================================

@Composable
fun ConnectionStatusScreen(
    role: UserRole,
    incident: SosIncident,
    relayStats: RelayStats,
    onKeepSearching: () -> Unit,
    onToggleBatteryMode: () -> Unit,
    onSimulateRelay: () -> Unit,
    onSimulateGateway: () -> Unit,
    onSwitchRole: () -> Unit
) {
    var isDetailsExpanded by remember { mutableStateOf(true) }
    val scrollState = rememberScrollState()

    val statusPillText = when (incident.deliveryState) {
        SosDeliveryState.SAVED_LOCALLY -> "Waiting for Relay"
        SosDeliveryState.RELAY_ACCEPTED -> "Relay Connected"
        SosDeliveryState.GATEWAY_RECEIVED -> "Gateway Connected"
        SosDeliveryState.RESPONDER_ACKNOWLEDGED -> "Gateway Connected"
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MidnightNavy)
    ) {
        // App Header
        AppHeader(
            role = role,
            connectivityText = statusPillText,
            isConnectivityActive = incident.deliveryState != SosDeliveryState.SAVED_LOCALLY,
            onRoleClick = onSwitchRole
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
                .verticalScroll(scrollState)
        ) {
            Spacer(modifier = Modifier.height(14.dp))

            // Title & Status Pill
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Connection Status",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = TextOffWhite,
                    modifier = Modifier.testTag("connection_status_title")
                )

                ConnectivityStatusChip(
                    text = statusPillText,
                    isActive = true
                )
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Central Visual Node Diagram
            MeshNetworkDiagram(
                deliveryState = incident.deliveryState
            )

            Spacer(modifier = Modifier.height(14.dp))

            // Main Message Card
            Card(
                colors = CardDefaults.cardColors(containerColor = InkBlueSurface),
                shape = RoundedCornerShape(14.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, InkBlueBorder, RoundedCornerShape(14.dp))
                    .testTag("connection_message_card")
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Surface(
                            color = SignalGreen.copy(alpha = 0.15f),
                            shape = CircleShape,
                            modifier = Modifier.size(32.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(
                                    imageVector = Icons.Default.Security,
                                    contentDescription = null,
                                    tint = SignalGreen,
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = "SOS saved safely",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = TextOffWhite
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Your message will relay automatically when another Disaster Mesh device comes nearby.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = TextMutedSlate,
                        lineHeight = 18.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Expandable Protocol Details Card
            Card(
                colors = CardDefaults.cardColors(containerColor = InkBlueSurface),
                shape = RoundedCornerShape(14.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, InkBlueBorder, RoundedCornerShape(14.dp))
                    .testTag("protocol_details_card")
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { isDetailsExpanded = !isDetailsExpanded },
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Radio & Protocol Diagnostics",
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold,
                            color = TextOffWhite
                        )
                        Icon(
                            imageVector = if (isDetailsExpanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                            contentDescription = "Expand",
                            tint = TextMutedSlate
                        )
                    }

                    AnimatedVisibility(visible = isDetailsExpanded) {
                        Column(modifier = Modifier.padding(top = 12.dp)) {
                            MetadataRow(label = "Stored emergency messages", value = "${relayStats.messagesStored}")
                            MetadataRow(
                                label = "Relay search",
                                value = if (relayStats.relayModeActive) "Active (Scanning BLE/Wi-Fi)" else "Paused",
                                isHighlight = relayStats.relayModeActive
                            )
                            MetadataRow(label = "Last location saved", value = "Just now (GPS/Cell cached)")
                            MetadataRow(label = "Encrypted packet ID", value = incident.id)
                            MetadataRow(label = "Payload size", value = "342 bytes (Compressed)")
                            MetadataRow(label = "Allowed hop limit", value = "5 max relays")
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Prototype Simulation Box
            Card(
                colors = CardDefaults.cardColors(containerColor = InkBlueElevated),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, WarmAmber.copy(alpha = 0.4f), RoundedCornerShape(12.dp))
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Text(
                        text = "Simulate Mesh Step",
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                        color = WarmAmber
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Button(
                            onClick = onSimulateRelay,
                            colors = ButtonDefaults.buttonColors(containerColor = InkBlueSurface, contentColor = WarmAmber),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.weight(1f).border(1.dp, WarmAmber.copy(alpha = 0.5f), RoundedCornerShape(8.dp))
                        ) {
                            Text("Simulate Relay", style = MaterialTheme.typography.labelSmall)
                        }
                        Button(
                            onClick = onSimulateGateway,
                            colors = ButtonDefaults.buttonColors(containerColor = InkBlueSurface, contentColor = ElectricCyan),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.weight(1f).border(1.dp, ElectricCyan.copy(alpha = 0.5f), RoundedCornerShape(8.dp))
                        ) {
                            Text("Simulate Gateway", style = MaterialTheme.typography.labelSmall)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            DisclaimerCard()

            Spacer(modifier = Modifier.height(20.dp))

            // Bottom Buttons
            Button(
                onClick = onKeepSearching,
                colors = ButtonDefaults.buttonColors(
                    containerColor = WarmAmber,
                    contentColor = TextDarkOnAccent
                ),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .testTag("keep_searching_relay_btn")
            ) {
                Icon(Icons.Default.Refresh, contentDescription = null, modifier = Modifier.size(18.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Keep Searching for Relay",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(6.dp))

            TextButton(
                onClick = onToggleBatteryMode,
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("toggle_battery_mode_text_btn")
            ) {
                Icon(
                    imageVector = Icons.Default.BatteryAlert,
                    contentDescription = null,
                    tint = if (relayStats.emergencyBatteryMode) SignalGreen else TextMutedSlate,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = if (relayStats.emergencyBatteryMode) "Emergency Battery Mode: ON" else "Emergency Battery Mode",
                    style = MaterialTheme.typography.labelMedium,
                    color = if (relayStats.emergencyBatteryMode) SignalGreen else TextMutedSlate
                )
            }

            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}
