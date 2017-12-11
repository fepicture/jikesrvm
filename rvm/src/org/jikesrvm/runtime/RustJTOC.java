package org.jikesrvm.runtime;

import org.jikesrvm.classloader.NormalMethod;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.lang.reflect.Field;

public class RustJTOC {
    public static void compileMMTk() {
        try {
            PrintWriter writer;
            writer = new PrintWriter("./mmtk/src/vm/jtoc.rs");
            writer.println("// Do not edit this file. It is automatically generated.");
            for (Field child : Entrypoints.class.getDeclaredFields()) {
                if (child.get(null) instanceof NormalMethod) {
                    writer.println("pub const " + child.getName()  + "JTOCOffset: i32 = " + ((NormalMethod) child.get(null)).getOffset() + ";");
                }
            }
            writer.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}