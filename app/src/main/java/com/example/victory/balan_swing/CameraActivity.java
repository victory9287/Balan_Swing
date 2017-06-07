package com.example.victory.balan_swing;

import android.content.ContentValues;
import android.content.Intent;
import android.hardware.Camera;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import java.io.File;

public class CameraActivity extends AppCompatActivity implements SurfaceHolder.Callback{

    private static String EXTERNAL_STORAGE_PATH = "";
    private static String RECORDED_FILE = "video_recorded";
    private static int fileIndex = 0;
    private static String filename = "";

    MediaPlayer player;
    MediaRecorder recorder;

    // 카메라 상태를 저장하고 있는 객체
    private Camera camera = null;

    SurfaceView surfaceView;
    SurfaceHolder holder;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        // 외장메모리가 있는지 확인한다.
        // Environment.getExternalStorageState() 를 통해서 현재 외장메모리를 상태를 알수있다.
        String state = Environment.getExternalStorageState();
        // Environment.MEDIA_MOUNTED 외장메모리가 마운트 flog
        if (!state.equals(Environment.MEDIA_MOUNTED)) {
            Toast.makeText(getApplicationContext(), "외장 메모리가 마운트 되지않았습니다.", Toast.LENGTH_LONG).show();
        } else {
            EXTERNAL_STORAGE_PATH = Environment.getExternalStorageDirectory().getAbsolutePath();
        }

        // SurfaceView 클래스 객체를 이용해서 카메라에 받은 녹화하고 재생하는데 쓰일것이다.
        surfaceView = (SurfaceView)findViewById(R.id.surface);
        // SurfaceView 클래스를 컨트롤하기위한 SurfaceHolder 생성
        holder = surfaceView.getHolder();
        holder.addCallback(this);
        // 버퍼없음
        holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        
        ImageButton recordBtn = (ImageButton) findViewById(R.id.recordBtn);
        final ImageButton recordStopBtn = (ImageButton) findViewById(R.id.recordStopBtn);
        final ImageButton saveBtn = (ImageButton) findViewById(R.id.saveBtn);
        recordStopBtn.setEnabled(false);
        saveBtn.setEnabled(false);

        // 녹화 시작 버튼
        recordBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {

                    if (camera != null){
                        camera.stopPreview();
                        camera.release();
                        camera = null;
                        Log.e("check","#3 Release Camera  _---> OK!!!");
                    }

                    // 녹화 시작을 위해  MediaRecorder 객체 recorder를 생성한다.
                    if (recorder == null) {
                        recorder = new MediaRecorder();
                    } else {
                        recorder.stop();
                        recorder.release();
                    }
                    // 오디오와영상 입력 형식 설정
                    recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                    recorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);
                    recorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);

                    // 오디오와영상 인코더 설정
                    recorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);
                    recorder.setVideoEncoder(MediaRecorder.VideoEncoder.DEFAULT);

                    // 저장될 파일 지정
                    filename = createFilename();
                    File file = new File(filename);
                    if (file.exists()) {
                        deleteFile(filename);
                        Log.d("check", "delete");
                    }
                    recorder.setOutputFile(filename);

                    // 녹화도중에 녹화화면을 뷰에다가 출력하게 해주는 설정
                    recorder.setVideoSize(1280, 720);
                    recorder.setPreviewDisplay(holder.getSurface());

                    // 녹화 준비,시작
                    recorder.prepare();
                    recorder.start();

                    recordStopBtn.setEnabled(true);

                } catch (Exception ex) {
                    ex.printStackTrace();
                    recorder.release();
                    recorder = null;
                }
            }
        });
        recordStopBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (recorder == null)
                    return;
                // 녹화 중지
                recorder.stop();

                // 영상 재생에 필요한 메모리를 해제한다.
                recorder.release();
                recorder = null;

                if ( camera == null ) {
                    Log.e("CAM TEST","Preview Restart!!!!!");
                    // 프리뷰 다시 설정
                    try {
                        // 카메라 객체를 만든다
                        camera = Camera.open();
                        camera.setPreviewDisplay(holder);
                    } catch (Exception e) {
                        // TODO: handle exception
                    }
                    // 프리뷰 재시작
                    camera.startPreview();
                }

                ContentValues values = new ContentValues(10);

                values.put(MediaStore.MediaColumns.TITLE, "RecordedVideo");
                values.put(MediaStore.Audio.Media.ALBUM, "Video Album");
                values.put(MediaStore.Audio.Media.ARTIST, "Mike");
                values.put(MediaStore.Audio.Media.DISPLAY_NAME, "Recorded Video");
                values.put(MediaStore.MediaColumns.DATE_ADDED, System.currentTimeMillis() / 1000);
                values.put(MediaStore.MediaColumns.MIME_TYPE, "video/mp4");
                values.put(MediaStore.Audio.Media.DATA, filename);

                Uri videoUri = getContentResolver().insert(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, values);
                if (videoUri == null) {
                    Log.d("SampleVideoRecorder", "Video insert failed.");
                    return;
                }

                sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, videoUri));

                saveBtn.setEnabled(true);
            }
        });
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CameraActivity.this, PersonalActivity.class);
                intent.putExtra("path",filename);
                setResult(RESULT_OK, intent);
                finish();
            }
        });

    }

    public String createFilename() {
        fileIndex++;

        String newFilename = "";
        if (EXTERNAL_STORAGE_PATH == null || EXTERNAL_STORAGE_PATH.equals("")) {
            // 내장 메모리를 사용합니다.
            newFilename = RECORDED_FILE + fileIndex + ".mp4";
        } else {
            // 외장 메모리를 사용합니다.
            newFilename = EXTERNAL_STORAGE_PATH + "/" + RECORDED_FILE + fileIndex + ".mp4";
        }

        return newFilename;
    }


    // 액티비티가 onPause 상태일때 녹화,재생에 필요한 모든 객체들의 메모리를 해제한다
    public void onPause() {
        super.onPause();
        Log.d("SampleVideoRecorder", "pause");
        if (camera != null) {
            camera.release();
            camera = null;
        }
        if (recorder != null) {
            recorder.release();
            recorder = null;
        }

        if (player != null) {
            player.release();
            player = null;
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        try {
            // 카메라 객체를 만든다
            camera = Camera.open();
            camera.setPreviewDisplay(holder);
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        if (camera !=null){
            // 프리뷰 다시 시작
            camera.startPreview();
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        // 프리뷰를 멈춘다
        if (camera != null){
            camera.stopPreview();
            // 카메라 객체 초기화
            camera = null;
        }
    }
}
