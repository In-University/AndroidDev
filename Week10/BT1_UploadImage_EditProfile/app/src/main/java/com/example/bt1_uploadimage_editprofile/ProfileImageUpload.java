package com.example.bt1_uploadimage_editprofile;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat; // Use ContextCompat for permissions

import java.io.File;
import java.io.IOException;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileImageUpload extends AppCompatActivity {

    Toolbar toolbarUpload;
    Button btnChoose, btnUpload;
    ImageView imageViewChoose; // The main preview ImageView (R.id.imgChoose)
    // --- End Views ---

    private Uri mUri; // Uri of the selected image
    private ProgressDialog mProgressDialog;
    private String userId; // To store the user ID passed from ProfileActivity

    public static final int MY_REQUEST_CODE = 100;
    public static final String TAG = ProfileImageUpload.class.getName();
    public static final String EXTRA_USER_ID = "USER_ID"; // Key for passing user ID
    public static final String RESULT_EXTRA_IMAGE_URL = "NEW_AVATAR_URL"; // Key for returning result

    // Activity Result Launcher for picking image
    private final ActivityResultLauncher<Intent> mActivityResultLauncher =
            registerForActivityResult(
                    new ActivityResultContracts.StartActivityForResult(),
                    new ActivityResultCallback<ActivityResult>() {
                        @Override
                        public void onActivityResult(ActivityResult result) {
                            Log.d(TAG, "onActivityResult received.");
                            if (result.getResultCode() == Activity.RESULT_OK) {
                                Intent data = result.getData();
                                // Ensure data and URI are not null
                                if (data == null || data.getData() == null) {
                                    Log.e(TAG, "Image selection returned null data or URI.");
                                    Toast.makeText(ProfileImageUpload.this, "Failed to get image.", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                Uri uri = data.getData();
                                Log.d(TAG, "Image selected: " + uri.toString());
                                mUri = uri;
                                try {
                                    // Display the selected image in the preview
                                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                                    imageViewChoose.setImageBitmap(bitmap);
                                } catch (IOException e) {
                                    Log.e(TAG, "Error loading bitmap from URI", e);
                                    Toast.makeText(ProfileImageUpload.this, "Failed to load image preview.", Toast.LENGTH_SHORT).show();
                                    // Reset URI if loading fails?
                                    mUri = null;
                                    // Optionally set back placeholder
                                    imageViewChoose.setImageResource(R.drawable.ic_launcher_background); // Make sure this drawable exists
                                }
                            } else {
                                Log.d(TAG, "Image selection cancelled or failed. Result code: " + result.getResultCode());
                                // No need to show a toast if the user simply cancelled
                            }
                        }
                    });

    // Initialize views
    private void AnhXa() {
        toolbarUpload = findViewById(R.id.toolbarUpload);
        btnChoose = findViewById(R.id.btnChoose);
        btnUpload = findViewById(R.id.btnUpload);
        imageViewChoose = findViewById(R.id.imgChoose); // This is the preview ImageView
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_profile_image);

        // Get User ID passed from ProfileActivity (Important for upload)
        userId = getIntent().getStringExtra(EXTRA_USER_ID);
        if (userId == null || userId.trim().isEmpty()) {
            Log.e(TAG, "User ID not passed via Intent. Upload might fail if ID is required.");
            Toast.makeText(this, "Error: User information missing.", Toast.LENGTH_LONG).show();
            // Handle this case - maybe finish the activity?
            // finish();
            // For demonstration, let's proceed but log the issue.
            userId = "UNKNOWN_USER"; // Assign a default or handle error more gracefully
        }

        // Call AnhXa to find views
        AnhXa();

        // --- Toolbar Setup ---
        setSupportActionBar(toolbarUpload);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true); // Show back arrow
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            // Title is likely set in XML, or you can set it here:
            // getSupportActionBar().setTitle("Change Avatar");
        }

        // Initialize ProgressDialog
        mProgressDialog = new ProgressDialog(this); // Use 'this' for context
        mProgressDialog.setMessage("Please wait, uploading image...");
        mProgressDialog.setCancelable(false); // Prevent dismissing by tapping outside

        // Set Click Listener for Choose Button
        btnChoose.setOnClickListener(v -> checkPermission());

        // Set Click Listener for Upload Button
        btnUpload.setOnClickListener(v -> {
            if (mUri != null) {
                uploadImageToServer(); // Changed method name for clarity
            } else {
                Toast.makeText(ProfileImageUpload.this, "Please choose an image first.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Renamed from UploadImagel for clarity
    private void uploadImageToServer() {
        if (mUri == null) {
            Toast.makeText(this, "No image selected.", Toast.LENGTH_SHORT).show();
            return;
        }
        if (userId == null || userId.equals("UNKNOWN_USER")) {
            Toast.makeText(this, "Cannot upload: User information is missing.", Toast.LENGTH_LONG).show();
            return;
        }


        mProgressDialog.show();

        // --- Prepare data for Retrofit ---

        // 1. User ID (or username if API requires that) as RequestBody
        // Assuming your API needs the user ID as a form part named "userId"
        RequestBody requestUserId = RequestBody.create(MediaType.parse("multipart/form-data"), userId);

        // 2. Get Real Path from Uri using your utility
        String imagePath;
        try {
            imagePath = vn.iotstar.appfoods.Utils.RealPathUtil.getRealPath(this, mUri);
            if (imagePath == null) {
                throw new IOException("Could not resolve file path from Uri.");
            }
            Log.d(TAG, "Resolved Image Path: " + imagePath);
        } catch (Exception e) { // Catch broader exceptions during path resolution
            Log.e(TAG, "Error getting real path from URI: " + mUri, e);
            Toast.makeText(this, "Error processing image file.", Toast.LENGTH_LONG).show();
            mProgressDialog.dismiss();
            return;
        }

        // 3. Create File object
        File file = new File(imagePath);
        if (!file.exists() || !file.canRead()) {
            Log.e(TAG, "File does not exist or cannot be read: " + imagePath);
            Toast.makeText(this, "Selected file is not accessible.", Toast.LENGTH_LONG).show();
            mProgressDialog.dismiss();
            return;
        }

        // 4. Create RequestBody for the file part
        RequestBody requestFile = RequestBody.create(MediaType.parse(getContentResolver().getType(mUri)), file); // Use MIME type from ContentResolver

        // 5. Create MultipartBody.Part (ensure Const.MY_IMAGES is the correct part name expected by your backend API)
        MultipartBody.Part partbodyavatar = MultipartBody.Part.createFormData(Const.MY_IMAGES, file.getName(), requestFile);

        // --- Make API Call using Retrofit ---
        // ** IMPORTANT: Ensure ServiceAPI interface and Retrofit instance are correctly set up **
        // Adjust the API call based on your ServiceAPI definition (e.g., parameters might differ)
        ServiceAPI.serviceApi.upload(requestUserId, partbodyavatar).enqueue(new Callback<List<ImageUpload>>() { // Assuming API returns a List
            @Override
            public void onResponse(@NonNull Call<List<ImageUpload>> call, @NonNull Response<List<ImageUpload>> response) {
                mProgressDialog.dismiss();
                if (response.isSuccessful() && response.body() != null && !response.body().isEmpty()) {
                    // Assuming the first item in the list contains the relevant info
                    ImageUpload uploadResult = response.body().get(0);
                    String newAvatarUrl = uploadResult.getAvatar(); // Get the new URL

                    Log.d(TAG, "Upload successful. New Avatar URL: " + newAvatarUrl);
                    Toast.makeText(ProfileImageUpload.this, "Avatar updated successfully!", Toast.LENGTH_LONG).show();

                    // --- Return result to ProfileActivity ---
                    Intent resultIntent = new Intent();
                    resultIntent.putExtra(RESULT_EXTRA_IMAGE_URL, newAvatarUrl);
                    setResult(Activity.RESULT_OK, resultIntent);
                    finish(); // Close this activity

                } else {
                    // Handle API error response
                    String errorMsg = "Upload failed.";
                    if (!response.isSuccessful()) {
                        errorMsg += " (Code: " + response.code() + ")";
                        try {
                            String errorBody = response.errorBody() != null ? response.errorBody().string() : "No error body";
                            Log.e(TAG, "API Error: " + response.code() + " - " + response.message() + " | Body: " + errorBody);
                        } catch (IOException e) {
                            Log.e(TAG, "Error reading error body", e);
                        }
                    } else if (response.body() == null || response.body().isEmpty()) {
                        Log.e(TAG, "API Error: Response body is null or empty.");
                        errorMsg = "Upload failed: Invalid response from server.";
                    }
                    Toast.makeText(ProfileImageUpload.this, errorMsg, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<ImageUpload>> call, @NonNull Throwable t) {
                mProgressDialog.dismiss();
                Log.e(TAG, "API Call Failed", t);
                Toast.makeText(ProfileImageUpload.this, "Upload failed: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    // --- Permission Handling ---

    // Define permissions based on Android version
    public static String[] storage_permissions = {
            Manifest.permission.READ_EXTERNAL_STORAGE
            // WRITE_EXTERNAL_STORAGE is often not needed just for reading/uploading
            // If your RealPathUtil copies file, you might need it on older APIs
    };

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    public static String[] storage_permissions_33 = {
            Manifest.permission.READ_MEDIA_IMAGES
            // Add READ_MEDIA_VIDEO, READ_MEDIA_AUDIO if needed
    };

    // Helper to get the correct permissions array
    public static String[] permissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            return storage_permissions_33;
        } else {
            return storage_permissions;
        }
    }

    // Check if the required permission is granted
    private boolean isPermissionGranted() {
        String permissionToCheck;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            permissionToCheck = Manifest.permission.READ_MEDIA_IMAGES;
        } else {
            permissionToCheck = Manifest.permission.READ_EXTERNAL_STORAGE;
        }
        return ContextCompat.checkSelfPermission(this, permissionToCheck) == PackageManager.PERMISSION_GRANTED;
    }


    // Check permissions and open gallery if granted, otherwise request
    private void checkPermission() {
        // No runtime permissions needed for versions below M
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            openGallery();
            return;
        }

        if (isPermissionGranted()) {
            Log.d(TAG, "Permission already granted.");
            openGallery();
        } else {
            Log.d(TAG, "Permission not granted, requesting...");
            // Request the appropriate permissions
            requestPermissions(permissions(), MY_REQUEST_CODE);
        }
    }

    // Handle permission request result
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_REQUEST_CODE) {
            // Check if the first permission (the one we need) was granted
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.d(TAG, "Permission granted after request.");
                openGallery();
            } else {
                Log.w(TAG, "Permission denied after request.");
                // Explain to the user why the permission is needed (optional)
                Toast.makeText(this, "Storage permission is required to select an image.", Toast.LENGTH_LONG).show();
            }
        }
    }

    // Launch the image picker intent
    private void openGallery() {
        Log.d(TAG, "Opening gallery.");
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        // intent.setType("image/*"); // ACTION_PICK sets the type implicitly for images
        // intent.setAction(Intent.ACTION_GET_CONTENT); // Use ACTION_PICK for gallery
        mActivityResultLauncher.launch(Intent.createChooser(intent, "Select Profile Picture"));
    }

    // Handle Toolbar back button press
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // Handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            // onBackPressed(); // Standard back behavior
            finish(); // Close this activity
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
