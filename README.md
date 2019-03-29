# Linear Probing Hash Table
### Arjun Khurana | axk172230 | CS3345 | 28 March 2019
---
## Imports
---
StringBuilder used for toString() method

```
import java.lang.StringBuilder;
```
## Class LinearProbingHashTable
---
Class LinearProbingHashTable contains a nested class Entry
which contains the data for each element in the hash table.
K and V are generics which refer to the generic types of the LinearProbingHashTable class.
```
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
```
The data in the hash table is contained within an array of type Entry<K, V>. Because there is no way to initialize an array of generic type in Java, this array is only declared, and defined in the constructor.
``` 
private Entry<K, V> table[];
```
The constructor takes as input the desired starting size of the hash table and defines the table property with the size.
```
LinearProbingHashTable(int size)
{
    this.table = new Entry[size];
}
```

```
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
    while (table[bucket] != null)
    {
        bucket = (bucket + 1) % this.table.length;
    }
    table[bucket] = new Entry<K, V>(key, value);
    return true;
}
```
```
public V find(K key)
{
    int L = getLocation(key);
    if (L != -1 && this.table[L].key == key && !this.table[L].deleted)
    {
        return this.table[L].val;
    }
    return null;
}
```
```
public boolean delete(K key)
{
    if (find(key) != null)
    {
        this.table[getLocation(key)].deleted = true;
        return true;
    }
    return false;
}
```
```
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
    if (count/this.table.length >= 0.5)
    {
        return true;
    }
    else
    {
        return false;
    }
}
```
```
private void rehash()
{
    Entry<K, V> oldtable[] = this.table.clone();
    Entry<K, V> newtable[];
    newtable = new Entry[oldtable.length * 2];
    this.table = newtable;
    for (int i = 0; i < oldtable.length; i++)
    {
        if (oldtable[i] != null && !oldtable[i].deleted)
        {
            insert(oldtable[i].key, oldtable[i].val);
        }
    }
}
```
```
public int getHashValue(K key)
{
    int h = key.hashCode();
    // h ^= (h >>> 20) ^ (h >>> 12);
    // h ^= (h >>> 7) ^ (h >>> 4);
    h = h % this.table.length;
    if (h < 0)
    {
        h += this.table.length;
    }
    return h;
}
```
```
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
```
```
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
```