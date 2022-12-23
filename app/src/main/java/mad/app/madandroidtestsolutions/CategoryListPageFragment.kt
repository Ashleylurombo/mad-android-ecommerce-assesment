package mad.app.madandroidtestsolutions

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding
import mad.app.madandroidtestsolutions.databinding.CategoryListPageFragmentBinding

class CategoryListPageFragment : MobileShoppingBaseFragment(R.layout.category_list_page_fragment) {
    private val binding by viewBinding(CategoryListPageFragmentBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fetchAndObserveCategories()
    }

    private fun fetchAndObserveCategories() {
        with(productListPageViewModel) {
            fetchCategories()
            categoriesLiveData.observe(viewLifecycleOwner) {
                val categoryAdapter = it?.let { categoryList -> CategoryAdapter(categoryList, productListPageViewModel) }
                binding.categoriesRecyclerView.apply {
                    layoutManager = LinearLayoutManager(requireContext())
                    adapter = categoryAdapter
                }
            }
        }
    }
}