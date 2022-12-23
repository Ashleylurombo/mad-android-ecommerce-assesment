package mad.app.madandroidtestsolutions

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import mad.app.madandroidtestsolutions.databinding.ProductItemBinding
import mad.app.plptest.CategoryQuery

class ProductAdapter(private val productList:List<CategoryQuery.Item?>, private val activity: Activity, private val productClickedListener: OnProductClickedListener): RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding = ProductItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val currentProduct = productList[position]?.productListFragment
        with(holder.binding) {
            productNameTextView.text = currentProduct?.name
            productCategoryTextView.text = currentProduct?.brand

            val priceTag = "R${currentProduct?.price_range?.priceRangeFragment?.minimum_price?.final_price?.value}"
            productPriceTextView.text = priceTag

            var imageResource = ""
            if (currentProduct?.mp_label_data?.isNotEmpty() == true) {
                imageResource = currentProduct.mp_label_data.first()?.label_image ?: ""
                Glide.with(activity).load(imageResource).into(productImageView)
            }
            productImageView.setOnClickListener { productClickedListener.onProductClicked(imageResource) }
        }
    }

    override fun getItemCount(): Int = productList.size

    inner class ProductViewHolder(val binding: ProductItemBinding) : RecyclerView.ViewHolder(binding.root)
}