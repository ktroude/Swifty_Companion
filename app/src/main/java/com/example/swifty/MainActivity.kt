package com.example.swifty

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

/**
 * The main activity of the application.
 * Acts as the entry point and container for the app's navigation and UI components.
 */
class MainActivity : AppCompatActivity() {

    /**
     * Called when the activity is starting.
     * Sets up the UI and adjusts the system bar insets for a seamless immersive experience.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down,
     * this Bundle contains the data it most recently supplied in `onSaveInstanceState`. Otherwise, it is null.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);

        // Enable edge-to-edge display, allowing content to extend into system bars.
        enableEdgeToEdge();

        // Set the layout resource for the activity.
        // This layout contains the `NavHostFragment` for managing navigation.
        setContentView(R.layout.activity_main);

        // Adjust padding to account for system bars (status and navigation bars).
        // Ensures UI elements are properly positioned on screens with system overlays.
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { view, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            view.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            insets;
        }
    }
}
