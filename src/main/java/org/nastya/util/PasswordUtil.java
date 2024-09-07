package org.nastya.util;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordUtil {

    public static String encode(String password){
        return BCrypt.hashpw(password, BCrypt.gensalt(12));
    }

    public static boolean decode(String hashPassword){
        return BCrypt.checkpw(hashPassword, BCrypt.gensalt(12));
    }
}
