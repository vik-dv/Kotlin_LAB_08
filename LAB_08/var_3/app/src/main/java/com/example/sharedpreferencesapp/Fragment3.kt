package com.example.sharedpreferencesapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.sharedpreferencesapp.databinding.Fragment3Binding

class Fragment3 : Fragment() {

    private var _binding: Fragment3Binding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = Fragment3Binding.inflate(
            inflater, container, false
        )

        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)


        val questions =
            arguments?.getStringArrayList("questions")
                ?: arrayListOf<String>()


        val answers =
            arguments?.getStringArrayList("answers")
                ?: arrayListOf<String>()


        val result = StringBuilder()

        result.append("Всего вопросов: ${questions.size}\n")
        result.append("Получено ответов: ${answers.size}\n\n")

        for (i in questions.indices) {

            result.append("${i + 1}. ${questions[i]}\n")

            result.append(
                "Ответ: ${answers.getOrElse(i) { "Нет ответа" }}\n\n"
            )
        }


        binding.textResult3.text = result.toString()


        binding.buttonBack3.setOnClickListener {

            findNavController().popBackStack()
        }
    }

    override fun onDestroyView() {

        super.onDestroyView()
        _binding = null
    }
}