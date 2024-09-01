package tj.example.effectivemobile.search.presentation.screen

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.chip.Chip
import dagger.hilt.android.AndroidEntryPoint
import tj.example.effectivemobile.R
import tj.example.effectivemobile.databinding.ChipItemBinding
import tj.example.effectivemobile.databinding.FragmentDetailsBinding
import tj.example.effectivemobile.search.presentation.adapter.VacanciesAdapter
import tj.example.effectivemobile.search.presentation.screen.bottomsheet.BottomSheetFragment
import tj.example.effectivemobile.search.presentation.viewModel.DetailViewModel
import java.util.Locale

@AndroidEntryPoint
class DetailsFragment : Fragment() {

    private lateinit var binding: FragmentDetailsBinding
    private val args by navArgs<DetailsFragmentArgs>()
    private val viewModel: DetailViewModel by viewModels()
    private lateinit var bottomSheetFragment: BottomSheetFragment

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentDetailsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getDataById(args.id).observe(viewLifecycleOwner) { res ->

        }

        observeLiveData()
        binding.back.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun observeLiveData() {
        viewModel.getDataById(args.id).observe(viewLifecycleOwner) { list ->

            val res = list.first()

            if (res.isFavorite) binding.likedIcon.setImageResource(R.drawable.ic_liked_yes) else binding.likedIcon.setImageResource(
                R.drawable.ic_liked_no
            )

            binding.likedIcon.setOnClickListener {
                viewModel.changeStatus(res.isFavorite, res.id)
            }

            binding.apply {
                title.text = res.title
                salary.text = res.salary.full
                experience.text = "Требуемый опыт: " + res.experience.text
                schedule.text = res.schedules.joinToString { s ->
                    s.replaceFirstChar {
                        if (it.isLowerCase()) it.titlecase(
                            Locale.ROOT
                        ) else it.toString()
                    }
                }

                appliesContainer.isVisible = res.appliedNumber > 0
                appliedPeople.text =
                    "${res.appliedNumber} ${VacanciesAdapter.getPersonSklonenie(res.appliedNumber)} уже откликнулось"

                observingPeopleContainer.isVisible = res.lookingNumber > 0
                observingPeople.text =
                    "${res.lookingNumber} ${VacanciesAdapter.getPersonSklonenie(res.lookingNumber)} сейчас смотрят"

                company.text = res.company
                address.text =
                    "${res.address.town}, ${res.address.street}, ${res.address.house}"

                description.isVisible = res.description != null
                description.text = res.description

                responsibilities.text = res.responsibilities

                res.questions.forEach { questions ->
                    val chip = createChip(questions)
                    chipGroup.addView(chip)
                }

                applyButton.setOnClickListener {
                    bottomSheetFragment = BottomSheetFragment()
                    bottomSheetFragment.show(parentFragmentManager, BOTTOM_SHEET)
                }

            }

        }
    }

    fun createChip(question: String): Chip {
        val chip = ChipItemBinding.inflate(layoutInflater).root
        chip.text = question
        chip.setOnClickListener {
            bottomSheetFragment = BottomSheetFragment(question)
            bottomSheetFragment.show(parentFragmentManager, BOTTOM_SHEET)
        }
        return chip
    }

    companion object {
        const val BOTTOM_SHEET = "bottom_sheet"
    }

}