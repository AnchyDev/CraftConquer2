package net.anchy.craftconquer.util;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class WeightBag<T>
{
    private List<Entry<T>> entries;
    private double accumulatedWeight;
    private Random random;

    public WeightBag()
    {
        entries = new ArrayList<Entry<T>>();
        random = new Random();
    }

    public void addItem(T item, double weight)
    {
        accumulatedWeight += weight;

        var entry = new Entry(item, accumulatedWeight);
        entries.add(entry);
    }

    public T getItem()
    {
        double rand = random.nextDouble() * accumulatedWeight;

        for(var entry : entries)
        {
            if(entry.accumulatedWeight >= rand)
            {
                return entry.item;
            }
        }

        return null;
    }

    private class Entry<T>
    {
        @Getter @Setter
        private double accumulatedWeight;

        @Getter @Setter
        private T item;

        public Entry(T item, double weight)
        {
            this.item = item;
            this.accumulatedWeight = weight;
        }
    }
}
