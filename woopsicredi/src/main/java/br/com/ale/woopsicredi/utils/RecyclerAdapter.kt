package br.com.ale.woopsicredi.utils

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.com.ale.woopsicredi.R
import br.com.ale.woopsicredi.data.Event
import br.com.ale.woopsicredi.utils.Constants.Companion.IMAGE_NOT_FOUND
import com.bumptech.glide.Glide

class RecyclerAdapter (private val context: Context, private val events: List<Event>, private val itemListener: EventItemListener): RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        val titulo: TextView = itemView.findViewById(R.id.titulo)
        val imageView: ImageView = itemView.findViewById(R.id.imageView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val layoutId = R.layout.main_list_item
        val view = inflater.inflate(layoutId, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val event = events[position]
        with(holder) {
            titulo.text = event.title
            Glide.with(context)
                .load(event.image)
                .error(IMAGE_NOT_FOUND)
                .into(imageView)
            holder.itemView.setOnClickListener{
                itemListener.onEventItemClick(event)
            }
            }
    }

    override fun getItemCount(): Int {
       return events.size
    }

    interface EventItemListener {
        fun onEventItemClick(event: Event)
    }
}