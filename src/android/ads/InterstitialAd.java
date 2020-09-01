package com.appodealprime.ads;

import android.util.Log;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.PluginResult;

import com.appodeal.ads.Appodeal;
import com.appodeal.ads.InterstitialCallbacks;
import com.appodealprime.Action;
import com.appodealprime.Events;

public class InterstitialAd extends AdBase {
    private static final String TAG = "AP::InterstitialAd";

    InterstitialAd(int id) {
        super(id);
    }

    public static boolean executeInterstitialShowAction(Action action, CallbackContext callbackContext) {
        plugin.cordova.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {

                InterstitialAd interstitialAd = (InterstitialAd) action.getAd();
                if (interstitialAd == null) {
                    interstitialAd = new InterstitialAd(
                            action.optId()
                    );
                }
                interstitialAd.show();
                PluginResult result = new PluginResult(PluginResult.Status.OK, "");
                callbackContext.sendPluginResult(result);
            }
        });

        return true;
    }

    public static boolean executeInterstitialCacheAction(Action action, CallbackContext callbackContext) {
        plugin.cordova.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {

                InterstitialAd interstitialAd = (InterstitialAd) action.getAd();
                if (interstitialAd == null) {
                    interstitialAd = new InterstitialAd(
                            action.optId()
                    );
                }
                interstitialAd.cache(Appodeal.INTERSTITIAL);
                PluginResult result = new PluginResult(PluginResult.Status.OK, "");
                callbackContext.sendPluginResult(result);
            }
        });

        return true;
    }

    public void show() {
        Appodeal.show(plugin.cordova.getActivity(), Appodeal.INTERSTITIAL);

    }
}
