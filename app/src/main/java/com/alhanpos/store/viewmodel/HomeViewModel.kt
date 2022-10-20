package com.alhanpos.store.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alhanpos.store.model.response.dashboard.DashboardResponse
import com.alhanpos.store.model.response.dashboard.graph.DashboardGraphResponse
import com.alhanpos.store.repo.MainRepository
import com.alhanpos.store.util.NetworkHelper
import com.alhanpos.store.util.Resource
import kotlinx.coroutines.launch
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class HomeViewModel(
    private val mainRepository: MainRepository,
    private val networkHelper: NetworkHelper,
) : ViewModel() {

    val startDate = MutableLiveData<String>()
    val endDate = MutableLiveData<String>()

    private val setHomeData = MutableLiveData<Resource<DashboardResponse>>()
    val getHomeData: LiveData<Resource<DashboardResponse>>
        get() = setHomeData

    private val setGraph = MutableLiveData<Resource<DashboardGraphResponse>>()
    val getGraph: LiveData<Resource<DashboardGraphResponse>>
        get() = setGraph

    init {
        getLastMonthDate()
    }

    fun fetchHomeData(token: String) {
        viewModelScope.launch {
            setHomeData.postValue(Resource.loading(null))
            if (networkHelper.isNetworkConnected()) {
                mainRepository.dashboard(
                    token,
                    startDate.value.toString(),
                    endDate.value.toString(),
                    "1",
                ).let {
                    if (it.isSuccessful) {
                        setHomeData.postValue(Resource.success(it.body()))
                    } else setHomeData.postValue(
                        Resource.error(
                            it.message(), null
                        )
                    )
                }
            } else setHomeData.postValue(Resource.error("No internet connection", null))
        }
    }

    fun fetchGraphData(token: String) {
        viewModelScope.launch {
            setGraph.postValue(Resource.loading(null))
            if (networkHelper.isNetworkConnected()) {
                mainRepository.dashboard_graph(
                    token,
                    "1",
                    "1"
                ).let {
                    if (it.isSuccessful) {
                        setGraph.postValue(Resource.success(it.body()))
                    } else setGraph.postValue(
                        Resource.error(
                            it.message(), null
                        )
                    )
                }
            } else setGraph.postValue(Resource.error("No internet connection", null))
        }
    }

    private fun getLastMonthDate() {
        val dateFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd")
        val aCalendar = Calendar.getInstance()
        aCalendar.add(Calendar.MONTH, -1)
        aCalendar[Calendar.DATE] = 1
        val firstDateOfPreviousMonth = aCalendar.time
        aCalendar[Calendar.DATE] = aCalendar.getActualMaximum(Calendar.DAY_OF_MONTH)
        val lastDateOfPreviousMonth = aCalendar.time

        startDate.value = dateFormat.format(firstDateOfPreviousMonth)
        endDate.value = dateFormat.format(lastDateOfPreviousMonth)

        Log.d(
            "Date",
            "Date " + dateFormat.format(firstDateOfPreviousMonth) + "   " + dateFormat.format(
                lastDateOfPreviousMonth
            )
        )
    }

}