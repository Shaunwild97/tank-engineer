package com.vobis.tankengineer.render;

import org.newdawn.slick.Image;
import org.newdawn.slick.SpriteSheet;

/**
 *
 * @author Shaun
 */
public class Animation {

    private SpriteSheet spriteSheet;
    private int fps;
    private float frame;
    private float spf;
    private boolean loop = true;
    private boolean playing = true;
    private boolean finished;

    public Animation(SpriteSheet spriteSheet, int fps) {
        this.spriteSheet = spriteSheet;
        setFps(fps);

        System.out.println(spriteSheet.getResourceReference() + ": " + spf);
    }

    public Animation() {
    }

    public Image getCurrentFrame() {
        return spriteSheet.getSprite((int) frame, 0);
    }

    public void update() {
        if (playing) {
            frame += spf;

            if (hasEnded()) {
                frame = 0;

                if (!loop) {
                    finished = true;
                    playing = false;
                }

            }
        }
    }

    public void setLoop(boolean loop) {
        this.loop = loop;
    }

    public boolean hasEnded() {
        return frame >= spriteSheet.getHorizontalCount() - 1;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setPlaying(boolean playing) {
        this.playing = playing;
    }

    public void play() {
        finished = false;
        loop = false;
        playing = true;
    }

    public void loop() {
        finished = false;
        loop = true;
        playing = true;
    }

    public Animation loop(boolean loop) {
        this.loop = loop;
        return this;
    }

    public Animation playing(boolean playing) {
        this.playing = playing;
        return this;
    }

    public void setSpriteSheet(SpriteSheet spriteSheet) {
        this.spriteSheet = spriteSheet;
    }

    public final void setFps(int fps) {
        this.fps = fps;
        this.spf = fps / 100f;
    }
}
