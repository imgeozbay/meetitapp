package com.project.meetit.core.util.helper;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class DateHelper {

    public static Date convertLocalDateToDate(LocalDate localDate)
    {
        ZoneId defaultZoneId = ZoneId.systemDefault();
        //local date + atStartOfDay() + default time zone + toInstant() = Date
        return  Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());
    }
}
