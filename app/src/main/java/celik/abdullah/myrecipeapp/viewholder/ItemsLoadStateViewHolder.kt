package celik.abdullah.myrecipeapp.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import celik.abdullah.myrecipeapp.R
import celik.abdullah.myrecipeapp.databinding.ItemsLoadStateFooterViewBinding


class ItemsLoadStateViewHolder(private val binding: ItemsLoadStateFooterViewBinding): RecyclerView.ViewHolder(binding.root) {

    fun bind(loadState: LoadState) {
        binding.progressBar.isVisible = loadState is LoadState.Loading
    }
    companion object {
        fun create(parent: ViewGroup): ItemsLoadStateViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.items_load_state_footer_view, parent, false)
            val binding = ItemsLoadStateFooterViewBinding.bind(view)
            return ItemsLoadStateViewHolder(binding)
        }
    }
}