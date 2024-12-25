package yeong.wish.lotto.menuFragmentPage.LuckFP;


import android.app.Fragment;
import android.media.MediaPlayer;

public class Fragment720plus extends Fragment {

    ImageView ball720_1,ball720_2,ball720_3,ball720_4,ball720_5,ball720_6,ball720_7;
    //ArrayList<Integer> list = null;
    StringBuilder list;
    int count[];
    ViewUpdate i_update = null;
    TextView logText;
    AssetManager assetManager;// AssetManager를 얻습니다.
    MediaPlayer mediaPlayer;
    MediaPlayer coint1_Player=null;
    MediaPlayer coint2_Player=null;
    MediaPlayer writing_Player = null;

    ScrollView scrollView;
    TextView aniText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.fragment_fragment720plus, container, false);

        setting_Widget(view);

        Button startButton = view.findViewById(R.id.start720_button);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start_lotto();

                try {
                    TimeUnit.MILLISECONDS.sleep(200);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        i_update.textAni();
                    }
                }).start();
                if (mediaPlayer!=null&&!mediaPlayer.isPlaying()) {
                    mediaPlayer.start();
                }
                scrollView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        scrollView.fullScroll(View.FOCUS_DOWN);
                    }
                }, 100);

            }
        });

        startButton.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if(list == null) return false;
                i_update.insert_720list_databse(list);
                if (writing_Player!=null&&!writing_Player.isPlaying()) {
                    writing_Player.start();
                }
                Snackbar.make(view, "저장 완료!", Snackbar.LENGTH_SHORT).show();
                return true;
            }
        });




        return view;
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
        if(coint1_Player!=null && coint2_Player!=null){
            coint1_Player.release();
            coint2_Player.release();

            coint1_Player = null;
            coint2_Player = null;
        }
        if(writing_Player !=null){
            writing_Player.release();
            writing_Player = null;
        }
    }
    private void start_lotto(){
        ImageView imageView = null;
        list = new StringBuilder("");

        for(int i=1; i<=7; i++)
            i_update.list_720load(list,i);


        for (int i = 1; i < 8; i++) {
            switch(i){
                case 1: imageView = ball720_1;break;
                case 2: imageView = ball720_2;break;
                case 3: imageView = ball720_3;break;
                case 4: imageView = ball720_4;break;
                case 5: imageView = ball720_5;break;
                case 6: imageView = ball720_6;break;
                case 7: imageView = ball720_7;break;
            }
            i_update.setImage720(list,imageView,i);

        }
        // 해당위치에서 해결 필요

        queryForall();
        i_update.w_text(count);

    }
    private void queryForall(){
        if(list==null) return;
        count = new int[]{0, 0, 0, 0, 0, 0, 0,0};
        HashSet<Integer> seenIds = new HashSet<>();

        String[] PRIZE_COLUMNS = {
                LogDagtabaseHelper.PRIZE_720_1,
                LogDagtabaseHelper.PRIZE_720_2,
                LogDagtabaseHelper.PRIZE_720_3,
                LogDagtabaseHelper.PRIZE_720_4,
                LogDagtabaseHelper.PRIZE_720_5,

                LogDagtabaseHelper.PRIZE_720_6,
                LogDagtabaseHelper.PRIZE_720_7,

        };



        String BonusvalueToCheck = list.substring(1);
        String query = "SELECT " + LogDagtabaseHelper.ID_720 + " FROM " + LogDagtabaseHelper.TABLE720NAME + " WHERE " + LogDagtabaseHelper.BONUS_720 + " = '" + BonusvalueToCheck + "'";
        Cursor cursor = Luck_Fragment.ldb.rawQuery(query, null);
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndexOrThrow(LogDagtabaseHelper.ID_720));
            seenIds.add(id);
            count[7]++;
            i_update.update_log(id + "회차 " + "bonus! ", 7);
        }
        cursor.close();



        for (int i = 0; i < PRIZE_COLUMNS.length; i++) {
            String column = PRIZE_COLUMNS[i];
            String valueToCheck = i == 0 ? list.toString() : list.substring(i);

            if (column.equals(LogDagtabaseHelper.BONUS_720)) {
                valueToCheck = list.substring(1);
            }

            // 각 컬럼에 대한 쿼리 실행
            query = "SELECT " + LogDagtabaseHelper.ID_720 + " FROM " + LogDagtabaseHelper.TABLE720NAME + " WHERE " + column + " = '" + valueToCheck + "'";
            Cursor cursor2 = Luck_Fragment.ldb.rawQuery(query, null);

            // 결과 처리
            while (cursor2.moveToNext()) {
                int id = cursor2.getInt(cursor2.getColumnIndexOrThrow(LogDagtabaseHelper.ID_720));

                if (seenIds.contains(id)) {
                    continue;
                }
                seenIds.add(id); // 새 ID를 추적
                count[i]++; // 해당 컬럼의 카운트 증가


                if (0 <= i && i <= 2) {
                    i_update.update_log(id + "회차 " + (i + 1) + "등", i + 1);
                }
               /* switch(i){
                    case 0: i_update.update_log(id+"회차 "+ (i+1) +"등",i+1); break;
                    case 1: i_update.update_log(id+"회차 "+ (i+1) +"등",i+1); break;
                    case 2: i_update.update_log(id+"회차 "+ (i+1) +"등",i+1); break;
                    case 3:
                    case 4: break;
                    case 5: i_update.update_log(id+"회차 "+ "Bonus",i+1);
                }*/

            }

            cursor2.close();
        }


    }

    private void setting_Widget(ViewGroup view){

        assetManager = getActivity().getAssets();
        logText = view.findViewById(R.id.log_textView2);
        aniText = view.findViewById(R.id.textView_Ani2);

        i_update = new ViewUpdate(logText,assetManager,aniText,false);

        mediaPlayer = MediaPlayer.create(getActivity(), R.raw.key_sound );
        coint1_Player = MediaPlayer.create(getActivity(), R.raw.coin1 );
        coint2_Player = MediaPlayer.create(getActivity(), R.raw.coin2 );
        writing_Player = MediaPlayer.create(getActivity(),R.raw.writingsound);

        i_update.setSound(coint1_Player,coint2_Player);

        scrollView = view.findViewById(R.id.scrolltextView2);

        ball720_1 = view.findViewById(R.id.ball720_imageView1);
        ball720_2 = view.findViewById(R.id.ball720_b_imageView1);
        ball720_3 = view.findViewById(R.id.ball720_b_imageView2);
        ball720_4 = view.findViewById(R.id.ball720_b_imageView3);
        ball720_5 = view.findViewById(R.id.ball720_b_imageView4);
        ball720_6 = view.findViewById(R.id.ball720_b_imageView5);
        ball720_7 = view.findViewById(R.id.ball720_b_imageView6);
    }
}