package com.example.android.droidhub.files

import android.app.Activity
import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.android.droidhub.databinding.FragmentUploadFilesBinding
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import dmax.dialog.SpotsDialog
import java.util.*

class UploadFilesFragment : Fragment() {
    private var _binding: FragmentUploadFilesBinding? = null
    private val binding get() = _binding!!
    private lateinit var alertDialog: AlertDialog
    private lateinit var load: ProgressBar
    private lateinit var fileName: TextView
    private var filePath: Uri? = null
    private var firebaseStore: FirebaseStorage? = null
    private var storageReference: StorageReference? = null

    companion object {
        private const val PICK_FILE_CODE = 1000
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode != Activity.RESULT_OK) {
            return
        }
        when (requestCode) {
            PICK_FILE_CODE -> {
                filePath = data!!.data
                uploadFiles()
            }
        }
    }

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
        fileName = binding.fileName
        alertDialog = SpotsDialog.Builder().setCancelable(false).setContext(context).build()
        load.visibility = View.INVISIBLE
        firebaseStore = FirebaseStorage.getInstance()
        storageReference = FirebaseStorage.getInstance().getReference("file_upload");

        binding.choose.setOnClickListener {
            chooseFile()
        }

        binding.upload.setOnClickListener {
            uploadFiles()
        }
    }

    //This selects a file from the files directory in the device
    private fun chooseFile() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "*/*"
        intent.addCategory(Intent.CATEGORY_OPENABLE)

        try {
            startActivityForResult(
                Intent.createChooser(intent, "Select a File to Upload"),
                PICK_FILE_CODE
            )
        } catch (ex: ActivityNotFoundException) {
            // Potentially direct the user to the Market with a Dialog
            Toast.makeText(
                activity, "Please install a File Manager.",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private  fun uploadFiles() {
        if ( filePath != null) {
            load.visibility = View.VISIBLE
            alertDialog.show()
            val reference = storageReference?.child("files" + UUID.randomUUID().toString())
            reference?.putFile(filePath!!)?.addOnSuccessListener {
                load.visibility = View.INVISIBLE
                alertDialog.dismiss()
                Toast.makeText(context, "File Upload successful", Toast.LENGTH_SHORT).show()
            }?.addOnFailureListener{ e ->
                load.visibility = View.GONE
                alertDialog.dismiss()
                Toast.makeText(context, "File upload failed", Toast.LENGTH_SHORT).show()
            }?.addOnProgressListener { taskSnapshot ->
                val progress = 100.0 * taskSnapshot.bytesTransferred / taskSnapshot.totalByteCount
                alertDialog.setMessage("Uploading $progress %")
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // Set the binding to null when fragment views are destroyed in order to avoid memory leaks
        _binding = null
    }
}