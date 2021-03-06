import java.lang.StringBuilder;

public class LinearProbingHashTable<K,V>
{
    private static class Entry<K,V>
    {
        K key;
        V val;
        boolean deleted = false;

        Entry(K k, V v)
        {
            this.key = k;
            this.val = v;
        }
    }

    private Entry<K, V> table[];
    
    LinearProbingHashTable(int size)
    {
        this.table = new Entry[size];
    }

    public boolean insert(K key, V value)
    {
        if (find(key) != null)
        {
            return false;
        }
        if (isHalfFull())
        {
            rehash();
        }
        int bucket = getHashValue(key);
        while (table[bucket] != null && !table[bucket].deleted)
        {
            bucket = (bucket + 1) % this.table.length;
        }
        table[bucket] = new Entry<K, V>(key, value);
        return true;
    }

    public V find(K key)
    {
        int L = getLocation(key);
        if (L != -1 && this.table[L].key == key && !this.table[L].deleted)
        {
            return this.table[L].val;
        }
        return null;
    }

    public boolean delete(K key)
    {
        if (find(key) != null)
        {
            this.table[getLocation(key)].deleted = true;
            return true;
        }
        return false;
    }

    private boolean isHalfFull()
    {
        double count = 0;
        for (int i = 0; i < this.table.length; i++)
        {
            if (this.table[i] != null)
            {
                count++;
            }
        }
        double lambda = count/this.table.length;
        return lambda >= 0.5;
    }

    private void rehash()
    {
        Entry<K, V> oldtable[] = this.table.clone();
        Entry<K, V> newtable[];
        newtable = new Entry[oldtable.length * 2];
        this.table = newtable;
        for (Entry<K,V> e : oldtable)
        {
            if (e != null && !e.deleted)
            {
                insert(e.key, e.val);
            }
        }
    }

    public int getHashValue(K key)
    {
        int h = key.hashCode() % this.table.length;
        if (h < 0)
        {
            h += this.table.length;
        }
        return h;
    }

    public int getLocation(K key)
    {
        int h = getHashValue(key);
        while (this.table[h] != null)
        {
            if (this.table[h].key == key)
            {
                return h;
            }
            h++;
        }
        return -1;
    }
    //hello world
    public String toString()
    {
        StringBuilder sb = new StringBuilder("HASH TABLE\n----------\n");
        for (int i = 0; i < this.table.length; i++)
        {
            sb.append(i + ": ");
            //System.out.println(this.table[i])
            if (this.table[i] != null)
            {
                sb.append(table[i].key.toString() + ", " + table[i].val.toString());
                if (this.table[i].deleted)
                {
                    sb.append(" (deleted)");
                }
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
