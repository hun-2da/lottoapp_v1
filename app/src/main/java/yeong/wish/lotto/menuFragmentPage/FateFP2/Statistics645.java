package yeong.wish.lotto.menuFragmentPage.FateFP2;


import android.app.Fragment;
import android.content.res.AssetManager;

public class Statistics645 extends Fragment {
    int start,end;
    AssetManager asset;

    public Statistics645(int start, int end, AssetManager asset){
        this.start = start;
        this.end = end;
        this.asset = asset;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.fragment_statistics645, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.recycler);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        Num645Adapter adapter = new Num645Adapter();

        int getNumber[] = getFrequency();
        big_number(getNumber);

        for(int i=1; i<46; i++){

            Bitmap bitmap = get_String(i);
            adapter.addItem(new Number_lotto(i,getNumber[i-1],bitmap));
        }

        recyclerView.setAdapter(adapter);

        return view;
    }

    public int[] getFrequency(){
        int ballNumber[] = new int[45];
        String[] columns = {PRIZE_645_1, PRIZE_645_2, PRIZE_645_3, PRIZE_645_4, PRIZE_645_5, PRIZE_645_6};

        for (String column : columns) {
            for(int i = 1; i <= 45; i++) {
                String selectQuery = "SELECT COUNT(" + column + ") AS freq FROM " + TABLE645NAME + " WHERE "+ID_645+" BETWEEN " + start + " AND " + end + " AND " + column + " = " + i;
                Cursor cursor = Fate_Fragment.ldb.rawQuery(selectQuery, null);

                if (cursor.moveToFirst()) {
                    int get = cursor.getColumnIndex("freq");
                    if(get == -1) continue;
                    int freq = cursor.getInt(get);
                    ballNumber[i-1] += freq;
                }
                cursor.close();
            }
        }
        return ballNumber;
    }
    private Bitmap get_String(int i) {
        StringBuilder imageString = new StringBuilder("ball");
        imageString.append(i);
        imageString.append(".png");

        InputStream image_stream = null;
        try {
            image_stream = asset.open(imageString.toString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // InputStream으로부터 Bitmap을 생성합니다.
        Bitmap bitmap = BitmapFactory.decodeStream(image_stream);
        return bitmap;
    }
    private void big_number(int[] number){
        int max = number[0]; // 배열의 첫번째 값을 최대값으로 초기화합니다.

        for (int i = 1; i < number.length; i++) { // 배열의 두번째 요소부터 순회합니다.
            if (number[i] > max) {
                max = number[i];
            }
        }
        Num645Adapter.viewHolder.maxlength = max;
    }
}