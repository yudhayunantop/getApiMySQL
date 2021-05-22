package com.example.myapplication

import RequestHandler
import android.app.ProgressDialog
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemClickListener
import android.widget.ListAdapter
import android.widget.ListView
import android.widget.SimpleAdapter
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.TampilData.Companion.EMP_ID
import org.json.JSONException
import org.json.JSONObject



/**
 * Created by Muhammad Rizal Supriadi on 15/2/2020.
 * forumkoding.com
 */
class MainActivity : AppCompatActivity(), OnItemClickListener {
    private var listView: ListView? = null
    private var JSON_STRING: String? = null


    val Konfigurasi = Konfigurasi()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        listView = findViewById<View>(R.id.listView) as ListView
        listView!!.onItemClickListener = this
        jSON
    }

    private fun showData() {
        var jsonObject: JSONObject? = null
        val list = ArrayList<HashMap<String, String?>>()
        try {
            jsonObject = JSONObject(JSON_STRING)
            val result = jsonObject.getJSONArray(Konfigurasi.TAG_JSON_ARRAY)
            for (i in 0 until result.length()) {
                val jo = result.getJSONObject(i)
                val id = jo.getString(Konfigurasi.TAG_ID)
                val extra = jo.getString(Konfigurasi.TAG_EXTRA)

                val users = HashMap<String, String?>()
                users[Konfigurasi.TAG_ID] = id
                users[Konfigurasi.TAG_EXTRA] = extra
                list.add(users)
            }
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        val adapter: ListAdapter = SimpleAdapter(
            this@MainActivity,
            list,
            R.layout.list_view,
            arrayOf(Konfigurasi.TAG_ID, Konfigurasi.TAG_EXTRA),
            intArrayOf(R.id.idaturanB5, R.id.extra)
        )
        listView!!.adapter = adapter
    }

    private val jSON: Unit
        private get() {
            class GetJSON :
                AsyncTask<Void?, Void?, String?>() {
                var loading: ProgressDialog? = null
                override fun onPreExecute() {
                    super.onPreExecute()
                    loading = ProgressDialog.show(
                        this@MainActivity,
                        "Mengambil Data",
                        "Mohon Tunggu...",
                        false,
                        false
                    )
                }

                override fun onPostExecute(s: String?) {
                    super.onPostExecute(s)
                    loading!!.dismiss()
                    JSON_STRING = s
                    showData()
                }

                override fun doInBackground(vararg params: Void?): String? {
                    val rh = RequestHandler()
                    return rh.sendGetRequest(Konfigurasi.URL_GET_ALL)
                }
            }

            val gj = GetJSON()
            gj.execute()
        }

    override fun onItemClick(parent: AdapterView<*>, view: View, position: Int, id: Long) {
        val intent = Intent(this, TampilData::class.java)
        val map: HashMap<*, *> = parent.getItemAtPosition(position) as HashMap<*, *>
        val empId = map[Konfigurasi.TAG_ID].toString()
        intent.putExtra(EMP_ID, empId)
        startActivity(intent)
    }
}