package org.nibiru.model.gen;

import org.junit.Test;

public class ImplGeneratorTests extends BaseTest {
    @Override
    Generator buildGenerator() {
        return new ImplGenerator();
    }

    @Test
    public void directoryTest() throws Exception {
        testCode(Directory.class,
                "package org.nibiru.model.gen;\n" +
                "\n" +
                "import java.lang.Override;\n" +
                "import java.lang.String;\n" +
                "import org.nibiru.model.core.api.Value;\n" +
                "\n" +
                "public class DirectoryImpl implements Directory {\n" +
                "  final Value<String> name = org.nibiru.model.core.impl.java.JavaValue.of((String)null);\n" +
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
                "}\n");
    }

    @Test
    public void projectTest() throws Exception {
        testCode(Project.class,
                "package org.nibiru.model.gen;\n" +
                "\n" +
                "import java.lang.Override;\n" +
                "import java.lang.String;\n" +
                "import org.nibiru.model.core.api.Value;\n" +
                "\n" +
                "public class ProjectImpl implements Project {\n" +
                "  final Value<Directory> root = new org.nibiru.model.gen.DirectoryValue();\n" +
                "\n" +
                "  final Value<String> name = org.nibiru.model.core.impl.java.JavaValue.of((String)null);\n" +
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
                "}\n");
    }
}
