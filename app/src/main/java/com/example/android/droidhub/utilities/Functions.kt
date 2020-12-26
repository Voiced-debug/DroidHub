package com.example.android.droidhub.utilities

import android.util.Patterns
import android.widget.Toast
import androidx.fragment.app.Fragment

fun Fragment.showToast(message: String) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}

fun verifyName(fragment: Fragment, name: String?): Boolean {
    if (name.isNullOrEmpty()) {
        fragment.showToast("Please enter your name")
        return false
    }
    if (name.length < 5) {
        fragment.showToast("Name is too short")
        return false
    }
    return true
}

fun verifyEmail(fragment: Fragment, email: String?): Boolean {
    if (email.isNullOrEmpty()) {
        fragment.showToast("Please enter your email")
        return false
    }
    if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
        fragment.showToast("Email address is not valid")
        return false
    }
    return true
}

fun verifyPassword(fragment: Fragment, password: String?): Boolean {
    if (password.isNullOrEmpty()) {
        fragment.showToast("Please enter a password")
        return false
    }
    if (password.length < 5) {
        fragment.showToast("Password is too short")
        return false
    }
    return true
}