# Little Lemon — Android

The Little Lemon ordering app, built as a native Android app with Kotlin and Jetpack
Compose. It uses Navigation Compose for screen flow and Preferences DataStore to keep
the user's details between launches.

Repository: https://github.com/Hamza9160/little-lemon-android-capstone

## What's inside

There are three screens:

- **Onboarding** — shown the first time you open the app. You enter your first name,
  last name, and email; the app validates them, saves them, and takes you to Home.
- **Home** — a header with the logo and profile avatar, a hero section with a short
  intro and a search bar, category filter chips, and a scrollable list of menu items
  (name, short description, price, and image).
- **Profile** — shows the details you entered during onboarding, so they're still
  there after a restart. Logging out wipes the saved data and sends you back to
  onboarding.

A wireframe for the Home screen is in `wireframe/home_wireframe.jpg`, and a few
screenshots of the running app are in `screenshots/`.

## Running it

In Android Studio: open this folder, let Gradle sync, pick a device, and hit Run.

From the command line:

```bash
./gradlew :app:assembleDebug
adb install -r app/build/outputs/apk/debug/app-debug.apk
adb shell am start -n com.littlelemon.app/.MainActivity
```

You'll need JDK 17, Android SDK 35, and a device or emulator on API 24 or newer. Menu
and hero images load over the network with Coil, so stay online to see them — the app
runs fine offline, the images just come up blank.

## Project layout

```
app/src/main/java/com/littlelemon/app/
├─ MainActivity.kt
├─ data/            # DataStore profile storage + sample menu data
├─ navigation/      # NavHost and first-run vs returning-user routing
└─ ui/              # theme, shared components, and the three screens
```

## Ideas for later

- A bottom navigation bar plus a menu-detail and cart screen.
- An editable profile with avatar upload.
- Loading the menu from a real API and caching it with Room for offline use.
- Inline per-field validation on the onboarding form.
- Dark theme polish and dynamic color on Android 12+.
- Tests for the repository and the navigation flow.
