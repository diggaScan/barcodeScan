# Barcode_creation
This is a repository aiming to provide some custom features.

## create_QRCode
this module extracts the QRCODE_creation module from Zxing libs.   
The easiest way to use this library is

    QRCodeEncoder qrCodeEncoder;
        try {
            qrCodeEncoder = new QRCodeEncoder(this, "hello", 500, Contents.Type.TEXT);
            Bitmap bitmap = qrCodeEncoder.encodeAsBitmap();
           // Bitmap bitmap = qrCodeEncoder.encodeAsBitmap(filePath);//store this bitmap into a file as a PNG
            if (bitmap == null) {
                qrCodeEncoder = null;
                return;
            }
            ImageView view = (ImageView) findViewById(R.id.imageView);
            view.setImageBitmap(bitmap);
    }catch (WriterException e) {
            e.printStackTrace();
        }
            qrCodeEncoder = null;
        }
the input params will be a content you may need to show after the decoding, the type of content, and the dimension of QRCode you may want to create.
# Zxingbarcode
basing on the original zxing library,several feature has been modified and more suitable for applying in the application
## modified 1:  
change the screen orientation to potrait and adjust the preview size with screen. 

### bug 1:
fix the bug which potrai screen cannot scan one dimension code

## modified 2:
reorder the multiple barcode reader, put orcode at the begining.

## modified 3:
reconstruct the qrcode encode module.

## modified 4:
delete the intent filter of capture activity, make it no more launch activity,add custom intent filter(action=com.barcode.SCAN).
delete the most UI widgets in capture activity.
