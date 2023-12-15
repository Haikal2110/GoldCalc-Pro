package com.example.goldcalculator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    EditText etWeight;
    RadioGroup radioGroupType;
    RadioButton radioKeep;
    RadioButton radioWear; // Rename radioSell to radioWear for clarity
    EditText etValue;
    Button btnResult;
    Button btnClear;
    TextView eWeightResult;
    TextView ePay;
    TextView eTotal;
    ImageView imageinfo;
    Toolbar myToolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etWeight = findViewById(R.id.editTextNumberDecimal3);
        radioGroupType = findViewById(R.id.radioGroupType);
        radioKeep = findViewById(R.id.radioKeep);
        radioWear = findViewById(R.id.radioSell);
        etValue = findViewById(R.id.editTextNumberDecimal);
        btnResult = findViewById(R.id.btnResult);
        eWeightResult = findViewById(R.id.textView4);
        ePay = findViewById(R.id.textView5);
        eTotal = findViewById(R.id.textView6);
        myToolbar = findViewById(R.id.my_toolbar);
        btnResult = findViewById(R.id.btnResult);
        btnClear = findViewById(R.id.button);
        imageinfo=findViewById(R.id.imageView3);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setTitle(R.string.app_name);

        imageinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoURL("https://www.goodreturns.in/gold-rates/malaysia.html");
            }
        });

        btnResult.setOnClickListener(this);
        btnClear.setOnClickListener(this);
    }

    private void gotoURL(String s) {
        Uri uri=Uri.parse(s);
        startActivity(new Intent(Intent.ACTION_VIEW,uri));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.item_share) {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_TEXT, "Please use my application");
            startActivity(Intent.createChooser(shareIntent, null));

            return true;
        } else if (item.getItemId() == R.id.item_about) {
            Intent aboutIntent = new Intent(this, About.class);
            startActivity(aboutIntent);
            return true;

        } else if (item.getItemId()==R.id.item_profile) {
            Intent profileIntent= new Intent(this, Profile.class);
            startActivity(profileIntent);
            return true;
        }
        return false;
    }



    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnResult) {
            try {
                double goldWeight = Double.parseDouble(etWeight.getText().toString());

                // Get the selected RadioButton
                String goldType;
                if (radioKeep.isChecked()) {
                    goldType = "keep";
                } else if (radioWear.isChecked()) {
                    goldType = "wear";
                } else {
                    // Handle the case where neither is selected
                    throw new IllegalStateException("Please select a gold type.");
                }

                double goldValue = Double.parseDouble(etValue.getText().toString());

                // Calculate values based on gold type
                double x = (goldType.equals("keep")) ? 85.0 : 200.0;
                double weightMinusX = Math.max(goldWeight - x, 0.0);
                double zakatPayableValue = weightMinusX * goldValue;
                double totalZakat = zakatPayableValue * 0.025;
                double totalGoldValue = goldWeight * goldValue;

                // Display the results
                String resultText = String.format("Total Value: RM%.2f\nZakat Payable Value: RM%.2f\nTotal Zakat: RM%.2f",
                        totalGoldValue, zakatPayableValue, totalZakat);

                eWeightResult.setText("Total Value: " + String.valueOf(totalGoldValue));
                ePay.setText("Zakat Payable: " + String.valueOf(zakatPayableValue));
                eTotal.setText("Total Zakat: " + String.valueOf(totalZakat));

            } catch (NumberFormatException e) {
                eWeightResult.setText("Please enter valid numbers.");
                ePay.setText("");
                eTotal.setText("");
            } catch (IllegalStateException e) {
                eWeightResult.setText(e.getMessage());
                ePay.setText("");
                eTotal.setText("");
            }
        } else if (v.getId() == R.id.button) {
            // Clear all values
            etWeight.setText("");
            radioGroupType.clearCheck();
            etValue.setText("");
            eWeightResult.setText("");
            ePay.setText("");
            eTotal.setText("");
        }
    }
}







