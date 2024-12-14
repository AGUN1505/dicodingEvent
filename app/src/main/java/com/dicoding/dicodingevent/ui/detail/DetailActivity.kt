package com.dicoding.dicodingevent.ui.detail

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.HtmlCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.dicoding.dicodingevent.R
import com.dicoding.dicodingevent.data.local.entity.EventEntity
import com.dicoding.dicodingevent.databinding.ActivityDetailBinding
import com.dicoding.dicodingevent.ui.favorite.FavoriteViewModel

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private val detailViewModel: FavoriteViewModel by viewModels {
        DetailViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val idEvent = intent.getIntExtra("EVENT_ID", 0)
        val finishEvent = intent.getBooleanExtra("FINISH_EVENT", false)
        getDetailEvent(idEvent, finishEvent)
        setupFavoriteButton(idEvent.toString())

        supportActionBar?.title = "Detail Event"
    }

    @Suppress("NAME_SHADOWING")
    private fun setupFavoriteButton(idEvent: String) {
        detailViewModel.getFavoriteEvent(idEvent).observe(this) { event ->
            val isFavorite = event != null
            binding.favoriteActionButton.setImageResource(
                if (isFavorite) R.drawable.baseline_favorite_24 else R.drawable.baseline_favorite_border_24
            )

            binding.favoriteActionButton.setOnClickListener {
                if (isFavorite) {
                    detailViewModel.deleteFavoriteById(idEvent)
                } else {
                    val event = detailViewModel.detailEvent.value
                    if (event?.name != null && event.mediaCover != null) {
                        detailViewModel.insertFavorite(
                            EventEntity(
                                idEvent,
                                event.name,
                                event.mediaCover
                            )
                        )
                        Toast.makeText(this, "Added To favorite", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
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




