package com.example.shipeatscustomer;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.view.MenuItem;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AdminDialogHelper {
    private static DatabaseReference menuRef = FirebaseDatabase.getInstance().getReference("Menu");

    // Fix: Use A5_MenuItem instead of android.view.MenuItem
    public static void showEditMenuDialog(Context context, A5_MenuItem item, boolean isNew) {
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.activity_a5_edit_menu);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        EditText etName = dialog.findViewById(R.id.etName);
        EditText etDesc = dialog.findViewById(R.id.etDescription);
        EditText etPrice = dialog.findViewById(R.id.etPrice);
        EditText etQty = dialog.findViewById(R.id.etQuantity);
        EditText etCat = dialog.findViewById(R.id.etCategory);
        Button btnDone = dialog.findViewById(R.id.btnDone);
        Button btnCancel = dialog.findViewById(R.id.btnCancel);

        if (!isNew && item != null) {
            etName.setText(item.name);
            etDesc.setText(item.description);
            etPrice.setText(item.price);
            etQty.setText(item.quantity);
            etCat.setText(item.category);
        }

        btnDone.setOnClickListener(v -> {
            String id = isNew ? menuRef.push().getKey() : item.id;
            // Create updated item object
            A5_MenuItem updatedItem = new A5_MenuItem(id,
                    etName.getText().toString(),
                    etDesc.getText().toString(),
                    etPrice.getText().toString(),
                    etQty.getText().toString(),
                    etCat.getText().toString(),
                    item != null ? item.imageUrl : "");

            menuRef.child(id).setValue(updatedItem).addOnSuccessListener(aVoid -> {
                dialog.dismiss();
                showStatusDialog(context, isNew ? R.layout.admin_dialog_menu_add : R.layout.admin_dialog_menu_complete);
            });
        });

        btnCancel.setOnClickListener(v -> dialog.dismiss());
        dialog.show();
    }

    // New: Handle Delete Confirmation
    public static void showDeleteConfirmDialog(Context context, String itemId) {
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.admin_dialog_item_confirm_delete);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        Button btnDelete = dialog.findViewById(R.id.btnDelete);
        Button btnCancel = dialog.findViewById(R.id.btnCancel);

        btnDelete.setOnClickListener(v -> {
            menuRef.child(itemId).removeValue().addOnSuccessListener(aVoid -> {
                dialog.dismiss();
                showStatusDialog(context, R.layout.admin_dialog_menu_delete);
            });
        });

        btnCancel.setOnClickListener(v -> dialog.dismiss());
        dialog.show();
    }

    public static void showStatusDialog(Context context, int layoutId) {
        Dialog dialog = new Dialog(context);
        dialog.setContentView(layoutId);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.show();
        new android.os.Handler().postDelayed(dialog::dismiss, 1500);
    }
}