package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class Entity extends Sprite {
	Body body;
	private boolean isPhysics;

	public Entity(int x, int y, Texture t, World world, boolean isPhysics) {

		this.setX(x);
		this.setY(y);
		this.setTexture(t);
		this.setPhysics(isPhysics);
		createBody(world);
	}

	private void createBody(World world) {
		// Create a physics world, the heart of the simulation. The Vector
		// passed in is gravity

		// Now create a BodyDefinition. This defines the physics objects type
		// and position in the simulation
		BodyDef bodyDef = new BodyDef();
		if (isPhysics) {
			bodyDef.type = BodyDef.BodyType.DynamicBody;
		} else {
			bodyDef.type = BodyDef.BodyType.StaticBody;
		}
		// We are going to use 1 to 1 dimensions. Meaning 1 in physics engine
		// is 1 pixel
		// Set our body to the same position as our sprite
		bodyDef.position.set(this.getX()-getWidth()/2, this.getY()-getHeight()/2);

		// Create a body in the world using our definition
		body = world.createBody(bodyDef);

		// Now define the dimensions of the physics shape
		PolygonShape shape = new PolygonShape();
		// We are a box, so this makes sense, no?
		// Basically set the physics polygon to a box with the same dimensions
		// as our sprite
		shape.setAsBox(getTexture().getWidth()/2, getTexture().getHeight()/2);

		// FixtureDef is a confusing expression for physical properties
		// Basically this is where you, in addition to defining the shape of the
		// body
		// you also define it's properties like density, restitution and others
		// we will see shortly
		// If you are wondering, density and area are used to calculate over all
		// mass
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = shape;
		fixtureDef.density = 100f;
		Fixture fixture = body.createFixture(fixtureDef);
	}

	@Override
	public void draw(Batch batch) {

		update();
		batch.draw(this.getTexture(), this.getX(), this.getY());
		super.draw(batch);
	}

	public void update() {

		if (isPhysics) {

			setPosition(body.getPosition().x, body.getPosition().y);
		}

	}

	public boolean isPhysics() {
		return isPhysics;
	}

	public void setPhysics(boolean isPhysics) {
		this.isPhysics = isPhysics;
	}

}
