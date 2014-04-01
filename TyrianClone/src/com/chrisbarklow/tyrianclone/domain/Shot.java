package com.chrisbarklow.tyrianclone.domain;

public enum Shot {
	
	BULLET( 1 ),
    MISSILE( 2 ),
    FIREBALL( 3 ),
    PROTON( 4 ),
    WAVE( 5 );

    private final int damage;

    private Shot(
        int damage )
    {
        this.damage = damage;
    }

    /**
     * Retrieves the damage caused by this shot.
     */
    public int getDamage()
    {
        return damage;
    }

}
