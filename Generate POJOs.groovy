import com.intellij.database.model.DasTable
import com.intellij.database.util.Case
import com.intellij.database.util.DasUtil

import java.text.SimpleDateFormat

/*
 * Available context bindings:
 *   SELECTION   Iterable<DasObject>
 *   PROJECT     project
 *   FILES       files helper
 */
packageName = ""
typeMapping = [
        (~/(?i)tinyint|smallint|mediumint/)      : "BigDecimal",
        (~/(?i)bool|bit/)                        : "BigDecimal",
        (~/(?i)int/)                             : "BigDecimal",
        (~/(?i)float|double|decimal|real|number/): "BigDecimal",
        (~/(?i)datetime|timestamp/)              : "Date",
        (~/(?i)date/)                            : "Date",
        (~/(?i)time/)                            : "Date",
        (~/(?i)/)                                : "String",
        (~/(?i)blob|binary|bfile|clob|raw|image/): "InputStream"
]

FILES.chooseDirectoryAndSave("Choose directory", "Choose where to store generated files") { dir ->
    SELECTION.filter { it instanceof DasTable }.each { generate(it, dir) }
}

def generate(table, dir) {
    def className = javaName(table.getName(), true)
    def fields = calcFields(table)
    packageName = getPackageName(dir)
    new File(dir, className + ".java").withPrintWriter("utf-8") { out -> generate(out, className, table, fields) }
}

def generate(out, className, table, fields) {

    Set<String> types = new HashSet<String>()
    fields.each() {
        types.add(it.type)
    }

    out.println "package $packageName"
    out.println ""
    out.println "import org.hibernate.annotations.GenericGenerator;"
    out.println "import lombok.Data;"
    out.println "import javax.persistence.*;"
    out.println ""
    if (types.contains("Date") || types.contains("timestamp")) {
        out.println "import java.util.Date;"
        out.println "import com.fasterxml.jackson.annotation.JsonFormat;"
    }

    if (types.contains("InputStream")) {
        out.println "import java.io.InputStream;"
    }

    if (types.contains("BigDecimal")) {
        out.println "import java.math.BigDecimal;"
    }

    out.println ""
    out.println "/**\n" +
            " * @Author hanshuhao \n" +
            " * @description $className \n" +
            " **/"
    out.println "@Data"
    out.println "@Entity"
    out.println "@Table(name = \"" + table.getName() + "\")"
    out.println "public class $className {"
    out.println "    private static final long serialVersionUID = 1734425407533156215L;"
    fields.each() {
        out.println ""
        if (isNotEmpty(it.comment)) {
            out.println "\t/**"
            out.println "\t * ${it.comment.toString()}"
            out.println "\t */"
        }
        if (it.name.toUpperCase().equals("ID")) {
            out.println "    @Id"
            out.println "    @GeneratedValue(generator = \"uuidGenerator\")"
            out.println "    @GenericGenerator(name = \"uuidGenerator\", strategy = \"uuid\")"
        }
        out.println "    @Column(name = \"${it.column}\")"

        if (it.type.toLowerCase().contains("date") || it.type.toLowerCase().contains("timestamp")) {
            out.println "    @JsonFormat(pattern = \"yyyy-MM-dd HH:mm:ss\", timezone = \"GMT+8\")"
        }

        if (it.annos != "") out.println "  ${it.annos}"
//    if (it.comment != "")  out.println "  /*** ${it.comment} */"
        out.println "    private ${it.type} ${it.name};"
    }
    out.println "}"
}

def getPackageName(dir) {
    return dir.toString().replaceAll("\\\\", ".").replaceAll("^.*src(\\.main\\.java\\.)?", "") + ";"
}

def calcFields(table) {
    DasUtil.getColumns(table).reduce([]) { fields, col ->
        def spec = Case.LOWER.apply(col.getDataType().getSpecification())
        def typeStr = typeMapping.find { p, t -> p.matcher(spec).find() }.value
        fields += [[
                           column : col.getName(),
                           comment: col.getComment(),
                           name   : javaName(col.getName(), false),
                           type   : typeStr,
                           annos  : ""]]
    }
}

def javaName(str, capitalize) {
    def s = com.intellij.psi.codeStyle.NameUtil.splitNameIntoWords(str)
            .collect { Case.LOWER.apply(it).capitalize() }
            .join("")
            .replaceAll(/[^\p{javaJavaIdentifierPart}[_]]/, "_")
    capitalize || s.length() == 1 ? s : Case.LOWER.apply(s[0]) + s[1..-1]
}

def isNotEmpty(content) {
    return content != null && content.toString().trim().length() > 0
}
