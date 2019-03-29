public class hashDriver
{
    public static void main(String[] args)
    {
        ///System.out.println("Hello world");
        int size = 13;
        LinearProbingHashTable<String, Integer> lpht = new LinearProbingHashTable<>(size);
        lpht.insert("Arjun", 5);
        lpht.insert("Aahlad", 12);
        lpht.insert("Jonah", 6);
        //lpht.insert("Ligature", 9);
        lpht.insert("Ligature", 9);

        lpht.delete("Ligature");

        System.out.println(lpht.find("NotInHash"));
        System.out.println(lpht.find("Ligature"));
        System.out.println(lpht.getHashValue("Arjun"));
        System.out.println(lpht.getLocation("Arjun"));
        System.out.print(lpht);
    }
}