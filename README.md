# Space Adventure 🚀

## Description 📝
Space Adventure is an exciting Android obstacle avoidance game where players pilot a spaceship through dangerous asteroid fields while collecting valuable coins. Test your reflexes and strategic skills as you navigate through increasingly challenging patterns of space debris!

## Game Features 🎮
* Intuitive dual control system:
  * Classic arrow button controls
  * Motion-based tilt controls
* Strategic gameplay mechanics:
  * Dodge dangerous asteroids
  * Collect valuable coins for points
  * Navigate across 5 lanes
* Life system with three chances
* Dynamic obstacle generation
* Vibration feedback on collisions
* Space-themed graphics and UI
* High score tracking with location
* Interactive score map

## How to Play 📱
1. **Start**:
   * Launch the game from the menu screen
   * Choose your preferred control method:
     * Arrow buttons for classic control
     * Device tilt for motion control
2. **Controls**:
   * Arrow Controls:
     * Tap the left arrow button to move left
     * Tap the right arrow button to move right
   * Tilt Controls:
     * Tilt device left to move left
     * Tilt device right to move right
3. **Objectives**:
   * Avoid colliding with falling asteroids
   * Collect coins to increase your score
4. **Lives**:
   * You start with 3 lives (represented by hearts)
   * Each asteroid collision reduces your lives by 1
   * The game ends when you lose all lives

## Game Mechanics ⚙️
* **Player Movement**: 
  * Horizontal movement across five lanes
  * Responsive controls with both button and tilt options
* **Scoring System**:
  * Points awarded for collecting coins
  * Score tracking with location data
* **Obstacles & Collectibles**:
  * Asteroids that fall from the top of the screen
  * Coins that can be collected for points
* **Collision System**:
  * Includes brief invulnerability period after hits
  * Triggers vibration feedback
  * Displays warning messages

## High Score System 🏆
* Tracks and saves your top scores
* Records location data with each score
* View scores on an interactive map
* Tap on scores to focus map location
* Displays date and time of achievements

## Technical Features 💻
* Built for Android using Android Studio
* Written in Kotlin
* Implements Handler for object movement
* Custom collision detection system
* Location services integration
* Google Maps API integration
* SharedPreferences for score storage
* Fragment-based UI for scores view
* Motion sensor integration for tilt controls

## Requirements 📋
* Android device or emulator
* Location services enabled
* Accelerometer for tilt controls
* Google Play Services for maps
* Storage permission for scores
* Location permission for tracking

## Installation 🔧
1. Clone the repository
2. Open the project in Android Studio
3. Configure Google Maps API key
4. Build and run on your Android device or emulator

## Development Structure 🏗️
The game is organized using several key components:
* `MenuActivity`: Game mode selection and start
* `MainActivity`: Core game logic and UI
* `GameOverActivity`: End game state handler
* `ScoresActivity`: High score display and mapping
* `GameManager`: Lives and score management
* `SignalManager`: Feedback system control
* Map and high score list fragments