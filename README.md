<div align="center">

# JUNEX

### Emergency communication and disaster coordination prototype

A concept Android application exploring how citizens, volunteers, and emergency responders could continue coordinating when conventional communication infrastructure becomes unreliable.

<br/>

<img src="docs/images/junex-demo.gif" alt="Junex application demo" width="850"/>

<br/>

[![Platform](https://img.shields.io/badge/Platform-Android-111111?style=flat-square)](https://www.android.com/)
[![Language](https://img.shields.io/badge/Language-Kotlin-111111?style=flat-square)](https://kotlinlang.org/)
[![UI](https://img.shields.io/badge/UI-Jetpack%20Compose-111111?style=flat-square)](https://developer.android.com/compose)
[![Status](https://img.shields.io/badge/Status-Prototype-111111?style=flat-square)](#project-status)

<br/>

> **When networks fail, coordination should not.**

Junex explores a community-supported emergency communication model where critical information can be stored locally, relayed through nearby devices, and eventually delivered to emergency responders or network gateways.

</div>

---

## Overview

During natural disasters and large-scale emergencies, communication infrastructure can become unreliable or unavailable entirely.

Junex explores an alternative approach to emergency coordination.

The application connects three groups involved in a disaster response ecosystem:

| Role          | Purpose                                                         |
| ------------- | --------------------------------------------------------------- |
| **Citizen**   | Request help, report hazards, and receive emergency information |
| **Volunteer** | Assist nearby people and relay emergency messages               |
| **Responder** | Monitor, manage, and resolve active incidents                   |

The current version focuses on the **product experience, emergency workflows, and interaction model** behind a potential offline or mesh-supported communication system.

> **Project Status:** Prototype / Concept Implementation

---

# See Junex in Action

<div align="center">

<img src="docs/images/junex-overview.png" alt="Junex overview" width="850"/>

</div>

Junex demonstrates how emergency information could move through a community-supported communication system.

```text
Citizen
   │
   │ Creates SOS / Hazard Report
   ▼
Local Device Storage
   │
   │ Message Relay
   ▼
Nearby Volunteer / Relay Node
   │
   │ Additional Relay Hops
   ▼
Gateway / Network Connection
   │
   ▼
Emergency Responder
   │
   ▼
Incident Management & Response
```

The current implementation simulates this workflow without requiring physical mesh networking hardware.

---

# Core Features

## Emergency SOS

Citizens can create emergency requests through a guided SOS workflow.

The request can include:

* Emergency category
* Number of affected people
* Additional emergency information
* Request status
* Delivery progress

Junex visualizes the journey of an emergency request through several stages:

```text
● Saved locally
        ↓
● Accepted by relay node
        ↓
● Received by gateway
        ↓
● Acknowledged by responders
```

<div align="center">

<img src="docs/images/sos-flow.png" alt="Junex SOS workflow" width="850"/>

</div>

---

## Hazard Reporting

Users can report dangerous situations affecting their surrounding area.

Reports can include:

* Hazard category
* Approximate location
* Description
* Number of people affected
* Photo availability

Community reports are displayed alongside official emergency information, allowing users to understand developing situations around them.

<div align="center">

<img src="docs/images/hazard-report.png" alt="Junex hazard reporting" width="850"/>

</div>

---

## Emergency Alerts

Junex includes a dedicated emergency alert system.

Alerts can provide:

* Verified emergency information
* Severity levels
* Safety instructions
* Affected areas
* Alert expiration information
* Community hazard reports

Responder workflows also demonstrate the creation of verified emergency alerts.

---

## Volunteer Assistance

Volunteers act as an important bridge between citizens and emergency coordination systems.

Volunteer functionality includes:

* Viewing nearby requests for assistance
* Reviewing emergency request details
* Offering assistance
* Relaying emergency messages
* Monitoring relay activity
* Managing relay mode
* Emergency battery mode controls

<div align="center">

<img src="docs/images/volunteer-dashboard.png" alt="Junex volunteer dashboard" width="850"/>

</div>

---

## Responder Dashboard

Responders have access to an incident management workflow designed around active emergency coordination.

Available functionality includes:

* Viewing active incidents
* Opening incident details
* Acknowledging emergency requests
* Assigning response teams
* Updating incident status
* Marking incidents as resolved
* Creating verified emergency alerts

<div align="center">

<img src="docs/images/responder-dashboard.png" alt="Junex responder dashboard" width="850"/>

</div>

---

# How the System Works

Junex connects multiple user roles through a shared emergency workflow.

### 1. A citizen creates an emergency request

A user creates an SOS request or reports a hazard.

### 2. The information is stored locally

The emergency information remains available within the application's local workflow.

### 3. Nearby devices relay the message

Junex simulates relay hops between nearby devices.

### 4. A gateway receives the request

The simulation demonstrates the emergency message reaching a network gateway.

### 5. Responders receive the incident

The incident appears in the responder workflow.

### 6. The incident is managed

Responders can acknowledge, assign, update, and resolve the incident.

---

# User Roles

## Citizen

The citizen experience focuses on requesting help and receiving critical emergency information.

**Available functionality**

* Create SOS requests
* Track SOS delivery progress
* Report hazards
* View verified alerts
* View community reports
* Access offline safety guidance
* Check connection status

---

## Volunteer

The volunteer experience focuses on assisting nearby communities and participating in emergency communication.

**Available functionality**

* View nearby help requests
* Review request details
* Offer assistance
* Participate in message relaying
* Monitor relay activity
* Manage emergency battery mode
* View emergency alerts
* Access offline safety guidance

---

## Responder

The responder experience focuses on emergency coordination and incident management.

**Available functionality**

* View active incident queues
* Review incident details
* Acknowledge incidents
* Assign response teams
* Update incident status
* Resolve incidents
* Create verified emergency alerts

---

# Mesh Connection Simulation

Junex currently includes an interactive simulation of emergency message delivery.

The simulation demonstrates:

* Relay hops between nearby devices
* Gateway delivery
* Message delivery status changes
* Incident synchronization across different user roles

This makes it possible to explore the complete emergency workflow without requiring physical mesh networking hardware.

<div align="center">

<img src="docs/images/mesh-simulation.gif" alt="Junex mesh communication simulation" width="850"/>

</div>

---

# Offline Safety Guide

Junex includes an offline safety section containing emergency guidance.

The goal is to ensure that essential safety information remains accessible even when the communication workflow is unavailable.

Potential guidance includes:

* Disaster preparation
* Emergency evacuation
* Basic safety procedures
* Communication guidance
* Situation-specific safety instructions

---

# Technology Stack

## Core

* Kotlin
* Android SDK
* Jetpack Compose
* Material 3

## Architecture

* MVVM-inspired architecture
* ViewModel state management
* Kotlin StateFlow
* Kotlin Coroutines

## Additional Technologies

The project also contains infrastructure and dependencies for future development involving:

* Room
* Firebase
* Firebase AI
* Retrofit
* OkHttp
* Moshi
* Kotlin Symbol Processing (KSP)

Some of these integrations are currently reserved for future development and are not part of the active prototype workflow.

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

The application is organized around:

* **Models** for emergency and application data
* **ViewModels** for application state and workflows
* **Composable screens** for role-specific experiences
* **Reusable UI components**
* **Theme definitions** for the application's visual system

---

# Getting Started

## Prerequisites

Before running Junex, install:

* Android Studio
* Android SDK
* A compatible Android emulator or physical Android device

## Installation

Clone the repository:

```bash
git clone https://github.com/Jofil-Joby/Junex.git
```

Move into the project directory:

```bash
cd Junex
```

Open the project in **Android Studio**.

Allow Gradle to synchronize and download the required dependencies.

Then run the application using either:

* An Android emulator
* A physical Android device

---



# Current Prototype Scope

Junex currently demonstrates the **interaction model and product workflow** of a disaster communication system.

The following features are currently simulated:

* Device-to-device emergency message relaying
* Gateway delivery
* Network status changes
* Cross-role incident synchronization

> Junex does **not currently implement a real peer-to-peer mesh networking protocol between physical devices**.

The simulation is intended to demonstrate how information could move through such a system and how the different user roles could interact with shared emergency incidents.

---

# Future Development

Potential future directions include:

* Real Bluetooth mesh communication
* Wi-Fi Direct device communication
* Device discovery
* Offline-first local data storage
* Persistent message queues
* Encrypted emergency message transport
* Real GPS integration
* Photo capture and attachments
* Persistent incident history
* Backend synchronization when connectivity becomes available
* Verified responder authentication
* Push notifications
* Multi-language emergency guidance
* Integration with disaster management organizations

---

# Vision

Junex explores a simple question:

> **How can communities continue to communicate and coordinate during an emergency when traditional communication infrastructure becomes unreliable?**

The project explores a role-based emergency ecosystem where:

* **Citizens** can request help and report hazards
* **Volunteers** can assist nearby people and support communication
* **Responders** can receive, manage, and resolve incidents

While the current version is a prototype, the broader goal is to explore more resilient and community-supported approaches to emergency communication.

---

# Disclaimer

> **Junex is currently a prototype and must not be relied upon as a real emergency communication system.**

The application does not replace:

* Official emergency services
* Disaster management authorities
* Established emergency communication infrastructure
* Professional emergency response systems

The networking and emergency workflows currently demonstrated in the application are conceptual and simulated.

---

<div align="center">

### Junex

**Exploring resilient communication when conventional networks cannot be relied upon.**

<br/>

Built as an Android prototype for exploring emergency coordination, offline workflows, and community-supported communication.

</div>
