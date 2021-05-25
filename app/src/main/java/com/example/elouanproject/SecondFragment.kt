package com.example.elouanproject

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
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
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_second, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getDealsServer()
        view.findViewById<Button>(R.id.button_second).setOnClickListener {
            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
        }
    }

    private fun getDealsServer(){
        val retrofitBuilder = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://www.cheapshark.com")
            .build()
            .create(ApiConnDeals::class.java)

        val retrofitData = retrofitBuilder.getDealsItem()

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
                    list.add(myData)
                    myStringBuilder.append(myData.title)
                    myStringBuilder.append(" at only ")
                    myStringBuilder.append(myData.salePrice)
                    myStringBuilder.append("\n")
                }
                //val textView = view?.findViewById<TextView>(R.id.text_api_result)
                //textView?.setText(myStringBuilder)?.toString()

                view?.findViewById<RecyclerView>(R.id.recycler_view)?.adapter = RecyclerItemsAdapter(list)
                view?.findViewById<RecyclerView>(R.id.recycler_view)?.layoutManager = LinearLayoutManager(activity!!)
                view?.findViewById<RecyclerView>(R.id.recycler_view)?.setHasFixedSize(true)
            }
        })
    }
}
