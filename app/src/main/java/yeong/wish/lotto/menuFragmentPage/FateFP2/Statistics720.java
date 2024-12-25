package yeong.wish.lotto.menuFragmentPage.FateFP2;


import android.content.res.AssetManager;

public class Statistics720 extends Fragment {
    int start,end;
    AssetManager asset;
    ArrayList<ArrayList<Integer>> prizes;
    public Statistics720(int start, int end, AssetManager asset) {
        this.start = start;
        this.end = end;
        this.asset = asset;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.fragment_statistics720, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.recycler720);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        Num720Adapter adapter = new Num720Adapter();

        getFrequency();
        int[][] num720 = getT();
        big_number(num720);

        int size = 0;
        int id = 0;
        int setNum = 0;
        for(int i=0; i < num720.length; i++){
            size = (i==0)?6:10;
            for(int j=0; j<size;j++){

                if(i==0&&j==0)
                    j++;

                Bitmap bitmap = get_String(i,j);

                if(i==0)
                    setNum = num720[i][j-1];
                else
                    setNum = num720[i][j];
                adapter.addItem(new Number_lotto(id++,setNum,bitmap));
            }

        }

        recyclerView.setAdapter(adapter);

        return view;
    }
    private Bitmap get_String(int i,int j){
        StringBuilder imageString = new StringBuilder("ball720");
        switch (i) {
            case 0:
                imageString.append("W_");
                break;
            case 1:
                imageString.append("R_");
                break;
            case 2:
                imageString.append("O_");
                break;
            case 3:
                imageString.append("y_");
                break;
            case 4:
                imageString.append("B_");
                break;
            case 5:
                imageString.append("P_");
                break;
            case 6:
                imageString.append("G_");
                break;
        }
        imageString.append(j);
        imageString.append(".png");

        InputStream image_stream = null;
        try {
            image_stream = asset.open(imageString.toString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return BitmapFactory.decodeStream(image_stream);

    }

    private void big_number(int[][] num720) {
        int size = 0;
        int big_num = num720[1][0];
        for(int i=0; i < num720.length; i++){
            size = (i==0)?5:9;
            for(int j=0; j<size;j++){
                if (i!=0&&num720[i][j] > big_num) {
                    big_num = num720[i][j];
                }
            }
        }
        Num720Adapter.viewHolder720.maxlength = big_num;
    }

    private void getFrequency() {
        prizes = new ArrayList<>();

        String query = "SELECT " + PRIZE_720_1 + " FROM " + TABLE720NAME
                + " WHERE " + ID_720 + " BETWEEN ? AND ?";
        Cursor cursor = Fate_Fragment.ldb.rawQuery(query, new String[]{String.valueOf(start), String.valueOf(end)});

        if (cursor.moveToFirst()) {
            do {
                ArrayList<Integer> list = new ArrayList<>();

                int get_number = cursor.getColumnIndex(PRIZE_720_1);
                StringBuilder prize = new StringBuilder(cursor.getString(get_number));

                for(int i=0;i<7;i++){
                    int num = prize.charAt(i) - '0';
                    list.add(num);
                }
                prizes.add(list);
            } while (cursor.moveToNext());
        }

        cursor.close();
    }
    private int[][] getT(){
        int count = 0;
        int numArray[][] = {
                {0,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,0}
        };
        int size = prizes.size();
        for(int choice_size = 0; choice_size < size;choice_size++) {
            for (int i = 0; i < 7; i++) {
                //Log.e("확인 필요 ",count++ + "확인 ");
                    int choice = prizes.get(choice_size).get(i);
                    if(i==0) choice--;
                    switch (choice) {
                        case 0:
                            numArray[i][0]++;
                            break;
                        case 1:
                            numArray[i][1]++;
                            break;
                        case 2:
                            numArray[i][2]++;
                            break;
                        case 3:
                            numArray[i][3]++;
                            break;
                        case 4:
                            numArray[i][4]++;
                            break;
                        case 5:
                            numArray[i][5]++;
                            break;
                        case 6:
                            numArray[i][6]++;
                            break;
                        case 7:
                            numArray[i][7]++;
                            break;
                        case 8:
                            numArray[i][8]++;
                            break;
                        case 9:
                            numArray[i][9]++;
                            break;
                    }
            }
        }
        return numArray;
    }
}