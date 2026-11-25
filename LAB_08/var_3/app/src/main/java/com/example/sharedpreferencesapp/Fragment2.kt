package com.example.sharedpreferencesapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.sharedpreferencesapp.databinding.Fragment2Binding

class Fragment2 : Fragment() {

    private var _binding: Fragment2Binding? = null
    private val binding get() = _binding!!

    private lateinit var databaseHelper: DatabaseHelper

    private lateinit var questions: ArrayList<Question>

    private val answers = ArrayList<String>()

    private var currentQuestion = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = Fragment2Binding.inflate(
            inflater, container, false
        )

        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        databaseHelper = DatabaseHelper(requireContext())

        questions = databaseHelper.getQuestions()

        currentQuestion = 0
        answers.clear()


        binding.buttonBack.setOnClickListener {
            findNavController().popBackStack()
        }


        if (questions.isEmpty()) {
            binding.textQuestion.text = "Нет вопросов"
            binding.buttonNext.isEnabled = false
            return
        }


        showQuestion()


        binding.buttonNext.setOnClickListener {
            nextQuestion()
        }
    }


    private fun showQuestion() {

        val question = questions[currentQuestion]

        binding.textNumber.text =
            "Вопрос ${currentQuestion + 1} из ${questions.size}"

        binding.textQuestion.text = question.question

        binding.radioYes.text = question.answer1
        binding.radioNo.text = question.answer2

        binding.radioGroup.clearCheck()

        if (currentQuestion == questions.size - 1) {
            binding.buttonNext.text = "Завершить тест"
        } else {
            binding.buttonNext.text = "Следующий вопрос"
        }
    }


    private fun nextQuestion() {

        val selectedId =
            binding.radioGroup.checkedRadioButtonId

        if (selectedId == -1) {

            Toast.makeText(
                requireContext(),
                "Выберите вариант ответа",
                Toast.LENGTH_SHORT
            ).show()

            return
        }


        if (selectedId == R.id.radioYes) {

            answers.add(
                questions[currentQuestion].answer1
            )

        } else {

            answers.add(
                questions[currentQuestion].answer2
            )
        }

        currentQuestion++


        if (currentQuestion < questions.size) {

            showQuestion()

        } else {

            binding.buttonNext.isEnabled = false

            showResult()
        }
    }


    private fun showResult() {

        val questionList = ArrayList<String>()

        for (question in questions) {
            questionList.add(question.question)
        }


        val bundle = Bundle()

        bundle.putStringArrayList(
            "questions",
            questionList
        )

        bundle.putStringArrayList(
            "answers",
            answers
        )

        findNavController().navigate(
            R.id.action_fragment2_to_fragment3,
            bundle
        )
    }

    override fun onDestroyView() {

        databaseHelper.close()

        super.onDestroyView()
        _binding = null
    }
}