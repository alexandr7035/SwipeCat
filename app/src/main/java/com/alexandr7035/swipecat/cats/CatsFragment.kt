package com.alexandr7035.swipecat.cats

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.LinearInterpolator
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DefaultItemAnimator
import com.alexandr7035.swipecat.data.CatEntity
import com.alexandr7035.swipecat.databinding.FragmentCatsBinding
import com.yuyakaido.android.cardstackview.CardStackLayoutManager
import com.yuyakaido.android.cardstackview.Direction
import com.yuyakaido.android.cardstackview.StackFrom
import com.yuyakaido.android.cardstackview.SwipeableMethod
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import timber.log.Timber
import java.net.URI

class CatsFragment : Fragment() {

    private val viewModel by viewModels<CatsViewModel>()
    private var binding: FragmentCatsBinding? = null

    private lateinit var manager: CardStackLayoutManager

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentCatsBinding.inflate(inflater, container, false)
        return  binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = CardsAdapter()

        adapter.setItems(viewModel.getCats(10))


        manager = CardStackLayoutManager(requireContext())

        binding?.cardsView?.adapter = adapter

        manager.setStackFrom(StackFrom.TopAndLeft)
        manager.setVisibleCount(3)
        manager.setTranslationInterval(0.0f)
//        manager.setScaleInterval(0.95f)
//        manager.setSwipeThreshold(0.3f)
        manager.setMaxDegree(0f)
        manager.setDirections(Direction.HORIZONTAL)
        manager.setCanScrollHorizontal(true)
        manager.setCanScrollVertical(false)
        manager.setSwipeableMethod(SwipeableMethod.AutomaticAndManual)
        manager.setOverlayInterpolator(LinearInterpolator())
        binding!!.cardsView.layoutManager = manager
        binding!!.cardsView.adapter = adapter
        binding!!.cardsView.itemAnimator.apply {
            if (this is DefaultItemAnimator) {
                supportsChangeAnimations = false
            }
        }

    }


    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    companion object {
        fun newInstance() = CatsFragment()
    }

}