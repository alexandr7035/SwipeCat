package com.alexandr7035.swipecat.liked

import android.graphics.BitmapFactory
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.alexandr7035.swipecat.R
import com.alexandr7035.swipecat.data.local.CatEntity
import com.alexandr7035.swipecat.databinding.ViewLikedCatCardBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import java.io.FileNotFoundException

class LikedCatsAdapter(private val deleteItemClickListener: RecyclerDeleteItemClickListener) : RecyclerView.Adapter<LikedCatsAdapter.ViewHolder>() {

    private var items: List<CatEntity> = emptyList()

    fun setItems(items: List<CatEntity>) {
        this.items = items
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ViewLikedCatCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        // Get context of item view
        val context = (holder.binding.root.rootView as View).context

        Glide.with(context)
            .load(items[position].url)
            .placeholder(R.drawable.background_image_placeholder)
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .into(holder.binding.image)

    }

    inner class ViewHolder(val binding: ViewLikedCatCardBinding): RecyclerView.ViewHolder(binding.root), View.OnClickListener {

        init {
            binding.deleteButton.setOnClickListener(this)
        }

        override fun onClick(v: View) {
            deleteItemClickListener.deleteItemClicked(items[adapterPosition])
            notifyItemRemoved(adapterPosition)
        }

    }

    interface RecyclerDeleteItemClickListener {
        fun deleteItemClicked(cat: CatEntity)
    }

}
