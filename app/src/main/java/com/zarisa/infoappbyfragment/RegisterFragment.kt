package com.zarisa.infoappbyfragment

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.zarisa.infoappbyfragment.databinding.FragmentRegisterBinding

const val fullName="fullName"
const val userName="userName"
const val email="email"
const val password="password"
const val Gender="gender"
class RegisterFragment : Fragment() {

    private lateinit var binding: FragmentRegisterBinding
    private var infoList= mutableSetOf<EditText>()
    private var sharedPreferences: SharedPreferences? = null
    private var gender=""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding= FragmentRegisterBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedPreferences = this.activity?.getSharedPreferences("Information", Context.MODE_PRIVATE)
        initViews()
    }
    private fun initViews(){
        infoList = mutableSetOf(binding.textFieldFullName,binding.textFieldUsername,binding.textFieldEmail,
            binding.textFieldPassword,binding.textFieldRetypePassword)
        if(sharedPreferences?.getString(fullName,"")!="")
           putDataForEdit()
        binding.buttonRegister.setOnClickListener {
            if (checkInfo()) {
                val bundle = bundleOf(
                    fullName to binding.textFieldFullName.text.toString(),
                    userName to binding.textFieldUsername.text.toString(),
                    email to binding.textFieldEmail.text.toString(),
                    password to binding.textFieldPassword.text.toString(),
                    Gender to gender
                )
                findNavController().navigate(R.id.action_registerFragment_to_saveFragment, bundle)
            }
        }
    }
    private fun checkInfo(): Boolean {
        var result=true
        when {
            binding.radioButtonFemale.isChecked -> gender="Female"
            binding.radioButtonMale.isChecked -> gender="Male"
            else -> {
                Toast.makeText(context, "Enter Gender!", Toast.LENGTH_SHORT).show()
                result=false
            }
        }
        infoList.forEach{
            if(it.text.isBlank()) {
                it.error ="Fill the field!"
                result= false
            }
        }
        binding.textFieldEmail.text.let{
            if (it != null) {
                if (it.isNotBlank()&&!it.toString().isEmailValid()){
                    binding.textFieldEmail.error="Email invalid!"
                    result =false
                }
            }
        }
        if(binding.textFieldPassword.text.toString()!=binding.textFieldRetypePassword.text.toString()){
            binding.textFieldRetypePassword.error="Password do not match!"
            result=false
        }
        return result
    }
    private fun String.isEmailValid(): Boolean {
        return !TextUtils.isEmpty(this) && android.util.Patterns.EMAIL_ADDRESS.matcher(this).matches()
    }
    private fun putDataForEdit() {
        binding.textFieldFullName.setText(sharedPreferences?.getString(fullName,""))
        binding.textFieldUsername.setText(sharedPreferences?.getString(userName,""))
        binding.textFieldEmail.setText(sharedPreferences?.getString(email,""))
        binding.textFieldPassword.setText(sharedPreferences?.getString(password,""))
        when(sharedPreferences?.getString(Gender,"")){
            "Female"->binding.radioButtonFemale.isChecked=true
            "Male"->binding.radioButtonMale.isChecked=true
        }
    }
}