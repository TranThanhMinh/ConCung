package com.example.concung.view.shop

import android.Manifest
import android.annotation.SuppressLint
import android.app.ActionBar
import android.content.pm.PackageManager
import android.content.res.Resources
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
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.concung.R
import com.example.concung.model.shop.AddressShop
import com.example.concung.view.adapter.DistanceAdapter
import com.example.concung.view.adapter.ShopsAdapter
import com.example.concung.viewmodel.HomeViewModel
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.LocationListener
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions
import kotlinx.android.synthetic.main.map_shop_fragment.*
import org.json.JSONObject
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.text.DecimalFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.asin
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt


class MapShopsFragment : Fragment(), OnMapReadyCallback,
        LocationListener, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    var mGoogleApiClient: GoogleApiClient? = null
    var mLocationRequest: LocationRequest? = null
    private var listDistance: ArrayList<String>? = null
    private var location2: LatLng? = null
    var current: LatLng? = null

    var number = 0
    var dis = 10
    var location: Location? = null
    private var listShop: List<AddressShop>? = null
    private var listShopSort: List<AddressShop>? = null

    var homeViewModel: HomeViewModel? = null
    var shopsAdapter: ShopsAdapter? = null

    companion object {
        private var mMap: GoogleMap? = null
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.map_shop_fragment, container, false)
    }

    @SuppressLint("WrongConstant")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Get the SupportMapFragment and request notification when the map is ready to be used.
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as? SupportMapFragment
        mapFragment?.getMapAsync(this)

        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)

        tvNumber.text = "Siêu thị gần nhất"

        listDistance = ArrayList()
        listDistance!!.add("10")
        listDistance!!.add("50")
        listDistance!!.add("100")
        listDistance!!.add("200")

        //Creating the ArrayAdapter instance having the country list
        val aa = DistanceAdapter(context!!, listDistance!!)
        //Setting the ArrayAdapter data on the Spinner
        spDistance.adapter = aa

        spDistance.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                dis = listDistance!![position].toInt()
                if (listShop != null) {
                    distanceShop(location!!)
                }
            }
        }

        var layout = LinearLayoutManager(context)
        layout.orientation = LinearLayout.VERTICAL
        rvShops.layoutManager = layout

        shopsAdapter = ShopsAdapter(context!!)
        rvShops.adapter = shopsAdapter
    }

    override fun onMapReady(googleMap: GoogleMap?) {
        mMap = googleMap
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
        homeViewModel!!.getShop().observe(this, androidx.lifecycle.Observer { item ->
            progress_bar.visibility = View.GONE
            if (item != null) {
                listShop = item.getData()!!
                distanceShop(location!!)
            }
        })


        //stop location updates
        if (mGoogleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this)
        }


        // Getting URL to the Google Directions API
        //  val url: String = getDirectionsUrl(current!!, location2!!)

        //   val downloadTask = DownloadTask()

        // Start downloading json data from Google Directions API

        // Start downloading json data from Google Directions API
        //   downloadTask.execute(url)
    }

    /**
     * function show address to maps
     */
    fun distanceShop(location: Location) {
        this.location = location
        // Set default position
        current = LatLng(location!!.latitude, location!!.longitude) // 14.0583° N, 108.2772° E
        val markerOptions = MarkerOptions()
        markerOptions.position(current!!)
        markerOptions.title(getNameCurrent(current!!))
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
        mMap!!.addMarker(markerOptions)
        mMap!!.animateCamera(CameraUpdateFactory.newLatLngZoom(current, 13f))

        number = 0
        listShopSort = ArrayList()
        for (i in listShop!!) {
            location2 = getLocationFromAddress(i.getAddress())
            if (calculationByDistance(current!!, location2!!) <= dis) {//shops in about 10km
                (listShopSort as ArrayList).add(i)
                number += 1
                val vietnam2 = LatLng(location2!!.latitude, location2!!.longitude) // 14.0583° N, 108.2772° E
                mMap!!.addMarker(MarkerOptions().position(vietnam2).title(i.getAddress()))
            }
        }
        shopsAdapter!!.loadData(listShopSort!!, current)
        tvNumber.text = "$number Siêu thị gần nhất"

        try {
            var layout = llMap.layoutParams
            var heightShop = rvShops.height
            var heightScreen = Resources.getSystem().displayMetrics.heightPixels / 2
            if (llMap.height < heightShop) {
                layout.height = heightScreen
                llMap.layoutParams = layout
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
        }

    }

    private fun getNameCurrent(current: LatLng): String {
        val gc = Geocoder(context)

        if (Geocoder.isPresent()) {
            val list = gc.getFromLocation(current.latitude, current.longitude, 1)
            val address = list[0]
            val str = StringBuffer()
            str.append("""${address.getAddressLine(0)}""".trimIndent())
            return str.toString()
        }
        return ""
    }

    private fun calculationByDistance(StartP: LatLng, EndP: LatLng): Double {
        val radius = 6371 // radius of earth in Km
        val lat1 = StartP.latitude
        val lat2 = EndP.latitude
        val lon1 = StartP.longitude
        val lon2 = EndP.longitude
        val dLat = Math.toRadians(lat2 - lat1)
        val dLon = Math.toRadians(lon2 - lon1)
        val a = (sin(dLat / 2) * sin(dLat / 2)
                + (cos(Math.toRadians(lat1))
                * cos(Math.toRadians(lat2)) * sin(dLon / 2)
                * sin(dLon / 2)))
        val c = 2 * asin(sqrt(a))
        val valueResult = radius * c
        val km = valueResult / 1
        val newFormat = DecimalFormat("####")
        val kmInDec: Int = Integer.valueOf(newFormat.format(km))
        val meter = valueResult % 1000
        val meterInDec: Int = Integer.valueOf(newFormat.format(meter))
        Log.e("Radius Value", "" + valueResult + "   KM  " + kmInDec
                + " Meter   " + meterInDec)
        return radius * c
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