package com.sjsu.cmpe273.lparilogisticapp;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.InputType;
import android.view.Gravity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

/** Scans a package barcode before hand-off. A manual entry fallback covers damaged or unreadable labels. */
public class SimpleScannerActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {

    private static final int CAMERA_REQUEST = 1;

    private ZXingScannerView scannerView;
    private int position;

    @Override
    public void onCreate(Bundle state) {
        super.onCreate(state);
        position = getIntent().getIntExtra("adapterPosition", 0);

        FrameLayout root = new FrameLayout(this);
        scannerView = new ZXingScannerView(this);
        root.addView(scannerView);

        Button manual = new Button(this);
        manual.setText(R.string.enter_code_manually);
        manual.setOnClickListener(v -> askForCode());
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT,
                Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL);
        params.bottomMargin = 96;
        root.addView(manual, params);
        setContentView(root);

        if (!hasCamera()) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, CAMERA_REQUEST);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (hasCamera()) {
            scannerView.setResultHandler(this);
            scannerView.startCamera();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        scannerView.stopCamera();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] results) {
        super.onRequestPermissionsResult(requestCode, permissions, results);
        if (requestCode == CAMERA_REQUEST && (results.length == 0 || results[0] != PackageManager.PERMISSION_GRANTED)) {
            Toast.makeText(this, R.string.camera_denied, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void handleResult(Result rawResult) {
        finishWithCode(rawResult.getText());
    }

    private void askForCode() {
        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        input.setHint(R.string.package_code);
        new AlertDialog.Builder(this)
                .setTitle(R.string.enter_code_manually)
                .setView(input)
                .setPositiveButton(R.string.confirm, (dialog, which) -> {
                    String code = input.getText().toString().trim();
                    if (!code.isEmpty()) {
                        finishWithCode(code);
                    }
                })
                .setNegativeButton(R.string.cancel, null)
                .show();
    }

    private void finishWithCode(String code) {
        Intent result = new Intent();
        result.putExtra("result", 12345);
        result.putExtra("position", position);
        result.putExtra("code", code);
        setResult(Activity.RESULT_OK, result);
        finish();
    }

    private boolean hasCamera() {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED;
    }
}
