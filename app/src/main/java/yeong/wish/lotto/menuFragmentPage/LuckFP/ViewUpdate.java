package yeong.wish.lotto.menuFragmentPage.LuckFP;



public class ViewUpdate {
    Random random = new Random();
    TextView logText; AssetManager assetManager;  TextView textViewAnimation;

    Handler mainHandler = new Handler(Looper.getMainLooper());
    StringBuilder modifiedMessage;
    final String message = "하늘은 어차피 당신의 편 언제나 당신의 행운과 행복을 바랍니다";

    //final String message = "स्वर्ग सदैव तव पक्षे अस्ति, सदैव तव सौभाग्यं च सुखं च इच्छति।";

    public static String boolString = "";
    int time = 0;
    MediaPlayer coint1_Player=null,coint2_Player=null;

    public ViewUpdate(AssetManager assetManager){
        this.assetManager = assetManager;
    }

    public ViewUpdate(TextView logText, AssetManager assetManager,TextView textViewAnimation,Boolean b){
        this.logText = logText;
        this.assetManager = assetManager;
        this.textViewAnimation = textViewAnimation;


        if(!b){
            modifiedMessage = new StringBuilder();
            for (char ch : message.toCharArray()) {
                modifiedMessage.append(ch).append("\n");
            }
            time = 75;
        }else {
            modifiedMessage = new StringBuilder(message);
            time = 150;
        }
    }
    public void setSound(MediaPlayer coint1_Player,MediaPlayer coint2_Player){
        this.coint1_Player = coint1_Player;
        this.coint2_Player = coint2_Player;
    }


    public void list_645load(ArrayList list,int num){
        //Boolean bonus = false;
        int list_size=0;


        int randomInt = random.nextInt(45)+1;


        /*if(lastNum==7) {
            lastNum = randomInt;
            bonus = true;
            list_size = list.size();
            Log.e("true","실행도미");
        } else {*/
            list.add(randomInt);
            list_size = list.size()-1;
        //}

        logText.append(" "+randomInt+" ");


        for(int i=0; i<list_size; i++){
            /*if(bonus && (int)list.get(i) == lastNum){
                randomInt = random.nextInt(45)+1;
                lastNum = randomInt;
                i = -1;
                logText.append(" "+randomInt+" ");
            }*/
            if(/*!bonus && */i != list_size && list.get(i) == list.get(list_size)){
                randomInt = random.nextInt(45)+1;

                list.set(list_size,randomInt);
                i = -1;
                logText.append(" "+randomInt+" ");
            }

        }



    }


    public void setImageView645(ArrayList list,ImageView imageView,int i,int lastNumber){
        try {
                StringBuilder imageString = new StringBuilder("ball");

                if(i!=7)
                    imageString.append(list.get(i-1));
                else
                    imageString.append(lastNumber);

                imageString.append(".png");

                // 이미지 파일의 InputStream을 얻습니다.
                InputStream image_stream = assetManager.open(imageString.toString());

                // InputStream으로부터 Bitmap을 생성합니다.
                Bitmap bitmap = BitmapFactory.decodeStream(image_stream);

                // ImageView를 얻고, Bitmap을 설정합니다.
                imageView.setImageBitmap(bitmap);

        }catch (IOException e){

        }
    }

    public void insert_645list_databse(ArrayList<Integer> list) {

        ContentValues values = new ContentValues();


        if(get645!=0) {
            values.put(USERDATE_ID_645, get645+1);

        }else{
            get645 = get_date(d645_year,d645_month,d645_day);
            values.put(USERDATE_ID_645, get645+1);
        }
        values.put(USERCHOICE_645_1, list.get(0));
        values.put(USERCHOICE_645_2, list.get(1));
        values.put(USERCHOICE_645_3, list.get(2));
        values.put(USERCHOICE_645_4, list.get(3));
        values.put(USERCHOICE_645_5, list.get(4));
        values.put(USERCHOICE_645_6, list.get(5));


        Luck_Fragment.ldb.insert(LOGTABLE_645, null, values);
    }


    public void list_720load(StringBuilder list,int number){
        int randomIndex = 0;

        randomIndex = (number==1)?5:10;


        int randomInt = random.nextInt(randomIndex);
        if(randomIndex==5) randomInt++;

        list.append(randomInt);

        logText.append(" "+randomInt+" ");


    }


