import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.time.temporal.WeekFields;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.NavigableMap;
import java.util.NavigableSet;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class DateUtility {

    /**
     * Crea un insieme contenente tutte le date all'interno di un periodo temporale, estremi inclusi
     *
     * @param dateStart inizio periodo
     * @param dateEnd   fine periodo
     * @return insieme di date
     */
    public static NavigableSet<LocalDate> createSetDates(LocalDate dateStart, LocalDate dateEnd) {
        NavigableSet<LocalDate> setDates = new TreeSet<>();
        LocalDate temp = dateStart;
        do {
            setDates.add(temp);
            temp = temp.plusDays(1);
        } while (!temp.isAfter(dateEnd));
        return setDates;
    }

    /**
     * Crea un oggetto di classe {@link java.time.LocalDateTime } partendo da due oggetti di classe {@link java.time.LocalDate } e
     * {@link java.time.LocalTime }.
     *
     * @param date data
     * @param time time
     * @return date-time
     */
    public static LocalDateTime createDateTime(LocalDate date, LocalTime time) {
        return LocalDateTime.of(date, time);
    }

    /**
     * Controlla che il periodo di tempo passato in ingresso non sia invertito, ovvero con fine minore inizio; in tal caso,
     * aggiunge un giorno a fine e restituisce la nuova fine, altrimenti il vecchio valore.
     *
     * @param inizio Inizio del periodo
     * @param fine   Fine del periodo
     * @return Fine in ingresso, o Fine + 1gg
     */
    public static LocalDateTime checkCrossDay(LocalDateTime inizio, LocalDateTime fine) {
        return !fine.isAfter(inizio) ? fine.plusDays(1) : fine;
    }

    /**
     * Restituisce il tempo in formato time a partire dai millisecondi trascorsi dalla mezzanotte
     *
     * @param millis i millisecondi da aggiungere alla mezzanotte
     * @return un oggetto time; se mezzanotte, restituisce 00:00
     */
    public static LocalTime getLocalTimeFromMillis(long millis) {
        LocalTime midnight = LocalTime.MIDNIGHT;
        return midnight.plus(millis, ChronoUnit.MILLIS);
    }

    /**
     * Controlla che il primo parametro sia successivo o uguale al secondo. L'utilizzo è in realtà sconsigliato, conviene sempre
     * controllare il date-time in caso di notturno
     *
     * @param timeFrom time da controllare
     * @param timeTo   time perno del controllo
     * @return vero se dateFrom è maggiore o uguale a dateTo, falso altrimenti
     */
    public static boolean isAfterOrEqual(LocalTime timeFrom, LocalTime timeTo) {
        return !timeFrom.isBefore(timeTo);
    }

    /**
     * Controlla che la prima data si successiva o uguale alla seconda
     *
     * @param dateFrom data che deve essere successiva o uguale
     * @param dateTo   data perno del controllo
     * @return vero se dateFrom è maggiore o uguale a dateTo, falso altrimenti
     */
    public static boolean isAfterOrEqual(LocalDate dateFrom, LocalDate dateTo) {
        return !dateFrom.isBefore(dateTo);
    }

    /**
     * Controlla che il primo parametro sia successivo o uguale al secondo
     *
     * @param dateFrom date-time da controllare
     * @param dateTo   date-time perno del controllo
     * @return vero se dateFrom è maggiore o uguale a dateTo, falso altrimenti
     */
    public static boolean isAfterOrEqual(LocalDateTime dateFrom, LocalDateTime dateTo) {
        return !dateFrom.isBefore(dateTo);
    }

    /**
     * Controlla che il primo parametro sia precedente o uguale al secondo
     *
     * @param timeFrom time da controllare
     * @param timeTo   time perno del controllo
     * @return vero se timeFrom è minore o uguale a timeTo, falso altrimenti
     */
    public static boolean isBeforeOrEqual(LocalTime timeFrom, LocalTime timeTo) {
        return !timeFrom.isAfter(timeTo);
    }

    /**
     * Controlla che il primo parametro sia precedente o uguale al secondo
     *
     * @param dateFrom date da controllare
     * @param dateTo   date perno del controllo
     * @return vero se dateFrom è minore o uguale a timeTo, falso altrimenti
     */
    public static boolean isBeforeOrEqual(LocalDate dateFrom, LocalDate dateTo) {
        return !dateFrom.isAfter(dateTo);
    }

    /**
     * Controlla che il primo parametro sia precedente o uguale al secondo
     *
     * @param dateFrom date-time da controllare
     * @param dateTo   date-time perno del controllo
     * @return vero se dateFrom è minore o uguale a timeTo, falso altrimenti
     */
    public static boolean isBeforeOrEqual(LocalDateTime dateFrom, LocalDateTime dateTo) {
        return !dateFrom.isAfter(dateTo);
    }

    /**
     * Controlla che un determinato time sia all'interno di un periodo orario
     *
     * @param toCheck   time da controllare
     * @param startTime time di inizio periodo, incluso
     * @param endTime   time di fine periodo, incluso
     * @return vero se toCheck è incluso nel periodo, falso altrimenti
     */
    public static boolean isBetweenPeriod(LocalTime toCheck, LocalTime startTime, LocalTime endTime) {
        return isAfterOrEqual(toCheck, startTime) && isBeforeOrEqual(toCheck, endTime);
    }

    /**
     * Controlla che una determinata data sia all'interno di un periodo
     *
     * @param toCheck   data da controllare
     * @param startDate date di inizio periodo, incluso
     * @param endDate   data di fine periodo, incluso
     * @return vero se toCheck è incluso nel periodo, falso altrimenti
     */
    public static boolean isBetweenPeriod(LocalDate toCheck, LocalDate startDate, LocalDate endDate) {
        return isAfterOrEqual(toCheck, startDate) && isBeforeOrEqual(toCheck, endDate);
    }

    /**
     * Controlla che un determinato date-time sia all'interno di un periodo
     *
     * @param toCheck     date-time da controllare
     * @param startPeriod date-time inizio periodo, incluso
     * @param endPeriod   date-time, incluso
     * @return vero se toCheck è incluso nel periodo, falso altrimenti
     */
    public static boolean isBetweenPeriod(LocalDateTime toCheck, LocalDateTime startPeriod, LocalDateTime endPeriod) {
        return isAfterOrEqual(toCheck, startPeriod) && isBeforeOrEqual(toCheck, endPeriod);
    }

    /**
     * Restituisce la data odierna per il fuso orario di default CEST+1
     *
     * @return data odierna per il fuso orario di Roma
     */
    public static LocalDate today() {
        return LocalDate.now(getDefaultTimezone());
    }

    /**
     * Restituisce il date-time del momento estratto, con il fuso orario di default CEST+1
     *
     * @return adesso
     */
    public static LocalDateTime now() {
        return LocalDateTime.now(getDefaultTimezone());
    }

    /**
     * Restituisce il fuso orario di default del sistema, ovvero quello di Roma
     *
     * @return Fuso orario di Roma
     */
    public static ZoneId getDefaultTimezone() {
        return ZoneId.of("Europe/Rome");
    }

    /**
     * Restituisce la formattazione di sistema per i date-time, ovvero yyyy-MM-dd HH:mm:ss
     *
     * @return Formattatore per i date-time
     */
    public static DateTimeFormatter getDefaultDateTimeFormatter() {
        return DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    }

    /**
     * Dato un mese ed un anno, restituisce il primo giorno del mese. Tiene conto degli anni bisestili.
     *
     * @param month mese, da 1 a 12
     * @param year  anno
     * @return primo giorno del mese
     */
    public static LocalDate getFirstDayOfMonth(int month, int year) {
        YearMonth yearMonth = getYearMonth(year, month);
        return yearMonth.atDay(1);
    }

    /**
     * Dato un mese e un anno, restituisce l'ultimo giorno del mese. Tiene conto degli anni bisestili.
     *
     * @param month mese, da 1 a 12
     * @param year  anno
     * @return ultimo giorno del mese
     */
    public static LocalDate getLastDayOfMonth(int month, int year) {
        YearMonth yearMonth = getYearMonth(year, month);
        return yearMonth.atEndOfMonth();
    }

    /**
     * Data una settimana dell'anno e un anno, restituisce il lunedì di quella settimana.
     *
     * @param week Settimana dell'anno.
     * @param year Anno.
     * @return Data del lunedì per la settimana dell'anno in ingresso.
     */
    public static LocalDate getMondayByWeekOfYear(int week, int year) {
        return today()
                .withYear(year)
                .with(WeekFields.of(Locale.ITALY).weekOfYear(), week)
                .with(DayOfWeek.MONDAY);
    }

    /**
     * Data una settimana dell'anno e un anno, restituisce la domenica di quella settimana.
     *
     * @param week Settimana dell'anno.
     * @param year Anno.
     * @return Data della domenica per la settimana dell'anno in ingresso.
     */
    public static LocalDate getSundayByWeekOfYear(int week, int year) {
        return getMondayByWeekOfYear(week, year).plusDays(6);
    }

    /**
     * Data una data in ingresso, restituisce il lunedì della stessa settimana, contando sempre all'indietro.
     * Ad esempio: la domenica viene spostata al giorno -6, e non al giorno +1; se la data è lunedì, rimane invece tale.
     *
     * @param toAdjust La data da spostare al lunedì
     * @return Lunedì della settimana in ingresso.
     */
    public static LocalDate getMondayOfWeekByDate(LocalDate toAdjust) {
        return adjustDateToDayOfWeek(toAdjust, DayOfWeek.MONDAY);
    }

    /**
     * Data una data in ingresso, restituisce la domenica della stessa settimana, contando sempre in avanti.
     * Ad esempio: se ho in ingresso un lunedì, riceverà in uscita la domenica successiva, ovvero lunedì + 6.
     *
     * @param toAdjust La data da spostare al lunedì
     * @return Domenica della settimana in ingresso.
     */
    public static LocalDate getSundayOfWeekByDate(LocalDate toAdjust) {
        return adjustDateToDayOfWeek(toAdjust, DayOfWeek.SUNDAY);
    }

    /**
     * Restituisce la settimana dell'anno dato un oggetto temporal in ingresso, che sia {@link LocalDateTime}, {@link LocalDate} o equivalente (deve supportare il campo {@link WeekFields}).
     * Nel caso in cui la settimana non cominci di lunedì, il metodo nativo di java restituisce la settimana 0; questo metodo invece, sposta i giorni
     * della settimana 0 all'ultima settimana dell'anno.
     *
     * @param temporal temporal
     * @return settimana dell'anno
     */
    public static int getWeekOfYear(Temporal temporal) {
        int weekOfYear = temporal.get(WeekFields.of(Locale.ITALY).weekOfYear());
        return weekOfYear == 0 ? 52 : weekOfYear;
    }

    /**
     * Data una data in ingresso, la modifica spostandosi sul relativo giorno della settimana. L'ordine dei giorni segue quello del {@link Locale} di default
     * di sistema, nel nostro caso quello italiano, dove la settimana va dal lunedì alla domenica. Per esempio, passando un sabato e spostando il giorno a {@link DayOfWeek#SUNDAY}
     * ci si sposta al giorno successivo; passando {@link DayOfWeek#MONDAY} ci si sposta all'inizio della settimana, quindi 5 giorni indietro.
     *
     * @param toAdjust  data da modificare
     * @param dayOfWeek giorno della settimana su cui muoversi
     * @return data modificata
     */
    public static LocalDate adjustDateToDayOfWeek(LocalDate toAdjust, DayOfWeek dayOfWeek) {
        return toAdjust.with(dayOfWeek);
    }

    /**
     * Restituisce un oggetto che rappresenta un mese di un anno specifico.
     *
     * @param year  anno
     * @param month mese
     * @return oggetto mese anno
     */
    public static YearMonth getYearMonth(int year, int month) {
        return YearMonth.of(year, Month.of(month));
    }

    /**
     * Restituisce un filtro per il test di una coppia di date all'interno di un periodo in ingresso. Restituisce true nel caso
     * in cui vi sia una qualsiasi intersezione.
     *
     * @param startPeriod inizio periodo
     * @param endPeriod   fine periodo
     * @return predicato multi-parametro
     */
    public static BiPredicate<LocalDate, LocalDate> getPredicateDatesInPeriod(@NotNull LocalDate startPeriod, @NotNull LocalDate endPeriod) {
        return (d1, d2) ->
                (d1.isBefore(startPeriod) && (d2 == null || d2.isAfter(endPeriod)))
                        ||
                        (DateUtility.isBetweenPeriod(d1, startPeriod, endPeriod))
                        ||
                        (d2 != null && DateUtility.isBetweenPeriod(d2, startPeriod, endPeriod));
    }

    /**
     * Restituisce un filtro per il test di una coppia di date-time all'interno di un periodo in ingresso. Restituisce true nel caso
     * in cui vi sia una qualsiasi intersezione.
     *
     * @param startPeriod inizio periodo
     * @param endPeriod   fine periodo
     * @return predicato multi-parametro
     */
    public static BiPredicate<LocalDateTime, LocalDateTime> getPredicateDatesInPeriod(@NotNull LocalDateTime startPeriod, @NotNull LocalDateTime endPeriod) {
        return (d1, d2) ->
                (d1.isBefore(startPeriod) && (d2 == null || d2.isAfter(endPeriod)))
                        ||
                        (DateUtility.isBetweenPeriod(d1, startPeriod, endPeriod))
                        ||
                        (d2 != null && DateUtility.isBetweenPeriod(d2, startPeriod, endPeriod));
    }

    /**
     * Data una entità che possiede metodi di getter per periodo, verifica l'intersezione della stessa nel periodo in ingresso.
     * Un esempio:
     * <pre>
     *  {@code DateUtility.entityInPeriod(now(), now(), SkillDto::getDataInizio, SkillDto::getDataFine).test(skill);}
     * </pre>
     * All'interno di uno stream:
     * <pre>
     *     {@code .filter(sk -> DateUtility.entityInPeriod(inizioPeriodo, finePeriodo, SkillDto::getDataInizio, SkillDto::getDataFine))}
     * </pre>
     *
     * @param startPeriod inizio periodo controllo, non null
     * @param endPeriod   fine periodo controllo, non null
     * @param getterStart getter inizio entità, non null
     * @param getterEnd   getter fine entità, non null
     * @param <T>         tipo entità
     * @return predicato, vero se: inizia nel periodo, finisce nel periodo, più grande del periodo; falso altrimenti.
     */
    public static <T> Predicate<T> entityInPeriod(@NotNull LocalDate startPeriod,
                                                  @NotNull LocalDate endPeriod,
                                                  @NotNull Function<T, LocalDate> getterStart,
                                                  @NotNull Function<T, LocalDate> getterEnd) {
        return Predicate.not(t -> {
            LocalDate startEntityValidity = getterStart.apply(t);
            @Nullable LocalDate endEntityValidity = getterEnd.apply(t);
            if (startPeriod.isAfter(endPeriod) || (endEntityValidity != null && startEntityValidity.isAfter(endEntityValidity)))
                throw new IllegalArgumentException("End period must be greater or equal start period.");
            return startEntityValidity.isAfter(endPeriod) || (endEntityValidity != null && endEntityValidity.isBefore(startPeriod));
        });
    }

    /**
     * Data una entità che possiede metodi di getter per periodo, verifica l'intersezione della stessa nel periodo in ingresso.
     * Un esempio:
     * <pre>
     *  {@code DateUtility.entityInPeriod(now(), now(), TurnoDto::getDataOraInizio, TurnoDto::getDataOraFine).test(turno);}
     * </pre>
     * All'interno di uno stream:
     * <pre>
     *     {@code .filter(t -> DateUtility.entityInPeriod(inizioPeriodo, finePeriodo, TurnoDto::getDataOraInizio, TurnoDto::getDataOraFine))}
     * </pre>
     *
     * @param startPeriod inizio periodo controllo, non null
     * @param endPeriod   fine periodo controllo, non null
     * @param getterStart getter inizio entità, non null
     * @param getterEnd   getter fine entità, non null
     * @param <T>         tipo entità
     * @return predicato, vero se: inizia nel periodo, finisce nel periodo, più grande del periodo; falso altrimenti.
     */
    public static <T> Predicate<T> entityInPeriod(@NotNull LocalDateTime startPeriod,
                                                  @NotNull LocalDateTime endPeriod,
                                                  @NotNull Function<T, LocalDateTime> getterStart,
                                                  @NotNull Function<T, LocalDateTime> getterEnd) {
        return Predicate.not(t -> {
            LocalDateTime startEntityValidity = getterStart.apply(t);
            @Nullable LocalDateTime endEntityValidity = getterEnd.apply(t);
            if (startPeriod.isAfter(endPeriod) || (endEntityValidity != null && startEntityValidity.isAfter(endEntityValidity)))
                throw new IllegalArgumentException("End period must be greater or equal start period.");
            return startEntityValidity.isAfter(endPeriod) || (endEntityValidity != null && endEntityValidity.isBefore(startPeriod));
        });
    }

    /**
     * Controlla che, data una collezione con funzioni di estrazione di data inizio e fine, non vi siano sovrapposizioni di periodo
     *
     * @param collection        collezione
     * @param functionDateStart getter data inizio
     * @param functionDateEnd   getter data fine
     * @param exceptionThrower  eccezione da sollevare
     * @param <T>               tipo collezione
     * @param <E>               tipo eccezione
     */
    public static <T, E extends RuntimeException> void checkOverlapDates(Collection<T> collection,
                                                                         Function<T, LocalDate> functionDateStart,
                                                                         Function<T, LocalDate> functionDateEnd,
                                                                         Supplier<E> exceptionThrower) {
        NavigableMap<LocalDate, LocalDate> mapStartEnd = new TreeMap<>();
        Map<LocalDate, LocalDate> invertedMapEndStart = new HashMap<>();
        for (var t : collection) {
            var start = functionDateStart.apply(t);
            var end = functionDateEnd.apply(t);
            if (mapStartEnd.containsKey(start)) {
                throw exceptionThrower.get();
            }
            mapStartEnd.put(start, end);
            invertedMapEndStart.put(end, start);
        }
        List<LocalDate> listEnds = mapStartEnd.values().stream()
                .sorted()
                .toList();
        for (var end : listEnds) {
            var start = invertedMapEndStart.get(end);
            var supposedStart = mapStartEnd.lowerKey(end);
            if (supposedStart != null && !start.equals(supposedStart)) {
                throw exceptionThrower.get();
            }
        }
    }

    /**
     * Controlla che, data una collezione con funzioni di estrazione di date-time di inizio e fine, non vi siano sovrapposizioni di periodo
     *
     * @param collection        collezione
     * @param functionDateStart funzione getter date-time inizio
     * @param functionDateEnd   funzione getter date-time fine
     * @param exceptionThrower  eccezione da sollevare
     * @param <T>               tipo collezione
     * @param <E>               tipo eccezione
     */
    public static <T, E extends RuntimeException> void checkOverlapDateTimes(Collection<T> collection,
                                                                             Function<T, LocalDateTime> functionDateStart,
                                                                             Function<T, LocalDateTime> functionDateEnd,
                                                                             Supplier<E> exceptionThrower) {
        NavigableMap<LocalDateTime, LocalDateTime> mapStartEnd = new TreeMap<>();
        Map<LocalDateTime, LocalDateTime> invertedMapEndStart = new HashMap<>();
        for (var t : collection) {
            var start = functionDateStart.apply(t);
            var end = functionDateEnd.apply(t);
            if (mapStartEnd.containsKey(start)) {
                throw exceptionThrower.get();
            }
            mapStartEnd.put(start, end);
            invertedMapEndStart.put(end, start);
        }
        List<LocalDateTime> listEnds = mapStartEnd.values().stream()
                .sorted()
                .toList();
        for (var end : listEnds) {
            var start = invertedMapEndStart.get(end);
            var supposedStart = mapStartEnd.lowerKey(end);
            if (supposedStart != null && !start.equals(supposedStart)) {
                throw exceptionThrower.get();
            }
        }
    }

    /**
     * Controlla che un periodo sia incluso in un altro. Gestisce i casi null.
     *
     * @param childStart  inizio periodo che deve essere incluso
     * @param childEnd    fine periodo che deve essere incluso
     * @param parentStart inizio periodo includente
     * @param parentEnd   fine periodo includente
     * @return vero se incluso, falso altrimenti
     */
    public static boolean checkInclusiveElement(@NotNull LocalDate childStart,
                                                @Nullable LocalDate childEnd,
                                                @NotNull LocalDate parentStart,
                                                @Nullable LocalDate parentEnd) {
        //not(childStart < parentStart || (parentEnd != null && (childEnd == null || childEnd > parentEnd))
        return !checkNotInclusive(childStart, childEnd).test(parentStart, parentEnd);
    }

    /**
     * @param childStart
     * @param childEnd
     * @return
     */
    public static BiPredicate<LocalDate, LocalDate> checkNotInclusive(@NotNull LocalDate childStart, @Nullable LocalDate childEnd) {
        return (parentStart, parentEnd) -> childStart.isBefore(parentStart)
                || parentEnd != null && (childEnd == null || childEnd.isAfter(parentEnd));
    }

}
