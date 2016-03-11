package be.kejcs.sadg;

import android.app.Application;

import java.util.Arrays;
import java.util.List;

import be.kejcs.sadg.Classes.DiaryBuilder;
import be.kejcs.sadg.Classes.EventBuilder;
import be.kejcs.sadg.Classes.ItemBuilder;
import be.kejcs.sadg.Classes.ItemCollection;
import be.kejcs.sadg.Classes.QuestionnaireBuilder;
import be.kejcs.sadg.Classes.TimeLineBuilder;

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
