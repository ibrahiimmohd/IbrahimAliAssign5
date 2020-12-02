package ibrahim.ali.s301022172.ui.IbrahimFragment;

import android.Manifest;
import android.app.Activity;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaScannerConnection;
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

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;

import ibrahim.ali.s301022172.R;

public class IbrDown extends Fragment {

    URL ImageUrl;
    InputStream is;
    Bitmap bmImg;
    ImageView imageView;
    ProgressDialog p;
    String DIR_NAME, filename;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.ibr_down, container, false);
        final TextView textView = root.findViewById(R.id.Ibrahim);

        Button button= root.findViewById(R.id.asyncTask);
        imageView = root.findViewById(R.id.image);

        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},1);

        DIR_NAME = "Assignment5 Images";

        filename = "filename.jpg";

        button.setOnClickListener(v -> {

            File imageExist =
                    new File(Environment
                            .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
                            .getAbsolutePath() + File.separator + DIR_NAME + File.separator + filename);

            if(!imageExist.exists()){

                AsyncTaskExample asyncTask = new AsyncTaskExample();
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

            }

            String photoPath = Environment.DIRECTORY_PICTURES + File.separator + DIR_NAME + File.separator + filename;

            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.RGB_565;
            Bitmap bitmap = BitmapFactory.decodeFile(photoPath, options);
            imageView.setImageBitmap(bitmap);

        });

        return root;
    }

    private class AsyncTaskExample extends AsyncTask<String, String, Bitmap> {

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
            try {

                ImageUrl = new URL(strings[0]);
                HttpURLConnection conn = (HttpURLConnection) ImageUrl
                        .openConnection();
                conn.setDoInput(true);
                conn.connect();
                is = conn.getInputStream();

                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inPreferredConfig = Bitmap.Config.RGB_565;
                bmImg = BitmapFactory.decodeStream(is, null, options);

            } catch (IOException e) {
                e.printStackTrace();
            }

            return bmImg;
        }

//        @Override
//        protected void onPostExecute(Bitmap bitmap) {
//            super.onPostExecute(bitmap);
//            if(imageView!=null) {
//                p.hide();
//                imageView.setImageBitmap(bitmap);
//            }else {
//                p.show();
//            }
//        }
    }
}






