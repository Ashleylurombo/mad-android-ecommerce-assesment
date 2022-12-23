package mad.app.madandroidtestsolutions
//This class is not necessarily needed seeing as this is a small project but will be helpful in bigger projects

import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels

abstract class MobileShoppingBaseFragment(@LayoutRes layout: Int) : Fragment(layout) {
    protected val productListPageViewModel by activityViewModels<ProductListPageViewModel>()
}