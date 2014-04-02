package com.chrisbarklow.tyrianclone.screens.scene2d;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Array;
import com.chrisbarklow.tyrianclone.domain.Ship;

public class Ship2D extends Image {
	
	/**
     * Creates a new ship2D.
     */
    private Ship2D(Ship ship, Array<AtlasRegion> regions ){
        // the super constructor does a lot of work
        super( regions.get( 0 ) );

        // set some basic attributes
        this.setTouchable(Touchable.disabled);
    }
    
    /**
     * Factory method to create a ship2D.
     */
    public static Ship2D create(Ship ship,
        TextureAtlas textureAtlas )
    {
        Array<AtlasRegion> regions = textureAtlas.findRegions( ship.getShipModel().getPreviewImageName());
        Ship2D ship2d = new Ship2D( ship, regions );
        return ship2d;
    }

}
