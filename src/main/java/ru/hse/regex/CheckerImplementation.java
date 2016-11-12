package ru.hse.regex;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CheckerImplementation implements Checker {
    public Pattern getPLSQLNamesPattern() {
        return Pattern.compile(
                "(\\p{Alpha})([$\\w]){0,29}");
    }

    public Pattern getHrefURLPattern() {
        // P. S. Я постарался реализовать чуть лучше, что бы можно было просто брать любой HTML со
        // страницы и проверять все теги. Естестевенно, в данном случае получается, что пробел в Regex
        // при отсутствии кавычек в свойстве атрибура href никак не учесть, но я отдельно выделил в группу
        // ссылку до пробела.
        return Pattern.compile(
                "<\\s*[Aa]\\s+([^>]*\\s+)?[Hh][Rr][Ee][Ff]\\s*=\\s*\"[^\"]*\"(\\s+([^>]*))*>");
    }

    public Pattern getEMailPattern() {
        return Pattern.compile(
                "((\\p{Alnum})([\\w][\\-.]?){0,19})?(\\p{Alnum})@((\\p{Alnum}){2,}\\.)+(\\p{Alpha}){2,6}");
    }

    public boolean checkAccordance(String inputString, Pattern pattern) throws IllegalArgumentException {
        if (inputString == null && pattern == null) return true;
        if (inputString == null ^ pattern == null) throw new IllegalArgumentException("Was passed null.");
        return pattern.matcher(inputString).matches();
    }

    public List<String> fetchAllTemplates(StringBuffer inputString, Pattern pattern) throws IllegalArgumentException {
        if (inputString == null || pattern == null) throw new IllegalArgumentException("Was passed null.");

        List<String> allMatches = new ArrayList<>();

        Matcher m = pattern.matcher(inputString);

        while (m.find())
            allMatches.add(m.group());

//        allMatches.forEach(System.out::println);

        return allMatches;
    }
}
