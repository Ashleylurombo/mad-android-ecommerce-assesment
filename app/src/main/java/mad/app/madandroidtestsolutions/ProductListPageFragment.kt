package mad.app.madandroidtestsolutions

import android.os.Bundle
import android.view.ScaleGestureDetector
import android.view.View
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding
import mad.app.madandroidtestsolutions.databinding.ProductListPageFragmentBinding
import mad.app.plptest.CategoryQuery

class ProductListPageFragment : MobileShoppingBaseFragment(R.layout.product_list_page_fragment), OnProductClickedListener {
    private val binding by viewBinding(ProductListPageFragmentBinding::bind)
    private lateinit var productAdapter: ProductAdapter
    private var currentProductList: List<CategoryQuery.Item?> = emptyList()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.progressBar.visibility = View.VISIBLE
        addListeners()
        fetchAndObserveProducts()
    }

    private fun initViews() {
        productAdapter = ProductAdapter(currentProductList, requireActivity(), this)
        binding.productsRecyclerView.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = productAdapter
            productAdapter.notifyItemRangeChanged(0, productAdapter.itemCount)
            binding.swipeContainer.isRefreshing = false
        }

        binding.swipeContainer.setColorSchemeResources(
            android.R.color.holo_red_light,
            android.R.color.holo_red_dark,
            android.R.color.holo_orange_light
        )
    }

    private fun fetchAndObserveProducts() {
        with(productListPageViewModel) {
            productsLiveData = MutableLiveData()

            fetchProducts()
            productsLiveData.observe(viewLifecycleOwner) {
                currentProductList = it
                initViews()
                binding.progressBar.visibility = View.GONE
            }
        }
    }

    private fun addListeners() {
        binding.swipeContainer.setOnRefreshListener {
            if (productListPageViewModel.shouldUpdateProductsList()) {
                productListPageViewModel.pageNumber++
                fetchAndObserveProducts()
            } else {
                Toast.makeText(context, getString(R.string.no_new_items), Toast.LENGTH_SHORT).show()
                binding.swipeContainer.isRefreshing = false
            }
        }
    }

    private fun zoomImage(imageResource: String?) {
        binding.swipeContainer.visibility = View.GONE
        with(binding.productLargeImageView) {
            visibility = View.VISIBLE
            if (!imageResource.isNullOrBlank()) {
                Glide.with(requireActivity()).load(imageResource).into(this)
            }

            val scaleGestureDetector = ScaleGestureDetector(requireContext(), object : ScaleGestureDetector.SimpleOnScaleGestureListener() {
                override fun onScale(detector: ScaleGestureDetector): Boolean {
                    scaleX *= detector.scaleFactor
                    scaleY *= detector.scaleFactor
                    return true
                }
            })

            setOnTouchListener { view, event ->
                scaleGestureDetector.onTouchEvent(event)
                view.performClick()
                true
            }
        }
    }

    override fun onProductClicked(imageResource: String?) {
        zoomImage(imageResource)
    }
}