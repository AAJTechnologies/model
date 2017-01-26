package org.nibiru.model.gen;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TypeGeneratorTests {
    private TypeGenerator generator;

    @Before
    public void setUp() {
        generator = new TypeGenerator();
    }

    @Test
    public void directoryTest() throws Exception {
        String code = generator.generate(Directory.class);

        assertEquals("package org.nibiru.model.gen;\n" +
                "\n" +
                "import java.lang.String;\n" +
                "import org.nibiru.model.core.impl.BaseComplexType;\n" +
                "\n" +
                "public class DirectoryType extends BaseComplexType<Directory> {\n" +
                "  static final DirectoryType INSTANCE;\n" +
                "\n" +
                "  static final String NAME_PROPERTY;\n" +
                "\n" +
                "  private DirectoryType() {\n" +
                "    super(Directory.class,\n" +
                "                        ImmutableMap.of(NAME_PROPERTY, org.nibiru.model.core.impl.java.JavaType.STRING));\n" +
                "  }\n" +
                "}\n", code);
    }

    @Test
    public void projectTest() throws Exception {
        String code = generator.generate(Project.class);

        assertEquals("package org.nibiru.model.gen;\n" +
                "\n" +
                "import java.lang.String;\n" +
                "import org.nibiru.model.core.impl.BaseComplexType;\n" +
                "\n" +
                "public class ProjectType extends BaseComplexType<Project> {\n" +
                "  static final ProjectType INSTANCE;\n" +
                "\n" +
                "  static final String ROOT_PROPERTY;\n" +
                "\n" +
                "  static final String NAME_PROPERTY;\n" +
                "\n" +
                "  private ProjectType() {\n" +
                "    super(Project.class,\n" +
                "                        ImmutableMap.of(ROOT_PROPERTY, org.nibiru.model.gen.DirectoryType.INSTANCE,NAME_PROPERTY, org.nibiru.model.core.impl.java.JavaType.STRING));\n" +
                "  }\n" +
                "}\n", code);
    }
}
