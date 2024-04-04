package com.farias.weatherapp.data.location

import android.app.Application
import android.content.Context
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
import com.farias.weatherapp.domain.location.LocationTracker
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.Geofence
import kotlinx.coroutines.suspendCancellableCoroutine
import java.util.Locale
import javax.inject.Inject
import kotlin.coroutines.resume

class DefaultLocationTracker @Inject constructor(
    private val locationClient: FusedLocationProviderClient,
    private val application: Application,
) : LocationTracker {

    private val locationManager =
        application.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    private val geocoder = Geocoder(application.applicationContext)

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override suspend fun getCurrentLocation(): Location? {
        val hasAccessFineLocation = ContextCompat.checkSelfPermission(
            application,
            android.Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
        val hasAccessCoarseLocation = ContextCompat.checkSelfPermission(
            application,
            android.Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        val isGpsEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)

        if (!hasAccessFineLocation || !hasAccessCoarseLocation || !isGpsEnabled) {
            return null
        }

        return suspendCancellableCoroutine { cont ->
            locationClient.lastLocation.apply {
                if (isComplete) {
                    val location = if (isSuccessful) result else null
                    cont.resume(location)
                    return@suspendCancellableCoroutine
                }
                addOnSuccessListener {
                    val address = geocoder.getFromLocation(it.latitude, it.longitude, 1 )
                    val extras = Bundle()
                    val cityName = address?.get(0)?.locality
                    val countryName = address?.get(0)?.countryName
                    extras.putString(KEY_CITY_NAME, cityName)
                    extras.putString(KEY_COUNTRY_NAME, countryName)
                    it.extras = extras
                    cont.resume(it)
                }
                addOnFailureListener {
                    cont.resume(null)
                }
                addOnCanceledListener {
                    cont.cancel()
                }
            }
        }
    }

    companion object {
        const val KEY_CITY_NAME = "CityName"
        const val KEY_COUNTRY_NAME = "CountryName"
    }
}