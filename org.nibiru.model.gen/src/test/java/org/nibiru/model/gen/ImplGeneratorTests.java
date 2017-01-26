package org.nibiru.model.gen;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ImplGeneratorTests {
    private ImplGenerator generator;

    @Before
    public void setUp() {
        generator = new ImplGenerator();
    }

    @Test
    public void directoryTest() throws Exception {
        String code = generator.generate(Directory.class);

        assertEquals("package org.nibiru.model.gen;\n" +
                "\n" +
                "import java.lang.Override;\n" +
                "import java.lang.String;\n" +
                "import org.nibiru.model.core.api.Value;\n" +
                "\n" +
                "public class DirectoryImpl implements Directory {\n" +
                "  final Value<String> name;\n" +
                "\n" +
                "  @Override\n" +
                "  public String getName() {\n" +
                "    return name.get();\n" +
                "  }\n" +
                "\n" +
                "  @Override\n" +
                "  public void setName(String name) {\n" +
                "    this.name.set(name);\n" +
                "  }\n" +
                "}\n", code);
    }

    @Test
    public void projectTest() throws Exception {
        String code = generator.generate(Project.class);

        assertEquals("package org.nibiru.model.gen;\n" +
                "\n" +
                "import java.lang.Override;\n" +
                "import java.lang.String;\n" +
                "import org.nibiru.model.core.api.Value;\n" +
                "\n" +
                "public class ProjectImpl implements Project {\n" +
                "  final Value<Directory> root;\n" +
                "\n" +
                "  final Value<String> name;\n" +
                "\n" +
                "  @Override\n" +
                "  public Directory getRoot() {\n" +
                "    return root.get();\n" +
                "  }\n" +
                "\n" +
                "  @Override\n" +
                "  public void setRoot(Directory root) {\n" +
                "    this.root.set(root);\n" +
                "  }\n" +
                "\n" +
                "  @Override\n" +
                "  public String getName() {\n" +
                "    return name.get();\n" +
                "  }\n" +
                "\n" +
                "  @Override\n" +
                "  public void setName(String name) {\n" +
                "    this.name.set(name);\n" +
                "  }\n" +
                "}\n", code);
    }
}
