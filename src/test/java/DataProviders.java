
/*
 * Copyright 8/3/18 by Stephen Beitzel
 */

import com.qbcps.util.collections.ArraySet;
import com.qbcps.util.collections.LinkedSet;
import org.testng.annotations.DataProvider;

/**
 * @author Stephen Beitzel &lt;sbeitzel@pobox.com&gt;
 */
public class DataProviders {

    @DataProvider(name = "listSets")
    public static Object[][] getListSets() {
        return new Object[][] {
                {"ArraySet", new ArraySet<Integer>()}
                ,{"LinkedSet", new LinkedSet<Integer>()}
        };
    }

}
