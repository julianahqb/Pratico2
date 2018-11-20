package com.example.professor.pratico2;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

public class ItemDialog extends AppCompatDialogFragment implements DialogInterface.OnClickListener {
    private OnItemListener listener;
    private EditText edtItem;
    private String item;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle(R.string.item);
        builder.setPositiveButton(R.string.ok, this);
        builder.setNegativeButton(R.string.cancelar, this);

        LayoutInflater layoutInflater = getActivity().getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.dialog_item, null);
        builder.setView(view);

        this.edtItem = (EditText) view.findViewById(R.id.edtItem);

        if (item != null) {
            edtItem.setText(item);
        }
        return builder.create();
    }

    public void setItem(String item) {
        this.item = item;
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        if (which == DialogInterface.BUTTON_POSITIVE) {
            String item = edtItem.getText().toString();
            if (!TextUtils.isEmpty(item)) {
                listener.onItem(item);
            }
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (!(context instanceof OnItemListener)) {
            throw new IllegalArgumentException("A activity deve implementar ItemDialog.ItemListener");
        }
        this.listener = (OnItemListener) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    public interface OnItemListener {
        public void onItem(String item);
    }
}
