package com.nos.tikihometest.tikihometest

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import org.w3c.dom.Text
import java.util.*
import kotlin.collections.ArrayList

class KeywordAdapter(private val context: Context?,private var mDataset: ArrayList<String> = ArrayList()) : RecyclerView.Adapter<KeywordAdapter.VH>() {

    class VH(val k_view: TwoLinesTextView) : RecyclerView.ViewHolder(k_view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val textview = LayoutInflater.from(parent.context)
                .inflate(R.layout.keyword_itemview, parent, false) as TwoLinesTextView
        return VH(textview)
    }

    override fun getItemCount(): Int {
        return mDataset.size
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.k_view.text = mDataset[position]
        holder.k_view.background=BackgroundPatternDrawable(context)
    }



    fun setDataset(dataSet: ArrayList<String>) {
        mDataset = ArrayList(dataSet)
        notifyDataSetChanged()
    }

}
