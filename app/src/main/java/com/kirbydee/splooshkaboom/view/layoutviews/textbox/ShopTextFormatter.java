package com.kirbydee.splooshkaboom.view.layoutviews.textbox;

import android.util.Log;

import com.kirbydee.splooshkaboom.model.counter.Rupees;
import com.kirbydee.splooshkaboom.view.layoutviews.shop.ShopItemView;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ShopTextFormatter implements TextFormatter {

    private static final String TAG = ShopTextFormatter.class.getName();

    private static final Pattern RUPEE_PATTERN = Pattern.compile("\\[rupees_([1-3])" + "]");

    private final Map<Integer, Integer> idRupeesMap;

    public ShopTextFormatter() {
        this(new HashSet<>());
    }

    public ShopTextFormatter(Set<ShopItemView> itemViews) {
        this.idRupeesMap = new HashMap<>();
        itemViews.forEach(v ->
                addView(v.getItemIndex(), Rupees.of(v.getRupees()).get())
        );
    }

    @Override
    public String format(String text) {
        Log.i(TAG, "format (" + text + ")");
        text = checkForRupees(text);
        return text;
    }

    private String checkForRupees(String text) {
        Log.i(TAG, "checkForRupees (" + text + ")");
        Matcher m = RUPEE_PATTERN.matcher(text);
        StringBuffer sb = new StringBuffer(text.length());
        while (m.find()) {
            String index = m.group(1);
            m.appendReplacement(sb, Matcher.quoteReplacement(this.idRupeesMap.get(Integer.valueOf(index)).toString()));
        }
        m.appendTail(sb);
        return sb.toString();
    }

    public void addView(int index, int rupees) {
        Log.i(TAG, "addView (" + index + ", " + rupees + ")");
        this.idRupeesMap.put(index, rupees);
    }
}
