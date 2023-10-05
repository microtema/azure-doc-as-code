package de.microtema.docascode.update;

import org.apache.commons.io.FileUtils;

import java.io.File;

public class MergeTemplates {

    public static void main(String[] args) throws Exception {

        GenerateReadmeTemplate generateReadmeTemplate = new GenerateReadmeTemplate();

        File fromFolder = new File(args[0]);
        File toFolder = new File(args[1]);

        System.out.println("Merge Templates from ./" + fromFolder.getName() + " to ./" + toFolder.getName());

        FileUtils.deleteDirectory(toFolder);

        for (File file : fromFolder.listFiles()) {

            FileUtils.copyDirectoryToDirectory(file, toFolder);
        }

        for (File file : toFolder.listFiles()) {

            String serviceName = file.getName();

            renameFiles(file, serviceName);

            generateReadmeTemplate.execute(serviceName + ".md", file);
        }
    }

    private static void renameFiles(File fileOrFolder, String serviceName) {

        if (fileOrFolder.isFile()) {

            String fileName = fileOrFolder.getName();
            String newFileName = fileName.replace(".md", String.format("[%s].md", serviceName));

            fileOrFolder.renameTo(new File(fileOrFolder.getParentFile(), newFileName));
        } else {

            for (File file : fileOrFolder.listFiles()) {
                renameFiles(file, serviceName);
            }
        }
    }
}
