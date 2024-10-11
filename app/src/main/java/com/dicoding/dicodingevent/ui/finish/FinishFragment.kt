package com.dicoding.dicodingevent.ui.finish

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.dicodingevent.ui.detail.DetailActivity
import com.dicoding.dicodingevent.data.adapter.FinishAdapter
import com.dicoding.dicodingevent.databinding.FragmentFinishBinding
import android.widget.Toast

class FinishFragment : Fragment() {

    private var _binding: FragmentFinishBinding? = null
    private val binding get() = _binding!!
    private lateinit var finishViewModel: FinishViewModel
    private lateinit var finishAdapter: FinishAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFinishBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        finishViewModel = ViewModelProvider(this)[FinishViewModel::class.java]

        finishViewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }

        finishViewModel.errorMessage.observe(viewLifecycleOwner) { message ->
            binding.progressBar.visibility = View.GONE
            message?.let {
                Toast.makeText(requireContext(), it, Toast.LENGTH_LONG).show()
            }
        }

        setupRecyclerView()
        observeEvent()

        binding.searchViewFinished.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                finishViewModel.findFinish(query)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })

        finishViewModel.findFinish()

    }

    private fun setupRecyclerView() {
        finishAdapter = FinishAdapter { event ->
            val intent = Intent(requireContext(), DetailActivity::class.java)
            intent.putExtra("EVENT_ID", event.id)
            intent.putExtra("Event_Selesai", true)
            startActivity(intent)
        }
        binding.rvFinish.apply {
            adapter = finishAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    private fun observeEvent() {
        finishViewModel.finish.observe(viewLifecycleOwner) { events ->
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
