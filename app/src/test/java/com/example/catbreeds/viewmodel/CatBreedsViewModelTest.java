package com.example.catbreeds.viewmodel;

import com.example.catbreeds.models.CatBreed;
import com.example.catbreeds.models.CatBreedImage;
import com.example.catbreeds.repository.Repository;

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
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;

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

    @Before
    public void setUp() {
        viewModel = new CatBreedsViewModel(repository);
        viewModel.getCatBreeds().observeForever(catBreedsObserver);
        createDummyContent();
    }

    @Test
    public void testRepositoryFetchListNull() {
        mockFetchListReturn(null);

        viewModel.fetchList(false);
        assertTrue(viewModel.loading.get());
        assertTrue(viewModel.getCatBreeds().hasObservers());
        verifyZeroInteractions(catBreedsObserver);
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
    public void testRepositoryForceFetchListSuccess() {
        mockFetchListReturn(CAT_BREED_LIST);
        mockFetchImageReturn(new CatBreedImage());

        viewModel.fetchList(false);
        viewModel.fetchCatBreedImageAt(0);
        assertEquals(viewModel.images.size(), 1);
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
        }
    }

    private CatBreed createDummyCatBreed(int i) {
        CatBreed catBreed = new CatBreed();
        catBreed.setId("BreedId-" + i);
        catBreed.setName("Name: " + i);
        catBreed.setDescription("Description: " + i);
        return catBreed;
    }
}
