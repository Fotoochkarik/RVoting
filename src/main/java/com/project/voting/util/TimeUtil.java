package com.project.voting.util;

import lombok.experimental.UtilityClass;

import java.time.Clock;
import java.time.LocalTime;

@UtilityClass
public class TimeUtil {
    public static Clock clock = Clock.systemUTC();

    public static final LocalTime LIMIT_TIME_OF_VOTING = LocalTime.of(11, 0);
}
