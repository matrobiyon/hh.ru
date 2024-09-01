package tj.example.effectivemobile.search.presentation.screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import tj.example.effectivemobile.R
import tj.example.effectivemobile.databinding.FragmentSearchBinding
import tj.example.effectivemobile.search.data.remote.models.Vacancy
import tj.example.effectivemobile.search.data.remote.models.VacancyModel
import tj.example.effectivemobile.search.presentation.adapter.OffersAdapter
import tj.example.effectivemobile.search.presentation.adapter.VacanciesAdapter
import tj.example.effectivemobile.search.presentation.viewModel.SearchViewModel


@AndroidEntryPoint
class SearchFragment : Fragment() {

    private lateinit var binding: FragmentSearchBinding
    private val viewModel: SearchViewModel by viewModels()
    private val offerAdapter = OffersAdapter()

    private lateinit var vacanciesAdapter: VacanciesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSearchBinding.inflate(layoutInflater)
        vacanciesAdapter = VacanciesAdapter(clickedMoreVacancyButton = {
            viewModel.isExpanded.value = true
        }, clickedVacancy = { id ->
            val actions = SearchFragmentDirections.actionSearchFragmentToDetailsFragment(id)
            findNavController().navigate(actions)
        }, onLikedClicked = { prevStatus: Boolean, id: String ->
            viewModel.changeStatus(prevStatus, id)
        })
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvOffers.adapter = offerAdapter
        binding.rvVacancies.adapter = vacanciesAdapter

        observeViewModel()
        onClickListener()

    }

    private fun onClickListener() {
        binding.apply {
            tabIcon.setOnClickListener {
                if (viewModel.isExpanded.value == true) {
                    viewModel.isExpanded.value = false
                }
            }
        }
    }

    private fun observeViewModel() {

        viewModel.apply {

            getVacancies().observe(viewLifecycleOwner) { res ->
                viewModel.saveVacanciesInViewModel(res)
                if (res.isEmpty()) viewModel.getDataRemotely()
                else if (viewModel.isExpanded.value == false || viewModel.isExpanded.value == null) vacanciesAdapter.submitList(
                    addButtonToList(viewModel.vacancies)
                ) else vacanciesAdapter.submitList(viewModel.vacancies.map { VacancyModel(it) })

                binding.vacancyCount.text = VacanciesAdapter.getVacanciesSklonenie(res.count())
            }

            getOffer().observe(viewLifecycleOwner) { res ->
                offerAdapter.submitList(res)
            }

            isLoading.observe(viewLifecycleOwner) { res ->
                binding.progressBar.isVisible = res
            }

            isExpanded.observe(viewLifecycleOwner) { res ->
                if (res == true) {
                    vacanciesAdapter.submitList(viewModel.vacancies.map {
                        VacancyModel(
                            it
                        )
                    })
                    binding.rvOffers.isVisible = false
                    binding.extraInfo.isVisible = true
                    binding.tabIcon.setImageResource(R.drawable.ic_back)
                    binding.rvOffers.isVisible = false
                    binding.vacanciesTitle.isVisible = false

                } else if (res == false) {
                    binding.extraInfo.isVisible = false
                    binding.rvOffers.isVisible = true
                    binding.tabIcon.setImageResource(R.drawable.ic_search)
                    binding.rvOffers.isVisible = true
                    binding.vacanciesTitle.isVisible = true
                    vacanciesAdapter.submitList(
                        addButtonToList(
                            viewModel.vacancies
                        )
                    )
                }
            }

        }

    }

}

fun addButtonToList(list: List<Vacancy>): List<VacancyModel> {
    val newList = mutableListOf<VacancyModel>()
    newList.addAll(list.take(3).map { VacancyModel(data = it) })
    newList.add(VacancyModel(data = null, button = true))
    return newList
}

