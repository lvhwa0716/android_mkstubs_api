package win.i029.platlib;

import android.content.Context;
import android.util.Log;

import win.i029.annotation.ExportHide;

/**
 * Created by lvh on 12/3/18.
 */

public class FirstClass {

    public final static String TAG = "FirstClass";
    public final static String TAG3; // This just export variable name without values
	public final String TAG1 = "FirstClass_1"; // export variable name and values, modify platform, can't change APP
	@ExportHide()
    public final String TAG2 = "FirstClass_2";
	static {
		TAG3 = "HAS VALUE";
	}
    public FirstClass(Context context) {
        Log.d(TAG, context.getPackageName());
    }

	
    public int Add(int i, int j) {
        return (j + i);
    }

	@ExportHide()
    public int sub(int i, int j) {
        return (i - j);
    }

	public static class Show {
		public int i;
		
	}

	public class Show1 {
		public int i;
		
	}

	@ExportHide()
	public class Hide {
		public int i;
		
	}

	private class Hide1 {
		public int i;
		
	}
}
