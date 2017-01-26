package org.nibiru.model.gen;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ValueGeneratorTests {
    private ValueGenerator generator;

    @Before
    public void setUp() {
        generator = new ValueGenerator();
    }

    @Test
    public void directoryTest() throws Exception {
        String code = generator.generate(Directory.class);

        assertEquals("package org.nibiru.model.gen;\n" +
                "\n" +
                "import java.lang.Override;\n" +
                "import javax.annotation.Nullable;\n" +
                "import org.nibiru.model.core.api.Property;\n" +
                "import org.nibiru.model.core.api.Type;\n" +
                "import org.nibiru.model.core.api.Value;\n" +
                "import org.nibiru.model.core.impl.BaseComplexValue;\n" +
                "\n" +
                "public class DirectoryValue extends BaseComplexValue<Directory> {\n" +
                "  private DirectoryImpl directory;\n" +
                "\n" +
                "  @Override\n" +
                "  public <X> Value<X> get(Property<X> property) {\n" +
                "    com.google.common.base.Preconditions.checkNotNull(property);\n" +
                "    switch (property.getName()) {\n" +
                "      case DirectoryType.NAME_PROPERTY:\n" +
                "        return (Value<X>) directory.name;\n" +
                "      default:\n" +
                "        throw new IllegalArgumentException(\"Invalid property: \" + property);\n" +
                "    }\n" +
                "  }\n" +
                "\n" +
                "  @Override\n" +
                "  protected void setValue(@Nullable Directory directory) {\n" +
                "    this.directory = (DirectoryImpl) directory;\n" +
                "  }\n" +
                "\n" +
                "  @Override\n" +
                "  @Nullable\n" +
                "  public Directory get() {\n" +
                "    return directory;\n" +
                "  }\n" +
                "\n" +
                "  @Override\n" +
                "  public Type<Directory> getType() {\n" +
                "    return directory.INSTANCE;\n" +
                "  }\n" +
                "}\n", code);
    }

    @Test
    public void projectTest() throws Exception {
        String code = generator.generate(Project.class);

        assertEquals("package org.nibiru.model.gen;\n" +
                "\n" +
                "import java.lang.Override;\n" +
                "import javax.annotation.Nullable;\n" +
                "import org.nibiru.model.core.api.Property;\n" +
                "import org.nibiru.model.core.api.Type;\n" +
                "import org.nibiru.model.core.api.Value;\n" +
                "import org.nibiru.model.core.impl.BaseComplexValue;\n" +
                "\n" +
                "public class ProjectValue extends BaseComplexValue<Project> {\n" +
                "  private ProjectImpl project;\n" +
                "\n" +
                "  @Override\n" +
                "  public <X> Value<X> get(Property<X> property) {\n" +
                "    com.google.common.base.Preconditions.checkNotNull(property);\n" +
                "    switch (property.getName()) {\n" +
                "      case ProjectType.ROOT_PROPERTY:\n" +
                "        return (Value<X>) project.root;\n" +
                "      case ProjectType.NAME_PROPERTY:\n" +
                "        return (Value<X>) project.name;\n" +
                "      default:\n" +
                "        throw new IllegalArgumentException(\"Invalid property: \" + property);\n" +
                "    }\n" +
                "  }\n" +
                "\n" +
                "  @Override\n" +
                "  protected void setValue(@Nullable Project project) {\n" +
                "    this.project = (ProjectImpl) project;\n" +
                "  }\n" +
                "\n" +
                "  @Override\n" +
                "  @Nullable\n" +
                "  public Project get() {\n" +
                "    return project;\n" +
                "  }\n" +
                "\n" +
                "  @Override\n" +
                "  public Type<Project> getType() {\n" +
                "    return project.INSTANCE;\n" +
                "  }\n" +
                "}\n", code);
    }
}
