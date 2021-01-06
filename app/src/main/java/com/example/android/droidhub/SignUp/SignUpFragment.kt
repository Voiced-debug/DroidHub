package com.example.android.droidhub.SignUp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.android.droidhub.R
import com.example.android.droidhub.databinding.FragmentSignUpBinding
import com.example.android.droidhub.utilities.verifyEmail
import com.example.android.droidhub.utilities.verifyName
import com.example.android.droidhub.utilities.verifyPassword
import com.google.firebase.auth.FirebaseAuth

class SignUpFragment : Fragment() {
    private var _binding: FragmentSignUpBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth
    private lateinit var load:ProgressBar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSignUpBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = FirebaseAuth.getInstance()

        binding.orLogin.setOnClickListener {
            view.findNavController().navigate(R.id.action_signUpFragment_to_logInFragment)
        }

        load = binding.load
        binding.signUpButton.setOnClickListener {
            load.visibility = View.VISIBLE

            if (verifyName(this@SignUpFragment, binding.nameEditText.text.toString()) &&
                    verifyEmail(this@SignUpFragment, binding.emailEditText.text.toString()) &&
                    verifyPassword(this@SignUpFragment, binding.passwordEditText.text.toString()))
            {

            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()

        // Set the binding to null when fragment views are destroyed in order to avoid memory leaks
        _binding = null
    }
}