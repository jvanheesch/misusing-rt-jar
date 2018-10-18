package sun.nio.ch;

import java.io.FileDescriptor;
import java.nio.channels.spi.SelectorProvider;

public class MySourceChannel extends SourceChannelImpl {
    public MySourceChannel(SelectorProvider sp, FileDescriptor fd) throws ClassNotFoundException {
        super(sp, fd);
    }
}
