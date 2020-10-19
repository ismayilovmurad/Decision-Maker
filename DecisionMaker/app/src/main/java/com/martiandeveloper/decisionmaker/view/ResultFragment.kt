package com.martiandeveloper.decisionmaker.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavDirections
import androidx.navigation.Navigation
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.InterstitialAd
import com.martiandeveloper.decisionmaker.R
import com.martiandeveloper.decisionmaker.databinding.FragmentResultBinding
import com.martiandeveloper.decisionmaker.viewmodel.ResultViewModel

class ResultFragment : Fragment() {

    private lateinit var fragmentResultBinding: FragmentResultBinding

    private lateinit var resultViewModel: ResultViewModel

    private lateinit var interstitialAd: InterstitialAd

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentResultBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_result, container, false)
        return fragmentResultBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
    }

    private fun initUI() {
        resultViewModel = getViewModel()

        fragmentResultBinding.resultViewModel = resultViewModel
        fragmentResultBinding.lifecycleOwner = this

        resultViewModel.setDecide("${arguments?.getString("decide")}")

        observe()

        interstitialAd = InterstitialAd(context)

        setAds()
    }

    private fun getViewModel(): ResultViewModel {

        return ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return ResultViewModel() as T
            }
        })[ResultViewModel::class.java]

    }

    private fun observe() {
        resultViewModel.eventTryAgainMBTNClick.observe(viewLifecycleOwner, {
            if (it) {

                navigate(ResultFragmentDirections.actionResultFragmentToMainFragment())

                if (interstitialAd.isLoaded) {
                    interstitialAd.show()
                }

                resultViewModel.onTryAgainMBTNClickComplete()

            }
        })
    }

    private fun navigate(navDirections: NavDirections) {
        view?.let { Navigation.findNavController(it).navigate(navDirections) }
    }

    private fun setAds() {
        interstitialAd.adUnitId = getString(R.string.main_interstitial)

        val interstitialAdRequest = AdRequest.Builder().build()
        interstitialAd.loadAd(interstitialAdRequest)

        interstitialAd.adListener = object : AdListener() {
            override fun onAdClosed() {
                super.onAdClosed()
                interstitialAd.loadAd(interstitialAdRequest)
            }
        }

    }

}
