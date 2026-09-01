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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CrisisAlert
import androidx.compose.material.icons.filled.DirectionsRun
import androidx.compose.material.icons.filled.DomainDisabled
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.FiberManualRecord
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.LocalHospital
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.PersonSearch
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.SignalCellularAlt
import androidx.compose.material.icons.filled.Tsunami
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.SosDeliveryState
import com.example.data.model.SosIncident
import com.example.ui.components.ConnectivityStatusChip
import com.example.ui.components.DarkContourMap
import com.example.ui.components.DisclaimerCard
import com.example.ui.components.MeshNetworkDiagram
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
// STEP 1: Emergency Category Selection
// ==========================================

data class SosCategoryItem(
    val title: String,
    val icon: ImageVector,
    val isCritical: Boolean = true
)

@Composable
fun SosCategorySelectionScreen(
    selectedCategory: String,
    onCategorySelected: (String) -> Unit,
    onContinue: () -> Unit,
    onBack: () -> Unit
) {
    val categories = listOf(
        SosCategoryItem("Trapped Person", Icons.Default.CrisisAlert),
        SosCategoryItem("Medical Emergency", Icons.Default.LocalHospital),
        SosCategoryItem("Flood", Icons.Default.Tsunami),
        SosCategoryItem("Fire", Icons.Default.LocalFireDepartment),
        SosCategoryItem("Need Evacuation", Icons.Default.DirectionsRun),
        SosCategoryItem("Building Collapse", Icons.Default.DomainDisabled),
        SosCategoryItem("Missing Person", Icons.Default.PersonSearch),
        SosCategoryItem("Other", Icons.Default.Warning)
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MidnightNavy)
            .padding(horizontal = 16.dp, vertical = 14.dp)
    ) {
        // Top Navigation Bar
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = onBack,
                modifier = Modifier.testTag("sos_step1_back_btn")
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = TextOffWhite
                )
            }
            Surface(
                color = InkBlueElevated,
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    text = "Step 1 of 3",
                    style = MaterialTheme.typography.labelSmall,
                    fontWeight = FontWeight.Bold,
                    color = WarmAmber,
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "What help do you need?",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = TextOffWhite,
            modifier = Modifier.testTag("sos_step1_title")
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = "Select the primary emergency category for mesh broadcast.",
            style = MaterialTheme.typography.bodyMedium,
            color = TextMutedSlate
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Grid of 8 Categories
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier
                .weight(1f)
                .testTag("category_grid")
        ) {
            items(categories) { item ->
                val isSelected = selectedCategory == item.title
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = if (isSelected) InkBlueElevated else InkBlueSurface
                    ),
                    shape = RoundedCornerShape(14.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(90.dp)
                        .border(
                            width = if (isSelected) 2.dp else 1.dp,
                            color = if (isSelected) CoralRed else InkBlueBorder,
                            shape = RoundedCornerShape(14.dp)
                        )
                        .clickable { onCategorySelected(item.title) }
                        .testTag("category_card_${item.title.lowercase().replace(' ', '_')}")
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(12.dp),
                        verticalArrangement = Arrangement.SpaceBetween,
                        horizontalAlignment = Alignment.Start
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = item.icon,
                                contentDescription = item.title,
                                tint = if (isSelected) CoralRed else ElectricCyan,
                                modifier = Modifier.size(24.dp)
                            )
                            if (isSelected) {
                                Box(
                                    modifier = Modifier
                                        .size(16.dp)
                                        .background(CoralRed, CircleShape),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Check,
                                        contentDescription = "Selected",
                                        tint = TextOffWhite,
                                        modifier = Modifier.size(12.dp)
                                    )
                                }
                            }
                        }
                        Text(
                            text = item.title,
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold,
                            color = if (isSelected) TextOffWhite else TextOffWhite.copy(alpha = 0.85f)
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = "For immediate danger, send an SOS. For hazards, use Report a Hazard.",
            style = MaterialTheme.typography.labelSmall,
            color = WarmAmber,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Amber Continue Button
        Button(
            onClick = onContinue,
            colors = ButtonDefaults.buttonColors(
                containerColor = WarmAmber,
                contentColor = TextDarkOnAccent
            ),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .testTag("sos_step1_continue_btn")
        ) {
            Text(
                text = "Continue",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

// ==========================================
// STEP 2: Confirm Emergency SOS
// ==========================================

@Composable
fun SosConfirmScreen(
    category: String,
    peopleCount: Int,
    message: String,
    onPeopleCountChange: (Int) -> Unit,
    onMessageChange: (String) -> Unit,
    onSendSos: () -> Unit,
    onBack: () -> Unit
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MidnightNavy)
            .padding(horizontal = 16.dp, vertical = 14.dp)
    ) {
        // Top Bar
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = onBack,
                modifier = Modifier.testTag("sos_step2_back_btn")
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = TextOffWhite
                )
            }
            Surface(
                color = InkBlueElevated,
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    text = "Step 2 of 3",
                    style = MaterialTheme.typography.labelSmall,
                    fontWeight = FontWeight.Bold,
                    color = CoralRed,
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(scrollState)
        ) {
            Text(
                text = "Confirm Emergency SOS",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = TextOffWhite,
                modifier = Modifier.testTag("sos_step2_title")
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "Review location and emergency details before mesh broadcast.",
                style = MaterialTheme.typography.bodyMedium,
                color = TextMutedSlate
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Dark Offline Map with cyan location pin
            DarkContourMap(
                locationName = "Riverside Road, Pune",
                accuracyText = "Approx. 24 m accuracy",
                height = 170.dp
            )

            Spacer(modifier = Modifier.height(14.dp))

            // Emergency Details Card
            Card(
                colors = CardDefaults.cardColors(containerColor = InkBlueSurface),
                shape = RoundedCornerShape(14.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, InkBlueBorder, RoundedCornerShape(14.dp))
                    .testTag("confirm_details_card")
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    // Category Row
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Emergency",
                            style = MaterialTheme.typography.labelMedium,
                            color = TextMutedSlate
                        )
                        Surface(
                            color = CoralRed.copy(alpha = 0.2f),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.border(1.dp, CoralRed.copy(alpha = 0.5f), RoundedCornerShape(8.dp))
                        ) {
                            Text(
                                text = category,
                                style = MaterialTheme.typography.labelMedium,
                                fontWeight = FontWeight.Bold,
                                color = CoralRed,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))
                    Box(modifier = Modifier.fillMaxWidth().height(1.dp).background(InkBlueDivider))
                    Spacer(modifier = Modifier.height(12.dp))

                    // Location Row
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Location",
                            style = MaterialTheme.typography.labelMedium,
                            color = TextMutedSlate
                        )
                        Column(horizontalAlignment = Alignment.End) {
                            Text(
                                text = "Riverside Road, Pune",
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.Bold,
                                color = TextOffWhite
                            )
                            Text(
                                text = "Approx. 24 m accuracy",
                                style = MaterialTheme.typography.labelSmall,
                                color = ElectricCyan
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))
                    Box(modifier = Modifier.fillMaxWidth().height(1.dp).background(InkBlueDivider))
                    Spacer(modifier = Modifier.height(12.dp))

                    // People Affected Stepper
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "People affected",
                            style = MaterialTheme.typography.labelMedium,
                            color = TextMutedSlate
                        )
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            IconButton(
                                onClick = { onPeopleCountChange((peopleCount - 1).coerceAtLeast(1)) },
                                modifier = Modifier.size(32.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Remove,
                                    contentDescription = "Decrease",
                                    tint = TextOffWhite
                                )
                            }
                            Surface(
                                color = InkBlueElevated,
                                shape = RoundedCornerShape(6.dp),
                                modifier = Modifier.padding(horizontal = 8.dp)
                            ) {
                                Text(
                                    text = "$peopleCount",
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = TextOffWhite,
                                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 2.dp)
                                )
                            }
                            IconButton(
                                onClick = { onPeopleCountChange(peopleCount + 1) },
                                modifier = Modifier.size(32.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Add,
                                    contentDescription = "Increase",
                                    tint = TextOffWhite
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))
                    Box(modifier = Modifier.fillMaxWidth().height(1.dp).background(InkBlueDivider))
                    Spacer(modifier = Modifier.height(12.dp))

                    // Optional Message Field
                    Text(
                        text = "Optional message",
                        style = MaterialTheme.typography.labelMedium,
                        color = TextMutedSlate
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    OutlinedTextField(
                        value = message,
                        onValueChange = onMessageChange,
                        placeholder = {
                            Text(
                                "e.g. Two people trapped near east gate",
                                style = MaterialTheme.typography.bodySmall,
                                color = TextMutedSlate
                            )
                        },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = InkBlueElevated,
                            unfocusedContainerColor = InkBlueElevated,
                            focusedBorderColor = ElectricCyan,
                            unfocusedBorderColor = InkBlueBorder,
                            focusedTextColor = TextOffWhite,
                            unfocusedTextColor = TextOffWhite
                        ),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("sos_optional_message_input")
                    )
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Supporting Text
            Text(
                text = "Your SOS will be saved and relayed through nearby devices when available.",
                style = MaterialTheme.typography.bodySmall,
                color = TextMutedSlate,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))
        }

        // Action: Large Coral Send Button
        Button(
            onClick = onSendSos,
            colors = ButtonDefaults.buttonColors(
                containerColor = CoralRed,
                contentColor = TextOffWhite
            ),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp)
                .testTag("send_emergency_sos_btn")
        ) {
            Icon(
                imageVector = Icons.Default.CrisisAlert,
                contentDescription = null,
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Send Emergency SOS",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

// ==========================================
// STEP 3: Citizen SOS Status Screen
// ==========================================

@Composable
fun CitizenSosStatusScreen(
    incident: SosIncident,
    onSimulateRelay: () -> Unit,
    onSimulateGateway: () -> Unit,
    onViewConnectionDetails: () -> Unit,
    onBackToHome: () -> Unit
) {
    var showUpdateDialog by remember { mutableStateOf(false) }
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MidnightNavy)
    ) {
        // Professional Polish Header
        Surface(
            color = InkBlueSurface,
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, InkBlueBorder, RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp))
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 14.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(
                        onClick = onBackToHome,
                        modifier = Modifier.size(36.dp).testTag("sos_status_back_btn")
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Home",
                            tint = TextOffWhite
                        )
                    }
                    Spacer(modifier = Modifier.width(6.dp))
                    Column {
                        Text(
                            text = "DISASTER MESH",
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 10.sp,
                            letterSpacing = 1.5.sp,
                            color = TextMutedSlate
                        )
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "SOS Status",
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold,
                                color = TextOffWhite,
                                modifier = Modifier.testTag("sos_status_title")
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Surface(
                                color = ElectricCyan,
                                shape = RoundedCornerShape(4.dp)
                            ) {
                                Text(
                                    text = "CITIZEN",
                                    style = MaterialTheme.typography.labelSmall,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 10.sp,
                                    color = MidnightNavy,
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                )
                            }
                        }
                    }
                }

                // Mesh Available Chip
                Surface(
                    color = InkBlueSurface,
                    shape = RoundedCornerShape(50),
                    modifier = Modifier.border(1.dp, ElectricCyan.copy(alpha = 0.3f), RoundedCornerShape(50))
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(8.dp)
                                .background(ElectricCyan, CircleShape)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "Mesh Available",
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Medium,
                            fontSize = 11.sp,
                            color = ElectricCyan
                        )
                    }
                }
            }
        }

        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(scrollState)
                .padding(horizontal = 16.dp, vertical = 14.dp)
        ) {
            // Main Incident & Delivery Status Card
            Card(
                colors = CardDefaults.cardColors(containerColor = InkBlueSurface),
                shape = RoundedCornerShape(14.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, TextMutedSlate.copy(alpha = 0.15f), RoundedCornerShape(14.dp))
                    .testTag("sos_status_main_card")
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.Top
                    ) {
                        Column {
                            Text(
                                text = "Incident ID",
                                style = MaterialTheme.typography.labelSmall,
                                fontSize = 11.sp,
                                color = TextMutedSlate
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = incident.id,
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = ElectricCyan
                            )
                        }
                        Column(horizontalAlignment = Alignment.End) {
                            Text(
                                text = "STATUS",
                                style = MaterialTheme.typography.labelSmall,
                                fontSize = 10.sp,
                                letterSpacing = 1.sp,
                                color = TextMutedSlate
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = incident.deliveryState.chipLabel,
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.SemiBold,
                                color = when (incident.deliveryState) {
                                    SosDeliveryState.RESPONDER_ACKNOWLEDGED -> SignalGreen
                                    SosDeliveryState.GATEWAY_RECEIVED -> ElectricCyan
                                    else -> WarmAmber
                                }
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Embedded Delivery Timeline
                    VerticalEvidenceTimeline(
                        currentState = incident.deliveryState,
                        auditSteps = incident.auditTrail
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Warning / Context Card
            Card(
                colors = CardDefaults.cardColors(containerColor = InkBlueSurface),
                shape = RoundedCornerShape(14.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, WarmAmber.copy(alpha = 0.25f), RoundedCornerShape(14.dp))
                    .testTag("sos_info_banner")
            ) {
                Row(
                    modifier = Modifier.padding(14.dp),
                    verticalAlignment = Alignment.Top
                ) {
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .background(WarmAmber.copy(alpha = 0.12f), RoundedCornerShape(10.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Warning,
                            contentDescription = null,
                            tint = WarmAmber,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = when (incident.deliveryState) {
                                SosDeliveryState.RESPONDER_ACKNOWLEDGED -> "DISPATCH CONFIRMED"
                                SosDeliveryState.GATEWAY_RECEIVED -> "GATEWAY INGESTION"
                                else -> "CONNECTION WARNING"
                            },
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 1.sp,
                            color = when (incident.deliveryState) {
                                SosDeliveryState.RESPONDER_ACKNOWLEDGED -> SignalGreen
                                SosDeliveryState.GATEWAY_RECEIVED -> ElectricCyan
                                else -> WarmAmber
                            }
                        )
                        Spacer(modifier = Modifier.height(3.dp))
                        Text(
                            text = when (incident.deliveryState) {
                                SosDeliveryState.SAVED_LOCALLY -> "No official responder gateway has received this message yet. Your device is actively looking for nearby volunteer relays."
                                SosDeliveryState.RELAY_ACCEPTED -> "A nearby peer device received your SOS packet and is forwarding it toward an emergency response gateway."
                                SosDeliveryState.GATEWAY_RECEIVED -> "Packet reached station gateway node. Pending verification by on-duty dispatchers."
                                SosDeliveryState.RESPONDER_ACKNOWLEDGED -> "A verified response unit has reviewed your emergency request and initiated coordination."
                            },
                            style = MaterialTheme.typography.bodySmall,
                            color = TextMutedSlate,
                            lineHeight = 17.sp
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // 2-Column Details Grid
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Card(
                    colors = CardDefaults.cardColors(containerColor = InkBlueSurface),
                    shape = RoundedCornerShape(14.dp),
                    modifier = Modifier
                        .weight(1f)
                        .border(1.dp, TextMutedSlate.copy(alpha = 0.2f), RoundedCornerShape(14.dp))
                        .testTag("sos_emergency_card")
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 14.dp, horizontal = 12.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "EMERGENCY",
                            style = MaterialTheme.typography.labelSmall,
                            fontSize = 10.sp,
                            letterSpacing = 1.5.sp,
                            color = TextMutedSlate
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = incident.category,
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Bold,
                            color = TextOffWhite,
                            textAlign = TextAlign.Center
                        )
                    }
                }

                Card(
                    colors = CardDefaults.cardColors(containerColor = InkBlueSurface),
                    shape = RoundedCornerShape(14.dp),
                    modifier = Modifier
                        .weight(1f)
                        .border(1.dp, TextMutedSlate.copy(alpha = 0.2f), RoundedCornerShape(14.dp))
                        .testTag("sos_affected_card")
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 14.dp, horizontal = 12.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "AFFECTED",
                            style = MaterialTheme.typography.labelSmall,
                            fontSize = 10.sp,
                            letterSpacing = 1.5.sp,
                            color = TextMutedSlate
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "${incident.peopleAffected} People",
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Bold,
                            color = TextOffWhite,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Interactive Simulation Controls for Prototype Testing
            Card(
                colors = CardDefaults.cardColors(containerColor = InkBlueElevated),
                shape = RoundedCornerShape(14.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, ElectricCyan.copy(alpha = 0.3f), RoundedCornerShape(14.dp))
                    .testTag("prototype_simulation_card")
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Mesh Relay Simulation",
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.Bold,
                            color = ElectricCyan
                        )
                        Text(
                            text = "Interactive Test",
                            style = MaterialTheme.typography.labelSmall,
                            color = TextMutedSlate
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Button(
                            onClick = onSimulateRelay,
                            enabled = incident.deliveryState == SosDeliveryState.SAVED_LOCALLY,
                            colors = ButtonDefaults.buttonColors(
                                containerColor = WarmAmber,
                                contentColor = TextDarkOnAccent,
                                disabledContainerColor = InkBlueSurface,
                                disabledContentColor = TextMutedSlate
                            ),
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier
                                .weight(1f)
                                .height(40.dp)
                                .testTag("sim_relay_accepted_btn")
                        ) {
                            Text("1. Relay Hop", style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold)
                        }

                        Button(
                            onClick = onSimulateGateway,
                            enabled = incident.deliveryState == SosDeliveryState.RELAY_ACCEPTED,
                            colors = ButtonDefaults.buttonColors(
                                containerColor = ElectricCyan,
                                contentColor = TextDarkOnAccent,
                                disabledContainerColor = InkBlueSurface,
                                disabledContentColor = TextMutedSlate
                            ),
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier
                                .weight(1f)
                                .height(40.dp)
                                .testTag("sim_gateway_received_btn")
                        ) {
                            Text("2. Gateway Ingest", style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Action Buttons
            Button(
                onClick = { showUpdateDialog = true },
                colors = ButtonDefaults.buttonColors(
                    containerColor = WarmAmber,
                    contentColor = MidnightNavy
                ),
                shape = RoundedCornerShape(14.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
                    .testTag("update_sos_btn")
            ) {
                Text(
                    text = "Update SOS Details",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 0.5.sp
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            Button(
                onClick = onViewConnectionDetails,
                colors = ButtonDefaults.buttonColors(
                    containerColor = InkBlueSurface,
                    contentColor = ElectricCyan
                ),
                shape = RoundedCornerShape(14.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(46.dp)
                    .border(1.dp, ElectricCyan.copy(alpha = 0.4f), RoundedCornerShape(14.dp))
                    .testTag("view_connection_details_btn")
            ) {
                Icon(
                    imageVector = Icons.Default.SignalCellularAlt,
                    contentDescription = null,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "View Mesh & Network Info",
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.SemiBold
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Your SOS is safely stored on this phone and shared anonymously with nearby Disaster Mesh devices to reach official responders.",
                style = MaterialTheme.typography.labelSmall,
                color = TextMutedSlate,
                textAlign = TextAlign.Center,
                fontSize = 11.sp,
                lineHeight = 15.sp,
                modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp)
            )

            Spacer(modifier = Modifier.height(12.dp))
        }
    }

    if (showUpdateDialog) {
        AlertDialog(
            onDismissRequest = { showUpdateDialog = false },
            containerColor = InkBlueSurface,
            shape = RoundedCornerShape(16.dp),
            title = {
                Text(
                    text = "Update Active SOS",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = TextOffWhite
                )
            },
            text = {
                Text(
                    text = "You can attach updated situational notes. Updated packets will increment the version sequence on the mesh relay network.",
                    style = MaterialTheme.typography.bodySmall,
                    color = TextMutedSlate
                )
            },
            confirmButton = {
                Button(
                    onClick = { showUpdateDialog = false },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = WarmAmber,
                        contentColor = TextDarkOnAccent
                    ),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Text("OK", fontWeight = FontWeight.Bold)
                }
            }
        )
    }
}

@Composable
fun MetadataRow(
    label: String,
    value: String,
    isHighlight: Boolean = false
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Top
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            color = TextMutedSlate
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodySmall,
            fontWeight = if (isHighlight) FontWeight.Bold else FontWeight.Medium,
            color = if (isHighlight) ElectricCyan else TextOffWhite,
            textAlign = TextAlign.End,
            modifier = Modifier.padding(start = 12.dp)
        )
    }
}
