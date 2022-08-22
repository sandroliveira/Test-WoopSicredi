package br.com.ale.woopsicredi.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.ale.woopsicredi.R
import br.com.ale.woopsicredi.utils.RecyclerAdapter
import br.com.ale.woopsicredi.data.Event
import br.com.ale.woopsicredi.utils.Constants.Companion.EVENT


class MainActivity : AppCompatActivity(), RecyclerAdapter.EventItemListener {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: RecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)


        val model: MainViewModel by viewModels()
        model.eventData.observe(this) {
            adapter = RecyclerAdapter(this, it, this)
            recyclerView.adapter = adapter
        }
    }

    override fun onEventItemClick(event: Event) {
        val eventMap = HashMap<String, Event>()
        eventMap[EVENT] = event
        val intent = Intent(this, DetailsActivity::class.java)
        intent.putExtra(EVENT, eventMap)
        startActivity(intent)
    }
}