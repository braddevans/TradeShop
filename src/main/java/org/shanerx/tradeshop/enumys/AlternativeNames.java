package org.shanerx.tradeshop.enumys;

public enum AlternativeNames {
    ENCHANTED_BOOK("enchanted_book", new String[]{"ebook"});

    String key;
    String[] alias;

    AlternativeNames(String key, String[] value) {
        this.key = key;
        this.alias = value;
    }

    public String[] getAlias() {
        return alias;
    }

    public String getKey() {
        return key;
    }

    public static String nameFromAlias(String alias) {
        String name = "";
        for(AlternativeNames an: AlternativeNames.values()) {
            for (int i = 0; i <= an.alias.length - 1; i++){
                if (an.alias[i].equals(alias.toLowerCase())) {
                    name = an.name();
                    return an.name();
                }
            }
        }
        if (name.equals("")){
            return alias;
        }
        return alias;
    }
}
