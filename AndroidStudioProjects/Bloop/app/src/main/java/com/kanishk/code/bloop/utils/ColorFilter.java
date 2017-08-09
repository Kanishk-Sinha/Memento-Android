package com.kanishk.code.bloop.utils;

import com.kanishk.code.bloop.R;
import com.kanishk.code.bloop.data.AppConstants;

/**
 * Created by kanishk on 31/7/17.
 */

public class ColorFilter {
    public static int getColorForType(int type) {
        int color = 0;
        switch (type) {
            case AppConstants.ColorConstants.COLOR_WHITE_1 :
                return R.color.colorWhite;
            case AppConstants.ColorConstants.COLOR_BLUE_1 :
                return R.color.colorBlue1;
            case AppConstants.ColorConstants.COLOR_BLUE_2 :
                return R.color.colorBlue2;
            case AppConstants.ColorConstants.COLOR_CREAM_1 :
                return R.color.colorCream1;
            case AppConstants.ColorConstants.COLOR_CREAM_2 :
                return R.color.colorCream2;
            case AppConstants.ColorConstants.COLOR_GREEN_1:
                return R.color.colorGreen1;
            case AppConstants.ColorConstants.COLOR_GREEN_2:
                return R.color.colorGreen2;
            case AppConstants.ColorConstants.COLOR_RED_1 :
                return R.color.colorRed1;
            case AppConstants.ColorConstants.COLOR_RED_2 :
                return R.color.colorRed2;
        }
        return color;
    }
}
