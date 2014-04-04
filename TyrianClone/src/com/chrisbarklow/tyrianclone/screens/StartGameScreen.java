package com.chrisbarklow.tyrianclone.screens;

import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.chrisbarklow.tyrianclone.TyrianClone;
import com.chrisbarklow.tyrianclone.domain.FrontGun;
import com.chrisbarklow.tyrianclone.domain.Item;
import com.chrisbarklow.tyrianclone.domain.Level;
import com.chrisbarklow.tyrianclone.domain.Profile;
import com.chrisbarklow.tyrianclone.domain.Shield;
import com.chrisbarklow.tyrianclone.domain.Ship;
import com.chrisbarklow.tyrianclone.domain.ShipModel;
import com.chrisbarklow.tyrianclone.managers.SoundManager.TyrianCloneSound;

public class StartGameScreen extends AbstractScreen {
	
	private Profile profile;
    private Ship ship;
    
    private TextButton episode1Button;
    private TextButton episode2Button;
    private TextButton episode3Button;
    private SelectBox<ShipModel> shipModelSelectBox;
    private SelectBox<FrontGun> frontGunSelectBox;
    private SelectBox<Shield> shieldModelSelectBox;
    private Label credits;
    
    private Image shipModelImage;
    
    public StartGameScreen(TyrianClone game){
    	super(game);
    	
    }
    
