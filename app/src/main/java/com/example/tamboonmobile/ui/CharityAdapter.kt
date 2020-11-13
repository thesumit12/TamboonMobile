package com.example.tamboonmobile.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.tamboonmobile.R
import com.example.tamboonmobile.databinding.CharityItemBinding
import com.example.tamboonmobile.model.Charity

class CharityAdapter(private val context: Context):
    RecyclerView.Adapter<CharityAdapter.CharityViewHolder>() {

    private val list: ArrayList<Charity> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharityViewHolder {
        val binding: CharityItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(context), R.layout.charity_item, parent, false
        )

        return CharityViewHolder(binding, context)
    }

    override fun onBindViewHolder(holder: CharityViewHolder, position: Int) =
        holder.bind(list[position])

    override fun getItemCount(): Int = list.size

    fun setList(data: List<Charity>) {
        list.clear()
        list.addAll(data)
        notifyDataSetChanged()
    }

    inner class CharityViewHolder(private val binding: CharityItemBinding, private val context: Context):
        RecyclerView.ViewHolder(binding.root) {

        fun bind(charity: Charity?) {
            binding.charityItem = charity
            binding.layoutCharity.setOnClickListener {

            }
        }
    }
}