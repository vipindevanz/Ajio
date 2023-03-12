package com.pns.ajio.data.repositories

import com.google.firebase.database.*
import com.pns.ajio.data.models.Category
import java.util.*

class CategoryRepository(onCategoryDataAdded: OnCategoryDataAdded) {

    private var onCategoryDataAdded: OnCategoryDataAdded

    init {
        this.onCategoryDataAdded = onCategoryDataAdded
    }

    fun getCategoryList() {


        val reference = FirebaseDatabase.getInstance().getReference("Category")
        reference.keepSynced(true)

        val categoryList = ArrayList<Category>()

        reference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    categoryList.clear()
                    for (dataSnapshot in snapshot.children) {
                        val category = dataSnapshot.getValue(
                            Category::class.java
                        )
                        if (category != null) {
                            categoryList.add(category)
                        }
                    }
                    onCategoryDataAdded.categoryDataAdded(categoryList)
                }
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }
}

interface OnCategoryDataAdded {
    fun categoryDataAdded(categoryModelList: List<Category>)
    fun onError(e: Exception?)
}