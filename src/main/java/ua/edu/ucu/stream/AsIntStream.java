package ua.edu.ucu.stream;
import ua.edu.ucu.function.*;

import java.util.Arrays;

public class AsIntStream implements IntStream {

    private final int [] stream;

    private AsIntStream(int[] stream){
        this.stream = Arrays.copyOf(stream, stream.length);
    }

    public static IntStream of(int... values) {
        return new AsIntStream(Arrays.copyOf(values, values.length));
    }

    @Override
    public Double average() {
        if (isEmpty()) {
            throw new IllegalArgumentException();
        }
        int sum = sum();
        return (double) (sum/stream.length);
    }

    @Override
    public Integer max() {
        if (isEmpty()) {
            throw new IllegalArgumentException();
        }
        int max = 0;
        for (int element: stream) {
            if (element > max) {
                max = element;
            }
        }
        return max;
    }

    @Override
    public Integer min() {
        if (isEmpty()) {
            throw new IllegalArgumentException();
        }
        int min = stream[0];
        for (int element: stream) {
            if (element < min) {
                min = element;
            }
        }
        return min;
    }

    @Override
    public long count() {
        int counter = 0;
        for (Integer element: stream) {
            if (element != null) {
                counter += 1;
            }
        }
        return counter;
    }

    @Override
    public Integer sum() {
        if (isEmpty()) {
            throw new IllegalArgumentException();
        }
        int sum = 0;
        for (Integer element: stream) {
            sum += element;
        }
        return sum;
    }

    @Override
    public IntStream filter(IntPredicate predicate) {
        int [] values = new int[stream.length];
        int ind = 0;
        for (int i = 0; i < stream.length; i++) {
            if (predicate.test(stream[i])) {
                values[ind] = stream[i];
                ind += 1;
            }
        }
        int [] resultArray = Arrays.copyOf(values, ind);
        return of(resultArray);
    }

    @Override
    public void forEach(IntConsumer action) {
        for (Integer element: stream) {
            action.accept(element);
        }
    }

    @Override
    public IntStream map(IntUnaryOperator mapper) {
        int [] resultArray = new int[stream.length];
        int index = 0;
        for (Integer element: stream) {
            resultArray[index] = mapper.apply(element);
            index += 1;
        }
        return of(resultArray);
    }

    @Override
    public IntStream flatMap(IntToIntStreamFunction func) {
        IntStream [] totalStream = new IntStream[stream.length];
        int mappedSize = 0;
        int index = 0;
        for (Integer element: stream) {
            IntStream mappedStream = func.applyAsIntStream(element);
            totalStream[index] = mappedStream;
            mappedSize += mappedStream.count();
            index += 1;
        }

        int [] resultArray = new int[mappedSize];
        mappedSize = 0;
        for (IntStream mappedStream: totalStream) {
            System.arraycopy(mappedStream.toArray(),
                    0, resultArray, mappedSize,
                    (int) mappedStream.count());
            mappedSize += mappedStream.count();
        }
        return of(resultArray);
    }

    @Override
    public int reduce(int identity, IntBinaryOperator op) {
        for (Integer element: stream) {
            identity = op.apply(identity, element);
        }
        return identity;
    }

    @Override
    public int[] toArray() {
        return Arrays.copyOf(stream, stream.length);
    }

    public boolean isEmpty() {
        return stream.length == 0;
    }

}
