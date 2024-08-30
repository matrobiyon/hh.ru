package tj.example.effectivemobile.search.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import tj.example.effectivemobile.R
import tj.example.effectivemobile.databinding.RvItemButtonBinding
import tj.example.effectivemobile.databinding.RvItemVacanciesBinding
import tj.example.effectivemobile.search.data.remote.models.VacancyModel

class VacanciesAdapter :
    ListAdapter<VacancyModel, ViewHolder>(VacanciesDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return if (viewType == DATA_TYPE) VacanciesViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.rv_item_vacancies, parent, false)
        ) else ButtonViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.rv_item_button, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (holder is VacanciesViewHolder) holder.bind(getItem(position))
        else if (holder is ButtonViewHolder) holder.bind()
    }

    override fun getItemViewType(position: Int): Int {
        return if (getItem(position).button) BUTTON_TYPE else DATA_TYPE
    }

    inner class VacanciesViewHolder(itemView: View) : ViewHolder(itemView) {
        private val binding = RvItemVacanciesBinding.bind(itemView)

        fun bind(item: VacancyModel) {

        }
    }

    inner class ButtonViewHolder(itemView: View) : ViewHolder(itemView) {
        private val binding = RvItemButtonBinding.bind(itemView)

        fun bind() {
            binding.text.text =
                binding.root.context.getString(R.string.more_vacancy, ((currentList.size)-1).toString())
        }
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
    }
}