package com.appodealprime.ads;

import android.content.Context;
import android.util.SparseArray;

import org.json.JSONException;
import org.json.JSONObject;

import com.appodealprime.AppodealPrime;


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


    public void cache(int adType){

        switch (adType) {
            case Appodeal.INTERSTITIAL:
                    Appodeal.setInterstitialCallbacks(new InterstitialCallbacks() {
                        @Override
                        public void onInterstitialLoaded(boolean b) {
                            Log.d(TAG, "on Interstitial loaded");
                            plugin.emit(Events.INTERSTITIAL_LOAD);
                        }
            
                        @Override
                        public void onInterstitialFailedToLoad() {
                            Log.d(TAG, "Interstitial Failed to load");
                            plugin.emit(Events.INTERSTITIAL_LOAD_FAIL);
                        }
            
                        @Override
                        public void onInterstitialShown() {
                            Log.d(TAG, "Interstitial shown");
                            plugin.emit(Events.INTERSTITIAL_SHOW);
                        }
            
                        @Override
                        public void onInterstitialClicked() {
                            Log.d(TAG, "Interstitial clicked");
                            plugin.emit(Events.INTERSTITIAL_CLICK);
                        }
            
                        @Override
                        public void onInterstitialClosed() {
                            Log.d(TAG, "Interstitial closed");
                            plugin.emit(Events.INTERSTITIAL_CLOSE);
                        }
            
                        @Override
                        public void onInterstitialExpired() {
                            Log.d(TAG, "Interstitial expired");
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
