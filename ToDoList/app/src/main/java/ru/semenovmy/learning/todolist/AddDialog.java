package ru.semenovmy.learning.todolist;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

public class AddDialog extends DialogFragment {

    private EditText mEditText;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View root = inflater.inflate(R.layout.add_dialog, null);
        mEditText = root.findViewById(R.id.goal);
        builder.setView(root);

        builder.setPositiveButton(getResources().getString(R.string.add), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ((AddListener) requireActivity()).addGoal(mEditText.getText().toString());
                    }
                })
                .setTitle(getResources().getString(R.string.set_goal));

        return builder.create();
    }
}
