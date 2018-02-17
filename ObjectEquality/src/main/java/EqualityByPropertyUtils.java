import com.google.gson.Gson;
import org.apache.commons.beanutils.PropertyUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class EqualityByPropertyUtils implements ObjectEqualityCheck {
    private static final Map<Class<?>, Class<?>> wrapperPrimitiveMap = new HashMap<>();

    static {
        wrapperPrimitiveMap.put(Boolean.class, Boolean.TYPE);
        wrapperPrimitiveMap.put(Byte.class, Byte.TYPE);
        wrapperPrimitiveMap.put(Character.class, Character.TYPE);
        wrapperPrimitiveMap.put(Short.class, Short.TYPE);
        wrapperPrimitiveMap.put(Integer.class, Integer.TYPE);
        wrapperPrimitiveMap.put(Long.class, Long.TYPE);
        wrapperPrimitiveMap.put(Double.class, Double.TYPE);
        wrapperPrimitiveMap.put(Float.class, Float.TYPE);
        wrapperPrimitiveMap.put(Void.TYPE, Void.TYPE);
    }

    public boolean checkEquality(Object object1, Object object2) {
        try {
            //todo: first check if only primitive check is needed..
            //todo: can i stop recursive objects equality?
            //todo: multithreaded?
            Map<String, Object> map1 = PropertyUtils.describe(object1);
            Map<String, Object> map2 = PropertyUtils.describe(object2);
            for (String property : map1.keySet()) {
                //when using describe one of the properties is class and we can ignore it
                if (!property.equals("class") && !property.equals("declaringClass")) {
                    Object value1 = map1.get(property);
                    Object value2 = map2.get(property);

                    if (bothValuesAreNull(value1, value2)) {
                        continue;
                    }
                    if (onlyOneOfTheValuesIsNull(value1, value2)) {
                        return false;
                    }
                    if (bothValuesAreOfPrimitiveClass(value1, value2) ||
                            bothValuesAreOfEnumClass(value1, value2) ||
                            bothValuesAreOfPrimitiveWrapperClass(value1, value2) ||
                            bothValuesAreStrings(value1, value2)) {
                        if (value1.equals(value2)) {
                            continue;
                        } else {
                            return false;
                        }
                    }

                    if (bothValuesAreArrays(value1, value2)) {
                        if (bothArraysAreEqual(value1, value2)) {
                            continue;
                        } else {
                            return false;
                        }
                    }

                    if (bothValuesAreCollections(value1, value2)) {
                        if (bothCollectionsAreEqual((Collection)value1, (Collection)value2)) {
                            continue;
                        } else {
                            return false;
                        }
                    }

                    if(bothValuesAreMaps(value1, value2)){
                        if(bothMapsAreEqual((Map)value1, (Map)value2)){
                            continue;
                        } else{
                            return false;
                        }
                    }
                    if(bothValuesAreOptionals(value1, value2)){
                        if(bothOptionalsAreEqual((Optional)value1,(Optional)value2)){
                            continue;
                        }else{
                            return false;
                        }
                    }

                    if (!checkEquality(map1.get(property), map2.get(property))) {
                        return false;
                    }
                }
            }
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        return true;
    }

    private boolean bothOptionalsAreEqual(Optional value1, Optional value2) {

        if(value1.isPresent() && value2.isPresent()){
            return checkEquality(value1.get(), value2.get());
        } else if (!value1.isPresent() && !value2.isPresent()){
            return true;
        } else{
            return false;
        }


    }

    private boolean bothValuesAreOptionals(Object value1, Object value2) {
        return value1.getClass().equals(Optional.class) && value2.getClass().equals(Optional.class);
    }

    private boolean bothEnumSetsAreEqual(EnumSet value1, EnumSet value2) {
        EnumSet enumSet1 = value1;
        EnumSet enumSet2 = value2;

        if (enumSet1.size() == enumSet2.size()) {
            for (Object itemInCollection1 : enumSet1) {
                for (Object itemInCollection2 : enumSet2) {
                    if (!checkEquality(itemInCollection1, itemInCollection2)) {
                        return false;
                    }
                }
            }
        } else {
            return false;
        }

        return true;
    }

    private boolean bothValuesAreEnumSets(Object value1, Object value2) {
        return value1 instanceof EnumSet && value2 instanceof EnumSet;
    }

    private boolean bothMapsAreEqual(Map value1, Map value2) {
        Map map1 = value1;
        Map map2 = value2;

        if (map1.size() == map2.size()) {
            for (Object key : map1.keySet()) {
                if(map2.keySet().contains(key)) {
                    if (!checkEquality(map1.get(key), map2.get(key))){
                        return false;
                    }
                } else {
                    return false;
                }
            }
        } else {
            return false;
        }

        return true;
    }


    private boolean bothValuesAreMaps(Object value1, Object value2) {
        return value1 instanceof Map && value2 instanceof Map;
    }

    private boolean bothCollectionsAreEqual(Collection value1, Collection value2) {
        Collection collection1 = value1;
        Collection collection2 = value2;

        if (collection1.size() == collection2.size()) {
            for (Object itemInCollection1 : collection1) {
                for (Object itemInCollection2 : collection2) {
                    if (!checkEquality(itemInCollection1, itemInCollection2)) {
                        return false;
                    }
                }
            }
        } else {
            return false;
        }

        return true;
    }

    private boolean bothValuesAreCollections(Object value1, Object value2) {
        return value1 instanceof Collection && value2 instanceof Collection;
    }

    /***
     note: I saw a suggestion to write switch\case on every primitive type
     but this seems much more easy and does the work for me
     ***/
    private boolean bothArraysAreEqual(Object value1, Object value2) {
        String array1AsJsonString = new Gson().toJson(value1);
        String array2AsJsonString = new Gson().toJson(value2);
        return array1AsJsonString.equals(array2AsJsonString);
    }

    private boolean bothValuesAreArrays(Object value1, Object value2) {
        return value1.getClass().isArray() && value2.getClass().isArray();
    }

    private boolean bothValuesAreStrings(Object value1, Object value2) {
        return value1.getClass().equals(String.class) && value2.getClass().equals(String.class);
    }

    private boolean onlyOneOfTheValuesIsNull(Object value1, Object value2) {
        return (value1 == null && value2 != null) || (value2 == null && value1 != null);
    }

    private boolean bothValuesAreOfPrimitiveWrapperClass(Object value1, Object value2) {
        Class primitiveWrapperClass1 = wrapperPrimitiveMap.get(value1.getClass());
        Class primitiveWrapperClass2 = wrapperPrimitiveMap.get(value2.getClass());
        return primitiveWrapperClass1 != null && primitiveWrapperClass2 != null;
    }

    private boolean bothValuesAreOfEnumClass(Object value1, Object value2) {
        return value1 instanceof Enum && value2 instanceof Enum;
    }

    private boolean bothValuesAreOfPrimitiveClass(Object value1, Object value2) {
        return value1.getClass().isPrimitive() && value2.getClass().isPrimitive();
    }

    private boolean bothValuesAreNull(Object value1, Object value2) {
        return value1 == null && value2 == null;
    }
}
