package com.peitaoye.qrencoder.qrcode;


import com.peitaoye.qrencoder.BarcodeFormat;
import com.peitaoye.qrencoder.EncodeHintType;
import com.peitaoye.qrencoder.Exception.WriterException;
import com.peitaoye.qrencoder.common.BitMatrix;

import java.util.Map;

public interface Writer {
    BitMatrix encode(String var1, BarcodeFormat var2, int var3, int var4) throws WriterException;

    BitMatrix encode(String var1, BarcodeFormat var2, int var3, int var4, Map<EncodeHintType, ?> var5) throws WriterException;
}
