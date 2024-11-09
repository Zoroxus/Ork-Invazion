package com.orkin.game;


import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.orkin.game.Arme.*;
import com.orkin.game.Map.*;
import com.orkin.game.Unite.*;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;


public class OrkIn extends ApplicationAdapter {
	// Constant rows and columns of the sprite sheet
	private static final int FRAME_COLS = 1, FRAME_ROWS = 8;

	// Objects used
	Animation<TextureRegion> selectorAnimation;	// Must declare frame type
	Texture selectorSheet;
	SpriteBatch spriteBatch;

	// A variable for tracking elapsed time for the animation
	float stateTime;
	float textSoftwareDraw;
	float textUnitDraw;
	// Gameplay Textures
	private Texture moveTexture;
	private Texture attackTexture;
	// Unit textures
	//private Texture boyzTexture;
	// Weapon textures
	private Texture bolterRifleTexture;
	private Texture shootaRifleTexture;
	private Texture twinShootaRifleTexture;
	// Map Textures
	private Texture tileset;
	private TextureRegion tileBlank;
	private TextureRegion tileGrass;
	private TextureRegion tileWater;
	private TextureRegion tileRock;
	private TextureRegion tileBuilding;
	private TextureRegion borderUp;
	private TextureRegion borderCorner;
	private TextureRegion borderRight;
	// Interface Textures
	private Texture tilesetInterface;
	private TextureRegion tileIntTL;
	private TextureRegion tileIntTR;
	private TextureRegion tileIntTM;
	private TextureRegion tileIntML;
	private TextureRegion tileIntMR;
	private TextureRegion tileIntMM;
	private TextureRegion tileIntBR;
	private TextureRegion tileIntBL;
	private TextureRegion tileIntBM;
	private TextureRegion tileIntTextL;
	private TextureRegion tileIntTextM;
	private TextureRegion tileIntTextR;
	private TextureRegion redExclamation;


	private Music startMusic;
	private SpriteBatch batch;
	private Rectangle selector;
	private Viewport viewport;
	BitmapFont textFont;
	BitmapFont endFont;
	private tileData selectedTile;
	long startTime;
	long elapsedTime = 0;
	int endGame = 0;
	int knucklesMode = 0;
	int debugMode = 0;
	int drawInterface = 0;

	// Turn based system
	private Turn_based_system game = new Turn_based_system();
	private Map map = new Map(game);
	private Player human1 = new Player("Human 1");
	private Player human2 = new Player("Human 2");
	private Bot bot = new Bot(map);	
	private int winner;
	private int p2Units;
	private int checkedUnits = 0;
	private int turnPlayerUnits = 0;
	private int otherPlaterUnits = 0;
	private int tempCheck = 0;


	// For mouse handling
	private final Vector2 mouseInWorld2D = new Vector2();
	private final Vector3 mouseInWorld3D = new Vector3();
	private OrthographicCamera camera;

	float time = 0;
	
	@Override
	public void create () {
		// load the images, 64x64px
		batch = new SpriteBatch();
		moveTexture = new Texture("tileMove.png");
		attackTexture = new Texture("tileAttack.png");
		// Weapon Textures
		bolterRifleTexture = new Texture("bolter.png");
		shootaRifleTexture = new Texture("Shoota.png");
		twinShootaRifleTexture = new Texture("Twin_Shoota.png");
		tileset = new Texture("gridBlank.png");
		tileBlank = new TextureRegion(tileset, 0, 0, 64, 64);
		tileGrass = new TextureRegion(tileset, 64, 0, 64, 64);
		tileWater = new TextureRegion(tileset, 0, 64, 64, 64);
		tileRock = new TextureRegion(tileset, 64, 64, 64, 64);
		tileBuilding = new TextureRegion(tileset, 0, 128, 64, 64);
		borderUp = new TextureRegion(tileset, 0, 192, 64, 64);
		borderCorner = new TextureRegion(tileset, 64, 192, 64, 64);
		borderRight = new TextureRegion(tileset, 64, 256, 64, 64);
		// Interface Textures
		tilesetInterface = new Texture("guiTilesheet.png");
		tileIntTL = new TextureRegion(tilesetInterface, 0, 0, 64, 64);
		tileIntTM = new TextureRegion(tilesetInterface, 64, 0, 64, 64);
		tileIntTR = new TextureRegion(tilesetInterface, 128, 0, 64, 64);
		tileIntML = new TextureRegion(tilesetInterface, 0, 64, 64, 64);
		tileIntMM = new TextureRegion(tilesetInterface, 64, 64, 64, 64);
		tileIntMR = new TextureRegion(tilesetInterface, 128, 64, 64, 64);
		tileIntBL = new TextureRegion(tilesetInterface, 0, 128, 64, 64);
		tileIntBM = new TextureRegion(tilesetInterface, 64, 128, 64, 64);
		tileIntBR = new TextureRegion(tilesetInterface, 128, 128, 64, 64);
		tileIntTextL = new TextureRegion(tilesetInterface, 0*64, 64*3, 64, 64);
		tileIntTextM = new TextureRegion(tilesetInterface, 1*64, 64*3, 64, 64);
		tileIntTextR = new TextureRegion(tilesetInterface, 2*64, 64*3, 64, 64);

		redExclamation = new TextureRegion(tilesetInterface, 5*64, 64*5, 64, 64);

		// Load the spriteSheet as a texture
		selectorSheet = new Texture("tileSelector_sheet.png");
		// Use the split utility method to create a 2d array of TextureRegions.
		TextureRegion[][] tmp = TextureRegion.split(selectorSheet, selectorSheet.getWidth() / FRAME_COLS, selectorSheet.getHeight()/FRAME_ROWS);
		// Place the regions into a 1D array for the Animation constructor.
		TextureRegion[] selectorFrames = new TextureRegion[FRAME_COLS * FRAME_ROWS];
		int index = 0;
		for (int i = 0; i < FRAME_ROWS; i++) {
			for (int j = 0; j < FRAME_COLS; j++) {
				selectorFrames[index++] = tmp[i][j];
			}
		}

		// Initialize the Animation with the frame interval and array of frames
		selectorAnimation = new Animation<>(0.1f, selectorFrames);

		// Load the background music at launch
		startMusic = Gdx.audio.newMusic(Gdx.files.internal("Legio Symphonica - Sol Invictus.wav"));
		startMusic.setLooping(true);
		startMusic.setVolume(0f);
		startMusic.play();

		// Create the camera
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 1856, 1088);
		viewport = new FitViewport(1856, 1088, camera);	// Viewport

		// Create the SpriteBatch
		batch = new SpriteBatch();
		stateTime = 0f;
		textSoftwareDraw = 0f;
		textUnitDraw = 0f;

		selector = new Rectangle();
		selector.x = (20*64)/2;
		selector.y = (10*64)/2;
		selector.width = 64;
		selector.height = 64;

		// Fonts
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Arial.ttf"));
		FreeTypeFontParameter parameter = new FreeTypeFontParameter();
		parameter.size = 16+8;
		textFont = generator.generateFont(parameter);
		generator.dispose();

