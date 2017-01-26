package org.nibiru.model.gen;

import org.junit.Test;

public class TypeGeneratorTests extends BaseTest {
    @Override
    Generator buildGenerator() {
        return new TypeGenerator();
    }

    @Test
    public void directoryTest() throws Exception {
        testCode(Directory.class,
                "package org.nibiru.model.gen;\n" +
                "\n" +
                "import java.lang.String;\n" +
                "import org.nibiru.model.core.impl.BaseComplexType;\n" +
                "\n" +
                "public class DirectoryType extends BaseComplexType<Directory> {\n" +
                "  static final DirectoryType INSTANCE = new DirectoryType();\n" +
                "\n" +
                "  static final String NAME_PROPERTY = \"name\";\n" +
                "\n" +
                "  private DirectoryType() {\n" +
                "    super(Directory.class,\n" +
                "                        com.google.common.collect.ImmutableMap.of(NAME_PROPERTY, org.nibiru.model.core.impl.java.JavaType.STRING));\n" +
                "  }\n" +
                "}\n");
    }

    @Test
    public void projectTest() throws Exception {
        testCode(Project.class,
                "package org.nibiru.model.gen;\n" +
                "\n" +
                "import java.lang.String;\n" +
                "import org.nibiru.model.core.impl.BaseComplexType;\n" +
                "\n" +
                "public class ProjectType extends BaseComplexType<Project> {\n" +
                "  static final ProjectType INSTANCE = new ProjectType();\n" +
                "\n" +
                "  static final String ROOT_PROPERTY = \"root\";\n" +
                "\n" +
                "  static final String NAME_PROPERTY = \"name\";\n" +
                "\n" +
                "  private ProjectType() {\n" +
                "    super(Project.class,\n" +
                "                        com.google.common.collect.ImmutableMap.of(ROOT_PROPERTY, org.nibiru.model.gen.DirectoryType.INSTANCE,NAME_PROPERTY, org.nibiru.model.core.impl.java.JavaType.STRING));\n" +
                "  }\n" +
                "}\n");
    }
}
