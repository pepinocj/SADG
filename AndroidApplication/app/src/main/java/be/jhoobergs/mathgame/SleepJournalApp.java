package be.jhoobergs.mathgame;

import android.app.Application;
import android.util.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import be.jhoobergs.mathgame.Classes.DiaryBuilder;
import be.jhoobergs.mathgame.Classes.EventBuilder;
import be.jhoobergs.mathgame.Classes.ItemBuilder;
import be.jhoobergs.mathgame.Classes.ItemCollection;
import be.jhoobergs.mathgame.Classes.QuestionnaireBuilder;
import be.jhoobergs.mathgame.Classes.TimeLineBuilder;

/**
 * Created by Josi on 13/02/2016.
 */
public class SleepJournalApp extends Application {
    public static List<String> diaryMoments = Arrays.asList("Voormiddag","Middag","Namiddag","Avond","Laatste uur","Net voor het slapen");
    public ItemCollection ic = new ItemCollection();

    public EventBuilder eventBuilder = new EventBuilder(ic);
    public TimeLineBuilder timelineBuilder = new TimeLineBuilder(ic);
    public QuestionnaireBuilder questionnaireBuilder = new QuestionnaireBuilder(ic);
    public DiaryBuilder diaryBuilder = new DiaryBuilder(ic);
    public List<ItemBuilder> builders = Arrays.asList(eventBuilder,timelineBuilder,questionnaireBuilder,diaryBuilder);


    public void resetBuilders(){

        for(ItemBuilder ib:builders){
            ib.reset();
        }
    }


}
