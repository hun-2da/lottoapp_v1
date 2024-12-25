package yeong.wish.lotto.menuFragmentPage.FateFP;


import android.content.ContentValues;

public class InsertLog {
    public static void lotto645(int round,int[] number,int bonus){
        ContentValues values = new ContentValues();
        values.put(LogDagtabaseHelper.ID_645, round); // ID_645 열에 들어갈 값

        int size = number.length;
        for(int i=0;i<size;i++) {
            values.put(LogDagtabaseHelper.PRIZE_645_+(i+1), number[i]);
        }

        values.put(LogDagtabaseHelper.BONUS_645, bonus); // BONUS_645 열에 들어갈 값

        // 데이터베이스 행 삽입
        long newRowId = Fate_Fragment.ldb.insert(LogDagtabaseHelper.TABLE645NAME, null, values);
        if(newRowId == -1) {
            //Log.e("문제","데이터베이스 insert 실패");
        }
    }
    public static void lotto720(int round,String number[],String bonus){
        ContentValues values = new ContentValues();
        values.put(LogDagtabaseHelper.ID_720, round);


        int size = number.length;
        for(int i=0;i<size;i++) {
            values.put(LogDagtabaseHelper.PRIZE_720_+(i+1), number[i]);
        }

        values.put(LogDagtabaseHelper.BONUS_720, bonus);

        // 데이터베이스 행 삽입
        long newRowId = Fate_Fragment.ldb.insert(LogDagtabaseHelper.TABLE720NAME, null, values);
        if(newRowId == -1) {
            //Log.e("문제","데이터베이스 insert 실패");
        }
    }


}
