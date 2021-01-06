package com.example.utb_app

import android.os.AsyncTask
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import org.json.JSONObject
import java.net.URL
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class HomeActivity : AppCompatActivity() {

    var CITY: String = "Pokojov"
    val API: String = "26b062aa8ccc96362764a7997d99f063"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        val myBtn = findViewById<Button>(R.id.button_najit)
        val poloha = findViewById<TextView>(R.id.editTextText_poloha)

        myBtn.setOnClickListener{
            this.CITY = poloha.text.toString();
            weatherTask().execute()
        }
        weatherTask().execute()
    }

    inner class weatherTask() : AsyncTask<String, Void, String>()
    {
        override fun onPreExecute() {
            super.onPreExecute()
            findViewById<ProgressBar>(R.id.nacitani).visibility = View.VISIBLE
            findViewById<ConstraintLayout>(R.id.hlavniController).visibility = View.GONE
            findViewById<TextView>(R.id.ChybaText).visibility = View.GONE

        }

    override fun doInBackground(vararg p0: String?) : String? {
        var response :String?

        try {
            response = URL("https://api.openweathermap.org/data/2.5/weather?q=$CITY&units=metric&appid=$API")
                .readText(Charsets.UTF_8)
            }
        catch (e: Exception){
            response = null
        }

        return response

    }

    override fun onPostExecute(result: String?)
    {
        super.onPostExecute(result)
        try
            {

                var jsonObj = JSONObject(result)
                val main = jsonObj.getJSONObject("main")
                val sys = jsonObj.getJSONObject("sys")
                val vitr = jsonObj.getJSONObject("wind")
                val weather = jsonObj.getJSONArray("weather").getJSONObject(0)
                val temp = main.getString("temp") + "°C"
                val temp_min = main.getString("temp_min") + "°C"
                val temp_max = main.getString("temp_max") + "°C"
                val vychod : Long = sys.getLong("sunrise")
                val zapad : Long = sys.getLong("sunset")
                val vitrRychlost = vitr.getString("speed")
                val pocasiPopis = weather.getString("description")
                val adresa = jsonObj.getString("name") + ", " + sys.getString("country")
                val formatter: DateFormat = SimpleDateFormat("HH:mm:ss")
                formatter.setTimeZone(TimeZone.getTimeZone("CET"))

                findViewById<TextView>(R.id.textView_min_value).text = temp_min
                findViewById<TextView>(R.id.textView_max_value).text = temp_max
                findViewById<TextView>(R.id.textView_teplota).text = temp
                findViewById<TextView>(R.id.textView_poloha).text = adresa
                findViewById<TextView>(R.id.textView_pocasi_popis).text = pocasiPopis
                findViewById<TextView>(R.id.textView_vitr_value).text = vitrRychlost + " m/s"
                findViewById<TextView>(R.id.textView_vychod_value).text = formatter.format(vychod*1000)
                findViewById<TextView>(R.id.textView_zapad_value).text = formatter.format(zapad*1000)

                findViewById<ProgressBar>(R.id.nacitani).visibility = View.GONE
                findViewById<ConstraintLayout>(R.id.hlavniController).visibility = View.VISIBLE
            }

        catch (e: Exception)
            {
                findViewById<ProgressBar>(R.id.nacitani).visibility = View.GONE
                findViewById<ConstraintLayout>(R.id.hlavniController).visibility = View.VISIBLE
                findViewById<TextView>(R.id.ChybaText).visibility = View.VISIBLE
            }
    }

    }

}