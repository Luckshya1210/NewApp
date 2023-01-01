package com.example.newsdaily

import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.browser.customtabs.CustomTabsIntent
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest


class MainActivity : AppCompatActivity(), NewsItemClicked {
    private lateinit var mAdapter: NewsListAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var recyclerView=findViewById(R.id.recycleView) as RecyclerView;
        recyclerView.layoutManager=LinearLayoutManager(this);
         fetchdata();
        mAdapter =NewsListAdapter(this);
        recyclerView.adapter=mAdapter

    }
    private fun fetchdata(){
        val url="https://saurav.tech/NewsAPI/everything/cnn.json"
        val jsonObjectRequest= JsonObjectRequest(
            Request.Method.GET,
            url,
            null,
            Response.Listener{
                val jsonarr=it.getJSONArray("articles")
                val newsarr=ArrayList<News>()
                for(i in 0 until jsonarr.length()){
                    val obj=jsonarr.getJSONObject(i);
                    val news=News(
                        obj.getString("title"),
                        obj.getString("author"),
                        obj.getString("url"),
                        obj.getString("urlToImage")
                    )
                    newsarr.add(news)
                }
                mAdapter.updateNews(newsarr)
            },
            Response.ErrorListener {

            }

        )

        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)
    }

    override fun onItemClick(item: News) {
        val builder = CustomTabsIntent.Builder()
        val customTabsIntent = builder.build()
        customTabsIntent.launchUrl(this, Uri.parse(item.url))
    }
}