    public void setImage720(StringBuilder list,ImageView imageView,int number){

        try {
            StringBuilder imageString = new StringBuilder("ball720");
            switch (number) {
                case 1:
                    imageString.append("W_");
                    break;
                case 2:
                    imageString.append("R_");
                    break;
                case 3:
                    imageString.append("O_");
                    break;
                case 4:
                    imageString.append("y_");
                    break;
                case 5:
                    imageString.append("B_");
                    break;
                case 6:
                    imageString.append("P_");
                    break;
                case 7:
                    imageString.append("G_");
                    break;
            }

            imageString.append(list.charAt(number - 1));



            imageString.append(".png");

            // 이미지 파일의 InputStream을 얻습니다.
            InputStream image_stream = assetManager.open(imageString.toString());

            // InputStream으로부터 Bitmap을 생성합니다.
            Bitmap bitmap = BitmapFactory.decodeStream(image_stream);

            // ImageView를 얻고, Bitmap을 설정합니다.
            imageView.setImageBitmap(bitmap);
        }catch(IOException e){

        }

    }
    public void insert_720list_databse(StringBuilder list) {

        ContentValues values = new ContentValues();

        if(get720!=0) {
            values.put(USERDATE_ID_720, get720+1);

        }else{
            get720 = get_date(d720_year,d720_month,d720_day);
            values.put(USERDATE_ID_720, get720+1);
        }

        for(int i=1; i<=7;i++) {
            values.put(USERCHOICE_720_+i, list.substring(i-1));
        }

        Luck_Fragment.ldb.insert(LOGTABLE_720, null, values);
    }
    public void w_text(int[] i){
        logText.append("\nTIP.버튼을 꾹 누르면 저장이 가능하며, 운명 페이지에서 당첨 확인이 가능합니다");
        String num = "";

        if(i.length==5)
            num = "\n결과 : \n1등 "+i[0]+"번, 2등 "+i[1]+"번, 3등 "+i[2]+"번, 4등 "+i[3]+"번, 5등 "+i[4]+"번\n의 결과가 존재합니다.\n";
        else
            num = "\n결과 : \n1등 "+i[0]+"번 2등 "+i[1]+"번,bonus "+i[7]+", 3등 "+i[2]+"번, 4등 "+i[3]+"번, 5등 "+i[4]+"번, 6등 "+i[5]+ "번, 7등 "+i[6]+ "번\n의 결과가 존재합니다.\n";

        SpannableStringBuilder ssb = new SpannableStringBuilder(num);

        // 전체 텍스트에 대해 큰 글자 크기를 적용합니다.
        int start = 0;
        int end = num.length();
        ssb.setSpan(new AbsoluteSizeSpan(20, true), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        // 전체 텍스트에 대해 빨간색을 적용합니다.
        ssb.setSpan(new ForegroundColorSpan(Color.BLACK), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);


        String text = boolString+"!\n";
        SpannableStringBuilder ssb2 = new SpannableStringBuilder(text);

        // 전체 텍스트에 대해 큰 글자 크기를 적용합니다.
        start = 0;
        end = text.length();
        ssb2.setSpan(new AbsoluteSizeSpan(25, true), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        // 전체 텍스트에 대해 빨간색을 적용합니다.
        ssb2.setSpan(new ForegroundColorSpan(Color.YELLOW), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        logText.append(ssb);
        logText.append(ssb2);
        logText.append("\n");

    }
    public void textAni(){
        if(modifiedMessage == null)return;
        String message = modifiedMessage.toString();
        for (int i = 0; i < message.length(); i++) {
            final int finalI = i;
            mainHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    textViewAnimation.setText(message.substring(0, finalI + 1));

                }
            }, time * i);  // 문자를 입력하는 속도를 조절합니다. 숫자가 클수록 느려집니다.
        }
    }
    public void update_log(String message,int i){

        String text = "\n\n"+message+"\n";
        SpannableStringBuilder ssb = new SpannableStringBuilder(text);

        // 전체 텍스트에 대해 큰 글자 크기를 적용합니다.
        int start = 0;
        int end = text.length();
        ssb.setSpan(new AbsoluteSizeSpan(20, true), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        // 전체 텍스트에 대해 빨간색을 적용합니다.
        int color = 0;
        switch(i) {
            case 1:
                color = Color.MAGENTA;
                if (coint1_Player!=null&&!coint1_Player.isPlaying()) {
                    coint1_Player.start();
                }
                break;
            case 2:
            case 3:
            case 7:
                color = Color.CYAN;
                if (coint2_Player!=null&&!coint2_Player.isPlaying()) {
                    coint2_Player.start();
                }
                break;

        }
        ssb.setSpan(new ForegroundColorSpan(color), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        logText.append(ssb);

    }

}
