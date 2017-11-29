package com.tfl.billing;

import com.oyster.OysterCard;
import com.oyster.ScanListener;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public interface OysterCardReader {
    UUID readerId = UUID.randomUUID();
    List<ScanListener> listeners = new ArrayList();

    void register(ScanListener scanListener);

    void touch(OysterCard card);

    UUID getReaderId();
}
