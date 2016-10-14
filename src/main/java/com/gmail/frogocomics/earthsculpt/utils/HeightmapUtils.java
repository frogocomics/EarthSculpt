package com.gmail.frogocomics.earthsculpt.utils;


import com.gmail.frogocomics.earthsculpt.core.Constants;
import com.gmail.frogocomics.earthsculpt.core.ProjectSettings;
import com.gmail.frogocomics.earthsculpt.core.data.HeightField;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 *
 *
 * @since 0.0.1
 * @author Jeff Chen
 */
public final class HeightmapUtils {

    private HeightmapUtils() {
    }

    public static File save(final HeightField field) throws IOException, ExecutionException, InterruptedException {
        ExecutorService service = Executors.newSingleThreadExecutor();
        Callable<File> callable = new Callable<File>() {
            @Override
            public File call() throws Exception {
                //For example: C:\Users\BobJones\Documents\EarthSculpt\Temp\FEFSvcsdfsd.hm
                File outputLocation = new File(Constants.ROOT_DIRECTORY.getAbsolutePath() + "\\Temp\\" + UUID.randomUUID().toString().replaceAll("\\-", "_") + ".hm");
                if(!outputLocation.exists()) {
                    outputLocation.getParentFile().mkdirs();
                    if(!ProjectSettings.COMPRESS_DATA) {
                        outputLocation.createNewFile();
                    }
                }
                if(ProjectSettings.COMPRESS_DATA) {
                    File newOutputLocation = new File(outputLocation.getAbsolutePath() + "z");
                    ObjectOutputStream object = new ObjectOutputStream(new GZIPOutputStream(new FileOutputStream(newOutputLocation)));
                    object.writeObject(field);
                    object.close();
                    return newOutputLocation;
                } else {
                    ObjectOutputStream object = new ObjectOutputStream(new FileOutputStream(outputLocation));
                    object.writeObject(field);
                    object.close();
                    return outputLocation;
                }
            }
        };
        Future<File> future = service.submit(callable);
        service.shutdown();
        return future.get();
    }

    public static HeightField load(File location) throws IOException, ClassNotFoundException {
        ObjectInputStream stream = new ObjectInputStream(new FileInputStream(location));
        Object o = stream.readObject();
        stream.close();
        return (HeightField) o;
    }

    public static HeightField loadZip(File location) throws IOException, ClassNotFoundException {
        ObjectInputStream stream = new ObjectInputStream(new GZIPInputStream(new FileInputStream(location)));
        Object o = stream.readObject();
        stream.close();
        return (HeightField) o;
    }

}
