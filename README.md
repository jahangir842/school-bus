# Smart School Bus Pickup and Tracking System

## Project Overview
Android application for real-time school bus tracking with three modules: Admin Panel, Driver App, and Parent App.

## Features
- Real-time GPS tracking
- Pickup/drop-off notifications
- QR-based student attendance
- Emergency alerts
- Route management
- ETA display

## Tech Stack
- Language: Java/Kotlin
- IDE: Android Studio
- Database: Firebase Firestore
- Maps: Google Maps API
- Authentication: Firebase Auth
- Notifications: Firebase Cloud Messaging (FCM)

## Project Structure
```
smart-school-bus-tracking/
├── app/                    # Main application module
├── admin/                  # Admin module
├── driver/                 # Driver module
├── parent/                 # Parent module
└── common/                 # Shared utilities and models
```

## Setup Instructions

### 1. Prerequisites
- Android Studio (latest version)
- Firebase project created
- Google Maps API key

### 2. Firebase Configuration
1. Create a Firebase project at https://console.firebase.google.com
2. Download `google-services.json`
3. Place it in the `app/` directory
4. Enable Firebase Authentication (Email/Password)
5. Create Firestore Database
6. Enable Firebase Cloud Messaging

### 3. Google Maps API Key
1. Get API key from Google Cloud Console
2. Add to `local.properties`:
   ```
   MAPS_API_KEY=your_api_key_here
   ```

### 4. Build and Run
```bash
./gradlew build
```

## Modules

### Admin Panel
- Manage users (Parents, Drivers)
- Assign students to buses
- Define routes and pickup points
- Live bus tracking dashboard
- Configure alerts

### Driver App
- View daily routes
- Start/end trips
- Mark student attendance
- Share GPS location
- Send emergency alerts

### Parent App
- Track bus in real-time
- Receive notifications
- View child's bus details
- Check ETA

## License
Academic project for BS Computer Science - Virtual University of Pakistan
