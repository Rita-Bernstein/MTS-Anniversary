package theAct.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.rooms.MonsterRoomElite;
import theAct.dungeons.Jungle;

import static com.megacrit.cardcrawl.rooms.AbstractRoom.RoomPhase.COMBAT;

@SpirePatch(
        clz = AbstractRoom.class,
        method = "update"
)
public class AbstractRoomUpdateIncrementElitesPatch {

    public static void Postfix(AbstractRoom __instance) {
        try {
            float endBattleTimer = (float) AbstractRoom.class.getDeclaredField("endBattleTimer").get(__instance);
            if (__instance.phase == COMBAT
                    && __instance.isBattleOver
                    && AbstractDungeon.actionManager.actions.isEmpty()
                    && endBattleTimer == 0.0f
                    && __instance instanceof MonsterRoomElite
                    && !AbstractDungeon.loading_post_combat
                    && CardCrawlGame.dungeon instanceof Jungle
            ) {
                ++CardCrawlGame.elites2Slain;
                //AbstractRoom.logger.info("ELITES SLAIN " + CardCrawlGame.elites2Slain); logger has private access
            }
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
