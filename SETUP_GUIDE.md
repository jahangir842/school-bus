# Smart School Bus Tracking System - Setup Guide

## Prerequisites
1. Android Studio (latest version)
2. JDK 17 or higher
3. Android SDK (API 24+)
4. Firebase account
5. Google Cloud account (for Maps API)

## Step 1: Firebase Setup

### Create Firebase Project
1. Go to [Firebase Console](https://console.firebase.google.com)
2. Click "Add project"
3. Enter project name: "SmartSchoolBusTracking"
4. Follow the setup wizard

### Add Android App to Firebase
1. In Firebase Console, click "Add app" → Android
2. Register app with package name: `com.schoolbus.tracking`
3. Download `google-services.json`
4. Place it in the `app/` directory

### Enable Firebase Services
1. **Authentication**
   - Go to Authentication → Sign-in method
   - Enable "Email/Password"

2. **Firestore Database**
   - Go to Firestore Database
   - Click "Create database"
   - Start in test mode (change rules later)

3. **Cloud Messaging**
   - Already enabled by default

### Firestore Security Rules
```
rules_version = '2';
service cloud.firestore {
  match /databases/{database}/documents {
    match /{document=**} {
      allow read, write: if request.auth != null;
    }
  }
}
```

## Step 2: Google Maps API Setup

1. Go to [Google Cloud Console](https://console.cloud.google.com)
2. Create a new project or select existing
3. Enable "Maps SDK for Android"
4. Go to Credentials → Create Credentials → API Key
5. Copy the API key
6. Open `local.properties` file
7. Replace `YOUR_GOOGLE_MAPS_API_KEY_HERE` with your actual API key

## Step 3: Project Configuration

### Update local.properties
```properties
sdk.dir=/path/to/your/android/sdk
MAPS_API_KEY=YOUR_ACTUAL_API_KEY_HERE
```

### Sync Gradle
1. Open project in Android Studio
2. Click "Sync Project with Gradle Files"
3. Wait for dependencies to download

## Step 4: Create Initial Admin User

Use Firebase Console to manually create the first admin user:

1. Go to Firebase Console → Authentication
2. Click "Add user"
3. Email: `admin@schoolbus.com`
4. Password: `admin123` (change after first login)

Then add user document in Firestore:
```
Collection: users
Document ID: [same as auth UID]
Fields:
  - email: "admin@schoolbus.com"
  - name: "Admin User"
  - role: "ADMIN"
  - phoneNumber: "+1234567890"
  - createdAt: [current timestamp]
```

## Step 5: Build and Run

### Using Android Studio
1. Connect Android device or start emulator
2. Click Run ▶️ button
3. Select target device
4. Wait for build to complete

### Using Command Line
```bash
./gradlew assembleDebug
./gradlew installDebug
```

## Step 6: Testing the Application

### Test Admin Module
1. Login with admin credentials
2. Navigate to "Manage Users"
3. Create driver and parent accounts
4. Navigate to "Manage Buses"
5. Add buses and assign drivers

### Test Driver Module
1. Logout and login as driver
2. Start a trip
3. Mark student attendance using QR scanner
4. End trip

### Test Parent Module
1. Logout and login as parent
2. View child details
3. Track bus in real-time

## Troubleshooting

### Google Maps not showing
- Verify API key is correct in `local.properties`
- Check if Maps SDK is enabled in Google Cloud Console
- Ensure billing is enabled for Google Cloud project

### Firebase connection issues
- Verify `google-services.json` is in `app/` directory
- Check package name matches in Firebase Console
- Rebuild project after adding google-services.json

### Location tracking not working
- Grant location permissions in device settings
- Enable GPS on device
- Check if location services are enabled

### Build errors
- Clean project: Build → Clean Project
- Rebuild: Build → Rebuild Project
- Invalidate caches: File → Invalidate Caches / Restart

## Production Deployment

### Update Firestore Rules
```
rules_version = '2';
service cloud.firestore {
  match /databases/{database}/documents {
    match /users/{userId} {
      allow read: if request.auth != null;
      allow write: if request.auth.uid == userId || 
                     get(/databases/$(database)/documents/users/$(request.auth.uid)).data.role == 'ADMIN';
    }
    
    match /buses/{busId} {
      allow read: if request.auth != null;
      allow write: if get(/databases/$(database)/documents/users/$(request.auth.uid)).data.role == 'ADMIN';
    }
    
    match /students/{studentId} {
      allow read: if request.auth != null;
      allow write: if get(/databases/$(database)/documents/users/$(request.auth.uid)).data.role == 'ADMIN';
    }
  }
}
```

### Generate Signed APK
1. Build → Generate Signed Bundle / APK
2. Select APK
3. Create new keystore or use existing
4. Fill in keystore details
5. Select release build variant
6. Click Finish

## Support
For issues and questions, contact your project supervisor:
- Name: Mr. Jibran Khan
- Email: jibrankhan@vu.edu.pk
