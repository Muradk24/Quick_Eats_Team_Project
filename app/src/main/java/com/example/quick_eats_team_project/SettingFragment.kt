package com.example.quick_eats_team_project

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.quick_eats_team_project.databinding.FragmentSettingBinding
import com.google.firebase.auth.FirebaseAuth

class SettingFragment: Fragment() {
    private lateinit var mBinding: FragmentSettingBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentSettingBinding.inflate(inflater, container, false);
        mBinding.tvLogout.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            val sharedPreference =  requireActivity().getSharedPreferences("PREFERENCE_USER", Context.MODE_PRIVATE)
sharedPreference .edit()
    .remove("userId").commit()
            sharedPreference.edit().remove("useremail").commit()
             sharedPreference.edit().remove("password").commit()
             startActivity(Intent(requireContext(),LoginAcitivity::class.java))
            requireActivity().finish()
        }
        return mBinding.root
    }
}