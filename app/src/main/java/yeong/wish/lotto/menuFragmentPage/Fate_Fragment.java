package yeong.wish.lotto.menuFragmentPage;


import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.OnUserEarnedRewardListener;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;


public class Fate_Fragment extends Fragment {

    public LogDagtabaseHelper localdb;
    public static SQLiteDatabase ldb;

    Boolean lotto645 = true;
    public static final int d645_year = 2002, d645_month = 12, d645_day = 7;

    Boolean lotto720 = false;
    public static final int d720_year = 2020, d720_month = 5, d720_day = 7;

    //---------------현재 최신 회차 확인용 전역변수------------------
    public static int get645 = 0 ;
    public static int get720 = 0;
    //------------------------------------------------------------
    final int TRUE = 0; final int ERROR = -1;
    Boolean switch645, switch720;

    InternetAccess access;

    //-------------------------db확인--------------------------------
    TextView textview645,textView720;
    Spinner spinner645,spinner720;

    //---------------------------------------------------------------

    ImageView image645_1,image645_2,image645_3,image645_4,image645_5,image645_6,image645_7;
    ImageView image720_1,image720_2,image720_3,image720_4,image720_5,image720_6,image720_7;

    EditText edit645,edit720;

    LinearLayout bonusLinearLayout;
    ImageView image720_bonus1,image720_bonus2,image720_bonus3,image720_bonus4,image720_bonus5,image720_bonus6;

    //-----------------------------------------------------------------

    public static ViewUpdate i_update;
    AssetManager assetManager;

    StringBuilder p_Number = null; // 이미지를 바꾸려면 전역변수로 지정
    
    Button button645,button720;

    ConstraintLayout ButtonEventLayout;

    Button CloseButton;

    Button w_view;
    FrameLayout close_layout;
    //-------------------------------------------------------------------------------광고

    private RewardedAd rewardedAd;

    AdView topad;
    private final String TAG = "feFragment";


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup viewGroup = (ViewGroup)inflater.inflate(R.layout.fragment_fate_, container, false);

        setViewUpdate(viewGroup);

        image720_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                    ImageView imageView = (ImageView)v;

