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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.CrisisAlert
import androidx.compose.material.icons.filled.GroupAdd
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.MilitaryTech
import androidx.compose.material.icons.filled.PendingActions
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Sync
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import com.example.data.model.IncidentStatus
import com.example.data.model.SosDeliveryState
import com.example.data.model.SosIncident
import com.example.data.model.UserRole
import com.example.ui.components.AppHeader
import com.example.ui.components.ConnectivityStatusChip
import com.example.ui.components.DarkContourMap
import com.example.ui.components.VerticalEvidenceTimeline
import com.example.ui.theme.CoralRed
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
// SCREEN 12: Verified Responder Incident Queue
// ==========================================

@Composable
fun ResponderIncidentQueueScreen(
    incidents: List<SosIncident>,
    onOpenIncident: (SosIncident) -> Unit,
    onSwitchRole: () -> Unit
) {
    var selectedFilter by remember { mutableStateOf("All") }
    val filterOptions = listOf("All", "Critical", "Urgent", "Unassigned", "In Progress")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MidnightNavy)
    ) {
        // App Header
        AppHeader(
            role = UserRole.RESPONDER,
            connectivityText = "Gateway Connected",
            isConnectivityActive = true,
            onRoleClick = onSwitchRole
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Title Row
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "Incident Queue",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = TextOffWhite,
                    modifier = Modifier.testTag("incident_queue_title")
                )
                Text(
                    text = "${incidents.size} active mesh-relayed packets received",
                    style = MaterialTheme.typography.labelSmall,
                    color = TextMutedSlate
                )
            }

            ConnectivityStatusChip(
                text = "Gateway Connected",
                isActive = true
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Filter Chips Row
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(filterOptions) { filter ->
                val isSelected = selectedFilter == filter
                Surface(
                    color = if (isSelected) InkBlueElevated else MidnightNavy,
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier
                        .border(
                            1.dp,
                            if (isSelected) CoralRed else InkBlueBorder,
                            RoundedCornerShape(16.dp)
                        )
                        .clickable { selectedFilter = filter }
                        .testTag("responder_filter_${filter.lowercase().replace(' ', '_')}")
                ) {
                    Text(
                        text = filter,
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                        color = if (isSelected) CoralRed else TextMutedSlate,
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Incidents List
        val filteredIncidents = incidents.filter {
            when (selectedFilter) {
                "Critical" -> it.priority.contains("Critical", ignoreCase = true)
                "Urgent" -> it.priority.contains("Urgent", ignoreCase = true)
                "Unassigned" -> it.assignedTeam == null
                "In Progress" -> it.incidentStatus == IncidentStatus.IN_PROGRESS
                else -> true
            }
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(filteredIncidents) { incident ->
                ResponderIncidentCard(
                    incident = incident,
                    onOpen = { onOpenIncident(incident) }
                )
            }
            item { Spacer(modifier = Modifier.height(30.dp)) }
        }
    }
}

@Composable
fun ResponderIncidentCard(
    incident: SosIncident,
    onOpen: () -> Unit
) {
    val isCritical = incident.priority.contains("Critical", ignoreCase = true)
    val isUrgent = incident.priority.contains("Urgent", ignoreCase = true)

    Card(
        colors = CardDefaults.cardColors(containerColor = InkBlueSurface),
        shape = RoundedCornerShape(14.dp),
        modifier = Modifier
            .fillMaxWidth()
            .border(
                1.dp,
                if (isCritical) CoralRed.copy(alpha = 0.7f) else if (isUrgent) WarmAmber.copy(alpha = 0.6f) else InkBlueBorder,
                RoundedCornerShape(14.dp)
            )
            .clickable { onOpen() }
            .testTag("incident_card_${incident.id.lowercase()}")
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Top Row: Priority Pill & ID
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    color = if (isCritical) CoralRed.copy(alpha = 0.2f) else if (isUrgent) WarmAmber.copy(alpha = 0.2f) else InkBlueElevated,
                    shape = RoundedCornerShape(6.dp),
                    modifier = Modifier.border(
                        1.dp,
                        if (isCritical) CoralRed.copy(alpha = 0.5f) else if (isUrgent) WarmAmber.copy(alpha = 0.5f) else InkBlueBorder,
                        RoundedCornerShape(6.dp)
                    )
                ) {
                    Text(
                        text = incident.priority,
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                        color = if (isCritical) CoralRed else if (isUrgent) WarmAmber else TextOffWhite,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                }

                Text(
                    text = incident.id,
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.Bold,
                    color = ElectricCyan
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Category & Location
            Text(
                text = incident.category,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = TextOffWhite
            )

            Spacer(modifier = Modifier.height(2.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.LocationOn,
                    contentDescription = null,
                    tint = TextMutedSlate,
                    modifier = Modifier.size(14.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = incident.location,
                    style = MaterialTheme.typography.bodyMedium,
                    color = TextMutedSlate
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Meta Info: Time, Relays, Assigned Status
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Received ${incident.time}",
                    style = MaterialTheme.typography.labelSmall,
                    color = TextMutedSlate
                )
                Surface(
                    color = InkBlueElevated,
                    shape = RoundedCornerShape(6.dp)
                ) {
                    Text(
                        text = "Reached via ${incident.relaysCount} relays",
                        style = MaterialTheme.typography.labelSmall,
                        color = ElectricCyan,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Bottom Status & Open Action Button
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = if (incident.assignedTeam != null) "Assigned: ${incident.assignedTeam}" else "Unassigned",
                    style = MaterialTheme.typography.labelSmall,
                    fontWeight = FontWeight.Bold,
                    color = if (incident.assignedTeam != null) SignalGreen else WarmAmber
                )

                Button(
                    onClick = onOpen,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = InkBlueElevated,
                        contentColor = TextOffWhite
                    ),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier
                        .height(36.dp)
                        .border(1.dp, InkBlueBorder, RoundedCornerShape(8.dp))
                        .testTag("open_incident_btn_${incident.id.lowercase()}")
                ) {
                    Text("Open Incident", style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

// ==========================================
// SCREEN 13: Verified Responder Incident Detail
// ==========================================

@Composable
fun ResponderIncidentDetailScreen(
    incident: SosIncident,
    onAcknowledge: () -> Unit,
    onAssignTeam: (String) -> Unit,
    onMarkInProgress: () -> Unit,
    onMarkResolved: () -> Unit,
    onBack: () -> Unit
) {
    var showAssignDialog by remember { mutableStateOf(false) }
    var teamNameInput by remember { mutableStateOf("Rescue Unit Alpha 1") }
    val scrollState = rememberScrollState()

    val isAcknowledged = incident.deliveryState == SosDeliveryState.RESPONDER_ACKNOWLEDGED || incident.incidentStatus != IncidentStatus.GATEWAY_RECEIVED

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
                modifier = Modifier.testTag("responder_detail_back_btn")
            ) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = TextOffWhite)
            }
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "${incident.priority} — ${incident.category}",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = TextOffWhite,
                    modifier = Modifier.testTag("responder_detail_title")
                )
                Text(
                    text = "ID: ${incident.id} • ${incident.incidentStatus.label}",
                    style = MaterialTheme.typography.labelSmall,
                    color = ElectricCyan
                )
            }
            Surface(
                color = CoralRed.copy(alpha = 0.15f),
                shape = RoundedCornerShape(8.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Security,
                    contentDescription = null,
                    tint = CoralRed,
                    modifier = Modifier.size(24.dp).padding(4.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(scrollState)
        ) {
            // Dark Offline Map with Precise Pin
            DarkContourMap(
                locationName = incident.location,
                accuracyText = incident.accuracy,
                height = 160.dp
            )

            Spacer(modifier = Modifier.height(14.dp))

            // SECTION: Incident Summary (Citizen Report)
            Card(
                colors = CardDefaults.cardColors(containerColor = InkBlueSurface),
                shape = RoundedCornerShape(14.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, InkBlueBorder, RoundedCornerShape(14.dp))
                    .testTag("citizen_summary_card")
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Citizen Report",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = WarmAmber
                        )
                        Surface(
                            color = InkBlueElevated,
                            shape = RoundedCornerShape(6.dp)
                        ) {
                            Text(
                                text = "Origin Packet",
                                style = MaterialTheme.typography.labelSmall,
                                color = TextMutedSlate,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    MetadataRow(label = "Citizen message", value = incident.message.ifBlank { "No text attached" })
                    MetadataRow(label = "People affected", value = "${incident.peopleAffected}")
                    MetadataRow(label = "Location & accuracy", value = "${incident.location} (${incident.accuracy})")
                    MetadataRow(label = "Time received", value = incident.time)
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // SECTION: Delivery Evidence Chain
            Card(
                colors = CardDefaults.cardColors(containerColor = InkBlueSurface),
                shape = RoundedCornerShape(14.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, InkBlueBorder, RoundedCornerShape(14.dp))
                    .testTag("delivery_evidence_card")
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Delivery Evidence (Mesh Chain)",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = ElectricCyan
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    EvidenceChainItem("1. SOS Saved Locally", "Phone A cached encrypted payload at 10:42 AM", true)
                    EvidenceChainItem("2. Relay Accepted", "Carried by Volunteer Phone #B4 via BLE", true)
                    EvidenceChainItem("3. Forwarded via Hop 2", "Relayed through Node #C7 at 10:43 AM", true)
                    EvidenceChainItem("4. Gateway Received", "Delivered to Station Gateway Alpha at 10:43 AM", true)
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // SECTION: Responder Confirmed & Audit Trail
            Card(
                colors = CardDefaults.cardColors(containerColor = InkBlueSurface),
                shape = RoundedCornerShape(14.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, InkBlueBorder, RoundedCornerShape(14.dp))
                    .testTag("responder_audit_card")
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Responder Confirmed Audit Trail",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = SignalGreen
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    incident.auditTrail.forEach { step ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Box(
                                    modifier = Modifier
                                        .size(8.dp)
                                        .background(
                                            if (step.isCompleted) SignalGreen else TextMutedSlate,
                                            CircleShape
                                        )
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Column {
                                    Text(
                                        text = step.title,
                                        style = MaterialTheme.typography.labelSmall,
                                        fontWeight = FontWeight.Bold,
                                        color = if (step.isCompleted) TextOffWhite else TextMutedSlate
                                    )
                                    Text(
                                        text = step.description,
                                        style = MaterialTheme.typography.labelSmall,
                                        fontSize = 10.sp,
                                        color = TextMutedSlate
                                    )
                                }
                            }
                            Text(
                                text = step.time,
                                style = MaterialTheme.typography.labelSmall,
                                color = if (step.isCompleted) SignalGreen else TextMutedSlate
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // SECTION: Response Workflow Actions
            Card(
                colors = CardDefaults.cardColors(containerColor = InkBlueElevated),
                shape = RoundedCornerShape(14.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, InkBlueBorder, RoundedCornerShape(14.dp))
                    .testTag("response_workflow_card")
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Response Workflow",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = TextOffWhite
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Button(
                            onClick = { showAssignDialog = true },
                            colors = ButtonDefaults.buttonColors(containerColor = InkBlueSurface, contentColor = ElectricCyan),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.weight(1f).border(1.dp, ElectricCyan.copy(alpha = 0.5f), RoundedCornerShape(8.dp))
                        ) {
                            Icon(Icons.Default.GroupAdd, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Assign Team", style = MaterialTheme.typography.labelSmall)
                        }

                        Button(
                            onClick = onMarkInProgress,
                            colors = ButtonDefaults.buttonColors(containerColor = InkBlueSurface, contentColor = WarmAmber),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.weight(1f).border(1.dp, WarmAmber.copy(alpha = 0.5f), RoundedCornerShape(8.dp))
                        ) {
                            Icon(Icons.Default.PendingActions, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("In Progress", style = MaterialTheme.typography.labelSmall)
                        }

                        Button(
                            onClick = onMarkResolved,
                            colors = ButtonDefaults.buttonColors(containerColor = InkBlueSurface, contentColor = SignalGreen),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.weight(1f).border(1.dp, SignalGreen.copy(alpha = 0.5f), RoundedCornerShape(8.dp))
                        ) {
                            Icon(Icons.Default.CheckCircle, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Resolved", style = MaterialTheme.typography.labelSmall)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
        }

        // Primary Amber Action: Acknowledge Incident
        Button(
            onClick = onAcknowledge,
            colors = ButtonDefaults.buttonColors(
                containerColor = if (isAcknowledged) SignalGreen else WarmAmber,
                contentColor = TextDarkOnAccent
            ),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp)
                .testTag("acknowledge_incident_btn")
        ) {
            Icon(
                imageVector = if (isAcknowledged) Icons.Default.CheckCircle else Icons.Default.Check,
                contentDescription = null,
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = if (isAcknowledged) "Incident Acknowledged • Citizen Updated" else "Acknowledge Incident",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
        }
    }

    if (showAssignDialog) {
        AlertDialog(
            onDismissRequest = { showAssignDialog = false },
            containerColor = InkBlueSurface,
            title = {
                Text(
                    text = "Assign Rescue Team",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = TextOffWhite
                )
            },
            text = {
                Column {
                    Text(
                        text = "Enter responding tactical rescue team name:",
                        style = MaterialTheme.typography.bodySmall,
                        color = TextMutedSlate
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = teamNameInput,
                        onValueChange = { teamNameInput = it },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = InkBlueElevated,
                            unfocusedContainerColor = InkBlueElevated,
                            focusedBorderColor = ElectricCyan,
                            unfocusedBorderColor = InkBlueBorder,
                            focusedTextColor = TextOffWhite,
                            unfocusedTextColor = TextOffWhite
                        ),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        onAssignTeam(teamNameInput)
                        showAssignDialog = false
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = WarmAmber, contentColor = TextDarkOnAccent)
                ) {
                    Text("Assign")
                }
            },
            dismissButton = {
                TextButton(onClick = { showAssignDialog = false }) {
                    Text("Cancel", color = TextMutedSlate)
                }
            }
        )
    }
}

@Composable
fun EvidenceChainItem(
    title: String,
    detail: String,
    isVerified: Boolean
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.Top
    ) {
        Icon(
            imageVector = Icons.Default.CheckCircle,
            contentDescription = null,
            tint = ElectricCyan,
            modifier = Modifier.size(16.dp).padding(top = 2.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column {
            Text(
                text = title,
                style = MaterialTheme.typography.labelSmall,
                fontWeight = FontWeight.Bold,
                color = TextOffWhite
            )
            Text(
                text = detail,
                style = MaterialTheme.typography.bodySmall,
                fontSize = 11.sp,
                color = TextMutedSlate
            )
        }
    }
}
