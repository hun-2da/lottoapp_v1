package yeong.wish.lotto.menuFragmentPage.LuckFP;




public class Fragment645 extends Fragment {


    Button start_button;
    ImageView ball_imageView1,ball_imageView2,ball_imageView3,ball_imageView4,ball_imageView5,ball_imageView6;
    TextView logText;
    TextView textViewAnimation;


    Handler mainHandler = new Handler(Looper.getMainLooper());
    MediaPlayer mediaPlayer;
    MediaPlayer coint1_Player=null;
    MediaPlayer coint2_Player=null;
    MediaPlayer writing_Player = null;

    ScrollView scrollView;

    Random random = new Random();
    ArrayList<Integer> list = null;
    int lastNum = 0;
    int count[];

    ViewUpdate i_update = null;
    AssetManager assetManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.fragment_645, container, false);

        setting_Widget(view);

        start_button.setOnClickListener(new View.OnClickListener() {
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

        start_button.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if(list == null) return false;
                i_update.insert_645list_databse(list);
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



    /** 버튼을 눌러 동작할때 발생시킬 동작들
     * 1. 이미지로드
     * 2. */
    private void start_lotto(){
        ImageView imageView = null;
        list = new ArrayList<>();
        lastNum = 1;

        for (int i = 1; i <= 6; i++)
            i_update.list_645load(list,i);

        Collections.sort(list);
        for(int i = 1; i <= 6; i++){
            switch(i){
                case 1: imageView = ball_imageView1;break;
                case 2: imageView = ball_imageView2;break;
                case 3: imageView = ball_imageView3;break;
                case 4: imageView = ball_imageView4;break;
                case 5: imageView = ball_imageView5;break;
                case 6: imageView = ball_imageView6;break;
                //case 7: imageView = ball_imageView8;break;
            }
            i_update.setImageView645(list,imageView,i,lastNum);

        }
        queryForall();
        i_update.w_text(count);
    }
   /* private void serch_database_all(){
        //int count = 0;


       *//* for (int i = 0; i < list.size(); i++) {
            numbers.append(list.get(i));
            if (i < list.size() - 1) {
                numbers.append(",");
            }
        }*//*

        //String numberList = numbers.toString();
        //Log.e("확인요 ",numberList+"뭐가 문제지 ");
        Log.e("확인요2",lastNum+"이것도");
        //queryForall(numberList);
        queryForall();

    }*/

    /**뽑은 숫자와 데이터 베이스 대조*/
    private void queryForall(){
        if(list==null) return;
        count = new int[]{0, 0, 0,0,0};
        String joinedNumbers = TextUtils.join(",", list);

        String checkForMatchesQuery = "SELECT *, ("
                + "CASE WHEN " + PRIZE_645_1 + " IN (" + joinedNumbers + ") THEN 1 ELSE 0 END + "
                + "CASE WHEN " + PRIZE_645_2 + " IN (" + joinedNumbers + ") THEN 1 ELSE 0 END + "
                + "CASE WHEN " + PRIZE_645_3 + " IN (" + joinedNumbers + ") THEN 1 ELSE 0 END + "
                + "CASE WHEN " + PRIZE_645_4 + " IN (" + joinedNumbers + ") THEN 1 ELSE 0 END + "
                + "CASE WHEN " + PRIZE_645_5 + " IN (" + joinedNumbers + ") THEN 1 ELSE 0 END + "
                + "CASE WHEN " + PRIZE_645_6 + " IN (" + joinedNumbers + ") THEN 1 ELSE 0 END"
                + ") AS MatchCount FROM " + TABLE645NAME
                + " WHERE ("
                + "CASE WHEN " + PRIZE_645_1 + " IN (" + joinedNumbers + ") THEN 1 ELSE 0 END + "
                + "CASE WHEN " + PRIZE_645_2 + " IN (" + joinedNumbers + ") THEN 1 ELSE 0 END + "
                + "CASE WHEN " + PRIZE_645_3 + " IN (" + joinedNumbers + ") THEN 1 ELSE 0 END + "
                + "CASE WHEN " + PRIZE_645_4 + " IN (" + joinedNumbers + ") THEN 1 ELSE 0 END + "
                + "CASE WHEN " + PRIZE_645_5 + " IN (" + joinedNumbers + ") THEN 1 ELSE 0 END + "
                + "CASE WHEN " + PRIZE_645_6 + " IN (" + joinedNumbers + ") THEN 1 ELSE 0 END"
                + ") >= 3";

        Cursor cursor = Luck_Fragment.ldb.rawQuery(checkForMatchesQuery, null);

        while(cursor.moveToNext()) {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(ID_645));
                int matchCount = cursor.getInt(cursor.getColumnIndexOrThrow("MatchCount"));
                int bonusMatch = cursor.getInt(cursor.getColumnIndexOrThrow(BONUS_645));

            if(matchCount == 3){
                //update_log(id+"회차 + 4등",4);
                count[4]++;
            }else if(matchCount == 4){
                //update_log(id+"회차 + 4등",5);
                count[3]++;
            }else if(matchCount == 5) {
                if(bonusM(bonusMatch)) {
                    i_update.update_log(id+"회차 + 2등",2);
                    count[1]++;
                } else {
                    i_update.update_log(id+"회차 + 3등",3);
                    count[2]++;
                }
            } else if(matchCount == 6) {
                i_update.update_log(id+"회차 + 1등",1);
                count[0]++;
            }




        }
// 커서를 닫습니다.
        cursor.close();
    }
    private boolean bonusM(int bonus){
        for(int i=0;i<list.size();i++){
            if(list.get(i) == bonus) return true;
        }

        return false;
    }
    /*private void queryForall(String numberList){
        Log.e("확인요 ",numberList+"뭐가 문제지 ");
        count = new int[]{0, 0, 0,0,0};

        String integratedQuery = "SELECT " + ID_645 + ", " + BONUS_645 + " FROM " + TABLE645NAME
                + " WHERE (" + PRIZE_645_1 + " IN (" + numberList + ") "
                + " OR " + PRIZE_645_2 + " IN (" + numberList + ") "
                + " OR " + PRIZE_645_3 + " IN (" + numberList + ") "
                + " OR " + PRIZE_645_4 + " IN (" + numberList + ") "
                + " OR " + PRIZE_645_5 + " IN (" + numberList + ") "
                + " OR " + PRIZE_645_6 + " IN (" + numberList + ")) "
                + " GROUP BY " + ID_645
                + " HAVING COUNT(DISTINCT CASE "
                + " WHEN " + PRIZE_645_1 + " IN (" + numberList + ") THEN " + PRIZE_645_1
                + " WHEN " + PRIZE_645_2 + " IN (" + numberList + ") THEN " + PRIZE_645_2
                + " WHEN " + PRIZE_645_3 + " IN (" + numberList + ") THEN " + PRIZE_645_3
                + " WHEN " + PRIZE_645_4 + " IN (" + numberList + ") THEN " + PRIZE_645_4
                + " WHEN " + PRIZE_645_5 + " IN (" + numberList + ") THEN " + PRIZE_645_5
                + " WHEN " + PRIZE_645_6 + " IN (" + numberList + ") THEN " + PRIZE_645_6
                + " END) >= 3";

        // SQLiteDatabase 객체와 쿼리를 사용하여 검색을 수행
        String[] prizeColumns = {PRIZE_645_1, PRIZE_645_2, PRIZE_645_3, PRIZE_645_4, PRIZE_645_5, PRIZE_645_6};
        Cursor cursor = Fate_Fragment.ldb.rawQuery(integratedQuery, null);
        Log.e("cursor", "cursor.getCount() = " + cursor.getCount());
        while(cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndexOrThrow(ID_645));
            int bonusNumber = cursor.getInt(cursor.getColumnIndexOrThrow(BONUS_645));
            int matchCount = 0;

            Log.e("확인용",id+"  "+bonusNumber+"  ");
            if(id!=-1){
                // 각 PRIZE_645_x 열에 대해 일치하는지 확인
                for(String prizeColumn : prizeColumns) {
                    int prizeNumber = cursor.getInt(cursor.getColumnIndexOrThrow(prizeColumn));
                    if(list.contains(prizeNumber)) {
                        matchCount++;
                        Log.e("확인용2",matchCount+"  "+prizeNumber+"  ");
                    }
                }
                if(matchCount == 3){
                    update_log(id+"회차 + 4등",4);
                    count[3]++;
                }else if(matchCount == 4){
                    update_log(id+"회차 + 4등",5);
                    count[4]++;
                }else if(matchCount == 5) {
                    if(list.contains(bonusNumber)) {
                        // 보너스 숫자를 포함하여 총 6개 숫자가 일치하는 경우
                        update_log(id+"회차 + 2등",2);
                        count[1]++;
                    } else {
                        update_log(id+"회차 + 3등",3);
                        count[2]++;
                    }
                } else if(matchCount == 6) {
                    update_log(id+"회차 + 1등",1);
                    count[0]++;
                }
            }
        }
        cursor.close();
    }*/

    private void setting_Widget(ViewGroup view) {


        ball_imageView1 = view.findViewById(R.id.image645_1);
        ball_imageView2 = view.findViewById(R.id.image645_2);
        ball_imageView3 = view.findViewById(R.id.image645_3);
        ball_imageView4 = view.findViewById(R.id.image645_4);
        ball_imageView5 = view.findViewById(R.id.image645_5);
        ball_imageView6 = view.findViewById(R.id.image645_6);
        //ball_imageView8 = view.findViewById(R.id.image645_7);

        logText = view.findViewById(R.id.log_textView);

        textViewAnimation = view.findViewById(R.id.textView);


        assetManager = getActivity().getAssets();

        i_update = new ViewUpdate(logText,assetManager,textViewAnimation,true);

        scrollView = view.findViewById(R.id.scroll_text);

        mediaPlayer = MediaPlayer.create(getActivity(), R.raw.key_sound );
        coint1_Player = MediaPlayer.create(getActivity(), R.raw.coin1 );
        coint2_Player = MediaPlayer.create(getActivity(), R.raw.coin2 );
        writing_Player = MediaPlayer.create(getActivity(),R.raw.writingsound);

        i_update.setSound(coint1_Player,coint2_Player);

        start_button = view.findViewById(R.id.start_645);
    }
}