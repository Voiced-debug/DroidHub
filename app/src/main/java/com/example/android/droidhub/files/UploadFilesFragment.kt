package com.example.android.droidhub.files

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import com.example.android.droidhub.databinding.FragmentUploadFilesBinding

class UploadFilesFragment : Fragment() {
    private var _binding: FragmentUploadFilesBinding? = null
    private val binding get() = _binding!!
    private lateinit var load: ProgressBar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentUploadFilesBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        load = binding.load
        binding.upload.setOnClickListener {
            load.visibility = View.VISIBLE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()

        // Set the binding to null when fragment views are destroyed in order to avoid memory leaks
        _binding = null
    }
}