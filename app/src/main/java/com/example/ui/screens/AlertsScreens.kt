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
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CrisisAlert
import androidx.compose.material.icons.filled.DomainDisabled
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.ReportProblem
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.Tsunami
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.EmergencyAlert
import com.example.data.model.HazardReport
import com.example.data.model.UserRole
import com.example.ui.components.AppHeader
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
// SCREEN 10: Nearby Alerts
// ==========================================

@Composable
fun AlertsScreen(
    role: UserRole,
    alerts: List<EmergencyAlert>,
    communityReports: List<HazardReport>,
    onCreateAlertClick: () -> Unit,
    onSwitchRole: () -> Unit
) {
    var selectedTab by remember { mutableIntStateOf(0) } // 0: Verified Alerts, 1: Community Reports
    var selectedFilter by remember { mutableStateOf("All") }

    val filterOptions = listOf("All", "Flood", "Fire", "Shelter", "Road Closure")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MidnightNavy)
    ) {
        // App Header
        AppHeader(
            role = role,
            connectivityText = "Mesh Available",
            isConnectivityActive = true,
            onRoleClick = onSwitchRole
        )

        // Tab Row: Verified Alerts vs Community Reports
        TabRow(
            selectedTabIndex = selectedTab,
            containerColor = InkBlueSurface,
            contentColor = TextOffWhite,
            indicator = { tabPositions ->
                TabRowDefaults.SecondaryIndicator(
                    modifier = Modifier.tabIndicatorOffset(tabPositions[selectedTab]),
                    color = if (selectedTab == 0) ElectricCyan else WarmAmber,
                    height = 3.dp
                )
            },
            divider = {
                Box(modifier = Modifier.fillMaxWidth().height(1.dp).background(InkBlueBorder))
            }
        ) {
            Tab(
                selected = selectedTab == 0,
                onClick = { selectedTab = 0 },
                text = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Verified,
                            contentDescription = null,
                            tint = if (selectedTab == 0) ElectricCyan else TextMutedSlate,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "Verified Alerts",
                            fontWeight = if (selectedTab == 0) FontWeight.Bold else FontWeight.Normal,
                            color = if (selectedTab == 0) TextOffWhite else TextMutedSlate
                        )
                    }
                },
                modifier = Modifier.testTag("tab_verified_alerts")
            )

            Tab(
                selected = selectedTab == 1,
                onClick = { selectedTab = 1 },
                text = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.ReportProblem,
                            contentDescription = null,
                            tint = if (selectedTab == 1) WarmAmber else TextMutedSlate,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "Community Reports",
                            fontWeight = if (selectedTab == 1) FontWeight.Bold else FontWeight.Normal,
                            color = if (selectedTab == 1) TextOffWhite else TextMutedSlate
                        )
                    }
                },
                modifier = Modifier.testTag("tab_community_reports")
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
                            if (isSelected) ElectricCyan else InkBlueBorder,
                            RoundedCornerShape(16.dp)
                        )
                        .clickable { selectedFilter = filter }
                        .testTag("filter_chip_${filter.lowercase().replace(' ', '_')}")
                ) {
                    Text(
                        text = filter,
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                        color = if (isSelected) ElectricCyan else TextMutedSlate,
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Content Area with Verified Responder "+ Create Alert" floating button
        Box(modifier = Modifier.fillMaxSize()) {
            if (selectedTab == 0) {
                // Verified Alerts Tab
                val filteredAlerts = alerts.filter {
                    if (selectedFilter == "All") true
                    else if (selectedFilter == "Flood") it.type.contains("Flood", ignoreCase = true)
                    else if (selectedFilter == "Fire") it.type.contains("Fire", ignoreCase = true)
                    else if (selectedFilter == "Shelter") it.type.contains("Shelter", ignoreCase = true)
                    else if (selectedFilter == "Road Closure") it.headline.contains("Road", ignoreCase = true)
                    else true
                }

                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(filteredAlerts) { alert ->
                        VerifiedAlertCard(alert = alert)
                    }
                    item { Spacer(modifier = Modifier.height(80.dp)) }
                }
            } else {
                // Community Reports Tab (Neutral dark cards with "Unverified report" label)
                val filteredCommunity = communityReports.filter {
                    if (selectedFilter == "All") true
                    else if (selectedFilter == "Flood") it.category.contains("Flood", ignoreCase = true)
                    else if (selectedFilter == "Fire") it.category.contains("Fire", ignoreCase = true)
                    else if (selectedFilter == "Road Closure") it.category.contains("Blocked", ignoreCase = true) || it.category.contains("Road", ignoreCase = true)
                    else true
                }

                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(filteredCommunity) { report ->
                        CommunityReportCard(report = report)
                    }
                    item { Spacer(modifier = Modifier.height(80.dp)) }
                }
            }

            // Create Verified Alert button for Verified Responders
            if (role == UserRole.RESPONDER) {
                FloatingActionButton(
                    onClick = onCreateAlertClick,
                    containerColor = CoralRed,
                    contentColor = TextOffWhite,
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(20.dp)
                        .testTag("create_verified_alert_fab")
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(Icons.Default.Add, contentDescription = null)
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Create Alert", fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}

@Composable
fun VerifiedAlertCard(
    alert: EmergencyAlert,
    modifier: Modifier = Modifier
) {
    val hazardIcon = when {
        alert.type.contains("Flood", ignoreCase = true) -> Icons.Default.Tsunami
        alert.type.contains("Fire", ignoreCase = true) -> Icons.Default.LocalFireDepartment
        alert.type.contains("Shelter", ignoreCase = true) -> Icons.Default.Home
        else -> Icons.Default.Warning
    }

    Card(
        colors = CardDefaults.cardColors(containerColor = InkBlueSurface),
        shape = RoundedCornerShape(14.dp),
        modifier = modifier
            .fillMaxWidth()
            .border(1.5.dp, ElectricCyan.copy(alpha = 0.6f), RoundedCornerShape(14.dp))
            .testTag("verified_alert_card_${alert.id.lowercase()}")
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Verified Authority Badge & Alert Type
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    color = ElectricCyan.copy(alpha = 0.15f),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.border(1.dp, ElectricCyan.copy(alpha = 0.4f), RoundedCornerShape(12.dp))
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Verified,
                            contentDescription = "Verified Authority",
                            tint = ElectricCyan,
                            modifier = Modifier.size(14.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "Verified Authority",
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            color = ElectricCyan
                        )
                    }
                }

                Surface(
                    color = if (alert.severity == "CRITICAL") CoralRed.copy(alpha = 0.2f) else WarmAmber.copy(alpha = 0.2f),
                    shape = RoundedCornerShape(6.dp)
                ) {
                    Text(
                        text = alert.severity,
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                        color = if (alert.severity == "CRITICAL") CoralRed else WarmAmber,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Headline with Hazard Icon
            Row(verticalAlignment = Alignment.Top) {
                Icon(
                    imageVector = hazardIcon,
                    contentDescription = null,
                    tint = if (alert.severity == "CRITICAL") CoralRed else WarmAmber,
                    modifier = Modifier.size(24.dp).padding(top = 2.dp)
                )
                Spacer(modifier = Modifier.width(10.dp))
                Column {
                    Text(
                        text = alert.headline,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = TextOffWhite
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Instruction Callout
            Surface(
                color = InkBlueElevated,
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, InkBlueBorder, RoundedCornerShape(10.dp))
            ) {
                Column(modifier = Modifier.padding(10.dp)) {
                    Text(
                        text = "Instruction:",
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                        color = ElectricCyan
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = alert.instruction,
                        style = MaterialTheme.typography.bodySmall,
                        color = TextOffWhite,
                        lineHeight = 16.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Affected Area & Time Info
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.LocationOn,
                        contentDescription = null,
                        tint = TextMutedSlate,
                        modifier = Modifier.size(13.dp)
                    )
                    Spacer(modifier = Modifier.width(3.dp))
                    Text(
                        text = alert.affectedArea,
                        style = MaterialTheme.typography.labelSmall,
                        color = TextMutedSlate
                    )
                }
                Text(
                    text = "Expires ${alert.expiryTime}",
                    style = MaterialTheme.typography.labelSmall,
                    color = WarmAmber
                )
            }
        }
    }
}

@Composable
fun CommunityReportCard(
    report: HazardReport,
    modifier: Modifier = Modifier
) {
    // Neutral dark card that DOES NOT look like official alerts
    Card(
        colors = CardDefaults.cardColors(containerColor = InkBlueSurface),
        shape = RoundedCornerShape(14.dp),
        modifier = modifier
            .fillMaxWidth()
            .border(1.dp, InkBlueBorder, RoundedCornerShape(14.dp))
            .testTag("community_report_card_${report.id.lowercase()}")
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    color = InkBlueElevated,
                    shape = RoundedCornerShape(6.dp),
                    modifier = Modifier.border(1.dp, InkBlueBorder, RoundedCornerShape(6.dp))
                ) {
                    Text(
                        text = "Unverified report",
                        style = MaterialTheme.typography.labelSmall,
                        color = TextMutedSlate,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                }
                Text(
                    text = report.timeAgo,
                    style = MaterialTheme.typography.labelSmall,
                    color = TextMutedSlate
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "${report.category} • ${report.location}",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold,
                color = TextOffWhite
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = report.description,
                style = MaterialTheme.typography.bodySmall,
                color = TextMutedSlate,
                lineHeight = 16.sp
            )

            if (report.hasPhoto) {
                Spacer(modifier = Modifier.height(8.dp))
                Surface(
                    color = InkBlueElevated,
                    shape = RoundedCornerShape(6.dp)
                ) {
                    Text(
                        text = "📷 Photo attached (34 KB)",
                        style = MaterialTheme.typography.labelSmall,
                        color = ElectricCyan,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                }
            }
        }
    }
}

// ==========================================
// SCREEN 14: Create Verified Alert (Responders Only)
// ==========================================

@Composable
fun CreateVerifiedAlertScreen(
    onCreateAlert: (type: String, severity: String, headline: String, instruction: String, area: String, expiry: String, lang: String) -> Unit,
    onBack: () -> Unit
) {
    var alertType by remember { mutableStateOf("Flood Warning") }
    var severity by remember { mutableStateOf("CRITICAL") }
    var headline by remember { mutableStateOf("Flood water rising near Riverside Road") }
    var instruction by remember { mutableStateOf("Avoid Riverside Road. Move toward North Assembly Point.") }
    var targetArea by remember { mutableStateOf("Riverside Road & Sector 4") }
    var expiryTime by remember { mutableStateOf("06:00 PM") }
    var language by remember { mutableStateOf("English") }

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
                modifier = Modifier.testTag("create_alert_back_btn")
            ) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = TextOffWhite)
            }
            Text(
                text = "Create Verified Alert",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = TextOffWhite,
                modifier = Modifier.testTag("create_alert_title")
            )
            Surface(
                color = CoralRed.copy(alpha = 0.15f),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.border(1.dp, CoralRed.copy(alpha = 0.5f), RoundedCornerShape(8.dp))
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(Icons.Default.Lock, contentDescription = null, tint = CoralRed, modifier = Modifier.size(12.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Responder Auth", style = MaterialTheme.typography.labelSmall, color = CoralRed, fontWeight = FontWeight.Bold)
                }
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(scrollState)
        ) {
            // Alert Type & Severity
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text("Alert Type", style = MaterialTheme.typography.labelSmall, color = TextMutedSlate)
                    Spacer(modifier = Modifier.height(4.dp))
                    OutlinedTextField(
                        value = alertType,
                        onValueChange = { alertType = it },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = InkBlueSurface,
                            unfocusedContainerColor = InkBlueSurface,
                            focusedBorderColor = ElectricCyan,
                            unfocusedBorderColor = InkBlueBorder,
                            focusedTextColor = TextOffWhite,
                            unfocusedTextColor = TextOffWhite
                        ),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                Column(modifier = Modifier.weight(1f)) {
                    Text("Severity", style = MaterialTheme.typography.labelSmall, color = TextMutedSlate)
                    Spacer(modifier = Modifier.height(4.dp))
                    OutlinedTextField(
                        value = severity,
                        onValueChange = { severity = it },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = InkBlueSurface,
                            unfocusedContainerColor = InkBlueSurface,
                            focusedBorderColor = CoralRed,
                            unfocusedBorderColor = InkBlueBorder,
                            focusedTextColor = CoralRed,
                            unfocusedTextColor = CoralRed
                        ),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Headline
            Text("Headline", style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.Bold, color = TextOffWhite)
            Spacer(modifier = Modifier.height(4.dp))
            OutlinedTextField(
                value = headline,
                onValueChange = { headline = it },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = InkBlueSurface,
                    unfocusedContainerColor = InkBlueSurface,
                    focusedBorderColor = ElectricCyan,
                    unfocusedBorderColor = InkBlueBorder,
                    focusedTextColor = TextOffWhite,
                    unfocusedTextColor = TextOffWhite
                ),
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier.fillMaxWidth().testTag("alert_headline_input")
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Instruction
            Text("Instruction", style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.Bold, color = TextOffWhite)
            Spacer(modifier = Modifier.height(4.dp))
            OutlinedTextField(
                value = instruction,
                onValueChange = { instruction = it },
                minLines = 2,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = InkBlueSurface,
                    unfocusedContainerColor = InkBlueSurface,
                    focusedBorderColor = ElectricCyan,
                    unfocusedBorderColor = InkBlueBorder,
                    focusedTextColor = TextOffWhite,
                    unfocusedTextColor = TextOffWhite
                ),
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier.fillMaxWidth().testTag("alert_instruction_input")
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Target Area & Expiry Time
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Column(modifier = Modifier.weight(1.2f)) {
                    Text("Target Area", style = MaterialTheme.typography.labelSmall, color = TextMutedSlate)
                    Spacer(modifier = Modifier.height(4.dp))
                    OutlinedTextField(
                        value = targetArea,
                        onValueChange = { targetArea = it },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = InkBlueSurface,
                            unfocusedContainerColor = InkBlueSurface,
                            focusedBorderColor = ElectricCyan,
                            unfocusedBorderColor = InkBlueBorder,
                            focusedTextColor = TextOffWhite,
                            unfocusedTextColor = TextOffWhite
                        ),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                Column(modifier = Modifier.weight(0.8f)) {
                    Text("Expiry Time", style = MaterialTheme.typography.labelSmall, color = TextMutedSlate)
                    Spacer(modifier = Modifier.height(4.dp))
                    OutlinedTextField(
                        value = expiryTime,
                        onValueChange = { expiryTime = it },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = InkBlueSurface,
                            unfocusedContainerColor = InkBlueSurface,
                            focusedBorderColor = WarmAmber,
                            unfocusedBorderColor = InkBlueBorder,
                            focusedTextColor = TextOffWhite,
                            unfocusedTextColor = TextOffWhite
                        ),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            // Live Citizen Alert Preview
            Text(
                text = "Live Citizen Alert Preview",
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.Bold,
                color = ElectricCyan
            )

            Spacer(modifier = Modifier.height(8.dp))

            VerifiedAlertCard(
                alert = EmergencyAlert(
                    id = "PREVIEW",
                    type = alertType,
                    severity = severity,
                    headline = headline,
                    instruction = instruction,
                    affectedArea = targetArea,
                    issuedTime = "Just now",
                    expiryTime = expiryTime,
                    isAuthorityVerified = true,
                    authorityName = "Disaster Management Authority, Pune"
                )
            )

            Spacer(modifier = Modifier.height(14.dp))

            // Governance Note
            Card(
                colors = CardDefaults.cardColors(containerColor = InkBlueElevated),
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, InkBlueBorder, RoundedCornerShape(10.dp))
            ) {
                Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Security, contentDescription = null, tint = CoralRed, modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Governance: Only cryptographically verified responders can sign and send official alerts across the mesh.",
                        style = MaterialTheme.typography.bodySmall,
                        color = TextMutedSlate,
                        fontSize = 11.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
        }

        // Primary Action Button
        Button(
            onClick = {
                onCreateAlert(alertType, severity, headline, instruction, targetArea, expiryTime, language)
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = CoralRed,
                contentColor = TextOffWhite
            ),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .testTag("send_verified_alert_btn")
        ) {
            Icon(Icons.Default.Verified, contentDescription = null, modifier = Modifier.size(18.dp))
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Review and Send Verified Alert",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
        }
    }
}
