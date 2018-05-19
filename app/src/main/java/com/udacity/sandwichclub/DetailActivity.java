package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import org.json.JSONException;

import java.util.Iterator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;
    private static final String TAG = DetailActivity.class.getSimpleName();

    @BindView(R.id.image_iv)
    ImageView ingredientsIv;

    @BindView(R.id.also_known_tv)
    TextView alsoKnownTv;

    @BindView(R.id.origin_tv)
    TextView originTv;

    @BindView(R.id.description_tv)
    TextView descriptionTv;

    @BindView(R.id.ingredients_tv)
    TextView ingredientsTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ButterKnife.bind(this);

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
        Sandwich sandwich;

        try {
            sandwich = JsonUtils.parseSandwichJson(json);
        } catch (JSONException e) {
            // Error parsing json
            Log.e(TAG, e.getMessage());
            closeOnError();
            return;
        }

        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                /*.placeholder(R.drawable.sad_sandwich)*/
                .error(R.drawable.sad_sandwich)
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {
        if (sandwich.getAlsoKnownAs() != null && !sandwich.getAlsoKnownAs().isEmpty() && !sandwich.getAlsoKnownAs().get(0).equals("")) {
            alsoKnownTv.setText(listToString(sandwich.getAlsoKnownAs(), ", "));
        }
        if (sandwich.getPlaceOfOrigin() != null && sandwich.getPlaceOfOrigin().length() > 0) {
            originTv.setText(sandwich.getPlaceOfOrigin());
        }
        if (sandwich.getDescription() != null && sandwich.getDescription().length() > 0) {
            descriptionTv.setText(sandwich.getDescription());
        }
        if (sandwich.getIngredients() != null && !sandwich.getIngredients().isEmpty() && !sandwich.getIngredients().get(0).equals("")) {
            ingredientsTv.setText(listToString(sandwich.getIngredients(), "\n"));
        }
    }

    private String listToString(List<String> stringList, String delimiter) {
        StringBuilder sb = new StringBuilder();
        Iterator<String> stringIterator = stringList.iterator();

        while (stringIterator.hasNext()) {
            sb.append(stringIterator.next());
            if (stringIterator.hasNext()) {
                sb.append(delimiter);
            }
        }
        return sb.toString();
    }
}
