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

import com.android.mkstubs.Main.Logger;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;


class FilterClassAnnotation extends ClassVisitor {

    private final Logger mLog;
    private final Filter mFilter;
    private String mClassName;
    private boolean isRemoved = false;

    public FilterClassAnnotation(ClassVisitor writer,Filter filter, Logger log) {
        super(Main.ASM_VERSION,writer);
        mFilter = filter;
        mLog = log;
    }

    @Override
    public void visit(int version, int access, String name, String signature,
            String superName, String[] interfaces) {
    	
        mClassName = name;
        if ((access & (Opcodes.ACC_PUBLIC | Opcodes.ACC_PROTECTED)) == 0) {
        	setRemoveClass();
        }
        super.visit(version, access, name, signature, superName, interfaces);
    }

    @Override
    public void visitEnd() {
        super.visitEnd();
    }

    @Override
    public FieldVisitor visitField(int access, String name, String desc,
            String signature, Object value) {
    	if(isRemoved)
    		return null;

        // only accept public/protected fields
        if ((access & (Opcodes.ACC_PUBLIC | Opcodes.ACC_PROTECTED)) == 0) {
            return null;
        }

        // filter on field name
        String filterName = String.format("%s#%s", mClassName, name);

        // TODO we should produce an error if a filtered desc/signature is being used.

        FieldVisitor fv = super.visitField(access, name, desc, signature, value);
        return new FilterFieldAnnotation(fv, filterName,mFilter, mLog );
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String desc,
            String signature, String[] exceptions) {
    	if(isRemoved)
    		return null;

        // only accept public/protected methods
        if ((access & (Opcodes.ACC_PUBLIC | Opcodes.ACC_PROTECTED)) == 0) {
            return null;
        }

        // filter on method name using the non-generic descriptor
        String filterName = String.format("%s#%s%s", mClassName, name, desc);
        
        MethodVisitor mw = super.visitMethod(access, name, desc, signature, exceptions);
        return new FilterMethodAnnotation(mw, filterName,mFilter, mLog );
        
    }

    @Override
    public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
    	if(isRemoved)
    		return null;
        // TODO produce an error if a filtered annotation type is being used
    	if(!mFilter.acceptAnnotation(desc.substring(1,desc.length() - 1))) { 
    		setRemoveClass();
    	}
        return super.visitAnnotation(desc, visible);
    }

    private void setRemoveClass() {
    	if( !isRemoved) {
    		mFilter.getExcludeFull().add(mClassName);
    		mFilter.getExcludePrefix().add(mClassName+"$");
    		isRemoved = true;
    	}
    }
}
