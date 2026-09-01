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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Handshake
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Shield
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
import com.example.ui.components.DisclaimerCard
import com.example.ui.theme.CoralRed
import com.example.ui.theme.ElectricCyan
import com.example.ui.theme.InkBlueBorder
import com.example.ui.theme.InkBlueElevated
import com.example.ui.theme.InkBlueSurface
import com.example.ui.theme.MidnightNavy
import com.example.ui.theme.TextDarkOnAccent
import com.example.ui.theme.TextMutedSlate
import com.example.ui.theme.TextOffWhite
import com.example.ui.theme.WarmAmber

@Composable
fun OnboardingRoleScreen(
    initialRole: UserRole,
    onRoleSelected: (UserRole) -> Unit,
    onContinue: () -> Unit
) {
    var selectedRole by remember { mutableStateOf(initialRole) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MidnightNavy)
            .padding(horizontal = 20.dp, vertical = 24.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        // Title
        Text(
            text = "How would you like to participate?",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = TextOffWhite,
            modifier = Modifier.testTag("onboarding_title")
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Subtitle
        Text(
            text = "Your phone can help keep emergency information moving.",
            style = MaterialTheme.typography.bodyLarge,
            color = TextMutedSlate
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Three Large Role Cards
        RoleSelectionCard(
            role = UserRole.CITIZEN,
            title = "Citizen",
            description = "Send SOS, report hazards, and receive emergency alerts.",
            note = null,
            icon = Icons.Default.Person,
            isSelected = selectedRole == UserRole.CITIZEN,
            accentColor = ElectricCyan,
            onClick = { selectedRole = UserRole.CITIZEN }
        )

        Spacer(modifier = Modifier.height(14.dp))

        RoleSelectionCard(
            role = UserRole.VOLUNTEER,
            title = "Volunteer Helper",
            description = "Safely relay emergency messages and offer nearby assistance.",
            note = "You are not an official responder.",
            icon = Icons.Default.Handshake,
            isSelected = selectedRole == UserRole.VOLUNTEER,
            accentColor = WarmAmber,
            onClick = { selectedRole = UserRole.VOLUNTEER }
        )

        Spacer(modifier = Modifier.height(14.dp))

        RoleSelectionCard(
            role = UserRole.RESPONDER,
            title = "Verified Responder",
            description = "For approved rescue teams and emergency personnel.",
            note = "Verification required",
            icon = Icons.Default.Security,
            isSelected = selectedRole == UserRole.RESPONDER,
            accentColor = CoralRed,
            isLocked = true,
            onClick = { selectedRole = UserRole.RESPONDER }
        )

        Spacer(modifier = Modifier.height(20.dp))

        DisclaimerCard()

        Spacer(modifier = Modifier.height(28.dp))

        // Primary Amber Button
        Button(
            onClick = {
                onRoleSelected(selectedRole)
                onContinue()
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = WarmAmber,
                contentColor = TextDarkOnAccent
            ),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp)
                .testTag("onboarding_continue_btn")
        ) {
            Text(
                text = "Continue as ${selectedRole.displayName}",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
fun RoleSelectionCard(
    role: UserRole,
    title: String,
    description: String,
    note: String?,
    icon: ImageVector,
    isSelected: Boolean,
    accentColor: androidx.compose.ui.graphics.Color,
    isLocked: Boolean = false,
    onClick: () -> Unit
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) InkBlueElevated else InkBlueSurface
        ),
        shape = RoundedCornerShape(14.dp),
        modifier = Modifier
            .fillMaxWidth()
            .border(
                width = if (isSelected) 2.dp else 1.dp,
                color = if (isSelected) accentColor else InkBlueBorder,
                shape = RoundedCornerShape(14.dp)
            )
            .clickable { onClick() }
            .testTag("role_card_${role.name.lowercase()}")
    ) {
        Column(modifier = Modifier.padding(18.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Surface(
                        color = accentColor.copy(alpha = 0.15f),
                        shape = CircleShape,
                        modifier = Modifier
                            .size(38.dp)
                            .border(1.dp, accentColor.copy(alpha = 0.5f), CircleShape)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(
                                imageVector = icon,
                                contentDescription = title,
                                tint = accentColor,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = title,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = TextOffWhite
                    )
                }

                Surface(
                    color = if (isSelected) accentColor else InkBlueElevated,
                    shape = CircleShape,
                    modifier = Modifier
                        .size(24.dp)
                        .border(1.dp, if (isSelected) accentColor else InkBlueBorder, CircleShape)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        if (isSelected) {
                            Icon(
                                imageVector = Icons.Default.Check,
                                contentDescription = "Selected",
                                tint = TextDarkOnAccent,
                                modifier = Modifier.size(16.dp)
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = description,
                style = MaterialTheme.typography.bodyLarge,
                color = TextOffWhite.copy(alpha = 0.9f)
            )

            if (note != null) {
                Spacer(modifier = Modifier.height(10.dp))
                Surface(
                    color = MidnightNavy.copy(alpha = 0.7f),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.border(1.dp, InkBlueBorder, RoundedCornerShape(8.dp))
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        if (isLocked) {
                            Icon(
                                imageVector = Icons.Default.Lock,
                                contentDescription = "Verification required",
                                tint = CoralRed,
                                modifier = Modifier.size(12.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                        }
                        Text(
                            text = note,
                            style = MaterialTheme.typography.labelSmall,
                            color = if (isLocked) CoralRed else WarmAmber,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }
        }
    }
}
