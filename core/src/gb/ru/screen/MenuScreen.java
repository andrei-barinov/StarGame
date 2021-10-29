package gb.ru.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import gb.ru.base.BaseScreen;
import gb.ru.math.Rect;
import gb.ru.sprite.Background;
import gb.ru.sprite.ExitButton;
import gb.ru.sprite.MainShip;
import gb.ru.sprite.PlayButton;
import gb.ru.sprite.Star;

public class MenuScreen extends BaseScreen {

    private final Game game;

    private static final int STAR_COUNT = 256;
    private static final float V_LEN = 1.5f;

    private TextureAtlas atlas;
    private Texture bg;

    private Background background;
    private Star stars[];


    private ExitButton exitButton;
    private PlayButton playButton;

    public MenuScreen(Game game) {
        this.game = game;
    }

    @Override
    public void show() {
        super.show();
        atlas = new TextureAtlas("textures/menuAtlas.tpack");
        bg = new Texture("textures/bg.png");
        background = new Background(bg);
        stars = new Star[STAR_COUNT];
        for (int i = 0; i < stars.length; i++){
            stars[i] = new Star(atlas);
        }

        exitButton = new ExitButton(atlas);
        playButton = new PlayButton(atlas, game);

    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        background.resize(worldBounds);
        for(Star star: stars){
            star.resize(worldBounds);
        }

        exitButton.resize(worldBounds);
        playButton.resize(worldBounds);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        update(delta);
        draw();

    }

    @Override
    public void dispose() {
        super.dispose();
        bg.dispose();
        atlas.dispose();
    }


    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        exitButton.touchDown(touch, pointer, button);
        playButton.touchDown(touch, pointer, button);
        return super.touchDown(touch, pointer, button);
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer, int button) {
        exitButton.touchUp(touch, pointer, button);
        playButton.touchUp(touch, pointer, button);
        return false;
    }

    private void update(float delta){
        for(Star star: stars){
            star.update(delta);
        }
    }

    private void draw(){
        batch.begin();
        background.draw(batch);
        for(Star star: stars){
            star.draw(batch);
        }

        exitButton.draw(batch);
        playButton.draw(batch);

        batch.end();
    }


}
