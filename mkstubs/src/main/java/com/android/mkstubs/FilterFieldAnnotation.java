/*
 * Copyright (C) 2009 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.android.mkstubs;

import com.android.mkstubs.Main;
import com.android.mkstubs.Main.Logger;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.FieldVisitor;

public class FilterFieldAnnotation extends FieldVisitor {
	private static final String TAG = "FilterFieldAnnotation: ";
	private String mFilterName;
	private Logger mLog;
	private Filter mFilter;
    public FilterFieldAnnotation(FieldVisitor mw,
            String filterName, Filter filter , Logger log) {
        super(Main.ASM_VERSION, mw);
        mFilterName = filterName;
        mLog = log;
        mFilter = filter;
    }


    @Override
    public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
    	if(!mFilter.acceptAnnotation(desc.substring(1,desc.length() - 1))) { 
    		mFilter.getExcludeFull().add(mFilterName);
    		
    	}
    	
        return super.visitAnnotation(desc, visible);
    }

}
