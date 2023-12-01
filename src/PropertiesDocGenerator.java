import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class PropertiesDocGenerator {
    public static void main(String[] args) throws IOException {
        String path = args[0];

        System.out.println("# .properties Files autogerated Documentation");
        System.out.println("");
        System.out.println("## File Descriptions");

        // Read file names from a folder
        File folder = new File(path);
        File[] listOfFiles = folder.listFiles();
        for (File file : listOfFiles) {
            if (file.isFile()) {
                System.out.println("");
                String fileName = file.getName();
                System.out.println(String.format("### `TrConfig/%s`", fileName));
                System.out.println(String.format("Description of %s file.", fileName));
                System.out.println("#### Properties:");

                List<String> lines = Files.readAllLines(Paths.get(path + "\\" + fileName));
                int i = 0;
                String desc = "";
                for (String line : lines) {
                    try {
                        ++i;

                        if (line.isEmpty()) {
                            continue;
                        } else if (line.startsWith("#")) {
                            // desc += line.replace("#", "").trim();
                            continue;
                        } else if (line.contains("=")) {
                            if (desc.isEmpty()) {
                                desc = "<Add description here>";
                            }

                            if (!desc.endsWith(".")) {
                                desc += ".";
                            }

                            String[] property = line.split("=");
                            if (property.length > 1) {
                                System.out.println(
                                        String.format("- `%s`: %s Example:`%s`",
                                                property[0], desc, property[1]));
                            } else {
                                System.out.println(
                                        String.format("- `%s`: %s Example:`%s`",
                                                property[0], desc, ""));
                            }
                            desc = "";
                        } else {
                            throw new RuntimeException(String.format("Not sure what line %d means\n%s", i, line));
                        }
                    } catch (Exception e) {
                        throw new RuntimeException(String.format("Not sure what's wrong with line %d\n%s", i, line));
                    }
                }
            }
        }
    }
}