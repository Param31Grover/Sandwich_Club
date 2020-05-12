package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
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

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = null;
        try {
            sandwich = JsonUtils.parseSandwichJson(json);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }
        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {
        TextView mainNameTextView = findViewById(R.id.main_name);
        String mainName = sandwich.getMainName();
        mainNameTextView.setText(mainName);

        TextView originTextView = findViewById(R.id.place_of_origin_tv);
        String origin = sandwich.getPlaceOfOrigin();
        if (!origin.isEmpty()) originTextView.setText(origin);
        else {
            originTextView.setVisibility(View.GONE);
            TextView originLabel = findViewById(R.id.place_of_origin_tv);
            originLabel.setVisibility(View.GONE);
        }

        TextView descriptionTextView = findViewById(R.id.description_tv);
        String description = sandwich.getDescription();
        if (!description.isEmpty()) descriptionTextView.setText(description);
        else {
            descriptionTextView.setVisibility(View.GONE);
            TextView descriptionLabel = findViewById(R.id.description_tv);
            descriptionLabel.setVisibility(View.GONE);
        }

        TextView ingredientsTextView = findViewById(R.id.ingredients_tv);
        List<String> ingredients = sandwich.getIngredients();
        if (!ingredients.isEmpty()) {
            for (int i = 0; i < ingredients.size(); i++) {
                ingredientsTextView.append("-> " + ingredients.get(i) + "\n");
            }
        } else {
            ingredientsTextView.setVisibility(View.GONE);
            TextView ingredientsLabel = findViewById(R.id.ingredients_tv);
            ingredientsLabel.setVisibility(View.GONE);
        }
        TextView alsoKnownAsTextView = findViewById(R.id.also_known_tv);
        List<String> alsoKnownAs = sandwich.getAlsoKnownAs();
        if (!alsoKnownAs.isEmpty()) {
            for (int i = 0; i < alsoKnownAs.size(); i++) {
                alsoKnownAsTextView.append("-> " + alsoKnownAs.get(i) + "\n");
            }
        } else {
            alsoKnownAsTextView.setVisibility(View.GONE);
            TextView alsoKnownAsLabel = findViewById(R.id.also_known_tv);
            alsoKnownAsLabel.setVisibility(View.GONE);
        }

    }
}
