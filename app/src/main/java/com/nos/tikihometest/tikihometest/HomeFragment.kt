package com.nos.tikihometest.tikihometest

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import java.net.URL


class HomeFragment : Fragment(), FetchKeywordListener {
    override fun onStartFetchKeyword() {
        progressBar.visibility = View.VISIBLE
        retryButton.visibility = View.GONE
    }

    override fun onFailedFetchKeyword() {
        Toast.makeText(context, getString(R.string.failed_fetch), Toast.LENGTH_SHORT).show()
        progressBar.visibility = View.GONE
        retryButton.visibility = View.VISIBLE

    }

    override fun onSuccessFetchKeyword(dataSet: ArrayList<String>) {
        viewAdapter.setDataset(dataSet)
        progressBar.visibility = View.GONE
        retryButton.visibility = View.GONE

    }


    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: KeywordAdapter
    private lateinit var viewManager: LinearLayoutManager

    private lateinit var progressBar: ProgressBar
    private lateinit var retryButton: Button

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        progressBar = view.findViewById<ProgressBar>(R.id.progress_bar)
        retryButton = view.findViewById<Button>(R.id.retry_btn)
        retryButton.setOnClickListener {
            fetchKeyword()
        }
        viewManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        viewAdapter = KeywordAdapter(context = context)
        val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view).apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }
        fetchKeyword()
        return view
    }

    private fun fetchKeyword() {
        val url = URL("https://gist.githubusercontent.com/talenguyen/38b790795722e7d7b1b5db051c5786e5/raw/63380022f5f0c9a100f51a1e30887ca494c3326e/keywords.json")
        FetchKeywordAsyncTask(this).execute(url)
    }


}
