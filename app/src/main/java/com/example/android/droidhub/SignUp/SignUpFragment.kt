package com.example.android.droidhub.SignUp

import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.example.android.droidhub.R
import com.example.android.droidhub.databinding.FragmentSignUpBinding
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth

class SignUpFragment : Fragment() {
    private var _binding: FragmentSignUpBinding? = null
    private val binding get() = _binding!!
    private lateinit var name: TextInputEditText
    private lateinit var email: TextInputEditText
    private  lateinit var password: TextInputEditText
    private lateinit var auth: FirebaseAuth
    private lateinit var load:ProgressBar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSignUpBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        load = binding.load
        name = binding.nameEditText
        email = binding.emailEditText
        password = binding.passwordEditText
        load.visibility = View.INVISIBLE
        auth = FirebaseAuth.getInstance()

        binding.orLogin.setOnClickListener {
            view.findNavController().navigate(R.id.action_signUpFragment_to_logInFragment)
        }

        binding.signUpButton.setOnClickListener {
            signUpUser()
        }
    }

    private fun  signUpUser() {
        load.visibility = View.VISIBLE
        if (name.text.toString().isEmpty()) {
            name.error = "Please provide your name"
            name.requestFocus()
            return
        }
        if (name.length() < 4) {
            name.error = "Name is too short"
            name.requestFocus()
            return
        }
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

        auth.createUserWithEmailAndPassword(email.text.toString(), password.text.toString())
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    user!!.sendEmailVerification()
                        .addOnCompleteListener { task ->
                            load.visibility = View.INVISIBLE
                            if (task.isSuccessful) {
                                view?.findNavController()?.navigate(R.id.action_signUpFragment_to_filesFragment)
                            }
                        }
                    Toast.makeText(
                        context, "User Sign up successful", Toast.LENGTH_SHORT
                    ).show()
                } else {
                    Toast.makeText(
                        context, "User Sign up failed. Try again in a few moment",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }

    override fun onStart() {
        super.onStart()
        if (auth.currentUser != null) {
            Log.d("UID  ", auth.currentUser!!.uid)
            Navigation.findNavController(requireView())
                .navigate(R.id.action_signUpFragment_to_filesFragment)
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        // Set the binding to null when fragment views are destroyed in order to avoid memory leaks
        _binding = null
    }
}

