package entities.npcs;

import game_states.Playing;
import utilz.LoadSaveImage;

import java.awt.*;
import java.awt.image.BufferedImage;

import static utilz.Constants.Directions.*;
import static utilz.Constants.NpcCsv.*;
import static utilz.Constants.PlayerConstants.STANDING;
import static utilz.Constants.PlayerConstants.WALKING;

public class npc_Klay extends NPCEntity{

    public npc_Klay(int npcID, String[][] npcInfo, Playing playing) {
        super(npcID, npcInfo, playing);
        initialize();
    }

    private void initialize() {
        loadAnimations();
        setHitbox(aniWidth/Float.parseFloat(npcInfo[npcID][HITBOX_LEFT]),
                aniHeight/Float.parseFloat(npcInfo[npcID][HITBOX_DOWN]),
                aniHeight/Float.parseFloat(npcInfo[npcID][HITBOX_UP]),
                aniWidth/Float.parseFloat(npcInfo[npcID][HITBOX_RIGHT]));
        setEntityInitialCenter();
        aniIndexI = Integer.parseInt(npcInfo[npcID][DIRECTION]);
    }

    public void loadAnimations() {
        BufferedImage img = LoadSaveImage.GetSpriteAtlas(sprite);
        animations = new BufferedImage[3][4];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 4; j++) {
                animations[i][j] = img.getSubimage(j*16,i*32,16,32);
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
            if (aniAction == STANDING)
                aniIndexI = 0;
            else
                aniIndexI++;
            if ((aniDirection == LEFT || aniDirection == RIGHT) && aniIndexI >= WALKING)
                aniIndexI = 0;
            else if (aniIndexI > WALKING)
                aniIndexI = 1;
        }
    }

    @Override
    public void draw(Graphics2D g2) {
        g2.drawImage(animations[aniIndexI][aniDirection], (int)npcCenterX,(int)npcCenterY,
                aniWidth, aniHeight, null);
    }
}
