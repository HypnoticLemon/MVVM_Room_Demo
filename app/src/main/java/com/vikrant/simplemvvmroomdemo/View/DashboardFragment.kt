package com.vikrant.simplemvvmroomdemo.View

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.util.Log
import android.view.View
import com.vikrant.simplemvvmroomdemo.Adapter.EventListAdapter
import com.vikrant.simplemvvmroomdemo.Common.BaseFragment
import com.vikrant.simplemvvmroomdemo.Data.Festival
import com.vikrant.simplemvvmroomdemo.R
import com.vikrant.simplemvvmroomdemo.Utils.NetworkStateHelper
import com.vikrant.simplemvvmroomdemo.Utils.Status
import com.vikrant.simplemvvmroomdemo.databinding.FragmentDashboardBinding
import com.vikrant.simplemvvmroomdemo.model.EventResponseModel
import com.vikrant.simplemvvmroomdemo.viewModel.DashboardViewModel
import kotlinx.android.synthetic.main.fragment_dashboard.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*


class DashboardFragment : BaseFragment<FragmentDashboardBinding, DashboardViewModel>() {
    private lateinit var mAdapter: EventListAdapter
    private var lastMonthOfCurrentList: Int = 0
    private var lastYearOfCurrentList: Int = 0

    override fun onLayoutInflate(): Int = R.layout.fragment_dashboard

    companion object {
        val TAG: String = DashboardFragment::class.java.simpleName
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadFestivalList()
    }

    private fun loadFestivalList() {
        GlobalScope.launch(Dispatchers.IO) {
            if (null != viewModel.getLastRowFromTable()) {
                viewModel.getLastRowFromTable().let {
                    lastMonthOfCurrentList = it?.tithiMM!!
                    lastYearOfCurrentList = it.tithiYYYY
                }
                if (lastMonthOfCurrentList == Calendar.getInstance().get(Calendar.MONTH)) {
                    Log.e(TAG, "loadFestivalList: this is the last month So update the list")
                    GlobalScope.launch(Dispatchers.Main) {
                        getFestivalList()
                    }
                } else if (lastMonthOfCurrentList < Calendar.getInstance()
                        .get(Calendar.MONTH) && lastYearOfCurrentList == Calendar.getInstance()
                        .get(Calendar.YEAR)
                ) {
                    Log.e(TAG, "loadFestivalList: toooo Late")
                    GlobalScope.launch(Dispatchers.Main) {
                        getFestivalList()
                    }
                } else {
                    Log.e(TAG, "Load from DB")
                    initEventRecyclerView()
                }

            } else {
                Log.e(TAG, "loadFestivalList: Data Not available in local DB: Load data from API")
                GlobalScope.launch(Dispatchers.Main) {
                    getFestivalList()
                }
            }
        }

    }

    /**
     * Tahevaro ni Festival List API
     */
    private fun getFestivalList() {
        requireActivity().let {
            viewModel.getFestivalList().observe(requireActivity(), { networkResource ->
                when (networkResource.status) {
                    Status.LOADING -> {
                        progressBar.show(requireContext(), false)
                        Log.e(TAG, "Loading.....")
                    }
                    Status.SUCCESS -> {
                        progressBar.getDialog()?.isShowing.let {
                            progressBar.getDialog()?.dismiss()
                        }
                        Log.d(TAG, "SUCCESS")
                        setUpUserData(networkResource)
                    }
                    Status.ERROR -> {
                        progressBar.getDialog()?.isShowing.let {
                            progressBar.getDialog()?.dismiss()
                        }
                        Log.e(TAG, "Error: " + networkResource.msg)
                    }
                }
            })
        }
    }

    private fun setUpUserData(networkResource: NetworkStateHelper<EventResponseModel>) {
        GlobalScope.launch(Dispatchers.Default) {
            networkResource.data?.Data?.let {
                val festivalList = mutableListOf<Festival>()
                it.forEach { it1 ->
                    val festival = Festival(
                        it1.Id,
                        it1.Date,
                        it1.Festival,
                        it1.Day,
                        parseIntoFormattedDate(it1.Date),
                        parseIntoFormattedMonth(it1.Date),
                        parseIntoFormattedYear(it1.Date)
                    )
                    festivalList.add(festival)
                }
            }
        }
    }

    private fun initEventRecyclerView() {
        GlobalScope.launch(Dispatchers.Main) {
            val festivalListFromMonth = viewModel.getFestivalListFromMonth()
            mAdapter = EventListAdapter(festivalListFromMonth)
            rvEvents.adapter = mAdapter
        }
    }

    private fun isInternetAvailable(): Boolean {
        val connectivityManager =
            context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val capabilities =
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            } else {
                TODO("VERSION.SDK_INT < M")
            }
        if (capabilities != null) {
            when {
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_CELLULAR")
                    return true
                }
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_WIFI")
                    return true
                }
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_ETHERNET")
                    return true
                }
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_VPN) -> {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_VPN")
                    return true
                }
            }
        }
        return false
    }


}