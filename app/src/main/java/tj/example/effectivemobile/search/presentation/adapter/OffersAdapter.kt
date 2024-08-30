package tj.example.effectivemobile.search.presentation.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import tj.example.effectivemobile.R
import tj.example.effectivemobile.databinding.RvItemOffersBinding
import tj.example.effectivemobile.search.data.remote.models.Offer

class OffersAdapter : ListAdapter<Offer, OffersAdapter.OffersViewHolder>(OfferDiffUtil()) {

    inner class OffersViewHolder(itemView: View) : ViewHolder(itemView) {

        private val binding = RvItemOffersBinding.bind(itemView)
        private val context = binding.root.context

        fun bind(item: Offer) {

            binding.icon.isVisible = true
            binding.iconParent.isVisible = true

            when (item.id) {
                "near_vacancies" -> {
                    binding.iconParent.setCardBackgroundColor(context.getColor(R.color.dark_blue))
                    binding.icon.isVisible = false
                }

                "level_up_resume" -> {
                    binding.icon.setImageResource(R.drawable.ic_star)
                }

                "temporary_job" -> {
                    binding.icon.setImageResource(R.drawable.ic_temp_job)
                }

                else -> {
                    binding.icon.isVisible = false
                    binding.iconParent.isVisible = false
                }
            }

            binding.text.text = item.title

            if (item.button?.text?.isNotEmpty() == true) {
                binding.button.isVisible = true
                binding.button.text = item.button.text
            } else binding.button.isVisible = false

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OffersViewHolder {
        return OffersViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.rv_item_offers, parent, false)
        )
    }

    override fun onBindViewHolder(holder: OffersViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    companion object {
        class OfferDiffUtil : DiffUtil.ItemCallback<Offer>() {
            override fun areItemsTheSame(oldItem: Offer, newItem: Offer): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Offer, newItem: Offer): Boolean {
                return oldItem == newItem
            }

        }
    }
}