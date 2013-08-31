package si.majcn.krizisce.utils;

import android.util.SparseArray;
import android.util.SparseIntArray;

public class DblIntCounter {
	private SparseArray<SparseIntArray> mCounters;

    public DblIntCounter() {
    	resetCounter();
	}
    
    public int incCounter(int vehicleType, int turnType) {
    	if(mCounters.get(vehicleType) == null) {
    		mCounters.put(vehicleType, new SparseIntArray());
    	}
    	int n = mCounters.get(vehicleType).get(turnType, 0) + 1;
    	mCounters.get(vehicleType).put(turnType, n);
    	return n;
    }
    
    public int getCounter(int vehicleType, int turnType) {
    	if(mCounters.get(vehicleType) == null) {
    		return 0;
    	}
    	return mCounters.get(vehicleType).get(turnType, 0);
    }
    
    public void resetCounter() {
    	mCounters = new SparseArray<SparseIntArray>();
    }
}
