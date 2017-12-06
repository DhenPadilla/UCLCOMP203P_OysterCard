package com.tfl.billing;

import java.util.UUID;

public interface ScanListener {
    boolean cardScanned(UUID var1, UUID var2);
}
