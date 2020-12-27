package com.example.utb_app

import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import org.json.JSONObject
import java.net.URL

class HomeActivity : AppCompatActivity() {

    val CITY: String = "Pokojov"
    val API: String = "26b062aa8ccc96362764a7997d99f063"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

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
        catch(e:Exception){
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
                val temp_min = "Minimum: " + main.getString("temp_min") + "°C"
                val temp_max = "Maximum: " + main.getString("temp_max") + "°C"
                val vychod : Long = sys.getLong("sunrise")
                val zapad : Long = sys.getLong("sunset")
                val vitrRychlost = vitr.getString("speed")
                val pocasiPopis = weather.getString("description")
                val adresa = jsonObj.getString("name") + ", " + sys.getString("country")

                findViewById<TextView>(R.id.textView_teplota).text = temp
                findViewById<TextView>(R.id.textView_teplota).text = temp





                findViewById<ProgressBar>(R.id.nacitani).visibility = View.GONE
                findViewById<ConstraintLayout>(R.id.hlavniController).visibility = View.VISIBLE
            }

        catch(e: Exception)
            {
                findViewById<ProgressBar>(R.id.nacitani).visibility = View.GONE
                findViewById<ConstraintLayout>(R.id.hlavniController).visibility = View.VISIBLE
                findViewById<TextView>(R.id.ChybaText).visibility = View.VISIBLE
            }
    }

    }

}