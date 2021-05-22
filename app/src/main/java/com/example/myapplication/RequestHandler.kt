import java.io.*
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder
import java.util.*
import javax.net.ssl.HttpsURLConnection

class RequestHandler {
    //Metode yang digunakan mengirim httpPostRequest
    fun sendPostRequest(
        requestURL: String?,
        postDataParams: HashMap<String, String>
    ): String {
        //Membuat URL
        val url: URL

        //Objek StringBuilder digunakan menyimpan pesan diambil dari server
        var sb = StringBuilder()
        try {
            //Inisialisasi URL
            url = URL(requestURL)

            //Koneksi HttpURLConnection
            val conn = url.openConnection() as HttpURLConnection

            //Konfigurasi koneksi
            conn.readTimeout = 15000
            conn.connectTimeout = 15000
            conn.requestMethod = "POST"
            conn.doInput = true
            conn.doOutput = true

            //Membuat Keluaran Stream
            val os = conn.outputStream

            //Menuliskan Parameter Untuk Permintaan
            //Kita menggunakan metode getPostDataString yang didefinisikan di bawah ini
            val writer = BufferedWriter(
                OutputStreamWriter(os, "UTF-8")
            )
            writer.write(getPostDataString(postDataParams))
            writer.flush()
            writer.close()
            os.close()
            val responseCode = conn.responseCode
            if (responseCode == HttpsURLConnection.HTTP_OK) {
                val br = BufferedReader(InputStreamReader(conn.inputStream))
                sb = StringBuilder()
                var response: String?
                //Pembaca respon server
                while (br.readLine().also { response = it } != null) {
                    sb.append(response)
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return sb.toString()
    }

    fun sendGetRequest(requestURL: String?): String {
        val sb = StringBuilder()
        try {
            val url = URL(requestURL)
            val con = url.openConnection() as HttpURLConnection
            val bufferedReader = BufferedReader(InputStreamReader(con.inputStream))
            var s: String
            while (bufferedReader.readLine().also { s = it } != null) {
                sb.append(s+"\n")
            }
        } catch (e: Exception) {
        }
        return sb.toString()
    }

    fun sendGetRequestParam(requestURL: String, id: String): String {
        val sb = StringBuilder()
        try {
            val url = URL(requestURL + id)
            val con = url.openConnection() as HttpURLConnection
            val bufferedReader = BufferedReader(InputStreamReader(con.inputStream))
            var s: String
            while (bufferedReader.readLine().also { s = it } != null) {
                sb.append(
                    """
                        $s
                        
                        """.trimIndent()
                )
            }
        } catch (e: Exception) {
        }
        return sb.toString()
    }

    @Throws(UnsupportedEncodingException::class)
    private fun getPostDataString(params: HashMap<String, String>): String {
        val result = StringBuilder()
        var first = true
        for ((key, value) in params) {
            if (first) first = false else result.append("&")
            result.append(URLEncoder.encode(key, "UTF-8"))
            result.append("=")
            result.append(URLEncoder.encode(value, "UTF-8"))
        }
        return result.toString()
    }
}