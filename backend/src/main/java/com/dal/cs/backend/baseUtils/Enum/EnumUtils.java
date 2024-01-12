package com.dal.cs.backend.baseUtils.Enum;

import com.dal.cs.backend.member.Enum.MemberType;

public class EnumUtils {
    public static <T extends Enum<T>>  T fromString(Class<T> type, String value){
        return Enum.valueOf(type, value);
    }
}
