public class Request {

    private static int idCount = 0;

    private int length;
    private int id;

    /**
     * Simple container class for requests.
     * @param length The length of this request in milliseconds.
     */
    public Request(int length) {
        this.length = length;
        id = idCount++;
    }

    public int getLength() {
        return length;
    }

    public int getId() {
        return id;
    }

}
