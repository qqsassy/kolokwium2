import java.util.*; //java.util.*: Importuje wszystkie klasy z pakietu java.util.
import java.util.function.Predicate; //java.util.function.Predicate: Importuje interfejs Predicate do definiowania warunków.
import java.util.stream.Collectors; //java.util.stream.Collectors: Importuje klasę Collectors do operacji na strumieniach.
import java.util.stream.Stream; //java.util.stream.Stream: Importuje klasę Stream do pracy ze strumieniami.

public class CustomList<T> extends AbstractList<T> { //CustomList<T>: Generyczna klasa dziedzicząca po AbstractList<T>.
    class Node { //Node: Klasa wewnętrzna reprezentująca węzeł listy jednokierunkowej.
        T value;
        Node next;
    }

    Node start;
    Node end;
    //Node start, end: Wskaźniki na pierwszy i ostatni węzeł listy.

    //Konstruktor CustomList(): Inicjalizuje pustą listę.
    public CustomList() {
        start = end = null;
    }

    @Override
    public int size() { //Zwraca rozmiar listy, iterując przez węzły od start do end.
        if(start == end) {
            return 0;
        } else {
            int counter = 1;
            Node temp = start;
            while(temp != end) {
                temp = temp.next;
                counter++;
            }
            return counter;
        }
    }

    @Override
    public T get(int index) { //Zwraca element na podanej pozycji index, iterując przez listę.
        if((start == null) || (size() <= index)) {
            throw new NoSuchElementException();
        }
        Node temp = start;
        for(int j = 0 ; j < index ; j++) {
            temp = temp.next;
        }
        return temp.value;
    }

    public void addLast(T value) { //Dodaje nowy element na końcu listy.
        Node newNode = new Node();
        newNode.value = value;
        newNode.next = null;

        if(start == null) {
            start = end = newNode;
        } else {
            end.next = newNode;
            end = newNode;
        }
    }

    public T getLast() { //Zwraca ostatni element listy.
        if(end == null) {
            throw new NoSuchElementException();
        }
        return end.value;
    }

    public void addFirst(T value) { //Dodaje nowy element na początku listy.
        Node newNode = new Node();
        newNode.value = value;
        newNode.next = start;

        if(start == null) {
            start = newNode;
            end = newNode;
        } else {
            start = newNode;
        }
    }
    public T getFirst() { //Zwraca pierwszy element listy.
        if(start == null) {
            throw new NoSuchElementException();
        }
        return start.value;
    }

    public T removeFirst() { //Usuwa pierwszy element z listy i zwraca jego wartość.
        if (start == null) {
            throw new NoSuchElementException();
        }
        T startValue = start.value;
        if(start == end) {
            start = end = null;
            return startValue;
        } else {
            start = start.next;
            return startValue;
        }
    }

    public T removeLast() { //Usuwa ostatni element z listy i zwraca jego wartość.
        if (start == null) {
            throw new NoSuchElementException();
        }
        T endValue = end.value;
        if(start == end) {
            start = null;
            end = null;
            return endValue;
        } else {
            Node temp = start;
            while(temp.next != end) {
                temp = temp.next;
            }
            temp.next = null;
            end = temp;
        }
        return endValue;
    }

    public boolean add(T t) { //Dodaje nowy element na końcu listy (nadpisuje metodę z AbstractList).
        addLast(t);
        return true;
    }

    public Iterator<T> iterator() { //Zwraca iterator do listy.
        return new Iterator<T>() {
            Node temp = start;
            @Override
            public boolean hasNext() {
                return temp != null;
            }

            @Override
            public T next() {
                T value = temp.value;
                temp = temp.next;
                return value;
            }
        };
    }

    public Stream<T> stream() { //Tworzy strumień z elementów listy.
        Stream.Builder<T> streamBuilder = Stream.builder();
        for(T item : this) {
            streamBuilder.accept(item);
        }
        return streamBuilder.build();
    }

    public static <T> List<T> filterByClass(List<T> list, Class<? extends T> clazz) { //Filtruje listę, zwracając tylko elementy danego typu clazz.
        List<T> filteredList = new CustomList<>();
        for(T obj : list) {
            if(clazz.isInstance(obj)) {
                filteredList.add(obj);
            }
        }
        return filteredList;
    }

    public static <T extends Comparable<T>> Predicate<T> isInBounds(T lowerBound, T upperBound) { //Zwraca predykat sprawdzający, czy element jest w zadanym zakresie.
        return num -> num.compareTo(lowerBound) >= 0 && num.compareTo(upperBound) <= 0;
    }

    public static <T extends Comparable<T>> int countElements(List<T> list, T lowerBound, T upperBound) { //Liczy elementy w zadanym zakresie lowerBound i upperBound.
        List<T> filteredList = list
                .stream()
                .filter(CustomList.isInBounds(lowerBound, upperBound))
                .toList();
        CustomList<T> resultList = new CustomList<>();
        resultList.addAll(filteredList);
        return resultList.size();
    }

    public static <T extends Number> Comparator<T> compareSums(Collection<T> a, Collection<T> b) { //Zwraca komparator porównujący sumy elementów w dwóch kolekcjach.
        return Comparator.comparingDouble(t -> a.stream().mapToDouble(Number::doubleValue).sum() - b.stream().mapToDouble(Number::doubleValue).sum());
    }
}

//Klasa CustomList implementuje listę jednokierunkową z podstawowymi operacjami takimi jak dodawanie, usuwanie, iterowanie i strumieniowanie elementów. Zawiera również metody statyczne do filtrowania, liczenia i porównywania elementów listy.