package com.sameh.todoapp.fragments.list.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.sameh.todoapp.R
import com.sameh.todoapp.data.models.Priority
import com.sameh.todoapp.data.models.ToDoData
import com.sameh.todoapp.fragments.list.ListFragmentDirections

class ToDoRecyclerView : RecyclerView.Adapter<ToDoRecyclerView.ToDoViewHolder>() {

    var toDoDataList = emptyList<ToDoData>()

    private var toDoDiffUtil: ToDoDiffUtil? = null

    fun setToDoList(toDoList: List<ToDoData>) {
        toDoDiffUtil = ToDoDiffUtil(toDoDataList, toDoList)
        val toDoDiffResult = DiffUtil.calculateDiff(toDoDiffUtil!!)
        this.toDoDataList = toDoList
        toDoDiffResult.dispatchUpdatesTo(this)
    }

    class ToDoViewHolder(itemView: View) : ViewHolder(itemView) {
        private val title: TextView = itemView.findViewById(R.id.tv_item_title)
        private val description: TextView = itemView.findViewById(R.id.tv_item_description)
        private val priority: CardView = itemView.findViewById(R.id.card_view_priority_indicator)
        private val itemBackground: ConstraintLayout = itemView.findViewById(R.id.constraint_layout_item_container)

        fun bind(toDoData: ToDoData) {
            title.text = toDoData.title
            description.text = toDoData.description

            // to go to update fragment
            itemBackground.setOnClickListener {
                val action = ListFragmentDirections.actionListFragmentToUpdateFragment(toDoData)
                itemView.findNavController().navigate(action)
            }

            when (toDoData.priority) {
                Priority.HIGH -> priority.setCardBackgroundColor(
                    ContextCompat.getColor(
                        itemView.context,
                        R.color.red
                    )
                )
                Priority.MEDIUM -> priority.setCardBackgroundColor(
                    ContextCompat.getColor(
                        itemView.context,
                        R.color.yellow
                    )
                )
                Priority.LOW -> priority.setCardBackgroundColor(
                    ContextCompat.getColor(
                        itemView.context,
                        R.color.green
                    )
                )
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToDoViewHolder {
        return ToDoViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_on_recycler_view, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ToDoViewHolder, position: Int) {
        val toDoData: ToDoData = toDoDataList[position]
        holder.bind(toDoData)
    }

    override fun getItemCount(): Int {
        return toDoDataList.size
    }
}