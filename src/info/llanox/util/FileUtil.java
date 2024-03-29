package info.llanox.util;

import java.io.File;

import android.content.Context;
import android.os.Environment;
import android.widget.Toast;

public class FileUtil {

	
	
	public static File[] getFilesFromSDPath(String sdPath){
		File dir = new File(Environment.getExternalStorageDirectory().toString() + sdPath); 
		return dir.listFiles() ;
	}
	
	
	
	
	public static boolean isExternalStoragePresent(Context context) {

        boolean mExternalStorageAvailable = false;
        boolean mExternalStorageWriteable = false;
        String state = Environment.getExternalStorageState();

        if (Environment.MEDIA_MOUNTED.equals(state)) {
            // We can read and write the media
            mExternalStorageAvailable = mExternalStorageWriteable = true;
        } else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            // We can only read the media
            mExternalStorageAvailable = true;
            mExternalStorageWriteable = false;
        } else {
            // Something else is wrong. It may be one of many other states, but
            // all we need
            // to know is we can neither read nor write
            mExternalStorageAvailable = mExternalStorageWriteable = false;
        }
        if (!((mExternalStorageAvailable) && (mExternalStorageWriteable))) {
            Toast.makeText(context, "SD card not present", Toast.LENGTH_LONG).show();

        }
        return (mExternalStorageAvailable) && (mExternalStorageWriteable);
    }

}
