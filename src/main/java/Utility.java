import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.function.UnaryOperator;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Utility {

    /**
     * Genera una collezione applicando a un valore di partenza una funzione che restituisce lo stesso tipo del valore iniziale.
     * <br>
     * Ad esempio:
     * {@code List<Integer> listZeroFill = utils.generateCollection(0, v -> 0, 97, Collectors.toList());}
     *
     * @param initialValue    valore iniziale
     * @param toApply         funzione che trasforma il tipo iniziale in un elemento dello stesso tipo
     * @param numberIteration numero totale di iterazioni, inclusivo
     * @param collector       collettore
     * @param <T>             tipo in ingresso
     * @param <A>             tipo accumulatore
     * @param <Z>             collezione in uscita
     * @return collezione
     */
    public static <T, A, Z> Z generateCollection(T initialValue, UnaryOperator<T> toApply, int numberIteration, Collector<T, A, Z> collector) {
        return Stream.iterate(initialValue, toApply)
                .limit(numberIteration)
                .collect(collector);
    }

    /**
     * Restituisce la sottomappa di una mappa, dato un insieme di chiavi
     *
     * @param keys chiavi
     * @param map  mappa
     * @param <T>  tipo chiavi
     * @param <K>  tipo valori
     * @return sottomappa
     */
    public static <T, K> Map<T, K> getSubMap(Collection<T> keys, Map<T, K> map) {
        return map.entrySet().stream()
                .filter(e -> keys.contains(e.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    /**
     * Controlla che due collezioni abbiano almeno un elemento in comune
     *
     * @param collection_1 collezione 1
     * @param collection_2 collezione 2
     * @param <T>          tipo generico
     * @return true se hanno almeno un elemento in comune, falso altrimenti
     */
    public static <T> boolean haveAtLeastOneElementInCommon(Collection<T> collection_1, Collection<T> collection_2) {
        return !Collections.disjoint(collection_1, collection_2);
    }

}
