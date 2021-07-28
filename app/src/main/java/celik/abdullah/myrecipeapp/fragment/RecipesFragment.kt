package celik.abdullah.myrecipeapp.fragment

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import celik.abdullah.myrecipeapp.R
import celik.abdullah.myrecipeapp.adapter.RecipesAdapter
import celik.abdullah.myrecipeapp.adapter.RecipesLoadStateAdapter
import celik.abdullah.myrecipeapp.databinding.FragmentRecipesBinding
import celik.abdullah.myrecipeapp.viewmodel.RecipesFragmentViewModel
import celik.abdullah.myrecipeapp.viewmodel.SharedViewModel
import celik.abdullah.speeching.utils.EventObserver
import com.bumptech.glide.RequestManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

@AndroidEntryPoint
class RecipesFragment : Fragment() {

    private lateinit var binding : FragmentRecipesBinding
    private val recipesFragmentViewModel by viewModels<RecipesFragmentViewModel>()
    private lateinit var searchView: SearchView
    private val sharedViewModel by activityViewModels<SharedViewModel>()

    private lateinit var recipesAdapter: RecipesAdapter
    private val footerAdapter: RecipesLoadStateAdapter = RecipesLoadStateAdapter()
    private val navController by lazy{findNavController()}

    @Inject
    lateinit var rm: RequestManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRecipesBinding.inflate(inflater, container, false)
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            retry.setOnClickListener { recipesAdapter.retry() }
        }

        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        setupScrolling()
        setupObservers()
    }

    private fun setupObservers() {
        recipesFragmentViewModel.recipeClickedEvent.observe(viewLifecycleOwner, EventObserver{ recipeId ->
            sharedViewModel.currentRecipe(recipeId)
            navController.navigate(RecipesFragmentDirections.actionRecipesFragmentToRecipeFragment())
        })
    }


    private fun setupScrolling() =
        lifecycleScope.launch {
            recipesAdapter.loadStateFlow
                .distinctUntilChangedBy { it.refresh }
                .filter { it.refresh is LoadState.NotLoading }
                .collect { binding.recipes.scrollToPosition(0) }
        }

    private fun initAdapter() {
        recipesAdapter = RecipesAdapter(recipesFragmentViewModel,rm)
        binding.recipes.adapter = recipesAdapter.withLoadStateFooter(footer = footerAdapter)
        recipesAdapter.addLoadStateListener { loadState ->
            val isListEmpty = loadState.refresh is LoadState.NotLoading && recipesAdapter.itemCount == 0
            showEmptyList(isListEmpty)
            binding.recipes.isVisible = loadState.mediator?.refresh is LoadState.NotLoading || loadState.source.refresh is LoadState.NotLoading
            binding.progressBar.isVisible = loadState.mediator?.refresh is LoadState.Loading
            binding.retry.isVisible = loadState.refresh is LoadState.Error && recipesAdapter.itemCount == 0
        }
    }

    private fun showEmptyList(show: Boolean) =
        if (show) {
            binding.emptyList.visibility = View.VISIBLE
            binding.recipes.visibility = View.GONE
        } else {
            binding.emptyList.visibility = View.GONE
            binding.recipes.visibility = View.VISIBLE
        }

    private fun searchRecipes(query:String) =
        lifecycleScope.launch{
            recipesFragmentViewModel.searchRecipes(query).collectLatest {
                recipesAdapter.submitData(it)
            }
        }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search_menu, menu)
        val searchItem = menu.findItem(R.id.search)
        searchView = searchItem.actionView as SearchView

        searchView.apply {
            queryHint = "Search recipes ..."
            isIconified = true

            setOnQueryTextListener(object : SearchView.OnQueryTextListener{
                override fun onQueryTextSubmit(query: String): Boolean {
                    searchRecipes(query)
                    return false
                }
                override fun onQueryTextChange(newText: String): Boolean = false
            })
        }
    }
}