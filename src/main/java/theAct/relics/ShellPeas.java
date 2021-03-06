package theAct.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import theAct.TheActMod;
import theAct.actions.InstantAddTemporaryHPAction;

public class ShellPeas extends CustomRelic {

    public static final String ID = TheActMod.makeID("ShellPeas");
    public static final Texture IMAGE_PATH = ImageMaster.loadImage(TheActMod.assetPath("images/relics/Peas.png"));
    public static final Texture IMAGE_OUTLINE_PATH = ImageMaster.loadImage(TheActMod.assetPath("images/relics/PeasOutline.png"));
    private static final int TEMPORARY_HP_PER_HIT = 2;

    public ShellPeas() {
        super(ID, IMAGE_PATH, IMAGE_OUTLINE_PATH, RelicTier.SPECIAL, AbstractRelic.LandingSound.FLAT);
    }

    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + TEMPORARY_HP_PER_HIT + DESCRIPTIONS[1];
    }

    public int onAttacked(DamageInfo info, int damageAmount) {
        if (info.type == DamageInfo.DamageType.NORMAL) {
            flash();
            AbstractDungeon.actionManager.addToTop(new InstantAddTemporaryHPAction(AbstractDungeon.player, AbstractDungeon.player, TEMPORARY_HP_PER_HIT));
        }
        return damageAmount;
    }

    public AbstractRelic makeCopy() {
        return new ShellPeas();
    }
}
