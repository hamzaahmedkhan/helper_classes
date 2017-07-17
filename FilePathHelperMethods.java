    /**
     * This Method Check if our mobile rotate image by default after capturing it and return BITMAP.
     *
     * @param file
     * @return
     */
    public static Bitmap checkifImageRotated(File file) {
        ExifInterface exif;
        try {
            exif = new ExifInterface(file.getAbsolutePath());
            int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            int rotate = 0;
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_270:
                    rotate = -90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    rotate = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_90:
                    rotate = 90;
                    break;
            }
            Bitmap bmp = BitmapFactory.decodeStream(new FileInputStream(file), null, null);
            if (rotate != 0) {
                Matrix matrix = new Matrix();
                matrix.setRotate(rotate);
                bmp = Bitmap.createBitmap(bmp, 0, 0, bmp.getWidth(), bmp.getHeight(), matrix, false);
                return bmp;
            } else {
                Matrix matrix = new Matrix();
                matrix.setRotate(0);
                bmp = Bitmap.createBitmap(bmp, 0, 0, bmp.getWidth(), bmp.getHeight(), matrix, false);
                return bmp;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }








 private void resizeAndStoreImageInStorage() {
        // Choose ResizeMode - FIT_TO_WIDTH, FIT_TO_HEIGHT, FIT_EXACT or AUTOMATIC
        Bitmap bitmap = ImageResizer.resize(fileOriginal, 192, 192, ResizeMode.FIT_EXACT);
         ByteArrayOutputStream bytes = new ByteArrayOutputStream();
//        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        fileThumbnail = new File(saveToInternalSorage(bitmap, "profile_picture", "profile_thumbnail"));

    }


    private Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "profile_thumbnail", null);
        return Uri.parse(path);
    }

    private String getRealPathFromURI(Uri uri) {
        Cursor cursor = getContext().getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
        return cursor.getString(idx);
    }


    private String saveToInternalSorage(Bitmap bitmapImage, String folderName, String fileName) {
        ContextWrapper cw = new ContextWrapper(getContext());
        File directory = cw.getDir(folderName, Context.MODE_PRIVATE);
        File mypath = new File(directory, fileName);

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(mypath);

            // Use the compress method on the BitMap object to write image to the OutputStream
            bitmapImage.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.d(TAG, "saveToInternalSorage: mypath.getPath()            ->" + mypath.getPath());
        Log.d(TAG, "saveToInternalSorage: mypath.getAbsolutePath()    ->" + mypath.getAbsolutePath());
        Log.d(TAG, "saveToInternalSorage: directory.getPath()         ->" + directory.getPath());
        Log.d(TAG, "saveToInternalSorage: directory.getAbsolutePath() ->" + directory.getAbsolutePath());
        return mypath.getAbsolutePath();
    }





  private void setProfilePicture() throws IOException {


        if (fileOriginal == null || fileThumbnail == null) {
            UIHelper.showShortToastInCenter(getActivity(), "Please Select Image to Update Profile Picture");
            return;
        } else {
//            Bitmap originalBitmap = checkifImageRotated(originalPicFile);
//            Bitmap thumbnailBitmap = checkifImageRotated(thumbnailPicFile);

            Bitmap originalBitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), Uri.fromFile(fileOriginal));
            Bitmap thumbnailBitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), Uri.fromFile(fileThumbnail));


            long originalLength = fileOriginal.length();
            originalLength = originalLength / 1024;

            String msgOriginalFile = "setProfilePicture: size Original " + originalLength + " Kb" + " -dimensions " + originalBitmap.getWidth() + " x " + originalBitmap.getHeight();
            Log.d(TAG, msgOriginalFile);


            long thumbLength = fileThumbnail.length();
            thumbLength = thumbLength / 1024;
            Log.d(TAG, "setProfilePicture: size Thumbnail " + thumbLength + " Kb" + " -dimensions " + thumbnailBitmap.getWidth() + " x " + thumbnailBitmap.getHeight());

            imageProfilePicture.setImageBitmap(originalBitmap);
            imageProfilePictureTick.setVisibility(View.VISIBLE);

        }
    }





    private void setProfilePictureAfterResult() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    setProfilePicture();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
