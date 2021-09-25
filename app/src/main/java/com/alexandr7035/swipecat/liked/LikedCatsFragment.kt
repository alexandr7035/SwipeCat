package com.alexandr7035.swipecat.liked

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.alexandr7035.swipecat.R
import com.alexandr7035.swipecat.cats.CatsFragment
import com.alexandr7035.swipecat.cats.CatsViewModel
import com.alexandr7035.swipecat.data.local.CatEntity
import com.alexandr7035.swipecat.databinding.FragmentLikedCatsBinding
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class LikedCatsFragment: Fragment(), LikedCatsAdapter.RecyclerDeleteItemClickListener {

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
            requireActivity().supportFragmentManager.popBackStack()
        }

        binding?.toolbar?.setOnMenuItemClickListener { item ->

            when (item.itemId) {
                R.id.item_linear_view -> {
                    setupLinearRecyclerMode()
                    true
                }

                R.id.item_grid_view -> {
                    setupGridRecyclerMode()
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


    private fun setupLinearRecyclerMode() {
        binding?.recycler?.adapter = adapter
        binding?.recycler?.layoutManager = LinearLayoutManager(requireContext())

        // Change item
        binding?.toolbar?.menu?.findItem(R.id.item_linear_view)?.isVisible = false
        binding?.toolbar?.menu?.findItem(R.id.item_grid_view)?.isVisible = true

        viewModel.saveLikedRecyclerMode(getString(R.string.shared_pref_likes_recycler_mode_linear))
    }

    private fun setupGridRecyclerMode() {
        binding?.recycler?.adapter = adapter
        binding?.recycler?.layoutManager = GridLayoutManager(requireContext(), 2)

        // Change item
        binding?.toolbar?.menu?.findItem(R.id.item_linear_view)?.isVisible = true
        binding?.toolbar?.menu?.findItem(R.id.item_grid_view)?.isVisible = false

        viewModel.saveLikedRecyclerMode(getString(R.string.shared_pref_likes_recycler_mode_grid))
    }

    override fun deleteItemClicked(cat: CatEntity) {
        Timber.tag("RECYCLER").d("delete item ${cat.id}")

        viewModel.removeLikedCat(cat)
    }

    override fun onDestroyView() {
        super.onDestroyView()

        binding = null
    }

    companion object {
        fun newInstance() = LikedCatsFragment()
    }

}