package com.alexandr7035.swipecat.ui.liked_cats

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.alexandr7035.swipecat.BuildConfig
import com.alexandr7035.swipecat.ui.MainActivity
import com.alexandr7035.swipecat.R
import com.alexandr7035.swipecat.ui.CatsViewModel
import com.alexandr7035.swipecat.data.local.CatEntity
import com.alexandr7035.swipecat.databinding.FragmentLikedCatsBinding
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import java.io.File

@AndroidEntryPoint
class LikedCatsFragment: Fragment(), LikedCatsAdapter.RecyclerItemClickListener {

    // FIXME viewmodel
    private val viewModel by viewModels<CatsViewModel>()
    private var binding: FragmentLikedCatsBinding? = null

    private lateinit var adapter: LikedCatsAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentLikedCatsBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = LikedCatsAdapter(this)
        binding?.recycler?.layoutManager = GridLayoutManager(requireContext(), 2)
        binding?.recycler?.adapter = adapter

        viewModel.getLikedCatsLiveData().observe(viewLifecycleOwner, { likedCatsList ->
            adapter.setItems(likedCatsList)
        })

        binding?.toolbar?.inflateMenu(R.menu.menu_liked_cats_toolbar)

        binding?.toolbar?.setNavigationOnClickListener {
            (requireActivity() as MainActivity).getNavigation().navigateBack()
        }

        binding?.toolbar?.setOnMenuItemClickListener { item ->

            // Do not use findFirstCompletelyVisibleItemPosition()
            // as none of pictures might be fully visible in linear mode
            // because of its size
            when (item.itemId) {
                R.id.item_linear_view -> {
                    // Get position in current layoutmanager
                    val position = (binding?.recycler?.layoutManager as GridLayoutManager).findFirstVisibleItemPosition()

                    setupLinearRecyclerMode(position)
                    true
                }

                R.id.item_grid_view -> {
                    // Get position in current layoutmanager
                    val position = (binding?.recycler?.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()

                    setupGridRecyclerMode(position)
                    true
                }

                else -> false
            }
        }

        
        Timber.tag("RECYCLER").d("mode ${viewModel.getLikedRecyclerMode()}")
        when (viewModel.getLikedRecyclerMode()) {
            getString(R.string.shared_pref_likes_recycler_mode_grid) -> {
                setupGridRecyclerMode()
            }

            getString(R.string.shared_pref_likes_recycler_mode_linear) -> {
                setupLinearRecyclerMode()
            }
        }
    }


    private fun setupLinearRecyclerMode(position: Int = 0) {
        binding?.recycler?.adapter = adapter
        binding?.recycler?.layoutManager = LinearLayoutManager(requireContext())

        // Change item
        binding?.toolbar?.menu?.findItem(R.id.item_linear_view)?.isVisible = false
        binding?.toolbar?.menu?.findItem(R.id.item_grid_view)?.isVisible = true

        binding?.recycler?.scrollToPosition(position)

        viewModel.saveLikedRecyclerMode(getString(R.string.shared_pref_likes_recycler_mode_linear))
    }

    private fun setupGridRecyclerMode(position: Int = 0) {
        binding?.recycler?.adapter = adapter
        binding?.recycler?.layoutManager = GridLayoutManager(requireContext(), 2)

        // Change item
        binding?.toolbar?.menu?.findItem(R.id.item_linear_view)?.isVisible = true
        binding?.toolbar?.menu?.findItem(R.id.item_grid_view)?.isVisible = false

        binding?.recycler?.scrollToPosition(position)

        viewModel.saveLikedRecyclerMode(getString(R.string.shared_pref_likes_recycler_mode_grid))
    }

    // Delete liked cat
    override fun deleteItemClicked(cat: CatEntity) {
        Timber.tag("RECYCLER").d("delete item ${cat.id}")

        viewModel.removeLikedCat(cat)
    }

    // Share action
    override fun itemImageClicked(cat: CatEntity) {
        Timber.tag("SHARE").d("image ${cat.imageLocalUri}")

        // We convert file uri to content uri to make it work
        val file = File(Uri.parse(cat.imageLocalUri).path!!)
        val uri = FileProvider.getUriForFile(requireContext(), BuildConfig.APPLICATION_ID + ".provider", file)

        Timber.tag("SHARE").d("image $uri")

        // Implicit intent
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "image/*"

        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT)

        intent.putExtra(Intent.EXTRA_STREAM, uri)

        intent.putExtra(Intent.EXTRA_TEXT, getString(R.string.share_image_text, BuildConfig.VERSION_NAME))
        startActivity(Intent.createChooser(intent, getString(R.string.share_dialog_text)))
    }

    override fun onDestroyView() {
        super.onDestroyView()

        binding = null
    }

    companion object {
        fun newInstance() = LikedCatsFragment()
    }

}