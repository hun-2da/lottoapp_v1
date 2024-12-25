package yeong.wish.lotto.menuFragmentPage;


import android.app.Activity;
import android.app.Fragment;
import android.content.DialogInterface;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.util.ArrayList;
import java.util.HashSet;

public class Pioneering_Fragment extends Fragment {
    WebView webView;
    int get_645ID = 0, get_720ID = 0;
    ArrayList<Dblogload_dialog> dialog_list;

    Activity activity;

    MediaPlayer mediaPlayer1, mediaPlayer2;


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup viewGroup = (ViewGroup)inflater.inflate(R.layout.fragment_pioneering_, container, false);

        activity = getActivity();
        mediaPlayer1 = MediaPlayer.create(activity, R.raw.success645 );
        mediaPlayer2 = MediaPlayer.create(activity, R.raw.successs720 );

        webload(viewGroup);

        dialog_list = new ArrayList<>();

        serchdb();
        logRemove();


        return viewGroup;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mediaPlayer1 != null) {
            mediaPlayer1.release();
            mediaPlayer1 = null;
        }
        if (mediaPlayer2 != null) {
            mediaPlayer2.release();
            mediaPlayer2 = null;
        }
    }

    private void logRemove() {
        int threshold645 = get_645ID - 2;
        String deleteQuery645 = "DELETE FROM " + LOGTABLE_645 + " WHERE " + USERDATE_ID_645 + " < " + threshold645;
        Fate_Fragment.ldb.execSQL(deleteQuery645);


        int threshold720 = get_720ID - 2;
        String deleteQuery720 = "DELETE FROM " + LOGTABLE_720 + " WHERE " + USERDATE_ID_720 + " < " + threshold720;
        Fate_Fragment.ldb.execSQL(deleteQuery720);
    }

    /**web(당첨자 글)load를 위한 webview작성*/
    private void webload(ViewGroup viewGroup) {
        webView = viewGroup.findViewById(R.id.happy_View);

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl("https://m.dhlottery.co.kr/gameResult.do?method=highWin");
    }
    private void serchdb(){
        ArrayList<Integer> list645 = re_table645();
        String[] list720 = re_table720();

        if(get_645ID == Fate_Fragment.get645){
            // 정상 645 유저로그에서 쿼리 및 다이얼로그 실행전 추가
            query_user645log(list645);
        }

        if(get_720ID == Fate_Fragment.get720){
            // 정상 720 우저로그에서 쿼리
            query_user720log(list720);
        }
        
        // show dialog 하는 거랑 , 머신러닝
        showDialogSequentially(0);



    }
    void showDialogSequentially(int index) {


        if (index < dialog_list.size()) {
            Dblogload_dialog dialog = dialog_list.get(index);

            dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialogInterface) {
                    // 다음 Dialog 표시
                    showDialogSequentially(index + 1);
                }
            });
            dialog.show();
        }
    }
    private ArrayList<Integer> re_table645(){
        int colum;


        String selectQuery = "SELECT * FROM " + TABLE645NAME + " ORDER BY " + ID_645 + " DESC LIMIT 1";

        // 쿼리 실행
        Cursor cursor = Fate_Fragment.ldb.rawQuery(selectQuery, null);

        ArrayList<Integer> resultList = new ArrayList<>();

        // 쿼리 결과 처리
        if (cursor.moveToFirst()) {
            colum = -1;

            for(int i=0; i<8; i++) {
                if(i==0){
                    colum = cursor.getColumnIndex(ID_645);
                    get_645ID = cursor.getInt(colum);
                }else if(i == 7){
                    colum = cursor.getColumnIndex(BONUS_645);
                    resultList.add(cursor.getInt(colum));
                }
                else {
                    colum = cursor.getColumnIndex(PRIZE_645_ + i);
                    resultList.add(cursor.getInt(colum));
                }
            }

        }

        // 커서 및 데이터베이스 닫기
        cursor.close();

        return resultList;
    }

    private String[] re_table720(){
        String selectQuery = "SELECT * FROM " + TABLE720NAME + " ORDER BY " + ID_720 + " DESC LIMIT 1";

        // 쿼리 실행
        Cursor cursor = Fate_Fragment.ldb.rawQuery(selectQuery, null);

        String resultList[] = new String[2];


        // 쿼리 결과 처리
        if (cursor.moveToFirst()) {
            // PRIZE_720_1부터 BONUS_720까지의 값을 ArrayList에 추가

            int xolumn0  = cursor.getColumnIndex(ID_720);
            get_720ID = cursor.getInt(xolumn0);

            int xolumn1  = cursor.getColumnIndex(PRIZE_720_1);
            resultList[0] = cursor.getString(xolumn1);

            int xolumn2  = cursor.getColumnIndex(BONUS_720);
            resultList[1] =cursor.getString(xolumn2);
        }

        cursor.close();

        return resultList;
    }
    private void query_user645log(ArrayList<Integer> list) {
        String joinedNumbers = TextUtils.join(",", list);

        String checkForMatchesQuery = "SELECT *, ("
                + "CASE WHEN " + USERCHOICE_645_1 + " IN (" + joinedNumbers + ") THEN 1 ELSE 0 END + "
                + "CASE WHEN " + USERCHOICE_645_2 + " IN (" + joinedNumbers + ") THEN 1 ELSE 0 END + "
                + "CASE WHEN " + USERCHOICE_645_3 + " IN (" + joinedNumbers + ") THEN 1 ELSE 0 END + "
                + "CASE WHEN " + USERCHOICE_645_4 + " IN (" + joinedNumbers + ") THEN 1 ELSE 0 END + "
                + "CASE WHEN " + USERCHOICE_645_5 + " IN (" + joinedNumbers + ") THEN 1 ELSE 0 END + "
                + "CASE WHEN " + USERCHOICE_645_6 + " IN (" + joinedNumbers + ") THEN 1 ELSE 0 END"
                + ") AS MatchCount FROM " + LOGTABLE_645
                + " WHERE ("
                + "CASE WHEN " + USERCHOICE_645_1 + " IN (" + joinedNumbers + ") THEN 1 ELSE 0 END + "
                + "CASE WHEN " + USERCHOICE_645_2 + " IN (" + joinedNumbers + ") THEN 1 ELSE 0 END + "
                + "CASE WHEN " + USERCHOICE_645_3 + " IN (" + joinedNumbers + ") THEN 1 ELSE 0 END + "
                + "CASE WHEN " + USERCHOICE_645_4 + " IN (" + joinedNumbers + ") THEN 1 ELSE 0 END + "
                + "CASE WHEN " + USERCHOICE_645_5 + " IN (" + joinedNumbers + ") THEN 1 ELSE 0 END + "
                + "CASE WHEN " + USERCHOICE_645_6 + " IN (" + joinedNumbers + ") THEN 1 ELSE 0 END"
                + ") >= 3 AND " + USERDATE_ID_720 + " = " + get_645ID;

        Cursor cursor = Fate_Fragment.ldb.rawQuery(checkForMatchesQuery, null);

        while (cursor.moveToNext()) {
            ArrayList<Integer> list_log645 = new ArrayList<>();
            int id = cursor.getInt(cursor.getColumnIndexOrThrow(USERDATE_ID_645));
            int matchCount = cursor.getInt(cursor.getColumnIndexOrThrow("MatchCount"));
            int bonusMatch = list.get(6);

            for(int i=1; i<7; i++) {
                list_log645.add(cursor.getInt(cursor.getColumnIndexOrThrow(USERCHOICE_645_ + i)));
            }


            if (matchCount == 3) {
                dialog_list.add(new Dblogload_dialog(activity,id,"5등 당첨!!",list_log645,mediaPlayer1,true));
            } else if (matchCount == 4) {
                dialog_list.add(new Dblogload_dialog(activity,id,"4등 당첨!!",list_log645,mediaPlayer1,true));
            } else if (matchCount == 5) {
                if (bonusM(bonusMatch,list_log645)) {
                    dialog_list.add(new Dblogload_dialog(activity,id,"2등 당첨!!",list_log645,mediaPlayer1,true));
                } else {
                    dialog_list.add(new Dblogload_dialog(activity,id,"3등 당첨!!",list_log645,mediaPlayer1,true));
                }
            } else if (matchCount == 6) {
                dialog_list.add(new Dblogload_dialog(activity,id,"1등 당첨!!",list_log645,mediaPlayer1,true));
            }
        }
        cursor.close();
    }
    private boolean bonusM(int bonus,ArrayList<Integer> list){
        for(int i=0;i<list.size();i++){
            if(list.get(i) == bonus) return true;
        }

        return false;
    }

    private void query_user720log(String[] list) {
        HashSet<Integer> seenIds = new HashSet<>();
        String prize[] = new String[7];
        for(int i=0;i< prize.length; i++){
            prize[i] = list[0].substring(i);
        }



        String b_valueToCheck = list[1];
        String b_query = "SELECT * FROM " + LOGTABLE_720 + " WHERE " + USERCHOICE_720_2 + "='" + b_valueToCheck + "' AND " + USERDATE_ID_720 + " = " + get_720ID;
        Cursor b_cursor = Fate_Fragment.ldb.rawQuery(b_query, null);

        // 결과 처리
        while (b_cursor.moveToNext()) {
            String string_list = b_cursor.getString(b_cursor.getColumnIndexOrThrow(USERCHOICE_720_1));
            int id = b_cursor.getInt(b_cursor.getColumnIndexOrThrow(USERDATE_ID_720));
            int id_auto = b_cursor.getInt(b_cursor.getColumnIndexOrThrow(USERID_720));

            if (seenIds.contains(id_auto)) {
                continue;
            }
            seenIds.add(id_auto);
            dialog_list.add(new Dblogload_dialog(activity,id, "bonus 등",string_list,mediaPlayer2,false));
        }



        String[] PRIZE_COLUMNS = {
                USERCHOICE_720_1,
                USERCHOICE_720_2,
                USERCHOICE_720_3,
                USERCHOICE_720_4,
                USERCHOICE_720_5,
                USERCHOICE_720_6,
                USERCHOICE_720_7
        };


        for (int i = 0; i < 7; i++) {

            String column = PRIZE_COLUMNS[i];
            String valueToCheck = prize[i];

            //String valueToCheck = i == 0 ? list.toString() : list.substring(i);
            // 각 컬럼에 대한 쿼리 실행
            //String query = "SELECT * FROM " + LOGTABLE_720 + " WHERE '" + column + "'='" + valueToCheck + "' AND " + USERDATE_ID_720 + " = " + get_720ID;
            String query = "SELECT * FROM " + LOGTABLE_720 + " WHERE " + column + "='" + valueToCheck + "' AND " + USERDATE_ID_720 + " = " + get_720ID;

            Cursor cursor = Fate_Fragment.ldb.rawQuery(query, null);

            // 결과 처리
            while (cursor.moveToNext()) {
                String string_list = cursor.getString(cursor.getColumnIndexOrThrow(USERCHOICE_720_1));
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(USERDATE_ID_720));
                int id_auto = cursor.getInt(cursor.getColumnIndexOrThrow(USERID_720));

                if (seenIds.contains(id_auto)) {
                    continue;
                }
                seenIds.add(id_auto); // 새 ID를 추적

                //if(0<=i && i<=6){
                    dialog_list.add(new Dblogload_dialog(activity,id,(i+1) +"등",string_list,mediaPlayer2,false));
                //}

            }
            cursor.close();
        }


    }

}