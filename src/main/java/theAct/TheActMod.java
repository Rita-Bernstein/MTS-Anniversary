package theAct;

import java.nio.charset.StandardCharsets;

import com.badlogic.gdx.Gdx;
import com.evacipated.cardcrawl.mod.stslib.Keyword;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.google.gson.Gson;
import com.megacrit.cardcrawl.dungeons.TheBeyond;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.localization.UIStrings;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import basemod.BaseMod;
import basemod.ModPanel;
import basemod.abstracts.CustomSavable;
import basemod.interfaces.EditKeywordsSubscriber;
import basemod.interfaces.EditStringsSubscriber;
import basemod.interfaces.PostInitializeSubscriber;
import theAct.dungeons.Jungle;
import theAct.events.River;
import theAct.events.SneckoCultEvent;
import theAct.patches.GetDungeonPatches;

@SpireInitializer
public class TheActMod implements
        PostInitializeSubscriber,
        EditKeywordsSubscriber,
        EditStringsSubscriber,
        CustomSavable<Boolean>
{
	
	
    public static final Logger logger = LogManager.getLogger(TheActMod.class.getSimpleName());

    public static boolean wentToTheJungle = false;
    
    

    public static void initialize()
    {
        BaseMod.subscribe(new TheActMod());
    }

    public static String makeID(String id)
    {
        return "theJungle:" + id;
    }

    public static String assetPath(String path)
    {
        return "theActAssets/" + path;
    }

    @Override
    public void receivePostInitialize()
    {
        // Settings panel
        ModPanel settingsPanel = new ModPanel();


        BaseMod.registerModBadge(ImageMaster.loadImage(assetPath("modBadge.png")), "MTS Anniversary Act", "Everyone", "TODO", settingsPanel);

        // Add events here
        BaseMod.addEvent(River.ID, River.class, Jungle.ID);
        BaseMod.addEvent(SneckoCultEvent.ID, SneckoCultEvent.class, Jungle.ID);

        // Add monsters here

        // Add dungeon
        GetDungeonPatches.addDungeon(Jungle.ID, Jungle.builder());
        GetDungeonPatches.addNextDungeon(Jungle.ID, TheBeyond.ID);

        //savable boolean
        BaseMod.addSaveField("wentToTheJungle", this);
    }

    @Override
    public void receiveEditKeywords()
    {
        String language = "eng";
        // TODO: Language support

        Gson gson = new Gson();
        String json = Gdx.files.internal(assetPath("localization/" + language + "/keywords.json")).readString(String.valueOf(StandardCharsets.UTF_8));
        Keyword[] keywords = gson.fromJson(json, Keyword[].class);

        if (keywords != null) {
            for (Keyword keyword : keywords) {
                BaseMod.addKeyword(keyword.PROPER_NAME, keyword.NAMES, keyword.DESCRIPTION);
            }
        }
    }

    @Override
    public void receiveEditStrings()
    {
        String language = "eng";
        // TODO: Language support
        String path = "localization/" + language + "/";

        BaseMod.loadCustomStringsFile(EventStrings.class, assetPath(path + "events.json"));
        BaseMod.loadCustomStringsFile(UIStrings.class, assetPath(path + "ui.json"));
        BaseMod.loadCustomStringsFile(CardStrings.class, assetPath(path + "cards.json"));
    }

    @Override
    public Boolean onSave() {
        logger.info("Saving wentToTheJungle boolean: " + wentToTheJungle);
        return wentToTheJungle;
    }

    @Override
    public void onLoad(Boolean loadedBoolean) {
        wentToTheJungle = loadedBoolean;
        logger.info("Loading wentToTheJungle boolean: " + wentToTheJungle);
    }
}
