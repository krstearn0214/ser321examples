import java.util.List;
import java.util.ArrayList;

class StringList {
    
    List<String> strings = new ArrayList<String>();

    public void add(String str) {
        int pos = strings.indexOf(str);
        if (pos < 0) {
            strings.add(str);
        }
    }

    public boolean contains(String str) {
        return strings.indexOf(str) >= 0;
    }

    public int size() {
        return strings.size();
    }

    public String toString() {
        return strings.toString();
    }

    public void remove(int index)
    {
        if(index > strings.size())
        {
            //implement exception check
        }
        else
        {
        strings.remove(index);
        }
    }

    public List<Integer> count()
    {
        List<Integer> charCount = new ArrayList<>();
        for(int i = 0; i < strings.size(); i++)
        {
            charCount.add(strings.get(i).length());
        }
        return charCount;
    }

    public void reverse(int index)
    {
        if(index > strings.size())
        {
            //implement exception check
        }
        else
        {
        StringBuilder flip = new StringBuilder();
        flip.append(strings.get(index));
        flip = flip.reverse();
        strings.set(index, flip.toString());
        }
    }
}
