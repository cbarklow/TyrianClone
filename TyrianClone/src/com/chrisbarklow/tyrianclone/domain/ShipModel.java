package com.chrisbarklow.tyrianclone.domain;

/**
 * The available ship's models.
 */
public enum ShipModel implements Item {
	USP_TALON( "USP Talon", 6000, 1, "ship-model-talon" ),
    GENCORE_PHOENIX( "Gencore Phoenix", 12000, 2, "ship-model-gencore" ),
    GENCORE_II( "Gencore II", 17000, 3, "ship-model-gencore2" ),
    MICROSOL_STALKER( "Microsol Stalker", 20000, 4, "ship-model-stalker" ),
    SUPER_CARROT( "Super Carrot", 50000, 5, "ship-model-carrot" );

    private final String name;
    private final int price;
    private final int firingCapacity;
    private final String previewImageName;

    private ShipModel(String name, int price, int firingCapacity, String previewImageName)
    {
        this.name = name;
        this.price = price;
        this.firingCapacity = firingCapacity;
        this.previewImageName = previewImageName;
    }

    public String getName()
    {
        return name;
    }

    public int getPrice()
    {
        return price;
    }

    /**
     * Retrieves the firing capacity for this ship model.
     * <p>
     * 1 means 1 shot each 1/4 sec.
     */
    public int getFiringCapacity()
    {
        return firingCapacity;
    }
    
    public String getPreviewImageName(){
    	return previewImageName;
    }

}
