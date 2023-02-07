package com.pns.ajio.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pns.ajio.data.models.Category
import com.pns.ajio.data.repositories.CategoryRepository
import com.pns.ajio.data.repositories.OnCategoryDataAdded

class CategoryViewModel : ViewModel(), OnCategoryDataAdded {

    private val _categoryListData: MutableLiveData<List<Category>> = MutableLiveData()

    val categoryListData: LiveData<List<Category>>
        get() = _categoryListData

    private val repo = CategoryRepository(this)

    init {
        repo.getCategoryList()
    }

    override fun categoryDataAdded(categoryModelList: List<Category>) {
        _categoryListData.value = categoryModelList
    }

    override fun onError(e: Exception?) {
        TODO("Not yet implemented")
    }
}
