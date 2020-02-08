package gin.featuresjson;

import java.util.HashMap;
import java.util.Map;

public class KeywordTranslator {

    private static Map<String, String> de;

    static {
        de = new HashMap<>();
        de.put("Funktionalität", "Feature");
        de.put("Grundlage", "Background");
        de.put("Szenario", "Scenario");
        de.put("Gegeben sei", "Given");
        de.put("Gegeben seien", "Given");
        de.put("Angenommen", "Given");
        de.put("Wenn", "When");
        de.put("Dann", "Then");
        de.put("Aber", "But");
        de.put("Und", "And");
        de.put("Beispiele", "Examples");
        de.put("Szenariogrundriss", "Scenario Outline");
        de.put("*", "*");
    }

    private static Map<String, String> current_language = null;

    public static void setLanguage(String language){ if(language.equals("en")){
          current_language = null;
        } else if(language.equals("de")){
            current_language = de;
        }else{
            throw new IllegalArgumentException("Language " + language + " not supported");
        }
    }
    public static String toStandard(String nativeKeyword){
        if(current_language == null){
            return nativeKeyword;
        }else {
            return current_language.get(nativeKeyword.trim()) + " ";
        }
    }
}
