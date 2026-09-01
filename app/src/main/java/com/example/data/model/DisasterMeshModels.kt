package com.example.data.model

enum class UserRole(val displayName: String, val badgeText: String, val subtitle: String) {
    CITIZEN(
        displayName = "Citizen",
        badgeText = "Citizen",
        subtitle = "Send SOS, report hazards, and receive emergency alerts."
    ),
    VOLUNTEER(
        displayName = "Volunteer Helper",
        badgeText = "Volunteer Helper",
        subtitle = "Safely relay emergency messages and offer nearby assistance."
    ),
    RESPONDER(
        displayName = "Verified Responder",
        badgeText = "Verified Responder",
        subtitle = "For approved rescue teams and emergency personnel."
    )
}

enum class SosDeliveryState(val title: String, val chipLabel: String, val stepIndex: Int) {
    SAVED_LOCALLY("SOS Saved Locally", "Searching for relay", 0),
    RELAY_ACCEPTED("Relay Accepted", "Relay Accepted", 1),
    GATEWAY_RECEIVED("Gateway Received", "Gateway Received", 2),
    RESPONDER_ACKNOWLEDGED("Responder Acknowledged", "Responder Acknowledged", 3)
}

enum class IncidentStatus(val label: String) {
    GATEWAY_RECEIVED("Gateway Received"),
    ACKNOWLEDGED("Acknowledged"),
    IN_PROGRESS("In Progress"),
    RESOLVED("Resolved")
}

enum class AppScreen {
    ONBOARDING_ROLE,
    CITIZEN_HOME,
    CITIZEN_SOS_STEP1_CATEGORY,
    CITIZEN_SOS_STEP2_CONFIRM,
    CITIZEN_SOS_STATUS,
    REPORT_HAZARD,
    VOLUNTEER_HOME,
    VOLUNTEER_HELP_DETAIL,
    VOLUNTEER_RELAY_ACTIVITY,
    ALERTS,
    CREATE_VERIFIED_ALERT,
    RESPONDER_QUEUE,
    RESPONDER_INCIDENT_DETAIL,
    CONNECTION_STATUS,
    SAFETY_GUIDE,
    PROFILE_SETTINGS
}

data class AuditStep(
    val time: String,
    val title: String,
    val description: String,
    val isCompleted: Boolean
)

data class SosIncident(
    val id: String = "DM-7F3A-29C1",
    val category: String = "Trapped Person",
    val location: String = "Riverside Road, Pune",
    val accuracy: String = "Approx. 24 m accuracy",
    val time: String = "10:42 AM",
    val peopleAffected: Int = 2,
    val message: String = "Two people trapped near east gate",
    val priority: String = "P0 Critical",
    val relaysCount: Int = 3,
    val assignedTeam: String? = null,
    val deliveryState: SosDeliveryState = SosDeliveryState.SAVED_LOCALLY,
    val incidentStatus: IncidentStatus = IncidentStatus.GATEWAY_RECEIVED,
    val auditTrail: List<AuditStep> = listOf(
        AuditStep("10:42 AM", "SOS Created", "Stored in encrypted local device cache", true),
        AuditStep("10:42 AM", "Relay Accepted", "Carried by nearby Volunteer Node (Device #B4)", false),
        AuditStep("10:43 AM", "Gateway Received", "Forwarded through Node #C7 to Station Gateway", false),
        AuditStep("10:44 AM", "Responder Acknowledged", "Incident opened by Verified Rescue Team Alpha", false)
    )
)

data class HazardReport(
    val id: String,
    val category: String,
    val location: String,
    val description: String,
    val peopleAffected: String? = null,
    val timeAgo: String,
    val hasPhoto: Boolean = false,
    val isVerified: Boolean = false
)

data class EmergencyAlert(
    val id: String,
    val type: String,
    val severity: String, // "CRITICAL", "WARNING", "ADVISORY"
    val headline: String,
    val instruction: String,
    val affectedArea: String,
    val issuedTime: String,
    val expiryTime: String,
    val isAuthorityVerified: Boolean = true,
    val authorityName: String = "Disaster Management Authority, Pune",
    val language: String = "English"
)

data class VolunteerHelpRequest(
    val id: String,
    val title: String,
    val approximateArea: String,
    val distance: String,
    val receivedAgo: String,
    val peopleAffected: Int,
    val category: String,
    val safeGuidance: String = "Carry basic first-aid if trained. Do not enter floodwaters or unsafe debris.",
    val isHelped: Boolean = false,
    val isRelayed: Boolean = false
)

data class RelayStats(
    val messagesStored: Int = 1,
    val messagesRelayedToday: Int = 2,
    val lastRelay: String = "3 min ago",
    val relayModeActive: Boolean = true,
    val emergencyBatteryMode: Boolean = false,
    val nearbyDevicesCount: Int = 3
)
