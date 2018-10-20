public class NotAllPublicClassesWorkExample {
    /**
     * [.../src/main/java] javac NotAllPublicClassesWorkExample.java
     * result:
     * - warning on line sun.font.AttributeMap
     * - error on line sun.net.httpserver.AuthFilter
     * Explanation: https://stackoverflow.com/a/4070685/1939921
     * Compilation succeeds if we add '-XDignore.symbol.file'.
     */
    public static void main(String[] args) {
        sun.font.AttributeMap test = new sun.font.AttributeMap(null);
        sun.net.httpserver.AuthFilter fail;
    }
}
