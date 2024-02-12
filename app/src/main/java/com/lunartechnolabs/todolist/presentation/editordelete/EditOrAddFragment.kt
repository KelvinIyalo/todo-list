package com.lunartechnolabs.todolist.presentation.editordelete

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavArgs
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.lunartechnolabs.todolist.databinding.FragmentEditOrAddBinding
import com.lunartechnolabs.todolist.domain.model.Task
import com.lunartechnolabs.todolist.presentation.dashboard.DashBoardViewModel
import com.lunartechnolabs.todolist.util.transformIntoDatePicker
import com.lunartechnolabs.todolist.util.transformIntoTimePicker
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditOrAddFragment : Fragment() {

    private  var _binding: FragmentEditOrAddBinding ?= null
    private val binding get() = _binding!!
    private val viewModel : DashBoardViewModel by viewModels()
    private lateinit var radioButton: RadioButton
    private val args : EditOrAddFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditOrAddBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onClickListener()
    }

    private fun onClickListener() {

        binding.edtTime.transformIntoTimePicker(requireContext(), "HH:mm aaa")
        binding.edtDate.transformIntoDatePicker(requireContext(), "MM/dd/yyyy")

        if (args.isForUpdate){
            //update
            updateTask()
        }else{
            //add
            addTask()
        }
    }

    //Add task
    private fun addTask() {
        TODO("Not yet implemented")
    }

    //Update task
    private fun updateTask() {
        TODO("Not yet implemented")
    }

    private fun validation(): Boolean {
        return if (binding.edtTitle.text.isNullOrBlank()) {
            binding.inputTitle.error = "Title can't Be Empty!!"
            false
        } else{
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