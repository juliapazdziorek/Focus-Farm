package Entities.Nature;

import Entities.Entity;
import Game.FocusFarm;
import Resources.Animation;

import java.awt.*;
import java.awt.image.BufferedImage;

public class TreePart extends Entity {

    public Tree parentTree;

    // visuals
    public BufferedImage staticImage;
    public Animation clickAnimation;
    public boolean isAnimating;
    private int animationCycleCount;

    public TreePart(Point position, int tileId) {
        super(position);
        staticImage = FocusFarm.resourceHandler.entitiesResourcesMap.get("tree").get(String.valueOf(tileId));
        clickAnimation = FocusFarm.resourceHandler.createTreeClickAnimationMap().get(tileId);

        clickable = true;
    }


    // mouse handling
    @Override
    public void onClick() {
        parentTree.playClickAnimation();
    }


    // animation
    public void startAnimation() {
        if (clickAnimation != null) {
            isAnimating = true;
            animationCycleCount = 0;
            clickAnimation.resetFrames();
        }
    }


    // getters
    private BufferedImage getCurrentImage() {
        if (isAnimating) {
            return clickAnimation.getCurrentFrameImage();
        }
        return staticImage;
    }


    // setters
    public void setParentTree(Tree tree) {
        this.parentTree = tree;
    }


    // updating & rendering
    @Override
    public void update() {
        if (isAnimating && clickAnimation != null) {
            int previousFrame = clickAnimation.getCurrentFrame();
            clickAnimation.update();

            if (previousFrame == clickAnimation.getNumberOfFrames() - 1 && clickAnimation.getCurrentFrame() == 0) {
                animationCycleCount++;

                if (animationCycleCount >= 1) {
                    isAnimating = false;
                    parentTree.isAnimating = false;
                }
            }
        }
    }

    @Override
    public void render(Graphics2D graphics2D) {
        BufferedImage imageToRender = getCurrentImage();
        graphics2D.drawImage(imageToRender,
                FocusFarm.camera.position.x + position.x * FocusFarm.scale,
                FocusFarm.camera.position.y + position.y * FocusFarm.scale,
                FocusFarm.scaledTileSize,
                FocusFarm.scaledTileSize,
                null);
    }
}
