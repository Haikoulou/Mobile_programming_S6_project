package com.example.elouanproject

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.elouanproject.api.*
import com.example.elouanproject.model.*
import com.google.android.material.snackbar.Snackbar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.StringBuilder

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment() {
    private val listItems = ArrayList<DealsItem>()


    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_second, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var inputGameTitle: String? = arguments?.getString("inputGameTitle")
        if (inputGameTitle != null) {
            getDealsServer(inputGameTitle!!, view.context)
        } else {
            getDealsServer("", view.context)
        }

    }

    private fun onClickItem(item: DealsItem){

    }

    private fun getDealsServer(title:String, context:Context){
        val retrofitBuilder = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://www.cheapshark.com")
            .build()
            .create(ApiConnDeals::class.java)

        val retrofitData = retrofitBuilder.getDealsItem(title, 0, 100)

        retrofitData.enqueue(object : Callback<List<DealsItem>?> {
            override fun onFailure(call: Call<List<DealsItem>?>, t: Throwable) {
                view?.let {
                    Snackbar.make(it, "Check your internet connexion.", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show()
                }
                Log.d("SecondFragment", "onFailure: "+t.message)
            }

            override fun onResponse(call: Call<List<DealsItem>?>, response: Response<List<DealsItem>?>) {
                val responseBody = response.body()!!
                val list = ArrayList<DealsItem>()

                val myStringBuilder = StringBuilder()
                for (myData in responseBody) {
                    listItems.add(myData)
                }
                view?.findViewById<ProgressBar>(R.id.progress_circular)?.isVisible = false
                view?.findViewById<RecyclerView>(R.id.recycler_view)?.adapter = RecyclerItemsAdapter(listItems, context)
                view?.findViewById<RecyclerView>(R.id.recycler_view)?.layoutManager = LinearLayoutManager(activity!!)
                view?.findViewById<RecyclerView>(R.id.recycler_view)?.setHasFixedSize(true)
                if(listItems.size == 0) view?.findViewById<TextView>(R.id.error_text)?.visibility = View.VISIBLE

            }
        })
    }
}
