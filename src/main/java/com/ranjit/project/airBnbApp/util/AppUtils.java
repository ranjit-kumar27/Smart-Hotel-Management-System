package com.ranjit.project.airBnbApp.util;

import com.ranjit.project.airBnbApp.entity.User;
import org.springframework.security.core.context.SecurityContextHolder;

public class AppUtils {

    public static User getCurrentUser(){
        //System.out.println(SecurityContextHolder.getContext().getAuthentication().getPrincipal().getClass());
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
