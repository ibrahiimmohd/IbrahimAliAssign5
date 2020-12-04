package ibrahim.ali.s301022172.ui.IbrahimFragment;

import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;

import ibrahim.ali.s301022172.R;

public class IbrDown extends Fragment {

    ImageView imageView;
    ProgressDialog p;
    String DIR_NAME, filename;
    File imageExist;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.ibr_down, container, false);
        final TextView textView = root.findViewById(R.id.Ibrahim);

        Button button= root.findViewById(R.id.ibrahimDownloadBtn);
        imageView = root.findViewById(R.id.ibrahimImg);

        DIR_NAME = "Assignment5 Images";
        filename = "filename.jpg";

        imageExist =
                new File(Environment
                        .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
                        .getAbsolutePath() + File.separator + DIR_NAME + File.separator + filename);

        button.setOnClickListener(v -> {

            //ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
            //ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},1);

            imageExist =
                    new File(Environment
                            .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
                            .getAbsolutePath() + File.separator + DIR_NAME + File.separator + filename);

            if(!imageExist.exists()){

                AsyncDownloadImg asyncTask = new AsyncDownloadImg();
                String downloadUrlOfImage = "https://onekindplanet.org/wp-content/uploads/2016/09/az_bald-eagle-520x294.jpg";
                asyncTask.execute(downloadUrlOfImage);


                File direct =
                        new File(Environment
                                .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
                                .getAbsolutePath() + "/" + DIR_NAME + "/");

                if (!direct.exists()) {
                    direct.mkdir();
                    Log.d("Testing", "dir created for first time");
                }

                DownloadManager dm = (DownloadManager) getContext().getSystemService(Context.DOWNLOAD_SERVICE);
                Uri downloadUri = Uri.parse(downloadUrlOfImage);
                DownloadManager.Request request = new DownloadManager.Request(downloadUri);
                request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE)
                        .setAllowedOverRoaming(false)
                        .setTitle(filename)
                        .setMimeType("image/jpeg")
                        .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                        .setDestinationInExternalPublicDir(Environment.DIRECTORY_PICTURES,
                                File.separator + DIR_NAME + File.separator + filename);

                dm.enqueue(request);

            }else{
                Toast toast = Toast.makeText(getContext(), "The image has been already downloaded", Toast.LENGTH_SHORT);
                toast.show();
            }
        });

        try{
            if(imageView!=null && imageExist.exists()) {
                imageView.setImageBitmap(loadImageBitmap(getContext(), filename));
            }
        }catch(Exception e){
            e.printStackTrace();
        }

        return root;
    }

    public void saveImage(Context context, Bitmap b, String imageName) {
        FileOutputStream foStream;
        try {
            foStream = context.openFileOutput(imageName, Context.MODE_PRIVATE);
            b.compress(Bitmap.CompressFormat.PNG, 100, foStream);
            foStream.close();
        } catch (Exception e) {
            Log.d("saveImage", "Exception 2, Something went wrong!");
            e.printStackTrace();
        }
    }

    public Bitmap loadImageBitmap(Context context, String imageName) {
        Bitmap bitmap = null;
        FileInputStream fiStream;
        try {
            fiStream    = context.openFileInput(imageName);
            bitmap      = BitmapFactory.decodeStream(fiStream);
            fiStream.close();
        } catch (Exception e) {
            Log.d("saveImage", "Exception 3, Something went wrong!");
            e.printStackTrace();
        }
        return bitmap;
    }

    private class AsyncDownloadImg extends AsyncTask<String, Void, Bitmap> {

        private Bitmap downloadImageBitmap(String sUrl) {
            Bitmap bitmap = null;
            try {
                InputStream inputStream = new URL(sUrl).openStream();   // Download Image from URL
                bitmap = BitmapFactory.decodeStream(inputStream);       // Decode Bitmap
                inputStream.close();
            } catch (Exception e) {
                Log.d("Download Image Error", "Exception 1, Something went wrong!");
                e.printStackTrace();
            }
            return bitmap;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            p=new ProgressDialog(getContext());
            p.setMessage("Please wait...It is downloading");
            p.setIndeterminate(false);
            p.setCancelable(false);
            p.show();
        }

        @Override
        protected Bitmap doInBackground(String... strings) {
            return downloadImageBitmap(strings[0]);
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            saveImage(getContext(), bitmap, filename);
            if(imageView!=null) {
                p.hide();
                imageView.setImageBitmap(loadImageBitmap(getContext(), filename));
                Toast toast = Toast.makeText(getContext(), "Download complete", Toast.LENGTH_SHORT);
                toast.show();
            }else {
                p.show();
            }
        }
    }
}






