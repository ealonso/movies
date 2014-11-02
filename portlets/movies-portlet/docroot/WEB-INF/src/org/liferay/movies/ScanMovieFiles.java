/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package org.liferay.movies;

import com.liferay.portal.kernel.util.*;

import java.io.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * @author Eudaldo Alonso
 */
public class ScanMovieFiles {

    public static void main(String[] args) {
        Reader reader = null;

        try {
            Properties properties = new SortedProperties();

            reader = new FileReader(args[0]);

            properties.load(reader);

            String[] directories = StringUtil.split(properties.getProperty("movies.dir"));

            List<String> movies = new ArrayList<String>();

            for (String directory : directories) {
                File file = new File(directory);

                if (!file.isDirectory()) {
                    continue;
                }

                listFilms(file, movies);
            }

            ListUtil.sort(movies);

            Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("movies.txt"), "UTF-8"));

            try {
                for(String movie: movies) {
                    out.write(movie);
                    out.write("\n");
                }
            }
            finally {
                out.close();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            if (reader != null) {
                try {
                    reader.close();
                }
                catch (IOException ioe) {
                    ioe.printStackTrace();
                }
            }
        }
    }

    protected static void listFilms(File file, List<String> movies) {
        File[] files = file.listFiles(new FilmFileFilter());

        if ((files == null) || (files.length <= 0)) {
            return;
        }

        for (File curFile : files) {
            if (curFile.isDirectory()) {
                listFilms(curFile, movies);
            }

            if (curFile.isFile()) {
                String film = cleanFileName(curFile.getName());

                movies.add(film);
            }
        }
    }

    protected static String cleanFileName(String name) {
        int end = name.lastIndexOf(CharPool.PERIOD);

        name = name.substring(0, end);

        name = name.replace(CharPool.PERIOD, CharPool.SPACE);

        name = name.replaceAll("\\(.+?\\)", StringPool.BLANK);

        name = name.replaceAll("\\[.+?\\]", StringPool.BLANK);

        return name;
    }

    private final static String[] extensions = {".avi", ".mp4"};

    protected static class FilmFileFilter implements FileFilter {

        @Override
        public boolean accept(File file) {
            if (file.isDirectory()) {
                return true;
            }

            String path = file.getName().toLowerCase();

            for (String extension : extensions) {
                if (path.endsWith(extension)) {
                    return true;
                }
            }

            return false;
        }
    }

}