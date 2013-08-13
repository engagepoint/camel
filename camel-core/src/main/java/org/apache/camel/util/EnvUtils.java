/*
 * Copyright 2004-2013 ICEsoft Technologies Canada Corp.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the
 * License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS
 * IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */

package org.apache.camel.util;

import java.util.HashSet;
import java.util.Iterator;

public class EnvUtils {

    /**
     * A set of Strings representing all of the reserved words in Java
     */
    public static final HashSet<String> JAVA_RESERVED_WORDS;
    static{
        JAVA_RESERVED_WORDS = new HashSet(53);
        JAVA_RESERVED_WORDS.add("abstract");
        JAVA_RESERVED_WORDS.add("assert");
        JAVA_RESERVED_WORDS.add("boolean");
        JAVA_RESERVED_WORDS.add("break");
        JAVA_RESERVED_WORDS.add("byte");
        JAVA_RESERVED_WORDS.add("case");
        JAVA_RESERVED_WORDS.add("catch");
        JAVA_RESERVED_WORDS.add("char");
        JAVA_RESERVED_WORDS.add("class");
        JAVA_RESERVED_WORDS.add("const");
        JAVA_RESERVED_WORDS.add("continue");
        JAVA_RESERVED_WORDS.add("default");
        JAVA_RESERVED_WORDS.add("do");
        JAVA_RESERVED_WORDS.add("double");
        JAVA_RESERVED_WORDS.add("else");
        JAVA_RESERVED_WORDS.add("enum");
        JAVA_RESERVED_WORDS.add("extends");
        JAVA_RESERVED_WORDS.add("false");
        JAVA_RESERVED_WORDS.add("final");
        JAVA_RESERVED_WORDS.add("finally");
        JAVA_RESERVED_WORDS.add("float");
        JAVA_RESERVED_WORDS.add("for");
        JAVA_RESERVED_WORDS.add("goto");
        JAVA_RESERVED_WORDS.add("if");
        JAVA_RESERVED_WORDS.add("implements");
        JAVA_RESERVED_WORDS.add("import");
        JAVA_RESERVED_WORDS.add("instanceof");
        JAVA_RESERVED_WORDS.add("int");
        JAVA_RESERVED_WORDS.add("interface");
        JAVA_RESERVED_WORDS.add("long");
        JAVA_RESERVED_WORDS.add("native");
        JAVA_RESERVED_WORDS.add("new");
        JAVA_RESERVED_WORDS.add("null");
        JAVA_RESERVED_WORDS.add("package");
        JAVA_RESERVED_WORDS.add("private");
        JAVA_RESERVED_WORDS.add("protected");
        JAVA_RESERVED_WORDS.add("public");
        JAVA_RESERVED_WORDS.add("return");
        JAVA_RESERVED_WORDS.add("short");
        JAVA_RESERVED_WORDS.add("static");
        JAVA_RESERVED_WORDS.add("strictfp");
        JAVA_RESERVED_WORDS.add("super");
        JAVA_RESERVED_WORDS.add("switch");
        JAVA_RESERVED_WORDS.add("synchronized");
        JAVA_RESERVED_WORDS.add("this");
        JAVA_RESERVED_WORDS.add("throw");
        JAVA_RESERVED_WORDS.add("throws");
        JAVA_RESERVED_WORDS.add("transient");
        JAVA_RESERVED_WORDS.add("true");
        JAVA_RESERVED_WORDS.add("try");
        JAVA_RESERVED_WORDS.add("void");
        JAVA_RESERVED_WORDS.add("volatile");
        JAVA_RESERVED_WORDS.add("while");
    }

    /**
     *  When using reflection to programmatically create a class, e.g. Class.forName(),
     *  it may be desirable for security purposes to ensure that the parameter is a
     *  valid Java identifier before trying to use it.  This utility method examines the
     *  identifier to ensure that it follows the rules laid out in the specification.
     *
     *  This includes checking the starting character of each part using
     *  Character.isJavaIdentifierStart, all other characters using Character.isJavaIdentifierPart,
     *  and ensuring that each part is not a reserved Java keyword.
     *
     *  @param identifier A String representing a fully qualified classname.
     *  @return Returns true if the supplied identifier qualifies as a fully qualified classname.
     *
     */
    public static boolean isValidJavaIdentifier(String identifier) {

        if (identifier == null || identifier.length() == 0) {
            return false;
        }

        //Break the identifier into parts based on the dot
        String[] parts = identifier.split("\\.");

        //Check each individual part
        for (int i = 0; i < parts.length; i++) {
            String part = parts[i];

             //Must start with a valid Java identifier character
            if (!Character.isJavaIdentifierStart(part.charAt(0))) {
                return false;
            }

            Iterator words = JAVA_RESERVED_WORDS.iterator();
            while (words.hasNext()) {
                String reservedWord = (String)words.next();
                if(part.equals(reservedWord)){
                    return false;
                }
            }

            //Rest of identifier part must also contain valid characters
            for(int index = 1; index < part.length(); index++){
                if (!Character.isJavaIdentifierPart(part.charAt(index))) {
                    return false;
                }
            }

        }

        return true;
    }


}

