package com.openclassrooms.entrevoisins.service;

import com.openclassrooms.entrevoisins.model.Neighbour;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * Dummy mock for the Api
 */
public class DummyNeighbourApiService implements  NeighbourApiService {

    private List<Neighbour> neighbours = DummyNeighbourGenerator.generateNeighbours();

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Neighbour> getNeighbours() {
        return neighbours;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteNeighbour(Neighbour neighbour) {
        neighbours.remove(neighbour);
    }

    /**
     * {@inheritDoc}
     * @param neighbour
     */
    @Override
    public void createNeighbour(Neighbour neighbour) {
        neighbours.add(neighbour);
    }


    /**
     * @method addFavorite
     *
     * Ajouter un voisin aux favoris
     *
     * @param neighbour
     */
    @Override
    public void addFavorite(Neighbour neighbour) {
       Neighbour n = neighbours.get(neighbours.indexOf(neighbour));
       n.setFavorite(true);
    }

    /**
     * @method removeFavorite
     *
     * Retirer un voisin des favoris
     *
     * @param neighbour neighbour voisin à supprimer
     */
    @Override
    public void removeFavorite(Neighbour neighbour) {
        Neighbour n = neighbours.get(neighbours.indexOf(neighbour));
        n.setFavorite(false);
    }

    /**
     * @method getFavoritesNeighbour
     *
     * Récupérer la liste des favoris
     *
     * @return ArrayList<Neighbour> liste des favoris
     */
    @Override
    public List<Neighbour> getFavoritesNeighbour() {
        List<Neighbour> fav = new ArrayList<Neighbour>();
        for(Neighbour n : neighbours) {
            if (n.getFavorite()) {
                fav.add(n);
            }
        }
        return fav;
    }
}
