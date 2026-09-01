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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddPhotoAlternate
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CrisisAlert
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Warning
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.CoralRed
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

@Composable
fun ReportHazardScreen(
    onSubmitReport: (category: String, location: String, description: String, people: String?, hasPhoto: Boolean) -> Unit,
    onBack: () -> Unit,
    onSwitchToSos: () -> Unit
) {
    var selectedCategory by remember { mutableStateOf("Flooded Road") }
    var locationInput by remember { mutableStateOf("Riverside Road (Underpass), Pune") }
    var peopleInput by remember { mutableStateOf("") }
    var descriptionInput by remember { mutableStateOf("") }
    var hasPhotoAttached by remember { mutableStateOf(false) }

    val categories = listOf(
        "Flooded Road",
        "Fire",
        "Damaged Building",
        "Blocked Route",
        "Medical Need",
        "Unsafe Area",
        "Other"
    )

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MidnightNavy)
            .padding(horizontal = 16.dp, vertical = 14.dp)
    ) {
        // Header Row
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = onBack,
                modifier = Modifier.testTag("report_hazard_back_btn")
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = TextOffWhite
                )
            }
            Text(
                text = "Report a Hazard",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = TextOffWhite,
                modifier = Modifier.testTag("report_hazard_title")
            )
            Spacer(modifier = Modifier.width(48.dp))
        }

        Spacer(modifier = Modifier.height(10.dp))

        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(scrollState)
        ) {
            // Immediate Danger Notice
            Card(
                colors = CardDefaults.cardColors(containerColor = InkBlueElevated),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, CoralRed.copy(alpha = 0.4f), RoundedCornerShape(12.dp))
                    .clickable { onSwitchToSos() }
                    .testTag("danger_notice_banner")
            ) {
                Row(
                    modifier = Modifier.padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.CrisisAlert,
                        contentDescription = null,
                        tint = CoralRed,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "For immediate danger, use Emergency SOS.",
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                        color = CoralRed
                    )
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Category Selection Chips
            Text(
                text = "Hazard Category",
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.Bold,
                color = TextOffWhite
            )

            Spacer(modifier = Modifier.height(8.dp))

            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth().testTag("hazard_categories_row")
            ) {
                items(categories) { cat ->
                    val isSelected = selectedCategory == cat
                    Surface(
                        color = if (isSelected) WarmAmber else InkBlueSurface,
                        shape = RoundedCornerShape(20.dp),
                        modifier = Modifier
                            .border(
                                1.dp,
                                if (isSelected) WarmAmber else InkBlueBorder,
                                RoundedCornerShape(20.dp)
                            )
                            .clickable { selectedCategory = cat }
                            .testTag("hazard_chip_${cat.lowercase().replace(' ', '_')}")
                    ) {
                        Text(
                            text = cat,
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                            color = if (isSelected) TextDarkOnAccent else TextOffWhite,
                            modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Current Location Field
            Text(
                text = "Current Location",
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.Bold,
                color = TextOffWhite
            )
            Spacer(modifier = Modifier.height(6.dp))
            OutlinedTextField(
                value = locationInput,
                onValueChange = { locationInput = it },
                leadingIcon = {
                    Icon(Icons.Default.LocationOn, contentDescription = null, tint = ElectricCyan)
                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = InkBlueSurface,
                    unfocusedContainerColor = InkBlueSurface,
                    focusedBorderColor = ElectricCyan,
                    unfocusedBorderColor = InkBlueBorder,
                    focusedTextColor = TextOffWhite,
                    unfocusedTextColor = TextOffWhite
                ),
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier.fillMaxWidth().testTag("hazard_location_input")
            )

            Spacer(modifier = Modifier.height(14.dp))

            // People Affected (Optional)
            Text(
                text = "People affected (Optional)",
                style = MaterialTheme.typography.labelMedium,
                color = TextMutedSlate
            )
            Spacer(modifier = Modifier.height(6.dp))
            OutlinedTextField(
                value = peopleInput,
                onValueChange = { peopleInput = it },
                placeholder = { Text("e.g. 0 or approx count", color = TextMutedSlate, fontSize = 13.sp) },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = InkBlueSurface,
                    unfocusedContainerColor = InkBlueSurface,
                    focusedBorderColor = ElectricCyan,
                    unfocusedBorderColor = InkBlueBorder,
                    focusedTextColor = TextOffWhite,
                    unfocusedTextColor = TextOffWhite
                ),
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier.fillMaxWidth().testTag("hazard_people_input")
            )

            Spacer(modifier = Modifier.height(14.dp))

            // Short Description
            Text(
                text = "Description",
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.Bold,
                color = TextOffWhite
            )
            Spacer(modifier = Modifier.height(6.dp))
            OutlinedTextField(
                value = descriptionInput,
                onValueChange = { descriptionInput = it },
                placeholder = {
                    Text(
                        "Describe hazards (e.g., downed power lines, rising water level, impassable road)",
                        color = TextMutedSlate,
                        fontSize = 13.sp
                    )
                },
                minLines = 3,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = InkBlueSurface,
                    unfocusedContainerColor = InkBlueSurface,
                    focusedBorderColor = ElectricCyan,
                    unfocusedBorderColor = InkBlueBorder,
                    focusedTextColor = TextOffWhite,
                    unfocusedTextColor = TextOffWhite
                ),
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier.fillMaxWidth().testTag("hazard_description_input")
            )

            Spacer(modifier = Modifier.height(14.dp))

            // Photo Attachment Box
            Card(
                colors = CardDefaults.cardColors(containerColor = InkBlueSurface),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .border(
                        1.dp,
                        if (hasPhotoAttached) SignalGreen else InkBlueBorder,
                        RoundedCornerShape(12.dp)
                    )
                    .clickable { hasPhotoAttached = !hasPhotoAttached }
                    .testTag("hazard_photo_attachment_box")
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = if (hasPhotoAttached) Icons.Default.Check else Icons.Default.CameraAlt,
                                contentDescription = null,
                                tint = if (hasPhotoAttached) SignalGreen else WarmAmber,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = if (hasPhotoAttached) "Photo Attached (compressed)" else "Attach Compressed Photo (Optional)",
                                style = MaterialTheme.typography.labelMedium,
                                fontWeight = FontWeight.Bold,
                                color = if (hasPhotoAttached) SignalGreen else TextOffWhite
                            )
                        }
                        if (hasPhotoAttached) {
                            Surface(color = SignalGreen.copy(alpha = 0.2f), shape = RoundedCornerShape(6.dp)) {
                                Text(
                                    text = "34 KB",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = SignalGreen,
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                )
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Avoid large files in low connectivity. Images are aggressively compressed for mesh bandwidth.",
                        style = MaterialTheme.typography.bodySmall,
                        color = TextMutedSlate,
                        fontSize = 11.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(18.dp))
        }

        // Amber Save & Relay Button
        Button(
            onClick = {
                onSubmitReport(
                    selectedCategory,
                    locationInput,
                    descriptionInput.ifBlank { "Hazard reported in sector" },
                    peopleInput.ifBlank { null },
                    hasPhotoAttached
                )
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = WarmAmber,
                contentColor = TextDarkOnAccent
            ),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .testTag("save_and_relay_hazard_btn")
        ) {
            Icon(Icons.Default.Warning, contentDescription = null, modifier = Modifier.size(18.dp))
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Save and Relay Report",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
        }
    }
}
