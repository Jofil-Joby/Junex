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
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.BatteryChargingFull
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.CrisisAlert
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.DomainDisabled
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.Handshake
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.LocalHospital
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.Tsunami
import androidx.compose.material.icons.filled.WifiTethering
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
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.UserRole
import com.example.ui.components.AppHeader
import com.example.ui.components.DisclaimerCard
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
// SCREEN 5: Offline Safety & Disaster Guide
// ==========================================

data class SafetyTopic(
    val id: String,
    val title: String,
    val summary: String,
    val content: String,
    val icon: ImageVector
)

@Composable
fun OfflineSafetyGuideScreen(
    onBack: () -> Unit
) {
    var searchQuery by remember { mutableStateOf("") }
    var expandedTopicId by remember { mutableStateOf<String?>(null) }
    val scrollState = rememberScrollState()

    val topics = listOf(
        SafetyTopic(
            id = "flood",
            title = "What to do in a flood",
            summary = "Evacuation routes, electrical dangers, high ground safety.",
            content = "1. Move immediately to higher ground.\n2. Do NOT walk, swim, or drive through flood waters (6 inches of moving water can knock you down).\n3. Stay off bridges over fast-moving water.\n4. Avoid contact with downed power lines and electrical cables.\n5. Boil or disinfect all drinking water before consumption.",
            icon = Icons.Default.Tsunami
        ),
        SafetyTopic(
            id = "earthquake",
            title = "Earthquakes and building collapse",
            summary = "Drop, cover, and hold on; structural hazard assessment.",
            content = "1. Drop onto hands and knees, cover head/neck under sturdy furniture, hold on until shaking stops.\n2. If trapped under debris: cover mouth with cloth, tap on pipes or walls rather than shouting to conserve energy and avoid inhaling dust.\n3. Stay clear of broken masonry, glass windows, and exterior facades.",
            icon = Icons.Default.DomainDisabled
        ),
        SafetyTopic(
            id = "fire",
            title = "Fire and smoke safety",
            summary = "Low crawl under smoke, assembly points, burn treatment.",
            content = "1. Stay low to the ground where air is cleaner and cooler.\n2. Feel closed doors with the back of your hand before opening—if warm, find an alternate route.\n3. Cover your nose and mouth with a wet cloth if possible.\n4. Once outside, stay outside at designated assembly points.",
            icon = Icons.Default.LocalFireDepartment
        ),
        SafetyTopic(
            id = "firstaid",
            title = "Emergency first aid basics",
            summary = "Bleeding control, recovery position, CPR rhythm.",
            content = "1. Direct Pressure: Apply firm, continuous pressure with a clean cloth directly on bleeding wounds.\n2. Recovery Position: Place unconscious breathing victims on their side to keep airways clear.\n3. Shock Management: Keep victim warm, calm, and lay them flat with feet slightly elevated if no spinal injury is suspected.",
            icon = Icons.Default.LocalHospital
        ),
        SafetyTopic(
            id = "mesh_limitations",
            title = "How Disaster Mesh works & limitations",
            summary = "Store-and-forward relay mechanics, what it does NOT do.",
            content = "• Disaster Mesh uses Bluetooth Low Energy and Wi-Fi Direct beacons to hop packets peer-to-peer.\n• Delivery depends on physical proximity of participating phones.\n• Disaster Mesh DOES NOT guarantee delivery.\n• Disaster Mesh DOES NOT replace official 112 emergency services, satellite communication, or radio dispatchers.",
            icon = Icons.Default.WifiTethering
        ),
        SafetyTopic(
            id = "battery",
            title = "Battery conservation during blackouts",
            summary = "Optimizing phone life during extended multi-day outages.",
            content = "1. Enable Emergency Battery Mode in Disaster Mesh.\n2. Turn off screen when not viewing status updates.\n3. Disable non-essential background app sync.\n4. Keep phone warm in cold weather and shaded from direct sunlight.",
            icon = Icons.Default.BatteryChargingFull
        )
    )

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
                modifier = Modifier.testTag("safety_guide_back_btn")
            ) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = TextOffWhite)
            }
            Text(
                text = "Offline Safety Guide",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = TextOffWhite,
                modifier = Modifier.testTag("safety_guide_title")
            )
            Surface(
                color = SignalGreen.copy(alpha = 0.15f),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.border(1.dp, SignalGreen.copy(alpha = 0.4f), RoundedCornerShape(8.dp))
            ) {
                Text(
                    text = "Offline Ready",
                    style = MaterialTheme.typography.labelSmall,
                    color = SignalGreen,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Search Bar
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            placeholder = { Text("Search offline safety topics...", color = TextMutedSlate, fontSize = 13.sp) },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = null, tint = TextMutedSlate) },
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = InkBlueSurface,
                unfocusedContainerColor = InkBlueSurface,
                focusedBorderColor = ElectricCyan,
                unfocusedBorderColor = InkBlueBorder,
                focusedTextColor = TextOffWhite,
                unfocusedTextColor = TextOffWhite
            ),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.fillMaxWidth().testTag("safety_search_input")
        )

        Spacer(modifier = Modifier.height(12.dp))

        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(scrollState)
        ) {
            // Emergency 112 Notice Card
            Card(
                colors = CardDefaults.cardColors(containerColor = InkBlueElevated),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, WarmAmber.copy(alpha = 0.5f), RoundedCornerShape(12.dp))
            ) {
                Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.CrisisAlert, contentDescription = null, tint = WarmAmber, modifier = Modifier.size(20.dp))
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = "If cellular network is available, call 112 directly for official emergency dispatch.",
                        style = MaterialTheme.typography.bodySmall,
                        color = TextOffWhite,
                        fontWeight = FontWeight.Medium
                    )
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Topics Accordion
            val filteredTopics = topics.filter {
                it.title.contains(searchQuery, ignoreCase = true) || it.summary.contains(searchQuery, ignoreCase = true)
            }

            filteredTopics.forEach { topic ->
                val isExpanded = expandedTopicId == topic.id

                Card(
                    colors = CardDefaults.cardColors(containerColor = InkBlueSurface),
                    shape = RoundedCornerShape(14.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                        .border(1.dp, if (isExpanded) ElectricCyan else InkBlueBorder, RoundedCornerShape(14.dp))
                        .clickable {
                            expandedTopicId = if (isExpanded) null else topic.id
                        }
                        .testTag("safety_topic_${topic.id}")
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.weight(1f)
                            ) {
                                Surface(
                                    color = ElectricCyan.copy(alpha = 0.15f),
                                    shape = CircleShape,
                                    modifier = Modifier.size(34.dp)
                                ) {
                                    Box(contentAlignment = Alignment.Center) {
                                        Icon(topic.icon, contentDescription = null, tint = ElectricCyan, modifier = Modifier.size(18.dp))
                                    }
                                }
                                Spacer(modifier = Modifier.width(10.dp))
                                Column {
                                    Text(
                                        text = topic.title,
                                        style = MaterialTheme.typography.titleSmall,
                                        fontWeight = FontWeight.Bold,
                                        color = TextOffWhite
                                    )
                                    Text(
                                        text = topic.summary,
                                        style = MaterialTheme.typography.bodySmall,
                                        color = TextMutedSlate,
                                        fontSize = 11.sp
                                    )
                                }
                            }

                            Icon(
                                imageVector = if (isExpanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                                contentDescription = null,
                                tint = TextMutedSlate
                            )
                        }

                        AnimatedVisibility(visible = isExpanded) {
                            Column(modifier = Modifier.padding(top = 12.dp)) {
                                Box(modifier = Modifier.fillMaxWidth().height(1.dp).background(InkBlueDivider))
                                Spacer(modifier = Modifier.height(10.dp))
                                Text(
                                    text = topic.content,
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = TextOffWhite,
                                    lineHeight = 20.sp
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            DisclaimerCard()

            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

// ==========================================
// SCREEN 15: Profile & Role Switcher
// ==========================================

@Composable
fun ProfileAndRoleScreen(
    currentRole: UserRole,
    onRoleChange: (UserRole) -> Unit,
    onBack: () -> Unit
) {
    var showClearDataDialog by remember { mutableStateOf(false) }
    var meshRelayEnabled by remember { mutableStateOf(true) }
    var compressPhotos by remember { mutableStateOf(true) }
    var batterySaver by remember { mutableStateOf(false) }
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
                modifier = Modifier.testTag("profile_back_btn")
            ) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = TextOffWhite)
            }
            Text(
                text = "Profile & Role Switcher",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = TextOffWhite,
                modifier = Modifier.testTag("profile_title")
            )
            Spacer(modifier = Modifier.width(48.dp))
        }

        Spacer(modifier = Modifier.height(10.dp))

        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(scrollState)
        ) {
            // Current Role Switcher Section
            Text(
                text = "Active App Mode / Role",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = TextOffWhite
            )
            Text(
                text = "Switch between participating roles in the unified Disaster Mesh app.",
                style = MaterialTheme.typography.bodySmall,
                color = TextMutedSlate
            )

            Spacer(modifier = Modifier.height(12.dp))

            UserRole.values().forEach { role ->
                val isSelected = currentRole == role
                val roleAccent = when (role) {
                    UserRole.CITIZEN -> ElectricCyan
                    UserRole.VOLUNTEER -> WarmAmber
                    UserRole.RESPONDER -> CoralRed
                }
                val roleIcon = when (role) {
                    UserRole.CITIZEN -> Icons.Default.Person
                    UserRole.VOLUNTEER -> Icons.Default.Handshake
                    UserRole.RESPONDER -> Icons.Default.Security
                }

                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = if (isSelected) InkBlueElevated else InkBlueSurface
                    ),
                    shape = RoundedCornerShape(14.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                        .border(
                            1.5.dp,
                            if (isSelected) roleAccent else InkBlueBorder,
                            RoundedCornerShape(14.dp)
                        )
                        .clickable { onRoleChange(role) }
                        .testTag("switch_role_${role.name.lowercase()}")
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(14.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Surface(
                                color = roleAccent.copy(alpha = 0.15f),
                                shape = CircleShape,
                                modifier = Modifier.size(36.dp)
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    Icon(roleIcon, contentDescription = null, tint = roleAccent, modifier = Modifier.size(20.dp))
                                }
                            }
                            Spacer(modifier = Modifier.width(12.dp))
                            Column {
                                Text(
                                    text = role.displayName,
                                    style = MaterialTheme.typography.titleSmall,
                                    fontWeight = FontWeight.Bold,
                                    color = TextOffWhite
                                )
                                Text(
                                    text = when (role) {
                                        UserRole.CITIZEN -> "Send SOS & receive alerts"
                                        UserRole.VOLUNTEER -> "Relay packets & nearby help"
                                        UserRole.RESPONDER -> "Official rescue incident queue"
                                    },
                                    style = MaterialTheme.typography.bodySmall,
                                    color = TextMutedSlate,
                                    fontSize = 11.sp
                                )
                            }
                        }

                        if (isSelected) {
                            Icon(Icons.Default.CheckCircle, contentDescription = "Active", tint = roleAccent)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            // Device Settings Section
            Text(
                text = "Mesh & Radio Settings",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = TextOffWhite
            )

            Spacer(modifier = Modifier.height(10.dp))

            Card(
                colors = CardDefaults.cardColors(containerColor = InkBlueSurface),
                shape = RoundedCornerShape(14.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, InkBlueBorder, RoundedCornerShape(14.dp))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    SettingToggleRow(
                        title = "Background Mesh Relaying",
                        subtitle = "Relay encrypted packets over BLE / Wi-Fi Direct",
                        isChecked = meshRelayEnabled,
                        onCheckedChange = { meshRelayEnabled = it }
                    )

                    Spacer(modifier = Modifier.height(10.dp))
                    Box(modifier = Modifier.fillMaxWidth().height(1.dp).background(InkBlueDivider))
                    Spacer(modifier = Modifier.height(10.dp))

                    SettingToggleRow(
                        title = "Aggressive Image Compression",
                        subtitle = "Keep attachments under 50 KB for peer hops",
                        isChecked = compressPhotos,
                        onCheckedChange = { compressPhotos = it }
                    )

                    Spacer(modifier = Modifier.height(10.dp))
                    Box(modifier = Modifier.fillMaxWidth().height(1.dp).background(InkBlueDivider))
                    Spacer(modifier = Modifier.height(10.dp))

                    SettingToggleRow(
                        title = "Power Conservation Mode",
                        subtitle = "Throttle non-critical radio beacons",
                        isChecked = batterySaver,
                        onCheckedChange = { batterySaver = it }
                    )
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            // Storage & Data
            Card(
                colors = CardDefaults.cardColors(containerColor = InkBlueSurface),
                shape = RoundedCornerShape(14.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, InkBlueBorder, RoundedCornerShape(14.dp))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Local Offline Cache",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold,
                        color = TextOffWhite
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "1 cached SOS packet • 3 safety guides • 4 map tiles cached",
                        style = MaterialTheme.typography.bodySmall,
                        color = TextMutedSlate
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Button(
                        onClick = { showClearDataDialog = true },
                        colors = ButtonDefaults.buttonColors(containerColor = InkBlueElevated, contentColor = CoralRed),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(1.dp, CoralRed.copy(alpha = 0.4f), RoundedCornerShape(8.dp))
                            .testTag("clear_offline_cache_btn")
                    ) {
                        Icon(Icons.Default.Delete, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Clear Local Cache", style = MaterialTheme.typography.labelSmall)
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            DisclaimerCard()

            Spacer(modifier = Modifier.height(20.dp))
        }
    }

    if (showClearDataDialog) {
        AlertDialog(
            onDismissRequest = { showClearDataDialog = false },
            containerColor = InkBlueSurface,
            title = {
                Text("Clear Offline Cache?", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = TextOffWhite)
            },
            text = {
                Text(
                    "This will clear locally cached maps and situational data on this device.",
                    style = MaterialTheme.typography.bodySmall,
                    color = TextMutedSlate
                )
            },
            confirmButton = {
                Button(
                    onClick = { showClearDataDialog = false },
                    colors = ButtonDefaults.buttonColors(containerColor = CoralRed, contentColor = TextOffWhite)
                ) {
                    Text("Clear")
                }
            },
            dismissButton = {
                TextButton(onClick = { showClearDataDialog = false }) {
                    Text("Cancel", color = TextMutedSlate)
                }
            }
        )
    }
}

@Composable
fun SettingToggleRow(
    title: String,
    subtitle: String,
    isChecked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(text = title, style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.Bold, color = TextOffWhite)
            Text(text = subtitle, style = MaterialTheme.typography.bodySmall, color = TextMutedSlate, fontSize = 11.sp)
        }
        Switch(
            checked = isChecked,
            onCheckedChange = onCheckedChange,
            colors = SwitchDefaults.colors(
                checkedThumbColor = TextDarkOnAccent,
                checkedTrackColor = ElectricCyan,
                uncheckedThumbColor = TextMutedSlate,
                uncheckedTrackColor = InkBlueElevated
            )
        )
    }
}
