package com.example.green2;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.UUID;

public class send_to_firebase extends AppCompatActivity {
    private ImageView imageView;
   // private EditText txtimageName;
    private Button sendimage, uploadImage;
    private Uri imgUri;
  //  private StorageReference mStorageRef;
    //private DatabaseReference ndatabaseRef;

    // public static final String FB_STORAGE_PATH = "image/";
    //public static final String FB_DATABASE_PATH = "image/";
    FirebaseStorage storage;
    StorageReference storageReference;
    public final int PIC_IMAGE_REQUEST = 71;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_to_firebase);
        storage=FirebaseStorage.getInstance();
        storageReference=storage.getReference();

       // mStorageRef = FirebaseStorage.getInstance().getReference();
        //ndatabaseRef = FirebaseDatabase.getInstance().getReference();
        sendimage = (Button) findViewById(R.id.sendImage);
        uploadImage = (Button) findViewById(R.id.UploadImage);
        imageView = (ImageView) findViewById(R.id.Image);
        //txtimageName = (EditText) findViewById(R.id.txtImageView);

        sendimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage();

            }
        });
        uploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UploadImage();

            }
        });

    }

   /* @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imgUri = data.getData();

            try {
                Bitmap bm = MediaStore.Images.Media.getBitmap(getContentResolver(), imgUri);
                imageView.setImageBitmap(bm);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();

            }
        }
    }

    public String getImageExt(Uri uri) {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }


    public void UploadImage_Click(View v) {
        if (imgUri != null) {
            final ProgressDialog dialog = new ProgressDialog(this);
            dialog.setTitle("Uploading Image");

            dialog.show();

            final StorageReference ref = mStorageRef.child(FB_STORAGE_PATH + System.currentTimeMillis() + "." + getImageExt(imgUri));
            ref.putFile(imgUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    dialog.dismiss();
                    Toast.makeText(getApplicationContext(), "Image Uploaded", Toast.LENGTH_SHORT).show();
                    ImageUpload imageUpload;
                     imageUpload = new ImageUpload(txtimageName.getText().toString(), taskSnapshot.getMetadata().getReference().getDownloadUrl().toString())  ;

                    String uploadId=ndatabaseRef.push().getKey();
                    ndatabaseRef.child(uploadId).setValue(imageUpload);
                }
            })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                            dialog.dismiss();
                            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {

                            double progress=(100 * taskSnapshot.getBytesTransferred())/taskSnapshot.getTotalByteCount();
                            dialog.setMessage("Uploaded" + (int)progress + "%");

                        }
                    });

        }
        else {

            Toast.makeText(getApplicationContext(), "Please Select Your Image", Toast.LENGTH_SHORT).show();
        }
    }*/




   public void UploadImage() {

       if (imgUri != null) {
           final ProgressDialog dialog = new ProgressDialog(this);
           dialog.setTitle("Uploading Image");
           dialog.show();
           StorageReference ref = storageReference.child("images/*" + UUID.randomUUID().toString());
           ref.putFile(imgUri)
                   .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                       @Override
                       public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                           dialog.dismiss();
                           Toast.makeText(send_to_firebase.this,"Uploaded...",Toast.LENGTH_SHORT).show();
                       }
                   })
                   .addOnFailureListener(new OnFailureListener() {
                       @Override
                       public void onFailure(@NonNull Exception e) {
                           dialog.dismiss();
                           Toast.makeText(send_to_firebase.this,"Failed..."+e.getMessage(),Toast.LENGTH_SHORT).show();
                       }
                   })
                   .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                       @Override
                       public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {

                           double progress=(100 * taskSnapshot.getBytesTransferred())/taskSnapshot.getTotalByteCount();
                           dialog.setMessage("Uploaded"+(int)progress + "%");
                       }
                   });
       }
   }



    public void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select the picture..."), PIC_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PIC_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imgUri = data.getData();

            try {
                Bitmap bm = MediaStore.Images.Media.getBitmap(getContentResolver(), imgUri);
                imageView.setImageBitmap(bm);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();

            }
        }
    }
}
