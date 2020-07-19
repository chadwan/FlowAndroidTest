package com.ccwo.flowaccounttest.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ccwo.flowaccounttest.R
import com.ccwo.flowaccounttest.model.RepoModel
import kotlinx.android.synthetic.main.adapter_repo.view.*

class RepoViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView) {

    companion object {
        fun create(parent: ViewGroup): RepoViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val view = layoutInflater.inflate(R.layout.adapter_repo, parent, false)
            return RepoViewHolder(view)
        }
    }

    fun bindTo(item : RepoModel?) {
        itemView.txvName.text = item?.name
        itemView.txvFullName.text = item?.fullName
        Glide.with(itemView.context).load(item?.owner?.avatarUrl).centerCrop().placeholder(R.drawable.gif_loading).into(itemView.imvAvatar)
    }
}