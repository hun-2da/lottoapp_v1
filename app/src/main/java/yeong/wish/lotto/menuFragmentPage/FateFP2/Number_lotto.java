package yeong.wish.lotto.menuFragmentPage.FateFP2;

import android.graphics.Bitmap;

public class Number_lotto {
    int id;
    int number_length;
    Bitmap bitmap;


    public Number_lotto(int id, int number_length, Bitmap bitmap) {
        this.id = id;
        this.number_length = number_length;
        this.bitmap = bitmap;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNumber_length() {
        return number_length;
    }

    public void setNumber_length(int number_length) {
        this.number_length = number_length;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }
}
