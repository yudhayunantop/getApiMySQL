package com.example.myapplication

import RequestHandler
import android.app.ProgressDialog
import android.os.AsyncTask
import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import org.json.JSONException
import org.json.JSONObject


class TampilData : AppCompatActivity() {
    companion object{
        const val EMP_ID = ""
    }

    val rh = RequestHandler()
    val Konfigurasi = Konfigurasi()

    private var id = ""

    private var tvidaturanB5: EditText? = null
    private var tvextra: EditText? = null
    private var tvagree: EditText? = null
    private var tvcons: EditText? = null
    private var tvneuro: EditText? = null
    private var tvopen: EditText? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tampil_data)

        tvidaturanB5 = findViewById(R.id.idaturanB5)
        tvextra = findViewById(R.id.extra)
        tvagree = findViewById(R.id.agree)
        tvcons = findViewById(R.id.cons)
        tvneuro = findViewById(R.id.neuro)
        tvopen = findViewById(R.id.open)

        id = intent.getStringExtra(EMP_ID).toString()

        getData()
    }

    private fun getData() {
        class GetData :
            AsyncTask<Void?, Void?, String?>() {
            var loading: ProgressDialog? = null
            override fun onPreExecute() {
                super.onPreExecute()
                loading =
                    ProgressDialog.show(this@TampilData, "Fetching...", "Wait...", false, false)
            }

            override fun onPostExecute(s: String?) {
                super.onPostExecute(s)
                loading!!.dismiss()
                showData(s!!)
            }

            override fun doInBackground(vararg params: Void?): String? {

                return rh.sendGetRequestParam(Konfigurasi.URL_GET_ID, id)
            }
        }

        val ge = GetData()
        ge.execute()
    }

    private fun showData(json: String) {
        try {
            val jsonObject = JSONObject(json)
            val result = jsonObject.getJSONArray(Konfigurasi.TAG_JSON_ARRAY)
            val c = result.getJSONObject(0)

            val idaturanB5 = c.getString(Konfigurasi.KEY_EMP_ID)
            val extra = c.getString(Konfigurasi.KEY_EMP_EXTRA)
            val agree = c.getString(Konfigurasi.KEY_EMP_AGREE)
            val cons = c.getString(Konfigurasi.KEY_EMP_CONS)
            val neuro = c.getString(Konfigurasi.KEY_EMP_NEURO)
            val open = c.getString(Konfigurasi.KEY_EMP_OPEN)

            tvidaturanB5!!.setText(idaturanB5)
            tvextra!!.setText(extra)
            tvagree!!.setText(agree)
            tvcons!!.setText(cons)
            tvneuro!!.setText(neuro)
            tvopen!!.setText(open)

            } catch (e: JSONException) {
            e.printStackTrace()
        }
    }
}