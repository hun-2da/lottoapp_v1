package yeong.wish.lotto.menuFragmentPage;


import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;

public class Luck_Fragment extends Fragment {
    Boolean Change = true;
    Button luck_Switch_Button;
    Fragment645 f645;   Fragment720plus f720;

    LogDagtabaseHelper localdb;
    public static SQLiteDatabase ldb;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup viewGroup = (ViewGroup)inflater.inflate(R.layout.fragment_luck, container, false);

        luck_Switch_Button = viewGroup.findViewById(R.id.L_button);


        f645 = new Fragment645();
        f720 = new Fragment720plus();


        File dbFile = getActivity().getApplicationContext().getDatabasePath(LogDagtabaseHelper.DATABASENAME);
        boolean isExist = dbFile.exists();
        if(!isExist){
            Toast.makeText(getContext(),"정보가 없습니다. \n개척페이지로 가십시오",Toast.LENGTH_SHORT).show();
            createDB();
        }else{
            createDB();
            if(isTableEmpty())
                ViewUpdate.boolString = "Success";
            else
                ViewUpdate.boolString = "failure";
        }

        change_f_lotto();


        luck_Switch_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                change_f_lotto();

            }
        });


        return viewGroup;
    }
    private void change_f_lotto(){
        if(Change) {
            getChildFragmentManager().beginTransaction().replace(R.id.luck_Container,f645).commit();
            luck_Switch_Button.setText("로또 6/45");
        }
        else {
            getChildFragmentManager().beginTransaction().replace(R.id.luck_Container,f720).commit();
            luck_Switch_Button.setText("연금 복권 720+");
        }

        Change = (Change)?false:true;
    }
    private void createDB(){
        localdb = new LogDagtabaseHelper(getContext());
        ldb = localdb.getWritableDatabase();
    }
    private boolean isTableEmpty() {
        String query = "SELECT EXISTS (SELECT 1 FROM " + LogDagtabaseHelper.TABLE720NAME + " LIMIT 1)";
        Cursor cursor = ldb.rawQuery(query, null);

        boolean tableIsEmpty = false;

        /*if(cursor.moveToNext()) {
            int count = cursor.getInt(0);
            if (count == 1) {
                tableIsEmpty = true;
                Log.e("db없", "1-2");
            }
        }*/
        cursor.moveToNext();
        if(cursor.getInt(0) == 1){
            tableIsEmpty = true;
        }

        cursor.close();
        return tableIsEmpty; // 테이블이 비어 있지 않다면 true 반환합니다.
    }
}