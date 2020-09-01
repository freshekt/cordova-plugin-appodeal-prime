package com.appodealprime.ads;

import android.content.Context;
import android.util.Log;
import android.util.SparseArray;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.PluginResult;
import org.json.JSONException;
import org.json.JSONObject;

import com.appodeal.ads.Appodeal;
import com.appodeal.ads.InterstitialCallbacks;
import com.appodealprime.Action;
import com.appodealprime.AppodealPrime;
import com.appodealprime.Events;


public abstract class AdBase {
    protected static AppodealPrime plugin;

    final int id;

    private static SparseArray<AdBase> ads = new SparseArray<AdBase>();


    AdBase(int id) {
        this.id = id;

        ads.put(id, this);
    }

    public static void initialize(AppodealPrime plugin) {
        AdBase.plugin = plugin;
    }

    public static AdBase getAd(Integer id) {
        return ads.get(id);
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

    public void cache(int adType){

        switch (adType) {
            case Appodeal.INTERSTITIAL:
                    Appodeal.setInterstitialCallbacks(new InterstitialCallbacks() {
                        @Override
                        public void onInterstitialLoaded(boolean b) {
                            Log.d(InterstitialAd.TAG, "on Interstitial loaded");
                            plugin.emit(Events.INTERSTITIAL_LOAD);
                        }
            
                        @Override
                        public void onInterstitialFailedToLoad() {
                            Log.d(InterstitialAd.TAG, "Interstitial Failed to load");
                            plugin.emit(Events.INTERSTITIAL_LOAD_FAIL);
                        }
            
                        @Override
                        public void onInterstitialShown() {
                            Log.d(InterstitialAd.TAG, "Interstitial shown");
                            plugin.emit(Events.INTERSTITIAL_SHOW);
                        }
            
                        @Override
                        public void onInterstitialClicked() {
                            Log.d(InterstitialAd.TAG, "Interstitial clicked");
                            plugin.emit(Events.INTERSTITIAL_CLICK);
                        }
            
                        @Override
                        public void onInterstitialClosed() {
                            Log.d(InterstitialAd.TAG, "Interstitial closed");
                            plugin.emit(Events.INTERSTITIAL_CLOSE);
                        }
            
                        @Override
                        public void onInterstitialExpired() {
                            Log.d(InterstitialAd.TAG, "Interstitial expired");
                        }
                    });
                break;
        
            default:
                break;
        }

        Appodeal.cache(plugin.cordova.getActivity(),adType);
    }



    JSONObject buildErrorPayload(int errorCode) {
        JSONObject data = new JSONObject();
        try {
            data.put("errorCode", errorCode);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return data;
    }

    public void destroy() {
        ads.remove(id);
    }

    protected static float pxFromDp(final Context context, final float dp) {
        return dp * context.getResources().getDisplayMetrics().density;
    }
}
