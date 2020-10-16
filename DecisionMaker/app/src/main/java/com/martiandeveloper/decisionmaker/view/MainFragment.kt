@file:Suppress("unused")

package com.martiandeveloper.decisionmaker.view

import android.app.AlertDialog
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
import com.martiandeveloper.decisionmaker.R
import com.martiandeveloper.decisionmaker.databinding.DialogOptionBinding
import com.martiandeveloper.decisionmaker.databinding.FragmentMainBinding
import com.martiandeveloper.decisionmaker.viewmodel.MainViewModel
import timber.log.Timber


class MainFragment : Fragment() {

    private lateinit var fragmentMainBinding: FragmentMainBinding

    private lateinit var mainViewModel: MainViewModel

    private lateinit var optionDialog: AlertDialog

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

        observe()

        optionDialog = AlertDialog.Builder(context).create()
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

    private fun observe() {

        mainViewModel.eventOptionMBTNClick.observe(viewLifecycleOwner, {
            if (it) {
                if (!optionDialog.isShowing) {
                    mainViewModel.setIsOptionDialogShowing(true)
                }
                mainViewModel.onOptionMBTNClickComplete()
            }
        })

        mainViewModel.eventDecideMBTNClick.observe(viewLifecycleOwner, {
            if (it) {
                Timber.i("Decide")
                mainViewModel.onDecideMBTNClickComplete()
            }
        })

        mainViewModel.eventCancelMBTNClick.observe(viewLifecycleOwner, {
            if (it) {
                if (optionDialog.isShowing) {
                    mainViewModel.setIsOptionDialogShowing(false)
                }
                mainViewModel.onCancelMBTNClickComplete()
            }
        })

        mainViewModel.eventDoneMBTNClick.observe(viewLifecycleOwner, {
            if (it) {
                if (optionDialog.isShowing) {
                    mainViewModel.setIsOptionDialogShowing(false)
                }

                val text = mainViewModel.optionsMTV.value

                if (text != null) {
                    mainViewModel.setOptionsMTVText("$text\n${mainViewModel.optionET.value}")
                } else {
                    mainViewModel.setOptionsMTVText("${mainViewModel.optionET.value}")
                }

                mainViewModel.onDoneMBTNClickComplete()
            }
        })

        mainViewModel.isOptionDialogShowing.observe(viewLifecycleOwner, {
            if (it) {
                showOptionDialog()
            } else {
                optionDialog.dismiss()
            }
        })

    }

    private fun showOptionDialog() {
        val binding = DialogOptionBinding.inflate(LayoutInflater.from(context))

        binding.mainViewModel = mainViewModel
        binding.lifecycleOwner = this

        binding.dialogOptionOptionET.requestFocus()

        optionDialog.setView(binding.root)
        optionDialog.setCanceledOnTouchOutside(false)
        optionDialog.setCancelable(false)
        optionDialog.show()
    }

}
