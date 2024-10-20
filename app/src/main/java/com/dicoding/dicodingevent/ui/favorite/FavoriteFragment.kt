package com.dicoding.dicodingevent.ui.favorite

import android.content.Intent
import androidx.fragment.app.viewModels
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.dicodingevent.data.adapter.FavoriteAdapter
import com.dicoding.dicodingevent.databinding.FragmentFavoriteBinding
import com.dicoding.dicodingevent.ui.detail.DetailActivity

class FavoriteFragment : Fragment() {
    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!
    private val favoriteViewModel: FavoriteViewModel by viewModels {
        FavoriteViewModelFactory.getInstance(requireActivity())
    }
    private lateinit var favAdapter: FavoriteAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        favoriteViewModel.getIsLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }

        favRv()
        observeFav()
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar2.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun favRv() {
        favAdapter = FavoriteAdapter(
            { event ->
                val intent = Intent(requireContext(), DetailActivity::class.java)
                intent.putExtra("EVENT_ID", event.id.toInt())
                intent.putExtra("Event_Selesai", true)
                startActivity(intent)
            },
            { event ->
                favoriteViewModel.deleteFavoriteById(event.id)
            }
        )
        binding.rvFavorite.adapter = favAdapter
        binding.rvFavorite.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun observeFav() {
        favoriteViewModel.getAllFavEvent().observe(viewLifecycleOwner) { favorite ->
            favAdapter.submitList(favorite)
            binding.progressBar2.visibility = View.GONE
        }
    }

    override fun onDestroyView() {
        Log.d("FavoriteFragment", "onDestroyView called")
        super.onDestroyView()
        _binding = null
    }
}


