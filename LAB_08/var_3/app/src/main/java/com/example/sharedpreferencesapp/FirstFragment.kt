package com.example.sharedpreferencesapp

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.sharedpreferencesapp.databinding.FragmentFirstBinding
import androidx.navigation.fragment.findNavController

class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null
    private val binding get() = _binding!!

    private lateinit var preferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentFirstBinding.inflate(
            inflater, container, false
        )

        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)


        preferences = requireContext().getSharedPreferences(
            "UserData",
            Context.MODE_PRIVATE
        )

        loadData()


        binding.buttonSave.setOnClickListener {
            saveData()
        }

        binding.buttonNext2.setOnClickListener {

            findNavController().navigate(
                R.id.action_firstFragment_to_fragment2
            )
        }
    }

    private fun saveData() {

        val login = binding.editLogin.text.toString()
        val password = binding.editPassword.text.toString()

        preferences.edit()
            .putString("login", login)
            .putString("password", password)
            .apply()

        Toast.makeText(
            requireContext(),
            "Данные сохранены",
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun loadData() {

        val login = preferences.getString("login", "")
        val password = preferences.getString("password", "")

        binding.editLogin.setText(login)
        binding.editPassword.setText(password)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}