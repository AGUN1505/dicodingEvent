package com.dicoding.dicodingevent.ui.upcoming

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.dicodingevent.data.adapter.FinishAdapter
import com.dicoding.dicodingevent.ui.detail.DetailActivity
import com.dicoding.dicodingevent.databinding.FragmentUpcomingBinding

class UpcomingFragment : Fragment() {

    private var _binding: FragmentUpcomingBinding? = null
    private val binding get() = _binding!!
    private val upcomingViewModel: UpcomingViewModel by viewModels {
        UpcomingViewModelFactory.getInstance()
    }
    private lateinit var finishAdapter: FinishAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUpcomingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        upcomingViewModel.getIsLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }

        upcomingViewModel.getErrorMessage.observe(viewLifecycleOwner) { message ->
            message?.let {
                Toast.makeText(requireContext(), it, Toast.LENGTH_LONG).show()
            }
        }

        setupRecyclerView()
        observeEvents()

        binding.searchViewUpcoming.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                upcomingViewModel.getEvent(query)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText.isNullOrEmpty()) {
                    upcomingViewModel.getEvent()
                }
                return false
            }
        })

        upcomingViewModel.getEvent()
    }

    private fun setupRecyclerView() {
        finishAdapter = FinishAdapter { event ->
            val intent = Intent(requireContext(), DetailActivity::class.java)
            intent.putExtra("EVENT_ID", event.id)
            intent.putExtra("Event_Selesai", true)
            startActivity(intent)
        }
        binding.rvEvent.apply {
            adapter = finishAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    private fun observeEvents() {
        upcomingViewModel.getEvent.observe(viewLifecycleOwner) { events ->
            finishAdapter.submitList(events)
            binding.progressBar.visibility = View.GONE
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}