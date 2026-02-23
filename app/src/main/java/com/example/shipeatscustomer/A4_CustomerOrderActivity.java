package com.example.shipeatscustomer;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class A4_CustomerOrderActivity extends AppCompatActivity {
    private TabLayout tabLayout;
    private RecyclerView rvOrders;
    private AdminOrdersAdapter adapter;
    private List<AdminOrderModel> orderList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a4_cust_order);

        tabLayout = findViewById(R.id.tabLayout);
        rvOrders = findViewById(R.id.rvOrders);
        rvOrders.setLayoutManager(new LinearLayoutManager(this));
        adapter = new AdminOrdersAdapter(this, orderList);
        rvOrders.setAdapter(adapter);

        // Default Load
        loadOrders("Pending");

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                loadOrders(tab.getText().toString());
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}
            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });
    }

    private void loadOrders(String status) {
        FirebaseDatabase.getInstance().getReference("Orders")
                .orderByChild("status").equalTo(status)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        orderList.clear();
                        for (DataSnapshot ds : snapshot.getChildren()) {
                            orderList.add(ds.getValue(AdminOrderModel.class));
                        }
                        adapter.notifyDataSetChanged();
                    }
                    @Override
                    public void onCancelled(DatabaseError error) {}
                });
    }
}
