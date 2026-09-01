package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CrisisAlert
import androidx.compose.material.icons.filled.Handshake
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ListAlt
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.SignalCellularAlt
import androidx.compose.material.icons.filled.Sync
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.data.model.AppScreen
import com.example.data.model.UserRole
import com.example.ui.screens.AlertsScreen
import com.example.ui.screens.CitizenHomeScreen
import com.example.ui.screens.CitizenSosStatusScreen
import com.example.ui.screens.ConnectionStatusScreen
import com.example.ui.screens.CreateVerifiedAlertScreen
import com.example.ui.screens.NearbyHelpDetailScreen
import com.example.ui.screens.OfflineSafetyGuideScreen
import com.example.ui.screens.OnboardingRoleScreen
import com.example.ui.screens.ProfileAndRoleScreen
import com.example.ui.screens.ReportHazardScreen
import com.example.ui.screens.ResponderIncidentDetailScreen
import com.example.ui.screens.ResponderIncidentQueueScreen
import com.example.ui.screens.SosCategorySelectionScreen
import com.example.ui.screens.SosConfirmScreen
import com.example.ui.screens.VolunteerHelpNearbyScreen
import com.example.ui.screens.VolunteerRelayActivityScreen
import com.example.ui.theme.CoralRed
import com.example.ui.theme.DisasterMeshTheme
import com.example.ui.theme.ElectricCyan
import com.example.ui.theme.InkBlueBorder
import com.example.ui.theme.InkBlueElevated
import com.example.ui.theme.InkBlueSurface
import com.example.ui.theme.MidnightNavy
import com.example.ui.theme.TextMutedSlate
import com.example.ui.theme.TextOffWhite
import com.example.ui.theme.WarmAmber
import com.example.viewmodel.DisasterMeshViewModel

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContent {
      DisasterMeshTheme {
        val vm: DisasterMeshViewModel = viewModel()
        DisasterMeshApp(viewModel = vm)
      }
    }
  }
}

data class NavigationTabItem(
  val screen: AppScreen,
  val label: String,
  val icon: ImageVector,
  val testTag: String
)

