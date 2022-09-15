package Team4.egg.AfterClass.utility;

import org.springframework.stereotype.Component;

@Component
public interface StringsMethods {


    default String transformString(String str) {
        str = str.trim();

        if(!str.isEmpty()) {
            char[] arr = str.toLowerCase().toCharArray();
            str = str.substring(0, 1).toUpperCase();
            for (int i = 1; i < arr.length; i++) {
                if (arr[i] == ' ' ) {
                    arr[i + 1] = Character.toUpperCase(arr[i + 1]);
                }
                str = str.concat(String.valueOf(arr[i]));
            }
        }
        return str;
    }

}
