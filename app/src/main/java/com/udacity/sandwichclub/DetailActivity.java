package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] list = getResources().getStringArray(R.array.sandwich_details);
        String sandwichJson = list[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(sandwichJson);

        if (sandwich == null) {
            closeOnError();
            return;
        }

        populateUI( sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {

        TextView name =findViewById(R.id.name_tv);
        TextView alsoKnownAs =findViewById(R.id.also_known_tv);
        TextView origin =findViewById(R.id.origin_tv);
        TextView description =findViewById(R.id.description_tv);
        TextView ingredients =findViewById(R.id.ingredients_tv);

        name.setText(sandwich.getMainName());

        if (sandwich.getPlaceOfOrigin().isEmpty()){
            origin.setText(R.string.no_data);
        }
        else {
            origin.setText(sandwich.getPlaceOfOrigin());
        }

        //set Text for description
        if (sandwich.getDescription().isEmpty()){
            origin.setText(R.string.no_data);
        }
        else {
            description.setText(sandwich.getDescription());
        }

        List<String >alsoKnown = sandwich.getAlsoKnownAs();
        if (alsoKnown.isEmpty()){
            alsoKnownAs.setText(R.string.no_data);

        }
        else {
            String result="";
            for (String  s :alsoKnown){
                result += s+",";
            }
            if (result.length()>0){
                result=result.substring(0,result.length()-2);

            }
            alsoKnownAs.setText(result);
            result ="";

            List<String > listIngredients = sandwich.getIngredients();
            if (listIngredients.isEmpty()){
                ingredients.setText(R.string.no_data);

            }
            else {
                for (String s:listIngredients){
                    result += s +  "\n";
                }
                ingredients.setText(result);
            }
        }

    }
}
