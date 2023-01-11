package com.example.cryptotracker.adapter

import android.graphics.Color
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.TextUtils
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.cryptotracker.R
import com.example.cryptotracker.databinding.SingleRowBinding
import com.example.cryptotracker.model.Data

class CryptoAdapter : ListAdapter<Data, CryptoAdapter.ViewHolder>(COMPARATOR) {


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
            SingleRowBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val dataItem = getItem(position)
        dataItem?.let {
            holder.Bind(dataItem)
        }

        holder.binding.addToFav.setOnClickListener {
            holder.binding.addToFav.setImageResource(R.drawable.ic_fav_filled)
            onItemClickListener?.let {
                it(dataItem)
            }
        }

    }

    override fun getItemCount(): Int {
        return currentList.size
    }


    private var onItemClickListener: ((Data) -> Unit)? = null

    fun setOnItemClickListener(listener: (Data) -> Unit) {
        onItemClickListener = listener
    }


    class ViewHolder(val binding: SingleRowBinding) : RecyclerView.ViewHolder(binding.root) {

        fun Bind(data: Data) {
            binding.response = data
            binding.changed24hrs.text = currencyChange("24h", data.changePercent24Hr.toString())
            binding.executePendingBindings()
        }

        val fcsRed = ForegroundColorSpan(Color.RED)
        val fcsGreen = ForegroundColorSpan(Color.parseColor("#73C580"))
        val fcsBlack = ForegroundColorSpan(Color.parseColor("#000000"))

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