package com.example.movie_ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movie_ui.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

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

        // Set up Top Five RecyclerView (you'll need to implement this part)
        binding.topFiveRecyclerView.layoutManager = LinearLayoutManager(context)
        // Add adapter here once MovieAdapter is set up

        // Set up Latest RecyclerView
        binding.latestRecyclerView.layoutManager = LinearLayoutManager(context)
        // Add adapter here once MovieAdapter is set up
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}