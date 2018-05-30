package com.peitaoye.qrencoder.qrcode;

import com.peitaoye.qrencoder.Exception.WriterException;
import com.peitaoye.qrencoder.common.BitMatrix;
import com.peitaoye.qrencoder.qrcode.decoder.ErrorCorrectionLevel;
import com.peitaoye.qrencoder.qrcode.encoder.ByteMatrix;
import com.peitaoye.qrencoder.qrcode.encoder.Encoder;
import com.peitaoye.qrencoder.qrcode.encoder.QRCode;

public class QRCodeWriter {
    private static final int QUIET_ZONE_SIZE = 4;

    public QRCodeWriter() {
    }



    public BitMatrix encode(String contents, int width, int height) throws WriterException {
        if (contents.isEmpty()) {
            throw new IllegalArgumentException("Found empty contents");
        }else if (width >= 0 && height >= 0) {
            ErrorCorrectionLevel errorCorrectionLevel = ErrorCorrectionLevel.L;
            int quietZone = 4;
            return renderResult(Encoder.encode(contents, errorCorrectionLevel), width, height, quietZone);
        } else {
            throw new IllegalArgumentException("Requested dimensions are too small: " + width + 'x' + height);
        }
    }

    private static BitMatrix renderResult(QRCode code, int width, int height, int quietZone) {
        ByteMatrix input;
        if ((input = code.getMatrix()) == null) {
            throw new IllegalStateException();
        } else {
            int inputWidth = input.getWidth();
            int inputHeight = input.getHeight();
            int qrWidth = inputWidth + (quietZone << 1);
            int qrHeight = inputHeight + (quietZone << 1);
            int outputWidth = Math.max(width, qrWidth);
            int outputHeight = Math.max(height, qrHeight);
            int multiple = Math.min(outputWidth / qrWidth, outputHeight / qrHeight);
            int leftPadding = (outputWidth - inputWidth * multiple) / 2;
            int topPadding = (outputHeight - inputHeight * multiple) / 2;
            BitMatrix output = new BitMatrix(outputWidth, outputHeight);
            int inputY = 0;

            for(int outputY = topPadding; inputY < inputHeight; outputY += multiple) {
                int inputX = 0;

                for(int outputX = leftPadding; inputX < inputWidth; outputX += multiple) {
                    if (input.get(inputX, inputY) == 1) {
                        output.setRegion(outputX, outputY, multiple, multiple);
                    }

                    ++inputX;
                }

                ++inputY;
            }

            return output;
        }
    }
}
