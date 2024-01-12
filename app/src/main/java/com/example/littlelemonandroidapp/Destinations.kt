package com.example.littlelemonandroidapp

interface Destinations {
    // Define your navigation routes here
    val home: String
    val profile: String
    val onboarding: String
}

object DestinationsImpl : Destinations {
    // Implement the navigation routes
    override val home: String = "Home"
    override val profile: String = "Profile"
    override val onboarding: String = "Onboarding"
}
