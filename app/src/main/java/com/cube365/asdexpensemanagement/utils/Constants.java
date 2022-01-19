package com.cube365.asdexpensemanagement.utils;

public class Constants {
    public static final String BASE_URL = "http://expensemanagementapp-env.eba-zn6m8awy.us-east-2.elasticbeanstalk.com";

    public static final String APP_NAME = "ExpenseManagement";
    public static final String AUTHORIZATION_TOKEN = "TOKEN";

    public class Colors {
        public static final String ACTIONBAR_COLOR = "#F6F6F6F6";
        public static final String ASD_BLUE_COLOR = "#1D2786";
        public static final String ASD_GREEN_COLOR = "#59B791";
        public static final String ASD_BUTTON_GRAY_COLOR = "#DCDCDE";
    }

    public class ActivityTitles {
        public static final String MAIN_MENU = "MENU";
        public static final String PICKLIST_ACTIVITY = "PICK LISTS";
        public static final String TRANSACTIONS_ACTIVITY = "TRANSACTIONS";
        public static final String ADD_TRANSACTIONS_ACTIVITY = "ADD TRANSACTION";
        public static final String PUTAWAY_ACTIVITY = "PUTAWAY";
        public static final String RECEIVING_ACTIVITY = "RECEIVING";
        public static final String BIN_SELECTION_ACTIVITY = "BINS YOU NEED TO GO";
        public static final String STOCKVIEW_ACTIVITY = "STOCK VIEW";
    }

    public class ErrorMessage {
        public static final String ERROR_TAG = "WMS ERROR";
        public static final String GENERAL_MESSAGE = "Something went wrong, please try again later";
        public static final String NODATA_MESSAGE = "No data found, please try again later";
        public static final String BINLOCATIONS_NODATA_MESSAGE = "No data for binlocations found, please try again later";
    }

    public class InfoMessage{
        public static final String INVALID_SCAN = "Scanned item does not exists";
        public static final String SUCCESS_MESSAGE = "Succesfully saved";
        public static final String SUCCESS_POST_MESSAGE = "Succesfully posted";
    }

    public class PicklistInfo {
        public static final String STATUS_IN_QUEUE = "In Queue";
        public static final String STATUS_IN_PROGRESS = "In Progress";
        public static final String STATUS_COMPLETED = "Completed";
    }

    public class Extras {
        public static final String CONFIRMED_TRANSFER = "ConfirmedTransfer";
        public static final String PENDING_TRANSFER = "PendingTransfer";
        public static final String BIN_LOCATION = "BinLocation";
        public static final String PICKLIST_ID = "PicklistId";
    }
}
