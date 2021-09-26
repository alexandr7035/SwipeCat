package com.alexandr7035.swipecat.cats

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alexandr7035.swipecat.R
import com.alexandr7035.swipecat.data.remote.CatRemote
import com.alexandr7035.swipecat.databinding.ViewCatCardBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import timber.log.Timber

class CardsAdapter: RecyclerView.Adapter<CardsAdapter.ViewHolder>() {

    private var items: List<CatRemote> = emptyList()

    fun setItems(items: List<CatRemote>) {
        this.items = items
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ViewCatCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        Timber.tag("RECYCLER").d("onBInd called ${items[position]}")
        val context = (holder.binding.root as View).context

        Glide.with(context)
            .load(items[position].url)
            .placeholder(R.drawable.background_image_placeholder)
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .into(holder.binding.image)
    }

    class ViewHolder(val binding: ViewCatCardBinding): RecyclerView.ViewHolder(binding.root)

}