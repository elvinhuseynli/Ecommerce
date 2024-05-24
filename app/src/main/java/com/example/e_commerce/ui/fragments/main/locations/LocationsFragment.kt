package com.example.e_commerce.ui.fragments.main.locations

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import com.example.e_commerce.MainApplication
import com.example.e_commerce.R
import com.example.e_commerce.core_utils.CookieBarInst
import com.example.e_commerce.core_utils.viewLifecycleScope
import com.example.e_commerce.data.models.auth.LocationDataModel
import com.example.e_commerce.databinding.BottomSheetLocationBinding
import com.example.e_commerce.databinding.FragmentLocationsBinding
import com.example.e_commerce.ui.adapters.LocationAdapter
import com.example.e_commerce.ui.adapters.ProductAdapter
import com.example.e_commerce.ui.intents.main.LocationUIEffect
import com.example.e_commerce.ui.intents.main.LocationUIEvent
import com.example.e_commerce.ui.intents.main.LocationUIState
import com.example.e_commerce.ui.intents.main.ProductUIEffect
import com.example.e_commerce.ui.intents.main.ProductUIEvent
import com.example.e_commerce.ui.intents.main.ProductUIState
import com.example.e_commerce.ui.viewModelFactory.ViewModelFactory
import com.example.e_commerce.ui.viewmodels.main.LocationViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onSubscription
import javax.inject.Inject

class LocationsFragment : Fragment(), OnMapReadyCallback {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var binding: FragmentLocationsBinding
    private lateinit var bottomSheetBinding: BottomSheetLocationBinding
    private val viewModel: LocationViewModel by viewModels { viewModelFactory }

    private lateinit var adapter: LocationAdapter

    private lateinit var dialog: BottomSheetDialog

    private lateinit var map: GoogleMap

    private val locationList: ArrayList<LatLng> = arrayListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLocationsBinding.inflate(layoutInflater)

        (activity?.application as? MainApplication)?.getComponent()?.inject(this)

        bottomSheetBinding = BottomSheetLocationBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dialog = BottomSheetDialog(requireContext())

        val map: SupportMapFragment = this.childFragmentManager.findFragmentById(R.id.mapObject) as SupportMapFragment

        map.getMapAsync(this)

        viewModel.uiEffect.onSubscription {
            if(savedInstanceState==null) {
                viewModel.onEvent(LocationUIEvent.OnViewCreated)
            }
        }.onEach(::renderEffect).launchIn(viewLifecycleScope)
        viewModel.uiState.onEach(::renderState).launchIn(viewLifecycleScope)

        setListeners()
        setListenersBottomSheet()
    }

    private fun setListeners() = with(binding){
        searchBtn.setOnClickListener {
            if(dialog.isShowing) {
                showBottomSheet(false)
            } else {
                showBottomSheet(true)
            }
        }
    }

    private fun setListenersBottomSheet() = with(bottomSheetBinding) {
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                searchView.clearFocus()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                val searchText = newText.orEmpty().lowercase()
                viewModel.onEvent(LocationUIEvent.OnSearchViewChange(searchText))
                return false
            }
        })
    }


    private fun renderState(state: LocationUIState) = with(bottomSheetBinding){

        state.data.let {
            loadMap(state.data)
        }

        progressBar.visibility = if(state.isLoading) View.VISIBLE else View.GONE
        rvLocation.visibility = if(state.isLoading) View.GONE else View.VISIBLE

        state.updatedList.let {
            adapter = LocationAdapter(requireContext(), state.updatedList) {
                moveCamera(it)
            }
            rvLocation.adapter = adapter
        }
    }

    private fun moveCamera(position: Int) {
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(locationList[position], 15f))
        showBottomSheet(false)
    }

    private fun loadMap(data: List<LocationDataModel>) {
        for (i in data) {
            val location = LatLng(i.latitude.toDouble(), i.longitude.toDouble())
            map.addMarker(MarkerOptions().position(location).title(i.addressTitle))
            locationList.add(location)
        }
    }

    override fun onMapReady(m0: GoogleMap) {
        map = m0
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(40.3911039316457, 49.859471405228334), 12f))
    }

    private fun showBottomSheet(visible: Boolean) {
        if(visible) {
            dialog.setContentView(bottomSheetBinding.root)
            dialog.setCancelable(true)
            dialog.show()
        } else {
            dialog.dismiss()
        }
    }

    private fun renderEffect(effect: LocationUIEffect) {
        when(effect) {
            is LocationUIEffect.ShowMessage -> handleShowMessageEffect(effect.msg)
        }
    }

    private fun handleShowMessageEffect(msg: String) {
        CookieBarInst.buildMessage(requireActivity(), msg)
    }

}