package com.example.calculadora_saudavel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Locale;

public class TmbActivity extends AppCompatActivity {

    private EditText editWeight;
    private EditText editHeight;
    private EditText editAge;
    private Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tmb);

        editWeight = findViewById(R.id.tmb_weight);
        editHeight = findViewById(R.id.tmb_height);
        editAge = findViewById(R.id.tmb_age);
        spinner = findViewById(R.id.tmb_lifestyle);
        Button btnSend = findViewById(R.id.tmb_send);

        btnSend.setOnClickListener(v -> {
            if (!validate()) {
                Toast.makeText(TmbActivity.this, R.string.fields_message, Toast.LENGTH_SHORT).show();
                return;
            }
            String sHeight = editHeight.getText().toString();
            String sWeight= editWeight.getText().toString();
            String sAge= editAge.getText().toString();

            int height = Integer.parseInt(sHeight);
            double weight = Double.parseDouble(sWeight);
            int age= Integer.parseInt(sAge);

            double tmb = calculateTmb(height, weight, age);
            double cal = tmbResponse(tmb);

            AlertDialog alertDialog = new AlertDialog.Builder(TmbActivity.this)
                    .setTitle(getString(R.string.tmb_response, tmb))
                    .setMessage(String.format(new Locale("pt", "BR"), "cal: %.2f", cal))
                    .setNegativeButton(android.R.string.ok, (dialog, which) -> dialog.dismiss())
                    .setPositiveButton(R.string.save, (dialog, which) -> {
                        SqlHelper sqlHelper = SqlHelper.getInstance(TmbActivity.this);
                        long calcId = sqlHelper.addItem(SqlHelper.TYPE_TMB, tmb);

                        if (calcId > 0)
                            Toast.makeText(TmbActivity.this, R.string.calc_saved, Toast.LENGTH_LONG).show();
                        openListCalcActivity();
                    })
                    .create();
            alertDialog.show();

            InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            im.hideSoftInputFromWindow(editWeight.getWindowToken(),0);
            im.hideSoftInputFromWindow(editHeight.getWindowToken(),0);
            im.hideSoftInputFromWindow(editAge.getWindowToken(),0);
            //aa
        });

    }

    private double calculateTmb(int height, double weight, int age) {
        return 66 + (weight * 13.8) + (5 * height) - (6.8 * age);
    }

    private double tmbResponse(double tmb) {
        int selectedItemPosition = spinner.getSelectedItemPosition();
        switch (selectedItemPosition) {
            case 0: return tmb * 1.2;
            case 1: return tmb * 1.375;
            case 2: return tmb * 1.55;
            case 3: return tmb * 1.725;
            case 4: return tmb * 1.9;
            default: return 0;
        }
    }

    private void openListCalcActivity() {
        Intent intent = new Intent(TmbActivity.this, ListCalcActivity.class);
        intent.putExtra("type", SqlHelper.TYPE_TMB);
        TmbActivity.this.startActivity(intent);
    }

    private boolean validate () {
        return !editHeight.getText().toString().startsWith("0")
                && !editWeight.getText().toString().startsWith("0")
                && !editAge.getText().toString().startsWith("0")
                && !editHeight.getText().toString().isEmpty()
                && !editAge.getText().toString().isEmpty()
                && !editWeight.getText().toString().isEmpty();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_list:
                openListCalcActivity();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

}
