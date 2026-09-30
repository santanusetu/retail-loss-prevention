package com.sjsu.cmpe273.lparilogisticapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.github.gcacace.signaturepad.views.SignaturePad;
import com.sjsu.cmpe273.lparilogisticapp.data.TripRepository;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/** Captures the customer's signature as proof of delivery, then marks the drop delivered. */
public class SignatureActivity extends AppCompatActivity {

    public static final String EXTRA_DROP_NO = "dropNo";

    private static final String TAG = "SignatureActivity";

    private SignaturePad signaturePad;
    private Button clearButton;
    private Button saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signature);

        signaturePad = findViewById(R.id.signature_pad);
        clearButton = findViewById(R.id.clear_button);
        saveButton = findViewById(R.id.save_button);

        signaturePad.setOnSignedListener(new SignaturePad.OnSignedListener() {
            @Override
            public void onStartSigning() {
            }

            @Override
            public void onSigned() {
                saveButton.setEnabled(true);
                clearButton.setEnabled(true);
            }

            @Override
            public void onClear() {
                saveButton.setEnabled(false);
                clearButton.setEnabled(false);
            }
        });

        clearButton.setOnClickListener(v -> signaturePad.clear());
        saveButton.setOnClickListener(v -> {
            String dropNo = getIntent().getStringExtra(EXTRA_DROP_NO);
            if (saveProofOfDelivery(dropNo, signaturePad.getSignatureBitmap())) {
                TripRepository.getInstance().markDelivered(dropNo);
                Toast.makeText(this, R.string.signature_saved, Toast.LENGTH_SHORT).show();
                Intent home = new Intent(this, HomeActivity.class);
                home.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                home.putExtra(HomeActivity.EXTRA_SHOW_COMPLETED, true);
                startActivity(home);
                finish();
            } else {
                Toast.makeText(this, R.string.signature_failed, Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Saves the signature in the app's private storage. The 2016 version wrote to the public
     * Pictures folder, which Android 10+ blocks, so every save failed.
     */
    private boolean saveProofOfDelivery(String dropNo, Bitmap signature) {
        File dir = new File(getFilesDir(), "proof-of-delivery");
        if (!dir.exists() && !dir.mkdirs()) {
            return false;
        }
        String name = (dropNo == null ? "drop" : dropNo.replaceAll("[^A-Za-z0-9]", "_"))
                + "_" + System.currentTimeMillis() + ".jpg";

        Bitmap flattened = Bitmap.createBitmap(signature.getWidth(), signature.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(flattened);
        canvas.drawColor(Color.WHITE);
        canvas.drawBitmap(signature, 0, 0, null);

        try (OutputStream out = new FileOutputStream(new File(dir, name))) {
            return flattened.compress(Bitmap.CompressFormat.JPEG, 85, out);
        } catch (IOException e) {
            Log.e(TAG, "Could not save signature", e);
            return false;
        }
    }
}
