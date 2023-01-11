package com.example.cryptotracker.adapter

import android.graphics.Color
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.cryptotracker.databinding.SingleRowSearchBinding
import com.example.cryptotracker.model.search.Data

class SearchAdapter : ListAdapter<Data, SearchAdapter.ViewHolder>(COMPARATOR) {

    object COMPARATOR : DiffUtil.ItemCallback<Data>() {
        override fun areItemsTheSame(oldItem: Data, newItem: Data): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Data, newItem: Data): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            SingleRowSearchBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        item?.let {
            holder.Bind(item)
        }

    }

    class ViewHolder(val binding: SingleRowSearchBinding) : RecyclerView.ViewHolder(binding.root) {

        fun Bind(result: Data) {
            binding.data = result
            binding.searchChanged24hrs.text =
                currencyChange("24h", result.changePercent24Hr.toString())
            binding.executePendingBindings()
        }

        val fcsRed = ForegroundColorSpan(Color.RED)
        val fcsGreen = ForegroundColorSpan(Color.parseColor("#73C580"))

        fun currencyChange(prefix: String, input: String): SpannableStringBuilder {
            val value = input.toDouble()
            val text = "$prefix: $value"
            val sb = SpannableStringBuilder(text)
            sb.setSpan(
                if (value > 0) fcsGreen else fcsRed,
                text.indexOf(" ") + 1,
                text.lastIndex + 1,
                Spannable.SPAN_INCLUSIVE_INCLUSIVE
            )
            return sb
        }


    }
}