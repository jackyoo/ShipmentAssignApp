package com.jacky.shipment.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.jacky.shipment.R
import com.jacky.shipment.databinding.FragmentMainBinding
import com.jacky.shipment.viewmodel.ShipmentAssignViewModel

class MainFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding
    private val viewModel: ShipmentAssignViewModel by activityViewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentMainBinding.inflate(layoutInflater)
        return binding.root
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.progressBar.visibility = View.VISIBLE
        viewModel.fetchAndMatchDrivers()
        viewModel.driverListLiveData.observe(
            viewLifecycleOwner
        ) { drivers ->
            binding.progressBar.visibility = View.GONE
            binding.listRecyclerView.apply {
                layoutManager = LinearLayoutManager(activity)
                adapter = ListAdapter(drivers) { driverId ->
                    val shipmentFragment = ShipmentFragment.newInstance(driverId)
                    activity?.supportFragmentManager?.beginTransaction()?.add(R.id.fragment_container, shipmentFragment)?.addToBackStack("shipment")?.commit()
                }
            }
        }
    }

    companion object {
        fun newInstance(): MainFragment = MainFragment()
    }
}
