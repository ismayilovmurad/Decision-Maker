@file:Suppress("unused")

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
import com.martiandeveloper.decisionmaker.databinding.FragmentMainBinding
import com.martiandeveloper.decisionmaker.viewmodel.MainViewModel

class MainFragment : Fragment() {

    private lateinit var fragmentMainBinding: FragmentMainBinding

    private lateinit var mainViewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentMainBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false)
        return fragmentMainBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
    }

    private fun initUI() {
        mainViewModel = getViewModel()

        fragmentMainBinding.mainViewModel = mainViewModel
        fragmentMainBinding.lifecycleOwner = this
    }

    private fun getViewModel(): MainViewModel {

        return ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return MainViewModel() as T
            }
        })[MainViewModel::class.java]

    }

    private fun navigate(navDirections: NavDirections) {
        view?.let { Navigation.findNavController(it).navigate(navDirections) }
    }

}