    @Override
    public void show()
    {
        super.show();

        // retrieve the custom skin for our 2D widgets
        Skin skin = super.getSkin();

        // create the table actor and add it to the stage
        Table table = super.getTable();
        table.defaults().spaceBottom(20);
        table.columnDefaults( 0 ).padRight( 20 );
        table.columnDefaults( 4 ).padLeft( 10 );
        table.add( "Start Game" ).colspan( 5 );

        profile = game.getProfileManager().retrieveProfile();
        ship = profile.getShip();

        // create the level buttons
        table.row();
        table.add( "Episodes" );
                
        episode1Button = new TextButton( "Episode 1", skin );
        episode1Button.addListener(new ChangeListener(){
        	@Override
        	public void changed(ChangeEvent event, Actor actor){
        		List<Level> levels = game.getLevelManager().getLevels();
        		int targetLevelId = levels.get(0).getId();
        		
        		if(profile.getCurrentLevelId() >= targetLevelId){
        			Gdx.app.log(TyrianClone.LOG, "Starting Level: " + targetLevelId);
        			game.setScreen(new LevelScreen(game, "test-level"));
        		} else {
        			Gdx.app.log(TyrianClone.LOG, "Unable to start level " + targetLevelId);
        		}
        	}
        });
        table.add(episode1Button).fillX().padRight(10);

        episode2Button = new TextButton( "Episode 2", skin );
        episode2Button.addListener(new ChangeListener(){
        	@Override
        	public void changed(ChangeEvent event, Actor actor){
        		List<Level> levels = game.getLevelManager().getLevels();
        		int targetLevelId = levels.get(1).getId();
        		
        		if(profile.getCurrentLevelId() >= targetLevelId){
        			Gdx.app.log(TyrianClone.LOG, "Starting Level: " + targetLevelId);
        		} else {
        			Gdx.app.log(TyrianClone.LOG, "Unable to start level " + targetLevelId);
        		}
        	}
        });
        table.add(episode2Button).fillX().padRight(10);

        episode3Button = new TextButton( "Episode 3", skin );
        episode3Button.addListener(new ChangeListener(){
        	@Override
        	public void changed(ChangeEvent event, Actor actor){
        		List<Level> levels = game.getLevelManager().getLevels();
        		int targetLevelId = levels.get(2).getId();
        		
        		if(profile.getCurrentLevelId() >= targetLevelId){
        			Gdx.app.log(TyrianClone.LOG, "Starting Level: " + targetLevelId);
        		} else {
        			Gdx.app.log(TyrianClone.LOG, "Unable to start level " + targetLevelId);
        		}
        	}
        });;
        table.add(episode3Button).fillX();
        table.row();

        // create the item select boxes
        table.add("Ship Model");
        shipModelSelectBox = new SelectBox<ShipModel>( skin );
        shipModelSelectBox.setItems( ShipModel.values() );
        shipModelSelectBox.addListener( new ChangeListener(){
        	@Override
        	public void changed(ChangeEvent event, Actor actor){
        		// find the selected item
                Item selectedItem = ShipModel.values()[ shipModelSelectBox.getSelectedIndex() ];               
                // if the ship already contains the item, there is no need to buy it
                if( ship.contains(selectedItem)) return;
                // try and buy the item or reset all select boxes
                if( profile.buy(selectedItem)) {
                    credits.setText(profile.getCreditsAsText());
                    updateValues();
                } else {
                    shipModelSelectBox.setSelectedIndex( ship.getShipModel().ordinal() );                    
                }
        	}
        });
        table.add(shipModelSelectBox).fillX().colspan(3);
        shipModelImage = new Image();
        table.add(shipModelImage);        
        table.row();

        Label frontGun = new Label("Front Gun", skin);
        table.add(frontGun);
        frontGunSelectBox = new SelectBox<FrontGun>( skin );
        frontGunSelectBox.setItems( FrontGun.values() );
        frontGunSelectBox.addListener( new ChangeListener(){
        	@Override
        	public void changed(ChangeEvent event, Actor actor){
        		// find the selected item
                Item selectedItem = FrontGun.values()[ frontGunSelectBox.getSelectedIndex() ];               
                // if the ship already contains the item, there is no need to buy it
                if( ship.contains( selectedItem ) ) return;
                // try and buy the item or reset all select boxes
                if( profile.buy( selectedItem ) ) {
                    credits.setText( profile.getCreditsAsText() );
                } else {
                    frontGunSelectBox.setSelectedIndex( ship.getShipModel().ordinal() );                    
                }
        	}
        });
        table.add(frontGunSelectBox).fillX().colspan(3);
        table.row();

        Label shield = new Label("Shield", skin);
        table.add(shield);
        shieldModelSelectBox = new SelectBox<Shield>( skin );
        shieldModelSelectBox.setItems( Shield.values() );
        shieldModelSelectBox.addListener( new ChangeListener(){
        	@Override
        	public void changed(ChangeEvent event, Actor actor){
        		// find the selected item
                Item selectedItem = ShipModel.values()[ shieldModelSelectBox.getSelectedIndex() ];               
                // if the ship already contains the item, there is no need to buy it
                if( ship.contains(selectedItem)) return;
                // try and buy the item or reset all select boxes
                if( profile.buy(selectedItem)) {
                    credits.setText(profile.getCreditsAsText());
                    updateValues();
                } else {
                    shipModelSelectBox.setSelectedIndex( ship.getShipModel().ordinal() );                    
                }
        	}
        });
        table.add(shieldModelSelectBox).fillX().colspan(3);
        table.row();

        table.add("Credits");
        credits = new Label( profile.getCreditsAsText(), skin );
        table.add(credits).left().colspan(4);
        table.row();

      //register the back button
  		TextButton backButton = new TextButton("Back to Main Menu", skin);
  		backButton.addListener(new ChangeListener(){
  			@Override
  			public void changed(ChangeEvent event, Actor actor){
  				game.getSoundManager().play( TyrianCloneSound.CLICK );
  				game.setScreen(game.getMenuScreen());
  			}
  		});
  		table.add(backButton).size(250, 60).colspan(5);

  		updateValues();
  		
        stage.addActor( table );
    }
    
    private void updateValues(){
    	//images
    	shipModelImage.setDrawable(new TextureRegionDrawable(getAtlas().findRegion("level-screen/" +ship.getShipModel().getSimpleName(), 0)));
    }
    
//	@Override
//	public void resize(int width, int height){
//		super.resize(width, height);
//		
//		table.setWidth(width);
//		table.setHeight(height);
//		
//		//we need a complete redraw
//		table.invalidateHierarchy();
//	}
}
