package entities.npcs;

import entities.NPCEntity;
import game_states.Playing;
import utilz.LoadSaveImage;

import java.awt.*;
import java.awt.image.BufferedImage;

import static main.GameWindow.ScreenSettings.Scale;
import static utilz.Constants.Directions.*;
import static utilz.Constants.NpcCsv.*;
import static utilz.Constants.PlayerConstants.STANDING;
import static utilz.Constants.PlayerConstants.WALKING;

public class npc_chemist extends NPCEntity {

    public npc_chemist(int npcID, String[][] npcInfo, Playing playing) {
        super(npcID, npcInfo, playing);
        initialize();
    }

    private void initialize() {
        loadAnimations();
        setHitbox(aniHeight/Float.parseFloat(npcInfo[npcID][HITBOX_UP]),
                aniWidth/Float.parseFloat(npcInfo[npcID][HITBOX_LEFT]),
                aniHeight/Float.parseFloat(npcInfo[npcID][HITBOX_DOWN]),
                aniWidth/Float.parseFloat(npcInfo[npcID][HITBOX_RIGHT]));
        setEntityInitialCenter();
        aniIndexI = Integer.parseInt(npcInfo[npcID][DIRECTION]);
        aniWidth = (int) (20*Scale);
        aniHeight= (int) (30*Scale);
    }

    public void loadAnimations() {
        BufferedImage img = LoadSaveImage.GetSpriteAtlas(sprite);
        animations = new BufferedImage[4][4];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                animations[i][j] = img.getSubimage(j*32,i*48,32,48);
            }
        }
    }

    @Override
    public void update() {
        px = playing.getPlayer().getPositionX();
        py = playing.getPlayer().getPositionY();

        //NPC movement
        if (npcInfo[npcID][CAN_MOVE].equals("1"))
            randomMovement();

        //change image after few frames
        updateAnimationTick();

        //updates NPC position when game screen moves
        updatePosition();

        //updates NPC position after it moves and checks for collisions
        checkCollision();
    }

    private void updateAnimationTick() {
        aniTick++;
        if (aniTick >= aniSpeed) {
            aniTick = 0;
            switch (aniAction) {
                case STANDING:
                    aniFrame = 0;
                    aniDirection = direction;
                    break;
                case WALKING:
                    aniFrame++;
                    aniDirection = direction;
                    if (aniFrame >= 4)
                        aniFrame = 1;
                    break;
            }
        }
    }

    @Override
    public void draw(Graphics2D g2) {
        g2.drawImage(animations[aniDirection][aniFrame], (int)npcCenterX,(int)npcCenterY,
                aniWidth, aniHeight, null);
    }
}
