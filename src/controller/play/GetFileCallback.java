package controller.play;

import java.io.File;

@FunctionalInterface
public interface GetFileCallback {
    File call();
}
