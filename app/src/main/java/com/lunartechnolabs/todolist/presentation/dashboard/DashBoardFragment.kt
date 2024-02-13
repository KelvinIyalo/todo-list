package com.lunartechnolabs.todolist.presentation.dashboard

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.lunartechnolabs.todolist.databinding.FragmentDashBoardBinding
import com.lunartechnolabs.todolist.domain.model.Task
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.ArrayList


/**
 * Dashboard Task Fragment
 * Hilt view model will create entry point for Fragment
 */
@AndroidEntryPoint
class DashBoardFragment : Fragment() {

    private var _binding: FragmentDashBoardBinding? = null
    private val binding get() = _binding!!
    private val viewModel: DashBoardViewModel by viewModels()
    private var taskAdapter: TaskAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDashBoardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.fetchOfflineArticle()
        //Floating button code
        binding.floatingActionButton.setOnClickListener {
            val action =
                DashBoardFragmentDirections.actionDashBoardFragmentToEditOrAddFragment(null, false)
            findNavController().navigate(action)
        }
        initializeAdapter()

        lifecycleScope.launch {
            viewModel.offlineArticleUIState.collectLatest {
                taskAdapter?.submitList(it.data)
            }
        }

    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

    //open view fragment
    private fun openViewFragment(task: Task) {
        val action = DashBoardFragmentDirections.actionDashBoardFragmentToViewTask(task)
        findNavController().navigate(action)
    }

    //open update Fragment
    private fun openUpdateFragment(task: Task) {
        val action =
            DashBoardFragmentDirections.actionDashBoardFragmentToEditOrAddFragment(task, true)
        findNavController().navigate(action)
    }

    private fun initializeAdapter() {
        taskAdapter = TaskAdapter(
            onItemChecked = { itemChecked ->
                openUpdateFragment(itemChecked)
            },
            onItemClicked = { itemAtPosition ->
                openViewFragment(itemAtPosition)
            }
        )

        binding.articleRcv.adapter = taskAdapter
    }
}

/*
 * Add adapter
 */