package mad.app.madandroidtestsolutions

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import mad.app.madandroidtestsolutions.databinding.LineItemBinding
import mad.app.plptest.CategoryRootQuery

class CategoryAdapter(private val categoryList: List<CategoryRootQuery.Child?>, private val productListPageViewModel: ProductListPageViewModel) : RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {
    inner class CategoryViewHolder(val binding: LineItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val binding = LineItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CategoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val currentProduct = categoryList[position]
        holder.binding.categoryTextView.text = currentProduct?.name
        holder.itemView.setOnClickListener {
            productListPageViewModel.categoryId = currentProduct?.uid ?: ""
            holder.itemView.findNavController().navigate(R.id.action_categoryListPageFragment_to_productListPageFragment)
        }
    }

    override fun getItemCount(): Int = categoryList.size
}