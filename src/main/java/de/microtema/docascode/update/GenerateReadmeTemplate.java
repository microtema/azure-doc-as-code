package de.microtema.docascode.update;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;


public class GenerateReadmeTemplate {

    public void execute(String templateName, File docDir) {

        System.out.println("Merge templates " + templateName + " to " + docDir.getName());

        File[] templateFiles = docDir.listFiles((File dir, String name) -> name.endsWith(".md"));

        List<File> orderedTemplateFiles = Arrays.asList(Objects.requireNonNull(templateFiles));

        orderedTemplateFiles.sort(Comparator.comparing(o -> getFileIndex(o.getName())));

        StringBuilder stringBuilder = new StringBuilder();

        for (File templateFile : templateFiles) {

            String template = importTemplate(templateFile)
                    .replace("images/", docDir.getName() + "/images/");

            stringBuilder.append(template).append(lineSeparator(2));
        }

        writeFile(new File(docDir, templateName), stringBuilder.toString());

    }

    private String importTemplate(File template) {

        try {
            return FileUtils.readFileToString(template, Charset.defaultCharset());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void writeFile(File outputFile, String fileContent) {

        try {
            FileUtils.writeStringToFile(outputFile, fileContent, Charset.defaultCharset());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String lineSeparator(int lines) {

        int index = 0;

        StringBuilder str = new StringBuilder();

        while (index++ < lines) {
            str.append(System.lineSeparator());
        }

        return str.toString();
    }

    private String getFileIndex(String fileName) {

        return fileName.split("-")[0];
    }
}
