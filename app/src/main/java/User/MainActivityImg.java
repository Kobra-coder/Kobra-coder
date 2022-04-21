package User;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.Image;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.tasks.OnFailureListener;


import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.text.FirebaseVisionText;
import com.google.firebase.ml.vision.text.FirebaseVisionTextRecognizer;
import com.ustglobal.arcloudanchors.R;

import java.util.List;


public class MainActivityImg extends AppCompatActivity {

    Button mCapture,mDetect;
    ImageView mImageView;
    TextView mTextView;
    Bitmap mBitmap;
    Image image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_rec);
        if (ContextCompat.checkSelfPermission(MainActivityImg.this,
                Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {

            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivityImg.this,
                    Manifest.permission.CAMERA)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            } else {
                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(MainActivityImg.this,
                        new String[]{Manifest.permission.CAMERA},
                        REQUEST_IMAGE_CAPTURE);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }
        //int rotation = getRotationCompensation("CameraID",)
        //CameraManager cameraManager = (CameraManager) this.getSystemService(Context.CAMERA_SERVICE);
        mImageView = (ImageView) findViewById(R.id.imageViewid);
        mCapture = (Button) findViewById(R.id.capturebtnid);
        mDetect = (Button) findViewById(R.id.detectbtnid);
        mTextView = (TextView) findViewById(R.id.textView2);

        mCapture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dispatchTakePictureIntent();
            }
        });

        mDetect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                detectText();
            }
        });
    }

    static final int REQUEST_IMAGE_CAPTURE = 1;

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            mBitmap = (Bitmap) extras.get("data");
            mImageView.setImageBitmap(mBitmap);
        }
    }

    private void detectText() {
        FirebaseVisionImage image = FirebaseVisionImage.fromBitmap(mBitmap);
        FirebaseVisionTextRecognizer textRecognizer = FirebaseVision.getInstance()
                .getOnDeviceTextRecognizer();

        textRecognizer.processImage(image)
                .addOnSuccessListener(new OnSuccessListener<FirebaseVisionText>() {
                    @Override
                    public void onSuccess(FirebaseVisionText result) {
                        processText(result);
                    }
                })
                .addOnFailureListener(
                        new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                // Task failed with an exception
                                // ...
                            }
                        });
    }

    private void processText(FirebaseVisionText mVisionText){
        List<FirebaseVisionText.TextBlock> mBlocks = mVisionText.getTextBlocks();
        if (mBlocks.size()==0){
            Toast.makeText(MainActivityImg.this,"No Text Found",Toast.LENGTH_SHORT).show();
            return;
        }
        for (FirebaseVisionText.TextBlock mBlock_i: mVisionText.getTextBlocks()) {
            String mText = mBlock_i.getText();

            if(mText.equals("206")){
                mImageView=(ImageView) findViewById(R.id.imageViewid1);
                mImageView.setImageResource(R.drawable.dean);
                mTextView.setTextSize(14);
                mTextView.setText(R.string.dean);
                mTextView.setTextColor(getColor(R.color.gmm_white));
            }
            else if(mText.equals("205")){
                mImageView=(ImageView) findViewById(R.id.imageViewid1);
                mImageView.setImageResource(R.drawable.kaferdra);
                mTextView.setTextSize(14);
                mTextView.setText(R.string.kaferdr);
                mTextView.setTextColor(getColor(R.color.gmm_white));
            }
            else if(mText.equals("222")){
                mImageView=(ImageView) findViewById(R.id.imageViewid1);
                mImageView.setImageResource(R.drawable.kaferdra);
                mTextView.setTextSize(14);
                mTextView.setText(R.string.lekzal);
                mTextView.setTextColor(getColor(R.color.gmm_white));
            }
        }


    }
}
