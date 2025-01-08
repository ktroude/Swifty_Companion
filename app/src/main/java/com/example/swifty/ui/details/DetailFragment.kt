package com.example.swifty.ui.details

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.swifty.R
import com.example.swifty.data.model.User

/**
 * Fragment responsible for displaying detailed information about a user.
 */
class DetailFragment : Fragment() {

    /**
     * Retrieves arguments passed to this fragment, specifically the `User` object.
     */
    private val args: DetailFragmentArgs by navArgs();

    /**
     * Inflates the layout for the fragment.
     *
     * @param inflater The layout inflater.
     * @param container The container holding this fragment.
     * @param savedInstanceState The saved state of the fragment.
     * @return The root view of the fragment's layout.
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_detail, container, false);
    }

    /**
     * Initializes the UI components and populates them with user data.
     *
     * @param view The root view of the fragment.
     * @param savedInstanceState The saved state of the fragment.
     */
    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState);

        // Retrieve the user object passed to this fragment
        val user: User = args.user;

        // Initialize views
        val profileImageView = view.findViewById<ImageView>(R.id.profileImageView);
        val loginTextView = view.findViewById<TextView>(R.id.loginTextView);
        val emailTextView = view.findViewById<TextView>(R.id.emailTextView);
        val walletTextView = view.findViewById<TextView>(R.id.walletTextView);
        val firstNameTextView = view.findViewById<TextView>(R.id.firstNameTextView);
        val lastNameTextView = view.findViewById<TextView>(R.id.lastNameTextView);
        val skillsTextView = view.findViewById<TextView>(R.id.skillsTextView);
        val projectsTextView = view.findViewById<TextView>(R.id.projectsTextView);
        val cursusTextView = view.findViewById<TextView>(R.id.cursusTextView);

        // Load user profile image using Glide
        Glide.with(this)
            .load(user.image?.link)
            .into(profileImageView);

        // Set user information
        loginTextView.text = user.login;
        emailTextView.text = "Email: ${user.email ?: "N/A"}";
        walletTextView.text = "Wallet: ${user.wallet}";

        // Display cursus information
        val cursusString = user.cursusUsers?.joinToString(separator = "\n") { cursusUser ->
            val cursusName = cursusUser.cursus?.name ?: "Unknown Cursus";
            val level = cursusUser.level;
            "$cursusName - Level: $level";
        } ?: "No cursus available.";
        cursusTextView.text = cursusString;

        firstNameTextView.text = "First Name: ${user.firstName}";
        lastNameTextView.text = "Last Name: ${user.lastName}";

        // Display skills with percentage of maximum level
        val maxLevel = 21.0 // Maximum skill level
        val skillsString = user.cursusUsers?.flatMap { it.skills ?: emptyList() }
            ?.joinToString(separator = "\n") { skill ->
                val percentage = (skill.level / maxLevel) * 100
                "${skill.name}: Level ${"%.2f".format(skill.level)} (${percentage.toInt()}%)"
            } ?: "No skills available.";
        skillsTextView.text = "Skills:\n$skillsString";

        // Display user projects
        val projectsString = user.projectsUsers?.joinToString(separator = "\n") { projectUser ->
            val projectName = projectUser.project?.name ?: "Unknown Project";
            val status = projectUser.status;
            val mark = projectUser.finalMark?.toString() ?: "N/A";
            "$projectName - Status: $status, Mark: $mark";
        } ?: "No projects available.";
        projectsTextView.text = "Projects:\n$projectsString";
    }
}

