package gb.ru.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import gb.ru.base.BaseScreen;
import gb.ru.math.Rect;
import gb.ru.pool.BulletPool;
import gb.ru.pool.EnemyPool;
import gb.ru.sprite.Background;
import gb.ru.sprite.MainShip;
import gb.ru.sprite.Star;
import gb.ru.util.EnemyEmmiter;

public class GameScreen extends BaseScreen {

    private static final int STAR_COUNT = 64;

    private Texture bg;
    private Background background;
    private TextureAtlas atlas;

    private Star[] stars;
    private BulletPool bulletPool;
    private EnemyPool enemyPool;

    private MainShip mainShip;

    private Music music;
    private Sound laserSound;
    private Sound bulletSound;

    private EnemyEmmiter enemyEmmiter;

    @Override
    public void show() {
        super.show();
        music = Gdx.audio.newMusic(Gdx.files.internal("sounds/music.mp3"));
        music.setLooping(true);
        music.play();
        laserSound = Gdx.audio.newSound(Gdx.files.internal("sounds/laser.wav"));
        bulletSound = Gdx.audio.newSound(Gdx.files.internal("sounds/bullet.wav"));
        atlas = new TextureAtlas("textures/mainAtlas.tpack");
        bg = new Texture("textures/bg.png");
        background = new Background(bg);
        stars = new Star[STAR_COUNT];
        for (int i = 0; i < stars.length; i++){
            stars[i] = new Star(atlas);
        }

        bulletPool = new BulletPool();
        enemyPool = new EnemyPool(bulletPool, worldBounds, bulletSound);

        mainShip = new MainShip(atlas, bulletPool, laserSound);

        enemyEmmiter = new EnemyEmmiter(enemyPool, worldBounds, atlas);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        update(delta);
        freeAllDestroy();
        draw();
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        background.resize(worldBounds);
        for(Star star: stars){
            star.resize(worldBounds);
        }

        mainShip.resize(worldBounds);
    }

    @Override
    public void dispose() {
        super.dispose();
        bg.dispose();
        atlas.dispose();
        music.dispose();
        enemyPool.dispose();
        laserSound.dispose();
        bulletSound.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {
        mainShip.keyDown(keycode);
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        mainShip.keyUp(keycode);
        return false;
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        mainShip.touchDown(touch, pointer, button);
        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer, int button) {
        mainShip.touchUp(touch, pointer, button);
        return false;
    }

    private void update(float delta){
        for(Star star: stars){
            star.update(delta);
        }
        bulletPool.updateActiveObjects(delta);
        enemyPool.updateActiveObjects(delta);
        mainShip.update(delta);
        enemyEmmiter.generate(delta);
    }

    private void freeAllDestroy(){
        bulletPool.freeAllDestroyed();
        enemyPool.freeAllDestroyed();
    }

    private void draw(){
        batch.begin();
        background.draw(batch);
        for(Star star: stars){
            star.draw(batch);
        }
        bulletPool.drawActiveObjects(batch);
        enemyPool.drawActiveObjects(batch);
        mainShip.draw(batch);

        batch.end();
    }
}
