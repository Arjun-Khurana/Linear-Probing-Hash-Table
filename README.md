# Linear Probing Hash Table
### Arjun Khurana | axk172230 | CS3345 | 28 March 2019
---
## Imports
---
StringBuilder is used for the toString() method.

```java
import java.lang.StringBuilder;
```
## Class LinearProbingHashTable
---
Class LinearProbingHashTable contains a nested class Entry
which contains the data for each element in the hash table.
K and V are generics which refer to the generic types of the LinearProbingHashTable class.
```java
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
```java
private Entry<K, V> table[];
```
The constructor takes as input the desired starting size of the hash table and defines the table property with the size.
```java
LinearProbingHashTable(int size)
{
    this.table = new Entry[size];
}
```
The insert method takes as input a key and a value to be inserted. The method calls the find() method to check if the key already exists in the table. If it does, the method returns false (no insertion of duplicates). Next, the method checks if the table is half full. If so, the table is rehashed. Next, the table calls the getHashValue() function to get an initial table location for the key. The method checks if that location is filled, and if so, linearly propagates to an open location. Finally, the method constructs a new table entry with the key and value, populates the table, and returns true.
```java
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
The find() method takes as input a key. The method calls the getLocation() method to get the location of the key. If the getLocation() method returns a valid value (not -1), the key at that location matches the passed key, and the entry is not deleted, the method returns the value at that table location. Else, the method returns null. 
```java
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
The delete() method takes as input a key. The method calls the find() method to make sure the key to be deleted is in the table. If it isn't present, the method returns false. Otherwise, the method marks the appropriate entry as deleted by calling the getLocation() method on the passed key and setting the deleted boolean on that entry, then returning true to indicate a successful delete.
```java
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
The private isHalfFull() method checks whether the load factor in the hash table is greater than 0.5. The method iterates over the length of the table, counting all the non-null entries. The load factor is the number of non-null entries divided by the tablesize. The method returns the boolean result of the greater or equal comparison between the load factor and 0.5.  
```java
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
```
The private rehash() method doubles the tablesize and rehashes every non-deleted element present in the original table. The method uses the clone() method for arrays to save the data in the old table, before setting the table property of the LinearProbingHashTable class to be a new table of doubled size. The method iterates over the old table and rehashes every non-deleted entry into the new table.
```java
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
```
The getHashValue() method takes as input a key. The method calls Java's inbuilt hashCode(), mod the tablesize. If the hashCode() resulted in a negative number, the method adds the tablesize to the hash value.
```java
public int getHashValue(K key)
{
    int h = key.hashCode() % this.table.length;
    if (h < 0)
    {
        h += this.table.length;
    }
    return h;
}
```
The getLocation() method takes as input a key. The method calls the getHashValue() method to get the initial location of the key, then linearly probes the table until it encounters a null, checking each key for a match to the passed key. If found, the method returns the location of the key, else it returns a flag value, -1.
```java
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
The toString() method uses StringBuilder to construct a string representation of the hash table. This is called when the print() or println() methods are called on the hash table object.
```java
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