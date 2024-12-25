package yeong.wish.lotto.menuFragmentPage.FateFP;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Crawling645 {

    public void parseLottoHtml(String html) {
        Document doc = Jsoup.parse(html);

        Elements trElements = doc.select("table.tbl_data tbody tr");


        for (Element tr : trElements) {
            // 회차 번호 추출
            int count = 0;
            int round = string_to_int(tr.select("td").first().text());


            // 로또 번호 추출
            Elements lottoNumbers = tr.select("span.ball_645.sml");
            int numbers[] = {0,0,0,0,0,0};
            //String numbers[] = {"0","0","0","0","0","0"};

            int bonusNumber = 0;
            for (Element number : lottoNumbers) {

                if(count==6){
                    bonusNumber = Integer.parseInt(number.text());
                }else {
                    int num = Integer.parseInt(number.text());
                    //String num = number.text();

                    numbers[count] = num;
                }
                count++;
            }

            // 보너스 번호 추출 (예: <span class="ball_645 sml ball1">4</span>)
            
            //String bonusNumber = tr.select("span.ball_645.sml.ball1").text();

            //Log.e("645db","회차: " + round + ", 번호: " + numbers.toString() + ", 보너스 번호: " + bonusNumber);
            InsertLog.lotto645(round,numbers,bonusNumber);
        }
    }
    public int string_to_int(String round){
        String numberStr = round.replaceAll("[^\\d]", "");
        return Integer.parseInt(numberStr);
    }


}
