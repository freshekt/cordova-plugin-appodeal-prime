var AppodealPrime = exports;

var exec = require('cordova/exec');
var cordova = require('cordova');

/**
 * @ignore
 */
function execute(method, args) {
    return new Promise((resolve, reject) => {
        exec(function(event) {
            resolve(event);
        }, function(err) {
            reject(err);
        }, 'AppodealPrime', method, [args]);
    });
}

function fireDocumentEvent(eventName, data) {
    if (data === void 0) { data = null; }
    var event = new CustomEvent(eventName, { detail: data });
    document.dispatchEvent(event);
}

var nextId = 100
var adUnits = {}

function getAdUnitId(adUnitId) {
    if (adUnits[adUnitId]) {
        return adUnits[adUnitId]
    }
    adUnits[adUnitId] = nextId
    nextId += 1
    return adUnits[adUnitId]
}

function initConfig(app_id) {
    return {
        app_id:app_id
    }
}

function adConfig(adType) {
    return {
        id: getAdUnitId(AppodealPrime[adType]),
        ad_type: adType
    }
}

function nativeConfig(data) {
    return {
        position: data.position,
        id: getAdUnitId('NATIVE'),

    }
}

AppodealPrime.pluginVersion = '0.0.9';

AppodealPrime.NONE = 0;
AppodealPrime.INTERSTITIAL = 3;
AppodealPrime.BANNER = 4;
AppodealPrime.BANNER_BOTTOM = 8;
AppodealPrime.BANNER_TOP = 16;
AppodealPrime.BANNER_VIEW = 64;
AppodealPrime.REWARDED_VIDEO = 128;
AppodealPrime.NON_SKIPPABLE_VIDEO = 128;
AppodealPrime.NATIVE = 512;
AppodealPrime.MREC = 256;
AppodealPrime[0] = "NONE";
AppodealPrime[3] = "INTERSTITIAL";
AppodealPrime[4] = "BANNER";
AppodealPrime[8] = "BANNER_BOTTOM";
AppodealPrime[16] = "BANNER_TOP";
AppodealPrime[64] = "BANNER_VIEW";
AppodealPrime[128] =  "REWARDED_VIDEO";
AppodealPrime[512] = "NATIVE";
AppodealPrime[256] = "MREC";

AppodealPrime.ready = function() {
    return new Promise((resolve, reject) => {
        exec(function(event) {
            fireDocumentEvent(event.type, event.data);
            resolve(event.data);
        }, function(err) {
            reject(err);
        }, 'AppodealPrime', 'ready');
    });
};

AppodealPrime.showBanner = function() {
    return execute('banner_show', adConfig(AppodealPrime.BANNER_BOTTOM));
};

AppodealPrime.hideBanner = function() {
    return execute('banner_hide', adConfig(AppodealPrime.BANNER_BOTTOM));
}

AppodealPrime.loadNative = function() {
    return execute('native_load', adConfig(AppodealPrime.NATIVE));
}

AppodealPrime.showNative = function(data) {
    return execute('native_show', nativeConfig(data));
}

AppodealPrime.hideNative = function() {
    return execute('native_hide', adConfig(AppodealPrime.NATIVE));
}

AppodealPrime.showInterstitial = function() {
    return execute('interstitial_show', adConfig(AppodealPrime.INTERSTITIAL));
}

AppodealPrime.init = function(app_id){
    return execute('init',initConfig(app_id));
}


AppodealPrime.cache = function(ad_type){
    return execute('cache',adConfig(ad_type));
}

AppodealPrime.showRewardVideo = function() {
    return execute('reward_video_show', adConfig(AppodealPrime.REWARD_VIDEO));
}