package com.rubynaxela.kyanite.game;

import com.rubynaxela.kyanite.game.assets.AnimatedTexture;
import com.rubynaxela.kyanite.game.entities.AnimatedEntity;
import com.rubynaxela.kyanite.game.entities.CompoundEntity;
import com.rubynaxela.kyanite.game.entities.MovingEntity;
import com.rubynaxela.kyanite.physics.GravityAffected;
import com.rubynaxela.kyanite.util.Colors;
import com.rubynaxela.kyanite.util.Vec2;
import com.rubynaxela.kyanite.window.Window;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jsfml.graphics.Drawable;
import org.jsfml.graphics.RectangleShape;
import org.jsfml.system.Time;

import java.util.ConcurrentModificationException;

/**
 * Provides an overlay designed for being displayed on a {@link Window} over a {@link Scene}.
 */
public abstract class HUD extends RenderLayer {

    private final RectangleShape solidBackground = new RectangleShape();

    /**
     * Creates an empty HUD.
     */
    public HUD() {
        backgroundColor = Colors.TRANSPARENT;
    }

    /**
     * Creates a "null" HUD with empty {@link HUD#init} method.
     */
    public static HUD empty() {
        return new HUD() {
            @Override
            protected void init() {
            }
        };
    }

    /**
     * Performs the full HUD initialization, i.a. calls the {@link #init} method. This method should not be invoked manually.
     */
    @Contract(value = "-> fail")
    public final void fullInit() {
        if (!ready) {
            ready = true;
            init();
        } else throw new IllegalStateException("This HUD has been already initialized");
    }

    /**
     * Draws every object on the window. This method is automatically executed every frame by the window it belongs
     * to and should not be invoked manually. If this HUD contains {@link AnimatedEntity}s or {@link AnimatedEntity}s,
     * their animation methods will be called with the currently set {@link Scene}'s {@code getDeltaTime()} method.
     *
     * @param window the window that the scene has to be displayed on
     */
    public void refresh(@NotNull Window window) {
        solidBackground.setSize(Vec2.f(window.getSize()));
        solidBackground.setFillColor(backgroundColor);
        if (background != null) window.draw(background);
        else window.draw(solidBackground);
        runScheduledActions();
        final Time deltaTime = window.getScene().getDeltaTime(), elapsedTime = getContext().getClock().getTime();
        final float dt = deltaTime.asSeconds();
        try {
            forEach(object -> {
                if (object instanceof final GravityAffected entity)
                    entity.setVelocity(Vec2.add(entity.getVelocity(), Vec2.f(0, entity.getGravity() * dt)));
                if (object instanceof final AnimatedEntity entity) entity.animate(deltaTime, elapsedTime);
                if (AnimatedTexture.animatedObjects.containsKey(object))
                    AnimatedTexture.animatedObjects.get(object).update(object, elapsedTime);
                if (object instanceof final CompoundEntity entity)
                    for (final Drawable component : entity.getComponents())
                        if (AnimatedTexture.animatedObjects.containsKey(component))
                            AnimatedTexture.animatedObjects.get(component).update(component, elapsedTime);
                if (object instanceof final MovingEntity entity) entity.move(Vec2.multiply(entity.getVelocity(), dt));
                window.draw(object);
            });
        } catch (ConcurrentModificationException e) {
            throw new ConcurrentModificationException("HUD contents cannot be modified during a draw iteration." +
                                                      " In order to add or remove an object, use the" +
                                                      " scheduleToAdd(), scheduleToRemove() or schedule() method");
        }
    }
}
