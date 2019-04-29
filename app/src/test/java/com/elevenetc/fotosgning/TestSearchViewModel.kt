package com.elevenetc.fotosgning

import com.elevenetc.fotosgning.search.Photo
import com.elevenetc.fotosgning.search.PhotoSearchViewModel
import com.elevenetc.fotosgning.search.ViewConfig
import com.elevenetc.fotosgning.utils.CurrentThreadScheduler
import com.elevenetc.fotosgning.utils.StubPhotoSearchModel
import org.junit.Assert.assertEquals
import org.junit.Test

class TestSearchViewModel {
    @Test
    fun pagesLoading() {

        val viewConfig = ViewConfig(300, 300, 3)

        val query = "abc"

        val page1 = getPage(1, viewConfig.photosPerPage)
        val page2 = getPage(2, viewConfig.photosPerPage)
        val page3 = getPage(3, viewConfig.photosPerPage)

        val model = StubPhotoSearchModel(mapOf(Pair(query, page1 + page2 + page3)))
        val viewModel = PhotoSearchViewModel(model, CurrentThreadScheduler(), viewConfig)

        viewModel.getFirstPage(query).subscribe({
            assertEquals(page1, it.photos)
        })

        viewModel.getNextPage(query).subscribe({
            assertEquals(page2, it.photos)
        })

        viewModel.getNextPage(query).subscribe({
            assertEquals(page3, it.photos)
        })

        viewModel.getNextPage(query).subscribe({
            assertEquals(emptyList<Photo>(), it.photos)
        })
    }

    private fun getPage(page: Int, count: Int): List<Photo> {
        val result = mutableListOf<Photo>()
        for (i in 0 until count)
            result.add(Photo("page-$page-$i"))
        return result
    }
}
