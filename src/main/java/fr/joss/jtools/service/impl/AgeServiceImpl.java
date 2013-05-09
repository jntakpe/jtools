package fr.joss.jtools.service.impl;

import fr.joss.jtools.domain.Age;
import fr.joss.jtools.service.AgeService;
import org.joda.time.*;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Implémentation des opérations sur l'age
 *
 * @author jntakpe
 */
@Service
public class AgeServiceImpl implements AgeService {

    @Override
    public Age calcAge(Date birthdate) {
        Interval interval = new Interval(new DateTime(birthdate), DateTime.now());
        Age age = new Age();
        age.setMonth(Months.monthsIn(interval).getMonths());
        age.setWeek(Weeks.weeksIn(interval).getWeeks());
        age.setDay(Days.daysIn(interval).getDays());
        age.setHour(Hours.hoursIn(interval).getHours());
        age.setMinute(Minutes.minutesIn(interval).getMinutes());
        age.setSecond(interval.toDuration().getStandardSeconds());
        return age;
    }
}
