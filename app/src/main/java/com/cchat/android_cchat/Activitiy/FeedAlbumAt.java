package com.cchat.android_cchat.Activitiy;

import android.app.Activity;
import android.content.Context;
import android.content.CursorLoader;
import android.database.Cursor;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.cchat.android_cchat.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

// AlbumAt.java
public class FeedAlbumAt extends Activity {

    GridView gvAlbum;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.atest);

// 그리드뷰
        gvAlbum = (GridView)findViewById(R.id.gvAlbum);
// 그리드뷰 어댑터 세팅(어댑터뷰 안에서 미디어스토어를 이용한 갤러리 리스트를 가져옴)
        final ImageAdapter ia = new ImageAdapter(this);
        gvAlbum.setAdapter(ia);
// 클릭시 어댑터 뷰의 callImageViewer 호출해서 전체이미지를 보여줌
        gvAlbum.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            public void onItemClick(AdapterView parent, View v, int position, long id){
                ia.callImageViewer(position);
            }
        });
    }

    /**==========================================
     *              Adapter class
     * ==========================================*/
    public class ImageAdapter extends BaseAdapter
    {
        private ArrayList<String> thumbsIDList;
        private ArrayList<String> thumbsDataList;
        private Context mContext;
        public ImageAdapter(Context c)
        {
            mContext = c;
            this.thumbsDataList = new ArrayList<String>();
            this.thumbsIDList = new ArrayList<String>();

// 미디어스토어를 이용한 이미지 가져오기
            getThumbInfo(thumbsIDList, thumbsDataList);
        }

        // 이미지 클릭시 상세뷰
        public final void callImageViewer(int selectedIndex){
//            Intent i = new Intent(mContext, ImagePopup.class);
//            String imgPath = getImageInfo(thumbsIDList.get(selectedIndex));
//            i.putExtra("filename", imgPath);
//            startActivityForResult(i, 1);
        }

        public boolean deleteSelected(int sIndex){
            return true;
        }

        public int getCount() {
            return thumbsIDList.size();
        }

        public Object getItem(int position) {
            return position;
        }

        public long getItemId(int position) {
            return position;
        }

        public View getView(final int position, View convertView, ViewGroup parent) {

// 화면의 가로사이즈를 받아와서 3으로 나눈다.(화면에 꽉찬 3열 정사각형으로 정렬하기 위해 사이즈 계산)
            final int imgSize = (getScreenSize(FeedAlbumAt.this).x / 3);

            ImageView imageView;
            if (convertView == null){
                imageView = new ImageView(mContext);
                imageView.setLayoutParams(new GridView.LayoutParams(imgSize, imgSize));
                imageView.setAdjustViewBounds(false);
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            }else{
                imageView = (ImageView) convertView;
            }

// 미디어스토어 ID를 이용해서 URI를 가져온다.
            Uri imageUri = Uri.withAppendedPath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, String.valueOf(thumbsIDList.get(position)));
// 피카소 라이브러리를 이용해서 이미지를 로드한다. (자동으로 캐쉬를 처리해줌)
            Picasso.with(mContext).load(imageUri).resize(imgSize, imgSize).centerInside().into(imageView);
            return imageView;
        }

        // 미디어스토어를 이용한 이미지 가져오기
        private void getThumbInfo(ArrayList<String> thumbsIDs, ArrayList<String> thumbsDatas){
            String[] proj = {
                    MediaStore.Images.Thumbnails._ID,
                    MediaStore.Images.Thumbnails.DATA,
            };

            CursorLoader cursorLoader = new CursorLoader(
                    mContext,  MediaStore.Images.Media.EXTERNAL_CONTENT_URI, proj, null, null, null);

            Cursor imageCursor = cursorLoader.loadInBackground();


            if (imageCursor != null && imageCursor.moveToFirst()){
                String thumbsID;
                String thumbsData;

                int thumbsIDCol = imageCursor.getColumnIndex(MediaStore.Images.Thumbnails._ID);
                int thumbsDataCol = imageCursor.getColumnIndex(MediaStore.Images.Thumbnails.DATA);
                do {
                    thumbsID = imageCursor.getString(thumbsIDCol);
                    thumbsData = imageCursor.getString(thumbsDataCol);
                    thumbsIDs.add(thumbsID);
                    thumbsDatas.add(thumbsData);
                }
                while (imageCursor.moveToNext());

                imageCursor.close();
            }
            return;
        }

        // 상세 이미지 path 가져오기
        private String getImageInfo(String thumbID){
            String imageDataPath = null;
            String[] proj = {MediaStore.Images.Media._ID,
                    MediaStore.Images.Media.DATA};

            CursorLoader cursorLoader = new CursorLoader(
                    mContext,  MediaStore.Images.Media.EXTERNAL_CONTENT_URI, proj, "_ID='"+ thumbID +"'", null, null);

            Cursor imageCursor = cursorLoader.loadInBackground();

            if (imageCursor != null && imageCursor.moveToFirst()){
                if (imageCursor.getCount() > 0){
                    int imgData = imageCursor.getColumnIndex(MediaStore.Images.Media.DATA);
                    imageDataPath = imageCursor.getString(imgData);
                }
            }
            imageCursor.close();
            return imageDataPath;
        }
    }

    public Point getScreenSize(Activity activity) {
        Display display = activity.getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return  size;
    }
}