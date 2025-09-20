package com.example.nativo

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

class DetailFragment : Fragment() {

    private val args: DetailFragmentArgs by navArgs()

    private lateinit var itemNameTextView: TextView
    private lateinit var itemDescriptionTextView: TextView
    private lateinit var locationTextView: TextView
    private lateinit var getLocationButton: Button

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            getLastKnownLocation()
        } else {
            locationTextView.text = getString(R.string.location_permission_denied_message)
            Toast.makeText(requireContext(), getString(R.string.location_permission_denied_toast), Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_detail, container, false)
        itemNameTextView = view.findViewById(R.id.detailItemNameTextView)
        itemDescriptionTextView = view.findViewById(R.id.detailItemDescriptionTextView)
        locationTextView = view.findViewById(R.id.locationTextView)
        getLocationButton = view.findViewById(R.id.getLocationButton)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val selectedItem = args.selectedItem
        itemNameTextView.text = selectedItem.name
        itemDescriptionTextView.text = selectedItem.description

        // Definir texto inicial para locationTextView usando string resource
        locationTextView.text = getString(R.string.location_label_default)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())

        getLocationButton.setOnClickListener {
            requestLocationPermission()
        }
    }

    private fun requestLocationPermission() {
        when {
            ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED -> {
                getLastKnownLocation()
            }
            shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION) -> {
                locationTextView.text = getString(R.string.location_permission_rationale)
                requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
            }
            else -> {
                requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
            }
        }
    }

    private fun getLastKnownLocation() {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            fusedLocationClient.lastLocation
                .addOnSuccessListener { location ->
                    if (location != null) {
                        locationTextView.text = getString(R.string.location_coordinates, location.latitude, location.longitude)
                    } else {
                        locationTextView.text = getString(R.string.location_not_found)
                    }
                }
                .addOnFailureListener {
                    locationTextView.text = getString(R.string.location_failure)
                }
        } else {
            locationTextView.text = getString(R.string.location_permission_not_granted_fallback)
        }
    }
}