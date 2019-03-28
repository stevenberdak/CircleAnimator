package app;

import java.io.File;
import java.io.RandomAccessFile;
import java.nio.channels.FileLock;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SingleInstanceService {

    public static boolean lockFile(final String lockFile, final Logger logger) {
        try {
            final File file = new File(lockFile);
            final RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rw");
            final FileLock fileLock = randomAccessFile.getChannel().tryLock();
            if (fileLock != null) {
                Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                    try {
                        fileLock.release();
                        randomAccessFile.close();
                        file.delete();
                    } catch (Exception e) {
                        logger.log(Level.WARNING, "remove_lock_file", new String[] {lockFile, e.toString()});
                    }
                }));
                return true;
            }
        } catch (Exception e) {
            logger.log(Level.WARNING, "create_lock_file", new String[] {lockFile, e.toString()});
        }
        return false;
    }
}
