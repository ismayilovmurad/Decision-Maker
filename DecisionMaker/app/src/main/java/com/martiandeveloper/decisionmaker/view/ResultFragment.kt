package com.martiandeveloper.decisionmaker.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavDirections
import androidx.navigation.Navigation
import com.martiandeveloper.decisionmaker.R
import com.martiandeveloper.decisionmaker.databinding.FragmentResultBinding
import com.martiandeveloper.decisionmaker.viewmodel.ResultViewModel

class ResultFragment : Fragment() {

    private lateinit var fragmentResultBinding: FragmentResultBinding

    private lateinit var resultViewModel: ResultViewModel

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
    }

    private fun getViewModel(): ResultViewModel {

        return ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return ResultViewModel() as T
            }
        })[ResultViewModel::class.java]

    }

    private fun navigate(navDirections: NavDirections) {
        view?.let { Navigation.findNavController(it).navigate(navDirections) }
    }

}
