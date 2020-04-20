package com.example.calculadora_saudavel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ListCalcActivity extends AppCompatActivity {

    private List<SqlHelper.Register> datas = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_calc);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String type = extras.getString("type");

            SqlHelper db = SqlHelper.getInstance(this);
            datas.addAll(db.getRegisterBy(type));

            ListCalcAdapter adapter = new ListCalcAdapter(datas);
            RecyclerView recyclerView = findViewById(R.id.recycler_view_list);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));

        }



    }

    private class ListCalcAdapter extends RecyclerView.Adapter<ListCalcViewHolder> {

        private final List<SqlHelper.Register> datas;

        ListCalcAdapter(List<SqlHelper.Register> datas) {
            this.datas = datas;
        }

        @NonNull
        public ListCalcViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = getLayoutInflater().inflate(android.R.layout.simple_list_item_1, viewGroup, false);

            return new ListCalcViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ListCalcViewHolder listCalcViewHolder, int i) {
            SqlHelper.Register data = this.datas.get(i);
            listCalcViewHolder.bind(data);
        }

        @Override
        public int getItemCount() {
            return datas.size();
        }

    }

    private static class ListCalcViewHolder extends  RecyclerView.ViewHolder {
        ListCalcViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        void bind(SqlHelper.Register data) {
            ((TextView) itemView).setText(String.format(new Locale("pt", "BR"),
                    "resultado: %.2f, data: %s", data.response, data.createdDate));
        }
    }

}
