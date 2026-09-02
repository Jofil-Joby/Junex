<div align="center">

# JUNEX

### Emergency Communication & Disaster Coordination

A concept Android application exploring how citizens, volunteers, and emergency responders could continue coordinating when conventional communication infrastructure becomes unreliable.

<br/>

[![Platform](https://img.shields.io/badge/Platform-Android-3DDC84?style=for-the-badge\&logo=android\&logoColor=white)](https://www.android.com/)
[![Language](https://img.shields.io/badge/Language-Kotlin-7F52FF?style=for-the-badge\&logo=kotlin\&logoColor=white)](https://kotlinlang.org/)
[![UI](https://img.shields.io/badge/UI-Jetpack%20Compose-4285F4?style=for-the-badge)](https://developer.android.com/compose)
[![Status](https://img.shields.io/badge/Status-Prototype-F59E0B?style=for-the-badge)](#project-status)

<br/>

> **When networks fail, coordination should not.**

Junex explores how critical emergency information could be stored locally, relayed through nearby devices, and eventually delivered to responders or network gateways.

</div>

---

## What is Junex?

During natural disasters and large-scale emergencies, communication infrastructure can become unreliable or unavailable.

Junex explores an alternative model for emergency coordination built around three groups:

<div align="center">

|    Citizen   |       Volunteer      |     Responder    |
| :----------: | :------------------: | :--------------: |
|      🚨      |          🤝          |        🛡️       |
| Request help | Assist nearby people | Manage incidents |

</div>

The current version focuses on the **product experience, emergency workflows, and interaction model** behind a potential community-supported communication system.

> 🟠 **Project Status:** Prototype / Concept Implementation

---

## How It Works

Junex demonstrates how emergency information could move through a disaster coordination workflow.

```text
┌──────────────┐
│   CITIZEN    │
│ Creates SOS  │
└──────┬───────┘
       │
       ▼
┌──────────────┐
│ LOCAL DEVICE │
│ Stores Data  │
└──────┬───────┘
       │
       ▼
┌──────────────┐
│  VOLUNTEER   │
│ Relay Node   │
└──────┬───────┘
       │
       ▼
┌──────────────┐
│   GATEWAY    │
│ Sync Point   │
└──────┬───────┘
       │
       ▼
┌──────────────┐
│  RESPONDER   │
│   Incident   │
└──────────────┘
```

The current implementation simulates this workflow without requiring physical mesh networking hardware.

---

# Features

## 🚨 Emergency SOS

Citizens can create emergency requests through a guided SOS workflow.

The request can include:

* Emergency category
* Number of affected people
* Additional emergency information
* Request status
* Delivery progress

### Delivery Lifecycle

```text
🟡 Saved Locally
      ↓
🔵 Accepted by Relay Node
      ↓
🟣 Received by Gateway
      ↓
🟢 Acknowledged by Responders
```

This allows users to understand where an emergency request is within the simulated delivery workflow.

---

## ⚠️ Hazard Reporting

Users can report potentially dangerous situations in their area.

Hazard reports can include:

* Hazard category
* Approximate location
* Description
* Number of people affected
* Photo availability

Community reports can exist alongside official emergency information to provide additional awareness during developing situations.

---

## 📢 Emergency Alerts

Junex provides a dedicated system for viewing emergency information.

Alerts can include:

* Verified emergency information
* Alert severity
* Safety instructions
* Affected areas
* Alert expiration information
* Community hazard reports

Responder workflows also demonstrate the creation of verified emergency alerts.

---

## 🤝 Volunteer Assistance

Volunteers act as an important bridge between citizens and emergency coordination systems.

Volunteer functionality includes:

* Viewing nearby help requests
* Reviewing emergency request details
* Offering assistance
* Relaying emergency messages
* Monitoring relay activity
* Managing relay mode
* Emergency battery mode controls

---

## 🛡️ Responder Dashboard

Responders have access to an incident management workflow.

Available functionality includes:

* Viewing active incidents
* Opening incident details
* Acknowledging emergency requests
* Assigning response teams
* Updating incident status
* Marking incidents as resolved
* Creating verified emergency alerts

---

## 📡 Mesh Connection Simulation

Junex currently includes an interactive simulation of emergency message delivery.

The simulation demonstrates:

* Relay hops between nearby devices
* Gateway delivery
* Message delivery status changes
* Incident synchronization across different user roles

```text
Citizen Device
      │
      │  Emergency Message
      ▼
Volunteer Node
      │
      │  Relay
      ▼
Gateway
      │
      │  Incident Delivery
      ▼
Responder
```

> 🔵 The current system is a simulation designed to demonstrate the workflow and interaction model.

---

## 📖 Offline Safety Guide

Junex includes an offline safety section containing emergency guidance.

The goal is to keep essential information accessible even when conventional connectivity is unavailable.

Potential guidance includes:

* Disaster preparation
* Emergency evacuation
* Basic safety procedures
* Communication guidance
* Situation-specific instructions

---

# User Roles

<table>
<tr>
<td width="33%" valign="top">

### 🚨 Citizen

Request help and receive emergency information.

**Capabilities**

* Create SOS requests
* Track delivery progress
* Report hazards
* View verified alerts
* View community reports
* Access safety guidance
* Check connection status

</td>

<td width="33%" valign="top">

### 🤝 Volunteer

Assist people and support emergency communication.

**Capabilities**

* View nearby requests
* Offer assistance
* Relay emergency messages
* Monitor relay activity
* Manage relay mode
* Enable battery-saving controls
* Access emergency alerts

</td>

<td width="33%" valign="top">

### 🛡️ Responder

Coordinate and manage active incidents.

**Capabilities**

* View incidents
* Review emergency details
* Acknowledge requests
* Assign teams
* Update incident status
* Resolve incidents
* Create verified alerts

</td>
</tr>
</table>

---

# Technology Stack

<div align="center">

| Core            | Architecture  | Additional |
| --------------- | ------------- | ---------- |
| Kotlin          | MVVM-inspired | Room       |
| Android SDK     | ViewModel     | Firebase   |
| Jetpack Compose | StateFlow     | Retrofit   |
| Material 3      | Coroutines    | OkHttp     |
|                 |               | Moshi      |
|                 |               | KSP        |

</div>

Some integrations are currently reserved for future development and are not part of the active prototype workflow.

---

# Project Structure

```text
app/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/example/
│   │   │       ├── data/
│   │   │       │   └── model/
│   │   │       │
│   │   │       ├── ui/
│   │   │       │   ├── components/
│   │   │       │   ├── screens/
│   │   │       │   └── theme/
│   │   │       │
│   │   │       ├── viewmodel/
│   │   │       └── MainActivity.kt
│   │   │
│   │   └── res/
│   │
│   └── test/
│
├── build.gradle.kts
├── proguard-rules.pro
└── README.md
```

---

# Getting Started

## Requirements

Make sure you have:

* Android Studio
* Android SDK
* A compatible Android emulator or physical Android device

## Installation

Clone the repository:

```bash
git clone https://github.com/Jofil-Joby/Junex.git
```

Move into the project:

```bash
cd Junex
```

Then:

1. Open the project in **Android Studio**
2. Allow **Gradle** to synchronize
3. Select an emulator or physical device
4. Run the application

---

# Current Prototype Scope

Junex currently demonstrates the **product and interaction model** of a disaster communication system.

The following functionality is currently simulated:

* Device-to-device emergency message relaying
* Gateway delivery
* Network status changes
* Cross-role incident synchronization

> 🔴 **Important:** Junex does not currently implement a real peer-to-peer mesh networking protocol between physical devices.

The simulation exists to demonstrate how information could move through such a system and how different user roles could interact with shared emergency incidents.

---

# Future Development

Potential directions for Junex include:

* Bluetooth-based device communication
* Wi-Fi Direct communication
* Device discovery
* Offline-first persistent storage
* Persistent message queues
* Encrypted emergency message transport
* Real GPS integration
* Photo capture and attachments
* Persistent incident history
* Backend synchronization
* Verified responder authentication
* Push notifications
* Multi-language emergency guidance
* Integration with disaster management organizations

---

# Vision

> **How can communities continue to communicate and coordinate when traditional communication infrastructure becomes unreliable?**

Junex explores a role-based emergency ecosystem where:

```text
🚨 Citizens
   Request help and report hazards

        ↓

🤝 Volunteers
   Assist communities and relay information

        ↓

🛡️ Responders
   Coordinate and manage incidents
```

While the current version is a prototype, the broader goal is to explore more resilient and community-supported approaches to emergency communication.

---

# Disclaimer

> ⚠️ **Junex is currently a prototype and must not be relied upon as a real emergency communication system.**

The application does not replace:

* Official emergency services
* Disaster management authorities
* Established emergency communication infrastructure
* Professional emergency response systems

The networking and emergency workflows currently demonstrated are conceptual and simulated.

---

<div align="center">

## JUNEX

**Resilient communication. Community coordination. Emergency response.**

<br/>

Built as an Android prototype exploring emergency coordination and communication when conventional networks become unreliable.

---
## License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.

</div>