                    if(bonusLinearLayout.getVisibility() == View.INVISIBLE) {
                        bonusLinearLayout.setVisibility(View.VISIBLE);
                        imageView.setImageResource(R.drawable.ball_icon);

                    } else {
                        try {
                        bonusLinearLayout.setVisibility(View.INVISIBLE);
                        String s = "ball720W_"+p_Number.charAt(0)+".png";
                        InputStream image_stream = assetManager.open(s);
                        Bitmap bitmap = BitmapFactory.decodeStream(image_stream);
                        imageView.setImageBitmap(bitmap);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        } catch (NullPointerException e1){
                            Toast.makeText(getActivity(),"데이터가 저장이 되어 있지 않습니다.",Toast.LENGTH_LONG).show();
                        }
                    }


            }
        });

        switch645 = switch720 = false;

        access = new InternetAccess(getContext());

        Boolean b = checkDatabase();
        localdb = new LogDagtabaseHelper(getContext());
        ldb = localdb.getWritableDatabase();

        if(!b) {
            make1tolast_645();
            make1tolast_720();

        }else{
            lastupdateCheck();
            if(switch645) make1tolast_645();
            if(switch720) make1tolast_720();
        }

        setup645Spinner();
        setup720Spinner();

        if(!b){
            try {
                Thread.sleep(777);
            } catch (InterruptedException e) {

            }
            Intent i = getActivity().getBaseContext().getPackageManager()
                    .getLaunchIntentForPackage( getActivity().getBaseContext().getPackageName());
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
            /*Intent intent = new Intent(getActivity().getApplicationContext(), MainActivity.class);
            int pendingIntentId = 123456;
            PendingIntent pendingIntent = PendingIntent.getActivity(getActivity().getApplicationContext(), pendingIntentId, intent, PendingIntent.FLAG_CANCEL_CURRENT | PendingIntent.FLAG_IMMUTABLE);
            AlarmManager mgr = (AlarmManager)getActivity().getApplicationContext().getSystemService(Context.ALARM_SERVICE);
            mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 100, pendingIntent);
            System.exit(0);*/
        }

        return viewGroup;
    }
    public static int get_date(int yeaer, int month, int day){
        long weeks = 0;
        /*if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            LocalDate start = LocalDate.of(yeaer, month, day);
            LocalDate end = null;  // 오늘 날짜
            end = LocalDate.now();
            weeks = ChronoUnit.WEEKS.between(start, end);
        }else{*/
            Calendar start = Calendar.getInstance();
            start.set(yeaer, month-1, day);
            Calendar end = Calendar.getInstance();
            long days = end.getTimeInMillis() - start.getTimeInMillis();
            days = TimeUnit.MILLISECONDS.toDays(days);
            weeks = days / 7;
        //}

        //Toast.makeText(getActivity().getApplicationContext(),"로또 회차는 " + (weeks + 1) + "회차입니다.",Toast.LENGTH_SHORT).show();
        return (int)weeks+1;

    }
    private boolean checkDatabase(){
        File dbFile = getActivity().getApplicationContext().getDatabasePath(LogDagtabaseHelper.DATABASENAME);
        boolean isExist = dbFile.exists();

        return isExist;

    }
    private void lastupdateCheck(){

        int last645 = check(TABLE645NAME,ID_645,get645);

        if(last645==ERROR) switch645 = true;
        else if(last645!=TRUE){
            last645++;
            access.makeRequest("https://dhlottery.co.kr/gameResult.do?method=allWinPrint&gubun=byWin&nowPage=&drwNoStart="+last645+"&drwNoEnd="+get645,lotto645);
        }

        int last720 = check(TABLE720NAME,ID_720,get720);

        if(last720==ERROR) switch720 = true;
        else if(last720!=TRUE){
            last720++;
            access.makeRequest("https://www.dhlottery.co.kr/gameResult.do?method=win720Print&Round=190&drwNoStart="+last720+"&drwNoEnd="+get720,lotto720);
        }

    }
    /**true인 경우 db의 정보가 최신 정보임을 나타냄. 최신이 아닐경우 lastID~getInt까지 db에 저장 필요*/
    private int check(String table,String id,int getInt){
        String selectLastIdQuery = "SELECT " + id
                + " FROM " + table
                + " ORDER BY " + id + " DESC LIMIT 1";
        Cursor get_cursor = ldb.rawQuery(selectLastIdQuery, null);

        if (get_cursor != null && get_cursor.moveToFirst()) {
            int lastId = get_cursor.getInt(0);
            get_cursor.close();

            if (lastId == getInt) return TRUE;
            else return lastId;

        }return ERROR;
    }
    private void make1tolast_645(){
        access.makeRequest("https://dhlottery.co.kr/gameResult.do?method=allWinPrint&gubun=byWin&nowPage=&drwNoStart=1&drwNoEnd="+get645,lotto645);
    }
    private void make1tolast_720(){
        access.makeRequest("https://www.dhlottery.co.kr/gameResult.do?method=win720Print&Round=190&drwNoStart=1&drwNoEnd="+get720,lotto720);
    }
    private void setup645Spinner() {
        ArrayList<Integer> ids = new ArrayList<>();
        // 데이터베이스에서 ID 값 목록을 가져와서 ids 리스트에 추가
        Cursor idCursor = ldb.rawQuery("SELECT " + ID_645 + " FROM " + TABLE645NAME, null);

        while (idCursor.moveToNext()) {
            ids.add(idCursor.getInt(0));
        }
        idCursor.close();

        ArrayAdapter<Integer> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, ids);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner645.setAdapter(adapter);

        spinner645.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int selectedId = (int) parent.getItemAtPosition(position);
                displayData645(selectedId);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void displayData645(int id) {
        ImageView imageView = null;
        int lastNum = 0;
        ArrayList<Integer> list = new ArrayList<>();

        Cursor cursor = ldb.rawQuery("SELECT * FROM " + TABLE645NAME + " WHERE " + ID_645 + " = ?", new String[]{String.valueOf(id)});
        if (cursor != null && cursor.moveToFirst()) {
            StringBuilder data = new StringBuilder();

            // ID 컬럼 확인 및 추가
            int idIndex = cursor.getColumnIndex(ID_645);
            if (idIndex != -1) {
                data.append("ID: ").append(cursor.getInt(idIndex));
            }

            for(int i=1; i<7; i++){
                String Property = PRIZE_645_ + i;
                int prizeIndex = cursor.getColumnIndex(Property);

                if (prizeIndex != -1)
                    list.add(cursor.getInt(prizeIndex));
            }

            int bonus = cursor.getColumnIndex(BONUS_645);
            if (bonus != -1) {
                lastNum = cursor.getInt(bonus);
            }


            textview645.setText(data);
            cursor.close();


            for(int i = 1; i <= 7; i++){
                switch(i){
                    case 1: imageView = image645_1;break;
                    case 2: imageView = image645_2;break;
                    case 3: imageView = image645_3;break;
                    case 4: imageView = image645_4;break;
                    case 5: imageView = image645_5;break;
                    case 6: imageView = image645_6;break;
                    case 7: imageView = image645_7;break;
                }
                i_update.setImageView645(list,imageView,i,lastNum);
            }

        }
    }

    private void setup720Spinner(){
        ArrayList<Integer> ids = new ArrayList<>();
        // 데이터베이스에서 ID 값 목록을 가져와서 ids 리스트에 추가
        Cursor idCursor = ldb.rawQuery("SELECT " + ID_720 + " FROM " + TABLE720NAME, null);

        while (idCursor.moveToNext()) {
            ids.add(idCursor.getInt(0));
        }
        idCursor.close();

        ArrayAdapter<Integer> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, ids);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner720.setAdapter(adapter);

        spinner720.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int selectedId = (int) parent.getItemAtPosition(position);
                displayData720(selectedId);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }
    private void displayData720(int id) {
        ImageView imageView=null,imageView2 = null;

        ArrayList<Integer> list = new ArrayList<>();

        Cursor cursor = ldb.rawQuery("SELECT * FROM " + TABLE720NAME + " WHERE " + ID_720 + " = ?", new String[]{String.valueOf(id)});
        if (cursor != null && cursor.moveToFirst()) {
            StringBuilder data = new StringBuilder();


            StringBuilder bonus = null;

            // ID 컬럼 확인 및 추가
            int idIndex = cursor.getColumnIndex(ID_720);
            if (idIndex != -1) {
                data.append("ID: ").append(cursor.getInt(idIndex));
            }


            int NumberIndex = cursor.getColumnIndex(PRIZE_720_1);
            if(NumberIndex != -1){
                p_Number = new StringBuilder((cursor.getString(NumberIndex)));
            }


            int bonusNumber = cursor.getColumnIndex(BONUS_720);
            if (bonusNumber != -1) {
                bonus = new StringBuilder(cursor.getString(bonusNumber)).insert(0,"0");
            }


            textView720.setText(data);
            cursor.close();


            for (int i = 1; i < 8; i++) {
                switch(i){
                    case 1: imageView = image720_1; break;
                    case 2: imageView = image720_2; imageView2 = image720_bonus1;break;
                    case 3: imageView = image720_3; imageView2 = image720_bonus2;break;
                    case 4: imageView = image720_4; imageView2 = image720_bonus3;break;
                    case 5: imageView = image720_5; imageView2 = image720_bonus4;break;
                    case 6: imageView = image720_6; imageView2 = image720_bonus5;break;
                    case 7: imageView = image720_7; imageView2 = image720_bonus6;break;
                }
                i_update.setImage720(p_Number,imageView,i);
                i_update.setImage720(bonus,imageView2,i);

            }



        }
    }





    private void setViewUpdate(ViewGroup view){
        textview645 = view.findViewById(R.id.TextView645);
        textView720 = view.findViewById(R.id.TextView720);

        spinner645 = view.findViewById(R.id.spinner);
        spinner720 = view.findViewById(R.id.spinner2);


        assetManager = getActivity().getAssets();

        get645 = get_date(d645_year,d645_month,d645_day);
        get720 = get_date(d720_year,d720_month,d720_day);

        i_update = new ViewUpdate(assetManager);

        button645 = view.findViewById(R.id.button645);
        button720 = view.findViewById(R.id.button720);

        edit645 = view.findViewById(R.id.editText645);
        edit720 = view.findViewById(R.id.editText720);

        image645_1 = view.findViewById(R.id.image645_1);
        image645_2 = view.findViewById(R.id.image645_2);
        image645_3 = view.findViewById(R.id.image645_3);
        image645_4 = view.findViewById(R.id.image645_4);
        image645_5 = view.findViewById(R.id.image645_5);
        image645_6 = view.findViewById(R.id.image645_6);
        image645_7 = view.findViewById(R.id.image645_7);

        image720_1 = view.findViewById(R.id.ball720_imageView1);

        image720_2 = view.findViewById(R.id.ball720_b_imageView1);
        image720_3 = view.findViewById(R.id.ball720_b_imageView2);
        image720_4 = view.findViewById(R.id.ball720_b_imageView3);
        image720_5 = view.findViewById(R.id.ball720_b_imageView4);
        image720_6 = view.findViewById(R.id.ball720_b_imageView5);
        image720_7 = view.findViewById(R.id.ball720_b_imageView6);

        bonusLinearLayout = view.findViewById(R.id.bonus720);
        image720_bonus1 = view.findViewById(R.id.image720_bonus1);
        image720_bonus2 = view.findViewById(R.id.image720_bonus2);
        image720_bonus3 = view.findViewById(R.id.image720_bonus3);
        image720_bonus4 = view.findViewById(R.id.image720_bonus4);
        image720_bonus5 = view.findViewById(R.id.image720_bonus5);
        image720_bonus6 = view.findViewById(R.id.image720_bonus6);

        ButtonEventLayout = view.findViewById(R.id.ButtonEventLayout);

        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();

        close_layout = view.findViewById(R.id.close_layout);

        CloseButton = view.findViewById(R.id.Exit_button);

        w_view = view.findViewById(R.id.button4);
        w_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (rewardedAd != null) {
                    run_ad();
                } else {
                    setad();
                    if (rewardedAd != null) {
                        run_ad();
                    }

                }
            }
        });

        CloseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ButtonEventLayout.setVisibility(View.INVISIBLE);
                close_layout.setVisibility(View.INVISIBLE);
                topad.setVisibility(View.GONE);


            }
        });


        edit645.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // 입력된 텍스트: s.toString()

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // 텍스트가 변경되기 전에 호출됩니다.
            }

            @Override
            public void afterTextChanged(Editable s) {
                int position = 1;
                if(s==null) return;
                String editNum = s.toString();

                ArrayAdapter<Integer> adapter = (ArrayAdapter<Integer>) spinner645.getAdapter();
                if(editNum.equals("")) return;
                try {
                    position = adapter.getPosition(Integer.parseInt(s.toString()));
                }catch(NumberFormatException e){
                    position = -1;
                }
                if(position!=-1) {
                    spinner645.setSelection(position);
                }

            }
        });

        edit720.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                int position = 1;
                if(s==null) return;
                String editNum = s.toString();

                ArrayAdapter<Integer> adapter = (ArrayAdapter<Integer>) spinner720.getAdapter();
                if(editNum.equals("")) return;
                try {
                    position = adapter.getPosition(Integer.parseInt(s.toString()));
                }catch(NumberFormatException e){
                    position = -1;
                }
                if(position!=-1) {
                    spinner720.setSelection(position);
                }
            }
        });
        
        button645.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = "";
                if (edit645 != null && edit645.getText() != null) {
                    s = edit645.getText().toString();
                }else return;
                int start = 1;
                int end = 1;
                if(s.equals("")) {
                    end = get645;
                }else{
                    try {
                        start = Integer.parseInt(s);
                    }catch(NumberFormatException e){
                        return;
                    }
                    if(spinner645.getSelectedItem()==null) return;
                    end = Integer.parseInt(spinner645.getSelectedItem().toString());
                }


                if(start<=end){
                    Statistics645 statistics645 = new Statistics645(start,end,assetManager);
                    ButtonEventLayout.setVisibility(View.VISIBLE);

                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.getEvent,statistics645);
                    try {
                        fragmentTransaction.commit();
                    }catch(IllegalStateException e){
                        Log.e("확인",e.toString()+" ");
                    }

                    close_layout.setVisibility(View.VISIBLE);
                    topad.setVisibility(View.VISIBLE);


                }

            }
        });

        button720.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = "";
                if (edit720 != null && edit720.getText() != null) {
                    s = edit720.getText().toString();
                }else return;

                int start = 1;
                int end = 1;
                if(s.equals("")) {
                    end = get720;
                }else{
                    try {
                        start = Integer.parseInt(s);
                    }catch (NumberFormatException e){
                        return;
                    }
                    if(spinner720.getSelectedItem()==null) return;
                    end = Integer.parseInt(spinner720.getSelectedItem().toString());
                }

                if(start<=end){
                    Statistics720 statistics720 = new Statistics720(start,end,assetManager);
                    ButtonEventLayout.setVisibility(View.VISIBLE);

                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.getEvent,statistics720);
                    try {
                        fragmentTransaction.commit();
                    }catch(IllegalStateException e){
                    }


                    close_layout.setVisibility(View.VISIBLE);
                    topad.setVisibility(View.VISIBLE);
                }
            }
        });

        topad = view.findViewById(R.id.adView_statistics_top);


        MobileAds.initialize(getActivity(), new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        loadBannerAd();
    }

    private void loadBannerAd() {
        AdRequest adRequest_top = new AdRequest.Builder().build();
        topad.loadAd(adRequest_top);

        topad.setAdListener(new AdListener() {
            @Override
            public void onAdClicked() {
                // Code to be executed when the user clicks on an ad.
            }

            @Override
            public void onAdClosed() {
                // Code to be executed when the user is about to return
                // to the app after tapping on an ad.
            }

            @Override
            public void onAdFailedToLoad(LoadAdError adError) {
                // Code to be executed when an ad request fails.
            }

            @Override
            public void onAdImpression() {
                // Code to be executed when an impression is recorded
                // for an ad.
            }

            @Override
            public void onAdLoaded() {
                // Code to be executed when an ad finishes loading.
            }

            @Override
            public void onAdOpened() {
                // Code to be executed when an ad opens an overlay that
                // covers the screen.
            }
        });
        AdRequest adRequest_bottom = new AdRequest.Builder().build();

    }
    /**클릭시 발생 광고*/
    private void setad() {

        AdRequest adRequest = new AdRequest.Builder().build();
        RewardedAd.load(getActivity(), "ca-app-pub-3252837628484304/1064266666", adRequest, new RewardedAdLoadCallback() {
            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                // 오류 처리
                Log.d(TAG, loadAdError.toString());
                rewardedAd = null;
            }

            @Override
            public void onAdLoaded(@NonNull RewardedAd ad) {
                rewardedAd = ad;

                // 여기에 콜백 설정을 추가합니다
                rewardedAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                    @Override
                    public void onAdClicked() {
                        // Called when a click is recorded for an ad.
                    }

                    @Override
                    public void onAdDismissedFullScreenContent() {
                        // Called when ad is dismissed.
                        // Set the ad reference to null so you don't show the ad a second time.
                        rewardedAd = null;
                    }

                    @Override
                    public void onAdFailedToShowFullScreenContent(AdError adError) {
                        rewardedAd = null;
                        setad(); // 광고 닫힘 후 새 광고 로드
                    }

                    @Override
                    public void onAdImpression() {
                        // Called when an impression is recorded for an ad.
                    }

                    @Override
                    public void onAdShowedFullScreenContent() {
                        // Called when ad is shown.
                    }
                });
                run_ad();
            }
        });
    }
    private void run_ad(){
        if (rewardedAd != null) {
            Activity activityContext = getActivity();
            rewardedAd.show(activityContext, new OnUserEarnedRewardListener() {
                @Override
                public void onUserEarnedReward(@NonNull RewardItem rewardItem) {
                    // Handle the reward.
                    int rewardAmount = rewardItem.getAmount();
                    String rewardType = rewardItem.getType();
                }
            });
        }
    }

}