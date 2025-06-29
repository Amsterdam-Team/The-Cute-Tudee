package com.amsterdam.cutetudee.utils

import com.amsterdam.cutetudee.data.local.dto.CategoryDto
import com.amsterdam.cutetudee.data.local.roomDB.TudeeDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PredefinedCategorySeeder(
    private val database: TudeeDatabase
) {
    init {
        CoroutineScope(Dispatchers.IO).launch {
            val categoryDao = database.categoryDao()
            getPredefinedCategories().forEach {
                categoryDao.upsertCategory(it)
            }
        }
    }

    private fun getPredefinedCategories(): List<CategoryDto> = listOf(
        CategoryDto(
            "1f44a081-3528-4546-aa94-d5133cceed41",
            "Education",
            "android.resource://com.amsterdam.cutetudee/drawable/education",
            0,
            false
        ),
        CategoryDto(
            "6b40a9e0-740f-4189-a200-97db5d171717",
            "Shopping",
            "android.resource://com.amsterdam.cutetudee/drawable/shopping",
            0,
            false
        ),
        CategoryDto(
            "eae97d07-be1c-4895-a3ef-7268e0953f2a",
            "Medical",
            "android.resource://com.amsterdam.cutetudee/drawable/medical",
            0,
            false
        ),
        CategoryDto(
            "bfd5561d-6a2a-43e3-a500-136db1c56ef3",
            "Gym",
            "android.resource://com.amsterdam.cutetudee/drawable/gym",
            0,
            false
        ),
        CategoryDto(
            "63af9aba-83b8-4746-aee7-5023e9d06920",
            "Entertainment",
            "android.resource://com.amsterdam.cutetudee/drawable/entertainment",
            0,
            false
        ),
        CategoryDto(
            "9a0433dc-8d8d-4da5-8cc8-f7ec2cd4ac5c",
            "Cooking",
            "android.resource://com.amsterdam.cutetudee/drawable/cooking",
            0,
            false
        ),
        CategoryDto(
            "9318b77e-a23c-492d-baa2-6a1cb3bf46ff",
            "Family & friend",
            "android.resource://com.amsterdam.cutetudee/drawable/family_and_friends",
            0,
            false
        ),
        CategoryDto(
            "695a1447-984e-4b06-9d29-58aa8d279c1c",
            "Traveling",
            "android.resource://com.amsterdam.cutetudee/drawable/travelling",
            0,
            false
        ),
        CategoryDto(
            "e708edd9-3f92-485b-b2b2-d319778b8754",
            "Agriculture",
            "android.resource://com.amsterdam.cutetudee/drawable/agriculture",
            0,
            false
        ),
        CategoryDto(
            "60d224c9-19cc-469b-a9c6-b61a77a37135",
            "Coding",
            "android.resource://com.amsterdam.cutetudee/drawable/coding",
            0,
            false
        ),
        CategoryDto(
            "d4cc202b-264d-4faf-a48c-147c4fed7cf9",
            "Adoration",
            "android.resource://com.amsterdam.cutetudee/drawable/adoration",
            0,
            false
        ),
        CategoryDto(
            "9326cf6e-8d88-4252-9e2a-8524a944e31e",
            "Fixing bugs",
            "android.resource://com.amsterdam.cutetudee/drawable/fixing_bugs",
            0,
            false
        ),
        CategoryDto(
            "0cb297a9-aee8-412b-baf0-6e947d460b14",
            "Cleaning",
            "android.resource://com.amsterdam.cutetudee/drawable/cleaning",
            0,
            false
        ),
        CategoryDto(
            "1c499a08-aca1-4abc-a9f8-edc81da0e011",
            "Work",
            "android.resource://com.amsterdam.cutetudee/drawable/work",
            0,
            false
        ),
        CategoryDto(
            "5cc51c5f-975e-4776-b408-b68e3bccae8a",
            "Budgeting",
            "android.resource://com.amsterdam.cutetudee/drawable/budgeting",
            0,
            false
        ),
        CategoryDto(
            "4ae9ef16-df56-48e1-a13d-73ed0b79b4d2",
            "Self-care",
            "android.resource://com.amsterdam.cutetudee/drawable/self_care",
            0,
            false
        ),
        CategoryDto(
            "f4ca23d8-a107-433c-a55a-1d4c40a55fdc",
            "Event",
            "android.resource://com.amsterdam.cutetudee/drawable/event",
            0,
            false
        )
    )
}
