package sun.net.httpserver;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        ExchangeImpl httpServer = new ExchangeImpl(null,null, null, 0, null);
    }
}
