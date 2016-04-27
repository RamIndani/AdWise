package com.learninghorizon.adwise.estimote.cloud;

/**
 * Created by ramnivasindani on 4/26/16.
 */
public interface BeaconContentFactory {

    void getContent(BeaconID beaconID, Callback callback);

    interface Callback {
        void onContentReady(Object content);
    }
}
