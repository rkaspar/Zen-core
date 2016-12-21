package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.gushikustudios.rube.loader.RubeSceneLoader;

public class MainGame extends ApplicationAdapter {
	public SpriteBatch batch;
	Texture img;
	public World world;
	private Array<Entity> Entities;
	public OrthographicCamera camera;
	Box2DDebugRenderer debugRenderer;
	public ContactHandler contactHandler = new ContactHandler();
	ParticleEffect snowEffect = new ParticleEffect();

	@Override
	public void create() {
		batch = new SpriteBatch();
		img = new Texture("circle.png");
		Entities = new Array<Entity>();
		world = new World(new Vector2(0, -1500), true);
		world.setContactListener(contactHandler);
		debugRenderer = new Box2DDebugRenderer();
		for (int i = 0; i < 5; i++) {
			Entity e = new Entity(MathUtils.random(10, 300), MathUtils.random(10, 300), img, world, true);
			Entities.add(e);
		}

		RubeSceneLoader loader = new RubeSceneLoader();
		
		//making a change.

		snowEffect.load(Gdx.files.local("effects/snow_effect.p"), Gdx.files.local("effects"));
		snowEffect.setPosition(Gdx.graphics.getHeight(), Gdx.graphics.getWidth() / 2);
		snowEffect.start();

		// add floor
		Entities.add(new Entity(0, -280, new Texture("floor.png"), world, false));

		camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		camera.translate(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);

	}

	@Override
	public void render() {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		input();

		camera.update();
		batch.setProjectionMatrix(camera.combined);
		debugRenderer.render(world, camera.combined);
		doPhysicsStep();

		// start batch
		batch.begin();

		for (Entity e : Entities) {
			e.draw(batch);
		}

		snowEffect.draw(batch, Gdx.graphics.getDeltaTime());

		batch.end();

	}

	private void input() {

		// pan camera

		if (Gdx.input.isKeyPressed(Keys.D)) {
			camera.translate(10, 0, 0);
		}
		if (Gdx.input.isKeyPressed(Keys.A)) {
			camera.translate(-10, 0, 0);
		}
		if (Gdx.input.isKeyPressed(Keys.S)) {
			camera.translate(0, -10, 0);
		}
		if (Gdx.input.isKeyPressed(Keys.W)) {
			camera.translate(0, 10, 0);
		}

	}

	@Override
	public void dispose() {
		batch.dispose();

	}

	private void doPhysicsStep() {

		// figure out complexities later TODO
		world.step(Gdx.graphics.getDeltaTime(), 6, 2);

	}
}
