package tj.example.effectivemobile.search.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import tj.example.effectivemobile.R
import tj.example.effectivemobile.databinding.RvItemButtonBinding
import tj.example.effectivemobile.databinding.RvItemVacanciesBinding
import tj.example.effectivemobile.search.data.remote.models.Vacancy
import tj.example.effectivemobile.search.data.remote.models.VacancyModel

class VacanciesAdapter(
    private val clickedMoreVacancyButton: () -> Unit,
    private val clickedVacancy: (id : String) -> Unit,
    private val onLikedClicked: (prevStatus : Boolean, id : String) -> Unit,
) :
    ListAdapter<VacancyModel, ViewHolder>(VacanciesDiffUtil()) {

    inner class VacanciesViewHolder(itemView: View) : ViewHolder(itemView) {
        private val binding = RvItemVacanciesBinding.bind(itemView)

        fun bind(item: Vacancy) {

            binding.apply {

                button.setOnClickListener{}
                root.setOnClickListener {
                    clickedVacancy(currentList[adapterPosition].data?.id?:"")
                }
                //Observe people
                peopleObserve.text = binding.root.context.getString(
                    R.string.observing_people, item.lookingNumber.toString(),
                    getPersonSklonenie(item.lookingNumber)
                )
                peopleObserve.isVisible = item.lookingNumber > 0

                // Liked icon
                if (item.isFavorite)
                    binding.likedIcon.setImageResource(R.drawable.ic_liked_yes)
                else binding.likedIcon.setImageResource(R.drawable.ic_liked_no)

                binding.likedIcon.setOnClickListener {
                        onLikedClicked(item.isFavorite,item.id)
                }

                //Title
                title.text = item.title

                //City
                city.text = item.address.town

                //Company
                companyName.text = item.company

                //Experience
                experience.text = item.experience.previewText
                publishDate.text = formatPublishedDate(item.publishedDate)


            }
        }
    }

    inner class ButtonViewHolder(itemView: View) : ViewHolder(itemView) {
        private val binding = RvItemButtonBinding.bind(itemView)

        fun bind() {
            binding.text.text =
                binding.root.context.getString(
                    R.string.more_vacancy, (getVacanciesSklonenie(currentList.size - 1))
                )
            binding.root.setOnClickListener {
                clickedMoreVacancyButton()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return if (viewType == DATA_TYPE) VacanciesViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.rv_item_vacancies, parent, false)
        ) else ButtonViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.rv_item_button, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (holder is VacanciesViewHolder) holder.bind(getItem(position).data!!)
        else if (holder is ButtonViewHolder) holder.bind()
    }

    override fun getItemViewType(position: Int): Int {
        return if (getItem(position).button) BUTTON_TYPE else DATA_TYPE
    }

    companion object {
        class VacanciesDiffUtil : DiffUtil.ItemCallback<VacancyModel>() {

            override fun areItemsTheSame(oldItem: VacancyModel, newItem: VacancyModel): Boolean {
                return oldItem.data?.id == newItem.data?.id
            }

            override fun areContentsTheSame(oldItem: VacancyModel, newItem: VacancyModel): Boolean {
                return oldItem.data == newItem.data
            }

        }

        const val DATA_TYPE = 1
        const val BUTTON_TYPE = 2

        fun getVacanciesSklonenie(count: Int): String {
            return count.toString() + " " + when {
                count % 10 == 1 && count != 11 -> "вакансия"
                count % 10 in 2..4 && count !in 12..14 -> "вакансии"
                else -> "вакансий"
            }
        }
        fun getPersonSklonenie(lookingNumber: Int): String {
            val lastDigit = lookingNumber % 10
            val lastTwoDigits = lookingNumber % 100

            return when {
                lastDigit == 1 && lastTwoDigits != 11 -> "человек"
                lastDigit in 2..4 && lastTwoDigits !in 12..14 -> "человека"
                else -> "человек"
            }
        }
    }


}

fun formatPublishedDate(publishedDate: String): String {
    val month = publishedDate.substringAfter("-").substringBeforeLast("-").toInt()
    val day = publishedDate.substringAfterLast("-").toInt()

    val months = arrayOf(
        "", "января", "февраля", "марта", "апреля", "мая", "июня",
        "июля", "августа", "сентября", "октября", "ноября", "декабря"
    )

    val monthName = when (day % 10) {
        1 -> if (day % 100 != 11) months[month] else months[month]
        2, 3, 4 -> if (day % 100 !in 12..14) months[month] else months[month]
        else -> months[month]
    }

    return "Опубликовано ${day} $monthName"
}