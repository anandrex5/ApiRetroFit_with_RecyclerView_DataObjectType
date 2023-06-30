package com.company0ne.apiretrofit_object

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import com.company0ne.apiretrofit.API.ApiClient
import com.company0ne.apiretrofit.API.ApiInterface
import com.company0ne.apiretrofit_object.API.HomeViewModel
import com.google.gson.JsonObject
import org.json.JSONObject
import retrofit2.Callback
import retrofit2.Response

/*  Api - https://jsonplaceholder.typicode.com/posts/1  */

class MainActivity : AppCompatActivity() {
    private lateinit var arrayList: ArrayList<HomeViewModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        arrayList = ArrayList()
        retroFit()
    }

    // This is for Object
    private fun retroFit() {
        val apiInterface = ApiClient.client.create(ApiInterface::class.java)
        val call: Call<JsonObject> = apiInterface.getData()
        call.enqueue(object : Callback<JsonObject> {
            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                if (response.isSuccessful) {
                    val objects = JSONObject(response.body().toString())
                    Log.d("jsonObjectData", objects.toString())
                    val model = HomeViewModel()
                    model.userId = objects.getString("userId")
                    model.id = objects.getString("id")
                    model.title = objects.getString("title")
                    model.body = objects.getString("body")
                    arrayList.add(model)

                    buildRecycler()
                } else {
                    Log.d("jsonObjectData", response.message())
                }
            }

            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                Log.d("errorMsg", t.toString())
                // Handle failure here
            }
        })
    }

    private fun buildRecycler() {
        val recycler: RecyclerView = findViewById(R.id.recyclerView)
        recycler.layoutManager = LinearLayoutManager(this)
        recycler.adapter = DataAdapter(arrayList)
    }

    private class DataAdapter(private val list: ArrayList<HomeViewModel>) :
        RecyclerView.Adapter<DataAdapter.DataViewHolder>() {

        class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val textViewId: TextView = itemView.findViewById(R.id.userId)
            val textViewTitle: TextView = itemView.findViewById(R.id.id)
            val textViewEmail: TextView = itemView.findViewById(R.id.title)
            val textViewDescription: TextView = itemView.findViewById(R.id.body)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
            val view: View = LayoutInflater.from(parent.context)
                .inflate(R.layout.data_item, parent, false)
            return DataViewHolder(view)
        }

        override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
            holder.textViewId.text = list[position].userId
            holder.textViewTitle.text = list[position].id
            holder.textViewEmail.text = list[position].title
            holder.textViewDescription.text = list[position].body
        }

        override fun getItemCount(): Int {
            return list.size
        }
    }
}




