# Space Adventure 🚀

## Description
Space Adventure is an exciting Android obstacle avoidance game where players pilot a spaceship through dangerous asteroid fields. Test your reflexes and piloting skills as you navigate through increasingly challenging patterns of space debris!

## Game Features
* Intuitive spaceship control system
* Strategic asteroid dodging gameplay
* Life system with three chances
* Dynamic asteroid generation
* Vibration feedback on collisions
* Space-themed graphics and UI

## How to Play
1. **Start**: Launch the game to begin your space mission
2. **Controls**:
   * Tap the left arrow button to move left
   * Tap the right arrow button to move right
3. **Objective**: Avoid colliding with falling asteroids
4. **Lives**:
   * You start with 3 lives (represented by hearts)
   * Each collision with an asteroid reduces your lives by 1
   * The game ends when you lose all lives

## Game Mechanics
* **Player Character**: A spaceship fixed at the bottom of the screen
* **Obstacles**: Asteroids that fall from the top of the screen
* **Movement**: Horizontal movement across three lanes
* **Collision System**:
   * Includes a brief invulnerability period after each hit
   * Triggers vibration feedback
   * Displays a warning message
* **Asteroid Generation**:
   * Random generation in three columns
   * Continuous falling pattern

## Technical Features
* Built for Android using Android Studio
* Written in Kotlin
* Implements Handler for asteroid movement
* Uses custom collision detection system
* Includes vibration feedback system
* Contains game over screen with status message
* Automatic activity transition on game end

## Requirements
* Android device or emulator
* Material Design components support
* Vibration permission enabled

## Installation
1. Clone the repository
2. Open the project in Android Studio
3. Build and run on your Android device or emulator

## Game Over
The game ends when all three lives are lost, displaying a "Game Over! 😭" message and transitioning to the game over screen.

## Development
The game is structured using several key components:
* `MainActivity`: Main game logic and UI
* `GameOverActivity`: Handles game end state
* `GameManager`: Manages lives and game state
* `SignalManager`: Manages vibrations and toast messages
* Custom layouts for both game and game over screens

## Credits
Created as part of Mobile Development course homework assignment.