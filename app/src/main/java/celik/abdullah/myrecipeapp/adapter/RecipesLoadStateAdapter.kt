package celik.abdullah.myrecipeapp.adapter

import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import celik.abdullah.myrecipeapp.viewholder.ItemsLoadStateViewHolder

class RecipesLoadStateAdapter() : LoadStateAdapter<ItemsLoadStateViewHolder>() {

    override fun onBindViewHolder(holder: ItemsLoadStateViewHolder, loadState: LoadState) =
        holder.bind(loadState)
    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): ItemsLoadStateViewHolder =
        ItemsLoadStateViewHolder.create(parent)
}