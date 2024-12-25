package yeong.wish.lotto;


import android.media.tv.AdRequest;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    public static InterstitialAd mInterstitialAd;
    BackDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNavigationView = findViewById(R.id.BNV);

        set_fragment();

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {

            }
        });
        loadInterstitialAd();

    }
    @Override
    public void onBackPressed() {
        if(dialog == null){
            super.onBackPressed();
        }
        dialog.show();

    }
    public void loadInterstitialAd() {
        AdRequest adRequest = new AdRequest.Builder().build();

        InterstitialAd.load(this, "ca-app-pub-3252837628484304/6413500535", adRequest,
                new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                        // 광고 로드 성공 시 호출됩니다.
                        MainActivity.mInterstitialAd = interstitialAd;

                        // 필요한 경우 여기에 추가적인 콜백 설정을 할 수 있습니다.
                        mInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                            @Override
                            public void onAdDismissedFullScreenContent() {
                                // 광고가 닫힐 때 호출됩니다.
                                mInterstitialAd = null;
                                loadInterstitialAd(); // 다음 광고를 다시 로드합니다.
                            }

                            @Override
                            public void onAdFailedToShowFullScreenContent(AdError adError) {
                                // 광고 표시에 실패했을 때 호출됩니다.
                            }

                            @Override
                            public void onAdShowedFullScreenContent() {
                                // 광고가 표시될 때 호출됩니다.
                            }
                        });
                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        // 광고 로드 실패 시 호출됩니다.
                        mInterstitialAd = null;
                        //Log.e("TAG", "onAdFailedToLoad: " + loadAdError.getMessage());
                    }
                });
    }

    /**프래그먼트를 바텀 네비게션에 연결위한 메소드*/
    public void set_fragment(){

        dialog = new BackDialog(this);
        Fate_Fragment Fate = new Fate_Fragment();
        Luck_Fragment Luck = new Luck_Fragment();
        Pioneering_Fragment Pioneering = new Pioneering_Fragment();


        getSupportFragmentManager().beginTransaction().replace(R.id.Container,Fate).commit();
        bottomNavigationView.setSelectedItemId(R.id.F_item);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.L_item) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.Container,Luck).commit();
                    return true;

                } else if (item.getItemId() == R.id.P_item) {
                    view_r(Pioneering);

                    return true;

                } else if (item.getItemId() == R.id.F_item) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.Container,Fate).commit();
                    return true;

                } else
                    return false;

            }

        });
    }
    private void view_r( Pioneering_Fragment Pioneering){
        if (mInterstitialAd != null) {
            mInterstitialAd.show(MainActivity.this);
            mInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                @Override
                public void onAdDismissedFullScreenContent() {
                    // 광고가 닫힐 때 호출됩니다.
                    mInterstitialAd = null;
                    loadInterstitialAd();
                    getSupportFragmentManager().beginTransaction().replace(R.id.Container,Pioneering).commit();
                    //MainActivity.super.onBackPressed();
                }

                @Override
                public void onAdFailedToShowFullScreenContent(AdError adError) {
                    // 광고 표시에 실패했을 때 호출됩니다.
                    mInterstitialAd = null;
                    //MainActivity.super.onBackPressed();
                    getSupportFragmentManager().beginTransaction().replace(R.id.Container,Pioneering).commit();
                }
            });
        }else{
            getSupportFragmentManager().beginTransaction().replace(R.id.Container,Pioneering).commit();
        }
    }
}