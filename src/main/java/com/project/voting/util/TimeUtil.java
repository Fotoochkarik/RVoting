package com.project.voting.util;

import lombok.experimental.UtilityClass;

import java.time.Clock;

@UtilityClass
public class TimeUtil {
    public static Clock clock = Clock.systemUTC();
}
