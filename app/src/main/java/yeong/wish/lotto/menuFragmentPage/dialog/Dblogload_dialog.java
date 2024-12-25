package yeong.wish.lotto.menuFragmentPage.dialog;


import android.app.Activity;
import android.app.Dialog;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class Dblogload_dialog extends Dialog {
    String list720;
    Activity activity;
    int id;
    String message;
    ArrayList<Integer> list;

    //---------------------------------------------------------------------------------------645ui
    TextView textView;
    ImageView imageView1,imageView2,imageView3,imageView4,imageView5,imageView6;
    //---------------------------------------------------------------------------------------720ui
    TextView textView2;
    ImageView imageView720_1,imageView720_2,imageView720_3,imageView720_4,imageView720_5,imageView720_6,imageView720_7;

    //---------------------------------------------------------------------------------------------------------------------------

    boolean game;

    final boolean game645 = true, game720 = false;

    MediaPlayer mediaPlayer;


    /**645용 생성자*/
    public Dblogload_dialog(@NonNull Activity activity, int id, String message, ArrayList<Integer> list, MediaPlayer mediaPlayer, boolean game) {
        super(activity);
        this.activity = activity;
        this.id = id;
        this.message = message;
        this.list = list;
        this.game = game;
        this.mediaPlayer = mediaPlayer;
    }

    /**720용 생성자*/
    public Dblogload_dialog(@NonNull Activity activity, int id, String message, String list720, MediaPlayer mediaPlayer, boolean game) {
        super(activity);
        this.activity = activity;
        this.id = id;
        this.message = message;
        this.list720 = list720;
        this.game = game;
        this.mediaPlayer = mediaPlayer;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (mediaPlayer!=null && !mediaPlayer.isPlaying()) {
            mediaPlayer.start();
        }

        if(game == game645)
            set645gameUI();
        else if(game == game720)
            set720gameUI();

        Button button = findViewById(R.id.dialog_x_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    private void set645gameUI(){
        setContentView(R.layout.dbload645_dialog);


        textView = findViewById(R.id.messageTextView);

        textView.setText(id+"회 "+message);

        imageView1 = findViewById(R.id.log645_1);
        imageView2 = findViewById(R.id.log645_2);
        imageView3 = findViewById(R.id.log645_3);
        imageView4 = findViewById(R.id.log645_4);
        imageView5 = findViewById(R.id.log645_5);
        imageView6 = findViewById(R.id.log645_6);

        set645Image();
    }
    private void set645Image(){
        ImageView imageView = null;

        for(int i = 1; i <= 6; i++){
            switch(i){
                case 1: imageView = imageView1;break;
                case 2: imageView = imageView2;break;
                case 3: imageView = imageView3;break;
                case 4: imageView = imageView4;break;
                case 5: imageView = imageView5;break;
                case 6: imageView = imageView6;break;
                //case 7: imageView = ball_imageView8;break;
            }
            Fate_Fragment.i_update.setImageView645(list,imageView,i,0);
        }
    }
    private void set720gameUI() {
        setContentView(R.layout.dbload720_dialog);

        textView2 = findViewById(R.id.messageTextView720);

        textView2.setText(id+"회 "+message);

        imageView720_1 = findViewById(R.id.log720_1);
        imageView720_2 = findViewById(R.id.log720_2);
        imageView720_3 = findViewById(R.id.log720_3);
        imageView720_4 = findViewById(R.id.log720_4);
        imageView720_5 = findViewById(R.id.log720_5);
        imageView720_6 = findViewById(R.id.log720_6);
        imageView720_7 = findViewById(R.id.log720_7);

        set720Image();
    }

    private void set720Image() {

        ImageView imageView = null;
        StringBuilder stringBuilder = new StringBuilder(list720);

        for (int i = 1; i < 8; i++) {
            switch(i){
                case 1: imageView = imageView720_1;break;
                case 2: imageView = imageView720_2;break;
                case 3: imageView = imageView720_3;break;
                case 4: imageView = imageView720_4;break;
                case 5: imageView = imageView720_5;break;
                case 6: imageView = imageView720_6;break;
                case 7: imageView = imageView720_7;break;
            }
            Fate_Fragment.i_update.setImage720(stringBuilder,imageView,i);

        }
    }
}