@Composable
fun DisasterMeshApp(viewModel: DisasterMeshViewModel) {
  val currentScreen by viewModel.currentScreen.collectAsState()
  val userRole by viewModel.userRole.collectAsState()
  val sosIncident by viewModel.currentSosIncident.collectAsState()
  val sosCategory by viewModel.sosSelectedCategory.collectAsState()
  val sosPeopleCount by viewModel.sosPeopleCount.collectAsState()
  val sosMessage by viewModel.sosMessage.collectAsState()
  val verifiedAlerts by viewModel.verifiedAlerts.collectAsState()
  val hazardReports by viewModel.hazardReports.collectAsState()
  val volunteerRequests by viewModel.volunteerRequests.collectAsState()
  val selectedRequest by viewModel.selectedVolunteerRequest.collectAsState()
  val relayStats by viewModel.relayStats.collectAsState()
  val responderIncidents by viewModel.responderIncidents.collectAsState()
  val selectedIncident by viewModel.selectedIncident.collectAsState()

  // Determine if bottom navigation bar should be shown
  val showBottomBar = currentScreen in listOf(
    AppScreen.CITIZEN_HOME,
    AppScreen.ALERTS,
    AppScreen.SAFETY_GUIDE,
    AppScreen.VOLUNTEER_HOME,
    AppScreen.VOLUNTEER_RELAY_ACTIVITY,
    AppScreen.RESPONDER_QUEUE,
    AppScreen.CONNECTION_STATUS,
    AppScreen.PROFILE_SETTINGS
  )

  // Configure tabs based on active role
  val bottomTabs = when (userRole) {
    UserRole.CITIZEN -> listOf(
      NavigationTabItem(AppScreen.CITIZEN_HOME, "Home", Icons.Default.Home, "nav_tab_citizen_home"),
      NavigationTabItem(AppScreen.ALERTS, "Alerts", Icons.Default.Notifications, "nav_tab_alerts"),
      NavigationTabItem(AppScreen.SAFETY_GUIDE, "Safety", Icons.Default.Shield, "nav_tab_safety"),
      NavigationTabItem(AppScreen.CONNECTION_STATUS, "Mesh", Icons.Default.SignalCellularAlt, "nav_tab_connection"),
      NavigationTabItem(AppScreen.PROFILE_SETTINGS, "Profile", Icons.Default.Person, "nav_tab_profile")
    )
    UserRole.VOLUNTEER -> listOf(
      NavigationTabItem(AppScreen.VOLUNTEER_HOME, "Help Nearby", Icons.Default.Handshake, "nav_tab_volunteer_home"),
      NavigationTabItem(AppScreen.VOLUNTEER_RELAY_ACTIVITY, "Relay", Icons.Default.Sync, "nav_tab_relay"),
      NavigationTabItem(AppScreen.ALERTS, "Alerts", Icons.Default.Notifications, "nav_tab_alerts"),
      NavigationTabItem(AppScreen.SAFETY_GUIDE, "Safety", Icons.Default.Shield, "nav_tab_safety"),
      NavigationTabItem(AppScreen.PROFILE_SETTINGS, "Profile", Icons.Default.Person, "nav_tab_profile")
    )
    UserRole.RESPONDER -> listOf(
      NavigationTabItem(AppScreen.RESPONDER_QUEUE, "Incidents", Icons.Default.ListAlt, "nav_tab_incidents"),
      NavigationTabItem(AppScreen.ALERTS, "Alerts", Icons.Default.Notifications, "nav_tab_alerts"),
      NavigationTabItem(AppScreen.CONNECTION_STATUS, "Gateway", Icons.Default.SignalCellularAlt, "nav_tab_gateway"),
      NavigationTabItem(AppScreen.SAFETY_GUIDE, "Safety", Icons.Default.Shield, "nav_tab_safety"),
      NavigationTabItem(AppScreen.PROFILE_SETTINGS, "Profile", Icons.Default.Person, "nav_tab_profile")
    )
  }

  Scaffold(
    modifier = Modifier.fillMaxSize(),
    containerColor = MidnightNavy,
    bottomBar = {
      if (showBottomBar) {
        NavigationBar(
          containerColor = InkBlueSurface,
          tonalElevation = 8.dp,
          modifier = Modifier
            .border(1.dp, InkBlueBorder, RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
            .testTag("app_bottom_nav_bar")
        ) {
          bottomTabs.forEach { tab ->
            val isSelected = currentScreen == tab.screen
            val activeColor = when (userRole) {
              UserRole.CITIZEN -> ElectricCyan
              UserRole.VOLUNTEER -> WarmAmber
              UserRole.RESPONDER -> CoralRed
            }

            NavigationBarItem(
              selected = isSelected,
              onClick = { viewModel.navigateTo(tab.screen) },
              icon = {
                Icon(
                  imageVector = tab.icon,
                  contentDescription = tab.label,
                  modifier = Modifier.size(20.dp)
                )
              },
              label = {
                Text(
                  text = tab.label,
                  style = MaterialTheme.typography.labelSmall,
                  fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                  fontSize = 11.sp
                )
              },
              colors = NavigationBarItemDefaults.colors(
                selectedIconColor = activeColor,
                selectedTextColor = activeColor,
                unselectedIconColor = TextMutedSlate,
                unselectedTextColor = TextMutedSlate,
                indicatorColor = InkBlueElevated
              ),
              modifier = Modifier.testTag(tab.testTag)
            )
          }
        }
      }
    }
  ) { innerPadding ->
    Box(
      modifier = Modifier
        .fillMaxSize()
        .padding(innerPadding)
        .background(MidnightNavy)
    ) {
      when (currentScreen) {
        AppScreen.ONBOARDING_ROLE -> {
          OnboardingRoleScreen(
            initialRole = userRole,
            onRoleSelected = { viewModel.setUserRole(it) },
            onContinue = { viewModel.completeOnboarding() }
          )
        }

        AppScreen.CITIZEN_HOME -> {
          CitizenHomeScreen(
            sosIncident = sosIncident,
            onStartSos = { viewModel.startSosFlow() },
            onViewActiveSos = { viewModel.navigateTo(AppScreen.CITIZEN_SOS_STATUS) },
            onReportHazard = { viewModel.navigateTo(AppScreen.REPORT_HAZARD) },
            onViewAlerts = { viewModel.navigateTo(AppScreen.ALERTS) },
            onSafetyGuide = { viewModel.navigateTo(AppScreen.SAFETY_GUIDE) },
            onSwitchRole = { viewModel.navigateTo(AppScreen.PROFILE_SETTINGS) }
          )
        }

        AppScreen.CITIZEN_SOS_STEP1_CATEGORY -> {
          SosCategorySelectionScreen(
            selectedCategory = sosCategory,
            onCategorySelected = { viewModel.setSosCategory(it) },
            onContinue = { viewModel.navigateTo(AppScreen.CITIZEN_SOS_STEP2_CONFIRM) },
            onBack = { viewModel.navigateTo(AppScreen.CITIZEN_HOME) }
          )
        }

        AppScreen.CITIZEN_SOS_STEP2_CONFIRM -> {
          SosConfirmScreen(
            category = sosCategory,
            peopleCount = sosPeopleCount,
            message = sosMessage,
            onPeopleCountChange = { viewModel.setSosPeopleCount(it) },
            onMessageChange = { viewModel.setSosMessage(it) },
            onSendSos = { viewModel.submitSos() },
            onBack = { viewModel.navigateTo(AppScreen.CITIZEN_SOS_STEP1_CATEGORY) }
          )
        }

        AppScreen.CITIZEN_SOS_STATUS -> {
          CitizenSosStatusScreen(
            incident = sosIncident,
            onSimulateRelay = { viewModel.simulateRelayHop() },
            onSimulateGateway = { viewModel.simulateGatewayReceived() },
            onViewConnectionDetails = { viewModel.navigateTo(AppScreen.CONNECTION_STATUS) },
            onBackToHome = { viewModel.navigateTo(AppScreen.CITIZEN_HOME) }
          )
        }

        AppScreen.REPORT_HAZARD -> {
          ReportHazardScreen(
            onSubmitReport = { category, location, description, people, hasPhoto ->
              viewModel.submitHazardReport(category, location, description, people, hasPhoto)
            },
            onBack = { viewModel.navigateTo(AppScreen.CITIZEN_HOME) },
            onSwitchToSos = { viewModel.startSosFlow() }
          )
        }

        AppScreen.VOLUNTEER_HOME -> {
          VolunteerHelpNearbyScreen(
            relayStats = relayStats,
            requests = volunteerRequests,
            onRequestClick = { viewModel.selectVolunteerRequest(it) },
            onViewRelayActivity = { viewModel.navigateTo(AppScreen.VOLUNTEER_RELAY_ACTIVITY) },
            onSafetyGuidance = { viewModel.navigateTo(AppScreen.SAFETY_GUIDE) },
            onSwitchRole = { viewModel.navigateTo(AppScreen.PROFILE_SETTINGS) }
          )
        }

        AppScreen.VOLUNTEER_HELP_DETAIL -> {
          selectedRequest?.let { req ->
            NearbyHelpDetailScreen(
              request = req,
              onOfferHelp = { viewModel.markVolunteerHelpOffered(req.id) },
              onRelayRequest = { viewModel.simulateRelayHop() },
              onShareSafetyGuidance = { viewModel.navigateTo(AppScreen.SAFETY_GUIDE) },
              onBack = { viewModel.navigateTo(AppScreen.VOLUNTEER_HOME) }
            )
          } ?: run {
            viewModel.navigateTo(AppScreen.VOLUNTEER_HOME)
          }
        }

        AppScreen.VOLUNTEER_RELAY_ACTIVITY -> {
          VolunteerRelayActivityScreen(
            relayStats = relayStats,
            onToggleBatteryMode = { viewModel.toggleEmergencyBatteryMode() },
            onToggleRelayMode = { viewModel.toggleRelayMode() },
            onBack = { viewModel.navigateTo(AppScreen.VOLUNTEER_HOME) }
          )
        }

        AppScreen.ALERTS -> {
          AlertsScreen(
            role = userRole,
            alerts = verifiedAlerts,
            communityReports = hazardReports,
            onCreateAlertClick = { viewModel.navigateTo(AppScreen.CREATE_VERIFIED_ALERT) },
            onSwitchRole = { viewModel.navigateTo(AppScreen.PROFILE_SETTINGS) }
          )
        }

        AppScreen.CREATE_VERIFIED_ALERT -> {
          CreateVerifiedAlertScreen(
            onCreateAlert = { type, severity, headline, instruction, area, expiry, lang ->
              viewModel.createVerifiedAlert(type, severity, headline, instruction, area, expiry, lang)
            },
            onBack = { viewModel.navigateTo(AppScreen.ALERTS) }
          )
        }

        AppScreen.RESPONDER_QUEUE -> {
          ResponderIncidentQueueScreen(
            incidents = responderIncidents,
            onOpenIncident = { viewModel.selectIncident(it) },
            onSwitchRole = { viewModel.navigateTo(AppScreen.PROFILE_SETTINGS) }
          )
        }

        AppScreen.RESPONDER_INCIDENT_DETAIL -> {
          selectedIncident?.let { inc ->
            ResponderIncidentDetailScreen(
              incident = inc,
              onAcknowledge = { viewModel.acknowledgeIncident(inc.id) },
              onAssignTeam = { team -> viewModel.assignIncidentTeam(inc.id, team) },
              onMarkInProgress = { viewModel.markIncidentInProgress(inc.id) },
              onMarkResolved = { viewModel.markIncidentResolved(inc.id) },
              onBack = { viewModel.navigateTo(AppScreen.RESPONDER_QUEUE) }
            )
          } ?: run {
            viewModel.navigateTo(AppScreen.RESPONDER_QUEUE)
          }
        }

        AppScreen.CONNECTION_STATUS -> {
          ConnectionStatusScreen(
            role = userRole,
            incident = sosIncident,
            relayStats = relayStats,
            onKeepSearching = { viewModel.simulateRelayHop() },
            onToggleBatteryMode = { viewModel.toggleEmergencyBatteryMode() },
            onSimulateRelay = { viewModel.simulateRelayHop() },
            onSimulateGateway = { viewModel.simulateGatewayReceived() },
            onSwitchRole = { viewModel.navigateTo(AppScreen.PROFILE_SETTINGS) }
          )
        }

        AppScreen.SAFETY_GUIDE -> {
          OfflineSafetyGuideScreen(
            onBack = {
              val homeScreen = when (userRole) {
                UserRole.CITIZEN -> AppScreen.CITIZEN_HOME
                UserRole.VOLUNTEER -> AppScreen.VOLUNTEER_HOME
                UserRole.RESPONDER -> AppScreen.RESPONDER_QUEUE
              }
              viewModel.navigateTo(homeScreen)
            }
          )
        }

        AppScreen.PROFILE_SETTINGS -> {
          ProfileAndRoleScreen(
            currentRole = userRole,
            onRoleChange = { viewModel.setUserRole(it) },
            onBack = {
              val homeScreen = when (userRole) {
                UserRole.CITIZEN -> AppScreen.CITIZEN_HOME
                UserRole.VOLUNTEER -> AppScreen.VOLUNTEER_HOME
                UserRole.RESPONDER -> AppScreen.RESPONDER_QUEUE
              }
              viewModel.navigateTo(homeScreen)
            }
          )
        }
      }
    }
  }
}

