package com.jacky.shipment.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import androidx.lifecycle.viewModelScope
import com.jacky.shipment.domain.Destination
import com.jacky.shipment.domain.Driver
import com.jacky.shipment.repository.DriverAndDestinationRepository
import com.jacky.shipment.repository.DriverAndDestinationRepositoryImpl
import com.jacky.shipment.repository.MatchedDriverRepository
import com.jacky.shipment.repository.MatchedDriverRepositoryImpl
import com.jacky.shipment.utils.MatchDriverEngine
import kotlinx.coroutines.launch

class ShipmentAssignViewModel(private val dispatcher: CoroutineDispatcher = Dispatchers.IO): ViewModel() {
    private val driverAndDestinationRepository: DriverAndDestinationRepository by lazy { DriverAndDestinationRepositoryImpl() }
    private val matchedDriverRepository: MatchedDriverRepository by lazy { MatchedDriverRepositoryImpl() }

    private val _driverListLiveData by lazy { MutableLiveData<List<Driver>>() }
    internal val driverListLiveData: LiveData<List<Driver>>
        get() = _driverListLiveData

    private val _shipmentDestinationLiveData by lazy { MutableLiveData<Destination>() }
    internal val shipmentDestinationLiveData: LiveData<Destination>
        get() = _shipmentDestinationLiveData

    fun fetchAndMatchDrivers() {
        viewModelScope.launch(dispatcher) {
            val driverAndDestinations = driverAndDestinationRepository.fetch()
            val matchEngine = MatchDriverEngine(driverAndDestinations.drivers, driverAndDestinations.destinations)
            matchEngine.matchDriver(0, 0)
            matchedDriverRepository.persistDriverMap(matchEngine.getOptimalDriverMap(), driverAndDestinations.destinations)
            _driverListLiveData.postValue(driverAndDestinations.drivers)
        }
    }

    fun fetchShipmentDestination(driverId: Int) {
        viewModelScope.launch(dispatcher) {
            val map = matchedDriverRepository.retrieveDriverMap()
            _shipmentDestinationLiveData.postValue(map[driverId])
        }
    }
}
