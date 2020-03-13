package com.example.katundu.ui.login;

import android.util.Patterns;

import androidx.lifecycle.ViewModel;

import com.example.katundu.R;

public class RegisterViewModel extends ViewModel {

    // A placeholder username validation check
    private boolean isUserNameValid(String username) {
        if (username == null) {
            return false;
        }
        if (username.contains("@")) {
            return Patterns.EMAIL_ADDRESS.matcher(username).matches();
        } else {
            return !username.trim().isEmpty();
        }
    }

    // A placeholder password validation check
    public boolean isPasswordValid(String password) {
        return password != null && password.trim().length() >= 8;
    }
}
