package com.example.galleries;

import androidx.appcompat.app.AppCompatActivity;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    ArrayList<Integer> mImageIds = new ArrayList<>(Arrays.asList(
            R.drawable.img1, R.drawable.img2, R.drawable.img3, R.drawable.img4,
            R.drawable.img5, R.drawable.img6, R.drawable.img7, R.drawable.img8,
            R.drawable.img9, R.drawable.img10, R.drawable.img11, R.drawable.img12
    ));

    Button BSelectImage;
    ImageView IVPreviewImage;
    int SELECT_PICTURE = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GridView gridView = findViewById(R.id.myGrid);
        BSelectImage = findViewById(R.id.BSelectImage);
        IVPreviewImage = findViewById(R.id.IVPreviewImage);

        ImageAdapter adapter = new ImageAdapter(mImageIds, this);
        gridView.setAdapter(adapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int item_pos = mImageIds.get(position);
                ShowDialogBox(item_pos);
            }
        });

        BSelectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageChooser();
            }
        });
    }

    private void ShowDialogBox(final int item_pos) {
        final Dialog dialog = new Dialog(this);

        dialog.setContentView(R.layout.custom_dialog);

        TextView Image_name = dialog.findViewById(R.id.txt_Image_name);
        ImageView Image = dialog.findViewById(R.id.img);
        Button btn_Full = dialog.findViewById(R.id.btn_full);
        Button btn_Close = dialog.findViewById(R.id.btn_close);

        String title = getResources().getResourceName(item_pos);

        int index = title.indexOf("/");
        String name = title.substring(index + 1, title.length());
        Image_name.setText(name);

        Image.setImageResource(item_pos);

        btn_Close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btn_Full.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, FullView.class);
                i.putExtra("img_id", item_pos);
                startActivity(i);
            }
        });

        dialog.show();
    }

    private void imageChooser() {
        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, SELECT_PICTURE);
    }

    // Handle the selected image result in onActivityResult() method
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SELECT_PICTURE && resultCode == RESULT_OK && data != null) {
            Uri selectedImageUri = data.getData();
            // Use the selected image URI for further processing (e.g., display in ImageView)
            IVPreviewImage.setVisibility(View.VISIBLE);
            IVPreviewImage.setImageURI(selectedImageUri);
        }
    }
}
