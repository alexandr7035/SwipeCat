package com.alexandr7035.swipecat.cats

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import com.alexandr7035.swipecat.MainActivity
import com.alexandr7035.swipecat.R
import com.alexandr7035.swipecat.data.remote.CatRemote
import com.alexandr7035.swipecat.databinding.FragmentCatsBinding
import com.alexandr7035.swipecat.liked.LikedCatsFragment
import com.yuyakaido.android.cardstackview.*
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class CatsFragment : Fragment(), CardStackListener {

    private val defaultCardsNumber = 50

    private val viewModel by viewModels<CatsViewModel>()
    private var binding: FragmentCatsBinding? = null

    private lateinit var manager: CardStackLayoutManager
    private lateinit var cardsAdapter: CardsAdapter
    private lateinit var catsLiveData: LiveData<List<CatRemote>>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentCatsBinding.inflate(inflater, container, false)
        return  binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Core for cards view
        cardsAdapter = CardsAdapter()
        manager = CardStackLayoutManager(requireContext(), this)
        setupCardStackView()

        catsLiveData = viewModel.getCatsLiveData()
        catsLiveData.observe(viewLifecycleOwner, { catsList ->
            Timber.tag("CATS_DATA").d("updated cats $catsList")
            cardsAdapter.setItems(catsList)
            binding?.cardsCountView?.text = getString(R.string.cards_number, 0, catsList.size)

            // Make action buttons visible if were hidden
            binding?.likeButton?.isEnabled = true
            binding?.skipButton?.isEnabled = true
        })

        viewModel.getLikedCatsLiveData().observe(viewLifecycleOwner, {
            binding?.likesCountView?.text = it.size.toString()
        })


        // Action buttons
        binding?.refreshButton?.setOnClickListener {
            viewModel.fetchCats(defaultCardsNumber)
        }

        binding?.likeButton?.setOnClickListener {
            Timber.tag("SWIPE").d("manual SWIPE RIGHT - like")
            val setting = SwipeAnimationSetting.Builder()
                .setDirection(Direction.Right)
                .setDuration(Duration.Normal.duration)
                .build()
            manager.setSwipeAnimationSetting(setting)
            binding?.cardsView?.swipe()
        }

        binding?.skipButton?.setOnClickListener {
            Timber.tag("SWIPE").d("manual SWIPE LEFT - skip")
            val setting = SwipeAnimationSetting.Builder()
                .setDirection(Direction.Left)
                .setDuration(Duration.Normal.duration)
                .build()
            manager.setSwipeAnimationSetting(setting)
            binding?.cardsView?.swipe()
        }

        binding?.likesButton?.setOnClickListener {
            (requireActivity() as MainActivity).getNavigation().add(
                fragment = LikedCatsFragment.newInstance(),
                allowGoBack = true
            )
        }

        // Fetch on start
        viewModel.fetchCats(defaultCardsNumber)
    }

    private fun setupCardStackView() {

        Timber.d("Adapter is $cardsAdapter")

        manager.apply {

            setStackFrom(StackFrom.None)
            manager.setVisibleCount(3)

            // Disable rotation
            setMaxDegree(0f)

            // Allow only horizontal swipes
            // FIXME that's for correct card sizing and wrapping
            // FIXME find better solution later
            manager.setDirections(Direction.HORIZONTAL)
            manager.setCanScrollHorizontal(true)
            manager.setCanScrollVertical(false)

            // TODO add automatic with delay later
            setSwipeableMethod(SwipeableMethod.AutomaticAndManual)
        }

        binding?.cardsView?.apply {
            layoutManager = manager
            adapter = cardsAdapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    companion object {
        fun newInstance() = CatsFragment()
    }


    // Cards callbacks
    override fun onCardDragging(direction: Direction?, ratio: Float) {

    }

    override fun onCardSwiped(direction: Direction) {
        Timber.tag("SWIPE").d("card swiped to ${manager.topPosition}")

        if (direction == Direction.Right) {
            // Add previous card to favourites
            viewModel.likeCat(catsLiveData.value!![manager.topPosition-1])
        }

        // Disable action buttons when swiped all cards
        val totalCards = catsLiveData.value!!.size

        if (manager.topPosition == totalCards) {
            Timber.tag("SWIPE").d("swiped last card")

            binding?.likeButton?.isEnabled = false
            binding?.skipButton?.isEnabled = false
        }
    }

    override fun onCardRewound() {

    }

    override fun onCardCanceled() {

    }

    override fun onCardAppeared(view: View?, position: Int) {
        val totalCards = catsLiveData.value?.size

        if (totalCards != null) {
            // +1 as numeration starts from 0
            updateCardsCounter(position + 1, totalCards)
        }
    }

    override fun onCardDisappeared(view: View?, position: Int) {
        val totalCards = catsLiveData.value?.size

        if (totalCards != null) {
            // When the cards ran out
            if (position + 1 == totalCards) {
                updateCardsCounter(0, 0)
            }
        }
    }

    private fun updateCardsCounter(currentCard: Int, totalCards: Int) {
        binding?.cardsCountView?.text = getString(R.string.cards_number, currentCard, totalCards)
    }

}