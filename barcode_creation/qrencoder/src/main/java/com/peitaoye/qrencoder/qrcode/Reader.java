package com.peitaoye.qrencoder.qrcode;


import com.peitaoye.qrencoder.BinaryBitmap;
import com.peitaoye.qrencoder.DecodeHintType;
import com.peitaoye.qrencoder.Exception.ChecksumException;
import com.peitaoye.qrencoder.Exception.FormatException;
import com.peitaoye.qrencoder.Exception.NotFoundException;
import com.peitaoye.qrencoder.Result;

import java.util.Map;

public interface Reader {
    Result decode(BinaryBitmap var1) throws NotFoundException, ChecksumException, FormatException;

    Result decode(BinaryBitmap var1, Map<DecodeHintType, ?> var2) throws NotFoundException, ChecksumException, FormatException;

    void reset();
}