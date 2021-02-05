package com.example.android.droidhub.files

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.android.droidhub.R
import com.example.android.droidhub.databinding.FragmentFilesBinding
import com.google.firebase.auth.FirebaseAuth

class FilesFragment : Fragment() {
    private var _binding: FragmentFilesBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFilesBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.uploadFile.setOnClickListener {
            view.findNavController().navigate(R.id.action_filesFragment_to_uploadFilesFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // Set the binding to null when fragment views are destroyed in order to avoid memory leaks
        _binding = null
    }

}