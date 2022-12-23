package mad.app.madandroidtestsolutions

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import mad.app.madandroidtestsolutions.service.ApiService
import mad.app.plptest.CategoryQuery
import mad.app.plptest.CategoryRootQuery

class ProductListPageViewModel : ViewModel() {
    private val apiService = ApiService.createEcommerceClient()
    var categoryId: String = ""
    var pageSize: Int = 20
    var pageNumber: Int = 1
    var totalNumberOfOfPages: Int = 0
    var productsLiveData: MutableLiveData<List<CategoryQuery.Item?>> = MutableLiveData()
    var categoriesLiveData: MutableLiveData<List<CategoryRootQuery.Child?>> = MutableLiveData()

    fun fetchProducts() {
        viewModelScope.launch(Dispatchers.IO) {
            val products =
                apiService.catalog.getProductsForCategory(categoryId, pageNumber, pageSize)
            pageNumber = products?.page_info?.current_page ?: 0
            pageSize = products?.page_info?.page_size ?: 0
            totalNumberOfOfPages = products?.page_info?.total_pages ?: 0
            productsLiveData.postValue(products?.items ?: emptyList())
        }
    }

    fun fetchCategories() {
        viewModelScope.launch(Dispatchers.IO) {
            val categories = apiService.catalog.fetchRootCategory()?.children ?: emptyList()
            categoriesLiveData.postValue(categories)
        }
    }

    fun shouldUpdateProductsList(): Boolean = pageNumber != totalNumberOfOfPages
}