package com.nos.tikihometest.tikihometest

import android.os.AsyncTask
import android.util.Log
import org.json.JSONArray
import org.json.JSONException
import java.io.*
import java.lang.IllegalStateException
import java.lang.StringBuilder
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.ProtocolException
import java.net.URL

class FetchKeywordAsyncTask(private val listener: FetchKeywordListener) : AsyncTask<URL, String, String>() {


    override fun onPreExecute() {
      listener.onStartFetchKeyword()
    }

    override fun doInBackground(vararg params: URL?): String {
        var conn: HttpURLConnection? = null
        var result = ""
        var inputStream: InputStream? = null
        for (url in params) {
            try {
                conn = url?.openConnection() as HttpURLConnection
                conn.requestMethod = "GET"
                inputStream = BufferedInputStream(conn.inputStream)
                val reader = BufferedReader(InputStreamReader(inputStream))
                val sb = StringBuilder()
                var line: String
                while (true) {
                    val line = reader.readLine() ?: break
                    sb.append(line + "\n")
                }
                result = sb.toString()
            } catch (ex: MalformedURLException) {
                Log.e(C.TAG, "MalformedURLException: $ex")
            } catch (ex: ProtocolException) {
                Log.e(C.TAG, "ProtocolException: $ex")
            } catch (ex: IllegalStateException) {
                Log.e(C.TAG, "IllegalStateException: $ex")

            } catch (ex: IOException) {
                Log.e(C.TAG, "IOException: $ex")

            } catch (ex: Exception) {
                Log.e(C.TAG, "Exception: $ex")

            } finally {
                if (conn != null) {
                    try {
                        inputStream?.close()
                        conn.disconnect()
                    } catch (ex: Exception) {
                        Log.e(C.TAG, "Exception: $ex")
                    }
                }
            }
        }
        return result
    }

    override fun onPostExecute(result: String?) {
        try {
            val jArray = JSONArray(result)
            var dataSet: ArrayList<String> = ArrayList()
            for (i in 0 until jArray.length()) {
                val key = jArray.getString(i)

                dataSet.add(key)
            }
            listener.onSuccessFetchKeyword(dataSet)
        } catch (ex: JSONException) {
            Log.e(C.TAG, "JSONException: $ex")
            listener.onFailedFetchKeyword()
        }


    }

}
interface FetchKeywordListener{
    fun onStartFetchKeyword()
    fun onFailedFetchKeyword()
    fun onSuccessFetchKeyword(dataSet: ArrayList<String>)
}