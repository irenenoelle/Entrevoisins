package com.openclassrooms.entrevoisins.service;

import com.openclassrooms.entrevoisins.di.DI;
import com.openclassrooms.entrevoisins.model.Neighbour;

import org.hamcrest.collection.IsIterableContainingInAnyOrder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * Unit test on Neighbour service
 */
@RunWith(JUnit4.class)
public class NeighbourServiceTest {

    private NeighbourApiService service;

    @Before
    public void setup() {
        service = DI.getNewInstanceApiService();
    }

    @Test
    public void getNeighboursWithSuccess() {
        List<Neighbour> neighbours = service.getNeighbours();
        List<Neighbour> expectedNeighbours = DummyNeighbourGenerator.DUMMY_NEIGHBOURS;
        assertThat(neighbours, IsIterableContainingInAnyOrder.containsInAnyOrder(expectedNeighbours.toArray()));
    }

    @Test
    public void deleteNeighbourWithSuccess() {
        Neighbour neighbourToDelete = service.getNeighbours().get(0);
        service.deleteNeighbour(neighbourToDelete);
        assertFalse(service.getNeighbours().contains(neighbourToDelete));
    }

    @Test
    public void createNeighbourWithSuccess() {
        Neighbour neighbourToCreate = new Neighbour(0, "Noelle", "", "52 rue vauban", "0769032543", "Ingenieur Informatique");
        //Neighbour neighbourToCreate = service.getNeighbours().get(0);
        service.createNeighbour(neighbourToCreate);
        assertTrue(service.getNeighbours().contains(neighbourToCreate));
    }

    @Test
    public void getFavoritesNeighboursWithSuccess() {
        List<Neighbour> neighbours = service.getNeighbours();
        Neighbour neighbour = neighbours.get(0);
        neighbour.setFavorite(true);
        List<Neighbour> favorites = service.getFavoritesNeighbour();
        assertThat(favorites.size(), is(1));
        assertTrue(favorites.contains(neighbour));

    }

    @Test
    public void neighbourIsFavorite()
    {
        List<Neighbour> neighbours = service.getFavoritesNeighbour();
        for (Neighbour n: neighbours)
        {
            assertTrue(n.getFavorite());
        }
    }

    @Test
    public void addFavoriteWithSuccess() {
        Neighbour favNeighbourToAdd = service.getNeighbours().get(0);
        service.addFavorite(favNeighbourToAdd);
        assertTrue(favNeighbourToAdd.getFavorite());
        assertTrue(service.getFavoritesNeighbour().contains(favNeighbourToAdd));
    }

    @Test
    public void removeFavoriteWithSuccess() {
        Neighbour favNeighbourToRemove = service.getNeighbours().get(0);
        service.removeFavorite(favNeighbourToRemove);
        assertFalse(favNeighbourToRemove.getFavorite());
        assertFalse(service.getFavoritesNeighbour().contains(favNeighbourToRemove));
    }
}
