package pages;

public class MyQueue extends MyList {
    public void push(int item) {
        int[] newData = new int[data.length + 1];
        newData[0] = item;
        for (int i = 0; i < data.length; i++) {
            newData[i + 1] = data[i];
        }
        data = newData;
    }
}
