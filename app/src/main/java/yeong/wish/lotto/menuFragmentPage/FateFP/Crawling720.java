package yeong.wish.lotto.menuFragmentPage.FateFP;

import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Crawling720 {

    public void parseLottoHtml(String html) {

        // HTML 문서 파싱
        Document doc = Jsoup.parse(html);

        // 특정 <tr> 태그 찾기
        //Elements trElements = doc.select("table.tbl_data tbody tr");
        Elements trElements = doc.select("table.tbl_data:nth-of-type(2) tbody tr");

        boolean isFirstElement = true;
        for (Element tr : trElements) {
            if (isFirstElement) { // 첫 번째 Element 건너뛰기
                isFirstElement = false;
                continue;
            }
            // 각 <tr> 태그의 첫 번째 <td> (회차)
            Element tdRound = tr.child(0);

            // <td> 태그의 특정 값 추출
            int round = Integer.parseInt(tdRound.text());

            String Prize[] = {"0","0","0","0","0","0","0"};
            Prize[0] = group_append(tr.child(2).text());
            Prize[1] = tr.child(3).text();
            Prize[2] = tr.child(4).text();
            Prize[3] = tr.child(5).text();
            Prize[4] = tr.child(6).text();


            Prize[5] = tr.child(7).text();
            Prize[6] = tr.child(8).text();


            String lastPrize = tr.child(9).text();


            //Log.e("720db확인용","회차: " + round + ", 2조 번호: " + secondPrize + ", 마지막 번호: " + lastPrize);
            InsertLog.lotto720(round,Prize,lastPrize);

        }
    }
    private String group_append(String prize){
        prize =  prize.replace("조 ", "");
        return prize;
    }
}
