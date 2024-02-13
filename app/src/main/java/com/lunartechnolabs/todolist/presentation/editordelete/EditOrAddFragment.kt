package com.lunartechnolabs.todolist.presentation.editordelete

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.lunartechnolabs.todolist.databinding.FragmentEditOrAddBinding
import com.lunartechnolabs.todolist.domain.model.Task
import com.lunartechnolabs.todolist.presentation.dashboard.DashBoardViewModel
import com.lunartechnolabs.todolist.util.showDialog
import com.lunartechnolabs.todolist.util.transformIntoDatePicker
import com.lunartechnolabs.todolist.util.transformIntoTimePicker
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditOrAddFragment : Fragment() {

    private var _binding: FragmentEditOrAddBinding? = null
    private val binding get() = _binding!!
    private val viewModel: DashBoardViewModel by viewModels()
    private lateinit var radioButton: RadioButton
    private val args: EditOrAddFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditOrAddBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onClickListener()
        binding.addArticleBtn.setOnClickListener {
            if (validation()) {
                if (args.isForUpdate) {
                    //update
                    updateTask()
                } else {
                    //add
                    addTask()
                }
            }
        }

    }

    private fun onClickListener() {

        binding.edtTime.transformIntoTimePicker(requireContext(), "HH:mm aaa")
        binding.edtDate.transformIntoDatePicker(requireContext(), "MM/dd/yyyy")

        if (args.isForUpdate) {
            //update
            updateView()
        } else {
            //add
            addTask()
        }
    }

    //Add task
    private fun addTask() {
        if (validation()) {
            viewModel.addArticle(taskInit())
            showDialog(requireContext(), "Your task ${taskInit().title}, has been added successfully") {
                findNavController().popBackStack()
            }
        }
    }

    fun taskInit(): Task {
        val id = binding.radioGroup.checkedRadioButtonId
        val radioButton = binding.radioGroup.findViewById<RadioButton>(id)
        return Task(
            title = binding.edtTitle.text.toString(),
            detail = binding.edtBody.text.toString(),
            priority = radioButton.text.toString(),
            taskDate = binding.edtDate.text.toString(),
            taskTime = binding.edtTime.text.toString()
        )
    }

    //Update task
    private fun updateView() {
        binding.edtTitle.setText(args.taskData?.title)
        binding.edtBody.setText(args.taskData?.title)
        binding.edtDate.setText(args.taskData?.taskDate)
        binding.edtTime.setText(args.taskData?.taskTime)
    }

    private fun updateTask() {
        if (validation()) {
            val task = taskInit().copy(id = args.taskData?.id)
            viewModel.updateArticle(task)
            showDialog(
                requireContext(),
                "Your task ${taskInit().title}, has been Updated successfully"
            ) {
                findNavController().popBackStack()
            }
        }
    }

    private fun validation(): Boolean {
        return if (binding.edtTitle.text.isNullOrBlank()) {
            binding.inputTitle.error = "Title can't Be Empty!!"
            false
        } else {
            true
        }
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

}

/*
 * Add task
 * Update task
 */