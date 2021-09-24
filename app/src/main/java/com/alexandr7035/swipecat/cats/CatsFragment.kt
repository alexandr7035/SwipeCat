package com.alexandr7035.swipecat.cats

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import com.alexandr7035.swipecat.R
import com.alexandr7035.swipecat.app_core.Cat
import com.alexandr7035.swipecat.databinding.FragmentCatsBinding
import com.yuyakaido.android.cardstackview.*
import timber.log.Timber

class CatsFragment : Fragment(), CardStackListener {

    private val defaultCardsNumber = 10

    private val viewModel by viewModels<CatsViewModel>()
    private var binding: FragmentCatsBinding? = null

    private lateinit var manager: CardStackLayoutManager
    private lateinit var cardsAdapter: CardsAdapter
    private lateinit var catsLiveData: LiveData<List<Cat>>

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
        })

        viewModel.getLikedCatsLiveData().observe(viewLifecycleOwner, {
            binding?.likesCountView?.text = it.size.toString()
        })

        binding?.refreshButton?.setOnClickListener {
            viewModel.fetchCats(defaultCardsNumber)
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
            setSwipeableMethod(SwipeableMethod.Manual)
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