# Swifty Companion: A Mobile App for 42 Students

## Overview
Swifty Companion is a mobile application designed to retrieve and display information about 42 students using the 42 API. The application allows users to search for a student's details, view their skills, projects, and profile, offering an intuitive and modern user experience. Developed entirely with Android Studio and Kotlin, this project aims to familiarize developers with mobile app development concepts and API integration.

## Features
- **Authentication via OAuth2** using the 42 API.
- **Search functionality**: Look up students by login and retrieve detailed information.
- **Detailed student profiles**: Display login, email, level, location, skills with percentages, completed and failed projects, and more.
- **Modern UI**: Responsive layout adapting to various screen sizes and platforms.
- **Error handling**: Gracefully manage network errors, invalid logins, and other edge cases.
- **Navigation**: Seamless navigation between search and profile views.

## Notions Covered
- Mobile app development with Kotlin
- Modern UI design principles with flexible layouts
- RESTful API integration and token-based authentication
- Error handling and user experience optimization
- Data security: Storing credentials in a `.env` file

## General Instructions
- **Solo Development**: This project was developed independently.
- **Security Compliance**: Sensitive credentials, API keys, and environment variables are excluded from version control.
- **API Compatibility**: The app uses the latest available version of the 42 API.

## Mandatory Features
To validate the mandatory part of this project, the following criteria were implemented:
- Two views: A search screen and a profile display screen.
- Display of at least four student details, including their profile picture.
- Navigation back to the search view from the profile screen.
- Error handling for login not found and network issues.
- Flexible layout techniques to ensure compatibility across various devices.

## Bonus Features
- Automatic token refresh: The app automatically regenerates a token upon expiration to ensure uninterrupted functionality.

## Development Environment
- **Language**: Kotlin
- **IDE**: Android Studio
- **Frameworks/Libraries**: Jetpack Compose for UI, Retrofit for API calls, and OAuth2 for authentication

## Challenges and Learnings
This project provided an excellent opportunity to:
- Deepen understanding of mobile development with Kotlin.
- Master modern UI principles for responsive design.
- Explore the integration of third-party APIs and OAuth2 authentication.
- Implement robust error handling and optimize the user experience.
