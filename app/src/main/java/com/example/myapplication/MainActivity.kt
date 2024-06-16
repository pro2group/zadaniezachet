package com.example.myapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONException

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        getWeatherButton.setOnClickListener {
            val cityName = cityEditText.text.toString().trim()
            if (cityName.isNotEmpty()) {
                requestWeather(cityName)
            }
        }
    }

    private fun requestWeather(city: String) {
        // Replace with your weather API endpoint and API key
        val apiKey = "your_api_key_here"
        val url = "https://api.openweathermap.org/data/2.5/weather?q=$city&appid=$apiKey&units=metric"

        val queue = Volley.newRequestQueue(this)

        val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, url, null,
            { response ->
                try {
                    val main = response.getJSONObject("main")
                    val temperature = main.getDouble("temp")
                    weatherTextView.text = "Temperature: $temperature°C"
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            },
            { error ->
                weatherTextView.text = "Error occurred: ${error.message}"
            })

        queue.add(jsonObjectRequest)
    }
}
