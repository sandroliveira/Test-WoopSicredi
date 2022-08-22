package br.com.ale.woopsicredi.ui

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import br.com.ale.woopsicredi.R
import br.com.ale.woopsicredi.data.Event
import br.com.ale.woopsicredi.data.User
import br.com.ale.woopsicredi.utils.Constants
import br.com.ale.woopsicredi.utils.Constants.Companion.BTN_ENVIAR
import br.com.ale.woopsicredi.utils.Constants.Companion.EVENT
import br.com.ale.woopsicredi.utils.Constants.Companion.MSG_CHECK_IN
import br.com.ale.woopsicredi.utils.Constants.Companion.TYPE_SHARE
import br.com.ale.woopsicredi.utils.createTextDescription
import com.bumptech.glide.Glide
import com.google.android.material.floatingactionbutton.FloatingActionButton

class DetailsActivity : AppCompatActivity() {
    private lateinit var event: Event
    private var hashMap = HashMap<String, Event>()
    private lateinit var textContent: TextView
    private lateinit var image: ImageView
    private lateinit var fabShare: FloatingActionButton
    private lateinit var fabCheckIn: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        initViews()
        getExtra()
        setInfoViews()
        configShareData()
        configCheckIn()
    }

    private fun initViews() {
        textContent = findViewById(R.id.titulo)
        image = findViewById(R.id.imageView)
        fabShare = findViewById(R.id.fab_share)
        fabCheckIn = findViewById(R.id.fab_check_in)
    }

    private fun getExtra() {
      val extras = intent.extras
        if (extras != null) {
            hashMap = extras.get(EVENT) as HashMap<String, Event>
            event = hashMap[EVENT] as Event
        }
    }

    private fun setInfoViews() {
        val stringBuilder = StringBuilder()
        textContent.text = stringBuilder.createTextDescription(event)
        Glide.with(this)
            .load(event.image)
            .error(Constants.IMAGE_NOT_FOUND)
            .into(image)

    }

    private fun configShareData() {
        fabShare.setOnClickListener {
            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, textContent.text)
                type = TYPE_SHARE
            }
            val shareIntent = Intent.createChooser(sendIntent, null)
            startActivity(shareIntent)
        }
    }

    private fun configCheckIn() {
        fabCheckIn.setOnClickListener {
            createDialog()
        }
    }

    private fun createDialog() {
        val model: DetailViewModel by viewModels()
        this.let {
            val builder = AlertDialog.Builder(it)
            val inflater = this.layoutInflater;
            val view = inflater.inflate(R.layout.dialog_layout, null)
            builder.setView(view)
                .setTitle(MSG_CHECK_IN)
                .setPositiveButton(BTN_ENVIAR) { _, _ ->
                    model.sendPost(this, User(event.id,
                        view.findViewById<EditText>(R.id.username).text.toString(),
                        view.findViewById<EditText>(R.id.email).text.toString()))
                }.create().show()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        finish()
    }
}