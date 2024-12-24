# 🚀 Space Game - Android App

## 📱 Description
A fast-paced arcade-style space game where players control a spaceship to dodge falling asteroids. The game features:
- Simple left/right controls
- Falling asteroids with increasing difficulty
- Lives system with heart indicators
- Vibration and toast notifications on collision
- Game over screen with status message

## 🎮 Game Mechanics
- Player controls a spaceship at the bottom of the screen
- Asteroids fall randomly from the top in three possible columns
- Player must dodge asteroids by moving left or right
- Player starts with 3 lives (hearts)
- Collision with an asteroid results in:
  - Loss of one life
  - Vibration feedback
  - Toast notification
  - Brief invulnerability period
- Game ends when all lives are lost

## 🛠️ Technical Implementation

### ⚙️ Core Components
1. `MainActivity`: Main game screen handling:
   - Game initialization
   - UI updates
   - Collision detection
   - Asteroid movement
   - Player input

2. `GameManager`: Manages game state:
   - Lives tracking
   - Game over condition
   - Life reduction logic

3. `SignalManager`: Handles user feedback:
   - Device vibration (with SDK version compatibility)
   - Toast notifications
   - Singleton pattern implementation

4. `GameOverActivity`: Displays game over screen with:
   - Final status message
   - Auto-dismiss after 1 second

### 🎨 UI Components
- Custom layouts using RelativeLayout and LinearLayoutCompat
- Material Design components integration
- Dynamic visibility management for game objects
- Heart indicators for life tracking
- Custom button styling for controls

### 📂 Project Structure
```
com.example.hw1_space/
├── App.kt
├── MainActivity.kt
├── GameOverActivity.kt
├── log/
│   └── GameManager.kt
└── utilities/
    ├── Constants.kt
    └── SignalManager.kt
```

## 💻 Requirements
- Android SDK version supporting Material Design components
- Minimum SDK version supporting VibrationEffect (API 26+)
- Compatible with both modern and legacy vibration implementations

## ⚡ Setup
1. Clone the repository
2. Open the project in Android Studio
3. Sync Gradle files
4. Build and run on your Android device or emulator

## 🎯 Future Improvements
- Add score tracking system
- Implement difficulty levels
- Add sound effects and background music
- Include high score leaderboard
- Add power-ups and special abilities

## ✨ Credits
Created as part of HW1 Space project.