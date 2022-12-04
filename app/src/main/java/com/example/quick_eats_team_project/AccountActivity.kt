package com.example.quick_eats_team_project

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.quick_eats_team_project.databinding.ActivityAccountBinding


class AccountActivity : Fragment() {

    private lateinit var mBinding: ActivityAccountBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = ActivityAccountBinding.inflate(inflater, container, false);
        mBinding.registrationButton.setOnClickListener {
            findNavController().navigate(R.id.action_accountActivity_to_fragmentTab)
        }
        return mBinding.root
    }
}