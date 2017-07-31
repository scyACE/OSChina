package com.wxhl.core.utils;

import java.util.Collection;
import java.util.List;

public class CollectionUtil {

    /**
     * @Description: 判断List集合是否为空
     * @param list
     * @return
     */
    public static boolean listIsNull(Collection<? extends Object> list){
        if(null ==list || list.size()==0){
            return true;
        }
        return false;
    }

    /**
     * @Description: 判断List集合是否为空
     * @param list
     * @return
     */
    public static boolean listIsNotNull(Collection<? extends Object> list){
        if(null !=list && list.size()>0){
            return true;
        }
        return false;
    }

    /**
     * 清除List中所有元素
     * @param list
     */
    public static void clearList(List<?> list){
        if(list!=null){
            list.clear();
            list=null;
        }
    }
}