		// Generate the map properly
		// Here because we want it once
		map.setTileUnite(1, 3, new Boyz());
		map.getTileUnite(1, 3).setOwner(bot);
		map.getTileUnite(1, 3).setController(bot);
		map.setTileUnite(1, 5, new Nobz());
		map.getTileUnite(1, 5).setOwner(bot);
		map.getTileUnite(1, 5).setController(bot);
		map.setTileUnite(1, 7, new Warboss());
		map.getTileUnite(1, 7).setOwner(bot);
		map.getTileUnite(1, 7).setController(bot);
		map.setTileUnite(1, 9, new Nobz());
		map.getTileUnite(1, 9).setOwner(bot);
		map.getTileUnite(1, 9).setController(bot);

		map.setTileUnite(1, 11, new Boyz());
		map.getTileUnite(1, 11).setOwner(bot);
		map.getTileUnite(1, 11).setController(bot);
		map.getTileUnite(1, 11).setNumber(5);;
		map.getTileUnite(1, 11).setArme(new Shoota());;


		map.setTileUnite(18, 5, new Intercessor());
		map.getTileUnite(18, 5).setOwner(human1);
		map.getTileUnite(18, 5).setController(human1);
		map.setTileUnite(18, 7, new Intercessor());
		map.getTileUnite(18, 7).setOwner(human1);
		map.getTileUnite(18, 7).setController(human1);
		map.setTileUnite(18, 9, new Intercessor());
		map.getTileUnite(18, 9).setOwner(human1);
		map.getTileUnite(18, 9).setController(human1);


		//map.setTileUnite(1, 2, new Deathmark());
		//map.getTileUnite(1, 2).setOwner(bot);
		//map.getTileUnite(1, 2).setController(bot);
		//map.setTileUnite(18, 2, new Deathmark());
		//map.getTileUnite(18, 2).setOwner(human1);
		//map.getTileUnite(18, 2).setController(human1);

		if(map.getTileUnite(1, 1)!=null && map.getTileTypeString(1, 1)=="Water") map.moveUnite(1,1,1+1,1+1);
		map.setTileArme(19, 5, new Slugga());

		// Start Turn Based Logic !
		game.addPlayer(human1);
		game.addPlayer(bot);
		game.startGame();

