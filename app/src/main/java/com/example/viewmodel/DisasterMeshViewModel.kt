package com.example.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.model.AppScreen
import com.example.data.model.AuditStep
import com.example.data.model.EmergencyAlert
import com.example.data.model.HazardReport
import com.example.data.model.IncidentStatus
import com.example.data.model.RelayStats
import com.example.data.model.SosDeliveryState
import com.example.data.model.SosIncident
import com.example.data.model.UserRole
import com.example.data.model.VolunteerHelpRequest
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DisasterMeshViewModel : ViewModel() {

    private val _currentScreen = MutableStateFlow(AppScreen.ONBOARDING_ROLE)
    val currentScreen: StateFlow<AppScreen> = _currentScreen.asStateFlow()

    private val _userRole = MutableStateFlow(UserRole.CITIZEN)
    val userRole: StateFlow<UserRole> = _userRole.asStateFlow()

    // Shared SOS Incident (ID DM-7F3A-29C1) synced across roles
    private val _currentSosIncident = MutableStateFlow(
        SosIncident(
            id = "DM-7F3A-29C1",
            category = "Trapped Person",
            location = "Riverside Road, Pune",
            accuracy = "Approx. 24 m accuracy",
            time = "10:42 AM",
            peopleAffected = 2,
            message = "Two people trapped near east gate",
            priority = "P0 Critical",
            relaysCount = 3,
            assignedTeam = null,
            deliveryState = SosDeliveryState.SAVED_LOCALLY,
            incidentStatus = IncidentStatus.GATEWAY_RECEIVED
        )
    )
    val currentSosIncident: StateFlow<SosIncident> = _currentSosIncident.asStateFlow()

    // SOS Creation Wizard States
    private val _sosSelectedCategory = MutableStateFlow("Trapped Person")
    val sosSelectedCategory: StateFlow<String> = _sosSelectedCategory.asStateFlow()

    private val _sosPeopleCount = MutableStateFlow(2)
    val sosPeopleCount: StateFlow<Int> = _sosPeopleCount.asStateFlow()

    private val _sosMessage = MutableStateFlow("Two people trapped near east gate")
    val sosMessage: StateFlow<String> = _sosMessage.asStateFlow()

    // Volunteer Requests
    private val _volunteerRequests = MutableStateFlow(
        listOf(
            VolunteerHelpRequest(
                id = "VR-701",
                title = "Medical help requested nearby",
                approximateArea = "Near Riverside Road (Approx. Sector 2)",
                distance = "About 700 m",
                receivedAgo = "4 min ago",
                peopleAffected = 1,
                category = "Medical Need",
                safeGuidance = "Carry basic first-aid if trained. Do not enter floodwaters or unsafe debris."
            ),
            VolunteerHelpRequest(
                id = "VR-702",
                title = "Shelter assistance & blanket support",
                approximateArea = "Near Central Camp Perimeter",
                distance = "About 1.2 km",
                receivedAgo = "18 min ago",
                peopleAffected = 3,
                category = "Shelter",
                safeGuidance = "Help guide disoriented families to the designated registration desk."
            ),
            VolunteerHelpRequest(
                id = "VR-703",
                title = "Evacuation assistance for elderly",
                approximateArea = "Near High School East Alley",
                distance = "About 850 m",
                receivedAgo = "32 min ago",
                peopleAffected = 2,
                category = "Evacuation",
                safeGuidance = "Do not move anyone with suspected spinal injury. Assist with dry mobility only."
            )
        )
    )
    val volunteerRequests: StateFlow<List<VolunteerHelpRequest>> = _volunteerRequests.asStateFlow()

    private val _selectedVolunteerRequest = MutableStateFlow<VolunteerHelpRequest?>(null)
    val selectedVolunteerRequest: StateFlow<VolunteerHelpRequest?> = _selectedVolunteerRequest.asStateFlow()

    // Responder Incidents
    private val _responderIncidents = MutableStateFlow(
        listOf(
            SosIncident(
                id = "DM-7F3A-29C1",
                category = "Trapped Person",
                location = "Riverside Road, Pune",
                accuracy = "Approx. 24 m accuracy",
                time = "10:42 AM",
                peopleAffected = 2,
                message = "Two people trapped near east gate",
                priority = "P0 Critical",
                relaysCount = 3,
                assignedTeam = null,
                deliveryState = SosDeliveryState.SAVED_LOCALLY,
                incidentStatus = IncidentStatus.GATEWAY_RECEIVED
            ),
            SosIncident(
                id = "DM-9B21-41E8",
                category = "Medical Emergency",
                location = "Ward 12, Shivaji Nagar",
                accuracy = "Approx. 35 m accuracy",
                time = "10:38 AM",
                peopleAffected = 1,
                message = "Elderly person needs oxygen assistance",
                priority = "P1 Urgent",
                relaysCount = 2,
                assignedTeam = "Team Charlie - Unit 2",
                deliveryState = SosDeliveryState.RESPONDER_ACKNOWLEDGED,
                incidentStatus = IncidentStatus.IN_PROGRESS
            ),
            SosIncident(
                id = "DM-4E18-92A3",
                category = "Flooded Road / Stranded",
                location = "Kalyani Nagar Bridge",
                accuracy = "Approx. 50 m accuracy",
                time = "10:25 AM",
                peopleAffected = 4,
                message = "Vehicle stalled in rising water",
                priority = "P1 Urgent",
                relaysCount = 4,
                assignedTeam = null,
                deliveryState = SosDeliveryState.GATEWAY_RECEIVED,
                incidentStatus = IncidentStatus.GATEWAY_RECEIVED
            )
        )
    )
    val responderIncidents: StateFlow<List<SosIncident>> = _responderIncidents.asStateFlow()

    private val _selectedIncident = MutableStateFlow<SosIncident?>(null)
    val selectedIncident: StateFlow<SosIncident?> = _selectedIncident.asStateFlow()

    // Verified Alerts
    private val _verifiedAlerts = MutableStateFlow(
        listOf(
            EmergencyAlert(
                id = "EA-101",
                type = "Flood Warning",
                severity = "CRITICAL",
                headline = "Flood water rising — Avoid Riverside Road",
                instruction = "Stay on higher ground. Do not attempt to cross underpasses or riverbank paths.",
                affectedArea = "Riverside Road, Deccan & Bund Garden Areas",
                issuedTime = "10:15 AM",
                expiryTime = "06:00 PM",
                isAuthorityVerified = true,
                authorityName = "Disaster Management Authority, Pune"
            ),
            EmergencyAlert(
                id = "EA-102",
                type = "Relief Shelter",
                severity = "ADVISORY",
                headline = "Shelter open — Central School Relief Camp",
                instruction = "Dry food, clean drinking water, and first-aid facilities available. Enter via North Gate.",
                affectedArea = "Central High School Ground, Ward 9",
                issuedTime = "09:30 AM",
                expiryTime = "10:00 PM",
                isAuthorityVerified = true,
                authorityName = "Municipal Emergency Relief Cell"
            ),
            EmergencyAlert(
                id = "EA-103",
                type = "Fire Evacuation",
                severity = "CRITICAL",
                headline = "Fire evacuation — Move toward North Assembly Point",
                instruction = "Industrial perimeter fire under containment. Keep windows closed and evacuate sector 4.",
                affectedArea = "Sector 4 Industrial Corridor",
                issuedTime = "08:50 AM",
                expiryTime = "02:00 PM",
                isAuthorityVerified = true,
                authorityName = "Pune Fire & Rescue Services"
            )
        )
    )
    val verifiedAlerts: StateFlow<List<EmergencyAlert>> = _verifiedAlerts.asStateFlow()

    // Hazard Reports (Community Reports)
    private val _hazardReports = MutableStateFlow(
        listOf(
            HazardReport(
                id = "HR-001",
                category = "Flooded Road",
                location = "Riverside Road (Underpass)",
                description = "Water level above 3 feet, completely submerged passage.",
                peopleAffected = "0",
                timeAgo = "12 min ago",
                hasPhoto = true,
                isVerified = false
            ),
            HazardReport(
                id = "HR-002",
                category = "Damaged Building",
                location = "East Gate Market",
                description = "Partial wall collapse blocking eastern pedestrian lane.",
                peopleAffected = "2",
                timeAgo = "25 min ago",
                hasPhoto = false,
                isVerified = false
            ),
            HazardReport(
                id = "HR-003",
                category = "Blocked Route",
                location = "Main Ring Road Junction",
                description = "Fallen tree and electrical wires obstructing two lanes.",
                peopleAffected = "0",
                timeAgo = "40 min ago",
                hasPhoto = true,
                isVerified = false
            )
        )
    )
    val hazardReports: StateFlow<List<HazardReport>> = _hazardReports.asStateFlow()

    // Relay Statistics
    private val _relayStats = MutableStateFlow(
        RelayStats(
            messagesStored = 1,
            messagesRelayedToday = 2,
            lastRelay = "3 min ago",
            relayModeActive = true,
            emergencyBatteryMode = false,
            nearbyDevicesCount = 4
        )
    )
    val relayStats: StateFlow<RelayStats> = _relayStats.asStateFlow()

    // Navigation & Role actions
    fun setUserRole(role: UserRole) {
        _userRole.value = role
        // Also update home screen if needed
        val targetScreen = when (role) {
            UserRole.CITIZEN -> AppScreen.CITIZEN_HOME
            UserRole.VOLUNTEER -> AppScreen.VOLUNTEER_HOME
            UserRole.RESPONDER -> AppScreen.RESPONDER_QUEUE
        }
        if (_currentScreen.value != AppScreen.ONBOARDING_ROLE) {
            _currentScreen.value = targetScreen
        }
    }

    fun completeOnboarding() {
        val target = when (_userRole.value) {
            UserRole.CITIZEN -> AppScreen.CITIZEN_HOME
            UserRole.VOLUNTEER -> AppScreen.VOLUNTEER_HOME
            UserRole.RESPONDER -> AppScreen.RESPONDER_QUEUE
        }
        _currentScreen.value = target
    }

    fun navigateTo(screen: AppScreen) {
        _currentScreen.value = screen
    }

    // SOS Flow methods
    fun startSosFlow() {
        _currentScreen.value = AppScreen.CITIZEN_SOS_STEP1_CATEGORY
    }

    fun setSosCategory(category: String) {
        _sosSelectedCategory.value = category
    }

    fun setSosPeopleCount(count: Int) {
        _sosPeopleCount.value = count.coerceAtLeast(1)
    }

    fun setSosMessage(msg: String) {
        _sosMessage.value = msg
    }

    fun submitSos() {
        val cat = _sosSelectedCategory.value
        val people = _sosPeopleCount.value
        val msg = _sosMessage.value

        val updated = _currentSosIncident.value.copy(
            category = cat,
            peopleAffected = people,
            message = msg,
            deliveryState = SosDeliveryState.SAVED_LOCALLY,
            incidentStatus = IncidentStatus.GATEWAY_RECEIVED,
            auditTrail = listOf(
                AuditStep("Just now", "SOS Saved Locally", "Stored in encrypted local device cache", true),
                AuditStep("Pending", "Relay Accepted", "Waiting for nearby volunteer phone connection", false),
                AuditStep("Pending", "Gateway Received", "Waiting for responder gateway hop", false),
                AuditStep("Pending", "Responder Acknowledged", "Waiting for verified responder review", false)
            )
        )

        _currentSosIncident.value = updated
        _responderIncidents.update { list ->
            listOf(updated) + list.filter { it.id != updated.id }
        }
        _currentScreen.value = AppScreen.CITIZEN_SOS_STATUS
    }

    // Simulation methods for interactive prototype testing
    fun simulateRelayHop() {
        val current = _currentSosIncident.value
        val updatedAudit = current.auditTrail.map { step ->
            if (step.title.contains("Relay Accepted")) {
                step.copy(time = "Just now", isCompleted = true, description = "Carried by nearby Volunteer Node (Device #B4)")
            } else step
        }
        val updated = current.copy(
            deliveryState = SosDeliveryState.RELAY_ACCEPTED,
            auditTrail = updatedAudit
        )
        _currentSosIncident.value = updated
        _responderIncidents.update { list ->
            list.map { if (it.id == updated.id) updated else it }
        }
        _relayStats.update {
            it.copy(
                messagesRelayedToday = it.messagesRelayedToday + 1,
                lastRelay = "Just now"
            )
        }
    }

    fun simulateGatewayReceived() {
        val current = _currentSosIncident.value
        val updatedAudit = current.auditTrail.map { step ->
            if (step.title.contains("Gateway Received")) {
                step.copy(time = "Just now", isCompleted = true, description = "Forwarded through Node #C7 to Station Gateway")
            } else if (step.title.contains("Relay Accepted") && !step.isCompleted) {
                step.copy(time = "Just now", isCompleted = true)
            } else step
        }
        val updated = current.copy(
            deliveryState = SosDeliveryState.GATEWAY_RECEIVED,
            auditTrail = updatedAudit
        )
        _currentSosIncident.value = updated
        _responderIncidents.update { list ->
            list.map { if (it.id == updated.id) updated else it }
        }
    }

    // Volunteer actions
    fun selectVolunteerRequest(request: VolunteerHelpRequest) {
        _selectedVolunteerRequest.value = request
        _currentScreen.value = AppScreen.VOLUNTEER_HELP_DETAIL
    }

    fun markVolunteerHelpOffered(requestId: String) {
        _volunteerRequests.update { list ->
            list.map { if (it.id == requestId) it.copy(isHelped = true) else it }
        }
        _selectedVolunteerRequest.update {
            if (it?.id == requestId) it.copy(isHelped = true) else it
        }
    }

    fun toggleEmergencyBatteryMode() {
        _relayStats.update { it.copy(emergencyBatteryMode = !it.emergencyBatteryMode) }
    }

    fun toggleRelayMode() {
        _relayStats.update { it.copy(relayModeActive = !it.relayModeActive) }
    }

    // Hazard submission
    fun submitHazardReport(
        category: String,
        location: String,
        description: String,
        peopleAffected: String?,
        hasPhoto: Boolean
    ) {
        val newReport = HazardReport(
            id = "HR-${System.currentTimeMillis() % 10000}",
            category = category,
            location = location.ifBlank { "Riverside Road, Pune" },
            description = description,
            peopleAffected = peopleAffected,
            timeAgo = "Just now",
            hasPhoto = hasPhoto,
            isVerified = false
        )
        _hazardReports.update { listOf(newReport) + it }
        _currentScreen.value = AppScreen.ALERTS
    }

    // Responder actions
    fun selectIncident(incident: SosIncident) {
        _selectedIncident.value = incident
        _currentScreen.value = AppScreen.RESPONDER_INCIDENT_DETAIL
    }

    fun acknowledgeIncident(incidentId: String) {
        val updatedAudit = _currentSosIncident.value.auditTrail.map { step ->
            if (step.title.contains("Responder Acknowledged")) {
                step.copy(time = "Just now", isCompleted = true, description = "Verified by Station Commander Team Alpha")
            } else step.copy(isCompleted = true)
        }
        val updatedSos = _currentSosIncident.value.copy(
            deliveryState = SosDeliveryState.RESPONDER_ACKNOWLEDGED,
            incidentStatus = IncidentStatus.ACKNOWLEDGED,
            assignedTeam = _currentSosIncident.value.assignedTeam ?: "Rescue Unit Alpha 1",
            auditTrail = updatedAudit
        )
        _currentSosIncident.value = updatedSos
        _responderIncidents.update { list ->
            list.map { if (it.id == incidentId) updatedSos else it }
        }
        _selectedIncident.value = updatedSos
    }

    fun assignIncidentTeam(incidentId: String, teamName: String) {
        _responderIncidents.update { list ->
            list.map {
                if (it.id == incidentId) it.copy(assignedTeam = teamName, incidentStatus = IncidentStatus.IN_PROGRESS) else it
            }
        }
        if (_currentSosIncident.value.id == incidentId) {
            _currentSosIncident.update { it.copy(assignedTeam = teamName, incidentStatus = IncidentStatus.IN_PROGRESS) }
        }
        _selectedIncident.update {
            if (it?.id == incidentId) it.copy(assignedTeam = teamName, incidentStatus = IncidentStatus.IN_PROGRESS) else it
        }
    }

    fun markIncidentInProgress(incidentId: String) {
        _responderIncidents.update { list ->
            list.map {
                if (it.id == incidentId) it.copy(incidentStatus = IncidentStatus.IN_PROGRESS) else it
            }
        }
        if (_currentSosIncident.value.id == incidentId) {
            _currentSosIncident.update { it.copy(incidentStatus = IncidentStatus.IN_PROGRESS) }
        }
        _selectedIncident.update {
            if (it?.id == incidentId) it.copy(incidentStatus = IncidentStatus.IN_PROGRESS) else it
        }
    }

    fun markIncidentResolved(incidentId: String) {
        _responderIncidents.update { list ->
            list.map {
                if (it.id == incidentId) it.copy(incidentStatus = IncidentStatus.RESOLVED) else it
            }
        }
        if (_currentSosIncident.value.id == incidentId) {
            _currentSosIncident.update { it.copy(incidentStatus = IncidentStatus.RESOLVED) }
        }
        _selectedIncident.update {
            if (it?.id == incidentId) it.copy(incidentStatus = IncidentStatus.RESOLVED) else it
        }
    }

    fun createVerifiedAlert(
        type: String,
        severity: String,
        headline: String,
        instruction: String,
        targetArea: String,
        expiryTime: String,
        language: String
    ) {
        val newAlert = EmergencyAlert(
            id = "EA-${System.currentTimeMillis() % 10000}",
            type = type,
            severity = severity,
            headline = headline,
            instruction = instruction,
            affectedArea = targetArea,
            issuedTime = "Just now",
            expiryTime = expiryTime.ifBlank { "06:00 PM" },
            isAuthorityVerified = true,
            authorityName = "Disaster Management Authority, Pune",
            language = language
        )
        _verifiedAlerts.update { listOf(newAlert) + it }
        _currentScreen.value = AppScreen.ALERTS
    }
}
