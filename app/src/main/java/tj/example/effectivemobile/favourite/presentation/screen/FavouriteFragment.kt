package tj.example.effectivemobile.favourite.presentation.screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import tj.example.effectivemobile.databinding.FragmentFavouriteBinding
import tj.example.effectivemobile.favourite.presentation.viewModel.FavouriteViewModel
import tj.example.effectivemobile.search.data.remote.models.VacancyModel
import tj.example.effectivemobile.search.presentation.adapter.VacanciesAdapter

@AndroidEntryPoint
class FavouriteFragment : Fragment() {

    private lateinit var binding: FragmentFavouriteBinding
    private lateinit var adapter: VacanciesAdapter
    private val viewModel by viewModels<FavouriteViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentFavouriteBinding.inflate(layoutInflater)
        adapter = VacanciesAdapter(clickedVacancy = {},
            clickedMoreVacancyButton = {},
            onLikedClicked = { status, id ->
                viewModel.changeLike(status, id)
            })
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvVacancies.adapter = adapter

        viewModel.getFavourites().observe(viewLifecycleOwner) { res ->
            binding.vacancyCount.text =
                VacanciesAdapter.getVacanciesSklonenie(res.count { it.isFavorite })
            binding.vacancyCount.isVisible = true
            adapter.submitList(res.filter { it.isFavorite }.map { VacancyModel(data = it) })
        }

    }

}