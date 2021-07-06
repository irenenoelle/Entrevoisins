
package com.openclassrooms.entrevoisins.neighbour_list;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.di.DI;
import com.openclassrooms.entrevoisins.service.NeighbourApiService;
import com.openclassrooms.entrevoisins.ui.neighbour_list.ListNeighbourActivity;
import com.openclassrooms.entrevoisins.utils.DeleteViewAction;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.matcher.ViewMatchers.assertThat;
import static android.support.test.espresso.matcher.ViewMatchers.hasMinimumChildCount;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withResourceName;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.openclassrooms.entrevoisins.utils.RecyclerViewItemCountAssertion.withItemCount;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.core.IsNull.notNullValue;



/**
 * Test class for list of neighbours
 */
@RunWith(AndroidJUnit4.class)
public class NeighboursListTest {

    private static final int ITEMS_COUNT = 12;
    private final int mPosition = 0;

    private ListNeighbourActivity mActivity;
    private NeighbourApiService mApiService;

    @Rule
    public ActivityTestRule<ListNeighbourActivity> mActivityRule =
            new ActivityTestRule<>(ListNeighbourActivity.class);

    @Before
    public void setUp()
    {
        mActivity = mActivityRule.getActivity();
        assertThat(mActivity, notNullValue());
        mApiService = DI.getNeighbourApiService();
    }

    /**
     * We ensure that our recyclerview is displaying at least on item
     */
    @Test
    public void myNeighboursList_shouldNotBeEmpty() {
        // First scroll to the position that needs to be matched and click on it.
        onView(allOf(withId(R.id.list_neighbours),isDisplayed()))
                .check(matches(hasMinimumChildCount(1)));
    }

    /**
     * When we delete an item, the item is no more shown
     */
    @Test
    public void myNeighboursList_deleteAction_shouldRemoveItem()
    {
        // Given : We remove the element at position 2
        onView(allOf(withId(R.id.list_neighbours), isDisplayed()))
                .check(withItemCount(ITEMS_COUNT));
        // When perform a click on a delete icon
        onView(allOf(withId(R.id.list_neighbours), isDisplayed()))
                .perform(actionOnItemAtPosition(1, new DeleteViewAction()));
        // Then : the number of element is 11
        onView(allOf(withId(R.id.list_neighbours), isDisplayed()))
                .check(withItemCount(ITEMS_COUNT-1));
    }

    @Test
    public void goToDetailedProfile_onClick()
    {
        // Cliquer sur l'élément en identifiant le conteneur parent et ses enfants
        onView(allOf(withId(R.id.list_neighbours), isDisplayed()))
                .perform(actionOnItemAtPosition(mPosition, click()));
        // Verifier qu'on est bien sur la page profil en cliquant sur le bouton favoris
        onView(allOf(withId(R.id.item_is_favorites))).perform(click());
        pressBack();
    }

    @Test
    public void viewNeighbourDetailsLaunch_textView_containName()
    {
        // Cliquer sur l'élement en identifiant le conteneur parent et ses enfants
        onView(allOf(withId(R.id.list_neighbours), isDisplayed()))
                .perform(actionOnItemAtPosition(mPosition, click()));
        // Verifier que le neighbourName est affiché sur l'écran viewNeighbour
        onView(withId(R.id.item_view_name))
                .check(matches(withText(mApiService.getNeighbours().get(mPosition).getName())));
    }

    @Test
    public void favoritePage_showNeighbours_onlyIfIsFav()
    {
        // Cliquer sur l'élément en identifiant le container parent et ses enfants
        onView(allOf(withId(R.id.list_neighbours), isDisplayed()))
                .perform(actionOnItemAtPosition(mPosition, click()));
        // Cliquer sur l'étoile pour ajouter en favoris
        onView(withId(R.id.item_is_favorites))
                .perform(click());
        // Retour à la page d'accueil
        pressBack();

        // Cliquer sur l'élément 2
        onView(allOf(withId(R.id.list_neighbours), isDisplayed()))
                .perform(actionOnItemAtPosition(mPosition+1, click()));
        // Cliquer pour ajouter aux favoris
        onView(withId(R.id.item_is_favorites))
                .perform(click());
        // Retour à la page d'accueil
        pressBack();

        // Cliquer sur l'onglet des favoris
        onView(withContentDescription("Favorites"))
                .perform(click());
        // Vérifier que la liste affcichée et bien celle des favoris
        onView(allOf(withId(R.id.list_neighbours), isDisplayed()))
                .check(withItemCount(mApiService.getFavoritesNeighbour().size()));
        // Cliquer sur l'utilisateur pour une 2e vérification
        onView(allOf(withId(R.id.list_neighbours), isDisplayed()))
                .perform(actionOnItemAtPosition(mPosition, click()));
    }

}