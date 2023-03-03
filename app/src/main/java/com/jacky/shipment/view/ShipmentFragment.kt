package com.jacky.shipment.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.jacky.shipment.databinding.FragmentShipmentBinding
import com.jacky.shipment.viewmodel.ShipmentAssignViewModel

class ShipmentFragment(val driverId: Int) : Fragment() {

    private lateinit var binding: FragmentShipmentBinding
    private val viewModel: ShipmentAssignViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentShipmentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.fetchShipmentDestination(driverId)
        viewModel.shipmentDestinationLiveData.observe(
            viewLifecycleOwner
        ) { destination ->
            binding.destination.text = destination.streetNumberAndName
        }
    }

    companion object {
        fun newInstance(driverId: Int): ShipmentFragment = ShipmentFragment(driverId)
    }
}
