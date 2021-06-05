package mynyresolutionslist.nexmii.com.mynyresolutionslist.models.appdata;

import android.content.Context;
import android.util.Log;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

import mynyresolutionslist.nexmii.com.mynyresolutionslist.R;
import mynyresolutionslist.nexmii.com.mynyresolutionslist.models.constants.LocalDbConstants;
import mynyresolutionslist.nexmii.com.mynyresolutionslist.models.localdb.NexmiiDatabaseHandler;
import mynyresolutionslist.nexmii.com.mynyresolutionslist.models.objects.Quote;
import mynyresolutionslist.nexmii.com.mynyresolutionslist.utils.settings.Preferences;

/**
 * Created by David on 9/22/2019 for Nexmii.
 */
public class QuotesSelectorOps {

    private Context context;
    private Random r;
    private LocalMaterial lm = new LocalMaterial();
    private NexmiiDatabaseHandler ndb;

    public QuotesSelectorOps(Context ctx){
        this.context = ctx;
        ndb = new NexmiiDatabaseHandler(ctx);
        r = new Random();
    }

    private String quoteSelector(String[] quotesArr){
        int randomQuote = r.nextInt(quotesArr.length);
        Log.i("returnedQuote", quotesArr[randomQuote]);
        return quotesArr[randomQuote];
    }

    private int arrayRandomIndicator(){
        return r.nextInt(4);
    }

    public void oneTimeQuoteArraySelector(){
        String category;
        Log.i("returned_arr_in_length", String.valueOf(arrayRandomIndicator()));
        for (int i = 0; i < 4; i++){
            int indicator = arrayRandomIndicator();
            if (String.valueOf(indicator).equals("0")){
                //life quotes
                String lifeQuote = quoteSelector(lm.getLifeMotivationalQuotes());
                category = context.getResources().getString(R.string.quote_category_life);
                Log.i("returned_category", "life - 0");
                saveQuoteToDB(lifeQuote, category);

            }else if(String.valueOf(indicator).equals("1")){
                //success quotes
                String successQuote = quoteSelector(lm.getSuccessMotivationalQuotes());
                category = context.getResources().getString(R.string.quote_category_success);
                Log.i("returned_category", "success - 1");
                saveQuoteToDB(successQuote, category);

            }else if(String.valueOf(indicator).equals("2")){
                //happiness quotes
                String happinessQuote = quoteSelector(lm.getHappinessMotivationalQuotes());
                category = context.getResources().getString(R.string.quote_category_happiness);
                Log.i("returned_category", "happiness - 2");
                saveQuoteToDB(happinessQuote, category);
            }
        }
        Preferences.setDefaults(context.getResources().getString
                (R.string.one_time_four_quotes_key),context.getResources()
                .getString(R.string.one_time_four_quotes_value), context);
    }

    void quoteArraySelector(TextView quoteView, TextView authorView){
        String category;
        int indicator = arrayRandomIndicator();
        if (String.valueOf(indicator).equals("0")){
            //life quotes
            String lifeQuote = quoteSelector(lm.getLifeMotivationalQuotes());
            category = context.getResources().getString(R.string.quote_category_life);
            Log.i("returned_category", "life1 - 0");
            existingQuoteChecker(lifeQuote, category, quoteView, authorView);
        }else if(String.valueOf(indicator).equals("1")){
            //success quotes
            String successQuote = quoteSelector(lm.getSuccessMotivationalQuotes());
            category = context.getResources().getString(R.string.quote_category_success);
            Log.i("returned_category", "success1 - 1");
            existingQuoteChecker(successQuote, category, quoteView, authorView);
        }else if(String.valueOf(indicator).equals("2")){
            //happiness quotes
            String happinessQuote = quoteSelector(lm.getHappinessMotivationalQuotes());
            category = context.getResources().getString(R.string.quote_category_happiness);
            Log.i("returned_category", "happiness1 - 2");
            existingQuoteChecker(happinessQuote, category, quoteView, authorView);
        }else{
            //life quotes again
            String lifeQuote = quoteSelector(lm.getLifeMotivationalQuotes());
            category = context.getResources().getString(R.string.quote_category_life);
            Log.i("returned_category", "life1 - 0");
            existingQuoteChecker(lifeQuote, category, quoteView, authorView);
        }
    }

    private void existingQuoteChecker(String leQuote, String leCategory,
                                      TextView quoteView, TextView authorView){
        ArrayList<Quote> quotesFromDB = ndb.getQuotes();
        ArrayList<String> quotesInStrings = new ArrayList<>();
        for (int i = 0;i < quotesFromDB.size(); i++){
            String existingQuote = quotesFromDB.get(i).getQuote();
            quotesInStrings.add(existingQuote);
        }
        if (quotesInStrings.contains(leQuote)){
            Log.i("newQuoteStatus", "Quote already exists in DB");
            quoteArraySelector(quoteView, authorView);
        }else{
            Log.i("newQuoteStatus", "New quote added");
            String shownQuote = "\"" + redoneMainQuote(leQuote) + "\"";
            quoteView.setText(shownQuote);
            authorView.setText(separatedAuthor(leQuote));
            saveQuoteToDB(leQuote, leCategory);
        }
        ndb.close();
    }

    private String redoneMainQuote(String quote){
        return quote.replace(quote.substring(quote.lastIndexOf("-")),"");
    }

    private String separatedAuthor(String quote){
        return quote.substring(quote.lastIndexOf("-"));
    }

    private void saveQuoteToDB(String leQuote, String leCategory){
        Quote quote = new Quote();
        quote.setQuote(leQuote.trim());
        quote.setCategory(leCategory.trim());
        quote.setLiked(LocalDbConstants.QUOTE_LIKED);
        ndb.addQuote(quote);
        ndb.close();
    }
}

