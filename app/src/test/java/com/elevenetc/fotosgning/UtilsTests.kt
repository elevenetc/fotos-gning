package com.elevenetc.fotosgning

import com.elevenetc.fotosgning.search.Photo
import com.elevenetc.fotosgning.utils.StubPhotoSearchModel
import org.junit.Assert.assertEquals
import org.junit.Test

class UtilsTests {
    @Test
    fun testPhotoSearchModel() {

        val page1 = listOf(Photo("1"), Photo("2"), Photo("3"))
        val page2 = listOf(Photo("4"), Photo("5"), Photo("6"))

        val model = StubPhotoSearchModel(mapOf(
                Pair("abc", page1.plus(page2))
        ))

        assertEquals(model.queryPhotos("abc", 1, 3), page1)
        assertEquals(model.queryPhotos("abc", 2, 3), page2)
        assertEquals(model.queryPhotos("z", 1, 3), emptyList<Photo>())

    }
}