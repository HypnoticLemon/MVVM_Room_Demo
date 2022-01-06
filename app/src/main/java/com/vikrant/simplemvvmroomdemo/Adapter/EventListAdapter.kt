package com.vikrant.simplemvvmroomdemo.Adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.vikrant.simplemvvmroomdemo.Data.Festival
import com.vikrant.simplemvvmroomdemo.R

class EventListAdapter(private var list: List<Festival>) :
    RecyclerView.Adapter<EventListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(inflater, parent)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val event: Festival = list[position]
        holder.bind(event)
    }

    class ViewHolder(inflater: LayoutInflater, parent: ViewGroup) : RecyclerView.ViewHolder(
        inflater.inflate(
            R.layout.row_event,
            parent,
            false
        )
    ) {

        private var tvEventDate: TextView? = null
        private var tvEventTithi: TextView? = null
        private var tvEventName: TextView? = null
        private var tvEventFullDate: TextView? = null
        private var tvEventDay: TextView? = null

        init {
            tvEventDate = itemView.findViewById(R.id.tvEventDate)
            tvEventName = itemView.findViewById(R.id.tvEventName)
            tvEventTithi = itemView.findViewById(R.id.tvEventTithi)
            tvEventFullDate = itemView.findViewById(R.id.tvEventFullDate)
            tvEventDay = itemView.findViewById(R.id.tvEventDay)
        }

        @SuppressLint("SetTextI18n")
        fun bind(event: Festival) {
            tvEventName?.text = event.description
            tvEventFullDate?.text = event.tithiDate
            tvEventDate?.text = event.tithiDD.toString()
            tvEventDay?.text = "-   "+event.tithiDay
        }
    }

}