package com.example.android.droidhub.main

import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.android.droidhub.R
import com.example.android.droidhub.databinding.FragmentLogInBinding
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class LogInFragment : Fragment() {
    private var _binding: FragmentLogInBinding? = null
    private val binding get() = _binding!!
    private lateinit var email: TextInputEditText
    private  lateinit var password: TextInputEditText
    private lateinit var auth: FirebaseAuth
    private lateinit var load: ProgressBar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLogInBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        load = binding.load
        email = binding.emailEditText
        password = binding.passwordEditText
        load.visibility = View.INVISIBLE
        auth = FirebaseAuth.getInstance()

        binding.orSignUp.setOnClickListener {
            view.findNavController().navigate(R.id.action_logInFragment_to_signUpFragment)
        }

        binding.logInButton.setOnClickListener {
            signInUser()
        }

    }

    private fun signInUser() {
        load.visibility = View.VISIBLE

        if (email.text.toString().isEmpty()) {
            email.error = "Please provide your email"
            email.requestFocus()
            return
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email.text.toString()).matches()) {
            email.error = "Please enter a valid email"
            email.requestFocus()
            return
        }

        if (password.text.toString().isEmpty()) {
            password.error = "Please enter a password"
            password.requestFocus()
            return
        }

        if (password.length() < 6) {
            password.error = "Password is too short"
            password.requestFocus()
            return
        }

        auth.signInWithEmailAndPassword(email.text.toString(), password.text.toString())
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    load.visibility = View.GONE
                    Toast.makeText(
                        context, "User successfully signed in", Toast.LENGTH_SHORT
                    ).show()
                    val user = auth.currentUser
                    updateUI(user)
                } else {
                    load.visibility = View.INVISIBLE
                    updateUI(null)
                }
            }
    }

    override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        updateUI(currentUser)
    }

    private fun updateUI(currentUser: FirebaseUser?) {
        if (currentUser != null) {
            if (currentUser.isEmailVerified) {
                view?.findNavController()?.navigate(R.id.action_logInFragment_to_filesFragment)
            } else {
                Toast.makeText(
                    context, "Please verify your email address",
                    Toast.LENGTH_SHORT
                ).show()
            }
        } else {
            Toast.makeText(
                context, "User sign In failed. Check user details",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()

        // Set the binding to null when fragment views are destroyed in order to avoid memory leaks
        _binding = null
    }
}