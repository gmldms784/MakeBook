package com.uos.makebook.Page;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.uos.makebook.R;

import petrov.kristiyan.colorpicker.ColorPicker;

public class AddTextActivity extends AppCompatActivity {
    private TextView doneText;
    private TextView colorText;
    private TextView sizeText;
    private EditText editText;
    private ColorPicker colorPicker;
    private int textColor = Color.BLACK;
    private int textSize = 64;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_bookpage_addtext);

        doneText = findViewById(R.id.add_text_done);
        colorText = findViewById(R.id.add_text_color);
        sizeText = findViewById(R.id.add_text_size);
        editText = findViewById(R.id.add_text_edit_text);

        Intent intent = getIntent();
        if (intent.hasExtra("value")) {
            editText.setText(intent.getStringExtra("value"));
        }
        if (intent.hasExtra("fontSize")) {
            textSize = intent.getIntExtra("fontSize", textSize);
            editText.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
        }
        if (intent.hasExtra("fontColor")) {
            textColor = intent.getIntExtra("fontColor", textColor);
            editText.setTextColor(textColor);
        }

        doneText.setOnClickListener(v -> {
            Intent result = new Intent();

            result.putExtra("value", editText.getText().toString());
            result.putExtra("fontColor", textColor);
            result.putExtra("fontSize", textSize);

            setResult(RESULT_OK, result);
            finish();
        });

        colorText.setOnClickListener(v -> {
            colorPicker = new ColorPicker(this);
            colorPicker.setOnFastChooseColorListener(new ColorPicker.OnFastChooseColorListener() {
                @Override
                public void setOnFastChooseColorListener(int position, int color) {
                    colorPicker.setColorButtonTickColor(position);
                    textColor = color;
                    editText.setTextColor(textColor);
                }

                @Override
                public void onCancel() { }
            });

            colorPicker.setColumns(5);
            colorPicker.setTitle("글씨 색 선택");
            colorPicker.setDefaultColorButton(textColor);
            colorPicker.setRoundColorButton(true);
            colorPicker.show();
        });

        editText.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
        sizeText.setOnClickListener(v -> {
            NumberPicker picker = new NumberPicker(this);
            picker.setMaxValue(300);
            picker.setMinValue(5);
            picker.setValue(textSize);

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setView(picker);
            builder.setTitle("글씨 크기 선택");
            builder.setPositiveButton("OK", (dialog, which) -> {
                textSize = picker.getValue();
                editText.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
            });
            builder.setNegativeButton("CANCEL", (dialog, which) -> { });

            final AlertDialog dialog = builder.create();
            dialog.setOnShowListener( new DialogInterface.OnShowListener() {
                @Override
                public void onShow(DialogInterface arg0) {
                    dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.parseColor("#3181F7"));
                    dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.parseColor("#3181F7"));
                }
            });
            dialog.show();
        });
    }
}
