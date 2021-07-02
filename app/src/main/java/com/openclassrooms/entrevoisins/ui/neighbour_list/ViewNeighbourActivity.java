package com.openclassrooms.entrevoisins.ui.neighbour_list;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.di.DI;
import com.openclassrooms.entrevoisins.model.Neighbour;
import com.openclassrooms.entrevoisins.service.NeighbourApiService;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ViewNeighbourActivity extends AppCompatActivity{

    private NeighbourApiService mApiService;
    private Neighbour n;

    @BindView(R.id.item_view_name) TextView mNeighbourName;
    @BindView(R.id.item_view_name_header) TextView mNeighbourNameHeader;
    @BindView(R.id.item_view_address) TextView mAddress;
    @BindView(R.id.item_view_phoneNumber) TextView mPhoneNumber;
    @BindView(R.id.item_view_contact) TextView mSocialMedia;
    @BindView(R.id.item_view_aboutMe) TextView mAboutMe;
    @BindView(R.id.item_view_avatar) ImageView mAvatar;
    @BindView(R.id.item_is_favorites) FloatingActionButton mFavorite;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_neighbour);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mApiService = DI.getNeighbourApiService();
        n = (Neighbour) getIntent().getSerializableExtra("Myneighbour");

        // Récupération et remplissage des informations du voisin sur le profil
        String name = n.getName();
        mNeighbourName.setText(n.getName());
        mNeighbourNameHeader.setText(n.getName());
        mNeighbourNameHeader.setTextColor(Color.parseColor("#FFFFFF"));
        mAddress.setText(n.getAddress());
        mPhoneNumber.setText(n.getPhoneNumber());
        mAboutMe.setText(n.getAboutMe());
        Glide.with(this).load(n.getAvatarUrl()).into(mAvatar);

        // Définition de l'étoile par défaut en fonction du statut favoris ou non
        if (n.getFavorite()) {
            mFavorite.setImageResource(R.drawable.ic_star_white_24dp);
        } else {
            mFavorite.setImageResource(R.drawable.ic_star_border_white_24dp);
        }

        mFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Ajouter le voisin aux favoris si true et retire si false
                if (n.getFavorite()) {
                    mFavorite.setImageResource(R.drawable.ic_star_border_white_24dp);
                    mApiService.removeFavorite(n);
                    n.setFavorite(false);
                    Snackbar.make(view, name + " a été rétiré(e) des favoris!", Snackbar.LENGTH_LONG).show();
                } else {
                    mFavorite.setImageResource(R.drawable.ic_star_white_24dp);
                    mApiService.addFavorite(n);
                    n.setFavorite(true);
                    Snackbar.make(view, name + " a été ajouté(e) aux favoris!", Snackbar.LENGTH_LONG).show();
                }
            }
        });

    }

    // Barre de retour sur la page précédente
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
