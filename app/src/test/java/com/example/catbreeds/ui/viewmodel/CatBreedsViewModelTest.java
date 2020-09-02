package com.example.catbreeds.ui.viewmodel;

import com.example.catbreeds.data.model.CatBreed;
import com.example.catbreeds.data.model.CatBreedImage;
import com.example.catbreeds.data.repository.Repository;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import java.util.ArrayList;
import java.util.List;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.Observer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class CatBreedsViewModelTest {

    @Rule
    public TestRule rule = new InstantTaskExecutorRule();

    @Mock
    private Observer<List<CatBreed>> catBreedsObserver;

    @Mock
    private Repository repository;

    private CatBreedsViewModel viewModel;

    private static final List<CatBreed> CAT_BREED_LIST = new ArrayList<>();
    private static final List<CatBreedImage> CAT_BREED_IMAGE_LIST = new ArrayList<>();

    @Before
    public void setUp() {
        viewModel = new CatBreedsViewModel(repository);
        viewModel.getCatBreeds().observeForever(catBreedsObserver);
        createDummyContent();
    }

    @Test
    public void testRepositoryFetchListSuccess() {
        mockFetchListReturn(CAT_BREED_LIST);

        viewModel.fetchList(false);
        assertTrue(viewModel.loading.get());
        assertTrue(viewModel.getCatBreeds().hasObservers());
        verify(catBreedsObserver).onChanged(CAT_BREED_LIST);
    }

    @Test
    public void testRepositoryFetchListNull() {
        mockFetchListReturn(null);

        viewModel.fetchList(false);
        assertTrue(viewModel.loading.get());
        verify(catBreedsObserver).onChanged(null);
    }

    @Test
    public void testRepositoryFetchImageFromItemListSuccess() {
        mockFetchListReturn(CAT_BREED_LIST);

        // random position to retrieve a breed from list
        int position = 0;

        mockFetchImageReturn(CAT_BREED_IMAGE_LIST.get(position));

        // firstly fetch breeds list with no images
        viewModel.fetchList(false);

        // make sure there is no image for that breed yet
        assertNull(viewModel.images.get(CAT_BREED_LIST.get(position).getId()));

        // fetch image for the given breed and make sure it is reflected on observable
        viewModel.fetchCatBreedImageAt(position);
        assertEquals(viewModel.images.get(CAT_BREED_LIST.get(position).getId()),
                CAT_BREED_IMAGE_LIST.get(position).getUrl());
    }

    @Test
    public void testRepositoryForceFetchImageFromItemListSuccess() {
        mockFetchListReturn(CAT_BREED_LIST);

        // random position to retrieve a breed from list
        int position = 0;

        mockFetchImageReturn(CAT_BREED_IMAGE_LIST.get(position));

        // fetch breeds list and assign an image for the given position
        viewModel.fetchList(false);
        viewModel.fetchCatBreedImageAt(position);

        // make sure image is correctly assigned
        assertEquals(viewModel.images.get(CAT_BREED_LIST.get(position).getId()),
                CAT_BREED_IMAGE_LIST.get(position).getUrl());

        int new_image_position = 3;

        // mock a new image for the given breed
        mockFetchImageReturn(CAT_BREED_IMAGE_LIST.get(new_image_position));

        // force fetch new breeds list to load a new mocked image
        viewModel.fetchList(true);

        // make sure image cache is reset
        assertNull(viewModel.images.get(CAT_BREED_LIST.get(position).getId()));

        // finally fetch the new image
        viewModel.fetchCatBreedImageAt(position);

        // make sure the image is updated
        assertEquals(viewModel.images.get(CAT_BREED_LIST.get(position).getId()),
                CAT_BREED_IMAGE_LIST.get(new_image_position).getUrl());
    }

    @Test
    public void testRepositoryFetchImageFromItemListNull() {
        mockFetchListReturn(CAT_BREED_LIST);

        // random position to retrieve a breed from list
        int position = 0;

        mockFetchImageReturn(CAT_BREED_IMAGE_LIST.get(position));

        // fetch breeds list and assign an image for the given position
        viewModel.fetchList(false);
        viewModel.fetchCatBreedImageAt(position);

        // make sure image is correctly assigned
        assertEquals(viewModel.images.get(CAT_BREED_LIST.get(position).getId()),
                CAT_BREED_IMAGE_LIST.get(position).getUrl());

        mockFetchImageReturn(null);

        // make sure image is not replaced with null return
        assertEquals(viewModel.images.get(CAT_BREED_LIST.get(position).getId()),
                CAT_BREED_IMAGE_LIST.get(position).getUrl());
    }

    private void mockFetchListReturn(final List<CatBreed> response) {
        doAnswer(new Answer<Object>() {
            @Override
            public Object answer(InvocationOnMock invocation) {
                Repository.FetchDataCallback<List<CatBreed>> callback = invocation.getArgument(0);
                callback.onLoaded(response);
                return null;
            }
        }).when(repository).fetchList(any(Repository.FetchDataCallback.class));
    }

    private void mockFetchImageReturn(final CatBreedImage response) {
        doAnswer(new Answer<Object>() {
            @Override
            public Object answer(InvocationOnMock invocation) {
                Repository.FetchDataCallback<CatBreedImage> callback = invocation.getArgument(1);
                callback.onLoaded(response);
                return null;
            }
        }).when(repository).fetchImage(any(String.class), any(Repository.FetchDataCallback.class));
    }

    private void createDummyContent() {
        for (int i = 0; i < 10; i++) {
            CAT_BREED_LIST.add(createDummyCatBreed(i));
            CAT_BREED_IMAGE_LIST.add(createDummyCatBreedImage(i));
        }
    }

    private CatBreed createDummyCatBreed(int i) {
        CatBreed catBreed = new CatBreed();
        catBreed.setId("BreedId-" + i);
        catBreed.setName("Name: " + i);
        catBreed.setDescription("Description: " + i);
        return catBreed;
    }

    private CatBreedImage createDummyCatBreedImage(int i) {
        CatBreedImage catBreed = new CatBreedImage();
        catBreed.setUrl("url-" + i);
        return catBreed;
    }
}
