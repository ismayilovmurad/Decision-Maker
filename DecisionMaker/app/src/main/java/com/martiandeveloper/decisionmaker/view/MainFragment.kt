@file:Suppress("unused")

package com.martiandeveloper.decisionmaker.view

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavDirections
import androidx.navigation.Navigation
import com.martiandeveloper.decisionmaker.R
import com.martiandeveloper.decisionmaker.databinding.DialogLoadingBinding
import com.martiandeveloper.decisionmaker.databinding.DialogOptionBinding
import com.martiandeveloper.decisionmaker.databinding.FragmentMainBinding
import com.martiandeveloper.decisionmaker.viewmodel.MainViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random


class MainFragment : Fragment() {

    private lateinit var fragmentMainBinding: FragmentMainBinding

    private lateinit var mainViewModel: MainViewModel

    private lateinit var optionDialog: AlertDialog

    private lateinit var loadingDialog: AlertDialog

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

        loadingDialog = AlertDialog.Builder(context).create()

        view?.let { mainViewModel.setView(it) }
    }

    private fun getViewModel(): MainViewModel {

        return ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return MainViewModel() as T
            }
        })[MainViewModel::class.java]

    }

    private suspend fun navigate(navDirections: NavDirections) {
        delay((4000))
        if (loadingDialog.isShowing) {
            mainViewModel.setIsLoadingDialogShowing(false)
        }
        mainViewModel.view.value?.let { Navigation.findNavController(it).navigate(navDirections) }
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

                if (mainViewModel.options.value!!.size >= 2) {
                    val decide = Random.nextInt(0, mainViewModel.options.value!!.size)
                    if (!loadingDialog.isShowing) {
                        mainViewModel.setIsLoadingDialogShowing(true)
                    }
                    CoroutineScope(Dispatchers.Main).launch {
                        navigate(
                            MainFragmentDirections.actionMainFragmentToResultFragment(
                                mainViewModel.options.value!![decide]
                            )
                        )
                    }
                } else {
                    Toast.makeText(
                        context,
                        getString(R.string.please_enter_at_least_2_options),
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }

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

                val text = mainViewModel.optionsMTV.value

                val option = mainViewModel.optionET.value

                if (option.isNullOrEmpty()) {
                    Toast.makeText(
                        context,
                        getString(R.string.please_enter_an_option),
                        Toast.LENGTH_SHORT
                    ).show()
                } else {

                    mainViewModel.addOption(option)

                    if (text != null) {
                        mainViewModel.setOptionsMTVText("$text\n\n$option")
                    } else {
                        mainViewModel.setOptionsMTVText("$option")
                    }

                    mainViewModel.resetOptionET()

                    if (optionDialog.isShowing) {
                        mainViewModel.setIsOptionDialogShowing(false)
                    }

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

        mainViewModel.isLoadingDialogShowing.observe(viewLifecycleOwner, {
            if (it) {
                showLoadingDialog()
            } else {
                loadingDialog.dismiss()
            }
        })

    }

    private fun showOptionDialog() {
        val dialogOptionBinding = DialogOptionBinding.inflate(LayoutInflater.from(context))

        dialogOptionBinding.mainViewModel = mainViewModel
        dialogOptionBinding.lifecycleOwner = this

        dialogOptionBinding.dialogOptionOptionET.requestFocus()

        optionDialog.setView(dialogOptionBinding.root)
        optionDialog.setCanceledOnTouchOutside(false)
        optionDialog.setCancelable(false)
        optionDialog.show()
    }

    private fun showLoadingDialog() {
        val dialogLoadingBinding = DialogLoadingBinding.inflate(LayoutInflater.from(context))

        loadingDialog.setView(dialogLoadingBinding.root)
        loadingDialog.setCanceledOnTouchOutside(false)
        loadingDialog.setCancelable(false)
        loadingDialog.show()
    }

}