		//
	}

	// Génération des polices dans Resize()
	@Override
	public void resize(int width, int height) {
        viewport.update(width, height);
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Arial.ttf"));
		FreeTypeFontParameter parameter = new FreeTypeFontParameter();
		parameter.size = 24;
		textFont = generator.generateFont(parameter);
		generator.dispose();
    }

	@Override
	public void render () {
		time += Gdx.graphics.getDeltaTime(); // This will add to your time variable the time between two frames
		ScreenUtils.clear(Color.valueOf("#888d8f"));
		stateTime += Gdx.graphics.getDeltaTime();

		// Decrement the time left for each text drawn !
		textSoftwareDraw -= 1;
		textUnitDraw -= 1;

		TextureRegion currentFrame = selectorAnimation.getKeyFrame(stateTime, true);	// tileSelector : Get current frame of animaton for the current stateTime

		// Count the number of units for each player
		for (tileData[] tileDatas : map.getMap()) {
			for (tileData tileData : tileDatas) {
				Unite unit = tileData.getUnite();
				if(unit==null) continue;
				if(unit.getController()==game.getTurnPlayer() && game.getTurnPlayer()!=bot) {
					//System.out.println("Unit detected : "+unit.toString());
					//if(game.getCurrentPhase()!="Movement") unit.setEveryState(false);
					turnPlayerUnits++;
				} else otherPlaterUnits++;
				if(tileData.getTypeString() =="Rock") {
					int i = tileData.x;
					int j = tileData.y;
					System.out.println(map.getTileUnite(i, j).toString()+": I can't be here !");
					if(i<10){
						if(map.canMoveUnite(i,j,i-1,j)==true) map.moveUnite(i,j,i-1,j);
						else if(map.canMoveUnite(i,j,i-1,j-1)==true) map.moveUnite(i,j,i-1,j-1);
						else if(map.canMoveUnite(i,j,i-1,j+1)==true) map.moveUnite(i,j,i-1,j+1);
					}else{
						if(map.canMoveUnite(i,j,i+1,j)==true) map.moveUnite(i,j,i+1,j);
						else if(map.canMoveUnite(i,j,i+1,j-1)==true) map.moveUnite(i,j,i+1,j-1);
						else if(map.canMoveUnite(i,j,i+1,j+1)==true) map.moveUnite(i,j,i+1,j+1);
					}
				}
				if(unit.getController()!=game.getTurnPlayer()) continue;
				if(unit.hasMoved()==true && (unit.hasShoot()==true || !map.canAttackAny(tileData.getX(), tileData.getY()))) tempCheck++;
			}
		}

		camera.update();	// Update the camera and set the batch to the camera projection
		batch.setProjectionMatrix(camera.combined);

		// Do the Pre-Game phase before drawing /\
		//if(game.getCurrentPhase()=="Pre-Game") game.nextPhase();

		batch.begin();	// Begin the batch for drawing

		// Draw the tiles
		for(int i = 0; i < 20*64; i+=64) {
			for(int j = 0; j < 15*64; j+=64) {
				switch(map.printTile(i/64, j/64)) {
					case "Grass":
						batch.draw(tileGrass, i, j);
						break;
					case "Water":
						batch.draw(tileWater, i, j);
						break;
					case "Rock":
						batch.draw(tileRock, i, j);
						break;
					case "Building":
						batch.draw(tileBuilding, i, j);
						break;
					default:
						batch.draw(tileBlank, i, j);
				}
				//textFont.draw(batch, i/64+":"+j/64, i+3, j+28);
			}
		}

		// And the interface

		batch.draw(tileIntTL, 64*21, 15*64);
		batch.draw(tileIntTM, 64*22, 15*64);
		batch.draw(tileIntTR, 64*23, 15*64);
		batch.draw(tileIntML, 64*21, 14*64);
		batch.draw(tileIntMM, 64*22, 14*64);
		batch.draw(tileIntMR, 64*23, 14*64);
		batch.draw(tileIntBL, 64*21, 13*64);
		batch.draw(tileIntBM, 64*22, 13*64);
		batch.draw(tileIntBR, 64*23, 13*64);

		for (int i = 2; i < 13; i++) {
			batch.draw(tileIntTextL, 64*20, 64*i);
			batch.draw(tileIntTextM, 64*21, 64*i);
			batch.draw(tileIntTextM, 64*22, 64*i);
			batch.draw(tileIntTextM, 64*23, 64*i);
			batch.draw(tileIntTextR, 64*24, 64*i);

			batch.draw(tileIntTextL, 64*25, 64*i);
			batch.draw(tileIntTextM, 64*26, 64*i);
			batch.draw(tileIntTextM, 64*27, 64*i);
			batch.draw(tileIntTextR, 64*28, 64*i);	
		}

		batch.draw(tileIntTL, 64*25, 64*15);
		batch.draw(tileIntTM, 64*26, 64*15);
		batch.draw(tileIntTM, 64*27, 64*15);
		batch.draw(tileIntTR, 64*28, 64*15);

		batch.draw(tileIntML, 64*25, 64*14);
		batch.draw(tileIntMM, 64*26, 64*14);
		batch.draw(tileIntMM, 64*27, 64*14);
		batch.draw(tileIntMR, 64*28, 64*14);

		batch.draw(tileIntBL, 64*25, 64*13);
		batch.draw(tileIntBM, 64*26, 64*13);
		batch.draw(tileIntBM, 64*27, 64*13);
		batch.draw(tileIntBR, 64*28, 64*13);

		batch.draw(tileIntTextL, 64*1, 16*64);
		batch.draw(tileIntTextM, 64*2, 16*64);
		batch.draw(tileIntTextM, 64*3, 16*64);
		batch.draw(tileIntTextM, 64*4, 16*64);
		batch.draw(tileIntTextR, 64*5, 16*64);

		batch.draw(tileIntTextL, 64*6, 16*64);
		batch.draw(tileIntTextM, 64*7, 16*64);
		batch.draw(tileIntTextM, 64*8, 16*64);
		batch.draw(tileIntTextR, 64*9, 16*64);

		
		textFont.setColor(0f, 0f, 0f, 0.50f);
		textFont.draw(batch, "Turn Player: "+game.getTurnPlayer().getName(), 64*1+23, 17*64-25);
		textFont.setColor(1, 1, 1, 1);
		textFont.draw(batch, "Turn Player: "+game.getTurnPlayer().getName(), 64*1+20, 17*64-22);

		drawText("Turns left : "+game.getTurnCounter(), 64*6, 64*17);

		//batch.draw(tileIntTextL, 64*10, 16*64);
		//batch.draw(tileIntTextM, 64*11, 16*64);
		//batch.draw(tileIntTextM, 64*12, 16*64);
		//batch.draw(tileIntTextR, 64*13, 16*64);
		//textFont.setColor(0f, 0f, 0f, 0.50f);
		//textFont.draw(batch, "Phase : "+"for 1.0"/*game.getCurrentPhase()*/, 64*10+23, 17*64-25);
		//textFont.setColor(1, 1, 1, 1);
		//textFont.draw(batch, "Phase : "+"for 1.0"/*game.getCurrentPhase()*/, 64*10+20, 17*64-22);

		if(debugMode==1){
			textFont.draw(batch, "DEBUG :", 64*10, 64*17-22);
			textFont.draw(batch, "FPS = "+ Gdx.graphics.getFramesPerSecond(), 64*12, 64*17-22);
			textFont.draw(batch, "tempCheck : "+tempCheck, 64*14+20, 64*17-22);
			textFont.draw(batch, "turnPlayerUnits : "+turnPlayerUnits, 64*17+20, 64*17-22);
		}

		// Draw the selected unit and it's name if one
		if(selectedTile != null && selectedTile.getUnite()!=null) {
			if(knucklesMode==0) batch.draw(selectedTile.getUnite().getSprite(), 64*21+32, 13*64+32, 64+64, 64+64);
			else batch.draw(selectedTile.getUnite().getKnuckles(), 64*21+32, 13*64+32, 64+64, 64+64);

			textFont.setColor(0f, 0f, 0f, 0.50f);
			textFont.draw(batch, selectedTile.getUnite().toString(), 64*20+20+3, 13*64-22-3);
			textFont.setColor(1, 1, 1, 1);
			textFont.draw(batch, selectedTile.getUnite().toString(), 64*20+20, 13*64-22);

			drawText("M  : "+selectedTile.getUnite().getM()/3, 64*20, 64*12);
			drawText("T  : "+selectedTile.getUnite().getT(), 64*20, 64*11);
			drawText("Sv : "+selectedTile.getUnite().getSv(), 64*20, 64*10);
			drawText("W  : "+selectedTile.getUnite().getW()+" / "+selectedTile.getUnite().getFullW(), 64*20, 64*9);
			//textFont.draw(batch, "Cm  : "+selectedTile.getUnite().getCm(), 64*21+20, 7*64-22);
			drawText("Nb  : "+selectedTile.getUnite().getNumber(), 64*20, 64*8);
			drawText(selectedTile.getUnite().getMeleeWeapon().toString(), 64*20, 64*7);
			drawText(selectedTile.getUnite().getRangeWeapon().toString()+" "+selectedTile.getUnite().getRangeWeapon().getRange()+"\"", 64*20, 64*6);
			textFont.draw(batch, "XP  : "+selectedTile.getUnite().getXP(), 64*20+20, 5*64-22);
			if(selectedTile.getUnite().hasFought()==true || selectedTile.getUnite().hasShoot()==true) textFont.draw(batch, "The unit has fought !", 64*20+20, 4*64-22);
			else textFont.draw(batch, "The unit hasn't fought !", 64*20+20, 4*64-22);
			if(selectedTile.getUnite().hasMoved()==true) textFont.draw(batch, "Has Moved:"+selectedTile.getUnite().hasMoved(), 64*20+20, 3*64-22);
			else textFont.draw(batch, "Has Moved:"+selectedTile.getUnite().hasMoved(), 64*20+20, 3*64-22);

			batch.draw(selectedTile.getUnite().getRangeWeapon().getSprite(), 64*25, 13*64+32, 64*4, 64*2);

			drawText(selectedTile.getUnite().getRangeWeapon().toString(), 64*25, 64*13);
			drawText("Range  : "+selectedTile.getUnite().getRangeWeapon().getRange()/3, 64*25, 64*12);
			drawText("A  : "+selectedTile.getUnite().getRangeWeapon().getA(), 64*25, 64*11);
			drawText("HIT : "+selectedTile.getUnite().getRangeWeapon().getHIT(), 64*25, 64*10);
			drawText("S  : "+selectedTile.getUnite().getRangeWeapon().getS(), 64*25, 64*9);
			drawText("AP  : "+selectedTile.getUnite().getRangeWeapon().getAP(), 64*25, 64*8);
			textFont.draw(batch, "Range  : "+selectedTile.getUnite().getRangeWeapon().getRange()/3, 64*25+20, 12*64-22);
			textFont.draw(batch, "A  : "+selectedTile.getUnite().getRangeWeapon().getA(), 64*25+20, 11*64-22);
			textFont.draw(batch, "HIT : "+selectedTile.getUnite().getRangeWeapon().getHIT(), 64*25+20, 10*64-22);
			textFont.draw(batch, "S  : "+selectedTile.getUnite().getRangeWeapon().getS(), 64*25+20, 9*64-22);
			textFont.draw(batch, "AP  : "+selectedTile.getUnite().getRangeWeapon().getAP(), 64*25+20, 8*64-22);

			drawText(selectedTile.getUnite().getMeleeWeapon().toString(), 64*25, 64*7);
			drawText("A  : "+selectedTile.getUnite().getMeleeWeapon().getA(), 64*25, 64*6);
			drawText("HIT : "+selectedTile.getUnite().getMeleeWeapon().getHIT(), 64*25, 64*5);
			drawText("S  : "+selectedTile.getUnite().getMeleeWeapon().getS(), 64*25, 64*4);
			drawText("AP  : "+selectedTile.getUnite().getMeleeWeapon().getAP(), 64*25, 64*3);
		}


		// The borders of the map
		for(int i = 0; i < 1280+256; i+=64) {
		 	for(int j = 0; j < 960+128; j+=64) {
		 		if(i<64*20 && j==15*64) batch.draw(borderUp, i, j);
		 		if(i==64*20 && j<15*64) batch.draw(borderRight, i, j);
				if(i==64*20 && j==15*64) batch.draw(borderCorner, i, j);
			}
		}


		// Draw the content of the tiles
		for(int i = 0; i < 20*64; i+=64) {
			for(int j = 0; j < 15*64; j+=64) {
				if(selectedTile != null && map.canMoveUnite(selectedTile.getX(), selectedTile.getY(), i/64, j/64)) {
					batch.draw(moveTexture, i, j);
				}
				if(map.getTileArme(i/64, j/64) instanceof Arme) {
					batch.draw(map.getTileArme(i/64, j/64).getSprite(), i+13, j+16);
				}
				if(map.getTileUnite(i/64, j/64)!=null && knucklesMode==0) {
					switch (map.getTileUnite(i/64, j/64).getNumber()) {
						case 1:
							batch.draw(map.getTileUnite(i/64, j/64).getSprite(), i, j+16, 64, 64);
							break;
						case 2:
							batch.draw(map.getTileUnite(i/64, j/64).getSprite(), i+32, j+16, 31, 31);
							batch.draw(map.getTileUnite(i/64, j/64).getSprite(), i, j+16, 31, 31);
							break;
						case 3:
							batch.draw(map.getTileUnite(i/64, j/64).getSprite(), i, j+48, 31, 31);
							batch.draw(map.getTileUnite(i/64, j/64).getSprite(), i+32, j+16, 31, 31);
							batch.draw(map.getTileUnite(i/64, j/64).getSprite(), i, j+16, 31, 31);
							break;
						case 4:
							batch.draw(map.getTileUnite(i/64, j/64).getSprite(), i+32, j+48, 31, 31);
							batch.draw(map.getTileUnite(i/64, j/64).getSprite(), i, j+48, 31, 31);
							batch.draw(map.getTileUnite(i/64, j/64).getSprite(), i+32, j+16, 31, 31);
							batch.draw(map.getTileUnite(i/64, j/64).getSprite(), i, j+16, 31, 31);
							break;
						case 5:
							batch.draw(map.getTileUnite(i/64, j/64).getSprite(), i+32, j+48, 31, 31);
							batch.draw(map.getTileUnite(i/64, j/64).getSprite(), i, j+48, 31, 31);
							batch.draw(map.getTileUnite(i/64, j/64).getSprite(), i+16, j+32, 31, 31);
							batch.draw(map.getTileUnite(i/64, j/64).getSprite(), i+32, j+16, 31, 31);
							batch.draw(map.getTileUnite(i/64, j/64).getSprite(), i, j+16, 31, 31);
							break;
						case 6:
							batch.draw(map.getTileUnite(i/64, j/64).getSprite(), i+(64/6*2)-2, j+(64/4*3)+3, 31, 31);

							batch.draw(map.getTileUnite(i/64, j/64).getSprite(), i+(64/6*4)-2, j+(64/4*2)+3, 31, 31);
							batch.draw(map.getTileUnite(i/64, j/64).getSprite(), i+(64/6*0)-2, j+(64/4*2)+3, 31, 31);

							batch.draw(map.getTileUnite(i/64, j/64).getSprite(), i+(64/6*2)-2, j+(64/4*1)+3, 31, 31);

							batch.draw(map.getTileUnite(i/64, j/64).getSprite(), i+(64/6*4)-2, j+(64/4*0)+3, 31, 31);
							batch.draw(map.getTileUnite(i/64, j/64).getSprite(), i+(64/6*0)-2, j+(64/4*0)+3, 31, 31);

							break;
						case 7:
							batch.draw(map.getTileUnite(i/64, j/64).getSprite(), i+(64/6*2)-2, j+(64/4*3)+3, 31, 31);

							batch.draw(map.getTileUnite(i/64, j/64).getSprite(), i+(64/6*4)-2, j+(64/4*2)+3, 31, 31);
							batch.draw(map.getTileUnite(i/64, j/64).getSprite(), i+(64/6*0)-2, j+(64/4*2)+3, 31, 31);

							batch.draw(map.getTileUnite(i/64, j/64).getSprite(), i+(64/6*3)-2, j+(64/4*1)+3, 31, 31);
							batch.draw(map.getTileUnite(i/64, j/64).getSprite(), i+(64/6*1)-2, j+(64/4*1)+3, 31, 31);

							batch.draw(map.getTileUnite(i/64, j/64).getSprite(), i+(64/6*4)-2, j+(64/4*0)+3, 31, 31);
							batch.draw(map.getTileUnite(i/64, j/64).getSprite(), i+(64/6*0)-2, j+(64/4*0)+3, 31, 31);

							break;
						case 8:
							batch.draw(map.getTileUnite(i/64, j/64).getSprite(), i+(64/6*3)-2, j+(64/4*3)+3, 31, 31);
							batch.draw(map.getTileUnite(i/64, j/64).getSprite(), i+(64/6*1)-2, j+(64/4*3)+3, 31, 31);

							batch.draw(map.getTileUnite(i/64, j/64).getSprite(), i+(64/6*4)-2, j+(64/4*2)+3, 31, 31);
							batch.draw(map.getTileUnite(i/64, j/64).getSprite(), i+(64/6*0)-2, j+(64/4*2)+3, 31, 31);

							batch.draw(map.getTileUnite(i/64, j/64).getSprite(), i+(64/6*3)-2, j+(64/4*1)+3, 31, 31);
							batch.draw(map.getTileUnite(i/64, j/64).getSprite(), i+(64/6*1)-2, j+(64/4*1)+3, 31, 31);

							batch.draw(map.getTileUnite(i/64, j/64).getSprite(), i+(64/6*4)-2, j+(64/4*0)+3, 31, 31);
							batch.draw(map.getTileUnite(i/64, j/64).getSprite(), i+(64/6*0)-2, j+(64/4*0)+3, 31, 31);

							break;
						case 9:
							batch.draw(map.getTileUnite(i/64, j/64).getSprite(), i+(64/6*3)-2, j+(64/4*3)+3, 31, 31);
							batch.draw(map.getTileUnite(i/64, j/64).getSprite(), i+(64/6*1)-2, j+(64/4*3)+3, 31, 31);

							batch.draw(map.getTileUnite(i/64, j/64).getSprite(), i+(64/6*4)-2, j+(64/4*2)+3, 31, 31);
							batch.draw(map.getTileUnite(i/64, j/64).getSprite(), i+(64/6*0)-2, j+(64/4*2)+3, 31, 31);

							batch.draw(map.getTileUnite(i/64, j/64).getSprite(), i+(64/6*3)-2, j+(64/4*1)+3, 31, 31);
							batch.draw(map.getTileUnite(i/64, j/64).getSprite(), i+(64/6*1)-2, j+(64/4*1)+3, 31, 31);

							batch.draw(map.getTileUnite(i/64, j/64).getSprite(), i+(64/6*4)-2, j+(64/4*0)+3, 31, 31);
							batch.draw(map.getTileUnite(i/64, j/64).getSprite(), i+(64/6*2)-2, j+(64/4*0)+3, 31, 31);
							batch.draw(map.getTileUnite(i/64, j/64).getSprite(), i+(64/6*0)-2, j+(64/4*0)+3, 31, 31);

							break;
						case 10:
							batch.draw(map.getTileUnite(i/64, j/64).getSprite(), i+(64/6*3)-2, j+(64/4*3)+3, 31, 31);
							batch.draw(map.getTileUnite(i/64, j/64).getSprite(), i+(64/6*1)-2, j+(64/4*3)+3, 31, 31);

							batch.draw(map.getTileUnite(i/64, j/64).getSprite(), i+(64/6*4)-2, j+(64/4*2)+3, 31, 31);
							batch.draw(map.getTileUnite(i/64, j/64).getSprite(), i+(64/6*2)-2, j+(64/4*2)+3, 31, 31);
							batch.draw(map.getTileUnite(i/64, j/64).getSprite(), i+(64/6*0)-2, j+(64/4*2)+3, 31, 31);

							batch.draw(map.getTileUnite(i/64, j/64).getSprite(), i+(64/6*3)-2, j+(64/4*1)+3, 31, 31);
							batch.draw(map.getTileUnite(i/64, j/64).getSprite(), i+(64/6*1)-2, j+(64/4*1)+3, 31, 31);

							batch.draw(map.getTileUnite(i/64, j/64).getSprite(), i+(64/6*4)-2, j+(64/4*0)+3, 31, 31);
							batch.draw(map.getTileUnite(i/64, j/64).getSprite(), i+(64/6*2)-2, j+(64/4*0)+3, 31, 31);
							batch.draw(map.getTileUnite(i/64, j/64).getSprite(), i+(64/6*0)-2, j+(64/4*0)+3, 31, 31);

							break;
						default:
							batch.draw(map.getTileUnite(i/64, j/64).getSprite(), i, j, 64, 64);
							batch.draw(map.getTileUnite(i/64, j/64).getSprite(), i+(64/6*2)-2, j+(64/4*3)+3, 31, 31);
							batch.draw(map.getTileUnite(i/64, j/64).getSprite(), i+(64/6*1)-2, j+(64/4*3)+3, 31, 31);
							batch.draw(map.getTileUnite(i/64, j/64).getSprite(), i+(64/6*0)-2, j+(64/4*3)+3, 31, 31);

							batch.draw(map.getTileUnite(i/64, j/64).getSprite(), i+(64/6*3)-2, j+(64/4*2)+3, 31, 31);
							batch.draw(map.getTileUnite(i/64, j/64).getSprite(), i+(64/6*2)-2, j+(64/4*2)+3, 31, 31);
							batch.draw(map.getTileUnite(i/64, j/64).getSprite(), i+(64/6*1)-2, j+(64/4*2)+3, 31, 31);
							batch.draw(map.getTileUnite(i/64, j/64).getSprite(), i+(64/6*0)-2, j+(64/4*2)+3, 31, 31);

							batch.draw(map.getTileUnite(i/64, j/64).getSprite(), i+(64/6*3)-2, j+(64/4*1)+3, 31, 31);
							batch.draw(map.getTileUnite(i/64, j/64).getSprite(), i+(64/6*2)-2, j+(64/4*1)+3, 31, 31);
							batch.draw(map.getTileUnite(i/64, j/64).getSprite(), i+(64/6*1)-2, j+(64/4*1)+3, 31, 31);
							batch.draw(map.getTileUnite(i/64, j/64).getSprite(), i+(64/6*0)-2, j+(64/4*1)+3, 31, 31);

							batch.draw(map.getTileUnite(i/64, j/64).getSprite(), i+(64/6*3)-2, j+(64/4*0)+3, 31, 31);
							batch.draw(map.getTileUnite(i/64, j/64).getSprite(), i+(64/6*2)-2, j+(64/4*0)+3, 31, 31);
							batch.draw(map.getTileUnite(i/64, j/64).getSprite(), i+(64/6*1)-2, j+(64/4*0)+3, 31, 31);
							batch.draw(map.getTileUnite(i/64, j/64).getSprite(), i+(64/6*0)-2, j+(64/4*0)+3, 31, 31);
							break;
					}
					if((!map.getTileUnite(i/64, j/64).hasFought() || !map.getTileUnite(i/64, j/64).hasShoot()) 
					&& map.getTileUnite(i/64, j/64).getController() == game.getTurnPlayer()
					&& map.canAttackAny(i/64, j/64))
					batch.draw(redExclamation, i, j+16*3, 64, 64);
				}
				if(map.getTileUnite(i/64, j/64)!=null && knucklesMode==1) {
					switch (map.getTileUnite(i/64, j/64).getNumber()) {
						case 1:
							batch.draw(map.getTileUnite(i/64, j/64).getKnuckles(), i, j+16, 64, 64);
							break;
						case 2:
							batch.draw(map.getTileUnite(i/64, j/64).getKnuckles(), i+32, j+16, 31, 31);
							batch.draw(map.getTileUnite(i/64, j/64).getKnuckles(), i, j+16, 31, 31);
							break;
						case 3:
							batch.draw(map.getTileUnite(i/64, j/64).getKnuckles(), i, j+48, 31, 31);
							batch.draw(map.getTileUnite(i/64, j/64).getKnuckles(), i+32, j+16, 31, 31);
							batch.draw(map.getTileUnite(i/64, j/64).getKnuckles(), i, j+16, 31, 31);
							break;
						case 4:
							batch.draw(map.getTileUnite(i/64, j/64).getKnuckles(), i+32, j+48, 31, 31);
							batch.draw(map.getTileUnite(i/64, j/64).getKnuckles(), i, j+48, 31, 31);
							batch.draw(map.getTileUnite(i/64, j/64).getKnuckles(), i+32, j+16, 31, 31);
							batch.draw(map.getTileUnite(i/64, j/64).getKnuckles(), i, j+16, 31, 31);
							break;
						case 5:
							batch.draw(map.getTileUnite(i/64, j/64).getKnuckles(), i+32, j+48, 31, 31);
							batch.draw(map.getTileUnite(i/64, j/64).getKnuckles(), i, j+48, 31, 31);
							batch.draw(map.getTileUnite(i/64, j/64).getKnuckles(), i+16, j+32, 31, 31);
							batch.draw(map.getTileUnite(i/64, j/64).getKnuckles(), i+32, j+16, 31, 31);
							batch.draw(map.getTileUnite(i/64, j/64).getKnuckles(), i, j+16, 31, 31);
							break;
						case 6:
							batch.draw(map.getTileUnite(i/64, j/64).getKnuckles(), i+(64/6*2)-2, j+(64/4*3)+3, 31, 31);
							batch.draw(map.getTileUnite(i/64, j/64).getKnuckles(), i+(64/6*4)-2, j+(64/4*2)+3, 31, 31);
							batch.draw(map.getTileUnite(i/64, j/64).getKnuckles(), i+(64/6*0)-2, j+(64/4*2)+3, 31, 31);
							batch.draw(map.getTileUnite(i/64, j/64).getKnuckles(), i+(64/6*2)-2, j+(64/4*1)+3, 31, 31);
							batch.draw(map.getTileUnite(i/64, j/64).getKnuckles(), i+(64/6*4)-2, j+(64/4*0)+3, 31, 31);
							batch.draw(map.getTileUnite(i/64, j/64).getKnuckles(), i+(64/6*0)-2, j+(64/4*0)+3, 31, 31);
							break;
						case 7:
							batch.draw(map.getTileUnite(i/64, j/64).getKnuckles(), i+(64/6*2)-2, j+(64/4*3)+3, 31, 31);
							batch.draw(map.getTileUnite(i/64, j/64).getKnuckles(), i+(64/6*4)-2, j+(64/4*2)+3, 31, 31);
							batch.draw(map.getTileUnite(i/64, j/64).getKnuckles(), i+(64/6*0)-2, j+(64/4*2)+3, 31, 31);
							batch.draw(map.getTileUnite(i/64, j/64).getKnuckles(), i+(64/6*3)-2, j+(64/4*1)+3, 31, 31);
							batch.draw(map.getTileUnite(i/64, j/64).getKnuckles(), i+(64/6*1)-2, j+(64/4*1)+3, 31, 31);
							batch.draw(map.getTileUnite(i/64, j/64).getKnuckles(), i+(64/6*4)-2, j+(64/4*0)+3, 31, 31);
							batch.draw(map.getTileUnite(i/64, j/64).getKnuckles(), i+(64/6*0)-2, j+(64/4*0)+3, 31, 31);
							break;
						case 8:
							batch.draw(map.getTileUnite(i/64, j/64).getKnuckles(), i+(64/6*3)-2, j+(64/4*3)+3, 31, 31);
							batch.draw(map.getTileUnite(i/64, j/64).getKnuckles(), i+(64/6*1)-2, j+(64/4*3)+3, 31, 31);
							batch.draw(map.getTileUnite(i/64, j/64).getKnuckles(), i+(64/6*4)-2, j+(64/4*2)+3, 31, 31);
							batch.draw(map.getTileUnite(i/64, j/64).getKnuckles(), i+(64/6*0)-2, j+(64/4*2)+3, 31, 31);
							batch.draw(map.getTileUnite(i/64, j/64).getKnuckles(), i+(64/6*3)-2, j+(64/4*1)+3, 31, 31);
							batch.draw(map.getTileUnite(i/64, j/64).getKnuckles(), i+(64/6*1)-2, j+(64/4*1)+3, 31, 31);
							batch.draw(map.getTileUnite(i/64, j/64).getKnuckles(), i+(64/6*4)-2, j+(64/4*0)+3, 31, 31);
							batch.draw(map.getTileUnite(i/64, j/64).getKnuckles(), i+(64/6*0)-2, j+(64/4*0)+3, 31, 31);
							break;
						case 9:
							batch.draw(map.getTileUnite(i/64, j/64).getKnuckles(), i+(64/6*3)-2, j+(64/4*3)+3, 31, 31);
							batch.draw(map.getTileUnite(i/64, j/64).getKnuckles(), i+(64/6*1)-2, j+(64/4*3)+3, 31, 31);
							batch.draw(map.getTileUnite(i/64, j/64).getKnuckles(), i+(64/6*4)-2, j+(64/4*2)+3, 31, 31);
							batch.draw(map.getTileUnite(i/64, j/64).getKnuckles(), i+(64/6*0)-2, j+(64/4*2)+3, 31, 31);
							batch.draw(map.getTileUnite(i/64, j/64).getKnuckles(), i+(64/6*3)-2, j+(64/4*1)+3, 31, 31);
							batch.draw(map.getTileUnite(i/64, j/64).getKnuckles(), i+(64/6*1)-2, j+(64/4*1)+3, 31, 31);
							batch.draw(map.getTileUnite(i/64, j/64).getKnuckles(), i+(64/6*4)-2, j+(64/4*0)+3, 31, 31);
							batch.draw(map.getTileUnite(i/64, j/64).getKnuckles(), i+(64/6*2)-2, j+(64/4*0)+3, 31, 31);
							batch.draw(map.getTileUnite(i/64, j/64).getKnuckles(), i+(64/6*0)-2, j+(64/4*0)+3, 31, 31);
							break;
						case 10:
							batch.draw(map.getTileUnite(i/64, j/64).getKnuckles(), i+(64/6*3)-2, j+(64/4*3)+3, 31, 31);
							batch.draw(map.getTileUnite(i/64, j/64).getKnuckles(), i+(64/6*1)-2, j+(64/4*3)+3, 31, 31);
							batch.draw(map.getTileUnite(i/64, j/64).getKnuckles(), i+(64/6*4)-2, j+(64/4*2)+3, 31, 31);
							batch.draw(map.getTileUnite(i/64, j/64).getKnuckles(), i+(64/6*2)-2, j+(64/4*2)+3, 31, 31);
							batch.draw(map.getTileUnite(i/64, j/64).getKnuckles(), i+(64/6*0)-2, j+(64/4*2)+3, 31, 31);
							batch.draw(map.getTileUnite(i/64, j/64).getKnuckles(), i+(64/6*3)-2, j+(64/4*1)+3, 31, 31);
							batch.draw(map.getTileUnite(i/64, j/64).getKnuckles(), i+(64/6*1)-2, j+(64/4*1)+3, 31, 31);
							batch.draw(map.getTileUnite(i/64, j/64).getKnuckles(), i+(64/6*4)-2, j+(64/4*0)+3, 31, 31);
							batch.draw(map.getTileUnite(i/64, j/64).getKnuckles(), i+(64/6*2)-2, j+(64/4*0)+3, 31, 31);
							batch.draw(map.getTileUnite(i/64, j/64).getKnuckles(), i+(64/6*0)-2, j+(64/4*0)+3, 31, 31);
							break;
						default:
							batch.draw(map.getTileUnite(i/64, j/64).getKnuckles(), i+(64/6*3)-2, j+(64/4*3)+3, 31, 31);
							batch.draw(map.getTileUnite(i/64, j/64).getKnuckles(), i+(64/6*2)-2, j+(64/4*3)+3, 31, 31);
							batch.draw(map.getTileUnite(i/64, j/64).getKnuckles(), i+(64/6*1)-2, j+(64/4*3)+3, 31, 31);
							batch.draw(map.getTileUnite(i/64, j/64).getKnuckles(), i+(64/6*0)-2, j+(64/4*3)+3, 31, 31);
							batch.draw(map.getTileUnite(i/64, j/64).getKnuckles(), i+(64/6*3)-2, j+(64/4*2)+3, 31, 31);
							batch.draw(map.getTileUnite(i/64, j/64).getKnuckles(), i+(64/6*2)-2, j+(64/4*2)+3, 31, 31);
							batch.draw(map.getTileUnite(i/64, j/64).getKnuckles(), i+(64/6*1)-2, j+(64/4*2)+3, 31, 31);
							batch.draw(map.getTileUnite(i/64, j/64).getKnuckles(), i+(64/6*0)-2, j+(64/4*2)+3, 31, 31);
							batch.draw(map.getTileUnite(i/64, j/64).getKnuckles(), i+(64/6*3)-2, j+(64/4*1)+3, 31, 31);
							batch.draw(map.getTileUnite(i/64, j/64).getKnuckles(), i+(64/6*2)-2, j+(64/4*1)+3, 31, 31);
							batch.draw(map.getTileUnite(i/64, j/64).getKnuckles(), i+(64/6*1)-2, j+(64/4*1)+3, 31, 31);
							batch.draw(map.getTileUnite(i/64, j/64).getKnuckles(), i+(64/6*0)-2, j+(64/4*1)+3, 31, 31);
							batch.draw(map.getTileUnite(i/64, j/64).getKnuckles(), i+(64/6*3)-2, j+(64/4*0)+3, 31, 31);
							batch.draw(map.getTileUnite(i/64, j/64).getKnuckles(), i+(64/6*2)-2, j+(64/4*0)+3, 31, 31);
							batch.draw(map.getTileUnite(i/64, j/64).getKnuckles(), i+(64/6*1)-2, j+(64/4*0)+3, 31, 31);
							batch.draw(map.getTileUnite(i/64, j/64).getKnuckles(), i+(64/6*0)-2, j+(64/4*0)+3, 31, 31);
							break;
					}
				}
				if(selectedTile != null && map.canAttack(selectedTile.getX(), selectedTile.getY(), i/64, j/64)) {
					batch.draw(attackTexture, i, j);
				}
			}
		}
		//textFont.draw(batch, "GLProfiles = "+ Gdx.graphics., 64, 64);

		batch.draw(currentFrame, selector.x, selector.y);
		//textFont.draw(batch, "NO ORK WAZ HERE", 64, 32);
		batch.end();
		// End the batch drawing

		// If turnPlayer == bot : IT PLAYS LIKE A BOZZ !
		if(game.getTurnPlayer()==bot) {
			bot.takeTurn(game);
		}

		// Left Click = Set Selector position
		if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT) || Gdx.input.isButtonJustPressed(Input.Buttons.RIGHT)) {
			mouseInWorld3D.x = Gdx.input.getX();
			mouseInWorld3D.y = Gdx.input.getY();
			mouseInWorld3D.z = 0;
			viewport.unproject(mouseInWorld3D);
			mouseInWorld2D.x = mouseInWorld3D.x;
			mouseInWorld2D.y = mouseInWorld3D.y;
			Vector2 cursorPos = new Vector2();
			cursorPos.set(mouseInWorld2D.x, mouseInWorld2D.y);
			// Snap to the grid you damn selector !!!
			for (int i = 0; i < 20*64; i+=64) {for (int j = 0; j < 15*64; j+=64) {
				if(i<=cursorPos.x) {
					selector.x = i;
					//System.out.println("Cursor X = "+cursorPos.x);
				}
				if(j<=cursorPos.y) {
					selector.y = j;
				}
			}}
		}
		//if (Gdx.input.isButtonPressed(Input.Buttons.RIGHT)
		//&& map.getTileUnite((int)selector.x/64, (int)selector.y/64)!=null) {
		//	selectedTile = null;
		//}

		// Left Click + no selected Tile + Unit in Target Tile = Select Target Tile
		if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)
		&& selectedTile==null
		&& map.getTileUnite((int)selector.x/64, (int)selector.y/64)!=null) selectTile();

		// Left Click + yes selected Tile + no Unite in Target Tile = Move Unit
		if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT) && selectedTile!=null && map.getTileUnite((int)selector.x/64, (int)selector.y/64)==null) {
			System.out.println("Moving the Unit...");
			if(map.moveUnite(selectedTile.getX(), selectedTile.getY(), (int)selector.x/64, (int)selector.y/64)==true){
				//game.nextTurn();
				selectedTile = null;
			}
		}

		// Left Click + yes selected Tile + Unite in Target Tile = Select Target Unit
		if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT) 
		&& selectedTile!=null 
		&& map.getTileUnite((int)selector.x/64, (int)selector.y/64)!=null) {
			System.out.println("Selecting target Unit");
			selectTile();
		}

		// Right Click + yes selected Tile + Unite in Target Tile = Attack Target Unit
		if (Gdx.input.isButtonJustPressed(Input.Buttons.RIGHT) 
		&& selectedTile!=null 
		&& map.getTileUnite((int)selector.x/64, (int)selector.y/64)!=null
		&& map.getTileUnite((int)selector.x/64, (int)selector.y/64).getController() != selectedTile.getUnite().getController() ) {
			if(map.canAttack(selectedTile.x, selectedTile.y, (int)selector.x/64, (int)selector.y/64)==true) {
				System.out.println("ATTACK !!!");
				map.attack(selectedTile.x, selectedTile.y, (int)selector.x/64, (int)selector.y/64);
				selectedTile = null;
				// if(map.getTileUnite((int)selector.x/64, (int)selector.y/64).toString()=="Warboss"){
				// 	Sound sound = Gdx.audio.newSound(Gdx.files.internal("Warboss_Hurt.mp3"));
				// 	sound.play(1f);
				// 	sound.dispose();
				// }
				//game.nextTurn();
			}
		}

		// ==================================================
		// INPUT CONTROL ====================================
		// ==================================================

		// Arrows = Directions
		if(Gdx.input.isKeyJustPressed(Input.Keys.LEFT)) {
			selector.x -= 64;
			System.out.println("["+selector.x/64+";"+selector.y/64+"]");
		}
		if(Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) {
			selector.x += 64;
			System.out.println("["+selector.x/64+";"+selector.y/64+"]");
		}
		if(Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) {
			selector.y -= 64;
			System.out.println("["+selector.x/64+";"+selector.y/64+"]");
		}
		if(Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
			selector.y += 64;
			System.out.println("["+selector.x/64+";"+selector.y/64+"]");
		}

		// Ctrl left + K = Knuckles Mode
		if(Gdx.input.isKeyJustPressed(Input.Keys.K)) {
			if(Gdx.input.isKeyPressed(Input.Keys.CONTROL_LEFT)){
				if(knucklesMode==0) knucklesMode=1;
				else knucklesMode=0;
				System.out.println("DO YOU KNOW DA WAE ?");
			}
		}

		// Ctrl left + D = Debug Mode
		if(Gdx.input.isKeyJustPressed(Input.Keys.D)) {
			if(Gdx.input.isKeyPressed(Input.Keys.CONTROL_LEFT)){
				if(debugMode==0) debugMode=1;
				else debugMode=0;
				System.out.println("DEBUG MODE == "+debugMode);
			}
		}
		
		// A == next turn
		if(Gdx.input.isKeyJustPressed(Input.Keys.A)) System.out.println("a");

		// P == pick up weapon on tile
		if(Gdx.input.isKeyJustPressed(Input.Keys.P) && selectedTile!=null) {
			if(selectedTile.getArme()!=null && selectedTile.getUnite()!=null){
				Arme tempArme = selectedTile.getArme();
				selectedTile.setArme(tempArme);
				selectedTile.getUnite().equipArme(selectedTile);
			}
		}

		// Enter while no selectedTile = Select Tile
		if(Gdx.input.isKeyJustPressed(Input.Keys.ENTER) && selectedTile==null
		&& map.getTileUnite((int)selector.x/64, (int)selector.y/64)!=null) selectTile();
		// Enter while selectedTile = Fire
		//if(Gdx.input.isKeyJustPressed(Input.Keys.ENTER) && selectedTile==null) fire();

		// SEMICOLON == M
		// If M pressed : Move the selected Unit
		// If M pressed && no Unit in selected Tile : Nothing
		if(Gdx.input.isKeyJustPressed(Input.Keys.SEMICOLON) && selectedTile != null && selectedTile.getUnite()==null) System.out.println("Prompting the move key...\nNo Unit in selected Tile !");
		// if M pressed && no selected Tile : Nothing
		else if(Gdx.input.isKeyJustPressed(Input.Keys.SEMICOLON) && selectedTile == null) System.out.println("Prompting the move key...\nNo Tile Selected !");
		if(Gdx.input.isKeyJustPressed(Input.Keys.SEMICOLON) && selectedTile != null) {
			System.out.println("Prompting the move key...");
			map.moveUnite(selectedTile.getX(), selectedTile.getY(), (int)selector.x/64, (int)selector.y/64);
			selectedTile = null;
		}

		// // L == debug key : kill tile unit !
		if(Gdx.input.isKeyJustPressed(Input.Keys.L) && selectedTile != null && debugMode==1) {
			System.out.println("Killing unit !");
			map.setTileUnite(selectedTile.x, selectedTile.y, null);
		 	selectedTile = null;
		}
		// C == debug key : kill tile unit !
		if(Gdx.input.isKeyJustPressed(Input.Keys.C) && selectedTile != null && debugMode==1) {
		 	System.out.println("Killing a model !");
		 	map.getTileUnite(selectedTile.x, selectedTile.y).setNumber(map.getTileUnite(selectedTile.x, selectedTile.y).getNumber()-1);
		}
		// V == debug key : add tile unit !
		if(Gdx.input.isKeyJustPressed(Input.Keys.V) && selectedTile != null && debugMode==1) {
		 	System.out.println("Adding a model !");
		 	map.getTileUnite(selectedTile.x, selectedTile.y).setNumber(map.getTileUnite(selectedTile.x, selectedTile.y).getNumber()+1);
		}

		// J == debug key : add deathmark for the player !
		if(Gdx.input.isKeyJustPressed(Input.Keys.J) && selector.x > 0 && debugMode==1) {
			System.out.println("Adding a deathmark !");
			map.setTileUnite((int)selector.x/64, (int)selector.y/64, new Deathmark());
			map.getTileUnite((int)selector.x/64, (int)selector.y/64).setOwner(human1);
			map.getTileUnite((int)selector.x/64, (int)selector.y/64).setController(human1);
	   }

		if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE) && endGame==0) {
			for (tileData[] tileDatas : map.getMap()) {
				for (tileData tileData : tileDatas) {
					if(tileData.getUnite()==null) continue;
					tileData.getUnite().setMoved(false);
					tileData.getUnite().setFought(false);
					tileData.getUnite().setShoot(false);
				}
			}
			game.nextTurn();
		}

		// Prevents the selector to go too far
		if(selector.x < 0) selector.x = 0;
		if(selector.x > 19*64) selector.x = 19*64;
		if(selector.y < 0) selector.y = 0;
		if(selector.y > 14*64) selector.y = 14*64;

		int tempPlayerUnits = 0;
		int tempBotUnits = 0;
		for (tileData[] tileDatas : map.getMap()) {
			for (tileData tileData : tileDatas) {
				if(tileData.getUnite()==null) continue;
				if (tileData.getUnite().getController()==bot) {
					tempBotUnits++;
				}else{
					tempPlayerUnits++;
				}
			}
		}
		batch.begin();
		if(tempPlayerUnits==0 && endGame==0) {
			System.out.println(human1.getName()+" loses !!!");
			winner = 2;
			startTime = TimeUtils.millis();
			endGame=1;
		}
		if(tempBotUnits==0 && endGame==0) {
			System.out.println(bot.getName()+" loses !!!");
			winner = 1;
			startTime = TimeUtils.millis();
			endGame=1;
		}
		if(game.getTurnCounter()==0 && tempPlayerUnits>0 && tempBotUnits>0 && endGame==0) {
			System.out.println("Game over !!!");
			winner = 3;
			startTime = TimeUtils.millis();
			endGame=1;
		}
		if(winner!=0) {
			FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Arial.ttf"));
			FreeTypeFontParameter parameter = new FreeTypeFontParameter();
			parameter.size = 64*3;
			endFont = generator.generateFont(parameter);
			switch (winner) {
			case 3:
				endFont.setColor(0f, 0f, 1f, 1f);
				endFont.draw(batch, "Game over !!!", 64, Gdx.graphics.getHeight()/2);
				break;
			case 1:
				endFont.setColor(0f, 1f, 0f, 1f);
				endFont.draw(batch, bot.getName()+" loses !!!", 64, Gdx.graphics.getHeight()/3*2);
				endFont.draw(batch, human1.getName()+" wins !!!", 64, Gdx.graphics.getHeight()/3*1);
				break;
			case 2:
				endFont.setColor(1f, 0f, 0f, 1f);
				endFont.draw(batch, human1.getName()+" loses !!!", 64, Gdx.graphics.getHeight()/3*2);
				endFont.draw(batch, bot.getName()+" wins !!!", 64, Gdx.graphics.getHeight()/3*1);
				break;
			default:
				break;
			}
			generator.dispose();
		}
		batch.end();
		if(elapsedTime==0) elapsedTime = TimeUtils.timeSinceMillis(startTime);
		if(endGame==1) {
			elapsedTime = TimeUtils.timeSinceMillis(startTime);
			System.out.println("Time before closing : "+((5000-elapsedTime)/1000));
		}
		if(elapsedTime>5000 && endGame==1) Gdx.app.exit();

		if(tempCheck==turnPlayerUnits && endGame==0) {
			for (tileData[] tileDatas : map.getMap()) {
				for (tileData tileData : tileDatas) {
					if(tileData.getUnite()==null) continue;
					tileData.getUnite().setMoved(false);
					tileData.getUnite().setFought(false);
					tileData.getUnite().setShoot(false);
				}
			}
			game.nextTurn();
		}

		checkedUnits = 0;
		turnPlayerUnits = 0;
		otherPlaterUnits = 0;
		tempCheck = 0;
	}
	
	@Override
	public void dispose () {
		moveTexture.dispose();
		tileset.dispose();
		startMusic.dispose();
		batch.dispose();
	}

	// Custom Methods after this point
	public void selectTile() {
		selectedTile = map.getTile((int)selector.x/64, (int)selector.y/64);
		System.out.println("Selected Tile : X="+selectedTile.getX()+";Y="+selectedTile.getY()+".");

		if(selectedTile.getUnite()==null)	System.out.println("Unit = "+selectedTile.getUnite());
		else	System.out.println("Unit = "+selectedTile.getUnite().toString());

		if(selectedTile.getArme()==null)	System.out.println("Weapon = "+selectedTile.getArme());
		else	System.out.println("Weapon = "+selectedTile.getArme().toString());
	}


	public void drawText(String text, int x, int y) {
		textFont.setColor(0f, 0f, 0f, 0.50f);
		textFont.draw(batch, text, x+20+3, y-22-3);
		textFont.setColor(1, 1, 1, 1);
		textFont.draw(batch, text, x+20, y-22);
	}
}