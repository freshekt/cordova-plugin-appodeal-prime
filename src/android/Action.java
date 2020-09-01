package com.appodealprime;

import com.appodealprime.ads.AdBase;

import org.json.JSONArray;
import org.json.JSONObject;

public class Action {
    public JSONObject opts;

    Action(JSONArray args) {
        this.opts = args.optJSONObject(0);
    }

    public int optId() {
        return this.opts.optInt("id");
    }

    public String optAppId() {
        return this.opts.optString("app_id");
    }

    public int getAdType() {
        return this.opts.optInt("ad_type");
    }

    public JSONObject optPosition() {
        return this.opts.optJSONObject("position");
    }

    public AdBase getAd() {
        return AdBase.getAd(optId());
    }
}
