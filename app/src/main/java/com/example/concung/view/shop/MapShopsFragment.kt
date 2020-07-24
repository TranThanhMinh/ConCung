package com.example.concung.view.shop

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.AsyncTask
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.concung.R
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.LocationListener
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions
import org.json.JSONObject
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.util.*


class MapShopsFragment : Fragment(), OnMapReadyCallback,
        LocationListener, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {
    var mLastLocation: Location? = null
    var mCurrLocationMarker: Marker? = null
    var mGoogleApiClient: GoogleApiClient? = null
    var mLocationRequest: LocationRequest? = null

    var location2: LatLng? = null
    var current: LatLng? = null
    private val LOWER_MANHATTAN = LatLng(40.722543,
            -73.998585)
    private val TIMES_SQUARE = LatLng(40.7577, -73.9857)
    private val BROOKLYN_BRIDGE = LatLng(40.7057, -73.9964)

    companion object {
        private var mMap: GoogleMap? = null
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.map_shop_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Get the SupportMapFragment and request notification when the map is ready to be used.
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as? SupportMapFragment
        mapFragment?.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap?) {
        mMap = googleMap;
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(context!!,
                            Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                buildGoogleApiClient()
                mMap!!.isMyLocationEnabled = true
            }
        } else {
            buildGoogleApiClient();
            mMap!!.isMyLocationEnabled = true
        }

    }

    @Synchronized
    protected fun buildGoogleApiClient() {
        mGoogleApiClient = GoogleApiClient.Builder(context!!)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API).build()
        mGoogleApiClient!!.connect()
    }


    private fun getLocationFromAddress(strAddress: String?): LatLng? {
        val coder = Geocoder(context)
        val address: List<Address>?
        var p1: LatLng? = null
        try {
            // May throw an IOException
            address = coder.getFromLocationName(strAddress, 5)
            if (address == null) {
                return null
            }
            val location: Address = address[0]
            p1 = LatLng(location.latitude, location.longitude)
        } catch (ex: IOException) {
            ex.printStackTrace()
        }
        return p1
    }

    override fun onLocationChanged(location: Location?) {
        // Set default position
        // Add a marker in Sydney and move the camera
        location2 = getLocationFromAddress("81 Quang Trung, Hải Châu I, Hải Châu District, Da Nang")
        val vietnam2 = LatLng(location2!!.latitude, location2!!.longitude) // 14.0583° N, 108.2772° E

        mMap!!.addMarker(MarkerOptions().position(vietnam2).title("81 Quang Trung, Hải Châu I, Hải Châu District, Da Nang"))
        // mMap!!.animateCamera(CameraUpdateFactory.newLatLngZoom(vietnam,15f))


        current = LatLng(location!!.latitude, location!!.longitude) // 14.0583° N, 108.2772° E

        mMap!!.addMarker(MarkerOptions().position(current!!).title("Current Position"))
        mMap!!.animateCamera(CameraUpdateFactory.newLatLngZoom(current, 15f))

        /*  mLastLocation = location
          if (mCurrLocationMarker != null) {
              mCurrLocationMarker!!.remove()
          }
          //Place current location marker
          //Place current location marker
          val latLng = LatLng(location!!.latitude, location!!.longitude)
          val markerOptions = MarkerOptions()
          markerOptions.position(latLng)
          markerOptions.title("Current Position")
          markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
          mCurrLocationMarker = mMap!!.addMarker(markerOptions)*/

        //move map camera

        //move map camera
        /* mMap!!.moveCamera(CameraUpdateFactory.newLatLng(latLng))
         mMap!!.animateCamera(CameraUpdateFactory.zoomTo(15f))*/

        //stop location updates
        if (mGoogleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this)
        }

        //addLines()

        // Getting URL to the Google Directions API
        val url: String = getDirectionsUrl(current!!, location2!!)

        val downloadTask = DownloadTask()

        // Start downloading json data from Google Directions API

        // Start downloading json data from Google Directions API
        downloadTask.execute(url)
    }

    private fun addLines() {
        mMap!!.addPolyline(PolylineOptions()
                .add(current, location2).width(5f).color(Color.BLUE)
                .geodesic(true))
        // move camera to zoom on map
        mMap!!.moveCamera(CameraUpdateFactory.newLatLngZoom(current, 13f))
    }


    override fun onConnected(p0: Bundle?) {
        mLocationRequest = LocationRequest()
        mLocationRequest!!.interval = 1000
        mLocationRequest!!.fastestInterval = 1000
        mLocationRequest!!.priority = LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY
        if (ContextCompat.checkSelfPermission(context!!,
                        Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this)
        }
    }

    override fun onConnectionSuspended(p0: Int) {

    }

    override fun onConnectionFailed(p0: ConnectionResult) {

    }


    class DownloadTask : AsyncTask<String, Void, String>() {
        override fun doInBackground(vararg url: String): String {
            var data = ""
            try {
                var iStream: InputStream? = null
                var urlConnection: HttpURLConnection? = null
                try {
                    val url = URL(url[0])
                    urlConnection = url.openConnection() as HttpURLConnection
                    urlConnection.connect()
                    iStream = urlConnection!!.inputStream
                    val br = BufferedReader(InputStreamReader(iStream))
                    val sb = StringBuffer()
                    var line: String? = ""
                    while (br.readLine().also { line = it } != null) {
                        sb.append(line)
                    }
                    data = sb.toString()
                    br.close()
                } catch (e: java.lang.Exception) {
                    Log.d("Exception", e.toString())
                } finally {
                    iStream!!.close()
                    urlConnection!!.disconnect()
                }
            } catch (e: Exception) {
                Log.d("Background Task", e.toString())
            }
            return data
        }

        override fun onPostExecute(result: String) {
            super.onPostExecute(result)
            val parserTask: ParserTask = ParserTask()
            parserTask.execute(result)
        }
    }

    /**
     * A class to parse the Google Places in JSON format
     */
    private class ParserTask : AsyncTask<String, Int, List<List<HashMap<String, String>>>?>() {
        // Parsing the data in non-ui thread
        override fun doInBackground(vararg jsonData: String): List<List<HashMap<String, String>>>? {
            val jObject: JSONObject
            var routes: List<List<HashMap<String, String>>>? = null
            try {
                jObject = JSONObject(jsonData[0])
                val parser = DirectionsJSONParser()
                routes = parser.parse(jObject)
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
            }
            return routes
        }

        override fun onPostExecute(result: List<List<HashMap<String, String>>>?) {
            var points = ArrayList<LatLng>()
            var lineOptions: PolylineOptions? = null
            val markerOptions = MarkerOptions()
            for (i in result!!.indices) {

                lineOptions = PolylineOptions()
                val path = result[i]
                for (j in path.indices) {
                    val point = path[j]
                    val lat = point["lat"]!!.toDouble()
                    val lng = point["lng"]!!.toDouble()
                    val position = LatLng(lat, lng)
                    points.add(position)
                }
                lineOptions.addAll(points)
                lineOptions.width(12f)
                lineOptions.color(Color.RED)
                lineOptions.geodesic(true)
            }

// Drawing polyline in the Google Map for the i-th route
            if (lineOptions != null)
                mMap!!.addPolyline(lineOptions)
        }
    }

    private fun getDirectionsUrl(origin: LatLng, dest: LatLng): String {

        // Origin of route
        val str_origin = "origin=" + origin.latitude.toString() + "," + origin.longitude

        // Destination of route
        val str_dest = "destination=" + dest.latitude.toString() + "," + dest.longitude

        // Sensor enabled
        val sensor = "sensor=false"
        val mode = "mode=driving"
        // Building the parameters to the web service
        val parameters = "$str_origin&$str_dest&$sensor&$mode"

        // Output format
        val output = "json"

        // Building the url to the web service
        return "https://maps.googleapis.com/maps/api/directions/$output?$parameters"
    }
}