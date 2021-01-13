package com.example.movieapidemo.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapidemo.R
import com.example.movieapidemo.viewmodel.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.launch


class MovieFragment : Fragment() {
    companion object {
        fun newInstance() = MovieFragment()
    }

    private lateinit var factory: ViewModelFactory
    private lateinit var moviesAdapter: MovieAdapter
    private lateinit var viewModel: MovieViewModel
    private val editText: EditText? = view?.findViewById(R.id.tv_movie_search)
    private val moviesList: RecyclerView? = view?.findViewById(R.id.rv_movie)

    @FlowPreview
    @ExperimentalCoroutinesApi
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProvider(this, factory).get(MovieViewModel::class.java)

        if (savedInstanceState == null) {
            lifecycleScope.launch {
                viewModel.query.send("")
            }
        }
        editText?.afterTextChanged {
            lifecycleScope.launch {
                viewModel.query.send(it.toString())
            }
        }

        viewModel.searchResult.observe(viewLifecycleOwner, Observer { handlemoviesList(it) })
        viewModel.searchState.observe(viewLifecycleOwner, Observer { handleLoadingState(it) })
    }

    private fun handleLoadingState(statusSearch: StatusSearch) {
        when (statusSearch) {
            Loading -> {
               // binding.searchIcon.visibility = View.GONE
               // binding.searchProgress.visibility = View.VISIBLE
            }
            Ready -> {
               // binding.searchIcon.visibility = View.VISIBLE
               // binding.searchProgress.visibility = View.GONE
            }
        }
    }

    private fun handlemoviesList(moviesResult: MoviesResult) {
        when (moviesResult) {
            is ValidResult -> {
               // binding.moviesPlaceholder.visibility = View.GONE
                moviesList?.visibility = View.VISIBLE
                moviesAdapter.submitList(moviesResult.result)
            }
            is ErrorResult -> {
                moviesAdapter.submitList(emptyList())
                //binding.moviesPlaceholder.visibility = View.VISIBLE
                //binding.moviesList.visibility = View.GONE
               // binding.moviesPlaceholder.setText(R.string.search_error)
                Log.e(MovieFragment::class.java.name, "Something went wrong.", moviesResult.e)
            }
            is EmptyResult -> {
                moviesAdapter.submitList(emptyList())
                //binding.moviesPlaceholder.visibility = View.VISIBLE
                //binding.moviesList.visibility = View.GONE
               // binding.moviesPlaceholder.setText(R.string.empty_result)
            }
            is EmptyQuery -> {
                moviesAdapter.submitList(emptyList())
              //  binding.moviesPlaceholder.visibility = View.VISIBLE
              //  binding.moviesList.visibility = View.GONE
               // binding.moviesPlaceholder.setText(R.string.movies_placeholder)
            }
            is TerminalError -> {
                // Something wen't terribly wrong!
                println("Our Flow terminated unexpectedly, so we're bailing!")
                Toast.makeText(
                    activity,
                    getString(R.string.error_unknown_on_download),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    // This property is only valid between onCreateView and onDestroyView.
    // private val binding get() = _binding!!

    override fun onDestroyView() {
        super.onDestroyView()
        // _binding = null
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_movie, container, false)
    }


}
