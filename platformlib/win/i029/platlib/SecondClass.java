package win.i029.platlib;

import android.content.Context;
import android.util.Log;

import win.i029.annotation.ExportHide;

/**
 * Created by lvh on 12/3/18.
 */

@ExportHide()
public class SecondClass {
    private final static String TAG = "SecondClass";
    public SecondClass(Context context) {
        Log.d(TAG, context.getPackageName());
    }

	
    public int Add(int i, int j) {
        return (j + i) * 5;
    }

	@ExportHide()
    public int sub(int i, int j) {
        return (i - j);
    }
	public static class Hide {
		public int i;
		
	}

	public class HideInner {
		public int i;
		
	}
}
