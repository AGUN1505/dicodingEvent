package com.dicoding.dicodingevent.ui.detail

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.HtmlCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.dicoding.dicodingevent.R
import com.dicoding.dicodingevent.databinding.ActivityDetailBinding


class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private lateinit var detailViewModel: DetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        detailViewModel = ViewModelProvider(this)[DetailViewModel::class.java]
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val idEvent = intent.getIntExtra("EVENT_ID", 0)
        val finishEvent = intent.getBooleanExtra("FINISH_EVENT", false)
        getDetailEvent(idEvent, finishEvent)

        supportActionBar?.title = "Detail Event"
    }

    @SuppressLint("SetTextI18n")
    private fun getDetailEvent(idEvent: Int, finishEvent: Boolean) {
        binding.progressBar.visibility = View.VISIBLE
        detailViewModel.detailEvent.observe(this) { event ->
            event?.let {
                binding.progressBar.visibility = View.GONE
                binding.eventName.text = it.name
                binding.eventSummary.text = it.summary
                binding.description.text = HtmlCompat.fromHtml(
                    it.description.toString(), HtmlCompat.FROM_HTML_MODE_LEGACY
                )
                binding.operator.text = "Operator:\n ${it.ownerName}"
                binding.eventTime.text = "Event Time:\n ${it.beginTime}"

                if (finishEvent) {
                    binding.remainingQuota.text = "Event\n has\n finished"
                } else {
                    val remainingQuota = (it.quota ?: 0) - (it.registrants ?: 0)
                    binding.remainingQuota.text = "Remaining Quota:\n $remainingQuota"
                }

                binding.browser.setOnClickListener {
                    val link = event.link
                    if (!link.isNullOrEmpty()) {
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(link))
                        startActivity(intent)
                    }
                }

                Glide.with(this@DetailActivity).load(it.mediaCover).into(binding.mediaCover)
            }
        }
        detailViewModel.getDetailEvent(idEvent)
    }
}




