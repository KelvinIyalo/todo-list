package com.lunartechnolabs.todolist.presentation.dashboard


import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.lunartechnolabs.todolist.databinding.SingleTaskBinding
import com.lunartechnolabs.todolist.domain.model.Task


class TaskAdapter(
    private val onItemClicked: (itemAtPosition: Task) -> Unit,
    private val onItemChecked: (itemAtPosition: Task) -> Unit
) : ListAdapter<Task, TaskAdapter.TaskVH>(object :
    DiffUtil.ItemCallback<Task>() {

    override fun areItemsTheSame(oldItem: Task, newItem: Task): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Task, newItem: Task): Boolean {
        return oldItem == newItem
    }

}) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskVH {
        //inflate the view to be used by the payment option view holder
        val binding =
            SingleTaskBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TaskVH(binding, onItemClick = { position ->
            val itemAtPosition = currentList[position]
            onItemClicked(itemAtPosition)
        })

    }

    override fun submitList(list: List<Task>?) {
        super.submitList(list)
    }

    override fun getItemCount(): Int = currentList.size

    override fun onBindViewHolder(holder: TaskVH, position: Int) {
        val itemAtPosition = currentList[position]
        holder.bind(itemAtPosition)
    }


    inner class TaskVH(
        private val binding: SingleTaskBinding,
        onItemClick: (position: Int) -> Unit
    ) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                onItemClick(adapterPosition)
            }
        }


        fun bind(task: Task) {

            with(binding) {
                title.text = task.title

                checkbox.setOnClickListener {
                    onItemChecked.invoke(task)
                }

                when (task.priority) {
                    "Urgent" -> {
                        priorityBar.setBackgroundColor(Color.RED)
                    }

                    "Medium" -> {
                        priorityBar.setBackgroundColor(Color.GREEN)
                    }

                    else -> {
                        priorityBar.setBackgroundColor(Color.BLACK)
                    }
                }
            }
        }

    }

}


/*
 * Complete Adapter
 */