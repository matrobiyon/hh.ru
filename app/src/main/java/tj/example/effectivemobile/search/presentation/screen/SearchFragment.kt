package tj.example.effectivemobile.search.presentation.screen

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import tj.example.effectivemobile.databinding.FragmentSearchBinding
import tj.example.effectivemobile.search.data.remote.models.VacancyModel
import tj.example.effectivemobile.search.presentation.adapter.OffersAdapter
import tj.example.effectivemobile.search.presentation.adapter.VacanciesAdapter
import tj.example.effectivemobile.search.presentation.viewModel.SearchViewModel


@AndroidEntryPoint
class SearchFragment : Fragment() {

    private lateinit var binding: FragmentSearchBinding
    private val viewModel: SearchViewModel by viewModels()
    private val offerAdapter = OffersAdapter()
    private val vacanciesAdapter = VacanciesAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSearchBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvOffers.adapter = offerAdapter
        binding.rvVacancies.adapter = vacanciesAdapter

        observeViewModel()

    }

    private fun observeViewModel() {

        viewModel.offers.observe(viewLifecycleOwner) { res ->
            Log.d("TAG", "observeViewModel: $res")
            offerAdapter.submitList(res)
        }

        viewModel.vacancies.observe(viewLifecycleOwner) { res ->
            val newList = mutableListOf<VacancyModel>()
            newList.addAll(res.map { VacancyModel(data = it) })
            newList.add(VacancyModel(data = null, button = true))
            vacanciesAdapter.submitList(newList)
        }

        viewModel.isLoading.observe(viewLifecycleOwner) { res ->
            binding.progressBar.isVisible = res
        }

    }

}

