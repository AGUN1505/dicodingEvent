package com.dicoding.dicodingevent.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.dicodingevent.data.adapter.FinishAdapter
import com.dicoding.dicodingevent.data.adapter.UpcomingAdapter
import com.dicoding.dicodingevent.databinding.FragmentHomeBinding
import com.dicoding.dicodingevent.ui.detail.DetailActivity

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var upcomingAdapter: UpcomingAdapter
    private lateinit var finishAdapter: FinishAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        homeViewModel = ViewModelProvider(this)[HomeViewModel::class.java]

        homeViewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }

        homeViewModel.errorMessage.observe(viewLifecycleOwner) {message ->
            message?.let {
                Toast.makeText(requireContext(), it, Toast.LENGTH_LONG).show()
            }}

        upcomingRv()
        finishRv()
        observeEvent()

        homeViewModel.getEventHome()
        homeViewModel.findFinishHome()
    }

    private fun observeEvent() {
        homeViewModel.event.observe(viewLifecycleOwner) { events ->
            upcomingAdapter.submitList(events.take(5))
            binding.progressBar.visibility = View.GONE
        }

        homeViewModel.finish.observe(viewLifecycleOwner) { events ->
            finishAdapter.submitList(events.take(5))
            binding.progressBar.visibility = View.GONE
        }
    }

    private fun finishRv() {
        finishAdapter = FinishAdapter { event ->
            val intent = Intent(requireContext(), DetailActivity::class.java)
            intent.putExtra("EVENT_ID", event.id)
            intent.putExtra("Event_Selesai", true)
            startActivity(intent)
        }
        binding.rvHomeFinish.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = finishAdapter
        }
    }

    private fun upcomingRv() {
        upcomingAdapter = UpcomingAdapter { event ->
            val intent = Intent(requireContext(), DetailActivity::class.java)
            intent.putExtra("EVENT_ID", event.id)
            intent.putExtra("Event_Selesai", true)
            startActivity(intent)
        }
        binding.rvUpcoming.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = upcomingAdapter
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