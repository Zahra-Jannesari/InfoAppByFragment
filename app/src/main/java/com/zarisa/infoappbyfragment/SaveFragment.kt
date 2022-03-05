package com.zarisa.infoappbyfragment

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.zarisa.infoappbyfragment.databinding.FragmentRegisterBinding
import com.zarisa.infoappbyfragment.databinding.FragmentSaveBinding


class SaveFragment : Fragment() {
    private var sharedPreferences: SharedPreferences? = null
    lateinit var binding:FragmentSaveBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding= FragmentSaveBinding.inflate(layoutInflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedPreferences = this.activity?.getSharedPreferences("Information", Context.MODE_PRIVATE)
        initViews()
    }

    private fun initViews() {
        var sentName=requireArguments().getString(fullName)
        binding.textViewFullName.text="Full Name: $sentName"
        var sentUserName=requireArguments().getString(userName)
        binding.textViewUserName.text="Username: $sentUserName"
        var sentEmail=requireArguments().getString(email)
        binding.textViewEmail.text="Email: $sentEmail"
        var sentPassword=requireArguments().getString(password)
        binding.textViewPassword.text="Password: $sentPassword"
        var sentGender=requireArguments().getString(Gender)
        binding.textViewGenderInfo.text="Gender: $sentGender"

        val editor = sharedPreferences?.edit()
        binding.buttonSaveInfo.setOnClickListener {
            editor?.putString(fullName,sentName)
            editor?.putString(userName, sentUserName)
            editor?.putString(email,sentEmail)
            editor?.putString(password,sentPassword)
            editor?.putString(Gender,sentGender)
            editor?.apply()
//            val bundle= bundleOf("saved" to true)
            findNavController().navigate(R.id.action_saveFragment_to_registerFragment)
        }
    }

}