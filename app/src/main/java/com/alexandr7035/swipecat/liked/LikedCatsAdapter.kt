package com.alexandr7035.swipecat.liked

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alexandr7035.swipecat.cats.CardsAdapter
import com.alexandr7035.swipecat.data.local.CatEntity
import com.alexandr7035.swipecat.data.remote.CatRemote
import com.alexandr7035.swipecat.databinding.ViewCatCardBinding
import com.alexandr7035.swipecat.databinding.ViewLikedCatCardBinding
import com.squareup.picasso.MemoryPolicy
import com.squareup.picasso.NetworkPolicy
import com.squareup.picasso.Picasso
import timber.log.Timber

class LikedCatsAdapter : RecyclerView.Adapter<LikedCatsAdapter.ViewHolder>() {

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
        Timber.tag("RECYCLER LIKED").d("onBInd called ${items[position]}")

        Picasso.get()
            .load(items[position].url)
            .memoryPolicy(MemoryPolicy.NO_CACHE)
            .networkPolicy(NetworkPolicy.NO_STORE)
            .into(holder.binding.image)
    }

    class ViewHolder(val binding: ViewLikedCatCardBinding): RecyclerView.ViewHolder(binding.root)

}
